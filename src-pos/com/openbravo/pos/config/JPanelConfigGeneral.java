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
package com.openbravo.pos.config;

import com.openbravo.data.user.DirtyManager;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import com.openbravo.pos.forms.AppConfig;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.util.ReportUtils;
import com.openbravo.pos.util.StringParser;
import java.util.Map;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.api.SubstanceSkin;
import org.jvnet.substance.skin.SkinInfo;

/**
 *
 * @author adrianromero
 */
public class JPanelConfigGeneral extends javax.swing.JPanel implements PanelConfig {

    private DirtyManager dirty = new DirtyManager();

    
    private ParametersConfig printer1printerparams;

    private ParametersConfig printer2printerparams;

    private ParametersConfig printer3printerparams;
    
    private ParametersConfig printer4printerparams;
    
    private ParametersConfig printer5printerparams;
    
    private ParametersConfig printer6printerparams;
     
    private ParametersConfig printer7printerparams;
    
    private ParametersConfig printer8printerparams;
    
    private ParametersConfig printer9printerparams;

    /** Creates new form JPanelConfigGeneral */
    public JPanelConfigGeneral() {

        initComponents();

        String[] printernames = ReportUtils.getPrintNames();

        jtxtMachineHostname.getDocument().addDocumentListener(dirty);
        jcboLAF.addActionListener(dirty);
        jcboMachineScreenmode.addActionListener(dirty);
        jcboTicketsBag.addActionListener(dirty);

        jcboMachineDisplay.addActionListener(dirty);
        jcboConnDisplay.addActionListener(dirty);
        jcboSerialDisplay.addActionListener(dirty);
        m_jtxtJPOSName.getDocument().addDocumentListener(dirty);

        jcboMachinePrinter.addActionListener(dirty);
        jcboConnPrinter.addActionListener(dirty);
        jcboSerialPrinter.addActionListener(dirty);
        m_jtxtJPOSPrinter.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer.getDocument().addDocumentListener(dirty);
        
        /**
         * Configuracion para las 9 impresoras - 
         * Settings for 9 pirnters
         */
        printer1printerparams = new ParametersPrinter(printernames);
        printer1printerparams.addDirtyManager(dirty);
        m_jPrinterParams1.add(printer1printerparams.getComponent(), "printer");

        jcboMachinePrinter2.addActionListener(dirty);
        jcboConnPrinter2.addActionListener(dirty);
        jcboSerialPrinter2.addActionListener(dirty);
        m_jtxtJPOSPrinter2.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer2.getDocument().addDocumentListener(dirty);

        printer2printerparams = new ParametersPrinter(printernames);
        printer2printerparams.addDirtyManager(dirty);
        m_jPrinterParams2.add(printer2printerparams.getComponent(), "printer");

        jcboMachinePrinter3.addActionListener(dirty);
        jcboConnPrinter3.addActionListener(dirty);
        jcboSerialPrinter3.addActionListener(dirty);
        m_jtxtJPOSPrinter3.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer3.getDocument().addDocumentListener(dirty);

        printer3printerparams = new ParametersPrinter(printernames);
        printer3printerparams.addDirtyManager(dirty);
        m_jPrinterParams3.add(printer3printerparams.getComponent(), "printer");
        
        
        jcboMachinePrinter4.addActionListener(dirty);
        jcboConnPrinter4.addActionListener(dirty);
        jcboSerialPrinter4.addActionListener(dirty);
        m_jtxtJPOSPrinter4.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer4.getDocument().addDocumentListener(dirty);

        printer4printerparams = new ParametersPrinter(printernames);
        printer4printerparams.addDirtyManager(dirty);
        m_jPrinterParams4.add(printer4printerparams.getComponent(), "printer");
        
        jcboMachinePrinter5.addActionListener(dirty);
        jcboConnPrinter5.addActionListener(dirty);
        jcboSerialPrinter5.addActionListener(dirty);
        m_jtxtJPOSPrinter5.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer5.getDocument().addDocumentListener(dirty);

        printer5printerparams = new ParametersPrinter(printernames);
        printer5printerparams.addDirtyManager(dirty);
        m_jPrinterParams5.add(printer5printerparams.getComponent(), "printer");
                
                
        jcboMachinePrinter6.addActionListener(dirty);
        jcboConnPrinter6.addActionListener(dirty);
        jcboSerialPrinter6.addActionListener(dirty);
        m_jtxtJPOSPrinter6.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer6.getDocument().addDocumentListener(dirty);

        printer6printerparams = new ParametersPrinter(printernames);
        printer6printerparams.addDirtyManager(dirty);
        m_jPrinterParams6.add(printer6printerparams.getComponent(), "printer");
        
        jcboMachinePrinter7.addActionListener(dirty);
        jcboConnPrinter7.addActionListener(dirty);
        jcboSerialPrinter7.addActionListener(dirty);
        m_jtxtJPOSPrinter7.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer7.getDocument().addDocumentListener(dirty);

        printer7printerparams = new ParametersPrinter(printernames);
        printer7printerparams.addDirtyManager(dirty);
        m_jPrinterParams7.add(printer7printerparams.getComponent(), "printer");
        
        jcboMachinePrinter8.addActionListener(dirty);
        jcboConnPrinter8.addActionListener(dirty);
        jcboSerialPrinter8.addActionListener(dirty);
        m_jtxtJPOSPrinter8.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer8.getDocument().addDocumentListener(dirty);

        printer8printerparams = new ParametersPrinter(printernames);
        printer8printerparams.addDirtyManager(dirty);
        m_jPrinterParams8.add(printer8printerparams.getComponent(), "printer");
        
        jcboMachinePrinter9.addActionListener(dirty);
        jcboConnPrinter9.addActionListener(dirty);
        jcboSerialPrinter9.addActionListener(dirty);
        m_jtxtJPOSPrinter9.getDocument().addDocumentListener(dirty);
        m_jtxtJPOSDrawer9.getDocument().addDocumentListener(dirty);

        printer9printerparams = new ParametersPrinter(printernames);
        printer9printerparams.addDirtyManager(dirty);
        m_jPrinterParams9.add(printer9printerparams.getComponent(), "printer");

        jcboMachineScale.addActionListener(dirty);
        jcboSerialScale.addActionListener(dirty);

        jcboMachineScanner.addActionListener(dirty);
        jcboSerialScanner.addActionListener(dirty);

        cboPrinters.addActionListener(dirty);

//        // Openbravo Skin
//        jcboLAF.addItem(new UIManager.LookAndFeelInfo("Openbravo", "com.openbravo.pos.skin.OpenbravoLookAndFeel"));

        // Installed skins
        LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
        for (int i = 0; i < lafs.length; i++) {
            jcboLAF.addItem(new LAFInfo(lafs[i].getName(), lafs[i].getClassName()));
        }

        // Substance skins
        // new SubstanceLookAndFeel(); // TODO: Remove in Substance 5.0. Workaround for Substance 4.3 to initialize static variables
        Map<String, SkinInfo> skins = SubstanceLookAndFeel.getAllSkins();
        for (SkinInfo skin : skins.values()) {
            jcboLAF.addItem(new LAFInfo(skin.getDisplayName(), skin.getClassName()));
        }

        jcboLAF.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeLAF();
            }
        });

        jcboMachineScreenmode.addItem("window");
        jcboMachineScreenmode.addItem("fullscreen");

        jcboTicketsBag.addItem("simple");
        jcboTicketsBag.addItem("standard");
        jcboTicketsBag.addItem("restaurant");

        // Printer 1
        jcboMachinePrinter.addItem("screen");
        jcboMachinePrinter.addItem("printer");
        jcboMachinePrinter.addItem("epson");
        jcboMachinePrinter.addItem("tmu220");
        jcboMachinePrinter.addItem("star");
        jcboMachinePrinter.addItem("ithaca");
        jcboMachinePrinter.addItem("surepos");
        jcboMachinePrinter.addItem("plain");
        jcboMachinePrinter.addItem("javapos");
        jcboMachinePrinter.addItem("Not defined");

        jcboConnPrinter.addItem("serial");
        jcboConnPrinter.addItem("file");

        jcboSerialPrinter.addItem("COM1");
        jcboSerialPrinter.addItem("COM2");
        jcboSerialPrinter.addItem("COM3");
        jcboSerialPrinter.addItem("COM4");
        jcboSerialPrinter.addItem("LPT1");
        jcboSerialPrinter.addItem("/dev/ttyS0");
        jcboSerialPrinter.addItem("/dev/ttyS1");
        jcboSerialPrinter.addItem("/dev/ttyS2");
        jcboSerialPrinter.addItem("/dev/ttyS3");

        // Printer 2        
        jcboMachinePrinter2.addItem("screen");
        jcboMachinePrinter2.addItem("printer");
        jcboMachinePrinter2.addItem("epson");
        jcboMachinePrinter2.addItem("tmu220");
        jcboMachinePrinter2.addItem("star");
        jcboMachinePrinter2.addItem("ithaca");
        jcboMachinePrinter2.addItem("surepos");
        jcboMachinePrinter2.addItem("plain");
        jcboMachinePrinter2.addItem("javapos");
        jcboMachinePrinter2.addItem("Not defined");

        jcboConnPrinter2.addItem("serial");
        jcboConnPrinter2.addItem("file");

        jcboSerialPrinter2.addItem("COM1");
        jcboSerialPrinter2.addItem("COM2");
        jcboSerialPrinter2.addItem("COM3");
        jcboSerialPrinter2.addItem("COM4");
        jcboSerialPrinter2.addItem("LPT1");
        jcboSerialPrinter2.addItem("/dev/ttyS0");
        jcboSerialPrinter2.addItem("/dev/ttyS1");
        jcboSerialPrinter2.addItem("/dev/ttyS2");
        jcboSerialPrinter2.addItem("/dev/ttyS3");

        // Printer 3
        jcboMachinePrinter3.addItem("screen");
        jcboMachinePrinter3.addItem("printer");
        jcboMachinePrinter3.addItem("epson");
        jcboMachinePrinter3.addItem("tmu220");
        jcboMachinePrinter3.addItem("star");
        jcboMachinePrinter3.addItem("ithaca");
        jcboMachinePrinter3.addItem("surepos");
        jcboMachinePrinter3.addItem("plain");
        jcboMachinePrinter3.addItem("javapos");
        jcboMachinePrinter3.addItem("Not defined");

        jcboConnPrinter3.addItem("serial");
        jcboConnPrinter3.addItem("file");

        jcboSerialPrinter3.addItem("COM1");
        jcboSerialPrinter3.addItem("COM2");
        jcboSerialPrinter3.addItem("COM3");
        jcboSerialPrinter3.addItem("COM4");
        jcboSerialPrinter3.addItem("LPT1");
        jcboSerialPrinter3.addItem("/dev/ttyS0");
        jcboSerialPrinter3.addItem("/dev/ttyS1");
        jcboSerialPrinter3.addItem("/dev/ttyS2");
        jcboSerialPrinter3.addItem("/dev/ttyS3");
        
        
        
        // Printer 4
        jcboMachinePrinter4.addItem("screen");
        jcboMachinePrinter4.addItem("printer");
        jcboMachinePrinter4.addItem("epson");
        jcboMachinePrinter4.addItem("tmu220");
        jcboMachinePrinter4.addItem("star");
        jcboMachinePrinter4.addItem("ithaca");
        jcboMachinePrinter4.addItem("surepos");
        jcboMachinePrinter4.addItem("plain");
        jcboMachinePrinter4.addItem("javapos");
        jcboMachinePrinter4.addItem("Not defined");

        jcboConnPrinter4.addItem("serial");
        jcboConnPrinter4.addItem("file");

        jcboSerialPrinter4.addItem("COM1");
        jcboSerialPrinter4.addItem("COM2");
        jcboSerialPrinter4.addItem("COM3");
        jcboSerialPrinter4.addItem("COM4");
        jcboSerialPrinter4.addItem("LPT1");
        jcboSerialPrinter4.addItem("/dev/ttyS0");
        jcboSerialPrinter4.addItem("/dev/ttyS1");
        jcboSerialPrinter4.addItem("/dev/ttyS2");
        jcboSerialPrinter4.addItem("/dev/ttyS3");
        
        
        // Printer 5
        jcboMachinePrinter5.addItem("screen");
        jcboMachinePrinter5.addItem("printer");
        jcboMachinePrinter5.addItem("epson");
        jcboMachinePrinter5.addItem("tmu220");
        jcboMachinePrinter5.addItem("star");
        jcboMachinePrinter5.addItem("ithaca");
        jcboMachinePrinter5.addItem("surepos");
        jcboMachinePrinter5.addItem("plain");
        jcboMachinePrinter5.addItem("javapos");
        jcboMachinePrinter5.addItem("Not defined");

        jcboConnPrinter5.addItem("serial");
        jcboConnPrinter5.addItem("file");

        jcboSerialPrinter5.addItem("COM1");
        jcboSerialPrinter5.addItem("COM2");
        jcboSerialPrinter5.addItem("COM3");
        jcboSerialPrinter5.addItem("COM4");
        jcboSerialPrinter5.addItem("LPT1");
        jcboSerialPrinter5.addItem("/dev/ttyS0");
        jcboSerialPrinter5.addItem("/dev/ttyS1");
        jcboSerialPrinter5.addItem("/dev/ttyS2");
        jcboSerialPrinter5.addItem("/dev/ttyS3");
        
        
        // Printer 6
        jcboMachinePrinter6.addItem("screen");
        jcboMachinePrinter6.addItem("printer");
        jcboMachinePrinter6.addItem("epson");
        jcboMachinePrinter6.addItem("tmu220");
        jcboMachinePrinter6.addItem("star");
        jcboMachinePrinter6.addItem("ithaca");
        jcboMachinePrinter6.addItem("surepos");
        jcboMachinePrinter6.addItem("plain");
        jcboMachinePrinter6.addItem("javapos");
        jcboMachinePrinter6.addItem("Not defined");

        jcboConnPrinter6.addItem("serial");
        jcboConnPrinter6.addItem("file");

        jcboSerialPrinter6.addItem("COM1");
        jcboSerialPrinter6.addItem("COM2");
        jcboSerialPrinter6.addItem("COM3");
        jcboSerialPrinter6.addItem("COM4");
        jcboSerialPrinter6.addItem("LPT1");
        jcboSerialPrinter6.addItem("/dev/ttyS0");
        jcboSerialPrinter6.addItem("/dev/ttyS1");
        jcboSerialPrinter6.addItem("/dev/ttyS2");
        jcboSerialPrinter6.addItem("/dev/ttyS3");
        
        // Printer 7
        jcboMachinePrinter7.addItem("screen");
        jcboMachinePrinter7.addItem("printer");
        jcboMachinePrinter7.addItem("epson");
        jcboMachinePrinter7.addItem("tmu220");
        jcboMachinePrinter7.addItem("star");
        jcboMachinePrinter7.addItem("ithaca");
        jcboMachinePrinter7.addItem("surepos");
        jcboMachinePrinter7.addItem("plain");
        jcboMachinePrinter7.addItem("javapos");
        jcboMachinePrinter7.addItem("Not defined");

        jcboConnPrinter7.addItem("serial");
        jcboConnPrinter7.addItem("file");

        jcboSerialPrinter7.addItem("COM1");
        jcboSerialPrinter7.addItem("COM2");
        jcboSerialPrinter7.addItem("COM3");
        jcboSerialPrinter7.addItem("COM4");
        jcboSerialPrinter7.addItem("LPT1");
        jcboSerialPrinter7.addItem("/dev/ttyS0");
        jcboSerialPrinter7.addItem("/dev/ttyS1");
        jcboSerialPrinter7.addItem("/dev/ttyS2");
        jcboSerialPrinter7.addItem("/dev/ttyS3");
        
        // Printer 8
        jcboMachinePrinter8.addItem("screen");
        jcboMachinePrinter8.addItem("printer");
        jcboMachinePrinter8.addItem("epson");
        jcboMachinePrinter8.addItem("tmu220");
        jcboMachinePrinter8.addItem("star");
        jcboMachinePrinter8.addItem("ithaca");
        jcboMachinePrinter8.addItem("surepos");
        jcboMachinePrinter8.addItem("plain");
        jcboMachinePrinter8.addItem("javapos");
        jcboMachinePrinter8.addItem("Not defined");

        jcboConnPrinter8.addItem("serial");
        jcboConnPrinter8.addItem("file");

        jcboSerialPrinter8.addItem("COM1");
        jcboSerialPrinter8.addItem("COM2");
        jcboSerialPrinter8.addItem("COM3");
        jcboSerialPrinter8.addItem("COM4");
        jcboSerialPrinter8.addItem("LPT1");
        jcboSerialPrinter8.addItem("/dev/ttyS0");
        jcboSerialPrinter8.addItem("/dev/ttyS1");
        jcboSerialPrinter8.addItem("/dev/ttyS2");
        jcboSerialPrinter8.addItem("/dev/ttyS3");
        
        // Printer 9
        jcboMachinePrinter9.addItem("screen");
        jcboMachinePrinter9.addItem("printer");
        jcboMachinePrinter9.addItem("epson");
        jcboMachinePrinter9.addItem("tmu220");
        jcboMachinePrinter9.addItem("star");
        jcboMachinePrinter9.addItem("ithaca");
        jcboMachinePrinter9.addItem("surepos");
        jcboMachinePrinter9.addItem("plain");
        jcboMachinePrinter9.addItem("javapos");
        jcboMachinePrinter9.addItem("Not defined");

        jcboConnPrinter9.addItem("serial");
        jcboConnPrinter9.addItem("file");

        jcboSerialPrinter9.addItem("COM1");
        jcboSerialPrinter9.addItem("COM2");
        jcboSerialPrinter9.addItem("COM3");
        jcboSerialPrinter9.addItem("COM4");
        jcboSerialPrinter9.addItem("LPT1");
        jcboSerialPrinter9.addItem("/dev/ttyS0");
        jcboSerialPrinter9.addItem("/dev/ttyS1");
        jcboSerialPrinter9.addItem("/dev/ttyS2");
        jcboSerialPrinter9.addItem("/dev/ttyS3");

        // Display
        jcboMachineDisplay.addItem("screen");
        jcboMachineDisplay.addItem("window");
        jcboMachineDisplay.addItem("javapos");
        jcboMachineDisplay.addItem("epson");
        jcboMachineDisplay.addItem("ld200");
        jcboMachineDisplay.addItem("surepos");
        jcboMachineDisplay.addItem("Not defined");

        jcboConnDisplay.addItem("serial");
        jcboConnDisplay.addItem("file");

        jcboSerialDisplay.addItem("COM1");
        jcboSerialDisplay.addItem("COM2");
        jcboSerialDisplay.addItem("COM3");
        jcboSerialDisplay.addItem("COM4");
        jcboSerialDisplay.addItem("LPT1");
        jcboSerialDisplay.addItem("/dev/ttyS0");
        jcboSerialDisplay.addItem("/dev/ttyS1");
        jcboSerialDisplay.addItem("/dev/ttyS2");
        jcboSerialDisplay.addItem("/dev/ttyS3");

        // Scale
        jcboMachineScale.addItem("screen");
        jcboMachineScale.addItem("dialog1");
        jcboMachineScale.addItem("samsungesp");
        jcboMachineScale.addItem("Not defined");

        jcboSerialScale.addItem("COM1");
        jcboSerialScale.addItem("COM2");
        jcboSerialScale.addItem("COM3");
        jcboSerialScale.addItem("COM4");
        jcboSerialScale.addItem("/dev/ttyS0");
        jcboSerialScale.addItem("/dev/ttyS1");
        jcboSerialScale.addItem("/dev/ttyS2");
        jcboSerialScale.addItem("/dev/ttyS3");

        // Scanner
        jcboMachineScanner.addItem("scanpal2");
        jcboMachineScanner.addItem("Not defined");

        jcboSerialScanner.addItem("COM1");
        jcboSerialScanner.addItem("COM2");
        jcboSerialScanner.addItem("COM3");
        jcboSerialScanner.addItem("COM4");
        jcboSerialScanner.addItem("/dev/ttyS0");
        jcboSerialScanner.addItem("/dev/ttyS1");
        jcboSerialScanner.addItem("/dev/ttyS2");
        jcboSerialScanner.addItem("/dev/ttyS3");

        // Printers
        cboPrinters.addItem("(Default)");
        cboPrinters.addItem("(Show dialog)");
        for (String name : printernames) {
            cboPrinters.addItem(name);
        }
    }

    public boolean hasChanged() {
        return dirty.isDirty();
    }

    public Component getConfigComponent() {
        return this;
    }

    public void loadProperties(AppConfig config) {

        jtxtMachineHostname.setText(config.getProperty("machine.hostname"));

        String lafclass = config.getProperty("swing.defaultlaf");
        jcboLAF.setSelectedItem(null);
        for (int i = 0; i < jcboLAF.getItemCount(); i++) {
            LAFInfo lafinfo = (LAFInfo) jcboLAF.getItemAt(i);
            if (lafinfo.getClassName().equals(lafclass)) {
                jcboLAF.setSelectedIndex(i);
                break;
            }
        }
        // jcboLAF.setSelectedItem(new LookAndFeelInfo());

        jcboMachineScreenmode.setSelectedItem(config.getProperty("machine.screenmode"));
        jcboTicketsBag.setSelectedItem(config.getProperty("machine.ticketsbag"));

        StringParser p = new StringParser(config.getProperty("machine.printer"));
        String sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter.setSelectedItem("epson");
            jcboConnPrinter.setSelectedItem(sparam);
            jcboSerialPrinter.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter.setSelectedItem(sparam);
            m_jtxtJPOSPrinter.setText(p.nextToken(','));
            m_jtxtJPOSDrawer.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter.setSelectedItem(sparam);
            printer1printerparams.setParameters(p);
        } else {
            jcboMachinePrinter.setSelectedItem(sparam);
            jcboConnPrinter.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.printer.2"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter2.setSelectedItem("epson");
            jcboConnPrinter2.setSelectedItem(sparam);
            jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter2.setSelectedItem(sparam);
            m_jtxtJPOSPrinter2.setText(p.nextToken(','));
            m_jtxtJPOSDrawer2.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter2.setSelectedItem(sparam);
            printer2printerparams.setParameters(p);
        } else {
            jcboMachinePrinter2.setSelectedItem(sparam);
            jcboConnPrinter2.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.printer.3"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter3.setSelectedItem("epson");
            jcboConnPrinter3.setSelectedItem(sparam);
            jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter3.setSelectedItem(sparam);
            m_jtxtJPOSPrinter3.setText(p.nextToken(','));
            m_jtxtJPOSDrawer3.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter3.setSelectedItem(sparam);
            printer3printerparams.setParameters(p);
        } else {
            jcboMachinePrinter3.setSelectedItem(sparam);
            jcboConnPrinter3.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
        }
        
        
        p = new StringParser(config.getProperty("machine.printer.4"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter4.setSelectedItem("epson");
            jcboConnPrinter4.setSelectedItem(sparam);
            jcboSerialPrinter4.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter4.setSelectedItem(sparam);
            m_jtxtJPOSPrinter4.setText(p.nextToken(','));
            m_jtxtJPOSDrawer4.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter4.setSelectedItem(sparam);
            printer4printerparams.setParameters(p);
        } else {
            jcboMachinePrinter4.setSelectedItem(sparam);
            jcboConnPrinter4.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter4.setSelectedItem(p.nextToken(','));
        }
        
        p = new StringParser(config.getProperty("machine.printer.5"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter5.setSelectedItem("epson");
            jcboConnPrinter5.setSelectedItem(sparam);
            jcboSerialPrinter5.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter5.setSelectedItem(sparam);
            m_jtxtJPOSPrinter5.setText(p.nextToken(','));
            m_jtxtJPOSDrawer5.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter5.setSelectedItem(sparam);

            printer5printerparams.setParameters(p);
        } else {
            jcboMachinePrinter5.setSelectedItem(sparam);
            jcboConnPrinter5.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter5.setSelectedItem(p.nextToken(','));
        }
        
        p = new StringParser(config.getProperty("machine.printer.6"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter6.setSelectedItem("epson");
            jcboConnPrinter6.setSelectedItem(sparam);
            jcboSerialPrinter6.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter6.setSelectedItem(sparam);
            m_jtxtJPOSPrinter6.setText(p.nextToken(','));
            m_jtxtJPOSDrawer6.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter6.setSelectedItem(sparam);
            printer6printerparams.setParameters(p);
        } else {
            jcboMachinePrinter6.setSelectedItem(sparam);
            jcboConnPrinter6.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter6.setSelectedItem(p.nextToken(','));
        }
        
        p = new StringParser(config.getProperty("machine.printer.7"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter7.setSelectedItem("epson");
            jcboConnPrinter7.setSelectedItem(sparam);
            jcboSerialPrinter7.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter7.setSelectedItem(sparam);
            m_jtxtJPOSPrinter7.setText(p.nextToken(','));
            m_jtxtJPOSDrawer7.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter7.setSelectedItem(sparam);
            printer7printerparams.setParameters(p);
        } else {
            jcboMachinePrinter7.setSelectedItem(sparam);
            jcboConnPrinter7.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter7.setSelectedItem(p.nextToken(','));
        }
        
        p = new StringParser(config.getProperty("machine.printer.8"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter8.setSelectedItem("epson");
            jcboConnPrinter8.setSelectedItem(sparam);
            jcboSerialPrinter8.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter8.setSelectedItem(sparam);
            m_jtxtJPOSPrinter8.setText(p.nextToken(','));
            m_jtxtJPOSDrawer8.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter8.setSelectedItem(sparam);
            printer8printerparams.setParameters(p);
        } else {
            jcboMachinePrinter8.setSelectedItem(sparam);
            jcboConnPrinter8.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter8.setSelectedItem(p.nextToken(','));
        }
        
        p = new StringParser(config.getProperty("machine.printer.9"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter9.setSelectedItem("epson");
            jcboConnPrinter9.setSelectedItem(sparam);
            jcboSerialPrinter9.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachinePrinter9.setSelectedItem(sparam);
            m_jtxtJPOSPrinter9.setText(p.nextToken(','));
            m_jtxtJPOSDrawer9.setText(p.nextToken(','));
        } else if ("printer".equals(sparam)) {
            jcboMachinePrinter9.setSelectedItem(sparam);
            printer9printerparams.setParameters(p);
        } else {
            jcboMachinePrinter9.setSelectedItem(sparam);
            jcboConnPrinter9.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialPrinter9.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.display"));
        sparam = unifySerialInterface(p.nextToken(':'));
        if ("serial".equals(sparam) || "file".equals(sparam)) {
            jcboMachineDisplay.setSelectedItem("epson");
            jcboConnDisplay.setSelectedItem(sparam);
            jcboSerialDisplay.setSelectedItem(p.nextToken(','));
        } else if ("javapos".equals(sparam)) {
            jcboMachineDisplay.setSelectedItem(sparam);
            m_jtxtJPOSName.setText(p.nextToken(','));
        } else {
            jcboMachineDisplay.setSelectedItem(sparam);
            jcboConnDisplay.setSelectedItem(unifySerialInterface(p.nextToken(',')));
            jcboSerialDisplay.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.scale"));
        sparam = p.nextToken(':');
        jcboMachineScale.setSelectedItem(sparam);
        if ("dialog1".equals(sparam) || "samsungesp".equals(sparam)) {
            jcboSerialScale.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.scanner"));
        sparam = p.nextToken(':');
        jcboMachineScanner.setSelectedItem(sparam);
        if ("scanpal2".equals(sparam)) {
            jcboSerialScanner.setSelectedItem(p.nextToken(','));
        }

        cboPrinters.setSelectedItem(config.getProperty("machine.printername"));

        dirty.setDirty(false);
    }

    public void saveProperties(AppConfig config) {

        config.setProperty("machine.hostname", jtxtMachineHostname.getText());

        LAFInfo laf = (LAFInfo) jcboLAF.getSelectedItem();
        config.setProperty("swing.defaultlaf", laf == null
                ? System.getProperty("swing.defaultlaf", "javax.swing.plaf.metal.MetalLookAndFeel")
                : laf.getClassName());

        config.setProperty("machine.screenmode", comboValue(jcboMachineScreenmode.getSelectedItem()));
        config.setProperty("machine.ticketsbag", comboValue(jcboTicketsBag.getSelectedItem()));

        String sMachinePrinter = comboValue(jcboMachinePrinter.getSelectedItem());
        if ("epson".equals(sMachinePrinter) || "tmu220".equals(sMachinePrinter) || "star".equals(sMachinePrinter) || "ithaca".equals(sMachinePrinter) || "surepos".equals(sMachinePrinter)) {
            config.setProperty("machine.printer", sMachinePrinter + ":" + comboValue(jcboConnPrinter.getSelectedItem()) + "," + comboValue(jcboSerialPrinter.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter)) {
            config.setProperty("machine.printer", sMachinePrinter + ":" + m_jtxtJPOSPrinter.getText() + "," + m_jtxtJPOSDrawer.getText());
        } else if ("printer".equals(sMachinePrinter)) {
            config.setProperty("machine.printer", sMachinePrinter + ":" + printer1printerparams.getParameters());
        } else {
            config.setProperty("machine.printer", sMachinePrinter);
        }

        String sMachinePrinter2 = comboValue(jcboMachinePrinter2.getSelectedItem());
        if ("epson".equals(sMachinePrinter2) || "tmu220".equals(sMachinePrinter2) || "star".equals(sMachinePrinter2) || "ithaca".equals(sMachinePrinter2) || "surepos".equals(sMachinePrinter2)) {
            config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + comboValue(jcboConnPrinter2.getSelectedItem()) + "," + comboValue(jcboSerialPrinter2.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter2)) {
            config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + m_jtxtJPOSPrinter2.getText() + "," + m_jtxtJPOSDrawer2.getText());
        } else if ("printer".equals(sMachinePrinter2)) {
            config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + printer2printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.2", sMachinePrinter2);
        }


        String sMachinePrinter3 = comboValue(jcboMachinePrinter3.getSelectedItem());
        if ("epson".equals(sMachinePrinter3) || "tmu220".equals(sMachinePrinter3) || "star".equals(sMachinePrinter3) || "ithaca".equals(sMachinePrinter3) || "surepos".equals(sMachinePrinter3)) {
            config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + comboValue(jcboConnPrinter3.getSelectedItem()) + "," + comboValue(jcboSerialPrinter3.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter3)) {
            config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + m_jtxtJPOSPrinter3.getText() + "," + m_jtxtJPOSDrawer3.getText());
        } else if ("printer".equals(sMachinePrinter3)) {
            config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + printer3printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.3", sMachinePrinter3);
        }
        
        String sMachinePrinter4 = comboValue(jcboMachinePrinter4.getSelectedItem());
        if ("epson".equals(sMachinePrinter4) || "tmu220".equals(sMachinePrinter4) || "star".equals(sMachinePrinter4) || "ithaca".equals(sMachinePrinter4) || "surepos".equals(sMachinePrinter4)) {
            config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + comboValue(jcboConnPrinter4.getSelectedItem()) + "," + comboValue(jcboSerialPrinter4.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter4)) {
            config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + m_jtxtJPOSPrinter4.getText() + "," + m_jtxtJPOSDrawer4.getText());
        } else if ("printer".equals(sMachinePrinter4)) {
            config.setProperty("machine.printer.4", sMachinePrinter4 + ":" + printer4printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.4", sMachinePrinter4);
        }
        
        String sMachinePrinter5 = comboValue(jcboMachinePrinter5.getSelectedItem());
        if ("epson".equals(sMachinePrinter5) || "tmu220".equals(sMachinePrinter5) || "star".equals(sMachinePrinter5) || "ithaca".equals(sMachinePrinter5) || "surepos".equals(sMachinePrinter5)) {
            config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + comboValue(jcboConnPrinter5.getSelectedItem()) + "," + comboValue(jcboSerialPrinter5.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter5)) {
            config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + m_jtxtJPOSPrinter5.getText() + "," + m_jtxtJPOSDrawer5.getText());
        } else if ("printer".equals(sMachinePrinter5)) {
            config.setProperty("machine.printer.5", sMachinePrinter5 + ":" + printer5printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.5", sMachinePrinter5);
        }
        
        String sMachinePrinter6 = comboValue(jcboMachinePrinter6.getSelectedItem());
        if ("epson".equals(sMachinePrinter6) || "tmu220".equals(sMachinePrinter6) || "star".equals(sMachinePrinter6) || "ithaca".equals(sMachinePrinter6) || "surepos".equals(sMachinePrinter6)) {
            config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + comboValue(jcboConnPrinter6.getSelectedItem()) + "," + comboValue(jcboSerialPrinter6.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter6)) {
            config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + m_jtxtJPOSPrinter6.getText() + "," + m_jtxtJPOSDrawer6.getText());
        } else if ("printer".equals(sMachinePrinter6)) {
            config.setProperty("machine.printer.6", sMachinePrinter6 + ":" + printer6printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.6", sMachinePrinter6);
        }
        
        String sMachinePrinter7 = comboValue(jcboMachinePrinter7.getSelectedItem());
        if ("epson".equals(sMachinePrinter7) || "tmu220".equals(sMachinePrinter7) || "star".equals(sMachinePrinter7) || "ithaca".equals(sMachinePrinter7) || "surepos".equals(sMachinePrinter7)) {
            config.setProperty("machine.printer.7", sMachinePrinter7 + ":" + comboValue(jcboConnPrinter7.getSelectedItem()) + "," + comboValue(jcboSerialPrinter7.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter7)) {
            config.setProperty("machine.printer.7", sMachinePrinter7 + ":" + m_jtxtJPOSPrinter7.getText() + "," + m_jtxtJPOSDrawer7.getText());
        } else if ("printer".equals(sMachinePrinter7)) {
            config.setProperty("machine.printer.7", sMachinePrinter7 + ":" + printer7printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.7", sMachinePrinter7);
        }
        
        String sMachinePrinter8 = comboValue(jcboMachinePrinter8.getSelectedItem());
        if ("epson".equals(sMachinePrinter8) || "tmu220".equals(sMachinePrinter8) || "star".equals(sMachinePrinter8) || "ithaca".equals(sMachinePrinter8) || "surepos".equals(sMachinePrinter8)) {
            config.setProperty("machine.printer.8", sMachinePrinter8 + ":" + comboValue(jcboConnPrinter8.getSelectedItem()) + "," + comboValue(jcboSerialPrinter8.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter8)) {
            config.setProperty("machine.printer.8", sMachinePrinter8 + ":" + m_jtxtJPOSPrinter8.getText() + "," + m_jtxtJPOSDrawer8.getText());
        } else if ("printer".equals(sMachinePrinter8)) {
            config.setProperty("machine.printer.8", sMachinePrinter8 + ":" + printer8printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.8", sMachinePrinter8);
        }
        
        String sMachinePrinter9 = comboValue(jcboMachinePrinter9.getSelectedItem());
        if ("epson".equals(sMachinePrinter9) || "tmu220".equals(sMachinePrinter9) || "star".equals(sMachinePrinter9) || "ithaca".equals(sMachinePrinter9) || "surepos".equals(sMachinePrinter9)) {
            config.setProperty("machine.printer.9", sMachinePrinter9 + ":" + comboValue(jcboConnPrinter9.getSelectedItem()) + "," + comboValue(jcboSerialPrinter9.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter9)) {
            config.setProperty("machine.printer.9", sMachinePrinter9 + ":" + m_jtxtJPOSPrinter9.getText() + "," + m_jtxtJPOSDrawer9.getText());
        } else if ("printer".equals(sMachinePrinter9)) {
            config.setProperty("machine.printer.9", sMachinePrinter9 + ":" + printer9printerparams.getParameters());
        } else {
            config.setProperty("machine.printer.9", sMachinePrinter9);
        }

        String sMachineDisplay = comboValue(jcboMachineDisplay.getSelectedItem());
        if ("epson".equals(sMachineDisplay) || "ld200".equals(sMachineDisplay) || "surepos".equals(sMachineDisplay)) {
            config.setProperty("machine.display", sMachineDisplay + ":" + comboValue(jcboConnDisplay.getSelectedItem()) + "," + comboValue(jcboSerialDisplay.getSelectedItem()));
        } else if ("javapos".equals(sMachineDisplay)) {
            config.setProperty("machine.display", sMachineDisplay + ":" + m_jtxtJPOSName.getText());
        } else {
            config.setProperty("machine.display", sMachineDisplay);
        }

        // La bascula
        String sMachineScale = comboValue(jcboMachineScale.getSelectedItem());
        if ("dialog1".equals(sMachineScale) || "samsungesp".equals(sMachineScale)) {
            config.setProperty("machine.scale", sMachineScale + ":" + comboValue(jcboSerialScale.getSelectedItem()));
        } else {
            config.setProperty("machine.scale", sMachineScale);
        }

        // El scanner
        String sMachineScanner = comboValue(jcboMachineScanner.getSelectedItem());
        if ("scanpal2".equals(sMachineScanner)) {
            config.setProperty("machine.scanner", sMachineScanner + ":" + comboValue(jcboSerialScanner.getSelectedItem()));
        } else {
            config.setProperty("machine.scanner", sMachineScanner);
        }

        config.setProperty("machine.printername", comboValue(cboPrinters.getSelectedItem()));

        dirty.setDirty(false);
    }

    private String unifySerialInterface(String sparam) {

        // for backward compatibility
        return ("rxtx".equals(sparam))
                ? "serial"
                : sparam;
    }

    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }

    private void changeLAF() {

        final LAFInfo laf = (LAFInfo) jcboLAF.getSelectedItem();
        if (laf != null && !laf.getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
            // The selected look and feel is different from the current look and feel.
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    try {
                        String lafname = laf.getClassName();
                        Object laf = Class.forName(lafname).newInstance();

                        if (laf instanceof LookAndFeel) {
                            UIManager.setLookAndFeel((LookAndFeel) laf);
                        } else if (laf instanceof SubstanceSkin) {
                            SubstanceLookAndFeel.setSkin((SubstanceSkin) laf);
                        }

                        SwingUtilities.updateComponentTreeUI(JPanelConfigGeneral.this.getTopLevelAncestor());
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

    private static class LAFInfo {

        private String name;
        private String classname;

        public LAFInfo(String name, String classname) {
            this.name = name;
            this.classname = classname;
        }

        public String getName() {
            return name;
        }

        public String getClassName() {
            return classname;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtxtMachineHostname = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jcboLAF = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jcboMachineScreenmode = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jcboTicketsBag = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jcboMachineDisplay = new javax.swing.JComboBox();
        m_jDisplayParams = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jlblConnDisplay = new javax.swing.JLabel();
        jcboConnDisplay = new javax.swing.JComboBox();
        jlblDisplayPort = new javax.swing.JLabel();
        jcboSerialDisplay = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        m_jtxtJPOSName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jcboMachinePrinter = new javax.swing.JComboBox();
        m_jPrinterParams1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jlblConnPrinter = new javax.swing.JLabel();
        jcboConnPrinter = new javax.swing.JComboBox();
        jlblPrinterPort = new javax.swing.JLabel();
        jcboSerialPrinter = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        m_jtxtJPOSPrinter = new javax.swing.JTextField();
        m_jtxtJPOSDrawer = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jcboMachinePrinter2 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        m_jPrinterParams2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jlblConnPrinter2 = new javax.swing.JLabel();
        jcboConnPrinter2 = new javax.swing.JComboBox();
        jlblPrinterPort2 = new javax.swing.JLabel();
        jcboSerialPrinter2 = new javax.swing.JComboBox();
        jPanel11 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter2 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer2 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jcboMachinePrinter3 = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jcboMachineScale = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jcboMachineScanner = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        cboPrinters = new javax.swing.JComboBox();
        m_jPrinterParams3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jlblConnPrinter3 = new javax.swing.JLabel();
        jcboConnPrinter3 = new javax.swing.JComboBox();
        jlblPrinterPort3 = new javax.swing.JLabel();
        jcboSerialPrinter3 = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter3 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer3 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        m_jScaleParams = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jlblPrinterPort4 = new javax.swing.JLabel();
        jcboSerialScale = new javax.swing.JComboBox();
        m_jScannerParams = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jlblPrinterPort5 = new javax.swing.JLabel();
        jcboSerialScanner = new javax.swing.JComboBox();
        m_jPrinterParams4 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jlblConnPrinter4 = new javax.swing.JLabel();
        jcboConnPrinter4 = new javax.swing.JComboBox();
        jlblPrinterPort6 = new javax.swing.JLabel();
        jcboSerialPrinter4 = new javax.swing.JComboBox();
        jPanel18 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter4 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer4 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jcboMachinePrinter4 = new javax.swing.JComboBox();
        jLabel32 = new javax.swing.JLabel();
        jcboMachinePrinter5 = new javax.swing.JComboBox();
        m_jPrinterParams5 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jlblConnPrinter5 = new javax.swing.JLabel();
        jcboConnPrinter5 = new javax.swing.JComboBox();
        jlblPrinterPort7 = new javax.swing.JLabel();
        jcboSerialPrinter5 = new javax.swing.JComboBox();
        jPanel22 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter5 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer5 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        m_jPrinterParams6 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jlblConnPrinter6 = new javax.swing.JLabel();
        jcboConnPrinter6 = new javax.swing.JComboBox();
        jlblPrinterPort8 = new javax.swing.JLabel();
        jcboSerialPrinter6 = new javax.swing.JComboBox();
        jPanel26 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter6 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer6 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jcboMachinePrinter6 = new javax.swing.JComboBox();
        jLabel37 = new javax.swing.JLabel();
        m_jPrinterParams7 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jlblConnPrinter7 = new javax.swing.JLabel();
        jcboConnPrinter7 = new javax.swing.JComboBox();
        jlblPrinterPort9 = new javax.swing.JLabel();
        jcboSerialPrinter7 = new javax.swing.JComboBox();
        jPanel29 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter7 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer7 = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jcboMachinePrinter7 = new javax.swing.JComboBox();
        jLabel40 = new javax.swing.JLabel();
        m_jPrinterParams8 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jlblConnPrinter8 = new javax.swing.JLabel();
        jcboConnPrinter8 = new javax.swing.JComboBox();
        jlblPrinterPort10 = new javax.swing.JLabel();
        jcboSerialPrinter8 = new javax.swing.JComboBox();
        jPanel32 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter8 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer8 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jcboMachinePrinter8 = new javax.swing.JComboBox();
        jLabel43 = new javax.swing.JLabel();
        m_jPrinterParams9 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jlblConnPrinter9 = new javax.swing.JLabel();
        jcboConnPrinter9 = new javax.swing.JComboBox();
        jlblPrinterPort11 = new javax.swing.JLabel();
        jcboSerialPrinter9 = new javax.swing.JComboBox();
        jPanel35 = new javax.swing.JPanel();
        m_jtxtJPOSPrinter9 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer9 = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jcboMachinePrinter9 = new javax.swing.JComboBox();
        jLabel46 = new javax.swing.JLabel();

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(AppLocal.getIntString("Label.CashMachine"))); // NOI18N

        jLabel5.setText(AppLocal.getIntString("Label.MachineName")); // NOI18N

        jLabel2.setText(AppLocal.getIntString("label.looknfeel")); // NOI18N

        jLabel6.setText(AppLocal.getIntString("Label.MachineScreen")); // NOI18N

        jLabel16.setText(AppLocal.getIntString("Label.Ticketsbag")); // NOI18N

        jLabel15.setText(AppLocal.getIntString("Label.MachineDisplay")); // NOI18N

        jcboMachineDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineDisplayActionPerformed(evt);
            }
        });

        m_jDisplayParams.setLayout(new java.awt.CardLayout());
        m_jDisplayParams.add(jPanel2, "empty");

        jlblConnDisplay.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblDisplayPort.setText(AppLocal.getIntString("label.machinedisplayport")); // NOI18N

        jcboSerialDisplay.setEditable(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblDisplayPort, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDisplayPort)
                    .addComponent(jcboSerialDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnDisplay))
                .addGap(59, 59, 59))
        );

        m_jDisplayParams.add(jPanel1, "comm");

        jLabel20.setText(AppLocal.getIntString("Label.Name")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSName, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(184, 184, 184))
        );

        m_jDisplayParams.add(jPanel3, "javapos");

        jLabel7.setText(AppLocal.getIntString("Label.MachinePrinter")); // NOI18N

        jcboMachinePrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinterActionPerformed(evt);
            }
        });

        m_jPrinterParams1.setLayout(new java.awt.CardLayout());
        m_jPrinterParams1.add(jPanel5, "empty");

        jlblConnPrinter.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter.setEditable(true);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort)
                    .addComponent(jcboSerialPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter))
                .addGap(195, 195, 195))
        );

        m_jPrinterParams1.add(jPanel6, "comm");

        jLabel21.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        jLabel24.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(m_jtxtJPOSDrawer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(184, 184, 184))
        );

        m_jPrinterParams1.add(jPanel4, "javapos");

        jLabel18.setText(AppLocal.getIntString("Label.MachinePrinter2")); // NOI18N

        jcboMachinePrinter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter2ActionPerformed(evt);
            }
        });

        jLabel19.setText(AppLocal.getIntString("Label.MachinePrinter3")); // NOI18N

        m_jPrinterParams2.setLayout(new java.awt.CardLayout());
        m_jPrinterParams2.add(jPanel7, "empty");

        jlblConnPrinter2.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort2.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter2.setEditable(true);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort2)
                    .addComponent(jcboSerialPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter2))
                .addGap(205, 205, 205))
        );

        m_jPrinterParams2.add(jPanel8, "comm");

        jLabel27.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel22.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(m_jtxtJPOSDrawer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(184, 184, 184))
        );

        m_jPrinterParams2.add(jPanel11, "javapos");

        jcboMachinePrinter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter3ActionPerformed(evt);
            }
        });

        jLabel25.setText(AppLocal.getIntString("label.scale")); // NOI18N

        jcboMachineScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineScaleActionPerformed(evt);
            }
        });

        jLabel26.setText(AppLocal.getIntString("label.scanner")); // NOI18N

        jcboMachineScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineScannerActionPerformed(evt);
            }
        });

        jLabel1.setText(AppLocal.getIntString("label.reportsprinter")); // NOI18N

        m_jPrinterParams3.setLayout(new java.awt.CardLayout());
        m_jPrinterParams3.add(jPanel9, "empty");

        jlblConnPrinter3.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort3.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter3.setEditable(true);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort3)
                    .addComponent(jcboSerialPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter3))
                .addGap(125, 125, 125))
        );

        m_jPrinterParams3.add(jPanel10, "comm");

        jLabel28.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel23.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(m_jtxtJPOSDrawer3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(224, 224, 224))
        );

        m_jPrinterParams3.add(jPanel12, "javapos");

        m_jScaleParams.setLayout(new java.awt.CardLayout());
        m_jScaleParams.add(jPanel16, "empty");

        jlblPrinterPort4.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialScale.setEditable(true);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblPrinterPort4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialScale, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboSerialScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort4))
                .addGap(135, 135, 135))
        );

        m_jScaleParams.add(jPanel17, "comm");

        m_jScannerParams.setLayout(new java.awt.CardLayout());
        m_jScannerParams.add(jPanel24, "empty");

        jlblPrinterPort5.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialScanner.setEditable(true);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblPrinterPort5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialScanner, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(270, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboSerialScanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort5))
                .addGap(235, 235, 235))
        );

        m_jScannerParams.add(jPanel19, "comm");

        m_jPrinterParams4.setPreferredSize(new java.awt.Dimension(500, 250));
        m_jPrinterParams4.setLayout(new java.awt.CardLayout());
        m_jPrinterParams4.add(jPanel14, "empty");

        jlblConnPrinter4.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort6.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter4.setEditable(true);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort6)
                    .addComponent(jcboSerialPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter4))
                .addGap(125, 125, 125))
        );

        m_jPrinterParams4.add(jPanel15, "comm");

        jLabel29.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel30.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(m_jtxtJPOSDrawer4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(224, 224, 224))
        );

        m_jPrinterParams4.add(jPanel18, "javapos");

        jLabel31.setText(AppLocal.getIntString("Label.MachinePrinter4")); // NOI18N

        jcboMachinePrinter4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter4ActionPerformed(evt);
            }
        });

        jLabel32.setText(AppLocal.getIntString("Label.MachinePrinter5")); // NOI18N

        jcboMachinePrinter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter5ActionPerformed(evt);
            }
        });

        m_jPrinterParams5.setLayout(new java.awt.CardLayout());
        m_jPrinterParams5.add(jPanel20, "empty");

        jlblConnPrinter5.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort7.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter5.setEditable(true);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort7)
                    .addComponent(jcboSerialPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter5))
                .addGap(125, 125, 125))
        );

        m_jPrinterParams5.add(jPanel21, "comm");

        jLabel33.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel34.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(m_jtxtJPOSDrawer5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addGap(224, 224, 224))
        );

        m_jPrinterParams5.add(jPanel22, "javapos");

        m_jPrinterParams6.setLayout(new java.awt.CardLayout());
        m_jPrinterParams6.add(jPanel23, "empty");

        jlblConnPrinter6.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort8.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter6.setEditable(true);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort8)
                    .addComponent(jcboSerialPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter6))
                .addGap(125, 125, 125))
        );

        m_jPrinterParams6.add(jPanel25, "comm");

        jLabel35.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel36.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(m_jtxtJPOSDrawer6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(224, 224, 224))
        );

        m_jPrinterParams6.add(jPanel26, "javapos");

        jcboMachinePrinter6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter6ActionPerformed(evt);
            }
        });

        jLabel37.setText(AppLocal.getIntString("Label.MachinePrinter6")); // NOI18N

        m_jPrinterParams7.setLayout(new java.awt.CardLayout());
        m_jPrinterParams7.add(jPanel27, "empty");

        jlblConnPrinter7.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort9.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter7.setEditable(true);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort9)
                    .addComponent(jcboSerialPrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter7))
                .addGap(125, 125, 125))
        );

        m_jPrinterParams7.add(jPanel28, "comm");

        jLabel38.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel39.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(m_jtxtJPOSDrawer7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38))
                .addGap(224, 224, 224))
        );

        m_jPrinterParams7.add(jPanel29, "javapos");

        jcboMachinePrinter7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter7ActionPerformed(evt);
            }
        });

        jLabel40.setText(AppLocal.getIntString("Label.MachinePrinter7")); // NOI18N

        m_jPrinterParams8.setLayout(new java.awt.CardLayout());
        m_jPrinterParams8.add(jPanel30, "empty");

        jlblConnPrinter8.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort10.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter8.setEditable(true);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort10)
                    .addComponent(jcboSerialPrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter8))
                .addGap(125, 125, 125))
        );

        m_jPrinterParams8.add(jPanel31, "comm");

        jLabel41.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel42.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(m_jtxtJPOSDrawer8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addGap(224, 224, 224))
        );

        m_jPrinterParams8.add(jPanel32, "javapos");

        jcboMachinePrinter8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter8ActionPerformed(evt);
            }
        });

        jLabel43.setText(AppLocal.getIntString("Label.MachinePrinter8")); // NOI18N

        m_jPrinterParams9.setLayout(new java.awt.CardLayout());
        m_jPrinterParams9.add(jPanel33, "empty");

        jlblConnPrinter9.setText(AppLocal.getIntString("label.machinedisplayconn")); // NOI18N

        jlblPrinterPort11.setText(AppLocal.getIntString("label.machineprinterport")); // NOI18N

        jcboSerialPrinter9.setEditable(true);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblConnPrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboConnPrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPrinterPort11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcboSerialPrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboConnPrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPrinterPort11)
                    .addComponent(jcboSerialPrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblConnPrinter9))
                .addGap(125, 125, 125))
        );

        m_jPrinterParams9.add(jPanel34, "comm");

        jLabel44.setText(AppLocal.getIntString("label.javapos.printer")); // NOI18N

        jLabel45.setText(AppLocal.getIntString("label.javapos.drawer")); // NOI18N

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSPrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_jtxtJPOSDrawer9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(m_jtxtJPOSPrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(m_jtxtJPOSDrawer9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44))
                .addGap(224, 224, 224))
        );

        m_jPrinterParams9.add(jPanel35, "javapos");

        jcboMachinePrinter9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter9ActionPerformed(evt);
            }
        });

        jLabel46.setText(AppLocal.getIntString("Label.MachinePrinter9")); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachineDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jDisplayParams, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams2, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachinePrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams3, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachineScale, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jScaleParams, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachineScanner, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboPrinters, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jScannerParams, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtMachineHostname, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboLAF, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboMachineScreenmode, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcboTicketsBag, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachinePrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachinePrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcboMachinePrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachinePrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachinePrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboMachinePrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(m_jPrinterParams4, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                            .addComponent(m_jPrinterParams5, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                            .addComponent(m_jPrinterParams6, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                            .addComponent(m_jPrinterParams7, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                            .addComponent(m_jPrinterParams8, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                            .addComponent(m_jPrinterParams9, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtMachineHostname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcboLAF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jcboMachineScreenmode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jcboTicketsBag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jcboMachineDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jDisplayParams, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jcboMachinePrinter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jcboMachinePrinter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jPrinterParams2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(m_jPrinterParams3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_jPrinterParams9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboMachinePrinter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboMachinePrinter4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jcboMachinePrinter5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboMachinePrinter6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboMachinePrinter7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboMachinePrinter8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcboMachinePrinter9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel25)
                        .addComponent(jcboMachineScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(m_jScaleParams, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(jcboMachineScanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cboPrinters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(m_jScannerParams, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcboMachineScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineScannerActionPerformed
        CardLayout cl = (CardLayout) (m_jScannerParams.getLayout());

        if ("scanpal2".equals(jcboMachineScanner.getSelectedItem())) {
            cl.show(m_jScannerParams, "comm");
        } else {
            cl.show(m_jScannerParams, "empty");
        }
    }//GEN-LAST:event_jcboMachineScannerActionPerformed

    private void jcboMachineScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineScaleActionPerformed
        CardLayout cl = (CardLayout) (m_jScaleParams.getLayout());

        if ("dialog1".equals(jcboMachineScale.getSelectedItem()) || "samsungesp".equals(jcboMachineScale.getSelectedItem())) {
            cl.show(m_jScaleParams, "comm");
        } else {
            cl.show(m_jScaleParams, "empty");
        }
    }//GEN-LAST:event_jcboMachineScaleActionPerformed

    private void jcboMachinePrinter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter3ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams3.getLayout());

        if ("epson".equals(jcboMachinePrinter3.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter3.getSelectedItem()) || "star".equals(jcboMachinePrinter3.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter3.getSelectedItem()) || "surepos".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "comm");
        } else if ("javapos".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "javapos");
        } else if ("printer".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "printer");
        } else {
            cl.show(m_jPrinterParams3, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter3ActionPerformed

    private void jcboMachinePrinter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter2ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams2.getLayout());

        if ("epson".equals(jcboMachinePrinter2.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter2.getSelectedItem()) || "star".equals(jcboMachinePrinter2.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter2.getSelectedItem()) || "surepos".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "comm");
        } else if ("javapos".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "javapos");
        } else if ("printer".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "printer");
         } else {
            cl.show(m_jPrinterParams2, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter2ActionPerformed

    private void jcboMachineDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineDisplayActionPerformed
        CardLayout cl = (CardLayout) (m_jDisplayParams.getLayout());

        if ("epson".equals(jcboMachineDisplay.getSelectedItem()) || "ld200".equals(jcboMachineDisplay.getSelectedItem()) || "surepos".equals(jcboMachineDisplay.getSelectedItem())) {
            cl.show(m_jDisplayParams, "comm");
        } else if ("javapos".equals(jcboMachineDisplay.getSelectedItem())) {
            cl.show(m_jDisplayParams, "javapos");
        } else {
            cl.show(m_jDisplayParams, "empty");
        }
    }//GEN-LAST:event_jcboMachineDisplayActionPerformed

    private void jcboMachinePrinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinterActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams1.getLayout());

        if ("epson".equals(jcboMachinePrinter.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter.getSelectedItem()) || "star".equals(jcboMachinePrinter.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter.getSelectedItem()) || "surepos".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "comm");
        } else if ("javapos".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "javapos");
        } else if ("printer".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "printer");
        } else {
            cl.show(m_jPrinterParams1, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinterActionPerformed

    private void jcboMachinePrinter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter4ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams4.getLayout());

        if ("epson".equals(jcboMachinePrinter4.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter4.getSelectedItem()) || "star".equals(jcboMachinePrinter4.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter4.getSelectedItem()) || "surepos".equals(jcboMachinePrinter4.getSelectedItem())) {
            cl.show(m_jPrinterParams4, "comm");
        } else if ("javapos".equals(jcboMachinePrinter4.getSelectedItem())) {
            cl.show(m_jPrinterParams4, "javapos");
        } else if ("printer".equals(jcboMachinePrinter4.getSelectedItem())) {
            cl.show(m_jPrinterParams4, "printer");
        } else {
            cl.show(m_jPrinterParams4, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter4ActionPerformed

    private void jcboMachinePrinter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter5ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams5.getLayout());

        if ("epson".equals(jcboMachinePrinter5.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter5.getSelectedItem()) || "star".equals(jcboMachinePrinter5.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter5.getSelectedItem()) || "surepos".equals(jcboMachinePrinter5.getSelectedItem())) {
            cl.show(m_jPrinterParams5, "comm");
        } else if ("javapos".equals(jcboMachinePrinter5.getSelectedItem())) {
            cl.show(m_jPrinterParams5, "javapos");
        } else if ("printer".equals(jcboMachinePrinter5.getSelectedItem())) {
            cl.show(m_jPrinterParams5, "printer");
        } else {
            cl.show(m_jPrinterParams5, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter5ActionPerformed

    private void jcboMachinePrinter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter6ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams6.getLayout());

        if ("epson".equals(jcboMachinePrinter6.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter6.getSelectedItem()) || "star".equals(jcboMachinePrinter6.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter6.getSelectedItem()) || "surepos".equals(jcboMachinePrinter6.getSelectedItem())) {
            cl.show(m_jPrinterParams6, "comm");
        } else if ("javapos".equals(jcboMachinePrinter6.getSelectedItem())) {
            cl.show(m_jPrinterParams6, "javapos");
        } else if ("printer".equals(jcboMachinePrinter6.getSelectedItem())) {
            cl.show(m_jPrinterParams6, "printer");
        } else {
            cl.show(m_jPrinterParams6, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter6ActionPerformed

    private void jcboMachinePrinter7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter7ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams7.getLayout());

        if ("epson".equals(jcboMachinePrinter7.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter7.getSelectedItem()) || "star".equals(jcboMachinePrinter7.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter7.getSelectedItem()) || "surepos".equals(jcboMachinePrinter7.getSelectedItem())) {
            cl.show(m_jPrinterParams7, "comm");
        } else if ("javapos".equals(jcboMachinePrinter7.getSelectedItem())) {
            cl.show(m_jPrinterParams7, "javapos");
        } else if ("printer".equals(jcboMachinePrinter7.getSelectedItem())) {
            cl.show(m_jPrinterParams7, "printer");
        } else {
            cl.show(m_jPrinterParams7, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter7ActionPerformed

    private void jcboMachinePrinter8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter8ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams8.getLayout());

        if ("epson".equals(jcboMachinePrinter8.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter8.getSelectedItem()) || "star".equals(jcboMachinePrinter8.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter8.getSelectedItem()) || "surepos".equals(jcboMachinePrinter8.getSelectedItem())) {
            cl.show(m_jPrinterParams8, "comm");
        } else if ("javapos".equals(jcboMachinePrinter8.getSelectedItem())) {
            cl.show(m_jPrinterParams8, "javapos");
        } else if ("printer".equals(jcboMachinePrinter8.getSelectedItem())) {
            cl.show(m_jPrinterParams8, "printer");
        } else {
            cl.show(m_jPrinterParams8, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter8ActionPerformed

    private void jcboMachinePrinter9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter9ActionPerformed
        CardLayout cl = (CardLayout) (m_jPrinterParams9.getLayout());

        if ("epson".equals(jcboMachinePrinter9.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter9.getSelectedItem()) || "star".equals(jcboMachinePrinter9.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter8.getSelectedItem()) || "surepos".equals(jcboMachinePrinter9.getSelectedItem())) {
            cl.show(m_jPrinterParams9, "comm");
        } else if ("javapos".equals(jcboMachinePrinter9.getSelectedItem())) {
            cl.show(m_jPrinterParams9, "javapos");
        } else if ("printer".equals(jcboMachinePrinter9.getSelectedItem())) {
            cl.show(m_jPrinterParams9, "printer");
        } else {
            cl.show(m_jPrinterParams9, "empty");
        }
    }//GEN-LAST:event_jcboMachinePrinter9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cboPrinters;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JComboBox jcboConnDisplay;
    private javax.swing.JComboBox jcboConnPrinter;
    private javax.swing.JComboBox jcboConnPrinter2;
    private javax.swing.JComboBox jcboConnPrinter3;
    private javax.swing.JComboBox jcboConnPrinter4;
    private javax.swing.JComboBox jcboConnPrinter5;
    private javax.swing.JComboBox jcboConnPrinter6;
    private javax.swing.JComboBox jcboConnPrinter7;
    private javax.swing.JComboBox jcboConnPrinter8;
    private javax.swing.JComboBox jcboConnPrinter9;
    private javax.swing.JComboBox jcboLAF;
    private javax.swing.JComboBox jcboMachineDisplay;
    private javax.swing.JComboBox jcboMachinePrinter;
    private javax.swing.JComboBox jcboMachinePrinter2;
    private javax.swing.JComboBox jcboMachinePrinter3;
    private javax.swing.JComboBox jcboMachinePrinter4;
    private javax.swing.JComboBox jcboMachinePrinter5;
    private javax.swing.JComboBox jcboMachinePrinter6;
    private javax.swing.JComboBox jcboMachinePrinter7;
    private javax.swing.JComboBox jcboMachinePrinter8;
    private javax.swing.JComboBox jcboMachinePrinter9;
    private javax.swing.JComboBox jcboMachineScale;
    private javax.swing.JComboBox jcboMachineScanner;
    private javax.swing.JComboBox jcboMachineScreenmode;
    private javax.swing.JComboBox jcboSerialDisplay;
    private javax.swing.JComboBox jcboSerialPrinter;
    private javax.swing.JComboBox jcboSerialPrinter2;
    private javax.swing.JComboBox jcboSerialPrinter3;
    private javax.swing.JComboBox jcboSerialPrinter4;
    private javax.swing.JComboBox jcboSerialPrinter5;
    private javax.swing.JComboBox jcboSerialPrinter6;
    private javax.swing.JComboBox jcboSerialPrinter7;
    private javax.swing.JComboBox jcboSerialPrinter8;
    private javax.swing.JComboBox jcboSerialPrinter9;
    private javax.swing.JComboBox jcboSerialScale;
    private javax.swing.JComboBox jcboSerialScanner;
    private javax.swing.JComboBox jcboTicketsBag;
    private javax.swing.JLabel jlblConnDisplay;
    private javax.swing.JLabel jlblConnPrinter;
    private javax.swing.JLabel jlblConnPrinter2;
    private javax.swing.JLabel jlblConnPrinter3;
    private javax.swing.JLabel jlblConnPrinter4;
    private javax.swing.JLabel jlblConnPrinter5;
    private javax.swing.JLabel jlblConnPrinter6;
    private javax.swing.JLabel jlblConnPrinter7;
    private javax.swing.JLabel jlblConnPrinter8;
    private javax.swing.JLabel jlblConnPrinter9;
    private javax.swing.JLabel jlblDisplayPort;
    private javax.swing.JLabel jlblPrinterPort;
    private javax.swing.JLabel jlblPrinterPort10;
    private javax.swing.JLabel jlblPrinterPort11;
    private javax.swing.JLabel jlblPrinterPort2;
    private javax.swing.JLabel jlblPrinterPort3;
    private javax.swing.JLabel jlblPrinterPort4;
    private javax.swing.JLabel jlblPrinterPort5;
    private javax.swing.JLabel jlblPrinterPort6;
    private javax.swing.JLabel jlblPrinterPort7;
    private javax.swing.JLabel jlblPrinterPort8;
    private javax.swing.JLabel jlblPrinterPort9;
    private javax.swing.JTextField jtxtMachineHostname;
    private javax.swing.JPanel m_jDisplayParams;
    private javax.swing.JPanel m_jPrinterParams1;
    private javax.swing.JPanel m_jPrinterParams2;
    private javax.swing.JPanel m_jPrinterParams3;
    private javax.swing.JPanel m_jPrinterParams4;
    private javax.swing.JPanel m_jPrinterParams5;
    private javax.swing.JPanel m_jPrinterParams6;
    private javax.swing.JPanel m_jPrinterParams7;
    private javax.swing.JPanel m_jPrinterParams8;
    private javax.swing.JPanel m_jPrinterParams9;
    private javax.swing.JPanel m_jScaleParams;
    private javax.swing.JPanel m_jScannerParams;
    private javax.swing.JTextField m_jtxtJPOSDrawer;
    private javax.swing.JTextField m_jtxtJPOSDrawer2;
    private javax.swing.JTextField m_jtxtJPOSDrawer3;
    private javax.swing.JTextField m_jtxtJPOSDrawer4;
    private javax.swing.JTextField m_jtxtJPOSDrawer5;
    private javax.swing.JTextField m_jtxtJPOSDrawer6;
    private javax.swing.JTextField m_jtxtJPOSDrawer7;
    private javax.swing.JTextField m_jtxtJPOSDrawer8;
    private javax.swing.JTextField m_jtxtJPOSDrawer9;
    private javax.swing.JTextField m_jtxtJPOSName;
    private javax.swing.JTextField m_jtxtJPOSPrinter;
    private javax.swing.JTextField m_jtxtJPOSPrinter2;
    private javax.swing.JTextField m_jtxtJPOSPrinter3;
    private javax.swing.JTextField m_jtxtJPOSPrinter4;
    private javax.swing.JTextField m_jtxtJPOSPrinter5;
    private javax.swing.JTextField m_jtxtJPOSPrinter6;
    private javax.swing.JTextField m_jtxtJPOSPrinter7;
    private javax.swing.JTextField m_jtxtJPOSPrinter8;
    private javax.swing.JTextField m_jtxtJPOSPrinter9;
    // End of variables declaration//GEN-END:variables
}
