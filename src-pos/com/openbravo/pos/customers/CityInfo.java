/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SerializerRead;

/**
 *class with the information of the cities
 * @author  Carlos Prieto SmartJSP S.A.S.
 */
public class CityInfo {
    private String id;
    private String countryId;
    private String regionId;
    private String name;
    private String postal;

    /**
     * make the city with the parameters
     * @param id
     * @param countryId
     * @param regionId
     * @param name
     * @param postal 
     */
    public CityInfo(String id, String countryId, String regionId, String name, String postal) {
        this.id = id;
        this.countryId = countryId;
        this.regionId = regionId;
        this.name = name;
        this.postal = postal;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
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

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
    
    public static SerializerRead getSerializerRead() {
        return new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
            return new CityInfo(dr.getString(1), dr.getString(2), dr.getString(3), dr.getString(4), dr.getString(5));
        }};
    }
}
