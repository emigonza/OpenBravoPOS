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
import javax.swing.JButton;
import com.openbravo.basic.BasicException;
import com.openbravo.data.user.EditorListener;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProviderCreator;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.forms.DataLogicSystem;
import com.openbravo.pos.panels.JPanelTable2;
import com.openbravo.pos.ticket.ProductFilter;

/**
 *
 * @author adrianromero
 * Created on 1 de marzo de 2007, 22:15
 *
 */
public class ProductsPanel extends JPanelTable2 implements EditorListener {

    private ProductsEditor jeditor;
    private ProductFilter jproductfilter;    
    
    private DataLogicSales m_dlSales = null;
    
    /** Creates a new instance of ProductsPanel2 */
    public ProductsPanel() {
    }
    
    protected void init() {   
        
        m_dlSales = (DataLogicSales) app.getBean("com.openbravo.pos.forms.DataLogicSales");
        
        // el panel del filtro
        jproductfilter = new ProductFilter();
        jproductfilter.init(app);
        row = m_dlSales.getProductsRow();
        lpr =  new ListProviderCreator(m_dlSales.getProductCatQBF(), jproductfilter);
//        spr = new SaveProvider(
//            m_dlSales.getProductCatUpdate(),
//            m_dlSales.getProductCatInsert(),
//            m_dlSales.getProductCatDelete());
        spr = createSaveProvider();
        // el panel del editor
        jeditor = new ProductsEditor(m_dlSales, dirty,(DataLogicSystem) app.getBean("com.openbravo.pos.forms.DataLogicSystem"));       
       
//        }catch(Exception e){
//            e.printStackTrace();
//            Writer result = new StringWriter();
//            PrintWriter printWriter = new PrintWriter(result);
//            e.printStackTrace(printWriter);
//            JOptionPane.showMessageDialog(null, result.toString());
//            Logger.getLogger(ProductsEditor.class.getName()).log(Level.SEVERE, null, e);
//        }
    }
    
    /**
     * crea un saveProvider vacio para bloquear los botones de guardar, nuevo y borrar
     * create empty saveProvider for block button save, button new and button delete
     * @return  SaveProvider vacio 
     * @autor Carlos Prieto
     */
    private SaveProvider  createSaveProvider(){

        if(app.getAppUserView().getUser().hasPermission("com.openbravo.pos.inventory.ProductsPanel"))
            return new SaveProvider(null,null,null);
        else{
             return new SaveProvider(
                     m_dlSales.getProductCatUpdate(),
                     m_dlSales.getProductCatInsert(),
                     m_dlSales.getProductCatDelete());
        }
    }
    
    public EditorRecord getEditor() {
        return jeditor;
    }
    
    @Override
    public Component getFilter() {
        return jproductfilter.getComponent();
    }  
    
    @Override
    public Component getToolbarExtras() {
        
        JButton btnScanPal = new JButton();
        btnScanPal.setText("ScanPal");
        btnScanPal.setVisible(app.getDeviceScanner() != null);
        btnScanPal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanPalActionPerformed(evt);
            }
        });      
        
        return btnScanPal;
    }
    
    private void btnScanPalActionPerformed(java.awt.event.ActionEvent evt) {                                           
  
        JDlgUploadProducts.showMessage(this, app.getDeviceScanner(), bd);
    }  
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.Products");
    } 
        
    @Override
    public void activate() throws BasicException {
        
        jeditor.activate(); 
        jproductfilter.activate();
        
        super.activate();
    } 
    
    public void updateValue(Object value) {
    }    
}
