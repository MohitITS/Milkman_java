/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.awt.FontFormatException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rakesh
 */
public class producer extends javax.swing.JInternalFrame {

    /**
     * Creates new form producer
     */
    
    private Connection conn;
    private ResultSet rs;
    private int framestate;
    private String[][] bankid;
    private int[] grid;
    private int[] rateid;
    private int id;
    
    DecimalFormat onedf = new DecimalFormat("#.#");
    
    private void loadcombo() {
        try {
            
            cmbgroup.removeAll();
            cmbbank.removeAll();
            cmbratechart.removeAll();
            int i=0;
            
            if(conn!=null) {
                conn.close();
            }
            conn = methods.getConnection();
            ResultSet rscombo;
            String qry;
            qry = "";
            
            // producer group
            qry = "SELECT producergroup.ID, producergroup.grname\n" +
                    "FROM producergroup\n" +
                    "ORDER BY producergroup.ID;";
            PreparedStatement loadqry = conn.prepareStatement(qry);
            rscombo = loadqry.executeQuery();
            grid = new int[30];
            while(rscombo.next()) {
               cmbgroup.insertItemAt(rscombo.getString("grname"), i);
               grid[i] = rscombo.getInt("ID");
               i++;
            }
            rscombo.close();
            cmbgroup.setSelectedIndex(0);
            
            // Ratechart
            qry = "SELECT rateChart.rtc_no " +
                    "FROM rateChart " +
                    "GROUP BY rateChart.rtc_no;";
            PreparedStatement ratechartid = conn.prepareStatement(qry);
            rscombo = ratechartid.executeQuery();
            rateid = new int[10];
            i = 0;
            while(rscombo.next()) {
               cmbratechart.insertItemAt(rscombo.getString("rtc_no"), i);
               //rateid[i] = rscombo.getInt("rtc_no");
               i++;
            }
            rscombo.close();
            
            
            if (cmbratechart.getItemCount() <= 0) {
                JOptionPane.showMessageDialog(null, "कृपया रेट चार्ट टाका....", "Milknan --> Producer", JOptionPane.INFORMATION_MESSAGE);
            } else {
                cmbratechart.setSelectedIndex(0);
            }
            
            // producer
            qry = "SELECT * FROM bank;";
            PreparedStatement loadcombo = conn.prepareStatement(qry);
            rscombo = loadcombo.executeQuery();
            i = 0;
            bankid = new String[20][3];
            while (rscombo.next()) {
               cmbbank.insertItemAt(rscombo.getString("bankname"), i);
               bankid[i][0] = ""+rscombo.getInt("ID");
               bankid[i][1] = rscombo.getString("branch");
               bankid[i][2] = rscombo.getString("ifsccode");
               i++;
            }
            rscombo.close();
            //conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public producer() throws FontFormatException, IOException {
            //this.newfont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("fonts/Mangal.ttf"));
            initComponents();
            
            loadcombo();
            emptyctr();
            framestate=3;
            ED_BUTTONS(false, true);
            displayrecord(4);
    }
    
    private void emptyctr() {
        txtproducerid.setText("");
        txtproducerid1.setText("");
        txtproducername.setText("");
        txtaddress.setText("");
        txtcontacno.setText("");
        chkmember.setSelected(true);
        cmbgroup.setSelectedIndex(0);
        if (cmbratechart.getItemCount() > 0) { cmbratechart.setSelectedIndex(0); }
        cmbbank.setSelectedIndex(-1);
        txtcommision.setText("0.00");
        txtshareanamat.setText("0.00");
        txtrebate.setText("0.00");
        txtchilingcharjes.setText("0.00");
        txtshareanamat.setText("0.00");
        txtacno.setText("");
        txtacno1.setText("");
        txtacno2.setText("");
        txtvehicalrent.setText("0.00");
    }
    
    private void ED_BUTTONS(boolean f1, boolean f2) {
        if(framestate==1 || framestate==2) {
            if (methods.prodcodemanual == true) {
                txtproducerid.setEditable(true);
            } else {
                txtproducerid.setEditable(false);
            }
        } else {
            txtproducerid.setEditable(true);
        }
        
        txtproducername.setEditable(f1); // true
        txtaddress.setEditable(f1); // true
        txtcontacno.setEditable(f1); // true
        chkmember.setEnabled(f1);
        //cmbgroup.setEnabled(f1);
        cmbratechart.setEnabled(f1);
        cmbbank.setEnabled(f1);
        txtshareanamat.setEditable(f1);
        txtcommision.setEditable(f1);
        txtrebate.setEditable(f1);
        txtchilingcharjes.setEditable(f1);
        txtshareanamat.setEditable(f1);
        txtacno.setEditable(f1);
        txtacno1.setEditable(f1);
        txtacno2.setEditable(f1);
        txtvehicalrent.setEditable(f1);
        
        btnprint.setEnabled(f2);

        // buttoms
        jButton1.setEnabled(f2); //new false
        jButton2.setEnabled(f2); //edit false
        jButton3.setEnabled(f2); //delete false 
        jButton4.setEnabled(f1); //save true
        jButton5.setEnabled(f2); //search false 
        if(framestate==1 || framestate==2){
            jButton6.setEnabled(f1); //exit true
            jButton6.setText("मागे"); 
        }
        else {
            jButton6.setEnabled(f2); //exit true
            jButton6.setText("बंद"); 
        }
        jButton7.setEnabled(f2); //first
        jButton8.setEnabled(f2); //previouse
        jButton9.setEnabled(f2); //next
        jButton10.setEnabled(f2); //last
    }    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        btnprint = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtcontacno = new javax.swing.JTextField();
        txtproducername = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaddress = new javax.swing.JTextArea();
        chkmember = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbratechart = new javax.swing.JComboBox();
        txtproducerid = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmbgroup = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cmbbank = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        txtacno = new javax.swing.JTextField();
        txtshareanamat = new javax.swing.JFormattedTextField();
        txtcommision = new javax.swing.JFormattedTextField();
        txtrebate = new javax.swing.JFormattedTextField();
        txtvehicalrent = new javax.swing.JFormattedTextField();
        txtchilingcharjes = new javax.swing.JFormattedTextField();
        txtproducerid1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbprodmilktype = new javax.swing.JComboBox();
        txtacno1 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtacno2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("उत्पादकाची माहिती");
        setFont(new java.awt.Font("Mangal", 1, 12)); // NOI18N
        setOpaque(true);

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jButton1.setBackground(new java.awt.Color(128, 157, 201));
        jButton1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton1.setText("नवीन");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.setBorderPainted(false);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton2.setText("बदल");
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton2.setBorderPainted(false);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton3.setText("डिलीट");
        jButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton3.setBorderPainted(false);
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton4.setMnemonic('s');
        jButton4.setText("सेव्ह");
        jButton4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton4.setBorderPainted(false);
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton5.setText("शोधणे");
        jButton5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton5.setBorderPainted(false);
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton6.setText("बंद");
        jButton6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton6.setBorderPainted(false);
        jButton6.setOpaque(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        btnprint.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        btnprint.setText("प्रिंट");
        btnprint.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnprint.setBorderPainted(false);
        btnprint.setOpaque(false);
        btnprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnprint, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprint, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(113, 168, 168));

        jButton7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton7.setText("पहिला");
        jButton7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton7.setBorderPainted(false);
        jButton7.setOpaque(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton8.setText("आगोदर");
        jButton8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton8.setBorderPainted(false);
        jButton8.setOpaque(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton9.setText("पुढचा");
        jButton9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton9.setBorderPainted(false);
        jButton9.setOpaque(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton10.setText("शेवटचा");
        jButton10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton10.setBorderPainted(false);
        jButton10.setOpaque(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel1.setText("उत्पादकाचे कोड :");

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel2.setText("उत्पादकाचे नाव :");

        jLabel3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel3.setText("संपर्क नंबर :");

        jLabel4.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel4.setText("पत्ता :");

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel5.setText("सभासद :");

        txtcontacno.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        txtproducername.setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N
        txtproducername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproducernameActionPerformed(evt);
            }
        });

        txtaddress.setColumns(20);
        txtaddress.setRows(5);
        jScrollPane1.setViewportView(txtaddress);

        chkmember.setBackground(new java.awt.Color(255, 255, 255));
        chkmember.setSelected(true);

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel6.setText("रेट चार्ट :");

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel7.setText("कमिशन प्रति ली. :");

        cmbratechart.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        cmbratechart.setActionCommand("");
        cmbratechart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbratechartActionPerformed(evt);
            }
        });

        txtproducerid.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        txtproducerid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproduceridActionPerformed(evt);
            }
        });
        txtproducerid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtproduceridKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtproduceridKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtproduceridKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel8.setText("शेअर अनामत :");

        jLabel9.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel9.setText("रिबेट :");

        jLabel10.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel10.setText("चिलिंग चार्जेस :");

        jLabel11.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel11.setText("गाडी भाडे प्रती ली. :");

        jLabel12.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel12.setText("ग्रुप :");

        cmbgroup.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        cmbgroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbgroupActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel13.setText("बँक :");

        cmbbank.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        cmbbank.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbbankItemStateChanged(evt);
            }
        });
        cmbbank.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbbankActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel14.setText("खाते नंबर :");

        txtacno.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        txtshareanamat.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtshareanamat.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtshareanamat.setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N

        txtcommision.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtcommision.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtcommision.setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N

        txtrebate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtrebate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtrebate.setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N

        txtvehicalrent.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtvehicalrent.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtvehicalrent.setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N

        txtchilingcharjes.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtchilingcharjes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtchilingcharjes.setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N

        txtproducerid1.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        txtproducerid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproducerid1ActionPerformed(evt);
            }
        });
        txtproducerid1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtproducerid1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtproducerid1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtproducerid1KeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel15.setText("उत्पादकाचे ID :");

        jLabel16.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel16.setText("दुध प्रकार : ");

        cmbprodmilktype.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        cmbprodmilktype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "COW", "BUFELOW" }));

        txtacno1.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel17.setText("शाखा :");

        txtacno2.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel18.setText("IFSC कोड :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtproducername, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                                    .addComponent(txtcontacno)
                                    .addComponent(jScrollPane1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtproducerid, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtproducerid1))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(txtchilingcharjes, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                                .addComponent(jLabel11))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(chkmember)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(cmbprodmilktype, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel12))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cmbratechart, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtshareanamat, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbgroup, javax.swing.GroupLayout.Alignment.TRAILING, 0, 123, Short.MAX_VALUE)
                                            .addComponent(txtcommision, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                            .addComponent(txtrebate, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                            .addComponent(txtvehicalrent, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)))
                                    .addComponent(txtacno)
                                    .addComponent(cmbbank, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtacno1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtacno2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtacno1, txtchilingcharjes});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(txtproducerid1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(txtproducerid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(11, 11, 11))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtproducername, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3)
                                            .addComponent(txtcontacno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(30, 30, 30)
                                                .addComponent(jLabel4)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(14, 14, 14)
                                                .addComponent(chkmember))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel16)
                                                    .addComponent(cmbprodmilktype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(2, 2, 2))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12)
                                        .addComponent(cmbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(cmbratechart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtcommision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(txtshareanamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtrebate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(txtvehicalrent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtchilingcharjes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbbank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtacno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtacno2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtacno1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        framestate = 1;
        ED_BUTTONS(true, false);
        emptyctr();        
        int prodid = getprodid();
        txtproducerid.setText(""+prodid);
        txtproducername.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        framestate = 2;
        ED_BUTTONS(true, false);        
        txtproducername.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(framestate==3){
            if(conn!=null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            dispose();
        }
        else {
            framestate = 3;
            ED_BUTTONS(false, true);
            displayrecord(4);
        }        
    }//GEN-LAST:event_jButton6ActionPerformed
    
    // method to get producer id
    /**
     *
     * @return
     */
    public int getprodid() {
        try {
            //System.out.println(cmbgroup.getSelectedIndex());
            //System.out.println(grid[cmbgroup.getSelectedIndex()]);
            int rid;
            String qry;
            qry = "";
            ResultSet rsid;
            if(conn!=null) {
                conn.close();
            }
            conn = methods.getConnection();
            //System.out.println(methods.prodcodegroupwise);
            if (methods.prodcodegroupwise == false) {
                qry = "SELECT Max(producer.ID) AS MaxOfID\n" +
                    "FROM producer;"; 
            } else {
                qry = "SELECT Max(producer.ID) AS MaxOfID FROM producer " +
                    "WHERE (((producer.producergroupid)="+grid[cmbgroup.getSelectedIndex()]+"));";
            }
            PreparedStatement maxid = conn.prepareStatement(qry);
            rsid = maxid.executeQuery();
            while(rsid.next()) {
                rid = rsid.getInt("MaxOfID");
                System.out.println(rid);
                if(rid == 0){
                    id = 1;
                } else {
                    id = rid + 1;
                }
                break;
            }
            rsid.close();
            //conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(producer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            if (cmbratechart.getItemCount() > 0) {
                if(conn!=null){
                    conn.close();
                }
                conn = methods.getConnection();

                // Validation - If the producer code exist with the same group
                boolean producer_exist = false;
                if(framestate == 1) {
                    String valqry = "SELECT producer.ID, producer.producergroupid FROM producer" +
                            " WHERE (((producer.ID)="+txtproducerid.getText().trim()+") AND ((producer.producergroupid)="+grid[cmbgroup.getSelectedIndex()]+"));";
                    PreparedStatement pr = conn.prepareStatement(valqry);
                    ResultSet rs = pr.executeQuery();
                    while (rs.next()) {
                        producer_exist = true;
                    }
                    rs.close();
                }
                if (producer_exist == false) {
                    // Insert new Record
                    if(framestate == 1) {
                        String insertqry = "INSERT INTO producer (pro_name, "
                                + "addr,"
                                + "contact_no, "
                                + "member, "
                                + "rtc_no, "
                                + "comission,"
                                + "ribet, "
                                + "share_amt,"
                                + "chiling_amt, "
                                + "producergroupid, "
                                + "bankid, "
                                + "acno, "
                                + "veh_exp, "
                                + "ID, "
                                + "prod_milktype, "
                                + "branch, "
                                + "ifsccode)"
                                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement INSERT_QRY = conn.prepareStatement(insertqry);
                        INSERT_QRY.setString(1, txtproducername.getText());
                        INSERT_QRY.setString(2, txtaddress.getText());
                        INSERT_QRY.setString(3, txtcontacno.getText());
                        INSERT_QRY.setBoolean(4, chkmember.isSelected());
                        INSERT_QRY.setInt(5, Integer.parseInt(""+cmbratechart.getSelectedItem()));
                        INSERT_QRY.setDouble(6, Double.parseDouble(txtcommision.getText()));
                        INSERT_QRY.setDouble(7, Double.parseDouble(txtrebate.getText()));
                        INSERT_QRY.setDouble(8, Double.parseDouble(txtshareanamat.getText()));
                        INSERT_QRY.setDouble(9, Double.parseDouble(txtchilingcharjes.getText()));
                        INSERT_QRY.setInt(10, grid[cmbgroup.getSelectedIndex()]);
                        if(cmbbank.getSelectedIndex()==-1){
                            INSERT_QRY.setString(11, null);
                        } else {
                            INSERT_QRY.setInt(11, Integer.parseInt(bankid[cmbbank.getSelectedIndex()][0]));
                        }
                        INSERT_QRY.setString(12, txtacno.getText());
                        INSERT_QRY.setDouble(13, Double.parseDouble(txtvehicalrent.getText()));
                        INSERT_QRY.setString(14, txtproducerid.getText());
                        INSERT_QRY.setString(15, ""+cmbprodmilktype.getSelectedItem());
                        if(cmbbank.getSelectedIndex()!=-1){
                            INSERT_QRY.setString(16, bankid[cmbbank.getSelectedIndex()][1]);
                            INSERT_QRY.setString(17, bankid[cmbbank.getSelectedIndex()][2]);
                        } else {
                            INSERT_QRY.setString(16, null);
                            INSERT_QRY.setString(17, null);
                        }

                        INSERT_QRY.execute();

                    }
                    // Update Existing record
                    if(framestate==2){
                        String updateqry = "UPDATE producer SET pro_name = ?, addr = ?,"
                                + " contact_no = ?, member = ?, rtc_no = ?, comission = ?,"
                                + " ribet= ?, share_amt = ?, chiling_amt = ?, "
                                + " producergroupid = ?, bankid = ?, acno = ?, veh_exp = ?, prod_milktype = ?, "
                                + " branch = ?, ifsccode = ? "
                                + " WHERE prod_id = "+txtproducerid1.getText()+";";
                        PreparedStatement UPDATE_QRY = conn.prepareStatement(updateqry);
                        UPDATE_QRY.setString(1, txtproducername.getText().toString());
                        UPDATE_QRY.setString(2, txtaddress.getText());
                        UPDATE_QRY.setString(3, txtcontacno.getText());
                        UPDATE_QRY.setBoolean(4, chkmember.isSelected());
                        UPDATE_QRY.setInt(5, Integer.parseInt(""+cmbratechart.getSelectedItem()));
                        UPDATE_QRY.setDouble(6, Double.parseDouble(txtcommision.getText()));
                        UPDATE_QRY.setDouble(7, Double.parseDouble(txtrebate.getText()));
                        UPDATE_QRY.setDouble(8, Double.parseDouble(txtshareanamat.getText()));
                        UPDATE_QRY.setDouble(9, Double.parseDouble(txtchilingcharjes.getText()));
                        UPDATE_QRY.setInt(10, grid[cmbgroup.getSelectedIndex()]);
                        if(cmbbank.getSelectedIndex()==-1){
                            UPDATE_QRY.setString(11, null);
                        } else {
                            UPDATE_QRY.setInt(11, Integer.parseInt(bankid[cmbbank.getSelectedIndex()][0]));
                        }
                        UPDATE_QRY.setString(12, txtacno.getText());
                        UPDATE_QRY.setDouble(13, Double.parseDouble(txtvehicalrent.getText()));
                        UPDATE_QRY.setString(14, ""+cmbprodmilktype.getSelectedItem());
                        if(cmbbank.getSelectedIndex()!=-1){
                            UPDATE_QRY.setString(15, bankid[cmbbank.getSelectedIndex()][1]);
                            UPDATE_QRY.setString(16, bankid[cmbbank.getSelectedIndex()][2]);
                        } else {
                            UPDATE_QRY.setString(15, null);
                            UPDATE_QRY.setString(16, null);
                        }
                        UPDATE_QRY.execute();
                    }
                    //conn.close();
                    String msg = "रेकॉर्ड सेव्ह झाला.";
                    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

                    if(framestate == 1){
                        displayrecord(4);
                    }
                    framestate=3;
                    ED_BUTTONS(false, true);
                } // producer exist
                else {
                    String msg = "Producer already exist. Please try another Producer Code or Group.";
                    JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
                }
            } else { // Rate chart if block
                JOptionPane.showMessageDialog(null, "कृपया रेट चार्ट टाका..!!", "Milkman --> Producer", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(producer.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void displayrecord(int flag) {
        if(framestate==3){
            try {
                if(conn!=null){
                    conn.close();
                }
                conn = methods.getConnection();
                String qry = null;
                if(flag==4){
                   qry = "SELECT * FROM producer WHERE ID=(SELECT max(ID) as maxid FROM producer) and producergroupid = "+grid[cmbgroup.getSelectedIndex()]+";";
                }
                if(flag==1){
                   qry = "SELECT * FROM producer WHERE ID = (SELECT min(ID) as minid FROM producer) and producergroupid = "+grid[cmbgroup.getSelectedIndex()]+";";
                }
                if(flag==2){
                   qry = "SELECT * FROM producer WHERE ID<"+txtproducerid.getText()+" and producergroupid = "+grid[cmbgroup.getSelectedIndex()]+" ORDER BY ID DESC;";
                }
                if(flag==3){
                   qry = "SELECT * FROM producer WHERE ID>"+txtproducerid.getText()+" and producergroupid = "+grid[cmbgroup.getSelectedIndex()]+" ORDER BY ID;";
                }                
                if(flag==5){
                   qry = "SELECT * FROM producer WHERE ID="+txtproducerid.getText()+" and producergroupid = "+grid[cmbgroup.getSelectedIndex()]+" ORDER BY ID;";
                }                                
                PreparedStatement selectqry = conn.prepareStatement(qry);
                rs = selectqry.executeQuery();
                
                //System.out.println(qry);
                
                while(rs.next()){
                    emptyctr();
                    //System.out.println(flag);
                    txtproducerid.setText(rs.getString("ID"));
                    txtproducerid1.setText(rs.getString("prod_id"));
                    String proname = rs.getString("pro_name");
                    txtproducername.setText(proname);
                    txtaddress.setText(rs.getString("addr"));
                    txtcontacno.setText(rs.getString("contact_no"));
                    chkmember.setSelected(rs.getBoolean("member"));
                    //txt.setText(rs.getString("pro_name"));
                    int rn = rs.getInt("rtc_no");
                    int i;
                    for (i=0;i<cmbratechart.getItemCount();i++){
                        String cmbrate = (String) cmbratechart.getItemAt(i);
                        if(cmbrate.equals(""+rn)){
                            cmbratechart.setSelectedIndex(i);
                            break;
                        }
                    }
                    txtcommision.setText(rs.getString("comission"));
                    txtrebate.setText(rs.getString("ribet"));
                    txtshareanamat.setText(rs.getString("share_amt"));
                    txtchilingcharjes.setText(rs.getString("chiling_amt"));
                    //cmbgroup.setSelectedIndex(0);
                    int gid = rs.getInt("producergroupid");
                    int k;
                    for(k=0; k<cmbgroup.getItemCount(); k++){
                        if(gid==grid[k]){
                            cmbgroup.setSelectedIndex(k);
                            break;
                        }
                    }
                    int bid = rs.getInt("bankid");
                    for(k=0; k<cmbbank.getItemCount(); k++){
                        if(bid==Integer.parseInt(bankid[k][0])){
                            cmbbank.setSelectedIndex(k);
                            break;
                        }
                    }
                    txtacno.setText(rs.getString("acno"));
                    txtacno1.setText(rs.getString("branch"));
                    txtacno2.setText(rs.getString("ifsccode"));
                    txtvehicalrent.setText(rs.getString("veh_exp"));
                    if (rs.getString("prod_milktype").equals("COW")) {
                        cmbprodmilktype.setSelectedIndex(0);
                    } else {
                        cmbprodmilktype.setSelectedIndex(1);
                    }

                    break;
                }
                rs.close();
                //conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String msg="रेकॉर्ड कायमचा काढून टाकणे?";
        int ans;
        if(!txtproducerid.getText().equals("")) {
        ans = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
        if(ans == JOptionPane.YES_OPTION){
            try {
                if(conn!=null){
                    conn.close();
                }
                conn=methods.getConnection();
                String deleteqry = "DELETE * FROM producer WHERE ID="+txtproducerid.getText()+" and producer.producergroupid = "+grid[cmbgroup.getSelectedIndex()]+";";
                PreparedStatement DELETE_QRY = conn.prepareStatement(deleteqry);
                DELETE_QRY.execute();
                JOptionPane.showMessageDialog(null, "रेकॉर्ड काढून टाकण्यात आला आहे.", title, JOptionPane.INFORMATION_MESSAGE);
                emptyctr();
                displayrecord(4);
            } catch (SQLException ex) {
                Logger.getLogger(producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } else {
            JOptionPane.showMessageDialog(null, "रेकॉर्ड नाही..!!", title, JOptionPane.INFORMATION_MESSAGE);
        }        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(!txtproducerid.getText().equals("")){
            displayrecord(1);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if(!txtproducerid.getText().equals("")){
        displayrecord(2);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        if(!txtproducerid.getText().equals("")){
            displayrecord(3);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if(!txtproducerid.getText().equals("")){
            displayrecord(4);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        displayrecord(5);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtproduceridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproduceridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproduceridActionPerformed

    private void txtproduceridKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproduceridKeyReleased
        // TODO add your handling code here:
        //System.out.println(evt.getKeyCode());
        if(evt.getKeyCode()!=10){
        } else {
            displayrecord(5);
        }
    }//GEN-LAST:event_txtproduceridKeyReleased

    private void txtproduceridKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproduceridKeyPressed
        // TODO add your handling code here:
        //System.out.println(evt.getKeyCode());
    }//GEN-LAST:event_txtproduceridKeyPressed

    private void txtproduceridKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproduceridKeyTyped
        // TODO add your handling code here:
        //System.out.println(evt.getKeyCode());
    }//GEN-LAST:event_txtproduceridKeyTyped

    private void btnprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintActionPerformed
        try {
            if(conn!=null) {
                conn.close();
            }
            conn = methods.getConnection();
            methods.displayreport("Producerlist", conn);
        } catch (SQLException ex) { //JRException | 
            Logger.getLogger(producer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btnprintActionPerformed

    private void cmbgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbgroupActionPerformed
        if(cmbgroup.getItemCount() > 0 && framestate != 3 && framestate != 2) {
            int prodid = getprodid();
            txtproducerid.setText(""+prodid); 
        } else if (framestate == 3){
            //displayrecord(4);
        }
    }//GEN-LAST:event_cmbgroupActionPerformed

    private void txtproducerid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproducerid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducerid1ActionPerformed

    private void txtproducerid1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproducerid1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducerid1KeyPressed

    private void txtproducerid1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproducerid1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducerid1KeyReleased

    private void txtproducerid1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproducerid1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducerid1KeyTyped

    private void txtproducernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproducernameActionPerformed
        
    }//GEN-LAST:event_txtproducernameActionPerformed

    private void cmbratechartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbratechartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbratechartActionPerformed

    private void cmbbankActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbbankActionPerformed

    }//GEN-LAST:event_cmbbankActionPerformed

    private void cmbbankItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbbankItemStateChanged
        if(cmbbank.getSelectedIndex()!= -1 && bankid.length > 0 && framestate != 3) {
            txtacno1.setText(""+bankid[cmbbank.getSelectedIndex()][1]);
            txtacno2.setText(""+bankid[cmbbank.getSelectedIndex()][2]);
        }
    }//GEN-LAST:event_cmbbankItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnprint;
    private javax.swing.JCheckBox chkmember;
    private javax.swing.JComboBox cmbbank;
    private javax.swing.JComboBox cmbgroup;
    private javax.swing.JComboBox cmbprodmilktype;
    private javax.swing.JComboBox cmbratechart;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtacno;
    private javax.swing.JTextField txtacno1;
    private javax.swing.JTextField txtacno2;
    private javax.swing.JTextArea txtaddress;
    private javax.swing.JFormattedTextField txtchilingcharjes;
    private javax.swing.JFormattedTextField txtcommision;
    private javax.swing.JTextField txtcontacno;
    private javax.swing.JTextField txtproducerid;
    private javax.swing.JTextField txtproducerid1;
    private javax.swing.JTextField txtproducername;
    private javax.swing.JFormattedTextField txtrebate;
    private javax.swing.JFormattedTextField txtshareanamat;
    private javax.swing.JFormattedTextField txtvehicalrent;
    // End of variables declaration//GEN-END:variables
}
