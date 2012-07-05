//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2008-2009 Openbravo, S.L.
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

package com.openbravo.pos.payment;

import com.openbravo.format.Formats;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public abstract class PaymentInfo {
    protected  String notes = "";
    
    public abstract String getName();
    public abstract double getTotal();
    public abstract PaymentInfo copyPayment();
    public abstract String getTransactionID();
    
    
    public String printTotal() {
        return Formats.CURRENCY.formatValue(new Double(getTotal()));
    }
    
    /**
     * modificacion para guardar las vueltas en los pagos y para guardar informacion de las tarjetas de credito
     * change for save payment return and credit card information
     * @return BigDecimal
     */
    public BigDecimal getTotalBigDecimal(){
        BigDecimal total = new BigDecimal(getTotal());
        total = total.setScale(2,RoundingMode.HALF_UP);
        return total;
    }
    
    public String getNotes(){
        return notes;
    }
    
    public void setNotes(String notes){
        this.notes = notes;
    }
}
