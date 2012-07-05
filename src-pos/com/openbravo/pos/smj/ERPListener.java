package com.openbravo.pos.smj;
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */    
     
import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.util.AltEncrypter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.w3c.dom.Document;



/**
 * clase para recepcion de mensaje XML provenientes del ERP
 * Processes all messages coming from the synchronization whith ERP
 * @author Carlos Prieto - SmartJSP S.A.S.
 */
public class ERPListener extends  Thread implements MessageListener{

    String subject;
    
    private String url; //"tcp://localhost:61616";
    private PosDAO dao ;
    private AppProperties props;
    DataLogicSystem m_dlSystem;
    private String userLogin;
    private String password;
    private static Logger logger = Logger.getLogger(ERPListener.class.getName());
    private String pcName;
    
    /**
     * 
     * @param props
     * @param m_dlSystem
     * @param pcName 
     */
     public ERPListener(AppProperties props, DataLogicSystem m_dlSystem, String pcName){
        this.pcName = pcName;
        subject = m_dlSystem.getResourceAsText("jms.inqueue");
        url = "failover:"+m_dlSystem.getResourceAsText("jms.url");
       // url = m_dlSystem.getResourceAsText("jms.url");
        userLogin = m_dlSystem.getResourceAsText("jms.userLogin");
        password = m_dlSystem.getResourceAsText("jms.password");
        this.m_dlSystem =m_dlSystem;
        
        if(password.indexOf("crypt") == 0){
            AltEncrypter cypher = new AltEncrypter("cypherkey" + userLogin);
            password = cypher.decrypt(password.substring(6));
        }
        
        this.props = props;
        
        dao = new PosDAO(props);
        dao.setLogger(logger, m_dlSystem);
    }
            
    
    
    
    public void run()  {
        try{
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userLogin, password, url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            //((ActiveMQConnection) connection).isClosed();
            Session session = connection.createSession(false,
            Session.AUTO_ACKNOWLEDGE);
            //Destination destination = session.createTopic(subject);
            Destination destination = session.createQueue(subject);
            MessageConsumer consumer = session.createConsumer(destination);
            
            consumer.setMessageListener(this);
            connection.start();
            System.out.println("Esperando Mensajes desde la cola:"+ subject+" ...");
        }catch(Exception e){
            e.printStackTrace();
            logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
            logger.log(Level.SEVERE, null, e);
        }
    }

    /**
     * processes messages for synchronization of:
     * bParner
     * PRODUCT
     * PRODUCT_CATEGORY
     * PRODUCTPRICE
     * STORAGE
     * BPARTNER_LOCATION
     * LOCATION
     * USER
     * UOM
     * UOM_CONVERSION
     * COUNTRY
     * REGION
     * CITY
     * TAXCATEGORY
     * TAX
     * PRICELISTVERSION
     * CreditCard
     * DELETE-PRODUCT
     * DELETE-PRODUCT-CATEGORY
     * DELETE-BPARTNER
     * DELETE-PHOTO
     * SYNC-END
     * SYNC-END-WITH-ERRORS
     * SYNC-ERROR
     * @param message 
     */
    public void onMessage(Message message) {
        Calendar            lastUpdate = null;
        Calendar            timeStampMessage = null;
        String              xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        SimpleDateFormat    sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        boolean             wereMessageProccess = true;
        
        try {
            timeStampMessage = Calendar.getInstance();
            timeStampMessage.setTimeInMillis(message.getJMSTimestamp());
            try {
                lastUpdate = Calendar.getInstance();
                lastUpdate.setTime(sdf.parse(m_dlSystem.getResourceAsText("jms.lasUpdate")));
            } catch (ParseException ex) {
                Logger.getLogger(ERPListener.class.getName()).log(Level.SEVERE, null, ex);
                logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                logger.log(Level.SEVERE, null, ex);
                lastUpdate = null;
            }
            
            if(lastUpdate != null && (lastUpdate.after(timeStampMessage) || lastUpdate.equals(timeStampMessage))){
                return;
            } 
            logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++++++++++\n\n");
            logger.log(Level.SEVERE, "*"+m_dlSystem.getResourceAsText("jms.lasUpdate")+"*");
            
            if(message instanceof ObjectMessage){
                processImg((ObjectMessage)message);
                return;
            }
            
            TextMessage textMessage = (TextMessage) message;
            
            if(textMessage.getStringProperty("PCName") != null &&  !textMessage.getStringProperty("PCName").equals("") && !textMessage.getStringProperty("PCName").equalsIgnoreCase(pcName))
                return;
            
            
            Document document = null;
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.xml.sax.InputSource inStream = new org.xml.sax.InputSource();
            String xml2 = textMessage.getText();

            // esta seccion limpieza la basura que parece venir en el encabezado del xml
            xml2 = xml2.trim();
            int ini = xml2.indexOf("<entityDetail>");
            xml2 = xml2.substring(ini);
            xml = xml + xml2;
             //     "<entityDetail><type>BPARTNER</type><detail><Value>test</Value><Name>Pedro</Name><Name2>test</Name2></detail></entityDetail>" ;
            //inStream.setEncoding("UTF-8");
            inStream.setCharacterStream(new java.io.StringReader(xml));
            document = builder.parse(inStream);
            String tabla =  document.getElementsByTagName("type").item(0).getTextContent();
            logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
            logger.log(Level.SEVERE, "Mensaje de entrada");
            logger.log(Level.SEVERE, xml);
            if (tabla.equalsIgnoreCase("BPARTNER"))         //Procesa Terceros
            {
                String id =  document.getElementsByTagName("C_BPartner_ID").item(0).getTextContent().trim();	
                String valor =  document.getElementsByTagName("Value").item(0).getTextContent().trim();	
                String nombre =  document.getElementsByTagName("Name").item(0).getTextContent().trim();	
                String nombre2 =  document.getElementsByTagName("Name2").item(0).getTextContent().trim();
                String nombre1 = "";
                if(textMessage.getStringProperty("Value") != null)
                    nombre1 = textMessage.getStringProperty("Value").trim();
                
                String taxId  =  document.getElementsByTagName("TaxID").item(0).getTextContent().trim();
                if (taxId.trim().equalsIgnoreCase("./.")){
                    taxId = "";
                }
                String duns  =  document.getElementsByTagName("DUNS").item(0).getTextContent().trim();
                String esCliente  =  document.getElementsByTagName("IsCustomer").item(0).getTextContent().trim();
                String isActive = document.getElementsByTagName("IsActive").item(0).getTextContent().trim();
                String customerTaxCategory = document.getElementsByTagName("C_TaxGroup_ID").item(0).getTextContent().trim();
                Double creditLimit = new Double(document.getElementsByTagName("SO_CreditLimit").item(0).getTextContent());
                Double creditUsed = new Double(document.getElementsByTagName("SO_CreditUsed").item(0).getTextContent());
                if(customerTaxCategory.equalsIgnoreCase("./.")){
                    customerTaxCategory =null;
                }
                String taxExempt= document.getElementsByTagName("IsTaxExempt").item(0).getTextContent().trim();
 
                if (esCliente.equalsIgnoreCase("true")) 
                {   // procesado solo si es cliente y su TaxID (cedula) no esta vacia
                    dao.procesaTercero(id,taxId,nombre1,nombre2,valor,duns,isActive,customerTaxCategory,nombre, creditLimit,creditUsed,taxExempt);
                }
            }
            else if (tabla.equalsIgnoreCase("PRODUCT")) {  // Procesa productos
                    String id =  document.getElementsByTagName("M_Product_ID").item(0).getTextContent().trim();	
                    String valor =  document.getElementsByTagName("Value").item(0).getTextContent().trim();
                    String nombre1 =  document.getElementsByTagName("Name").item(0).getTextContent().trim();
                    String categoria =  document.getElementsByTagName("M_Product_Category_ID").item(0).getTextContent().trim();
                    String unidad =  document.getElementsByTagName("SKU").item(0).getTextContent().trim();
                    String accesorio =  document.getElementsByTagName("Group1").item(0).getTextContent().trim();
                    String cocina =  document.getElementsByTagName("Group2").item(0).getTextContent().trim();
                    String ayuda =  document.getElementsByTagName("Help").item(0).getTextContent().trim();
                    String umoId = document.getElementsByTagName("C_UOM_ID").item(0).getTextContent().trim();
                    String taxCategoryID = document.getElementsByTagName("C_TaxCategory_ID").item(0).getTextContent().trim();
                    String upc = document.getElementsByTagName("UPC").item(0).getTextContent().trim();
                    if(upc.equals("./.")){
                        upc = "C" + valor;
                    }
                    boolean isActive =Boolean.parseBoolean(document.getElementsByTagName("IsActive").item(0).getTextContent().trim());
                    String imgUrl = document.getElementsByTagName("ImageURL").item(0).getTextContent().trim();
                    dao.procesaProducto(id,valor,nombre1,categoria,unidad,accesorio,ayuda,cocina,umoId,taxCategoryID,isActive, upc, imgUrl);
            }	
            else if (tabla.equalsIgnoreCase("PRODUCT_CATEGORY")) {  // procesa categorias de productos
                    String id =  document.getElementsByTagName("M_Product_Category_ID").item(0).getTextContent().trim();
                    String nombre =  document.getElementsByTagName("Name").item(0).getTextContent().trim();	
                    boolean isActive =Boolean.parseBoolean(document.getElementsByTagName("IsActive").item(0).getTextContent().trim());
                    dao.procesaCategoriasProd(id,nombre,isActive);	
                    }	
            else if (tabla.equalsIgnoreCase("PRODUCTPRICE")) {  // procesa precios de producto
                    String prodId =  document.getElementsByTagName("M_Product_ID").item(0).getTextContent().trim();
                    String precioLista =  document.getElementsByTagName("PriceList").item(0).getTextContent().trim();
                    String precioEstandar = document.getElementsByTagName("PriceStd").item(0).getTextContent().trim();
                    String precioLimite = document.getElementsByTagName("PriceLimit").item(0).getTextContent().trim();
                    dao.procesaPrecioProd(prodId,precioLista,precioEstandar,precioLimite);	
                    }
            else if (tabla.equalsIgnoreCase("STORAGE")) {  // procesa precios de producto
                    String prodId =  document.getElementsByTagName("M_Product_ID").item(0).getTextContent().trim();
                    String existencias =  textMessage.getStringProperty("Value").trim();
                    dao.procesaStockProd(prodId,existencias);	
                    }

            else if (tabla.equalsIgnoreCase("BPARTNER_LOCATION"))         //Procesa localizaciones de terceros (relacion)
            {
                    String id =  document.getElementsByTagName("C_BPartner_ID").item(0).getTextContent().trim();
                    String phone =  document.getElementsByTagName("Phone").item(0).getTextContent().trim();
                    String cellPhone =  document.getElementsByTagName("Phone2").item(0).getTextContent().trim();
                    String locId =  document.getElementsByTagName("C_Location_ID").item(0).getTextContent().trim();
                    String fax = document.getElementsByTagName("Fax").item(0).getTextContent().trim();


                    dao.procesaBPLocation( fax,phone,cellPhone,locId,id);
            }
            else if (tabla.equalsIgnoreCase("LOCATION"))         //Procesa localizaciones detalle 
            {
                String locId =  document.getElementsByTagName("C_Location_ID").item(0).getTextContent().trim();
                String add1 =  document.getElementsByTagName("Address1").item(0).getTextContent().trim();
                String add2 =  document.getElementsByTagName("Address2").item(0).getTextContent().trim();
                String city =  document.getElementsByTagName("C_City_ID").item(0).getTextContent().trim();
                String regionName =  document.getElementsByTagName("C_Region_ID").item(0).getTextContent().trim();
                String countryId =document.getElementsByTagName("C_Country_ID").item(0).getTextContent().trim();
                dao.procesaLocation(add1,add2,city,regionName,locId,countryId);
            }
            else if (tabla.equalsIgnoreCase("USER"))         //Procesa informacion de usuario (email)
            {
                String id =  document.getElementsByTagName("C_BPartner_ID").item(0).getTextContent().trim();
                String email =  document.getElementsByTagName("EMail").item(0).getTextContent().trim();
                dao.procesaUsuario(email,id);

            } 
            else if(tabla.equalsIgnoreCase("UOM"))//Procesa unidades detalle 
            {                  
                String id = document.getElementsByTagName("C_UOM_ID").item(0).getTextContent().trim();
                String code = document.getElementsByTagName("UOMSymbol").item(0).getTextContent().trim();
                String name = document.getElementsByTagName("Name").item(0).getTextContent().trim();
                String costingPrecision = document.getElementsByTagName("CostingPrecision").item(0).getTextContent().trim();
                String type = document.getElementsByTagName("UOMType").item(0).getTextContent().trim();
                String stdPrecision = document.getElementsByTagName("StdPrecision").item(0).getTextContent();
                dao.procesaUnidad(id,code,name,costingPrecision,type,stdPrecision);
            }
            else if(tabla.equalsIgnoreCase("UOM_CONVERSION")){ //Procesa conversiones de unidades detalle 
                String id = document.getElementsByTagName("C_UOM_Conversion_ID").item(0).getTextContent().trim();
                String unitId = document.getElementsByTagName("C_UOM_ID").item(0).getTextContent().trim();
                String unitToId = document.getElementsByTagName("C_UOM_To_ID").item(0).getTextContent().trim();
                String divideRate = document.getElementsByTagName("DivideRate").item(0).getTextContent().trim();
                String multiplyRate = document.getElementsByTagName("MultiplyRate").item(0).getTextContent().trim();
                String productId = document.getElementsByTagName("M_Product_ID").item(0).getTextContent().trim();
                dao.procesaConversionUnidad(id,unitId,unitToId,divideRate,multiplyRate,productId);
            }
            else if(tabla.equalsIgnoreCase("COUNTRY")){ //Procesa paises detalle 
                String id = document.getElementsByTagName("C_Country_ID").item(0).getTextContent().trim();
                String currencyID = document.getElementsByTagName("C_Currency_ID").item(0).getTextContent().trim();
                String countryCode = document.getElementsByTagName("CountryCode").item(0).getTextContent().trim();
                String description = document.getElementsByTagName("Description").item(0).getTextContent().trim();
                String name = document.getElementsByTagName("Name").item(0).getTextContent().trim();
                String regionName = document.getElementsByTagName("RegionName").item(0).getTextContent().trim();
                dao.procesaPais(id, currencyID, countryCode, description, name, regionName);
            }
            else if(tabla.equalsIgnoreCase("REGION")){ //Procesa regiones detalle 
                String id = document.getElementsByTagName("C_Region_ID").item(0).getTextContent().trim();
                String countryID = document.getElementsByTagName("C_Country_ID").item(0).getTextContent().trim();
                String description = document.getElementsByTagName("Description").item(0).getTextContent().trim();
                String name = document.getElementsByTagName("Name").item(0).getTextContent().trim();
                dao.procesaRegion(id, countryID, description, name);
            }
            else if(tabla.equalsIgnoreCase("CITY")){ //Procesa ciudades detalle 
                String id = document.getElementsByTagName("C_City_ID").item(0).getTextContent().trim();
                String countryID = document.getElementsByTagName("C_Country_ID").item(0).getTextContent().trim();
                String regionID = document.getElementsByTagName("C_Region_ID").item(0).getTextContent().trim();
                String name = document.getElementsByTagName("Name").item(0).getTextContent().trim();
                String postal = document.getElementsByTagName("Postal").item(0).getTextContent().trim();
                dao.procesaCiudad(id, countryID, regionID, name, postal);
            }
            else if(tabla.equalsIgnoreCase("TAXCATEGORY")){ //Procesa categorias de impuestos detalle 
                String id = document.getElementsByTagName("C_TaxCategory_ID").item(0).getTextContent().trim();
                String name = document.getElementsByTagName("Name").item(0).getTextContent().trim();
                dao.procesaCategoriasDeImpuestos(id, name);
                dao.procesaCategoriasDeImpuestosPorClientes(id, name);
            }
            else if(tabla.equalsIgnoreCase("TAX")){ //Procesa impuestos detalle 
                String id = document.getElementsByTagName("C_Tax_ID").item(0).getTextContent().trim();
                String name = document.getElementsByTagName("Name").item(0).getTextContent().trim();

                String validfromStr = document.getElementsByTagName("ValidFrom").item(0).getTextContent().trim();
                SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date validfrom = formatoDelTexto.parse(validfromStr);

                String categoryID = document.getElementsByTagName("C_TaxCategory_ID").item(0).getTextContent().trim();
                double rate = Double.parseDouble(document.getElementsByTagName("Rate").item(0).getTextContent().trim());
                dao.procesaImpuestos(id, name, validfrom, categoryID, rate);
            }
            else if(tabla.equalsIgnoreCase("PRICELISTVERSION")){ //Procesa precios de productos por unidad detalle 
                String productid = document.getElementsByTagName("M_PRODUCT_ID").item(0).getTextContent().trim();
                String unitName = document.getElementsByTagName("VERSION").item(0).getTextContent().trim();
                double priceList = Double.parseDouble(document.getElementsByTagName("PRICELIST").item(0).getTextContent().trim());
                double priceSTD = Double.parseDouble(document.getElementsByTagName("PRICESTD").item(0).getTextContent().trim());
                double priceLimit = Double.parseDouble(document.getElementsByTagName("PRICELIMIT").item(0).getTextContent().trim());
                String isDefaultPos = document.getElementsByTagName("DEFAULTSALESPOS").item(0).getTextContent().trim();

                dao.procesaPrecioUnd(productid, unitName, priceList, priceSTD, priceLimit,isDefaultPos);
            }
            else if(tabla.equalsIgnoreCase("CreditCard")){ //Procesa nombres de tarjetas de credito detalle 
                String value = document.getElementsByTagName("Value").item(0).getTextContent().trim();
                String nameCC = document.getElementsByTagName("Name").item(0).getTextContent().trim();
                dao.procesaCreditCard(nameCC);
            }
            else if(tabla.equalsIgnoreCase("DELETE-PRODUCT")){ //Procesa borrado de productos detalle 
                String id = document.getElementsByTagName("PRODUCT-ID").item(0).getTextContent().trim();
                dao.procesaBorradoProducto(id);
            }
            else if(tabla.equalsIgnoreCase("DELETE-PRODUCT-CATEGORY")){//Procesa borrado de categorias de productos detalle 
                String id = document.getElementsByTagName("PRODUCT-CATEGORY-ID").item(0).getTextContent().trim();
                dao.procesaBorradoCategoriaProducto(id);
            }
            else if(tabla.equalsIgnoreCase("DELETE-BPARTNER")){//Procesa borrado de clientes detalle 
                String id = document.getElementsByTagName("BPARTNER-ID").item(0).getTextContent().trim();
                dao.procesaBorradoClientes(id);
            }
            else if(tabla.equalsIgnoreCase("DELETE-PHOTO")){//Procesa borrado de fotos detalle 
                String id = document.getElementsByTagName("PHOTO-ID").item(0).getTextContent().trim();
                dao.procesaImgProducto(null, id);
            }
            else if(tabla.equalsIgnoreCase("SYNC-END")){//Procesa mensaje de fin de sincronizacion detalle 
                m_dlSystem.setResource("jms.message",0,"SYNC-END".getBytes());
            }
            else if(tabla.equalsIgnoreCase("SYNC-END-WITH-ERRORS")){
                m_dlSystem.setResource("jms.message",0,"SYNC-END-WITH-ERRORS".getBytes());
            }
            else if(tabla.equalsIgnoreCase("SYNC-ERROR")){//Procesa mensaje de error de sincronizacion detalle 
                String error = m_dlSystem.getResourceAsText("jms.error");
                error += "\n\n" +document.getElementsByTagName("Value").item(0).getTextContent().trim();
                m_dlSystem.setResource("jms.message",0,"SYNC-ERROR".getBytes());
                m_dlSystem.setResource("jms.error",0,error.getBytes());
            }
            else  {
                logger.log(Level.FINE, "Mensaje no reconocido aun por el POS");
                wereMessageProccess = false;
            }
            
            if(wereMessageProccess){
                m_dlSystem.setResource("jms.lasUpdate", 0, sdf.format(timeStampMessage.getTime()).getBytes());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
            logger.log(Level.SEVERE, null, e);
        }
    }

    
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * processed product images
     * @param objectMessage 
     */
    private void processImg(ObjectMessage objectMessage) {
        try {
            byte[] barray = (byte[])objectMessage.getObject();
            System.out.println(barray);
            System.out.println("Value:"+objectMessage.getStringProperty("Value"));
            dao.procesaImgProducto(barray, objectMessage.getStringProperty("Value"));
        } catch (JMSException ex) {
            Logger.getLogger(ERPListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
