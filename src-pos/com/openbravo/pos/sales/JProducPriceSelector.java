/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JProducPriceSelector.java
 *
 * Created on 25/08/2011, 10:01:51 AM
 */
package com.openbravo.pos.sales;

import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.ticket.AdditionalPricesForProductsInfo;
import com.openbravo.pos.ticket.ProductInfoExt;
import com.openbravo.pos.ticket.UnitInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *class created for the selection of types of unit prices for POS
 * @author Carlos Prieto - SmartJSP S.A.S.
 */
public class JProducPriceSelector extends javax.swing.JPanel implements ActionListener{
    private Map<String,Price> map;
    private String unitId= null;
    
    

    /** Creates new form JProducPriceSelector */
    public JProducPriceSelector(ProductInfoExt productInfo, DataLogicSales dlSales,DataLogicSystem dlSystem,String defaultUnit) {
        double                                      priceAux =0;
        double                                      priceList =0;
        double                                      priceStd =0;
        double                                      priceLimit =0;
        Price                                       price =null;
        UnitInfo                                    unitInfo= null; 
        UnitInfo                                    unitInfoToId= null; 
        Iterator<AdditionalPricesForProductsInfo>   it = null;
        AdditionalPricesForProductsInfo             additionalPricesForProductsInfo = null;
        
        
        try{
            initComponents();
            initLabels();
            //this.setLabels(dlSystem.getResourceAsText("label.priceList"),dlSystem.getResourceAsText("label.priceSTD"),dlSystem.getResourceAsText("label.priceLimt"));
            jComboBox1.addActionListener(this);

            map = new HashMap<String, Price>();

//            unitInfo = dlSales.getUnitInfo(productInfo.getUnit());
//            price = new Price(productInfo.getPriceSell(), productInfo.getPriceSellSpecial(), productInfo.getPriceSellMin(), unitInfo.getName());
//            priceList = productInfo.getPriceSell();
//            priceStd = productInfo.getPriceSellSpecial();
//            priceLimit = productInfo.getPriceSellMin();
//            price.setUnityId(unitInfo.getId()); 
//            map.put(price.getName(), price);
//            jComboBox1.addItem(price.getName());
            
            it = dlSales.getAdditionalPricesForProductsInfos(productInfo.getID()).iterator();
            
            while(it.hasNext()){
                additionalPricesForProductsInfo = it.next();

                unitInfo = dlSales.getUnitInfo(additionalPricesForProductsInfo.getUnitId());
                unitInfoToId = dlSales.getUnitInfo(additionalPricesForProductsInfo.getUnitToId());
                
                price = new Price();
                price.setName(unitInfoToId.getName());
                        
                priceAux = additionalPricesForProductsInfo.getPriceList();
                price.setPriceList(priceAux);
                
                priceAux = additionalPricesForProductsInfo.getPriceSTD();
                price.setPriceStd(priceAux);
                
                priceAux = additionalPricesForProductsInfo.getPriceLimit();
                price.setPriceLimit(priceAux);
                
                price.setUnityId(unitInfoToId.getId());
                
                if(price.getPriceLimit() ==0 && price.getPriceList() ==0 && price.getPriceStd() ==0)
                    continue;
                
                map.put(price.getName(), price);
                jComboBox1.addItem(price.getName());
                
                if(unitInfoToId.getId().equalsIgnoreCase(defaultUnit)){
                    jComboBox1.setSelectedItem(price.getName());
                }
            }
            
            if(map.size() ==0){
                unitInfo = dlSales.getUnitInfo(productInfo.getUnit());
                price = new Price(productInfo.getPriceSell(), productInfo.getPriceSellSpecial(), productInfo.getPriceSellMin(), unitInfo.getName());
                price.setUnityId(unitInfo.getId()); 
                map.put(price.getName(), price);
                jComboBox1.addItem(price.getName());
            }
            
            setPrices();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * gets the double value of selected new price
     * @return 
     */
    public Double getSelectedPrice(){
        if(jRadioButton1.isSelected()){
            return jTextField1.getDoubleValue();
        }else if(jRadioButton2.isSelected()){
            return jTextField2.getDoubleValue();
        }else{
            return jTextField3.getDoubleValue();
        }
    }
    
    /**
     * set prices that are displayed
     */
    private void setPrices(){
        String key = (String)jComboBox1.getSelectedItem();
        Price price = map.get(key);
        
        jTextField1.setDoubleValue(price.getPriceList());
        jTextField2.setDoubleValue(price.getPriceStd());
        jTextField3.setDoubleValue(price.getPriceLimit());
        this.unitId = price.getUnityId();
    }

    /**
     * get the unit of the price selected
     * @return 
     */
    public String getUnitId() {
        return unitId;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jTextField1 = new com.openbravo.editor.JEditorCurrency();
        jTextField2 = new com.openbravo.editor.JEditorCurrency();
        jTextField3 = new com.openbravo.editor.JEditorCurrency();
        jComboBox1 = new javax.swing.JComboBox();

        setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Precio de lista");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 12, 0, 0);
        add(jRadioButton1, gridBagConstraints);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Precio estándar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 12, 0, 0);
        add(jRadioButton2, gridBagConstraints);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Precio límite");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 51;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 12, 52, 0);
        add(jRadioButton3, gridBagConstraints);

        jTextField1.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(26, 54, 0, 0);
        add(jTextField1, gridBagConstraints);

        jTextField2.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.ipady = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 54, 0, 42);
        add(jTextField2, gridBagConstraints);

        jTextField3.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 54, 52, 42);
        add(jTextField3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 268;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 36, 0, 0);
        add(jComboBox1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private com.openbravo.editor.JEditorCurrency jTextField1;
    private com.openbravo.editor.JEditorCurrency jTextField2;
    private com.openbravo.editor.JEditorCurrency jTextField3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent ae) {
        setPrices();
    }

    private void initLabels() {
        jRadioButton1.setText(AppLocal.getIntString("label.producpriceselector.pricelist"));
        jRadioButton2.setText(AppLocal.getIntString("label.producpriceselector.pricestandar"));
        jRadioButton3.setText(AppLocal.getIntString("label.producpriceselector.pricelimit"));
    }
    
    private class Price{
        private double priceList;
        private double priceStd;
        private double priceLimit;
        private String name;
        private String unityId;
        
        public Price(double priceList, double priceStd, double priceLimit, String name) {
            this.priceList = priceList;
            this.priceStd = priceStd;
            this.priceLimit = priceLimit;
            this.name = name;
        }

        public Price() {
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

        public double getPriceStd() {
            return priceStd;
        }

        public void setPriceStd(double priceStd) {
            this.priceStd = priceStd;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnityId() {
            return unityId;
        }

        public void setUnityId(String unityId) {
            this.unityId = unityId;
        }
    }
    
    public void setLabels(String label1, String label2,String label3){
     jRadioButton1.setText(label1);   
     jRadioButton2.setText(label2);
     jRadioButton3.setText(label3);
    }
}
