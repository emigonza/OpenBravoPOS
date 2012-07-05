/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.ticket;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;

/**
 * clase para manejo de unidades de productos
 * class for management product units
 * @author Carlos Prieto - SmartJSP S.A.S.
 */
public class UnitInfo {
    private String id;
    private String code;
    private String name;
    private double costingPrecision;
    private String type;
    private double stdPrecision;

    public UnitInfo() {
    }

    public UnitInfo(String id, String code, String name, double costingPrecision, String type, double stdPrecision) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.costingPrecision = costingPrecision;
        this.type = type;
        this.stdPrecision = stdPrecision;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getCostingPrecision() {
        return costingPrecision;
    }

    public void setCostingPrecision(double costingPrecision) {
        this.costingPrecision = costingPrecision;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStdPrecision() {
        return stdPrecision;
    }

    public void setStdPrecision(double stdPrecision) {
        this.stdPrecision = stdPrecision;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public static SerializerRead getSerializerRead() {
        return new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
            return new UnitInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getDouble(4),dr.getString(5) ,dr.getDouble(6));
        }};
    }
}
