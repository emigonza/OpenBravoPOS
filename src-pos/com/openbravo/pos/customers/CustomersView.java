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

package com.openbravo.pos.customers;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.util.StringUtils;
import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author  adrianromero
 */
public class CustomersView extends javax.swing.JPanel implements EditorRecord {

    private Object m_oId;
    
    private SentenceList m_sentcat;
    private ComboBoxValModel m_CategoryModel;
    
    private DirtyManager m_Dirty;
    private DataLogicCustomers dlCustomers;
    
    private Map<String,String> countriesMap;
    private Map<String,String> regionsMap;
    private Map<String,String> citiesMap;
    private boolean            isActiveEvent = true;
    private String organization;
        
    /** Creates new form CustomersView */
    public CustomersView(AppView app, DirtyManager dirty) {
        
        DataLogicSales dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        dlCustomers = (DataLogicCustomers) app.getBean("com.openbravo.pos.customers.DataLogicCustomers");
        
        initComponents();
        initLabels();
        fillCountriesList();
        txtCountry.setVisible(false);
        txtRegion.setVisible(false);
        txtCity.setVisible(false);
        txtAddress2.setVisible(false);
        jLabel9.setVisible(false);
        m_jCategory.setVisible(false);
        
        m_sentcat = dlSales.getTaxCustCategoriesList();
        m_CategoryModel = new ComboBoxValModel();
        
        m_Dirty = dirty;
        m_jTaxID.getDocument().addDocumentListener(dirty);
        m_jSearchkey.getDocument().addDocumentListener(dirty);
        m_jName.getDocument().addDocumentListener(dirty);
        m_jCategory.addActionListener(dirty);
        m_jNotes.getDocument().addDocumentListener(dirty);
        txtMaxdebt.getDocument().addDocumentListener(dirty);
        m_jVisible.addActionListener(dirty);
        m_jExempt.addActionListener(dirty);
        
        txtFirstName.getDocument().addDocumentListener(dirty);
        txtLastName.getDocument().addDocumentListener(dirty);
        txtEmail.getDocument().addDocumentListener(dirty);
        txtPhone.getDocument().addDocumentListener(dirty);
        txtPhone2.getDocument().addDocumentListener(dirty);
        txtFax.getDocument().addDocumentListener(dirty);
        
        txtAddress.getDocument().addDocumentListener(dirty);
        txtAddress2.getDocument().addDocumentListener(dirty);
        txtPostal.getDocument().addDocumentListener(dirty);
        txtCity.getDocument().addDocumentListener(dirty);
        txtRegion.getDocument().addDocumentListener(dirty);
        txtCountry.getDocument().addDocumentListener(dirty);
        
        writeValueEOF(); 
    }
    
    public void activate() throws BasicException {
        
        List a = m_sentcat.list();
        a.add(0, null); // The null item
        m_CategoryModel = new ComboBoxValModel(a);
        m_jCategory.setModel(m_CategoryModel);         
    }
    
    public void refresh() {
    }
    
    public void writeValueEOF() {
        m_oId = null;
        m_jTaxID.setText(null);
        m_jSearchkey.setText(null);
        m_jName.setText(null);
        m_CategoryModel.setSelectedKey(null);
        m_jNotes.setText(null);
        txtMaxdebt.setText(null);
        txtCurdebt.setText(null);
        txtCurdate.setText(null);
        m_jVisible.setSelected(false);
        m_jExempt.setSelected(false);
        jcard.setText(null);
        
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtEmail.setText(null);
        txtPhone.setText(null);
        txtPhone2.setText(null);
        txtFax.setText(null);
       
        txtAddress.setText(null);
        txtAddress2.setText(null);
        txtPostal.setText(null);
        txtCity.setText(null);
        txtRegion.setText(null);
        txtCountry.setText(null);
        
        m_jTaxID.setEnabled(false);
        m_jSearchkey.setEnabled(false);
        m_jName.setEnabled(false);
        m_jCategory.setEnabled(false);
        m_jNotes.setEnabled(false);
        txtMaxdebt.setEnabled(false);
        txtCurdebt.setEnabled(false);
        txtCurdate.setEnabled(false);
        m_jVisible.setEnabled(false);
        m_jExempt.setEnabled(false);
        jcard.setEnabled(false);
        
        txtFirstName.setEnabled(false);
        txtLastName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPhone.setEnabled(false);
        txtPhone2.setEnabled(false);
        txtFax.setEnabled(false);
       
        txtAddress.setEnabled(false);
        txtAddress2.setEnabled(false);
        txtPostal.setEnabled(false);
        txtCity.setEnabled(false);
        txtRegion.setEnabled(false);
        txtCountry.setEnabled(false);
        
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    } 
    
    public void writeValueInsert() {
        m_oId = null;
        m_jTaxID.setText(null);
        m_jSearchkey.setText(null);
        m_jName.setText(null);
        m_CategoryModel.setSelectedKey(null);
        m_jNotes.setText(null);
        txtMaxdebt.setText(null);
        txtCurdebt.setText(null);
        txtCurdate.setText(null);        
        m_jVisible.setSelected(false);
        m_jExempt.setSelected(false);
        jcard.setText(null);
        
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtEmail.setText(null);
        txtPhone.setText(null);
        txtPhone2.setText(null);
        txtFax.setText(null);
       
        txtAddress.setText(null);
        txtAddress2.setText(null);
        txtPostal.setText(null);
        txtCity.setText(null);
        txtRegion.setText(null);
        txtCountry.setText(null);
        
        m_jTaxID.setEnabled(true);
        m_jSearchkey.setEnabled(false);
        m_jName.setEnabled(false);
        m_jCategory.setEnabled(true);
        m_jNotes.setEnabled(true);
        txtMaxdebt.setEnabled(true);
        txtCurdebt.setEnabled(true);
        txtCurdate.setEnabled(true);
        m_jVisible.setEnabled(false);
        m_jExempt.setEnabled(true);
        jcard.setEnabled(true);
               
        txtFirstName.setEnabled(true);
        txtLastName.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPhone.setEnabled(true);
        txtPhone2.setEnabled(true);
        txtFax.setEnabled(true);
       
        txtAddress.setEnabled(true);
        txtAddress2.setEnabled(true);
        txtPostal.setEnabled(true);
        txtCity.setEnabled(true);
        txtRegion.setEnabled(true);
        txtCountry.setEnabled(true);
        
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
    }

    public void writeValueDelete(Object value) {
        Object[] customer = (Object[]) value;
        m_oId = customer[0];
        m_jTaxID.setText((String) customer[1]);
        m_jSearchkey.setText((String) customer[2]);
        m_jName.setText((String) customer[3]);
        m_jNotes.setText((String) customer[4]);
        m_jVisible.setSelected(((Boolean) customer[5]).booleanValue());
        jcard.setText((String) customer[6]);
        txtMaxdebt.setText(Formats.CURRENCY.formatValue(customer[7]));
        txtCurdate.setText(Formats.DATE.formatValue(customer[8]));        
        txtCurdebt.setText(Formats.CURRENCY.formatValue(customer[9]));    
        
        txtFirstName.setText(Formats.STRING.formatValue(customer[10]));
        txtLastName.setText(Formats.STRING.formatValue(customer[11]));
        txtEmail.setText(Formats.STRING.formatValue(customer[12]));
        txtPhone.setText(Formats.STRING.formatValue(customer[13]));
        txtPhone2.setText(Formats.STRING.formatValue(customer[14]));
        txtFax.setText(Formats.STRING.formatValue(customer[15]));
       
        txtAddress.setText(Formats.STRING.formatValue(customer[16]));
        txtAddress2.setText(Formats.STRING.formatValue(customer[17]));
        txtPostal.setText(Formats.STRING.formatValue(customer[18]));
        txtCity.setText(Formats.STRING.formatValue(customer[19]));
        txtRegion.setText(Formats.STRING.formatValue(customer[20]));
        txtCountry.setText(Formats.STRING.formatValue(customer[21]));      
        
        m_CategoryModel.setSelectedKey(customer[22]);
        
        m_jTaxID.setEnabled(false);
        m_jSearchkey.setEnabled(false);
        m_jName.setEnabled(false);
        m_jNotes.setEnabled(false);
        txtMaxdebt.setEnabled(false);
        txtCurdebt.setEnabled(false);
        txtCurdate.setEnabled(false);
        m_jVisible.setEnabled(false);
        m_jExempt.setEnabled(false);
        jcard.setEnabled(false);       
        
        txtFirstName.setEnabled(false);
        txtLastName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtPhone.setEnabled(false);
        txtPhone2.setEnabled(false);
        txtFax.setEnabled(false);
       
        txtAddress.setEnabled(false);
        txtAddress2.setEnabled(false);
        txtPostal.setEnabled(false);
        txtCity.setEnabled(false);
        txtRegion.setEnabled(false);
        txtCountry.setEnabled(false);
        
        m_jCategory.setEnabled(false);
        
        jButton2.setEnabled(false);
        jButton3.setEnabled(false);
    }

    public void writeValueEdit(Object value) {
        Object[] customer = (Object[]) value;
        m_oId = customer[0];
        m_jTaxID.setText((String) customer[1]);
        m_jSearchkey.setText((String) customer[2]);
        m_jName.setText((String) customer[3]);
        m_jNotes.setText((String) customer[4]);
        m_jVisible.setSelected(((Boolean) customer[5]).booleanValue());
        jcard.setText((String) customer[6]);
        txtMaxdebt.setText(Formats.CURRENCY.formatValue(customer[7]));
        txtCurdate.setText(Formats.DATE.formatValue(customer[8]));        
        txtCurdebt.setText(Formats.CURRENCY.formatValue(customer[9]));    
        
        txtFirstName.setText(Formats.STRING.formatValue(customer[10]));
        txtLastName.setText(Formats.STRING.formatValue(customer[11]));
        txtEmail.setText(Formats.STRING.formatValue(customer[12]));
        txtPhone.setText(Formats.STRING.formatValue(customer[13]));
        txtPhone2.setText(Formats.STRING.formatValue(customer[14]));
        txtFax.setText(Formats.STRING.formatValue(customer[15]));
       
        txtAddress.setText(Formats.STRING.formatValue(customer[16]));
        txtAddress2.setText(Formats.STRING.formatValue(customer[17]));
        txtPostal.setText(Formats.STRING.formatValue(customer[18]));
System.out.println("Localisacion: pais:" +Formats.STRING.formatValue(customer[21])+ " Region:"+Formats.STRING.formatValue(customer[20])+" Ciudad:"+Formats.STRING.formatValue(customer[19]));
        isActiveEvent = false;
        txtCountry.setText(Formats.STRING.formatValue(customer[21]));
        setCountriesList();
        txtRegion.setText(Formats.STRING.formatValue(customer[20]));
        setRegionsList();
        txtCity.setText(Formats.STRING.formatValue(customer[19]));
        setCities();
        isActiveEvent = true;
        
        m_CategoryModel.setSelectedKey(customer[22]);
        m_jExempt.setSelected(((Boolean) customer[23]).booleanValue());
        
        m_jTaxID.setEnabled(true);
        m_jSearchkey.setEnabled(false);
        m_jName.setEnabled(false);
        m_jNotes.setEnabled(true);
        txtMaxdebt.setEnabled(true);
        txtCurdebt.setEnabled(true);
        txtCurdate.setEnabled(true);
        m_jVisible.setEnabled(false);
        m_jExempt.setEnabled(true);
        jcard.setEnabled(true);
               
        txtFirstName.setEnabled(true);
        txtLastName.setEnabled(true);
        txtEmail.setEnabled(true);
        txtPhone.setEnabled(true);
        txtPhone2.setEnabled(true);
        txtFax.setEnabled(true);
       
        txtAddress.setEnabled(true);
        txtAddress2.setEnabled(true);
        txtPostal.setEnabled(true);
        txtCity.setEnabled(true);
        txtRegion.setEnabled(true);
        txtCountry.setEnabled(true);
        
        m_jCategory.setEnabled(true);
        
        jButton2.setEnabled(true);
        jButton3.setEnabled(true);
    }
    
    public Object createValue() throws BasicException {
        Object[] customer = new Object[24];
        customer[0] = m_oId == null ? UUID.randomUUID().toString() : m_oId;
        customer[1] = m_jTaxID.getText();
        customer[2] = m_jSearchkey.getText();
        customer[3] = m_jName.getText();
        customer[4] = m_jNotes.getText();
        customer[5] = Boolean.valueOf(m_jVisible.isSelected());
        customer[6] = Formats.STRING.parseValue(jcard.getText()); // Format to manage NULL values
        customer[7] = Formats.CURRENCY.parseValue(txtMaxdebt.getText(), new Double(0.0));
        customer[8] = Formats.TIMESTAMP.parseValue(txtCurdate.getText()); // not saved
        customer[9] = Formats.CURRENCY.parseValue(txtCurdebt.getText()); // not saved
        
        customer[10] = Formats.STRING.parseValue(txtFirstName.getText());
        customer[11] = Formats.STRING.parseValue(txtLastName.getText());
        customer[12] = Formats.STRING.parseValue(txtEmail.getText());
        customer[13] = Formats.STRING.parseValue(txtPhone.getText());
        customer[14] = Formats.STRING.parseValue(txtPhone2.getText());
        customer[15] = Formats.STRING.parseValue(txtFax.getText());
       
        customer[16] = Formats.STRING.parseValue(txtAddress.getText());
        customer[17] = Formats.STRING.parseValue(txtAddress2.getText());
        customer[18] = Formats.STRING.parseValue(txtPostal.getText());
        customer[19] = Formats.STRING.parseValue(txtCity.getText());
        customer[20] = Formats.STRING.parseValue(txtRegion.getText());
        customer[21] = Formats.STRING.parseValue(txtCountry.getText()); 
        
        customer[22] = m_CategoryModel.getSelectedKey();
        customer[23] = Boolean.valueOf(m_jExempt.isSelected());
        
        return customer;
    }   
    
    public Component getComponent() {
        return this;
    }    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        m_jSearchkey = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        m_jName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        m_jVisible = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jcard = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        m_jCategory = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtMaxdebt = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtLastName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtPhone2 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        m_jTaxID = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtAddress2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtPostal = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtRegion = new javax.swing.JTextField();
        txtCountry = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        m_jNotes = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtCurdebt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCurdate = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        m_jExempt = new javax.swing.JCheckBox();

        jLabel8.setText(AppLocal.getIntString("label.searchkey")); // NOI18N

        jLabel3.setText(AppLocal.getIntString("label.name")); // NOI18N

        jLabel4.setText(AppLocal.getIntString("label.visible")); // NOI18N

        m_jVisible.setEnabled(false);

        jLabel5.setText(AppLocal.getIntString("label.card")); // NOI18N

        jcard.setEditable(false);

        jLabel9.setText(AppLocal.getIntString("label.custtaxcategory")); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/color_line16.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/fileclose.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText(AppLocal.getIntString("label.maxdebt")); // NOI18N

        txtMaxdebt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel14.setText(AppLocal.getIntString("label.fax")); // NOI18N

        jLabel15.setText(AppLocal.getIntString("label.lastname")); // NOI18N

        txtLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLastNameKeyReleased(evt);
            }
        });

        jLabel16.setText(AppLocal.getIntString("label.email")); // NOI18N

        jLabel17.setText(AppLocal.getIntString("label.phone")); // NOI18N

        jLabel18.setText(AppLocal.getIntString("label.phone2")); // NOI18N

        jLabel19.setText(AppLocal.getIntString("label.firstname")); // NOI18N

        txtFirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFirstNameKeyReleased(evt);
            }
        });

        jLabel7.setText(AppLocal.getIntString("label.taxid")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(m_jTaxID, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(m_jTaxID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtPhone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtFax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.contact"), jPanel1); // NOI18N

        jLabel13.setText(AppLocal.getIntString("label.address")); // NOI18N

        jLabel20.setText(AppLocal.getIntString("label.country")); // NOI18N

        jLabel21.setText(AppLocal.getIntString("label.address2")); // NOI18N

        txtAddress2.setEditable(false);

        jLabel22.setText(AppLocal.getIntString("label.postal")); // NOI18N

        jLabel23.setText(AppLocal.getIntString("label.city")); // NOI18N

        jLabel24.setText(AppLocal.getIntString("label.region")); // NOI18N

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, 205, Short.MAX_VALUE)
                                    .addComponent(txtPostal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, 0, 205, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, 0, 205, Short.MAX_VALUE))))
                        .addGap(137, 137, 137))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCity)
                            .addComponent(txtRegion)
                            .addComponent(txtCountry))
                        .addGap(54, 54, 54))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtPostal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.location"), jPanel2); // NOI18N

        jScrollPane1.setViewportView(m_jNotes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(AppLocal.getIntString("label.notes"), jPanel3); // NOI18N

        jLabel2.setText(AppLocal.getIntString("label.curdebt")); // NOI18N

        txtCurdebt.setEditable(false);
        txtCurdebt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setText(AppLocal.getIntString("label.curdate")); // NOI18N

        txtCurdate.setEditable(false);
        txtCurdate.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel10.setText("Tax Exempt");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jSearchkey, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jVisible, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jExempt, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(163, 163, 163))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcard, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(m_jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaxdebt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCurdebt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCurdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(m_jSearchkey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(m_jName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jcard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(m_jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel10)
                        .addComponent(m_jExempt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jVisible, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaxdebt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCurdebt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCurdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.cardnew"), AppLocal.getIntString("title.editor"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {            
            jcard.setText("c" + StringUtils.getCardNumber());
            m_Dirty.setDirty(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.cardremove"), AppLocal.getIntString("title.editor"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            jcard.setText(null);
            m_Dirty.setDirty(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if(isActiveEvent){
            txtCountry.setText((String)jComboBox1.getSelectedItem());
            this.setRegionsList();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        if(isActiveEvent){
            txtRegion.setText((String)jComboBox2.getSelectedItem());
            this.setCities();
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        if(isActiveEvent){
            txtCity.setText((String)jComboBox3.getSelectedItem());
        }
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void txtFirstNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFirstNameKeyReleased
        setName();
    }//GEN-LAST:event_txtFirstNameKeyReleased

    private void txtLastNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLastNameKeyReleased
        setName();
    }//GEN-LAST:event_txtLastNameKeyReleased
    
    private void setName(){
        m_jName.setText(txtLastName.getText() +" "+ txtFirstName.getText());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jcard;
    private javax.swing.JComboBox m_jCategory;
    private javax.swing.JCheckBox m_jExempt;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextArea m_jNotes;
    private javax.swing.JTextField m_jSearchkey;
    private javax.swing.JTextField m_jTaxID;
    private javax.swing.JCheckBox m_jVisible;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtAddress2;
    private javax.swing.JTextField txtCity;
    private javax.swing.JTextField txtCountry;
    private javax.swing.JTextField txtCurdate;
    private javax.swing.JTextField txtCurdebt;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMaxdebt;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPhone2;
    private javax.swing.JTextField txtPostal;
    private javax.swing.JTextField txtRegion;
    // End of variables declaration//GEN-END:variables

    public String getXML() {
        String xml = "";
        Integer id = null;
        try {
            xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            xml += "<entityDetail>";
            xml += "<type>CUSTOMER_POS</type>";
            xml += "<id>";

            try{
                id = new Integer((String)m_oId);
                xml += m_oId;
            }catch(Exception e){
                xml += "null";
            }
            xml += "</id>";
            xml += "<taxID>" + m_jTaxID.getText() +" </taxID>";
            xml +="<organization>"+organization+"</organization>";
            xml += "<searchkey>" +m_jSearchkey.getText() +" </searchkey>";
            xml += "<name>" +m_jName.getText()+"</name>";
            xml += "<notes>" + m_jNotes.getText()+" </notes>";
            xml += "<visible>" + Boolean.valueOf(m_jVisible.isSelected()) +" </visible>";
            xml += "<card>" + Formats.STRING.parseValue(jcard.getText()) +" </card>"; // Format to manage NULL values
            xml += "<maxDebt>" + Formats.CURRENCY.parseValue(txtMaxdebt.getText(), new Double(0.0)) +" </maxDebt>";
            xml += "<curdate>" + Formats.TIMESTAMP.parseValue(txtCurdate.getText()) +" </curdate>"; // not saved
            xml += "<curdebt>" + Formats.CURRENCY.parseValue(txtCurdebt.getText()) +" </curdebt>"; // not saved
            
            xml += "<firstName>" + Formats.STRING.parseValue(txtFirstName.getText()) +" </firstName>";
            xml += "<lastName>" + Formats.STRING.parseValue(txtLastName.getText()) +" </lastName>";
            xml += "<email>" + Formats.STRING.parseValue(txtEmail.getText()) +" </email>";
            xml += "<phone>" + Formats.STRING.parseValue(txtPhone.getText()) +" </phone>";
            xml += "<phone2>" + Formats.STRING.parseValue(txtPhone2.getText()) +" </phone2>";
            xml += "<fax>" + Formats.STRING.parseValue(txtFax.getText()) +" </fax>";
           
            xml += "<address>" + Formats.STRING.parseValue(txtAddress.getText()) +" </address>";
            xml += "<address2>" + Formats.STRING.parseValue(txtAddress2.getText()) +" </address2>";
            xml += "<postal>" + Formats.STRING.parseValue(txtPostal.getText()) +" </postal>";
            xml += "<city>" + Formats.STRING.parseValue(citiesMap.get((String)jComboBox3.getSelectedItem())) +" </city>";
            xml += "<region>" + Formats.STRING.parseValue(regionsMap.get((String)jComboBox2.getSelectedItem())) +" </region>";
            xml += "<country>" + Formats.STRING.parseValue(countriesMap.get((String)jComboBox1.getSelectedItem())) +" </country>"; 
            
            xml += "<categoryModel>"+m_CategoryModel.getSelectedKey()+"</categoryModel>";
            xml += "<taxExempt>"+m_jExempt.isSelected()+"</taxExempt>";
            xml += "</entityDetail>";
            
            
        System.out.println("CP:CV:843" +xml);
        } catch (BasicException ex) {
            Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return xml;
        
        
    }

    private void fillCountriesList() {
        countriesMap = new HashMap<String,String>();
        List<CountryInfo> countryInfosL = dlCustomers.getCountrys();
        Iterator<CountryInfo> countryInfos  = countryInfosL.iterator();
        CountryInfo countryInfo = null;
        
        jComboBox1.removeAllItems();
        
        while(countryInfos.hasNext()){
            countryInfo = countryInfos.next();
            countriesMap.put(countryInfo.getName(), countryInfo.getId());
            jComboBox1.addItem(countryInfo.getName());
            if(countryInfo.getName().equalsIgnoreCase(txtCountry.getText())){
                jComboBox1.setSelectedIndex(jComboBox1.getItemCount() -1);
            }
        }
        
        
    }
    
    private void setCountriesList(){
        int index =0;
        for(index =0;index< jComboBox1.getItemCount(); index ++){
            if(((String)jComboBox1.getItemAt(index)).equalsIgnoreCase(txtCountry.getText())){
                jComboBox1.setSelectedIndex(index);
                break;
            }
        }
    }

    private void setRegionsList() {
        regionsMap = new HashMap<String,String>();
        Iterator<RegionInfo> iterator = dlCustomers.getRegions(countriesMap.get((String)jComboBox1.getSelectedItem())).iterator();
        RegionInfo regionInfo = null;
        jComboBox2.removeAllItems();
System.out.println("txtRegion"+txtRegion.getText());
        
        while(iterator.hasNext()){
            regionInfo = iterator.next();
            regionsMap.put(regionInfo.getName(), regionInfo.getId());
            jComboBox2.addItem(regionInfo.getName());
            if(regionInfo.getName().equalsIgnoreCase(txtRegion.getText())){
                jComboBox2.setSelectedIndex(jComboBox2.getItemCount() -1);
            }
            
        }
    }

    private void setCities() {
        citiesMap = new HashMap<String,String>();
        Iterator<CityInfo> iterator = dlCustomers.getCities(regionsMap.get((String)jComboBox2.getSelectedItem())).iterator();
        CityInfo cityInfo = null;
        jComboBox3.removeAllItems();
        
        while(iterator.hasNext()){
            cityInfo = iterator.next();
            citiesMap.put(cityInfo.getName(), cityInfo.getId());
            jComboBox3.addItem(cityInfo.getName());
            if(cityInfo.getName().equalsIgnoreCase(txtCity.getText())){
                jComboBox3.setSelectedIndex(jComboBox3.getItemCount() -1);
            }
            
        }
    }
    
    public boolean validateTaxId(){
        if(m_jTaxID.getText() == null || m_jTaxID.getText().trim().equals(""))
            return false;
        else{
            try {
                String id = (String)m_oId;
                if(id == null)
                    id="";
                String customerId = dlCustomers.getCustomerIdWithTaxId(m_jTaxID.getText());
                if(customerId == null)
                    customerId = id;
                if(customerId.trim().equals(id))
                    return true;
                else
                    return false;
            } catch (BasicException ex) {
                Logger.getLogger(CustomersView.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
            
    }
    
    public void setOrganization(String organization){
        this.organization = organization;
    }
    
    public JTextField getSearchkey(){
        return m_jSearchkey;
    }

    private void initLabels() {
        jLabel10.setText(AppLocal.getIntString("lable.taxexempt"));
    }
    
}
