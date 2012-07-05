/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.smj.beans;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author carlos
 */
public class RenderJCombobox extends JLabel implements ListCellRenderer {
    /** Para guardar los iconos por cada item. El item es la clave y el valor
     * es el icono.
     */
    HashMap<Object, ImageIcon> icon = null;

    /**
     * Construye el Hashtable con los iconos seleccionados, asociándolos a los
     * items que tendrá el JComboBox "uno", "dos", "tres" y "cuatro"
     */
    public RenderJCombobox() {
        icon = new HashMap<Object, ImageIcon>();
    }
    
    public RenderJCombobox(HashMap<Object, ImageIcon> icon) {
        this.icon = icon;
    }

    /**
     * En función del value que se pasa (el item del JComboBox), se pone el icono
     * y se devuelve el JLabel.
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        if (icon.get(value) != null){
            setIcon(icon.get(value));
            setText(""+value);
        }else{
            setIcon(null);
            setText(""+value);
        }
        return this;
    }
}
