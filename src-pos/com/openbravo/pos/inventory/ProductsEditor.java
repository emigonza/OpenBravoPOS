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

package com.openbravo.pos.inventory;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.format.Formats;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.ticket.AdditionalPricesForProductsInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.UnitInfo;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrianromero
 */
public class ProductsEditor extends JPanel implements EditorRecord {
       
    private SentenceList m_sentcat;
    private ComboBoxValModel m_CategoryModel;

    private SentenceList taxcatsent;
    private ComboBoxValModel taxcatmodel;  

    private SentenceList attsent;
    private ComboBoxValModel attmodel;
    
    private SentenceList taxsent;
    private TaxesLogic taxeslogic;
    
    private ComboBoxValModel m_CodetypeModel;
    
    private Object m_id;
    private Object pricesell;
    private Object pricesellSpecial;
    private Object pricesellMin;
    private boolean priceselllock = false;
    
    private boolean reportlock = false;
    
    private DataLogicSystem dlSystem;
    DataLogicSales dlSales;
    
    /** Creates new form JEditProduct */
    public ProductsEditor(DataLogicSales dlSales, DirtyManager dirty,DataLogicSystem dlSystem) {

        initComponents();
        this.dlSystem = dlSystem;
        this.dlSales = dlSales;
        initCustom();
        // The taxes sentence
        taxsent = dlSales.getTaxList();
        // The categories model
        m_sentcat = dlSales.getCategoriesList();
        m_CategoryModel = new ComboBoxValModel();
        
        // The taxes model
        taxcatsent = dlSales.getTaxCategoriesList();
        taxcatmodel = new ComboBoxValModel();
        // The attributes model
        attsent = dlSales.getAttributeSetList();
        attmodel = new ComboBoxValModel();
        m_CodetypeModel = new ComboBoxValModel();
        m_CodetypeModel.add(null);
        m_CodetypeModel.add(CodeType.EAN13);
        m_CodetypeModel.add(CodeType.CODE128);
        m_jCodetype.setModel(m_CodetypeModel);
        m_jCodetype.setVisible(false);
        m_jCode.getDocument().addDocumentListener(dirty);
        m_jName.getDocument().addDocumentListener(dirty);
        m_jComment.addActionListener(dirty);
        m_jScale.addActionListener(dirty);
        m_jCategory.addActionListener(dirty);
        m_jTax.addActionListener(dirty);
        m_jAtt.addActionListener(dirty);
        m_jPriceBuy.getDocument().addDocumentListener(dirty);
        m_jPriceSell.getDocument().addDocumentListener(dirty);
        m_jImage.addPropertyChangeListener("image", dirty);
        m_jstockcost.getDocument().addDocumentListener(dirty);
        m_jstockvolume.getDocument().addDocumentListener(dirty);
        m_jInCatalog.addActionListener(dirty);
        m_jCatalogOrder.getDocument().addDocumentListener(dirty);
        txtAttributes.getDocument().addDocumentListener(dirty);
        FieldsManager fm = new FieldsManager();
        m_jPriceBuy.getDocument().addDocumentListener(fm);
        m_jPriceSell.getDocument().addDocumentListener(new PriceSellManager());
        m_jTax.addActionListener(fm);
        m_jPriceSellTax.getDocument().addDocumentListener(new PriceTaxManager());
        m_jmargin.getDocument().addDocumentListener(new MarginManager());
        writeValueEOF();
    }
    
    
    
    public void activate() throws BasicException {
        
        // Load the taxes logic
        taxeslogic = new TaxesLogic(taxsent.list());        
        
        m_CategoryModel = new ComboBoxValModel(m_sentcat.list());
        m_jCategory.setModel(m_CategoryModel);

        taxcatmodel = new ComboBoxValModel(taxcatsent.list());
        m_jTax.setModel(taxcatmodel);

        attmodel = new ComboBoxValModel(attsent.list());
        attmodel.add(0, null);
        m_jAtt.setModel(attmodel);
    }
    
    public void refresh() {
    }    
    
    public void writeValueEOF() {
        
        reportlock = true;
        // Los valores
        m_jTitle.setText(AppLocal.getIntString("label.recordeof"));
        m_id = null;
        m_jRef.setText(null);
        m_jCode.setText(null);
        m_jName.setText(null);
        m_jComment.setSelected(false);
        m_jScale.setSelected(false);
        m_CategoryModel.setSelectedKey(null);
        taxcatmodel.setSelectedKey(null);
        attmodel.setSelectedKey(null);
        m_jPriceBuy.setText(null);
        setPriceSell(null);         
        setPriceSellSpecial(null);         
        setPriceSellMin(null);         
        m_jImage.setImage(null);
        m_jstockcost.setText(null);
        m_jstockvolume.setText(null);
        m_jInCatalog.setSelected(false);
        m_jCatalogOrder.setText(null);
        txtAttributes.setText(null);
        reportlock = false;
        
        // Los habilitados
        m_jRef.setEnabled(false);
        m_jCode.setEnabled(false);
        m_jName.setEnabled(false);
        m_jComment.setEnabled(false);
        m_jScale.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jTax.setEnabled(false);
        m_jAtt.setEnabled(false);
        m_jPriceBuy.setEnabled(false);
        m_jPriceSell.setEnabled(false);
        m_jPriceSellTax.setEnabled(false);
        m_jmargin.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jstockcost.setEnabled(false);
        m_jstockvolume.setEnabled(false);
        m_jInCatalog.setEnabled(false);
        m_jCatalogOrder.setEnabled(false);
        txtAttributes.setEnabled(false);
                
        calculateMargin();
        calculatePriceSellTax();
        
        /**********************************/
        m_jPriceSellMin.setEnabled(false);
        m_jPriceSellMinTax.setEnabled(false);
        m_jPriceSellSpecial.setEnabled(false);
        m_jPriceSellSpecialTax.setEnabled(false);
        calculatePriceSellSpecialTax();
        calculatePriceSellMinTax();
        /***********************************/
    }
    public void writeValueInsert() {
       
        reportlock = true;
        // Los valores
        m_jTitle.setText(AppLocal.getIntString("label.recordnew"));
        m_id = UUID.randomUUID().toString();
        m_jRef.setText(null);
        m_jCode.setText(null);
        m_jName.setText(null);
        m_jComment.setSelected(false);
        m_jScale.setSelected(false);
        m_CategoryModel.setSelectedKey(null);
        taxcatmodel.setSelectedKey(null);
        attmodel.setSelectedKey(null);
        m_jPriceBuy.setText(null);
        setPriceSell(null);                     
        setPriceSellSpecial(null);                     
        setPriceSellMin(null);                     
        m_jImage.setImage(null);
        m_jstockcost.setText(null);
        m_jstockvolume.setText(null);
        m_jInCatalog.setSelected(true);
        m_jCatalogOrder.setText(null);
        txtAttributes.setText(null);
        reportlock = false;
        
        // Los habilitados
//        m_jRef.setEnabled(true);
//        m_jCode.setEnabled(true);
//        m_jName.setEnabled(true);
//        m_jComment.setEnabled(true);
//        m_jScale.setEnabled(true);
//        m_jCategory.setEnabled(true);
//        m_jTax.setEnabled(true);
//        m_jAtt.setEnabled(true);
//        m_jPriceBuy.setEnabled(true);
//        m_jPriceSell.setEnabled(true); 
//        m_jPriceSellTax.setEnabled(true);
//        m_jmargin.setEnabled(true);
//        m_jImage.setEnabled(true);
//        m_jstockcost.setEnabled(true);
//        m_jstockvolume.setEnabled(true);
//        m_jInCatalog.setEnabled(true); 
//        m_jCatalogOrder.setEnabled(false);
//        txtAttributes.setEnabled(true);
        
         m_jRef.setEnabled(false);
        m_jCode.setEnabled(false);
        m_jName.setEnabled(false);
        m_jComment.setEnabled(false);
        m_jScale.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jTax.setEnabled(false);
        m_jAtt.setEnabled(false);
        m_jPriceBuy.setEnabled(false);
        m_jPriceSell.setEnabled(false); 
        m_jPriceSellTax.setEnabled(false);
        m_jmargin.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jstockcost.setEnabled(false);
        m_jstockvolume.setEnabled(false);
        m_jInCatalog.setEnabled(false); 
        m_jCatalogOrder.setEnabled(false);
        txtAttributes.setEnabled(false);
        
        calculateMargin();
        calculatePriceSellTax();
        
        
        /**********************************/
        m_jPriceSellMin.setEnabled(false);
        m_jPriceSellMinTax.setEnabled(false);
        m_jPriceSellSpecial.setEnabled(false);
        m_jPriceSellSpecialTax.setEnabled(false);
        calculatePriceSellSpecialTax();
        calculatePriceSellMinTax();
        /***********************************/
   }
    public void writeValueDelete(Object value) {
        
        reportlock = true;       
        Object[] myprod = (Object[]) value;
        m_jTitle.setText(Formats.STRING.formatValue(myprod[1]) + " - " + Formats.STRING.formatValue(myprod[3]) + " " + AppLocal.getIntString("label.recorddeleted"));
        m_id = myprod[0];
        m_jRef.setText(Formats.STRING.formatValue(myprod[1]));
        m_jCode.setText(Formats.STRING.formatValue(myprod[2]));
        m_jName.setText(Formats.STRING.formatValue(myprod[3]));
        m_jComment.setSelected(((Boolean)myprod[4]).booleanValue());
        m_jScale.setSelected(((Boolean)myprod[5]).booleanValue());
        m_jPriceBuy.setText(Formats.CURRENCY.formatValue(myprod[6]));
        setPriceSell(myprod[7]);                    
        setPriceSellSpecial(myprod[17]);                    
        setPriceSellSpecial(myprod[18]);                    
        m_CategoryModel.setSelectedKey(myprod[8]);
        taxcatmodel.setSelectedKey(myprod[9]);
        attmodel.setSelectedKey(myprod[10]);
        m_jImage.setImage((BufferedImage) myprod[11]);
        m_jstockcost.setText(Formats.CURRENCY.formatValue(myprod[12]));
        m_jstockvolume.setText(Formats.DOUBLE.formatValue(myprod[13]));
        m_jInCatalog.setSelected(((Boolean)myprod[14]).booleanValue());
        m_jCatalogOrder.setText(Formats.INT.formatValue(myprod[15]));
        txtAttributes.setText(Formats.BYTEA.formatValue(myprod[16]));
        txtAttributes.setCaretPosition(0);
        reportlock = false;
        
        // Los habilitados
        m_jRef.setEnabled(false);
        m_jCode.setEnabled(false);
        m_jName.setEnabled(false);
        m_jComment.setEnabled(false);
        m_jScale.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jTax.setEnabled(false);
        m_jAtt.setEnabled(false);
        m_jPriceBuy.setEnabled(false);
        m_jPriceSell.setEnabled(false);
        m_jPriceSellTax.setEnabled(false);
        m_jmargin.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jstockcost.setEnabled(false);
        m_jstockvolume.setEnabled(false);
        m_jInCatalog.setEnabled(false);
        m_jCatalogOrder.setEnabled(false);
        txtAttributes.setEnabled(false);
        
        calculateMargin();
        calculatePriceSellTax();
        
        
        /**********************************/
        m_jPriceSellMin.setEnabled(false);
        m_jPriceSellMinTax.setEnabled(false);
        m_jPriceSellSpecial.setEnabled(false);
        m_jPriceSellSpecialTax.setEnabled(false);
        calculatePriceSellSpecialTax();
        calculatePriceSellMinTax();
        /***********************************/
    }    
    
    public void writeValueEdit(Object value) {
        
        reportlock = true;
        Object[] myprod = (Object[]) value;
        m_jTitle.setText(Formats.STRING.formatValue(myprod[1]) + " - " + Formats.STRING.formatValue(myprod[3]));
        m_id = myprod[0];
        m_jRef.setText(Formats.STRING.formatValue(myprod[1]));
        m_jCode.setText(Formats.STRING.formatValue(myprod[2]));
        m_jName.setText(Formats.STRING.formatValue(myprod[3]));
        m_jComment.setSelected(((Boolean)myprod[4]).booleanValue());
        m_jScale.setSelected(((Boolean)myprod[5]).booleanValue());
        m_jPriceBuy.setText(Formats.CURRENCY.formatValue(myprod[6]));
        setPriceSell(myprod[7]);   
        setPriceSellSpecial(myprod[17]);  
        setPriceSellMin(myprod[18]);   
        m_CategoryModel.setSelectedKey(myprod[8]);
        taxcatmodel.setSelectedKey(myprod[9]);
        attmodel.setSelectedKey(myprod[10]);
        m_jImage.setImage((BufferedImage) myprod[11]);
        m_jstockcost.setText(Formats.CURRENCY.formatValue(myprod[12]));
        m_jstockvolume.setText(Formats.DOUBLE.formatValue(myprod[13]));
        m_jInCatalog.setSelected(((Boolean)myprod[14]).booleanValue());
        m_jCatalogOrder.setText(Formats.INT.formatValue(myprod[15]));
        txtAttributes.setText(Formats.BYTEA.formatValue(myprod[16]));
        txtAttributes.setCaretPosition(0);
        reportlock = false;
        
        
        
        // Los habilitados
//        m_jRef.setEnabled(true);
//        m_jCode.setEnabled(true);
//        m_jName.setEnabled(true);
//        m_jComment.setEnabled(true);
//        m_jScale.setEnabled(true);
//        m_jCategory.setEnabled(true);
//        m_jTax.setEnabled(true);
//        m_jAtt.setEnabled(true);
//        m_jPriceBuy.setEnabled(true);
//        m_jPriceSell.setEnabled(true); 
//        m_jPriceSellTax.setEnabled(true);
//        m_jmargin.setEnabled(true);
//        m_jImage.setEnabled(true);
//        m_jstockcost.setEnabled(true);
//        m_jstockvolume.setEnabled(true);
//        m_jInCatalog.setEnabled(true);
//        m_jCatalogOrder.setEnabled(m_jInCatalog.isSelected());  
//        txtAttributes.setEnabled(true);
        
         m_jRef.setEnabled(false);
        m_jCode.setEnabled(false);
        m_jName.setEnabled(false);
        m_jComment.setEnabled(false);
        m_jScale.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jTax.setEnabled(false);
        m_jAtt.setEnabled(false);
        m_jPriceBuy.setEnabled(false);
        m_jPriceSell.setEnabled(false); 
        m_jPriceSellTax.setEnabled(false);
        m_jmargin.setEnabled(false);
        m_jImage.setEnabled(false);
        m_jstockcost.setEnabled(false);
        m_jstockvolume.setEnabled(false);
        m_jInCatalog.setEnabled(false);
        m_jCatalogOrder.setEnabled(m_jInCatalog.isSelected());  
        txtAttributes.setEnabled(false);
        
        calculateMargin();
        calculatePriceSellTax();
        
        
        /**********************************/
        m_jPriceSellMin.setEnabled(false);
        m_jPriceSellMinTax.setEnabled(false);
        m_jPriceSellSpecial.setEnabled(false);
        m_jPriceSellSpecialTax.setEnabled(false);
        calculatePriceSellSpecialTax();
        calculatePriceSellMinTax();
        loadAdditionalPricesForProductsInfo((String)myprod[19]);
        
        String stock = "0";
        try {
            stock = Double.toString(dlSales.findProductStock("0", (String)m_id, null));
        } catch (BasicException ex) {
            Logger.getLogger(ProductsEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        m_jStock.setText(stock);
        
        String unit = "";
        try {
            UnitInfo unitInfo = dlSales.getUnitInfo((String)myprod[19]);
            unit = unitInfo.getName();
            m_jUnit.setText(unit);
        } catch (Exception ex) {
            Logger.getLogger(ProductsEditor.class.getName()).log(Level.SEVERE, null, ex);
            unit="";
        }
        
        /***********************************/
    }

    public Object createValue() throws BasicException {
        
        Object[] myprod = new Object[17];
        myprod[0] = m_id;
        myprod[1] = m_jRef.getText();
        myprod[2] = m_jCode.getText();
        myprod[3] = m_jName.getText();
        myprod[4] = Boolean.valueOf(m_jComment.isSelected());
        myprod[5] = Boolean.valueOf(m_jScale.isSelected());
        myprod[6] = Formats.CURRENCY.parseValue(m_jPriceBuy.getText());
        myprod[7] = pricesell;
        myprod[8] = m_CategoryModel.getSelectedKey();
        myprod[9] = taxcatmodel.getSelectedKey();
        myprod[10] = attmodel.getSelectedKey();
        myprod[11] = m_jImage.getImage();
        myprod[12] = Formats.CURRENCY.parseValue(m_jstockcost.getText());
        myprod[13] = Formats.DOUBLE.parseValue(m_jstockvolume.getText());
        myprod[14] = Boolean.valueOf(m_jInCatalog.isSelected());
        myprod[15] = Formats.INT.parseValue(m_jCatalogOrder.getText());
        myprod[16] = Formats.BYTEA.parseValue(txtAttributes.getText());
        
        return myprod;
    }    
    
    public Component getComponent() {
        return this;
    }
    
    private void calculateMargin() {
        
        if (!reportlock) {
            reportlock = true;
            
            Double dPriceBuy = readCurrency(m_jPriceBuy.getText());
            Double dPriceSell = (Double) pricesell;

            if (dPriceBuy == null || dPriceSell == null) {
                m_jmargin.setText(null);
            } else {
                m_jmargin.setText(Formats.PERCENT.formatValue(new Double(dPriceSell.doubleValue() / dPriceBuy.doubleValue() - 1.0)));
            }    
            reportlock = false;
        }
    }
    
    private void calculatePriceSellTax() {
        
        if (!reportlock) {
            reportlock = true;
            
            Double dPriceSell = (Double) pricesell;
            
            if (dPriceSell == null) {
                m_jPriceSellTax.setText(null);
            } else {               
                double dTaxRate = taxeslogic.getTaxRate((TaxCategoryInfo) taxcatmodel.getSelectedItem(), new Date());
                m_jPriceSellTax.setText(Formats.CURRENCY.formatValue(new Double(dPriceSell.doubleValue() * (1.0 + dTaxRate))));
            }            
            reportlock = false;
        }
    }
    
    private void calculatePriceSellfromMargin() {
        
        if (!reportlock) {
            reportlock = true;
            
            Double dPriceBuy = readCurrency(m_jPriceBuy.getText());
            Double dMargin = readPercent(m_jmargin.getText());  
            
            if (dMargin == null || dPriceBuy == null) {
                setPriceSell(null);
                setPriceSellSpecial(null);
                setPriceSellMin(null);
            } else {
                setPriceSell(new Double(dPriceBuy.doubleValue() * (1.0 + dMargin.doubleValue())));
                setPriceSellSpecial(new Double(dPriceBuy.doubleValue() * (1.0 + dMargin.doubleValue())));
                setPriceSellMin(new Double(dPriceBuy.doubleValue() * (1.0 + dMargin.doubleValue())));
            }                        
            
            reportlock = false;
        }
      
    }
    
    private void calculatePriceSellfromPST() {
        
        if (!reportlock) {
            reportlock = true;
                    
            Double dPriceSellTax = readCurrency(m_jPriceSellTax.getText());  

            if (dPriceSellTax == null) {
                setPriceSell(null);
                setPriceSellSpecial(null);
                setPriceSellMin(null);
            } else {
                double dTaxRate = taxeslogic.getTaxRate((TaxCategoryInfo) taxcatmodel.getSelectedItem(), new Date());
                setPriceSell(new Double(dPriceSellTax.doubleValue() / (1.0 + dTaxRate)));
                setPriceSellSpecial(new Double(dPriceSellTax.doubleValue() / (1.0 + dTaxRate)));
                setPriceSellMin(new Double(dPriceSellTax.doubleValue() / (1.0 + dTaxRate)));
            }   
                        
            reportlock = false;
        }    
    }
    
    private void setPriceSell(Object value) {
        
        if (!priceselllock) {
            priceselllock = true;
            pricesell = value;
            m_jPriceSell.setText(Formats.CURRENCY.formatValue(pricesell));  
            priceselllock = false;
        }
    }
    
    /**********************************************************/
    private void setPriceSellSpecial(Object value) {
        
        if (!priceselllock) {
            priceselllock = true;
            pricesellSpecial = value;
            m_jPriceSellSpecial.setText(Formats.CURRENCY.formatValue(pricesellSpecial));  
            priceselllock = false;
        }
    }
    
    private void setPriceSellMin(Object value) {
        
        if (!priceselllock) {
            priceselllock = true;
            pricesellMin = value;
            m_jPriceSellMin.setText(Formats.CURRENCY.formatValue(pricesellMin));  
            priceselllock = false;
        }
    }
    
    private void calculatePriceSellSpecialTax() {
        
        if (!reportlock) {
            reportlock = true;
            
            Double dPriceSell = (Double) pricesellSpecial;
            
            if (dPriceSell == null) {
                m_jPriceSellSpecialTax.setText(null);
            } else {               
                double dTaxRate = taxeslogic.getTaxRate((TaxCategoryInfo) taxcatmodel.getSelectedItem(), new Date());
                m_jPriceSellSpecialTax.setText(Formats.CURRENCY.formatValue(new Double(dPriceSell.doubleValue() * (1.0 + dTaxRate))));
            }            
            reportlock = false;
        }
    }
    
    private void calculatePriceSellMinTax() {
        
        if (!reportlock) {
            reportlock = true;
            
            Double dPriceSell = (Double) pricesellMin;
            
            if (dPriceSell == null) {
                m_jPriceSellMinTax.setText(null);
            } else {               
                double dTaxRate = taxeslogic.getTaxRate((TaxCategoryInfo) taxcatmodel.getSelectedItem(), new Date());
                m_jPriceSellMinTax.setText(Formats.CURRENCY.formatValue(new Double(dPriceSell.doubleValue() * (1.0 + dTaxRate))));
            }            
            reportlock = false;
        }
    }

    private void initCustom() {
        //        jLabel4.setText(dlSystem.getResourceAsText("label.priceList"));
//        jLabel16.setText(dlSystem.getResourceAsText("label.priceListTax"));
//        jLabel14.setText(dlSystem.getResourceAsText("label.priceSTD"));
//        jLabel15.setText(dlSystem.getResourceAsText("label.priceSTDTax"));
//        jLabel17.setText(dlSystem.getResourceAsText("label.priceLimt"));
//        jLabel19.setText(dlSystem.getResourceAsText("label.priceLimtTax"));
        
        m_jPriceSell.setVisible(false);
        m_jPriceSellMin.setVisible(false);
        m_jPriceSellMinTax.setVisible(false);
        m_jPriceSellSpecial.setVisible(false);
        m_jPriceSellSpecialTax.setVisible(false);
        m_jPriceSellTax.setVisible(false);
        m_jmargin.setVisible(false);
    }

    /**
     * nuevo tab para visualización y actualización de los precios adicionales por producto y  parametro de es visible.
     * new tab to view and update product prices and additional parameter is visible.
     * @param unitId 
     */
    private void loadAdditionalPricesForProductsInfo(String unitId) {
        java.util.List<AdditionalPricesForProductsInfo> additionalPricesForProductsInfos= dlSales.getAdditionalPricesForProductsInfos((String)m_id);       
        Iterator<AdditionalPricesForProductsInfo>       iterator = additionalPricesForProductsInfos.iterator();
        AdditionalPricesForProductsInfo                 additionalPricesForProductsInfo = null;
        UnitInfo                                        unitInfo = null;
        DefaultTableModel                               model = (DefaultTableModel)jTable1.getModel();
        ProductInfoExt                                  productInfo =null;
        
        try{
            while(model.getRowCount()>0){
                model.removeRow(0);
            }
            
            
            
                
            while(iterator.hasNext()){
                additionalPricesForProductsInfo = iterator.next();
                if(additionalPricesForProductsInfo.getPriceLimit() == 0 && additionalPricesForProductsInfo.getPriceList() == 0 && additionalPricesForProductsInfo.getPriceSTD() == 0)
                    continue;
                unitInfo = dlSales.getUnitInfo(additionalPricesForProductsInfo.getUnitToId());
                if(unitInfo != null)
                    model.addRow(new Object[]{unitInfo.getName(), additionalPricesForProductsInfo.getPriceList(),additionalPricesForProductsInfo.getPriceSTD(),additionalPricesForProductsInfo.getPriceLimit()});
            }
            
            if(model.getRowCount() ==0){
                unitInfo = dlSales.getUnitInfo(unitId);
                if(unitInfo != null)
                    model.addRow(new Object[]{unitInfo.getName(), m_jPriceSell.getText(),m_jPriceSellSpecial.getText(),m_jPriceSellMin.getText()});
               
            }
        }catch(BasicException be){
            be.printStackTrace();
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            be.printStackTrace(printWriter);
            JOptionPane.showMessageDialog(null, result.toString());
            Logger.getLogger(ProductsEditor.class.getName()).log(Level.SEVERE, null, be);
        }
        
        
    }
    /**********************************************************/
    
    
    private class PriceSellManager implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            if (!priceselllock) {
                priceselllock = true;
                pricesell = readCurrency(m_jPriceSell.getText());
                priceselllock = false;
            }
            calculateMargin();
            calculatePriceSellTax();
            calculatePriceSellSpecialTax();
            calculatePriceSellMinTax();
        }
        public void insertUpdate(DocumentEvent e) {
            if (!priceselllock) {
                priceselllock = true;
                pricesell = readCurrency(m_jPriceSell.getText());
                priceselllock = false;
            }
            calculateMargin();
            calculatePriceSellTax();
            calculatePriceSellSpecialTax();
            calculatePriceSellMinTax();
        }    
        public void removeUpdate(DocumentEvent e) {
            if (!priceselllock) {
                priceselllock = true;
                pricesell = readCurrency(m_jPriceSell.getText());
                priceselllock = false;
            }
            calculateMargin();
            calculatePriceSellTax();
            calculatePriceSellSpecialTax();
            calculatePriceSellMinTax();
        }  
    }
    
    private class FieldsManager implements DocumentListener, ActionListener {
        public void changedUpdate(DocumentEvent e) {
            calculateMargin();
            calculatePriceSellTax();
            calculatePriceSellSpecialTax();
            calculatePriceSellMinTax();
        }
        public void insertUpdate(DocumentEvent e) {
            calculateMargin();
            calculatePriceSellTax();
            calculatePriceSellSpecialTax();
            calculatePriceSellMinTax();
        }    
        public void removeUpdate(DocumentEvent e) {
            calculateMargin();
            calculatePriceSellTax();
            calculatePriceSellSpecialTax();
            calculatePriceSellMinTax();
        }         
        public void actionPerformed(ActionEvent e) {
            calculateMargin();
            calculatePriceSellTax();
            calculatePriceSellSpecialTax();
            calculatePriceSellMinTax();
        }
    }

    private class PriceTaxManager implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            calculatePriceSellfromPST();
            calculateMargin();
        }
        public void insertUpdate(DocumentEvent e) {
            calculatePriceSellfromPST();
            calculateMargin();
        }    
        public void removeUpdate(DocumentEvent e) {
            calculatePriceSellfromPST();
            calculateMargin();
        }         
    }
    
    private class MarginManager implements DocumentListener  {
        public void changedUpdate(DocumentEvent e) {
            calculatePriceSellfromMargin();
            calculatePriceSellTax();
        }
        public void insertUpdate(DocumentEvent e) {
            calculatePriceSellfromMargin();
            calculatePriceSellTax();
        }    
        public void removeUpdate(DocumentEvent e) {
            calculatePriceSellfromMargin();
            calculatePriceSellTax();
        }         
    }
    
    private final static Double readCurrency(String sValue) {
        try {
            return (Double) Formats.CURRENCY.parseValue(sValue);
        } catch (BasicException e) {
            return null;
        }
    }
        
    private final static Double readPercent(String sValue) {
        try {
            return (Double) Formats.PERCENT.parseValue(sValue);
        } catch (BasicException e) {
            return null;
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        m_jRef = new javax.swing.JTextField();
        m_jName = new javax.swing.JTextField();
        m_jTitle = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        m_jCode = new javax.swing.JTextField();
        m_jImage = new com.openbravo.data.gui.JImageEditor();
        jLabel3 = new javax.swing.JLabel();
        m_jPriceBuy = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        m_jCategory = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        m_jTax = new javax.swing.JComboBox();
        m_jCodetype = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        m_jAtt = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        m_jstockcost = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        m_jstockvolume = new javax.swing.JTextField();
        m_jScale = new javax.swing.JCheckBox();
        m_jComment = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        m_jCatalogOrder = new javax.swing.JTextField();
        m_jInCatalog = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        m_jStock = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        m_jUnit = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAttributes = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        m_jPriceSell = new javax.swing.JTextField();
        m_jmargin = new javax.swing.JTextField();
        m_jPriceSellTax = new javax.swing.JTextField();
        m_jPriceSellSpecial = new javax.swing.JTextField();
        m_jPriceSellSpecialTax = new javax.swing.JTextField();
        m_jPriceSellMin = new javax.swing.JTextField();
        m_jPriceSellMinTax = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(null);

        jLabel1.setText(AppLocal.getIntString("label.prodref")); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(10, 50, 80, 14);

        jLabel2.setText(AppLocal.getIntString("label.prodname")); // NOI18N
        add(jLabel2);
        jLabel2.setBounds(180, 50, 70, 14);

        m_jRef.setEditable(false);
        add(m_jRef);
        m_jRef.setBounds(90, 50, 70, 20);

        m_jName.setEditable(false);
        add(m_jName);
        m_jName.setBounds(250, 50, 220, 20);

        m_jTitle.setFont(new java.awt.Font("SansSerif", 3, 18));
        add(m_jTitle);
        m_jTitle.setBounds(10, 10, 320, 30);

        jPanel1.setLayout(null);

        jLabel6.setText(AppLocal.getIntString("label.prodbarcode")); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 20, 150, 14);

        m_jCode.setEditable(false);
        jPanel1.add(m_jCode);
        m_jCode.setBounds(160, 20, 170, 20);
        jPanel1.add(m_jImage);
        m_jImage.setBounds(340, 20, 200, 180);

        jLabel3.setText(AppLocal.getIntString("label.prodpricebuy")); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 50, 150, 14);

        m_jPriceBuy.setEditable(false);
        m_jPriceBuy.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel1.add(m_jPriceBuy);
        m_jPriceBuy.setBounds(160, 50, 80, 20);

        jLabel5.setText(AppLocal.getIntString("label.prodcategory")); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 120, 150, 14);

        m_jCategory.setEnabled(false);
        jPanel1.add(m_jCategory);
        m_jCategory.setBounds(160, 120, 170, 20);

        jLabel7.setText(AppLocal.getIntString("label.taxcategory")); // NOI18N
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 90, 150, 14);

        m_jTax.setEnabled(false);
        jPanel1.add(m_jTax);
        m_jTax.setBounds(160, 90, 170, 20);

        m_jCodetype.setEnabled(false);
        jPanel1.add(m_jCodetype);
        m_jCodetype.setBounds(260, 50, 80, 20);

        jLabel13.setText(AppLocal.getIntString("label.attributes")); // NOI18N
        jPanel1.add(jLabel13);
        jLabel13.setBounds(10, 150, 150, 14);

        m_jAtt.setEnabled(false);
        jPanel1.add(m_jAtt);
        m_jAtt.setBounds(160, 150, 170, 20);

        jTabbedPane1.addTab(AppLocal.getIntString("label.prodgeneral"), jPanel1); // NOI18N

        jLabel9.setText(AppLocal.getIntString("label.prodstockcost")); // NOI18N

        m_jstockcost.setEditable(false);
        m_jstockcost.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel10.setText(AppLocal.getIntString("label.prodstockvol")); // NOI18N

        m_jstockvolume.setEditable(false);
        m_jstockvolume.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        m_jScale.setEnabled(false);

        jLabel18.setText(AppLocal.getIntString("label.prodorder")); // NOI18N

        m_jCatalogOrder.setEditable(false);
        m_jCatalogOrder.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        m_jCatalogOrder.setEnabled(false);

        m_jInCatalog.setEnabled(false);
        m_jInCatalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jInCatalogActionPerformed(evt);
            }
        });

        jLabel8.setText(AppLocal.getIntString("label.prodincatalog")); // NOI18N

        jLabel11.setText(AppLocal.getIntString("label.prodaux")); // NOI18N

        jLabel12.setText(AppLocal.getIntString("label.prodscale")); // NOI18N

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pos_messages"); // NOI18N
        jLabel4.setText(bundle.getString("label.inventory")); // NOI18N

        m_jStock.setEditable(false);
        m_jStock.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel14.setText(bundle.getString("label.produnits")); // NOI18N

        m_jUnit.setEditable(false);
        m_jUnit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jLabel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 16, Short.MAX_VALUE)
                                .add(m_jstockcost, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                    .add(jLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(m_jInCatalog, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(m_jComment, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(6, 6, 6))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 86, Short.MAX_VALUE)
                                .add(m_jStock, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(m_jCatalogOrder, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(m_jstockvolume, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(m_jUnit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(105, 105, 105))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_jScale, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(19, 19, 19)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_jStock, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(jLabel14)
                    .add(m_jUnit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10)
                    .add(m_jstockvolume, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel9)
                    .add(m_jstockcost, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(16, 16, 16)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel18)
                        .add(m_jCatalogOrder, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel8))
                    .add(m_jInCatalog))
                .add(10, 10, 10)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel11)
                    .add(m_jComment))
                .add(4, 4, 4)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel12)
                    .add(m_jScale))
                .add(51, 51, 51))
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.prodstock"), jPanel2); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel3.setLayout(new java.awt.BorderLayout());

        txtAttributes.setEditable(false);
        txtAttributes.setFont(new java.awt.Font("DialogInput", 0, 12));
        jScrollPane1.setViewportView(txtAttributes);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab(AppLocal.getIntString("label.properties"), jPanel3); // NOI18N

        m_jPriceSell.setEditable(false);
        m_jPriceSell.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        m_jmargin.setEditable(false);
        m_jmargin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        m_jPriceSellTax.setEditable(false);
        m_jPriceSellTax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        m_jPriceSellMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jPriceSellMinActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Unit", "Price List", "Price STD", "Price Limit"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(m_jPriceSell, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 41, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_jmargin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_jPriceSellTax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_jPriceSellSpecial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_jPriceSellSpecialTax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_jPriceSellMin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_jPriceSellMinTax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_jPriceSell, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(m_jmargin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(m_jPriceSellTax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(m_jPriceSellSpecial, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(m_jPriceSellSpecialTax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(m_jPriceSellMin, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(m_jPriceSellMinTax, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 184, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.producteditor.salesprice"), jPanel4); // NOI18N

        add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 90, 560, 280);
    }// </editor-fold>//GEN-END:initComponents

    private void m_jInCatalogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jInCatalogActionPerformed
 
        if (m_jInCatalog.isSelected()) {
            m_jCatalogOrder.setEnabled(true);   
        } else {
            m_jCatalogOrder.setEnabled(false);   
            m_jCatalogOrder.setText(null);   
        }

    }//GEN-LAST:event_m_jInCatalogActionPerformed

private void m_jPriceSellMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jPriceSellMinActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_m_jPriceSellMinActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox m_jAtt;
    private javax.swing.JTextField m_jCatalogOrder;
    private javax.swing.JComboBox m_jCategory;
    private javax.swing.JTextField m_jCode;
    private javax.swing.JComboBox m_jCodetype;
    private javax.swing.JCheckBox m_jComment;
    private com.openbravo.data.gui.JImageEditor m_jImage;
    private javax.swing.JCheckBox m_jInCatalog;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextField m_jPriceBuy;
    private javax.swing.JTextField m_jPriceSell;
    private javax.swing.JTextField m_jPriceSellMin;
    private javax.swing.JTextField m_jPriceSellMinTax;
    private javax.swing.JTextField m_jPriceSellSpecial;
    private javax.swing.JTextField m_jPriceSellSpecialTax;
    private javax.swing.JTextField m_jPriceSellTax;
    private javax.swing.JTextField m_jRef;
    private javax.swing.JCheckBox m_jScale;
    private javax.swing.JTextField m_jStock;
    private javax.swing.JComboBox m_jTax;
    private javax.swing.JLabel m_jTitle;
    private javax.swing.JTextField m_jUnit;
    private javax.swing.JTextField m_jmargin;
    private javax.swing.JTextField m_jstockcost;
    private javax.swing.JTextField m_jstockvolume;
    private javax.swing.JTextArea txtAttributes;
    // End of variables declaration//GEN-END:variables
    
}
