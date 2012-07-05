//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.pos.forms;

import com.openbravo.pos.ticket.CategoryInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.TaxInfo;
import com.openbravo.pos.ticket.TicketInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.openbravo.data.loader.*;
import com.openbravo.format.Formats;
import com.openbravo.basic.BasicException;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.Row;
import com.openbravo.pos.customers.CustomerInfoExt;
import com.openbravo.pos.inventory.AttributeSetInfo;
import com.openbravo.pos.inventory.TaxCustCategoryInfo;
import com.openbravo.pos.inventory.LocationInfo;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.inventory.TaxCategoryInfo;
import com.openbravo.pos.mant.FloorsInfo;
import com.openbravo.pos.payment.PaymentInfo;
import com.openbravo.pos.payment.PaymentInfoCash;
import com.openbravo.pos.payment.PaymentInfoMagcard;
import com.openbravo.pos.payment.PaymentInfoTicket;
import com.openbravo.pos.ticket.AdditionalPricesForProductsInfo;
import com.openbravo.pos.ticket.FindTicketsInfo;
import com.openbravo.pos.ticket.TicketTaxInfo;
import com.openbravo.pos.ticket.UnitInfo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrianromero
 */
public class DataLogicSales extends BeanFactoryDataSingle {

    protected Session s;

    protected Datas[] auxiliarDatas;
    protected Datas[] stockdiaryDatas;
    // protected Datas[] productcatDatas;
    protected Datas[] paymenttabledatas;
    
    protected Datas[] stockdatas;

    protected Row productsRow;
    protected Row productsRowForList;

    /** Creates a new instance of SentenceContainerGeneric */
    public DataLogicSales() {
        stockdiaryDatas = new Datas[] {Datas.STRING, Datas.TIMESTAMP, Datas.INT, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE};
        paymenttabledatas = new Datas[] {Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.DOUBLE,Datas.STRING};
        stockdatas = new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE, Datas.DOUBLE};
        auxiliarDatas = new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING};

        productsRow = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true),
                new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true),
                new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true),
                new Field("ISCOM", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISSCALE", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field(AppLocal.getIntString("label.prodpricebuy"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodpricesell"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.taxcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.attributeset"), Datas.STRING, Formats.STRING, false, false, true),
                new Field("IMAGE", Datas.IMAGE, Formats.NULL),
                new Field("STOCKCOST", Datas.DOUBLE, Formats.CURRENCY),
                new Field("STOCKVOLUME", Datas.DOUBLE, Formats.DOUBLE),
                new Field("ISCATALOG", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("CATORDER", Datas.INT, Formats.INT),
                new Field("PROPERTIES", Datas.BYTES, Formats.NULL));
        
        productsRowForList = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true),
                new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true),
                new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true),
                new Field("ISCOM", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISSCALE", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field(AppLocal.getIntString("label.prodpricebuy"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodpricesell"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.taxcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.attributeset"), Datas.STRING, Formats.STRING, false, false, true),
                new Field("IMAGE", Datas.IMAGE, Formats.NULL),
                new Field("STOCKCOST", Datas.DOUBLE, Formats.CURRENCY),
                new Field("STOCKVOLUME", Datas.DOUBLE, Formats.DOUBLE),
                new Field("ISCATALOG", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("CATORDER", Datas.INT, Formats.INT),
                new Field("PROPERTIES", Datas.BYTES, Formats.NULL),
                new Field("PRICESELLSPECIAL", Datas.DOUBLE, Formats.CURRENCY,false,true,true),
                new Field("PRICESELLMIN", Datas.DOUBLE, Formats.CURRENCY,false,true,true),
                new Field("UNIT", Datas.STRING, Formats.CURRENCY,false,true,true)
                );
    }

    public void init(Session s){
        this.s = s;
    }

    public final Row getProductsRow() {
        return productsRow;
    }

    /**
     * regresa la informacion de producto por id - returns info product by Id
     * @param id
     * @return ProductInfoExt
     * @throws BasicException 
     */
    public final ProductInfoExt getProductInfo(String id) throws BasicException {
        return (ProductInfoExt) new PreparedSentence(s
            , "SELECT ID, REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, TAXCAT, CATEGORY, ATTRIBUTESET_ID, IMAGE, ATTRIBUTES, PRICESTD, PRICELIMIT, UNIT " +
              "FROM PRODUCTS WHERE ID = ?"
            , SerializerWriteString.INSTANCE
            , ProductInfoExt.getSerializerRead()).find(id);
    }
    public final ProductInfoExt getProductInfoByCode(String sCode) throws BasicException {
        return (ProductInfoExt) new PreparedSentence(s
            , "SELECT P.ID, P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAXCAT, P.CATEGORY, P.ATTRIBUTESET_ID, P.IMAGE, P.ATTRIBUTES, P.PRICESTD, P.PRICELIMIT, P.UNIT " +
              "FROM PRODUCTS AS P, products_cat AS PC, categories AS C WHERE P.CODE = ? AND P.ID = PC.PRODUCT AND P.CATEGORY = C.ID AND C.VISIBLE "
            , SerializerWriteString.INSTANCE
            , ProductInfoExt.getSerializerRead()).find(sCode);
    }
    public final ProductInfoExt getProductInfoByReference(String sReference) throws BasicException {
        return (ProductInfoExt) new PreparedSentence(s
            , "SELECT ID, REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, TAXCAT, CATEGORY, ATTRIBUTESET_ID, IMAGE, ATTRIBUTES, PRICESTD, PRICELIMIT, UNIT " +
              "FROM PRODUCTS WHERE REFERENCE = ?"
            , SerializerWriteString.INSTANCE
            , ProductInfoExt.getSerializerRead()).find(sReference);
    }


    /**
     * regresa el listado de categorias - returns category list
     * @return List<CategoryInfo>
     * @throws BasicException 
     */
    public final List<CategoryInfo> getRootCategories() throws BasicException {
        return new PreparedSentence(s
            , "SELECT ID, NAME, IMAGE FROM CATEGORIES WHERE PARENTID IS NULL AND VISIBLE = TRUE ORDER BY NAME"
            , null
            , CategoryInfo.getSerializerRead()).list();
    }
    
    /**
     * regresa el listado de sub categorias - returns sub category list
     * @param category
     * @return List<CategoryInfo>
     * @throws BasicException 
     */
    public final List<CategoryInfo> getSubcategories(String category) throws BasicException  {
        return new PreparedSentence(s
            , "SELECT ID, NAME, IMAGE FROM CATEGORIES WHERE PARENTID = ? ORDER BY NAME"
            , SerializerWriteString.INSTANCE
            , CategoryInfo.getSerializerRead()).list(category);
    }
    
    /**
     * Regresa el catalogo de productos - returns product catalog
     * @param category
     * @return List<ProductInfoExt>
     * @throws BasicException 
     */
    public List<ProductInfoExt> getProductCatalog(String category) throws BasicException  {
        return new PreparedSentence(s
            , "SELECT P.ID, P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAXCAT, P.CATEGORY, P.ATTRIBUTESET_ID, P.IMAGE, P.ATTRIBUTES, P.PRICESTD, P.PRICELIMIT, P.UNIT " +
              "FROM PRODUCTS P, PRODUCTS_CAT O WHERE P.ID = O.PRODUCT AND P.CATEGORY = ? AND VISIBLE = TRUE " +
              "ORDER BY O.CATORDER, P.NAME"
            , SerializerWriteString.INSTANCE
            , ProductInfoExt.getSerializerRead()).list(category);
    }
    public List<ProductInfoExt> getProductComments(String id) throws BasicException {
        return new PreparedSentence(s
            , "SELECT P.ID, P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAXCAT, P.CATEGORY, P.ATTRIBUTESET_ID, P.IMAGE, P.ATTRIBUTES, P.PRICESTD, P.PRICELIMIT, P.UNIT " +
              "FROM PRODUCTS P, PRODUCTS_CAT O, PRODUCTS_COM M WHERE P.ID = O.PRODUCT AND P.ID = M.PRODUCT2 AND M.PRODUCT = ? " +
              "AND P.ISCOM = " + s.DB.TRUE() + " " +
              "ORDER BY O.CATORDER, P.NAME"
            , SerializerWriteString.INSTANCE
            , ProductInfoExt.getSerializerRead()).list(id);
    }
  
    // Products list
    public final SentenceList getProductList() {
        return new StaticSentence(s
            , new QBFBuilder(
              "SELECT ID, REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, TAXCAT, CATEGORY, ATTRIBUTESET_ID, IMAGE, ATTRIBUTES, PRICESTD, PRICELIMIT, UNIT " +
              "FROM PRODUCTS WHERE ?(QBF_FILTER) ORDER BY REFERENCE", new String[] {"NAME", "PRICEBUY", "PRICESELL", "CATEGORY", "CODE"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING})
            , ProductInfoExt.getSerializerRead());
    }
    
    // Products list
    public SentenceList getProductListNormal() {
        return new StaticSentence(s
            , new QBFBuilder(
              "SELECT ID, REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, TAXCAT, CATEGORY, ATTRIBUTESET_ID, IMAGE, ATTRIBUTES, PRICESTD, PRICELIMIT, UNIT " +
              "FROM PRODUCTS WHERE ISCOM = " + s.DB.FALSE() + " AND ?(QBF_FILTER) ORDER BY REFERENCE", new String[] {"NAME", "PRICEBUY", "PRICESELL", "CATEGORY", "CODE"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING})
            , ProductInfoExt.getSerializerRead());
    }
    
    //Auxiliar list for a filter
    public SentenceList getProductListAuxiliar() {
         return new StaticSentence(s
            , new QBFBuilder(
              "SELECT ID, REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, TAXCAT, CATEGORY, ATTRIBUTESET_ID, IMAGE, ATTRIBUTES, PRICESTD, PRICELIMIT, UNIT " +
              "FROM PRODUCTS WHERE ISCOM = " + s.DB.TRUE() + " AND ?(QBF_FILTER) ORDER BY REFERENCE", new String[] {"NAME", "PRICEBUY", "PRICESELL", "CATEGORY", "CODE"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING})
            , ProductInfoExt.getSerializerRead());
    }
    
    //Tickets and Receipt list
    public SentenceList getTicketsList() {
         return new StaticSentence(s
            , new QBFBuilder(
            "SELECT T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME, SUM(PM.TOTAL) "+ 
            "FROM RECEIPTS R JOIN TICKETS T ON R.ID = T.ID LEFT OUTER JOIN PAYMENTS PM ON R.ID = PM.RECEIPT LEFT OUTER JOIN CUSTOMERS C ON C.ID = T.CUSTOMER LEFT OUTER JOIN PEOPLE P ON T.PERSON = P.ID " +
            "WHERE ?(QBF_FILTER) GROUP BY T.ID, T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME ORDER BY R.DATENEW DESC, T.TICKETID", new String[] {"T.TICKETID", "T.TICKETTYPE", "PM.TOTAL", "R.DATENEW", "R.DATENEW", "P.NAME", "C.NAME"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING})
            , new SerializerReadClass(FindTicketsInfo.class));
    }
    
    //User list
    public final SentenceList getUserList() {
        return new StaticSentence(s
            , "SELECT ID, NAME FROM PEOPLE ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new TaxCategoryInfo(
                        dr.getString(1), 
                        dr.getString(2));
            }});
    }
   
    // Listados para combo
    public final SentenceList getTaxList() {
        return new StaticSentence(s
            , "SELECT ID, NAME, CATEGORY, VALIDFROM, CUSTCATEGORY, PARENTID, RATE, RATECASCADE, RATEORDER FROM TAXES ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new TaxInfo(
                        dr.getString(1), 
                        dr.getString(2),
                        dr.getString(3),
                        dr.getTimestamp(4),
                        dr.getString(5),
                        dr.getString(6),
                        dr.getDouble(7).doubleValue(),
                        dr.getBoolean(8).booleanValue(),
                        dr.getInt(9));
            }});
    }
    public final SentenceList getCategoriesList() {
        return new StaticSentence(s
            , "SELECT ID, NAME, IMAGE FROM CATEGORIES ORDER BY NAME"
            , null
            , CategoryInfo.getSerializerRead());
    }
    public final SentenceList getTaxCustCategoriesList() {
        return new StaticSentence(s
            , "SELECT ID, NAME FROM TAXCUSTCATEGORIES ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new TaxCustCategoryInfo(dr.getString(1), dr.getString(2));
            }});
    }
    public final SentenceList getTaxCategoriesList() {
        return new StaticSentence(s
            , "SELECT ID, NAME FROM TAXCATEGORIES ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new TaxCategoryInfo(dr.getString(1), dr.getString(2));
            }});
    }
    public final SentenceList getTaxCategoriesListWithTaxRateZero() {
        return new StaticSentence(s
            , "SELECT distinct(tc.ID) as ID, tc.NAME as NAME  FROM TAXCATEGORIES as tc, TAXES as t where t.category = tc.id and t.rate =0  ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new TaxCategoryInfo(dr.getString(1), dr.getString(2));
            }});
    }
    public final SentenceList getAttributeSetList() {
        return new StaticSentence(s
            , "SELECT ID, NAME FROM ATTRIBUTESET ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new AttributeSetInfo(dr.getString(1), dr.getString(2));
            }});
    }
    public final SentenceList getLocationsList() {
        return new StaticSentence(s
            , "SELECT ID, NAME, ADDRESS FROM LOCATIONS ORDER BY NAME"
            , null
            , new SerializerReadClass(LocationInfo.class));
    }
    public final SentenceList getFloorsList() {
        return new StaticSentence(s
            , "SELECT ID, NAME FROM FLOORS ORDER BY NAME"
            , null
            , new SerializerReadClass(FloorsInfo.class));
    }

    public CustomerInfoExt findCustomerExt(String card) throws BasicException {
        return (CustomerInfoExt) new PreparedSentence(s
                , "SELECT ID, TAXID, SEARCHKEY, NAME, CARD, TAXCATEGORY, NOTES, MAXDEBT, VISIBLE, CURDATE, CURDEBT" +
                  ", FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX" +
                  ", ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, tax_exempt" +
                  " FROM CUSTOMERS WHERE CARD = ? AND VISIBLE = " + s.DB.TRUE()
                , SerializerWriteString.INSTANCE
                , new CustomerExtRead()).find(card);
    }

    public CustomerInfoExt loadCustomerExt(String id) throws BasicException {
        return (CustomerInfoExt) new PreparedSentence(s
                , "SELECT ID, TAXID, SEARCHKEY, NAME, CARD, TAXCATEGORY, NOTES, MAXDEBT, VISIBLE, CURDATE, CURDEBT" +
                  ", FIRSTNAME, LASTNAME, EMAIL, PHONE, PHONE2, FAX" +
                  ", ADDRESS, ADDRESS2, POSTAL, CITY, REGION, COUNTRY, tax_exempt" +
                " FROM CUSTOMERS WHERE ID = ?"
                , SerializerWriteString.INSTANCE
                , new CustomerExtRead()).find(id);
    }

    public final boolean isCashActive(String id) throws BasicException {

        return new PreparedSentence(s,
                "SELECT MONEY FROM CLOSEDCASH WHERE DATEEND IS NULL AND MONEY = ?",
                SerializerWriteString.INSTANCE,
                SerializerReadString.INSTANCE).find(id)
            != null;
    }

    public final TicketInfo loadTicket(final int tickettype, final int ticketid) throws BasicException {
        TicketInfo ticket = (TicketInfo) new PreparedSentence(s
                , "SELECT T.ID, T.TICKETTYPE, T.TICKETID, R.DATENEW, R.MONEY, R.ATTRIBUTES, P.ID, P.NAME, T.CUSTOMER FROM RECEIPTS R JOIN TICKETS T ON R.ID = T.ID LEFT OUTER JOIN PEOPLE P ON T.PERSON = P.ID WHERE T.TICKETTYPE = ? AND T.TICKETID = ?"
                , SerializerWriteParams.INSTANCE
                , new SerializerReadClass(TicketInfo.class))
                .find(new DataParams() { public void writeValues() throws BasicException {
                    setInt(1, tickettype);
                    setInt(2, ticketid);
                }});
        if (ticket != null) {

            String customerid = ticket.getCustomerId();
            ticket.setCustomer(customerid == null
                    ? null
                    : loadCustomerExt(customerid));

            ticket.setLines(new PreparedSentence(s
                , "SELECT L.TICKET, L.LINE, L.PRODUCT, L.ATTRIBUTESETINSTANCE_ID, L.UNITS, L.PRICE, T.ID, T.NAME, T.CATEGORY, T.VALIDFROM, T.CUSTCATEGORY, T.PARENTID, T.RATE, T.RATECASCADE, T.RATEORDER, L.ATTRIBUTES " +
                  "FROM TICKETLINES L, TAXES T WHERE L.TAXID = T.ID AND L.TICKET = ? ORDER BY L.LINE"
                , SerializerWriteString.INSTANCE
                , new SerializerReadClass(TicketLineInfo.class)).list(ticket.getId()));
            ticket.setPayments(getTicketPayments(ticket.getId()));
        }
        return ticket;
    }
    
    public final List<PaymentInfo> getTicketPayments(String ticketId){
        List<PaymentInfo> paymentInfoList;
        try {
            paymentInfoList = new PreparedSentence(s
                    , "SELECT PAYMENT, TOTAL, TRANSID, NOTES, CHANGE, CARDTYPE FROM PAYMENTS WHERE RECEIPT = ?"
                    , SerializerWriteString.INSTANCE
                    , new SerializerReadClass(PaymentInfoTicket.class)).list(ticketId);
        } catch (BasicException ex) {
            Logger.getLogger(DataLogicSales.class.getName()).log(Level.SEVERE, null, ex);
            paymentInfoList =  new ArrayList<PaymentInfo>();
        }
        return paymentInfoList;
    }

    public final void saveTicket(final TicketInfo ticket, final String location) throws BasicException {

        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {

                // Set Receipt Id
                if (ticket.getTicketId() == 0) {
                    switch (ticket.getTicketType()) {
                        case TicketInfo.RECEIPT_NORMAL:
                            ticket.setTicketId(getNextTicketIndex().intValue());
                            break;
                        case TicketInfo.RECEIPT_REFUND:
                            ticket.setTicketId(getNextTicketRefundIndex().intValue());
                            break;
                        case TicketInfo.RECEIPT_PAYMENT:
                            ticket.setTicketId(getNextTicketPaymentIndex().intValue());
                            break;
                        default:
                            throw new BasicException();
                    }
                }

                // new receipt
                new PreparedSentence(s
                    , "INSERT INTO RECEIPTS (ID, MONEY, DATENEW, ATTRIBUTES) VALUES (?, ?, ?, ?)"
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setString(1, ticket.getId());
                        setString(2, ticket.getActiveCash());
                        setTimestamp(3, ticket.getDate());
                        try {
                            ByteArrayOutputStream o = new ByteArrayOutputStream();
                            ticket.getProperties().storeToXML(o, AppLocal.APP_NAME, "UTF-8");
                            setBytes(4, o.toByteArray());
                        } catch (IOException e) {
                            setBytes(4, null);
                        }
                    }});

                // new ticket
                new PreparedSentence(s
                    , "INSERT INTO TICKETS (ID, TICKETTYPE, TICKETID, PERSON, CUSTOMER) VALUES (?, ?, ?, ?, ?)"
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setString(1, ticket.getId());
                        setInt(2, ticket.getTicketType());
                        setInt(3, ticket.getTicketId());
                        setString(4, ticket.getUser().getId());
                        setString(5, ticket.getCustomerId());
                    }});

                SentenceExec ticketlineinsert = new PreparedSentence(s
                    , "INSERT INTO TICKETLINES (TICKET, LINE, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, TAXID, ATTRIBUTES,UNITSID) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)"
                    , SerializerWriteBuilder.INSTANCE);

                for (TicketLineInfo l : ticket.getLines()) {
                    ticketlineinsert.exec(l);
                    if (l.getProductID() != null)  {
                        // update the stock
                        getStockDiaryInsert().exec(new Object[] {
                            UUID.randomUUID().toString(),
                            ticket.getDate(),
                            l.getMultiply() < 0.0
                                ? MovementReason.IN_REFUND.getKey()
                                : MovementReason.OUT_SALE.getKey(),
                            location,
                            l.getProductID(),
                            l.getProductAttSetInstId(),
                            new Double(-l.getMultiply()),
                            new Double(l.getPrice())
                        });
                    }
                }

                SentenceExec paymentinsert = new PreparedSentence(s
                    , "INSERT INTO PAYMENTS (ID, RECEIPT, PAYMENT, TOTAL, TRANSID, RETURNMSG, NOTES, CARDTYPE, CHANGE) VALUES (?, ?, ?, ?, ?, ?,?, ?,?)"
                    , SerializerWriteParams.INSTANCE);
                for (final PaymentInfo p : ticket.getPayments()) {
                    paymentinsert.exec(new DataParams() { public void writeValues() throws BasicException {
                        setString(1, UUID.randomUUID().toString());
                        setString(2, ticket.getId());
                        setString(3, p.getName());
                        setDouble(4, p.getTotal());
                        setString(5, ticket.getTransactionID());
                        setBytes(6, (byte[]) Formats.BYTEA.parseValue(ticket.getReturnMessage()));
                        setString(7, p.getNotes());
                        if(p instanceof PaymentInfoMagcard )
                            setString(8, ((PaymentInfoMagcard)p).getHolderName());
                        else
                            setString(8, null);
                        
                        if(p instanceof PaymentInfoCash ){
                            setDouble(9, ((PaymentInfoCash)p).getPaid() - ((PaymentInfoCash)p).getTotal());
                        }else
                            setDouble(9, 0.0);
                        
                        
                    }});

                    if ("debt".equals(p.getName()) || "debtpaid".equals(p.getName())) {

                        // udate customer fields...
                        ticket.getCustomer().updateCurDebt(p.getTotal(), ticket.getDate());

                        // save customer fields...
                        getDebtUpdate().exec(new DataParams() { public void writeValues() throws BasicException {
                            setDouble(1, ticket.getCustomer().getCurdebt());
                            setTimestamp(2, ticket.getCustomer().getCurdate());
                            setString(3, ticket.getCustomer().getId());
                        }});
                    }
                }

                SentenceExec taxlinesinsert = new PreparedSentence(s
                        , "INSERT INTO TAXLINES (ID, RECEIPT, TAXID, BASE, AMOUNT)  VALUES (?, ?, ?, ?, ?)"
                        , SerializerWriteParams.INSTANCE);
                if (ticket.getTaxes() != null) {
                    for (final TicketTaxInfo tickettax: ticket.getTaxes()) {
                        taxlinesinsert.exec(new DataParams() { public void writeValues() throws BasicException {
                            setString(1, UUID.randomUUID().toString());
                            setString(2, ticket.getId());
                            setString(3, tickettax.getTaxInfo().getId());
                            setDouble(4, tickettax.getSubTotal());
                            setDouble(5, tickettax.getTax());
                        }});
                    }
                }

                return null;
            }
        };
        t.execute();
    }
    
    public final void deleteTicket(final TicketInfo ticket, final String location) throws BasicException {

        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {

                // update the inventory
                Date d = new Date();
                for (int i = 0; i < ticket.getLinesCount(); i++) {
                    if (ticket.getLine(i).getProductID() != null)  {
                        // Hay que actualizar el stock si el hay producto
                        getStockDiaryInsert().exec( new Object[] {
                            UUID.randomUUID().toString(),
                            d,
                            ticket.getLine(i).getMultiply() >= 0.0
                                ? MovementReason.IN_REFUND.getKey()
                                : MovementReason.OUT_SALE.getKey(),
                            location,
                            ticket.getLine(i).getProductID(),
                            ticket.getLine(i).getProductAttSetInstId(),
                            new Double(ticket.getLine(i).getMultiply()),
                            new Double(ticket.getLine(i).getPrice())
                        });
                    }
                }

                // update customer debts
                for (PaymentInfo p : ticket.getPayments()) {
                    if ("debt".equals(p.getName()) || "debtpaid".equals(p.getName())) {

                        // udate customer fields...
                        ticket.getCustomer().updateCurDebt(-p.getTotal(), ticket.getDate());

                         // save customer fields...
                        getDebtUpdate().exec(new DataParams() { public void writeValues() throws BasicException {
                            setDouble(1, ticket.getCustomer().getCurdebt());
                            setTimestamp(2, ticket.getCustomer().getCurdate());
                            setString(3, ticket.getCustomer().getId());
                        }});
                    }
                }

                // and delete the receipt
                new StaticSentence(s
                    , "DELETE FROM TAXLINES WHERE RECEIPT = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM PAYMENTS WHERE RECEIPT = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM TICKETLINES WHERE TICKET = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM TICKETS WHERE ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM RECEIPTS WHERE ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                return null;
            }
        };
        t.execute();
    }
    
    public final void voidTicket(final TicketInfo ticket, final String location) throws BasicException {

        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {

                // update the inventory
                Date d = new Date();
                for (int i = 0; i < ticket.getLinesCount(); i++) {
                    if (ticket.getLine(i).getProductID() != null)  {
                        // Hay que actualizar el stock si el hay producto
                        getStockDiaryInsert().exec( new Object[] {
                            UUID.randomUUID().toString(),
                            d,
                            ticket.getLine(i).getMultiply() >= 0.0
                                ? MovementReason.IN_REFUND.getKey()
                                : MovementReason.OUT_SALE.getKey(),
                            location,
                            ticket.getLine(i).getProductID(),
                            ticket.getLine(i).getProductAttSetInstId(),
                            new Double(ticket.getLine(i).getMultiply()),
                            new Double(ticket.getLine(i).getPrice())
                        });
                    }
                }

                // update customer debts
                for (PaymentInfo p : ticket.getPayments()) {
                    if ("debt".equals(p.getName()) || "debtpaid".equals(p.getName())) {

                        // udate customer fields...
                        ticket.getCustomer().updateCurDebt(-p.getTotal(), ticket.getDate());

                         // save customer fields...
                        getDebtUpdate().exec(new DataParams() { public void writeValues() throws BasicException {
                            setDouble(1, ticket.getCustomer().getCurdebt());
                            setTimestamp(2, ticket.getCustomer().getCurdate());
                            setString(3, ticket.getCustomer().getId());
                        }});
                    }
                }

                // and delete the receipt
                new StaticSentence(s
                    , "DELETE FROM TAXLINES WHERE RECEIPT = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM PAYMENTS WHERE RECEIPT = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM TICKETLINES WHERE TICKET = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "UPDATE TICKETS SET status = -99 WHERE ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
//                new StaticSentence(s
//                    , "DELETE FROM RECEIPTS WHERE ID = ?"
//                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                return null;
            }
        };
        t.execute();
    }

    public final Integer getNextTicketIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM").find();
    }

    public final Integer getNextTicketRefundIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM_REFUND").find();
    }

    public final Integer getNextTicketPaymentIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM_PAYMENT").find();
    }

    public final SentenceList getProductCatQBF() {
        return new StaticSentence(s
            , new QBFBuilder(
                "SELECT P.ID, P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.CATEGORY, P.TAXCAT, P.ATTRIBUTESET_ID, P.IMAGE, P.STOCKCOST, P.STOCKVOLUME, CASE WHEN C.PRODUCT IS NULL THEN " + s.DB.FALSE() + " ELSE " + s.DB.TRUE() + " END, C.CATORDER, P.ATTRIBUTES, P.PRICESTD, P.PRICELIMIT, P.UNIT " +
                "FROM PRODUCTS P LEFT OUTER JOIN PRODUCTS_CAT C ON P.ID = C.PRODUCT " +
                "WHERE ?(QBF_FILTER) " +
                "ORDER BY P.REFERENCE", new String[] {"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING})
            , productsRowForList.getSerializerRead());
    }

    public final SentenceExec getProductCatInsert() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                int i = new PreparedSentence(s
                    , "INSERT INTO PRODUCTS (ID, REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, ATTRIBUTESET_ID, IMAGE, STOCKCOST, STOCKVOLUME, ATTRIBUTES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16})).exec(params);
                if (i > 0 && ((Boolean)values[14]).booleanValue()) {
                    return new PreparedSentence(s
                        , "INSERT INTO PRODUCTS_CAT (PRODUCT, CATORDER) VALUES (?, ?)"
                        , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0, 15})).exec(params);
                } else {
                    return i;
                }
            }
        };
    }
    
    public final SentenceExec getProductCatUpdate() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                int i = new PreparedSentence(s
                    , "UPDATE PRODUCTS SET ID = ?, REFERENCE = ?, CODE = ?, NAME = ?, ISCOM = ?, ISSCALE = ?, PRICEBUY = ?, PRICESELL = ?, CATEGORY = ?, TAXCAT = ?, ATTRIBUTESET_ID = ?, IMAGE = ?, STOCKCOST = ?, STOCKVOLUME = ?, ATTRIBUTES = ? WHERE ID = ?"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 0})).exec(params);
                if (i > 0) {
                    if (((Boolean)values[14]).booleanValue()) {
                        if (new PreparedSentence(s
                                , "UPDATE PRODUCTS_CAT SET CATORDER = ? WHERE PRODUCT = ?"
                                , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {15, 0})).exec(params) == 0) {
                            new PreparedSentence(s
                                , "INSERT INTO PRODUCTS_CAT (PRODUCT, CATORDER) VALUES (?, ?)"
                                , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0, 15})).exec(params);
                        }
                    } else {
                        new PreparedSentence(s
                            , "DELETE FROM PRODUCTS_CAT WHERE PRODUCT = ?"
                            , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0})).exec(params);
                    }
                }
                return i;
            }
        };
    }

    public final SentenceExec getProductCatDelete() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s
                    , "DELETE FROM PRODUCTS_CAT WHERE PRODUCT = ?"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0})).exec(params);
                return new PreparedSentence(s
                    , "DELETE FROM PRODUCTS WHERE ID = ?"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0})).exec(params);
            }
        };
    }

    public final SentenceExec getDebtUpdate() {

        return new PreparedSentence(s
                , "UPDATE CUSTOMERS SET CURDEBT = ?, CURDATE = ? WHERE ID = ?"
                , SerializerWriteParams.INSTANCE);
    }

    public final SentenceExec getStockDiaryInsert() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                int updateresult = ((Object[]) params)[5] == null // si ATTRIBUTESETINSTANCE_ID is null
                    ? new PreparedSentence(s
                        , "UPDATE STOCKCURRENT SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4})).exec(params)
                    : new PreparedSentence(s
                        , "UPDATE STOCKCURRENT SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4, 5})).exec(params);

                if (updateresult == 0) {
                    new PreparedSentence(s
                        , "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, ?)"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(s
                    , "INSERT INTO STOCKDIARY (ID, DATENEW, REASON, LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                    , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {0, 1, 2, 3, 4, 5, 6, 7})).exec(params);
            }
        };
    }

    public final SentenceExec getStockDiaryDelete() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                int updateresult = ((Object[]) params)[5] == null // if ATTRIBUTESETINSTANCE_ID is null
                        ? new PreparedSentence(s
                            , "UPDATE STOCKCURRENT SET UNITS = (UNITS - ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL"
                            , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4})).exec(params)
                        : new PreparedSentence(s
                            , "UPDATE STOCKCURRENT SET UNITS = (UNITS - ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?"
                            , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4, 5})).exec(params);

                if (updateresult == 0) {
                    new PreparedSentence(s
                        , "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, -(?))"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(s
                    , "DELETE FROM STOCKDIARY WHERE ID = ?"
                    , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {0})).exec(params);
            }
        };
    }

    public final SentenceExec getPaymentMovementInsert() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s
                    , "INSERT INTO RECEIPTS (ID, MONEY, DATENEW) VALUES (?, ?, ?)"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0, 1, 2})).exec(params);
                
            //SMJ    
                return new PreparedSentence(s
                    , "INSERT INTO PAYMENTS (ID, RECEIPT, PAYMENT, TOTAL, NOTES) VALUES (?, ?, ?, ?,?)"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {3, 0, 4, 5,6})).exec(params);
            }
        };
    }

    public final SentenceExec getPaymentMovementDelete() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s
                    , "DELETE FROM PAYMENTS WHERE ID = ?"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {3})).exec(params);
                return new PreparedSentence(s
                    , "DELETE FROM RECEIPTS WHERE ID = ?"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0})).exec(params);
            }
        };
    }

    public final double findProductStock(String warehouse, String id, String attsetinstid) throws BasicException {

        PreparedSentence p = attsetinstid == null
                ? new PreparedSentence(s, "SELECT UNITS FROM STOCKCURRENT WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL"
                    , new SerializerWriteBasic(Datas.STRING, Datas.STRING)
                    , SerializerReadDouble.INSTANCE)
                : new PreparedSentence(s, "SELECT UNITS FROM STOCKCURRENT WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?"
                    , new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING)
                    , SerializerReadDouble.INSTANCE);

        Double d = (Double) p.find(warehouse, id, attsetinstid);
        return d == null ? 0.0 : d.doubleValue();
    }

    public final SentenceExec getCatalogCategoryAdd() {
        return new StaticSentence(s
                , "INSERT INTO PRODUCTS_CAT(PRODUCT, CATORDER) SELECT ID, " + s.DB.INTEGER_NULL() + " FROM PRODUCTS WHERE CATEGORY = ?"
                , SerializerWriteString.INSTANCE);
    }
    public final SentenceExec getCatalogCategoryDel() {
        return new StaticSentence(s
                , "DELETE FROM PRODUCTS_CAT WHERE PRODUCT = ANY (SELECT ID FROM PRODUCTS WHERE CATEGORY = ?)"
                , SerializerWriteString.INSTANCE);
    }

    public final TableDefinition getTableCategories() {
        return new TableDefinition(s,
            "CATEGORIES"
            , new String[] {"ID", "NAME", "PARENTID", "IMAGE","VISIBLE"}
            , new String[] {"ID", AppLocal.getIntString("Label.Name"), "", AppLocal.getIntString("label.image"),"VISIBLE"}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.IMAGE,Datas.BOOLEAN}
            , new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING, Formats.NULL,Formats.BOOLEAN}
            , new int[] {0}
        );
    }
    public final TableDefinition getTableTaxes() {
        return new TableDefinition(s,
            "TAXES"
            , new String[] {"ID", "NAME", "CATEGORY", "VALIDFROM", "CUSTCATEGORY", "PARENTID", "RATE", "RATECASCADE", "RATEORDER"}
            , new String[] {"ID", AppLocal.getIntString("Label.Name"), AppLocal.getIntString("label.taxcategory"), AppLocal.getIntString("Label.ValidFrom"), AppLocal.getIntString("label.custtaxcategory"), AppLocal.getIntString("label.taxparent"), AppLocal.getIntString("label.dutyrate"), AppLocal.getIntString("label.cascade"), AppLocal.getIntString("label.order")}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.BOOLEAN, Datas.INT}
            , new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING, Formats.TIMESTAMP, Formats.STRING, Formats.STRING, Formats.PERCENT, Formats.BOOLEAN, Formats.INT}
            , new int[] {0}
        );
    }

    public final TableDefinition getTableTaxCustCategories() {
        return new TableDefinition(s,
            "TAXCUSTCATEGORIES"
            , new String[] {"ID", "NAME"}
            , new String[] {"ID", AppLocal.getIntString("Label.Name")}
            , new Datas[] {Datas.STRING, Datas.STRING}
            , new Formats[] {Formats.STRING, Formats.STRING}
            , new int[] {0}
        );
    }
    public final TableDefinition getTableTaxCategories() {
        return new TableDefinition(s,
            "TAXCATEGORIES"
            , new String[] {"ID", "NAME"}
            , new String[] {"ID", AppLocal.getIntString("Label.Name")}
            , new Datas[] {Datas.STRING, Datas.STRING}
            , new Formats[] {Formats.STRING, Formats.STRING}
            , new int[] {0}
        );
    }

    public final TableDefinition getTableLocations() {
        return new TableDefinition(s,
            "LOCATIONS"
            , new String[] {"ID", "NAME", "ADDRESS"}
            , new String[] {"ID", AppLocal.getIntString("label.locationname"), AppLocal.getIntString("label.locationaddress")}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING}
            , new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING}
            , new int[] {0}
        );
    }

    protected static class CustomerExtRead implements SerializerRead {
        public Object readValues(DataRead dr) throws BasicException {
            CustomerInfoExt c = new CustomerInfoExt(dr.getString(1));
            c.setTaxid(dr.getString(2));
            c.setSearchkey(dr.getString(3));
            c.setName(dr.getString(4));
            c.setCard(dr.getString(5));
            c.setTaxCustomerID(dr.getString(6));
            c.setNotes(dr.getString(7));
            c.setMaxdebt(dr.getDouble(8));
            c.setVisible(dr.getBoolean(9).booleanValue());
            c.setCurdate(dr.getTimestamp(10));
            c.setCurdebt(dr.getDouble(11));
            c.setFirstname(dr.getString(12));
            c.setLastname(dr.getString(13));
            c.setEmail(dr.getString(14));
            c.setPhone(dr.getString(15));
            c.setPhone2(dr.getString(16));
            c.setFax(dr.getString(17));
            c.setAddress(dr.getString(18));
            c.setAddress2(dr.getString(19));
            c.setPostal(dr.getString(20));
            c.setCity(dr.getString(21));
            c.setRegion(dr.getString(22));
            c.setCountry(dr.getString(23));
            c.setTaxExempt(dr.getBoolean(24).booleanValue());

            return c;
        }
    }
    
    
    
    /**************************Modificaciones********************************/
    /**
     * Guarda motivo de eliminar item - save reason to eliminate item
     * @param productId
     * @param reasonToDeleteItem
     * @param afectsStock
     * @param ticketId
     * @param peopleId
     * @throws BasicException 
     */
    public final void saveReasonToDeleteItem(final String productId, final String reasonToDeleteItem, final boolean afectsStock, final String ticketId, final String peopleId) throws BasicException {
        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {
                // new ticket
                new PreparedSentence(s
                        , "INSERT INTO TICKET_AUDIT (PRODUCT_ID, REASON, AFECTS_STOCK,PEOPLE_ID, TICKET_ID,  REASON_TIMESTAMP) VALUES (?, ?, ?,?,?,NOW())"
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setString(1, productId);
                        setString(2, reasonToDeleteItem);
                        setBoolean(3, afectsStock); 
                        setString(4,peopleId);
                        setString(5, ticketId);
                    }});
                
                return null;
            }
        };
        t.execute();
    }
    
    /**
     * regresa la lista de precios para el id producto - return price lis by product id
     * @param productID
     * @return List<AdditionalPricesForProductsInfo>
     */
    public final List<AdditionalPricesForProductsInfo> getAdditionalPricesForProductsInfos(String productID)   {
        try{
            return new PreparedSentence(s
                , "SELECT additional_prices_for_products.id AS ID, additional_prices_for_products.unit_id, additional_prices_for_products.unit_to_id, additional_prices_for_products.divide_rate, additional_prices_for_products.multiply_rate, additional_prices_for_products.product_id,  additional_prices_for_products.priceList, additional_prices_for_products.priceSTD, additional_prices_for_products.priceLimit "+
                  "FROM additional_prices_for_products "+
                  "WHERE product_id = ?  ORDER BY ID "
                , SerializerWriteString.INSTANCE
                , AdditionalPricesForProductsInfo.getSerializerRead()).list(productID);
        }catch(BasicException be){
            return new ArrayList<AdditionalPricesForProductsInfo>() {};
        }
    }
    
    /**
     * regresa la informacion de las unidades para el producto por id
     * returns info unit by product id
     * @param id
     * @return UnitInfo
     * @throws BasicException 
     */
    public final UnitInfo getUnitInfo(String id) throws BasicException {
        return (UnitInfo) new PreparedSentence(s
            , "SELECT   id,   code,   name,   costing_precision,   type,   std_precision  " +
              "FROM   units WHERE id = ?"
            , SerializerWriteString.INSTANCE
            , UnitInfo.getSerializerRead()).find(id);
    }
    
    /**
     * actualiza las devoluciones de una linea de ticket - update refound for ticket line
     * @param ticketLineOld
     * @param ticketIdOld
     * @param multiply
     * @throws BasicException 
     */
    public final void updateTicketLineRefund(final Integer ticketLineOld, final String ticketIdOld, final Double multiply) throws BasicException {
        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {
                // new ticket
                new PreparedSentence(s
                        , "UPDATE ticketlines SET refund = refund + ? WHERE ticket = ? AND line = ? "
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setDouble(1, multiply);
                        setString(2, ticketIdOld);
                        setInt(3, ticketLineOld); 
                    }});
                
                return null;
            }
        };
        t.execute();
    }
    
    /**
     * actualiza la deuda actual de un cliente - update current debt by customer 
     * @param customerId
     * @param price
     * @throws BasicException 
     */
    public final void updateCustmerCurDeb(final String customerId,  final Double price) throws BasicException {
        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {
                // new ticket
                new PreparedSentence(s
                        , "UPDATE customers SET curdebt = curdebt - ? WHERE id = ?"
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setDouble(1, price);
                        setString(2, customerId);
                    }});
                
                return null;
            }
        };
        t.execute();
    }
    
    /**
     * actuliza las devoluciones para un cliente - update refound by customr
     * @param customerId
     * @param price
     * @throws BasicException 
     */
    public final void updateDebtRefoundForReceipt(final String customerId,  final Double price) throws BasicException {
        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {
                // new ticket
                new PreparedSentence(s
                        , "UPDATE customers SET curdebt = curdebt - ? WHERE id = ?"
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setDouble(1, price);
                        setString(2, customerId);
                    }});
                
                return null;
            }
        };
        t.execute();
    }
    
    /**
     * regresa los attributos del ticket - returns ticket attribute
     * @param ticketId
     * @return Properties
     */
    public final Properties getReceiptAttributes(String ticketId){
        Properties p = new Properties();
        try {
            byte[] img  = (byte[])new PreparedSentence(s
                , "SELECT attributes  " +
                  "  FROM receipts where id=? "
                , SerializerWriteString.INSTANCE
                , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                    return dr.getBytes(1);
                }}).find(ticketId);
            
            if (img != null) {
                p.loadFromXML(new ByteArrayInputStream(img));
            }
            
        } catch (Exception ex) {
            Logger.getLogger(DataLogicSales.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    /**
     * actualiza los atributos de un ticket - update ticket attribute
     * @param ticketId
     * @param p 
     */
    public final void updateReceiptAttributes(final String ticketId, Properties p){
        try {
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            byte[] r = null;
            try {
                p.storeToXML(o, AppLocal.APP_NAME, "UTF-8");
                r = o.toByteArray();
            } catch (IOException e) {
                r = null;
            }
            
            final byte[] r2 =r; 
            
          Transaction t = new Transaction(s) {
                public Object transact() throws BasicException {
                    // new ticket
                    new PreparedSentence(s
                            , "UPDATE receipts SET attributes = ? WHERE id = ? "
                        , SerializerWriteParams.INSTANCE
                        ).exec(new DataParams() { public void writeValues() throws BasicException {
                            setBytes(1, r2); 
                            setString(2, ticketId);
                        }});
                    
                    return null;
                }
            };
            t.execute();
        } catch (BasicException ex) {
            ex.printStackTrace();
            Logger.getLogger(DataLogicSales.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * regresa deuda actual de una devolucion - returns current debt by properties
     * @param p
     * @return double
     */
    public double getDebtRefoundForReceipt(Properties p){
        double debtRefoundForReceipt =0;
        if(p.getProperty("debtRefoundForReceipt") != null && !p.getProperty("debtRefoundForReceipt").equals("debtRefoundForReceipt")){
             debtRefoundForReceipt = Double.valueOf(p.getProperty("debtRefoundForReceipt"));
        }
        return debtRefoundForReceipt;
    }
    
    /**
     * regresa la informacion de la linea de la devolucion
     * returns line info for refound
     * @param ticketLineOld
     * @param ticketIdOld
     * @return Double
     * @throws BasicException 
     */
    public final Double getTicketLineRefund(final Integer ticketLineOld, final String ticketIdOld) throws BasicException {
        return (Double)new PreparedSentence(s
            , "SELECT refund  " +
              "  FROM ticketlines where ticket = ? and line = ? "
            , new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.INT})
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return dr.getDouble(1);
            }}).find(ticketIdOld,ticketLineOld);  
    }
    
    /**
     * regresa la unidad por defecto para el producto - retuns default unit by product id
     * @param productId
     * @return String
     * @throws BasicException 
     */
    public final String getDefaultUnitProductID(final String productId) throws BasicException {
        return (String) new PreparedSentence(s
            , "SELECT unit_to_id  " +
              "  FROM additional_prices_for_products where product_id =? and is_default = true"
            , SerializerWriteString.INSTANCE
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return dr.getString(1);
            }}).find(productId);
    }
}
