package com.openbravo.pos.smj;

import com.openbravo.pos.forms.AppProperties;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.forms.DriverWrapper;
import com.openbravo.pos.util.AltEncrypter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para manejo de logica de sincronizacion con el ERP
 * Process all transactions database synchronization process of the ERP to POS
 * @author pedrorozo - SmartJSP S.A.S.
 *
 */
public class PosDAO {
	private Connection con;
    private Statement st;
    private ResultSet rs;
    private String mensaje;
    private String url;
    private String usuario;
    private String clave;
    private AppProperties props;
    private Logger logger;
    private DataLogicSystem dlSystem;
    

    /*
     * Logger settings for error log
     */
    public void setLogger(Logger logger, DataLogicSystem dlSystem) {
        this.logger = logger;
        this.dlSystem = dlSystem;
    }
    
    
    
	
    /**
     * Constructor
     */
    public PosDAO(AppProperties props) {
    	try {
            this.props = props;
            Conexion();
	} catch (SQLException e) {
            logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n");
            logger.log(Level.SEVERE, null, e);
	} catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n");
            logger.log(Level.SEVERE, null, e);
	}
    }
    
    /**
     * Saves the errors presented in  syncronizacion on resource jms.error 
     * @param errorMsj
     * @param t 
     */
    private void setErrorInLog(String errorMsj,Throwable t){
        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
        logger.log(Level.SEVERE, errorMsj, t);
        String error = dlSystem.getResourceAsText("jms.error");
        error += "\n\n" +errorMsj;
        dlSystem.setResource("jms.message",0,"SYNC-ERROR".getBytes());
        dlSystem.setResource("jms.error",0,error.getBytes());
    }
	
    /**
     *  Processes a BPartner, if you update it, if you do not believe (veficia by ID)
     *  Tabla: BPARTNER  
     * @param id
     * @param taxId
     * @param nombre1
     * @param nombre2
     * @param valor
     * @param duns
     */
    void procesaTercero (String id,String taxId,String nombre1,String nombre2,String valor,String duns,String isActive,String category, String nombre, double creditLimit,double creditUsed, String taxExempt)
    {
        try {
            PreparedStatement ps = con.prepareStatement("select id  from customers where id=?");
            ps.setString(1, id.trim());
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                ps = con.prepareStatement("update customers set searchkey=?,firstname=?, lastname=?,notes=?,name=?, visible=?, taxcategory=?, maxdebt=?, curdebt=?, tax_exempt =?, taxid=? where id=?");
                ps.setString(1, id);
                ps.setString(2, nombre1);
                ps.setString(3, nombre2);
                ps.setString(4, duns);
                ps.setString(5, nombre1.trim()+" "+ nombre2.trim());
                ps.setBoolean(6,Boolean.parseBoolean(isActive));
                ps.setString(7,category);
                ps.setDouble(8, creditLimit);
                ps.setDouble(9, creditUsed);
                ps.setBoolean(10,Boolean.parseBoolean(taxExempt));
                ps.setString(11, taxId.trim());
                ps.setString(12, id);

                int i = ps.executeUpdate();
                 if (i != 0) {
                     logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                     logger.log(Level.SEVERE, "tercero updated");
                 } else {
                     logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                     logger.log(Level.SEVERE, "tercero not updated");
                 }
            } else {
                ps = con.prepareStatement("select taxid  from customers where taxid=? " );
                ps.setString(1, taxId.trim());
                ResultSet res2 = ps.executeQuery();

                if(res2.next()){
                    ps = con.prepareStatement("update customers set searchkey=?,firstname=?, lastname=?,notes=?,name=?, visible=?, taxcategory=?,  id=?, maxdebt=?, curdebt=?, tax_exempt =? where taxid=? ");
                    ps.setString(1, id);
                    ps.setString(2, nombre1);
                    ps.setString(3, nombre2);
                    ps.setString(4, duns);
                    ps.setString(5, nombre1.trim()+" "+ nombre2.trim());
                    ps.setBoolean(6,Boolean.parseBoolean(isActive));
                    ps.setString(7,category);
                    ps.setString(8, id);
                    ps.setDouble(9, creditLimit);
                    ps.setDouble(10, creditUsed);
                    ps.setBoolean(11,Boolean.parseBoolean(taxExempt));
                    ps.setString(12, taxId.trim());

                    int i = ps.executeUpdate();
                    if (i != 0) {
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                        logger.log(Level.SEVERE, "tercero updated");
                    } else {
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                        logger.log(Level.SEVERE, "tercero not updated");
                    }
                }else{
                    ps = con.prepareStatement("insert into customers(id, firstname, lastname,searchkey, notes,taxid,name,taxcategory,curdebt, visible, maxdebt,tax_exempt) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setString(1, id);
                    ps.setString(2, nombre1);
                    ps.setString(3, nombre2);
                    ps.setString(4, id);
                    ps.setString(5, duns);
                    ps.setString(6, taxId);
                    ps.setString(7, nombre);
                    ps.setString(8, category);
                    ps.setDouble(9, creditUsed);    //credito = 0  - local en el pos
                    ps.setBoolean(10, Boolean.parseBoolean(isActive));
                    ps.setDouble(11, creditLimit);
                    ps.setBoolean(12, Boolean.parseBoolean(taxExempt));
                    int i = ps.executeUpdate();
                    if (i != 0){
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE, "tercero not Inserted");
                    } 
                    else {
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE, "tercero not Inserted");
                    }
                }
                res2.close();
            }
            res.close();
        } catch (SQLException ex) {
            setErrorInLog("Business Partner Error", ex);
        }
    	

    	
    }
/**
 * Processing a product, if you update it, if you do not believe (veficia by ID)
     *  Tabla: PRODUCT
 * @param id
 * @param valor
 * @param nombre1
 * @param categoria
 * @param unidad
 * @param accesorio
 * @param ayuda
 * @param uomId
 */
    void procesaProducto (String id,String valor,String nombre1,String categoria,String unidad, String accesorio,String ayuda,String cocina,String uomId,String taxCategoryId, boolean  isActive, String upc,String imgUrl)
    {
        // hace un select para mirar si el tercero existe, si esta lo actualiza, si no lo crea
    	try {
    	PreparedStatement ps = con.prepareStatement("select id  from products where id=?");
        ps.setString(1, id);
        ResultSet res = ps.executeQuery();
        if (!categoria.trim().equalsIgnoreCase("1000018"))  //no es materia prima
        {
            if (!res.next()) {              // no existia el usuario   (insert)
                    ps = con.prepareStatement("insert into products(id, name, category,attributes,reference,code,pricebuy,pricesell,taxcat,unit) values(?,?,?,?,?,?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, nombre1);
                ps.setString(3, categoria);
                // adicionar segun la categoria el envio a bar, lo demas va para la cocina
                String atri = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">"+
                    "<properties>"+
                    "<entry key=\"printkb\">"+cocina.trim() +"</entry>"+ 
                    "<entry key=\"sendstatus\">No</entry>"+
                    "<entry key=\"accesorio\">"+ accesorio +"</entry>"+
                    "<entry key=\"unidad\">"+unidad+"</entry>"+
                    "<entry key=\"info\">"+ayuda+"</entry>"+
                    "</properties>";
                ByteArrayInputStream b = new ByteArrayInputStream (atri.getBytes());
                ps.setBinaryStream(4, (InputStream) b, (int) atri.length());
                ps.setString(5, valor +  "  ");   //reference
                ps.setString(6, upc);  //code
                ps.setInt(7, 0);   //pricebuy
                ps.setInt(8, 0);   //pricesell
                ps.setString(9,taxCategoryId );   //taxcat
                ps.setString(10,uomId );   //uomId (unidad por defecto)

                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE, "Prod Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Prod not Inserted");
                }

                ps = con.prepareStatement("insert into stockcurrent(product, units,location) values(?,?,?)");

             //inserta existencias del producto en la tabla stockcurrent

                ps.setString(1, id);
                ps.setInt(2, 0);
                ps.setString(3, "0");

                i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"stock current Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"stock current not Inserted");
                }

    //inserta el id del producto en la tabla products_cat
                procesaProductsCat(id, isActive);

                procesaConversionUnidad("id-"+id+"umo"+uomId +"umoid"+uomId , uomId ,uomId ,"1","1", id);

            } else {                             // si existia el usuario  (update)
             ps = con.prepareStatement("update products set name=?,category=?, attributes=?,unit=?, taxcat=?, code =?, reference=?  where id=?");
             ps.setString(1, nombre1);
             ps.setString(2, categoria);
             // adicionar segun la categoria el envio a bar, lo demas va para la cocina
             String atri = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">"+
                    "<properties>"+
                    "<entry key=\"printkb\">"+cocina.trim()+"</entry>"+ 
                    "<entry key=\"sendstatus\">No</entry>"+
                    "<entry key=\"accesorio\">"+ accesorio +"</entry>"+
                    "<entry key=\"unidad\">"+unidad+"</entry>"+
                    "<entry key=\"info\">"+ayuda+"</entry>"+
                    "</properties>";
             ByteArrayInputStream b = new ByteArrayInputStream (atri.getBytes());
             ps.setBinaryStream(3, (InputStream) b, (int) atri.length());
             ps.setString(4, uomId);
             ps.setString(5, taxCategoryId);
             ps.setString(6, upc);
             ps.setString(7, valor+"  ");
             ps.setString(8, id);

             int i = ps.executeUpdate();
              if (i != 0) {
                  logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Prod updated");
              } else {
                  logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Prod not updated");
              }
              procesaProductsCat(id, isActive);
            }
            
            
        }
        res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("Product error " +id + " " + nombre1, e);
	}
    	
    }
    
    /**
     * process a images  product. stored in the database as a byte array
     * @param imgByteArray
     * @param id 
     */
    void procesaImgProducto(byte[] imgByteArray, String id){
        try {
            PreparedStatement ps = con.prepareStatement("update products set image=?  where id=?");
            ps.setBytes(1, imgByteArray);
            ps.setString(2, id);
            
            int i = ps.executeUpdate();
            if (i != 0) {
                logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                logger.log(Level.SEVERE,"Prod IMG updated");
            } else {
                logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                logger.log(Level.SEVERE,"Prod IMG not updated");
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(PosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   
    /**
     * processes and product catalogs on or off depending on the case
     * @param productId
     * @param isActive 
     */
    void procesaProductsCat(String productId,boolean isActive){
        PreparedStatement ps = null;
        ResultSet res = null;
        int i =0;
        try {
            ps = con.prepareStatement("select product  from products_cat where product=?");
            ps.setString(1, productId);
            res = ps.executeQuery();
            if(!res.next()){      
                if(isActive){
                    ps = con.prepareStatement("insert into products_cat(product, catorder) values(?,?)");
                    ps.setString(1, productId);
                    ps.setInt(2, 0);

                    i = ps.executeUpdate();
                    if (i != 0){
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"Prod cat Inserted");
                    } 
                    else {
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"Prod cat not Inserted");
                    }
                }
            }else if(!isActive){
                ps = con.prepareStatement("delete from products_cat where product=?");
                    ps.setString(1, productId);

                    i = ps.executeUpdate();
                    if (i != 0){
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"Prod cat deleted");
                    } 
                    else {
                        logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"Prod cat not deleted");
                    }
            }
        } catch (SQLException ex) {
            setErrorInLog("Prod catalog error", ex);
        }
    }

    /**
     * Processed product category (the id and the name will be equal in the POS from the name of the ERP
     * @param nombre
     */
    void procesaCategoriasProd (String id,String nombre,boolean isActive)
    {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from categories where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            if (!(id.trim().equalsIgnoreCase("1000018")))  //no es materia prima 
            {
            if (!res.next() ) {    // no existia el usuario y    (insert)
                    ps = con.prepareStatement("insert into categories(id, name,visible) values(?,?,?)");
                ps.setString(1, id);
                ps.setString(2, nombre);
                ps.setBoolean(3, isActive);

                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Cat Prod Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Cat Prod not Inserted");
                }

            }   
            else {                              // si existia el usuario  (update)
             ps = con.prepareStatement("update categories set name=?, visible=? where id=?");
             ps.setString(1, nombre);
             ps.setBoolean(2, isActive);
             ps.setString(3,id);

             int i = ps.executeUpdate();
              if (i != 0) {
                  logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Cat Prod updated");
              } else {
                  logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Cat Prod not updated");
              }
            }
        }
        res.close();	
    	
    	} catch (Exception e) {
           setErrorInLog("Product categories error ", e);
    	}
    }
    
    
    /**
     * Processes price changes
     * @param idProd
     * @param precio
     */
    void procesaPrecioProd (String idProd,String precio, String precioStd,String precioLimite)
    {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	try {
    	PreparedStatement ps = con.prepareStatement("update products set pricesell=?, pricestd=?, pricelimit=? where id=?");
         ps.setDouble(1, Double.parseDouble(precio));
         ps.setDouble(2, Double.parseDouble(precioStd));
         ps.setDouble(3, Double.parseDouble(precioLimite));
         ps.setString(4,idProd);
         
         int i = ps.executeUpdate();
          if (i != 0) {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Precio Prod updated");
          } else {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Precio not updated");
          }
            
//            procesaPrecioUndConId(idProd,);
    	
    	} catch (Exception e) {
             setErrorInLog("Product prices error", e);
	}
    	
    }
    
    
    /**
     * Processes inventory changes
     * @param idProd
     * @param precio
     */
    void procesaStockProd (String idProd,String stock)
    {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	try {
    	//PreparedStatement ps = con.prepareStatement("update products set stockvolume=? where id=?");
    	PreparedStatement ps = con.prepareStatement("update stockcurrent set units=? where product=?");
         ps.setDouble(1, Double.parseDouble(stock));
         ps.setString(2,idProd);
         
         int i = ps.executeUpdate();
          if (i != 0) {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Stock Prod updated");
          } else {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Stock not updated");
          }
    	
    	} catch (Exception e) {
             setErrorInLog("Product stock error", e);
	}
    	
    }
    
    /**
     * Process email changes 
     * @param email
     * @param precio
     */
    void procesaUsuario (String email,String id)
    {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	try {
    		PreparedStatement ps = con.prepareStatement("update customers set email=? where id=?");
            ps.setString(1, email); 
            ps.setString(2, id);
         int i = ps.executeUpdate();
          if (i != 0) {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"email updated");
          } else {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"email not updated");
          }
    	
    	} catch (Exception e) {
             setErrorInLog("User error", e);
	}
    	
    }
    
    /**
     * Processing changes in the location table from BP - Locations
     * @param email
     * @param precio
     */
    void procesaBPLocation (String fax, String phone, String cellPhone,String locId, String id)
    {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	try {
    		PreparedStatement ps = con.prepareStatement("update customers set fax=?, phone=?,phone2=?,address2=? where id=?");
                ps.setString(1, fax); 
                ps.setString(2, phone); 
                ps.setString(3, cellPhone);
                ps.setString(4, locId);
                ps.setString(5, id);
         int i = ps.executeUpdate();
          if (i != 0) {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"phone updated");
          } else {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"phone not updated");
          }
    	
    	} catch (Exception e) {
             setErrorInLog("BP Location error", e);
	}
    	
    }
    /**
     * Update existing locations with the text of the address, city and region
     * @param add1
     * @param add2
     * @param city
     * @param regionName
     * @param locId
     */
    void procesaLocation (String add1, String add2, String city, String regionName,String locId,String  countryId)
    {
        String            countryName = "";
        PreparedStatement ps = null;
        ResultSet         rs = null;
        
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	try {
            ps = con.prepareStatement("Select name from country where id = ?");
            ps.setString(1, countryId);
            rs = ps.executeQuery();
            if(rs.next())
                countryName = rs.getString("name");
            
            rs.close();
            ps.close();
            
            ps = con.prepareStatement("Select name from region where id = ?");
            ps.setString(1, regionName);
            rs = ps.executeQuery();
            if(rs.next())
                regionName = rs.getString("name");
            
            ps = con.prepareStatement("Select name from city where id = ?");
            ps.setString(1, city);
            rs = ps.executeQuery();
            if(rs.next())
                city = rs.getString("name");
            
            
            
            ps = con.prepareStatement("update customers set address=?,city=?,region=?,country=? where address2=?");
            ps.setString(1, add1.trim());//+" "+add2.trim()); 
            ps.setString(2, city);
            ps.setString(3, regionName);
            ps.setString(4, countryName);
            ps.setString(5, locId);
         int i = ps.executeUpdate();
          if (i != 0) {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"location updated");
          } else {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"location not updated");
          }
    	
    	} catch (Exception e) {
            setErrorInLog("BP Location error 1", e);
        }finally{
            try {
                if(rs != null && !rs.isClosed()){
                    ps.close();
                }
            } catch (SQLException ex) {
                setErrorInLog("BP Location error 2", ex);
            }
            try {
                if(ps != null && !ps.isClosed()){
                    ps.close();
                }
            } catch (SQLException ex) {
                setErrorInLog("BP Location error 3", ex);
            }
        }
    	
    }
    
    /**
     * processing units derived from ERP
     * @param id
     * @param code
     * @param name
     * @param costingPrecision
     * @param type
     * @param stdPrecision 
     */
    void procesaUnidad(String id, String code, String name,String costingPrecision, String type, String stdPrecision){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from units where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into units(id, code, name, costing_precision, type, std_precision) values(?,?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, code);
                ps.setString(3, name);
                ps.setDouble(4, Double.parseDouble(costingPrecision));
                ps.setString(5, type);
                ps.setDouble(6, Double.parseDouble(stdPrecision));

                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"unit Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"inset not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
             ps = con.prepareStatement("update units set code =?, name=?, costing_precision =?, type=?, std_precision =?   where id=?");
             ps.setString(1, code);
             ps.setString(2, name);
             ps.setDouble(3, Double.parseDouble(costingPrecision));
             ps.setString(4, type);
             ps.setDouble(5, Double.parseDouble(stdPrecision));
             ps.setString(6,id);

             int i = ps.executeUpdate();
              if (i != 0) {
                  logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"unit updated");
              } else {
                  logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"unit not updated");
              }
            }
            res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("Unit error", e);
    	}
    }
    
    /**
     * Processing unit conversions product
     * @param id
     * @param unitId
     * @param unitToId
     * @param divideRate
     * @param multiplyRate
     * @param productId 
     */
    void procesaConversionUnidad(String id, String unitId, String unitToId,String divideRate, String multiplyRate,String productId){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from additional_prices_for_products where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into additional_prices_for_products(id, unit_id, unit_to_id, divide_rate, multiply_rate,product_id) values(?,?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, unitId);
                ps.setString(3, unitToId);
                ps.setDouble(4, Double.parseDouble(divideRate));
                ps.setDouble(5, Double.parseDouble(multiplyRate));
                ps.setString(6, productId);

                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"unit Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"inset not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
                 ps = con.prepareStatement("update additional_prices_for_products set unit_id =?, unit_to_id=?, divide_rate=?, multiply_rate=?, product_id =? where id=?");
                 ps.setString(1, unitId);
                 ps.setString(2, unitToId);
                 ps.setDouble(3, Double.parseDouble(divideRate));
                 ps.setDouble(4, Double.parseDouble(multiplyRate));
                 ps.setString(5, productId);
                 ps.setString(6,id);

                 int i = ps.executeUpdate();
                  if (i != 0) {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"additional_prices_for_products updated");
                  } else {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"additional_prices_for_products not updated");
                  }
            }
            res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("Unit conversion error", e);
    	}
    }
    
    /**
     * process countries
     * @param id
     * @param currencyID
     * @param countryCode
     * @param description
     * @param name
     * @param regionName 
     */
    void procesaPais(String id, String currencyID, String countryCode,String description, String name,String regionName){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from country where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into country(id, currency_id, country_code, description, name,region_name) values(?,?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, currencyID);
                ps.setString(3, countryCode);
                ps.setString(4, description);
                ps.setString(5, name);
                ps.setString(6, regionName);

                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"country Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"country not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
                 ps = con.prepareStatement("update country set currency_id =?, country_code=?, description=?, name=?, region_name =? where id=?");
                 ps.setString(1, currencyID);
                 ps.setString(2, countryCode);
                 ps.setString(3, description);
                 ps.setString(4, name);
                 ps.setString(5, regionName);
                 ps.setString(6,id);

                 int i = ps.executeUpdate();
                  if (i != 0) {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"country updated");
                  } else {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"country not updated");
                  }
            }
            res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("Country error", e);
    	}
    }
    
    /**
     * process regions for  information from bpartner
     * @param id
     * @param countryID
     * @param description
     * @param name 
     */
    void procesaRegion(String id, String countryID, String description, String name){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from region where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into region(id, country_id, description, name) values(?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, countryID);
                ps.setString(3, description);
                ps.setString(4, name);

                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Region Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"Region not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
                 ps = con.prepareStatement("update region set country_id =?, description=?, name=? where id=?");
                 ps.setString(1, countryID);
                 ps.setString(2, description);
                 ps.setString(3, name);
                 ps.setString(4,id);

                 int i = ps.executeUpdate();
                  if (i != 0) {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"Region updated");
                  } else {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"Region not updated");
                  }
            }
            res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("Region error", e);
    	}
    }
    
    /**
     * process cities for partner
     * @param id
     * @param countryID
     * @param regionID
     * @param name
     * @param postal 
     */
    void procesaCiudad(String id, String countryID, String regionID, String name,String postal){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from city where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into city(id, country_id, region_id,name, postal) values(?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, countryID);
                ps.setString(3, regionID);
                ps.setString(4, name);
                ps.setString(5, postal);

                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"city Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"city not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
                 ps = con.prepareStatement("update city set country_id =?, region_id=?, name=?, postal=? where id=?");
                 ps.setString(1, countryID);
                 ps.setString(2, regionID);
                 ps.setString(3, name);
                 ps.setString(4, postal);
                 ps.setString(5,id);

                 int i = ps.executeUpdate();
                  if (i != 0) {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"city updated");
                  } else {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"city not updated");
                  }
            }
            res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("City error", e);
    	}
    }
    
    /**
     * processes the tax category
     * @param id
     * @param name 
     */
    void procesaCategoriasDeImpuestos(String id, String name){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from taxcategories where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into taxcategories(id, name) values(?,?)");
                ps.setString(1, id);
                ps.setString(2, name);
                                
                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"taxcategories Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"taxcategories not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
                 ps = con.prepareStatement("update taxcategories set name =? where id=?");
                 ps.setString(1, name);
                 ps.setString(2,id);

                 int i = ps.executeUpdate();
                  if (i != 0) {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"taxcategories updated");
                  } else {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"taxcategories not updated");
                  }
            }
            res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("Tax category error", e);
    	}
    }
    
    /**
     * processes the tax categories for clients
     * @param id
     * @param name 
     */
    void procesaCategoriasDeImpuestosPorClientes(String id, String name){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            PreparedStatement ps = con.prepareStatement("select id  from taxcustcategories where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into taxcustcategories(id, name) values(?,?)");
                ps.setString(1, id);
                ps.setString(2, name);
                                
                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"taxcustcategories Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"taxcustcategories not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
                 ps = con.prepareStatement("update taxcustcategories set name =? where id=?");
                 ps.setString(1, name);
                 ps.setString(2,id);

                 int i = ps.executeUpdate();
                  if (i != 0) {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"taxcustcategories updated");
                  } else {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"taxcustcategories not updated");
                  }
            }
            res.close();	
    	
    	} catch (Exception e) {
            setErrorInLog("Tax category per client error", e);

    	}
    }
    
    /**
     * processes tax
     * @param id
     * @param name
     * @param validfrom
     * @param categoryID
     * @param rate 
     */
    void procesaImpuestos(String id, String name,Date validfrom,String categoryID,double rate ){
        // hace un select y mirar si existe, si esta lo actualiza, si no lo crea
    	try {
            rate = rate/100;
            PreparedStatement ps = con.prepareStatement("select id  from taxes where id=?");
            ps.setString(1, id);
            ResultSet res = ps.executeQuery();
            java.sql.Date validfromSQL = new java.sql.Date(validfrom.getTime());
            
            if (!res.next() ) {      // no existia el usuario y    (insert)
                ps = con.prepareStatement("insert into taxes(id, name, validfrom, category,rate) values(?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, name);
                
                
                ps.setDate(3, validfromSQL);
                
                ps.setString(4, categoryID);
                ps.setDouble(5, rate);
                                
                int i = ps.executeUpdate();
                if (i != 0){
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"tax Inserted");
                } 
                else {
                    logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                  logger.log(Level.SEVERE,"tax not Inserted");
                }

            }  else {                              // si existia el usuario  (update)
                 ps = con.prepareStatement("update taxes set name =?, validfrom=?, category=?, rate=? where id=?");
                 ps.setString(1, name);
                 ps.setDate(2, validfromSQL);
                 ps.setString(3, categoryID);
                 ps.setDouble(4, rate);
                 ps.setString(5,id);

                 int i = ps.executeUpdate();
                  if (i != 0) {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"tax updated");
                  } else {
                      logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
                      logger.log(Level.SEVERE,"tax not updated");
                  }
            }
            res.close();	
    	
    	} catch (Exception e) {
           setErrorInLog("Tax error ", e);
    	}
    }
    
    /**
     *processed product prices per unit
     * @param idProd
     * @param unidadNombre
     * @param precio
     * @param precioStd
     * @param precioLimite
     * @param isDefaultPrice 
     */
    void procesaPrecioUnd (String idProd,String unidadNombre,double precio, double precioStd,double precioLimite,String isDefaultPrice)
    {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	try {
         PreparedStatement ps = con.prepareStatement("update additional_prices_for_products set pricelist = ?, pricestd = ?, pricelimit = ?, is_default=? where product_id = ? and unit_to_id = (select id from units where name = ?)");
         ps.setDouble(1, precio);
         ps.setDouble(2, precioStd);
         ps.setDouble(3, precioLimite);
         if(isDefaultPrice.equalsIgnoreCase("Y"))
            ps.setBoolean(4, true);
         else
            ps.setBoolean(4, false);
         ps.setString(5,idProd);
         
         ps.setString(6, unidadNombre.trim());
         
         
         int i = ps.executeUpdate();
          if (i != 0) {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Precio unidad updated");
          } else {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Precio unidad not updated");
          }
    	
          if(isDefaultPrice.equalsIgnoreCase("Y")){
              procesaPrecioProd(idProd, Double.toString(precio), Double.toString(precioStd), Double.toString(precioLimite));
          }
    	} catch (Exception e) {
            setErrorInLog("Prices per unit error", e);
	}
    	
    }
    
    /**
     * process prices per unit of products bearing the ID
     * @param idProd
     * @param unidadId
     * @param precio
     * @param precioStd
     * @param precioLimite 
     */
    void procesaPrecioUndConId (String idProd,String unidadId,double precio, double precioStd,double precioLimite)
    {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	try {
         PreparedStatement ps = con.prepareStatement("update additional_prices_for_products set pricelist = ?, pricestd = ?, pricelimit = ? where product_id = ? and unit_to_id = ?");
         ps.setDouble(1, precio);
         ps.setDouble(2, precioStd);
         ps.setDouble(3, precioLimite);
         ps.setString(4,idProd);
         ps.setString(5, unidadId);
         
         
         int i = ps.executeUpdate();
          if (i != 0) {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Precio unidad updated");
          } else {
              logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
              logger.log(Level.SEVERE,"Precio unidad not updated");
          }
    	
    	} catch (Exception e) {
            setErrorInLog("Prices per unit error 2", e);
	}
    	
    }
    
    /**
     * processes names of credit cards for payments
     * @param nameCC 
     */
    void procesaCreditCard(String nameCC) {
        // hace un select y mria si existe, si esta lo actualiza, si no lo crea
    	 
    	String cardNames = dlSystem.getResourceAsText("card.names");
        String auxCardNames = cardNames.toUpperCase().trim();
        String nameCCAux = nameCC.toUpperCase().trim();
        
        int index = auxCardNames.indexOf(nameCCAux);
        if(index < 0){
            cardNames = cardNames+ ","+nameCC;
            dlSystem.setResource("card.names", 0, cardNames.getBytes());
        }
        
    }
    
    
    /**
     * Establishes connection to the database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void Conexion() throws SQLException, ClassNotFoundException {
        try{
//            Class.forName(p.getDriverName());
//            url = p.getUrl();
//            usuario = p.getDBUser() ;
//            clave = p.getDBPassword() ;
//            con = DriverManager.getConnection(url,usuario,clave);
//            System.out.println("Conexion exitosa"+url+","+usuario+","+clave);
            /**
             * @Author Carlos Prieto
             * Modificacion de la forma de cargar las clases
             */
            ClassLoader cloader = new URLClassLoader(new URL[] {new File(props.getProperty("db.driverlib")).toURI().toURL()});
            DriverManager.registerDriver(new DriverWrapper((Driver) Class.forName(props.getProperty("db.driver"), true, cloader).newInstance()));
            
            url = props.getProperty("db.URL");
            usuario = props.getProperty("db.user");
            AltEncrypter cypher = new AltEncrypter("cypherkey" + usuario);
            clave = cypher.decrypt(props.getProperty("db.password").substring(6));

            con = DriverManager.getConnection(url,usuario,clave);

        }
        catch(Exception e){
            logger.log(Level.SEVERE,"\n++Error trying to get local DB connection    ++\n\n");
            logger.log(Level.SEVERE, null, e);

        }
    }
      
    public String mensaje(){
        return this.mensaje; 
    }
    
    public void cerrarBD()throws SQLException{
        cerrarConsulta();
        if(con!= null){
            con.close();
            con = null;
        }
    }
    /**
     *  ejecutada cuando el objeto dejar de existir
     */
    protected void finalize() throws java.lang.Throwable {
        cerrarBD();
        super.finalize();
    }
    
    public void cerrarConsulta() throws SQLException{
        if(rs!= null){
            rs.close();
            rs = null;
        }
        if(st != null){
            st.close();
            st = null;
        }
    }

    /**
     * removes the products and if not possible the inactive
     * @param id 
     */
    void procesaBorradoProducto(String id) {
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("select line  from ticketlines where product=?");
            ps.setString(1, id.trim());
            res = ps.executeQuery();

            if (!res.next()) {
                ps = con.prepareStatement("delete from products_cat where product = ?");
                ps.setString(1, id);
                ps.executeUpdate();
                
                ps = con.prepareStatement("delete from stockcurrent where product = ?");
                ps.setString(1, id);
                ps.executeUpdate();
                
                ps = con.prepareStatement("delete from stockdiary where product  = ?");
                ps.setString(1, id);
                ps.executeUpdate();
                
                ps = con.prepareStatement("delete from products where id = ?");
                ps.setString(1, id);
                ps.executeUpdate();
            }else{
                ps = con.prepareStatement("update products set visible = false where id = ?");
                ps.setString(1, id);
                ps.executeUpdate();
                
                ps = con.prepareStatement("delete from products_cat where product = ?");
                ps.setString(1, id);
                ps.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
                logger.log(Level.SEVERE,"\n++Error trying to delete produtc    ++\n\n"+e.getMessage());
        }finally{
            try {
                if(rs != null && !rs.isClosed())
                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * eliminates product categories and if possible the inactive
     * @param id 
     */
    void procesaBorradoCategoriaProducto(String id) {
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("select id from products where category = ? and visible = true");
            ps.setString(1, id.trim());
            res = ps.executeQuery();

            if (!res.next()) {
                ps = con.prepareStatement("delete from products_cat where product in (select id from products where category = ? )");
                ps.setString(1, id);
                ps.executeUpdate();
                
                ps = con.prepareStatement("delete from categories where id = ?");
                ps.setString(1, id);
                ps.executeUpdate();
            }else{
//                ps = con.prepareStatement("delete from products_cat where product in (select id from products where category = ? )");
//                ps.setString(1, id);
//                ps.executeUpdate();
//                ps.close();

                ps = con.prepareStatement("update categories set visible = false where id = ?");
                ps.setString(1, id);
                ps.executeUpdate();
                
                
            }
        }catch(Exception e){
                logger.log(Level.SEVERE,"\n++Error trying to delete productt category    ++\n\n");
        }finally{
            try {
                if(rs != null && !rs.isClosed())
                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * eliminate customers if not possible inactive
     * @param id 
     */
    void procesaBorradoClientes(String id) {
        PreparedStatement ps = null;
        ResultSet res = null;
        try {
            ps = con.prepareStatement("select id from tickets where customer = ?");
            ps.setString(1, id.trim());
            res = ps.executeQuery();

            if (!res.next()) {
                ps = con.prepareStatement("delete from customers where id = ?");
                ps.setString(1, id);
                ps.executeUpdate();
            }else{
                ps = con.prepareStatement("update customers set visible = false where id = ?");
                ps.setString(1, id);
                ps.executeUpdate();
            }
        }catch(Exception e){
                        logger.log(Level.SEVERE,"\n++Error trying to delete customer    ++\n\n"+e.getMessage());
        }finally{
            try {
                if(rs != null && !rs.isClosed())
                    rs.close();
                if(ps != null && !ps.isClosed())
                    ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PosDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}