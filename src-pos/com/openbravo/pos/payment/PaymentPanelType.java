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

package com.openbravo.pos.payment;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.util.RoundUtils;
import com.openbravo.smj.beans.RenderJCombobox;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 *
 * @author adrianromero
 */
public class PaymentPanelType extends javax.swing.JPanel implements PaymentPanel {
    private double              m_dPaid;
    private double              m_dTotal;
    private String              m_sTransactionID;
    private JPaymentNotifier    m_notifier;
    private AppView             appView;
    
    
    
    /** Creates new form JPaymentCash */
    public PaymentPanelType(JPaymentNotifier notifier) {
        
        m_notifier = notifier;
        
        initComponents();  
        
        m_jHolderName.addPropertyChangeListener("Edition", new RecalculateName());
        m_jCardNumber.addPropertyChangeListener("Edition", new RecalculateName());
        m_jExpirationDate.addPropertyChangeListener("Edition", new RecalculateName());
        m_jMoneyEuros.addPropertyChangeListener("Edition", new RecalculateName());
        
        
        m_jHolderName.addEditorKeys(m_jKeys);
        m_jCardNumber.addEditorKeys(m_jKeys);
        m_jExpirationDate.addEditorKeys(m_jKeys);
        m_jMoneyEuros.addEditorKeys(m_jKeys);
        

    }
    
    public JComponent getComponent(){
        return this;
    }
    
    public void activate(String sTransaction, double dTotal) {
        
        m_sTransactionID = sTransaction;
        m_dTotal = dTotal;
        
        resetState();
        
        m_jHolderName.activate();
    }
    
    private void resetState() {
        
        m_notifier.setStatus(false, false);  
              
        m_jHolderName.setText((String)jComboBox1.getSelectedItem());;
        m_jCardNumber.setText(null);
        m_jExpirationDate.setText(null);
        m_jMoneyEuros.setDoubleValue(m_dTotal);
    }
    
    public PaymentInfoMagcard getPaymentInfoMagcard() {
        
        if (m_dTotal > 0.0) {
            return new PaymentInfoMagcard(
                    m_jHolderName.getText(),
                    m_jCardNumber.getText(), 
                    m_jExpirationDate.getText(),
                    null,
                    null,                    
                    null,                    
                    m_sTransactionID,
                    m_dPaid);
        } else {
            return new PaymentInfoMagcardRefund(
                    m_jHolderName.getText(),
                    m_jCardNumber.getText(), 
                    m_jExpirationDate.getText(),
                    null,
                    null,                    
                    null,                    
                    m_sTransactionID,
                    m_dPaid);
        }
    }    
    
    private class RecalculateName implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            validatePayment();
        }
    }
    
    private void validatePayment(){
        boolean isvalid = isValidHolder() && isValidCardNumber() && isValidExpirationDate();
            
            Double value = m_jMoneyEuros.getDoubleValue();
            if (value == null) {
                m_dPaid = m_dTotal;
                m_jMoneyEuros.setDoubleValue(m_dPaid);
            } else {
                m_dPaid = value;
            }
            int iCompare = RoundUtils.compare(m_dPaid, m_dTotal);
            m_notifier.setStatus(isvalid && m_dPaid > 0.0 && iCompare <= 0, isvalid && iCompare == 0);
    }
    
    private boolean isValidHolder() {
        return !(m_jHolderName.getText() == null || m_jHolderName.getText().equals(""));
    }
    private boolean isValidCardNumber() {
        // SMJ Cambio de validacion (solo 4 digitos)
        if(m_jCardNumber.getText() == null )
            return false;
        else
            return (m_jCardNumber.getText().length()>3 && m_jCardNumber.getText().length()<5);
        //return (LuhnAlgorithm.checkCC(m_jCardNumber.getText()) && m_jCardNumber.getText().length()>13 && m_jCardNumber.getText().length()<20);
    }
    private boolean isValidExpirationDate() {
        // SMJ ignorar fecha de expiracion
         return true;
       // return !(m_jExpirationDate.getText() == null || m_jExpirationDate.getText().length() != 4);
    }

    /**
     * agrega combo para seleccion de tarjeta de credito
     * add combo for credit card select
     * @param appView 
     */
    public void setAppView(AppView appView) {
        this.appView = appView;
        DataLogicSystem dlSystem = (DataLogicSystem) appView.getBean("com.openbravo.pos.forms.DataLogicSystem");
        
        String reason = dlSystem.getResourceAsText("card.names");
        jComboBox1.removeAllItems();
        String[] reasonList = reason.split(",");
        
        HashMap<Object, ImageIcon> icons = new HashMap<Object, ImageIcon>();
        
        
        for(int i =0; i < reasonList.length; i++){
            jComboBox1.addItem(reasonList[i]);
            /**
             * Carga logos de tarjetas de credito
             * load credit card logo
             */
            BufferedImage put = dlSystem.getResourceAsImage(reasonList[i]);
            if(put != null){
                Image img = (Image)resize(put, 30, 30);
                icons.put(reasonList[i], new ImageIcon(img));
            }else
                icons.put(reasonList[i], null);
            //ImageIcon put = icons.put(reasonList[i], new ImageIcon((Image)dlSystem.getResourceAsImage("CashRegister.Logo")));
        }
        
        jComboBox1.setRenderer(new RenderJCombobox(icons));
        /**
         * carga combo de tarjeta de credito
         * load credit card combo
         */
        m_jHolderName.setText((String)jComboBox1.getSelectedItem());
        m_jHolderName.setVisible(false);
    }
    
    public BufferedImage resize(BufferedImage image, int width, int height) { 
        BufferedImage resizedImage = new BufferedImage(width, height, 
        BufferedImage.TYPE_INT_ARGB); 
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage; 
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();
        jPanel4 = new javax.swing.JPanel();
        m_jCardNumber = new com.openbravo.editor.JEditorStringNumber();
        m_jExpirationDate = new com.openbravo.editor.JEditorStringNumber();
        m_jHolderName = new com.openbravo.editor.JEditorString();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        m_jMoneyEuros = new com.openbravo.editor.JEditorCurrencyPositive();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(m_jKeys);

        jPanel2.add(jPanel1, java.awt.BorderLayout.NORTH);

        add(jPanel2, java.awt.BorderLayout.EAST);

        m_jCardNumber.setOpaque(false);

        jLabel8.setText(AppLocal.getIntString("label.cardholder")); // NOI18N

        jLabel6.setText(AppLocal.getIntString("label.cardnumber")); // NOI18N

        jLabel7.setText(AppLocal.getIntString("label.cardexpdate")); // NOI18N
        jLabel7.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 10));
        jLabel2.setText("MMYY");

        jLabel9.setText(AppLocal.getIntString("label.cardnumber")); // NOI18N

        m_jMoneyEuros.setOpaque(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(m_jHolderName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(m_jExpirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_jMoneyEuros, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_jCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(m_jMoneyEuros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(m_jCardNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(m_jExpirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jHolderName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        add(jPanel4, java.awt.BorderLayout.CENTER);

        jLabel1.setText(AppLocal.getIntString("message.paymentgatewaytype")); // NOI18N
        jPanel5.add(jLabel1);

        add(jPanel5, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        m_jHolderName.setText((String)jComboBox1.getSelectedItem());
        validatePayment();
    }//GEN-LAST:event_jComboBox1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private com.openbravo.editor.JEditorStringNumber m_jCardNumber;
    private com.openbravo.editor.JEditorStringNumber m_jExpirationDate;
    private com.openbravo.editor.JEditorString m_jHolderName;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private com.openbravo.editor.JEditorCurrencyPositive m_jMoneyEuros;
    // End of variables declaration//GEN-END:variables
    
}
