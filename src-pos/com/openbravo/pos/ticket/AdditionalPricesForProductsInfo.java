/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;

/**
 * clase para manejo de precios adicionales de productos por unidad
 * class for additional price per unit product
 * @author Carlos Prieto - SmartJSP S.A.S.
 */
public class AdditionalPricesForProductsInfo {
    private String id = "";
    private String unitId ="";
    private String unitToId = "";
    private double divideRate = 0;
    private double multiplyRate =0;
    private String productId = "";
    private double priceList =0;
    private double priceSTD =0;
    private double priceLimit =0;

    public AdditionalPricesForProductsInfo(String id, String unitId, String unitToId, double divideRate,double multiplyRate, String productId, double priceList, double priceSTD, double priceLimit ) {
        this.id = id;
        this.unitId = unitId;
        this.unitToId = unitToId;
        this.divideRate  = divideRate;
        this.multiplyRate = multiplyRate;
        this.productId = productId;
        this.priceLimit =priceLimit;
        this.priceSTD =priceSTD;
        this.priceList =priceList;
    }

    public double getDivideRate() {
        return divideRate;
    }

    public void setDivideRate(double divideRate) {
        this.divideRate = divideRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMultiplyRate() {
        return multiplyRate;
    }

    public void setMultiplyRate(double multiplyRate) {
        this.multiplyRate = multiplyRate;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitToId() {
        return unitToId;
    }

    public void setUnitToId(String unitToId) {
        this.unitToId = unitToId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPriceLimit() {
        return priceLimit;
    }

    public void setPriceLimit(double priceLimit) {
        this.priceLimit = priceLimit;
    }

    public double getPriceList() {
        return priceList;
    }

    public void setPriceList(double priceList) {
        this.priceList = priceList;
    }

    public double getPriceSTD() {
        return priceSTD;
    }

    public void setPriceSTD(double priceSTD) {
        this.priceSTD = priceSTD;
    }
    

    public static SerializerRead getSerializerRead() {
        return new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
            return new AdditionalPricesForProductsInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getDouble(4), dr.getDouble(5),dr.getString(6),dr.getDouble(7),dr.getDouble(8),dr.getDouble(9));
        }};
    }
    
}
