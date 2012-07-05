/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;

/**
 * class for handling synchronized Regions from the ERP 
 * @author Carlos Prieto - SmartJSP S.A.S.
 */
public class RegionInfo {
    
    private String id;
    private String countryId;
    private String description;
    private String name;

    public RegionInfo(String id, String countryId, String description, String name) {
        this.id = id;
        this.countryId = countryId;
        this.description = description;
        this.name = name;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    
    public static SerializerRead getSerializerRead() {
        return new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
            return new RegionInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4));
        }};
    }
}
