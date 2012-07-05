/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;

/**
 *class for handling synchronized countries from the ERP
 * @author Carlos Prieto SmartJSP S.A.S.
 */
public class CountryInfo {
    private String id;
    private String currencyId;
    private String  countryCode;
    private String description;
    private String name;
    private String regionname;

    public CountryInfo(String id, String currencyId, String countryCode, String description, String name, String regionname) {
        this.id = id;
        this.currencyId = currencyId;
        this.countryCode = countryCode;
        this.description = description;
        this.name = name;
        this.regionname = regionname;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }
    
    
    public static SerializerRead getSerializerRead() {
        return new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
            return new CountryInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getString(5),dr.getString(6));
        }};
    }
}
