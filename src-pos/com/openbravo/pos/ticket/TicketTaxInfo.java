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

import com.openbravo.format.Formats;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;


public class TicketTaxInfo {
    
    private TaxInfo tax;
    
    private double subtotal;
    private double taxtotal;
            
    /** Creates a new instance of TicketTaxInfo */
    public TicketTaxInfo(TaxInfo tax) {
        this.tax = tax;
        
        subtotal = 0.0;
        taxtotal = 0.0;
    }
    
    public TaxInfo getTaxInfo() {
        return tax;
    }
    
    /**
     * Modificacion para mejorar el redondeo de decimales
     * Change to improve decimal rounding
     * @param dValue 
     */
    public void add(double dValue) {
        BigDecimal dValueDB = new BigDecimal(dValue);
        BigDecimal subTotalDB = new BigDecimal(subtotal);
        BigDecimal rateDB = new BigDecimal(tax.getRate());
        BigDecimal taxtotalDB = new BigDecimal(taxtotal);
        
        dValueDB = dValueDB.setScale(2,RoundingMode.HALF_UP);
        subTotalDB = subTotalDB.setScale(2,RoundingMode.HALF_UP);
        rateDB = rateDB.setScale(2,RoundingMode.HALF_UP);
        
        subTotalDB = subTotalDB.add(dValueDB).setScale(2,RoundingMode.HALF_UP);
        dValueDB = dValueDB.multiply(rateDB).setScale(2,RoundingMode.HALF_UP);
        taxtotalDB = taxtotalDB.add(dValueDB).setScale(2,RoundingMode.HALF_UP);
        
        subtotal = subTotalDB.doubleValue();
        taxtotal = taxtotalDB.doubleValue();
        
//        subtotal += dValue;
//        taxtotal = subtotal * tax.getRate();
    }
    
    public double getSubTotal() {    
        return subtotal;
    }
    
    public double getTax() {       
        return taxtotal;
    }
    
    public double getTotal() {         
        return subtotal + taxtotal;
    }
    
    public String printSubTotal() {
        return Formats.CURRENCY.formatValue(new Double(getSubTotal()));
    }
    public String printTax() {
        return Formats.CURRENCY.formatValue(new Double(getTax()));
    }    
    public String printTotal() {
        return Formats.CURRENCY.formatValue(new Double(getTotal()));
    }  
    public String printTaxRate(){
        return tax.printRate();
    }
}
