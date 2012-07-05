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

package com.openbravo.pos.sales.restaurant;

import com.openbravo.pos.ticket.TicketInfo;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.openbravo.pos.sales.*;
import com.openbravo.pos.forms.*; 
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.data.loader.SerializerReadClass;
import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.format.Formats;
import com.openbravo.pos.customers.CustomerInfo;
import com.openbravo.pos.ticket.TicketLineInfo;
import com.openbravo.pos.util.AltEncrypter;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQConnection;

public class JTicketsBagRestaurantMap extends JTicketsBag {

//    private static final Icon ICO_OCU = new ImageIcon(JTicketsBag.class.getResource("/com/openbravo/images/edit_group.png"));
//    private static final Icon ICO_FRE = new NullIcon(22, 22);
        
    private java.util.List<Place> m_aplaces;
    private java.util.List<Floor> m_afloors;
    
    private JTicketsBagRestaurant m_restaurantmap;  
    private JTicketsBagRestaurantRes m_jreservations;   
    
    private Place m_PlaceCurrent;
    
    // State vars
    private Place m_PlaceClipboard;  
    private CustomerInfo customer;

    private DataLogicReceipts dlReceipts = null;
    private DataLogicSales dlSales = null;
    protected DataLogicSystem dlSystem;
    private static Logger logger = Logger.getLogger(JTicketsBagRestaurantMap.class.getName());
    /** Creates new form JTicketsBagRestaurant */
    public JTicketsBagRestaurantMap(AppView app, TicketsEditor panelticket) {
        
        super(app, panelticket);
        
        dlReceipts = (DataLogicReceipts) app.getBean("com.openbravo.pos.sales.DataLogicReceipts");
        dlSales = (DataLogicSales) m_App.getBean("com.openbravo.pos.forms.DataLogicSales");
        dlSystem= (DataLogicSystem) m_App.getBean("com.openbravo.pos.forms.DataLogicSystem");
        
        m_restaurantmap = new JTicketsBagRestaurant(app, this);
        m_PlaceCurrent = null;
        m_PlaceClipboard = null;
        customer = null;
            
        try {
            SentenceList sent = new StaticSentence(
                    app.getSession(), 
                    "SELECT ID, NAME, IMAGE FROM FLOORS ORDER BY NAME", 
                    null, 
                    new SerializerReadClass(Floor.class));
            m_afloors = sent.list();
               
                
            
        } catch (BasicException eD) {
            m_afloors = new ArrayList<Floor>();
        }
        try {
            SentenceList sent = new StaticSentence(
                    app.getSession(), 
                    "SELECT ID, NAME, X, Y, FLOOR FROM PLACES ORDER BY FLOOR", 
                    null, 
                    new SerializerReadClass(Place.class));
            m_aplaces = sent.list();
        } catch (BasicException eD) {
            m_aplaces = new ArrayList<Place>();
        } 
        
        initComponents(); 
          
        // add the Floors containers
        if (m_afloors.size() > 1) {
            // A tab container for 2 or more floors
            JTabbedPane jTabFloors = new JTabbedPane();
            jTabFloors.applyComponentOrientation(getComponentOrientation());
            jTabFloors.setBorder(new javax.swing.border.EmptyBorder(new Insets(5, 5, 5, 5)));
            jTabFloors.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            jTabFloors.setFocusable(false);
            jTabFloors.setRequestFocusEnabled(false);
            m_jPanelMap.add(jTabFloors, BorderLayout.CENTER);
            
            for (Floor f : m_afloors) {
                f.getContainer().applyComponentOrientation(getComponentOrientation());
                
                JScrollPane jScrCont = new JScrollPane();
                jScrCont.applyComponentOrientation(getComponentOrientation());
                JPanel jPanCont = new JPanel();  
                jPanCont.applyComponentOrientation(getComponentOrientation());
                
                jTabFloors.addTab(f.getName(), f.getIcon(), jScrCont);     
                jScrCont.setViewportView(jPanCont);
                jPanCont.add(f.getContainer());
            }
        } else if (m_afloors.size() == 1) {
            // Just a frame for 1 floor
            Floor f = m_afloors.get(0);
            f.getContainer().applyComponentOrientation(getComponentOrientation());
            
            JPanel jPlaces = new JPanel();
            jPlaces.applyComponentOrientation(getComponentOrientation());
            jPlaces.setLayout(new BorderLayout());
            jPlaces.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.EmptyBorder(new Insets(5, 5, 5, 5)),
                    new javax.swing.border.TitledBorder(f.getName())));
            
            JScrollPane jScrCont = new JScrollPane();
            jScrCont.applyComponentOrientation(getComponentOrientation());
            JPanel jPanCont = new JPanel();
            jPanCont.applyComponentOrientation(getComponentOrientation());
            
            // jPlaces.setLayout(new FlowLayout());           
            m_jPanelMap.add(jPlaces, BorderLayout.CENTER);
            jPlaces.add(jScrCont, BorderLayout.CENTER);
            jScrCont.setViewportView(jPanCont);            
            jPanCont.add(f.getContainer());
        }   
        
        // Add all the Table buttons.
        Floor currfloor = null;
        
        
        for (Place pl : m_aplaces) {
            int iFloor = 0;
            
            if (currfloor == null || !currfloor.getID().equals(pl.getFloor())) {
                // Look for a new floor
                do {
                    currfloor = m_afloors.get(iFloor++);
                } while (!currfloor.getID().equals(pl.getFloor()));
            }

            currfloor.getContainer().add(pl.getButton());
            pl.setButtonBounds();
            pl.getButton().addActionListener(new MyActionListener(pl));
        }
        
        // Add the reservations panel
        m_jreservations = new JTicketsBagRestaurantRes(app, this);
        add(m_jreservations, "res");
    }
    
    public void activate() {
        
        // precondicion es que no tenemos ticket activado ni ticket en el panel

        m_PlaceClipboard = null;
        customer = null;
        loadTickets();        
        printState(); 
        
        m_panelticket.setActiveTicket(null, null); 
        m_restaurantmap.activate();
       
        showView("map"); // arrancamos en la vista de las mesas.
        
        // postcondicion es que tenemos ticket activado aqui y ticket en el panel
    }
    
    public boolean deactivate() {
        
        // precondicion es que tenemos ticket activado aqui y ticket en el panel
        
        if (viewTables()) {
        
            // borramos el clipboard
            m_PlaceClipboard = null;
            customer = null;

            // guardamos el ticket
            if (m_PlaceCurrent != null) {
                            
                try {
                    dlReceipts.updateSharedTicket(m_PlaceCurrent.getId(), m_panelticket.getActiveTicket());
                } catch (BasicException e) {
                    new MessageInf(e).show(this);
                }                                  
 
                m_PlaceCurrent = null;
            }

            // desactivamos cositas.
            printState();     
            m_panelticket.setActiveTicket(null, null); 

            return true;
        } else {
            return false;
        }
        
        // postcondicion es que no tenemos ticket activado
    }

        
    protected JComponent getBagComponent() {
        return m_restaurantmap;
    }
    protected JComponent getNullComponent() {
        return this;
    }
   
    public void moveTicket() {
        
        // guardamos el ticket
        if (m_PlaceCurrent != null) {
                          
            try {
                dlReceipts.updateSharedTicket(m_PlaceCurrent.getId(), m_panelticket.getActiveTicket());
            } catch (BasicException e) {
                new MessageInf(e).show(this);
            }      
            
            // me guardo el ticket que quiero copiar.
            m_PlaceClipboard = m_PlaceCurrent;    
            customer = null;
            m_PlaceCurrent = null;
        }
        
        printState();
        m_panelticket.setActiveTicket(null, null);
    }
    
    public boolean viewTables(CustomerInfo c) {
        // deberiamos comprobar si estamos en reservations o en tables...
        if (m_jreservations.deactivate()) {
            showView("map"); // arrancamos en la vista de las mesas.
            
            m_PlaceClipboard = null;    
            customer = c;     
            printState();
            
            return true;
        } else {
            return false;
        }        
    }
    
    public boolean viewTables() {
        return viewTables(null);
    }
        
    public void newTicket() {
        
        // guardamos el ticket
        if (m_PlaceCurrent != null) {
                         
            try {
                dlReceipts.updateSharedTicket(m_PlaceCurrent.getId(), m_panelticket.getActiveTicket());                
            } catch (BasicException e) {
                new MessageInf(e).show(this); // maybe other guy deleted it
            }              

            m_PlaceCurrent = null;
        }
        
        printState();     
        m_panelticket.setActiveTicket(null, null);     
    }
    
    public void deleteTicket() {
        deleteTicket(false);
    }
    
    
    
    public void deleteTicket(boolean isTicketCancel) {
        if (m_PlaceCurrent != null) {
            
            String id = m_PlaceCurrent.getId();
            try {
                if(isTicketCancel){
                    TicketInfo ticket = m_panelticket.getActiveTicket();
                    for(TicketLineInfo line: ticket.getLines()){
                        if(line.getProperty("sendstatus").equals("Yes") && line.getProductID() != null){
                            ReasonToDeleteTicket reasonToDeleteTicket = new ReasonToDeleteTicket(dlSystem.getResourceAsText("label.razon"),dlSystem.getResourceAsText("label.affectsInventory"));
                            reasonToDeleteTicket.setReason(dlSystem.getResourceAsText("reasonToCancel"));

                            JOptionPane.showMessageDialog(null, reasonToDeleteTicket,LocalRes.getIntString("message.eliminationitem") + line.getProductName(),JOptionPane.DEFAULT_OPTION);
                            if(reasonToDeleteTicket.afectsStock()){
                                dlSales.saveReasonToDeleteItem(line.getProductID(),reasonToDeleteTicket.getReason(),reasonToDeleteTicket.afectsStock(),ticket.getId(), m_App.getAppUserView().getUser().getId() );
                                sendInventoryShipping(ticket,line,reasonToDeleteTicket.getReason());
                            }
                        }
                    }
                }
                
                dlReceipts.deleteSharedTicket(id);
                
                
            } catch (BasicException e) {
                new MessageInf(e).show(this);
            }       
            
            m_PlaceCurrent.setPeople(false);
            
            m_PlaceCurrent = null;
        }        
        
        printState();     
        m_panelticket.setActiveTicket(null, null); 
    }
    
    public void loadTickets() {

        Set<String> atickets = new HashSet<String>();
        
        try {
            java.util.List<SharedTicketInfo> l = dlReceipts.getSharedTicketList();
            for (SharedTicketInfo ticket : l) {
                atickets.add(ticket.getId());
            }
        } catch (BasicException e) {
            new MessageInf(e).show(this);
        }            
            
        for (Place table : m_aplaces) {
            table.setPeople(atickets.contains(table.getId()));
        }
    }
    
    private void printState() {
        
        if (m_PlaceClipboard == null) {
            if (customer == null) {
                // Select a table
                m_jText.setText(null);
                // Enable all tables
                for (Place place : m_aplaces) {
                    place.getButton().setEnabled(true);
                }
                m_jbtnReservations.setEnabled(true);
            } else {
                // receive a customer
                m_jText.setText(AppLocal.getIntString("label.restaurantcustomer", new Object[] {customer.getName()}));
                // Enable all tables
                for (Place place : m_aplaces) {
                    place.getButton().setEnabled(!place.hasPeople());
                }                
                m_jbtnReservations.setEnabled(false);
            }
        } else {
            // Moving or merging the receipt to another table
            m_jText.setText(AppLocal.getIntString("label.restaurantmove", new Object[] {m_PlaceClipboard.getName()}));
            // Enable all empty tables and origin table.
            for (Place place : m_aplaces) {
                place.getButton().setEnabled(true);
            }  
            m_jbtnReservations.setEnabled(false);
        }
    }   
    
    private TicketInfo getTicketInfo(Place place) {

        try {
            return dlReceipts.getSharedTicket(place.getId());
        } catch (BasicException e) {
            new MessageInf(e).show(JTicketsBagRestaurantMap.this);
            return null;
        }
    }
    
    private void setActivePlace(Place place, TicketInfo ticket) {
        m_PlaceCurrent = place;
        m_panelticket.setActiveTicket(ticket, m_PlaceCurrent.getName());
    }

    private void showView(String view) {
        CardLayout cl = (CardLayout)(getLayout());
        cl.show(this, view);  
    }

    private void sendInventoryShipping(TicketInfo ticket, TicketLineInfo line, String reason) {
        try{
            String jmsOutQueue = dlSystem.getResourceAsText("jms.outqueue");
            String jmsPassword = dlSystem.getResourceAsText("jms.password");
            String jmsUserLogin = dlSystem.getResourceAsText("jms.userLogin");
            String jmsInQueue = dlSystem.getResourceAsText("jms.inqueue");
            String jmsUrlOut = dlSystem.getResourceAsText("jms.url.out");
            
            Formats.setDoublePattern("#0.##");
            String subject = jmsOutQueue;
            String password = "";
            if(jmsPassword.indexOf("crypt") == 0){
                AltEncrypter cypher = new AltEncrypter("cypherkey" + jmsUserLogin);
                password = cypher.decrypt(jmsPassword.substring(6));
            } else{
                password = jmsPassword;
            }

            ConnectionFactory connectionFactory =  new ActiveMQConnectionFactory(jmsUserLogin,password,jmsUrlOut);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            String xml ="";
            xml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            xml += "<entityDetail>";
            xml += "	<type>inventory-shipping</type>";
            xml += "	<detail>";
            xml += "		<productId>" + line.getProductID() +"</productId>";
            xml += "		<productName>" + line.getProductName() +"</productName>";
            xml += "		<productAmount>" + line.getMultiply() +"</productAmount>";
            xml += "		<organization>" + jmsInQueue+"</organization>";
            xml += "                <productTaxCategoryID>"+ line.getProductTaxCategoryID()  +"</productTaxCategoryID>";
            xml += "		<m_dDate>" + sdf.format(ticket.getDate())+"</m_dDate>";
            xml += "		<unit>" + line.getProperty("unitid")+"</unit>";
            xml += "		<unit-amount>" + line.getProperty("product.amount")+"</unit-amount>";
            xml += "                <price>"+ line.getPrice()  +"</price>";
            xml += "                <tax-name>"+ line.getTaxInfo().getName()  +"</tax-name>";
            xml += "                <subValue>"+ line.getSubValue()  +"</subValue>";
            xml += "                <m_sId>"+ ticket.getId()  +"</m_sId>";
            xml += "		<m_iTicketId>" + ticket.getTicketId()+"</m_iTicketId>";
            xml += "		<machine-hostname>" + m_App.getProperties().getProperty("machine.hostname") +"</machine-hostname>";
            xml += "		<description>" + reason +"</description>";
            xml += "		<priceListID>" + dlSystem.getResourceAsText("price.listId")+"</priceListID>";

            if(ticket.getCustomer() != null){
                    xml += "		<m_Customer ";
                    xml += " taxcustomerid=\""+ ticket.getCustomer().getTaxCustCategoryID()  +"\"";
                    xml += " visible=\""+ ticket.getCustomer().isVisible()  +"\"";
                    xml += " card=\""+ ticket.getCustomer().getCard()  +"\"";
                    xml += " maxdebt=\""+ ticket.getCustomer().getMaxdebt()  +"\"";
                    xml += " curdate=\""+ ticket.getCustomer().getCurdate()  +"\"";
                    xml += " curdebt=\""+ ticket.getCustomer().getCurdebt()  +"\"";
                    xml += " firstname=\""+ ticket.getCustomer().getFirstname()  +"\"";
                    xml += " lastname=\""+ ticket.getCustomer().getLastname()  +"\"";
                    xml += " email=\""+ ticket.getCustomer().getEmail()  +"\"";
                    xml += " phone=\""+ ticket.getCustomer().getPhone()  +"\"";
                    xml += " phone2=\""+ ticket.getCustomer().getPhone2()  +"\"";
                    xml += " fax=\""+ ticket.getCustomer().getFax()  +"\"";
                    xml += " address=\""+ ticket.getCustomer().getAddress()  +"\"";
                    xml += " address2=\""+ ticket.getCustomer().getAddress2()  +"\"";
                    xml += " postal=\""+ ticket.getCustomer().getPostal()  +"\"";
                    xml += " city=\""+ ticket.getCustomer().getCity()  +"\"";
                    xml += " region=\""+ ticket.getCustomer().getRegion()  +"\"";
                    xml += " country=\""+ ticket.getCustomer().getCountry()  +"\"";
                    xml += "/>";
            }

            xml += "            <waiter-login>"+ticket.getProperty("waiter-login") +"</waiter-login>";
            xml += "            <value>"+ line.getValue()  +"</value>";
            xml += "	</detail>";
            xml += "</entityDetail>";
            logger.log(Level.SEVERE,"\n+++++++++++++++++++++++++\n\n");
            logger.log(Level.SEVERE, "Mensaje de salida");
            logger.log(Level.SEVERE, xml);
            TextMessage message = session.createTextMessage(xml);
            producer.send(message);
            connection.close();
         }catch(Exception jmse){
             jmse.printStackTrace();
         }
    }
    
    private class MyActionListener implements ActionListener {
        
        private Place m_place;
        
        public MyActionListener(Place place) {
            m_place = place;
        }
        
        public void actionPerformed(ActionEvent evt) {    
            
            if (m_PlaceClipboard == null) {  
                
                if (customer == null) {
                    // tables
                
                    // check if the sharedticket is the same
                    TicketInfo ticket = getTicketInfo(m_place);

                    // check
                    if (ticket == null && !m_place.hasPeople()) {
                        // Empty table and checked

                        // table occupied
                        ticket = new TicketInfo();
                        try {
                            dlReceipts.insertSharedTicket(m_place.getId(), ticket);
                        } catch (BasicException e) {
                            new MessageInf(e).show(JTicketsBagRestaurantMap.this); // Glup. But It was empty.
                        }                     
                        m_place.setPeople(true);
                        setActivePlace(m_place, ticket);

                    } else if (ticket == null  && m_place.hasPeople()) {
                        // The table is now empty
                        new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.tableempty")).show(JTicketsBagRestaurantMap.this);
                        m_place.setPeople(false); // fixed        

                    } else if (ticket != null && !m_place.hasPeople()) {
                        // The table is now full
                        new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.tablefull")).show(JTicketsBagRestaurantMap.this);       
                        m_place.setPeople(true);

                    } else { // both != null
                        // Full table                
                        // m_place.setPeople(true); // already true                           
                        setActivePlace(m_place, ticket);                   
                    }
                } else {
                    // receiving customer.
                    
                    // check if the sharedticket is the same
                    TicketInfo ticket = getTicketInfo(m_place);
                    if (ticket == null) {
                        // receive the customer
                        // table occupied
                        ticket = new TicketInfo();
                        
                        try {
                            ticket.setCustomer(customer.getId() == null
                                    ? null
                                    : dlSales.loadCustomerExt(customer.getId()));
                        } catch (BasicException e) {
                            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotfindcustomer"), e);
                            msg.show(JTicketsBagRestaurantMap.this);            
                        }                     
                        
                        try {
                            dlReceipts.insertSharedTicket(m_place.getId(), ticket);
                        } catch (BasicException e) {
                            new MessageInf(e).show(JTicketsBagRestaurantMap.this); // Glup. But It was empty.
                        }                     
                        m_place.setPeople(true);
                        
                        m_PlaceClipboard = null;
                        customer = null;     
                        
                        setActivePlace(m_place, ticket);                        
                    } else {
                        // TODO: msg: The table is now full
                        new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.tablefull")).show(JTicketsBagRestaurantMap.this);       
                        m_place.setPeople(true);
                        m_place.getButton().setEnabled(false);
                    }
                }
            } else {
                // check if the sharedticket is the same
                TicketInfo ticketclip = getTicketInfo(m_PlaceClipboard);

                if (ticketclip == null) {
                    new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.tableempty")).show(JTicketsBagRestaurantMap.this);
                    m_PlaceClipboard.setPeople(false);
                    m_PlaceClipboard = null;
                    customer = null;
                    printState();
                } else {
                    // tenemos que copiar el ticket del clipboard
                    if (m_PlaceClipboard == m_place) {
                        // the same button. Canceling.
                        Place placeclip = m_PlaceClipboard;                       
                        m_PlaceClipboard = null;
                        customer = null;
                        printState();
                        setActivePlace(placeclip, ticketclip);
                    } else if (!m_place.hasPeople()) {
                        // Moving the receipt to an empty table
                        TicketInfo ticket = getTicketInfo(m_place);

                        if (ticket == null) {
                            try {
                                dlReceipts.insertSharedTicket(m_place.getId(), ticketclip);
                                m_place.setPeople(true);
                                dlReceipts.deleteSharedTicket(m_PlaceClipboard.getId());
                                m_PlaceClipboard.setPeople(false);
                            } catch (BasicException e) {
                                new MessageInf(e).show(JTicketsBagRestaurantMap.this); // Glup. But It was empty.
                            }

                            m_PlaceClipboard = null;
                            customer = null;
                            printState();

                            // No hace falta preguntar si estaba bloqueado porque ya lo estaba antes
                            // activamos el ticket seleccionado
                            setActivePlace(m_place, ticketclip);
                        } else {
                            // Full table
                            new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.tablefull")).show(JTicketsBagRestaurantMap.this);
                            m_PlaceClipboard.setPeople(true);
                            printState();
                        }
                    } else {                          
                        // Merge the lines with the receipt of the table
                        TicketInfo ticket = getTicketInfo(m_place);

                        if (ticket == null) {
                            // The table is now empty
                            new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.tableempty")).show(JTicketsBagRestaurantMap.this);
                            m_place.setPeople(false); // fixed
                        } else {
                            //asks if you want to merge tables
                            if (JOptionPane.showConfirmDialog(JTicketsBagRestaurantMap.this, AppLocal.getIntString("message.mergetablequestion"), AppLocal.getIntString("message.mergetable"), JOptionPane.YES_NO_OPTION)
                                    == JOptionPane.YES_OPTION){                                 
                                // merge lines ticket

                                try {
                                    dlReceipts.deleteSharedTicket(m_PlaceClipboard.getId());
                                    m_PlaceClipboard.setPeople(false);
                                    if (ticket.getCustomer() == null) {
                                    ticket.setCustomer(ticketclip.getCustomer());
                                    }
                                    for(TicketLineInfo line : ticketclip.getLines()) {
                                        ticket.addLine(line);
                                    }
                                    dlReceipts.updateSharedTicket(m_place.getId(), ticket);
                                } catch (BasicException e) {
                                    new MessageInf(e).show(JTicketsBagRestaurantMap.this); // Glup. But It was empty.
                                }

                                m_PlaceClipboard = null;
                                customer = null;
                                printState();

                                setActivePlace(m_place, ticket);
                            } else { 
                                // Cancel merge operations
                                Place placeclip = m_PlaceClipboard;                       
                                m_PlaceClipboard = null;
                                customer = null;
                                printState();
                                setActivePlace(placeclip, ticketclip);                                   
                            }
                        }                                
                    }
                }
            }
        }
    }  
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        m_jPanelMap = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        m_jbtnReservations = new javax.swing.JButton();
        m_jbtnRefresh = new javax.swing.JButton();
        m_jText = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        m_jPanelMap.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        m_jbtnReservations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/date.png"))); // NOI18N
        m_jbtnReservations.setText(AppLocal.getIntString("button.reservations")); // NOI18N
        m_jbtnReservations.setFocusPainted(false);
        m_jbtnReservations.setFocusable(false);
        m_jbtnReservations.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jbtnReservations.setRequestFocusEnabled(false);
        m_jbtnReservations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnReservationsActionPerformed(evt);
            }
        });
        jPanel2.add(m_jbtnReservations);

        m_jbtnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/reload.png"))); // NOI18N
        m_jbtnRefresh.setText(AppLocal.getIntString("button.reloadticket")); // NOI18N
        m_jbtnRefresh.setFocusPainted(false);
        m_jbtnRefresh.setFocusable(false);
        m_jbtnRefresh.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jbtnRefresh.setRequestFocusEnabled(false);
        m_jbtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnRefreshActionPerformed(evt);
            }
        });
        jPanel2.add(m_jbtnRefresh);
        jPanel2.add(m_jText);

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        m_jPanelMap.add(jPanel1, java.awt.BorderLayout.NORTH);

        add(m_jPanelMap, "map");
    }// </editor-fold>//GEN-END:initComponents

    private void m_jbtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnRefreshActionPerformed

        m_PlaceClipboard = null;
        customer = null;
        loadTickets();     
        printState();   
        
    }//GEN-LAST:event_m_jbtnRefreshActionPerformed

    private void m_jbtnReservationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnReservationsActionPerformed

        showView("res");
        m_jreservations.activate();
        
    }//GEN-LAST:event_m_jbtnReservationsActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel m_jPanelMap;
    private javax.swing.JLabel m_jText;
    private javax.swing.JButton m_jbtnRefresh;
    private javax.swing.JButton m_jbtnReservations;
    // End of variables declaration//GEN-END:variables
    
}
