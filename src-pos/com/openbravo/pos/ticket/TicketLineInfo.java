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

package com.openbravo.pos.ticket;

import java.io.*;
import com.openbravo.pos.util.StringUtils;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializableRead;
import com.openbravo.data.loader.DataWrite;
import com.openbravo.format.Formats;
import com.openbravo.data.loader.SerializableWrite;
import com.openbravo.basic.BasicException;
import com.openbravo.pos.forms.AppLocal;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrianromero
 */
public class TicketLineInfo implements SerializableWrite, SerializableRead, Serializable {

    private static final long serialVersionUID = 6608012948284450199L;
    private String m_sTicket;
    private int m_iLine;
    private String m_sTicketOld = "";
    private int m_iLineOld =0;
    private double multiply;
    private double price;
    private TaxInfo tax;
    private Properties attributes;
    private String productid;
    private String attsetinstid;

    /** Creates new TicketLineInfo */
    public TicketLineInfo(String productid, double dMultiply, double dPrice, TaxInfo tax, Properties props) {
System.out.println("tax:"+tax);
        init(productid, null, dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo(String productid, double dMultiply, double dPrice, TaxInfo tax) {
        init(productid, null, dMultiply, dPrice, tax, new Properties());
    }

    public TicketLineInfo(String productid, String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax) {
        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        init(productid, null, dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo(String productname, String producttaxcategory, double dMultiply, double dPrice, TaxInfo tax) {

        Properties props = new Properties();
        props.setProperty("product.name", productname);
        props.setProperty("product.taxcategoryid", producttaxcategory);
        init(null, null, dMultiply, dPrice, tax, props);
    }

    public TicketLineInfo() {
        init(null, null, 0.0, 0.0, null, new Properties());
    }

    public TicketLineInfo(ProductInfoExt product, double dMultiply, double dPrice, TaxInfo tax, Properties attributes) {

        String pid;

        if (product == null) {
            pid = null;
        } else {
            pid = product.getID();
            attributes.setProperty("product.name", product.getName());
            attributes.setProperty("product.com", product.isCom() ? "true" : "false");
            attributes.setProperty("product.unit", product.getUnit());
            if (product.getAttributeSetID() != null) {
                attributes.setProperty("product.attsetid", product.getAttributeSetID());
            }
            attributes.setProperty("product.taxcategoryid", product.getTaxCategoryID());
            if (product.getCategoryID() != null) {
                attributes.setProperty("product.categoryid", product.getCategoryID());
            }
        }
        init(pid, null, dMultiply, dPrice, tax, attributes);
    }

    public TicketLineInfo(ProductInfoExt oProduct, double dPrice, TaxInfo tax, Properties attributes) {
        this(oProduct, 1.0, dPrice, tax, attributes);
    }

    public TicketLineInfo(TicketLineInfo line) {
        init(line.productid, line.attsetinstid, line.multiply, line.price, line.tax, (Properties) line.attributes.clone());
        if(line.m_sTicketOld.equalsIgnoreCase("")){
            m_sTicketOld =  line.m_sTicket;
            m_iLineOld = line.m_iLine;
        }else{
            m_sTicketOld =  line.m_sTicketOld;
            m_iLineOld = line.m_iLineOld;
        }
        
    }

    private void init(String productid, String attsetinstid, double dMultiply, double dPrice, TaxInfo tax, Properties attributes) {

        this.productid = productid;
        this.attsetinstid = attsetinstid;
        multiply = dMultiply;
        price = dPrice;
        this.tax = tax;
        this.attributes = attributes;

        m_sTicket = null;
        m_iLine = -1;
    }

    void setTicket(String ticket, int line) {
        m_sTicket = ticket;
        m_iLine = line;
    }

    public void writeValues(DataWrite dp) throws BasicException {
        dp.setString(1, m_sTicket);
        dp.setInt(2, new Integer(m_iLine));
        dp.setString(3, productid);
        dp.setString(4, attsetinstid);

        dp.setDouble(5, new Double(multiply));
        dp.setDouble(6, new Double(price));

        dp.setString(7, tax.getId());
        try {
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            attributes.storeToXML(o, AppLocal.APP_NAME, "UTF-8");
            dp.setBytes(8, o.toByteArray());
            if(attributes.getProperty("unitid") != null)
                dp.setString(9, attributes.getProperty("unitid"));
            else
                dp.setString(9, null);
            
        } catch (IOException e) {
            dp.setBytes(8, null);
            dp.setString(9, null);
        }
    }

    public void readValues(DataRead dr) throws BasicException {
        m_sTicket = dr.getString(1);
        m_iLine = dr.getInt(2).intValue();
        productid = dr.getString(3);
        attsetinstid = dr.getString(4);

        multiply = dr.getDouble(5);
        price = dr.getDouble(6);

        tax = new TaxInfo(dr.getString(7), dr.getString(8), dr.getString(9), dr.getTimestamp(10), dr.getString(11), dr.getString(12), dr.getDouble(13), dr.getBoolean(14), dr.getInt(15));
        attributes = new Properties();
        try {
            byte[] img = dr.getBytes(16);
            if (img != null) {
                attributes.loadFromXML(new ByteArrayInputStream(img));
            }
        } catch (IOException e) {
        }
    }

    public TicketLineInfo copyTicketLine() {
        TicketLineInfo l = new TicketLineInfo();
        // l.m_sTicket = null;
        // l.m_iLine = -1;
        l.productid = productid;
        l.attsetinstid = attsetinstid;
        l.multiply = multiply;
        l.price = price;
        l.tax = tax;
        l.attributes = (Properties) attributes.clone();
        return l;
    }

    public int getTicketLine() {
        return m_iLine;
    }

    public String getProductID() {
        return productid;
    }

    public String getProductName() {
        return attributes.getProperty("product.name");
    }

    public String getProductAttSetId() {
        return attributes.getProperty("product.attsetid");
    }

    public String getProductAttSetInstDesc() {
        return attributes.getProperty("product.attsetdesc", "");
    }

    public void setProductAttSetInstDesc(String value) {
        if (value == null) {
            attributes.remove(value);
        } else {
            attributes.setProperty("product.attsetdesc", value);
        }
    }

    public String getProductAttSetInstId() {
        return attsetinstid;
    }

    public void setProductAttSetInstId(String value) {
        attsetinstid = value;
    }

    public boolean isProductCom() {
        return "true".equals(attributes.getProperty("product.com"));
    }

    public String getProductTaxCategoryID() {
        return (attributes.getProperty("product.taxcategoryid"));
    }

    public String getProductCategoryID() {
        return (attributes.getProperty("product.categoryid"));
    }

    public double getMultiply() {
        return multiply;
    }
    
    public double getMultiplyNegative() {
        return multiply * -1;
    }
    
    public BigDecimal getValueNegative(){
        return new BigDecimal(getValueWithDiscount() * -1);
    }
    
    
    public double getValueWithDiscount(){
        double discount = getDiscount();
        return ((price * multiply) - (price * multiply * discount)) * (1.0 + getTaxRate());
    }

    public void setMultiply(double dValue) {
        multiply = dValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double dValue) {
        price = dValue;
    }

    public double getPriceTax() {
        return price * (1.0 + getTaxRate());
    }

    public void setPriceTax(double dValue) {
        price = dValue / (1.0 + getTaxRate());
    }

    public TaxInfo getTaxInfo() {
        return tax;
    }

    public void setTaxInfo(TaxInfo value) {
        tax = value;
    }

    public String getProperty(String key) {
        return attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return attributes.getProperty(key, defaultvalue);
    }

    public void setProperty(String key, String value) {
        attributes.setProperty(key, value);
    }
    
    public Properties getProperties() {
        return attributes;
    }

    public double getTaxRate() {
        return tax == null ? 0.0 : tax.getRate();
    }

    /**
     * Modificacion para mejorar el redondeo de decimales
     * Change to improve decimal rounding
     * @return 
     */
    public double getSubValue() {
        BigDecimal priceDecimal = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        BigDecimal multiplyDecimal = new BigDecimal(multiply).setScale(2, RoundingMode.HALF_UP);
        
        return multiplyDecimal.multiply(priceDecimal).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    
    public double getSubValueWithDiscount(){
        double discount = getDiscount();
        return ((price * multiply) - (price * multiply * discount));
    }

    public double getTax() {
        BigDecimal discount = new BigDecimal(getDiscount());
        BigDecimal priceBD = new BigDecimal(price);
        BigDecimal multiplyBD = new BigDecimal(multiply);
        BigDecimal taxRateBD = new BigDecimal(getTaxRate());
        
        //discount = discount.setScale(2,RoundingMode.HALF_UP);
        priceBD = priceBD.setScale(2,RoundingMode.HALF_UP);
        multiplyBD = multiplyBD.setScale(2,RoundingMode.HALF_UP);
        taxRateBD = taxRateBD.setScale(2,RoundingMode.HALF_UP);
        
        BigDecimal aux1  =  priceBD.multiply(multiplyBD);
        BigDecimal aux2 =  priceBD.multiply(multiplyBD).multiply(discount);
        BigDecimal valueToReturnBD = aux1.subtract(aux2).multiply(taxRateBD).setScale(2,RoundingMode.HALF_UP);

//        double valueToReturn =((price * multiply) - (price * multiply * discount)) * getTaxRate();
//        BigDecimal valueToReturnBD = new BigDecimal(valueToReturn);
//        valueToReturnBD = valueToReturnBD.setScale(2, RoundingMode.HALF_EVEN);
//System.out.println("valueToReturn:" + valueToReturn + " valueToReturnBD:"+valueToReturnBD);
        return valueToReturnBD.doubleValue();
    }
    
    public double getDiscount(){
        try {
//            NumberFormat nf = NumberFormat.getInstance();
            if(getProperty("discount-value") != null && !getProperty("discount-value").equals("")){
//                Number number = nf.parse(getProperty("discount-rate"));
//                return number.doubleValue();
                return Double.parseDouble(getProperty("discount-rate"));
            }else{
                return 0;
            }
        } catch (Exception ex) {
            Logger.getLogger(TicketLineInfo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        
    }

    public double getValue() {
        return price * multiply * (1.0 + getTaxRate());
    }

    public String printName() {
        return StringUtils.encodeXML(attributes.getProperty("product.name"));
    }

    public String printMultiply() {
        return Formats.DOUBLE.formatValue(multiply);
    }

    public String printPrice() {
        return Formats.CURRENCY.formatValue(getPrice());
    }

    public String printPriceTax() {
        return Formats.CURRENCY.formatValue(getPriceTax());
    }

    public String printTax() {
        return Formats.CURRENCY.formatValue(getTax());
    }

    public String printTaxRate() {
        return Formats.PERCENT.formatValue(getTaxRate());
    }

    public String printSubValue() {
        return Formats.CURRENCY.formatValue(getSubValue());
    }

    public String printValue() {
        return Formats.CURRENCY.formatValue(getValue());
    }
    
    public int getTicketLineOld() {
        return m_iLineOld;
    }
    
    public String getTicketIdOld() {
        return m_sTicketOld;
    }
    
    public void setTicketLineOld(int iLineOld) {
        m_iLineOld = iLineOld;
    }
    
    public void setTicketIdOld(String ticketOld) {
        m_sTicketOld = ticketOld;
    }
    
    public BigDecimal getPriceBigDecimal(){
        BigDecimal priceBD = new BigDecimal(this.getPrice());

        if(getProperty("discount-rate") != null && !getProperty("discount-rate").equals("")){
            BigDecimal discountRateBD = new BigDecimal(getProperty("discount-rate"));
            BigDecimal priceDiscountBD = priceBD.multiply(discountRateBD);
            priceBD = priceBD.subtract(priceDiscountBD);
        }
        priceBD = priceBD.setScale(2,RoundingMode.HALF_UP);
        return priceBD;   
    }
    
    public BigDecimal getPriceTaxBigDecimal(){
//        BigDecimal priceBD = new BigDecimal(this.getPriceTax());
//        priceBD = priceBD.round(new MathContext(2));
//        return priceBD;   
        
        
        BigDecimal priceBD = new BigDecimal(this.getPrice());
//        return price * (1.0 + getTaxRate());
        BigDecimal taxRate = new BigDecimal(this.getTaxRate() + 1);
        if(getProperty("discount-rate") != null && !getProperty("discount-rate").equals("")){
//            BigDecimal priceDiscountBD = new BigDecimal(getProperty("discount-value"));
//            priceBD = priceBD.add(priceDiscountBD);
            
            BigDecimal discountRateBD = new BigDecimal(getProperty("discount-rate"));
            BigDecimal priceDiscountBD = priceBD.multiply(discountRateBD);
            priceBD = priceBD.subtract(priceDiscountBD);
        }
        taxRate.setScale(2,RoundingMode.HALF_UP);
        priceBD = priceBD.multiply(taxRate);
        priceBD = priceBD.setScale(2,RoundingMode.HALF_UP);
        return priceBD;   
    }
    
}
