/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class accounttype extends javax.swing.JInternalFrame {

    int framestate; // 1=addmode 2=Editmode 3=viewmode
    Connection conn;
    ResultSet rs;
    int mainaccid[];
    int acctypeid[];
    int crdrid[];
    
    public accounttype() {
        try {
            initComponents();

            if(conn!=null) {
                conn.close();
            }            
            conn = methods.getConnection();
            cbmainacctype.removeAll();
            String qry = "SELECT * FROM mainaccount;";
            try (PreparedStatement loadcombo = conn.prepareStatement(qry); ResultSet rsmacc = loadcombo.executeQuery()) {
                int i=0;
                mainaccid = new int[4];
                while (rsmacc.next()) {
                    String insetritem = ""+rsmacc.getString("macode") + " - " + rsmacc.getString("acname");
//                    System.out.println(insetritem);
                    cbmainacctype.insertItemAt(insetritem, i);
                    mainaccid[i] = rsmacc.getInt("ID");
                    i++;
                }
            }
            
            loadcombo();
            
            framestate=3;
            ED_BUTTONS(false, true);
            displayrecord(4);
        } catch (SQLException ex) {
            Logger.getLogger(accounttype.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadcombo() {
        try {
            cbsecondaryacc.removeAllItems();
            String qry = "SELECT * FROM accounttype;";
            //PreparedStatement loadcombo = conn.prepareStatement(qry);
            try (PreparedStatement loadcombo = conn.prepareStatement(qry); ResultSet rslc = loadcombo.executeQuery()) {
                int i=0;
                acctypeid = new int[100];
                while (rslc.next()) {
                   cbsecondaryacc.insertItemAt(rslc.getString("accountname"), i);
                   acctypeid[i] = rslc.getInt("ID");
                   i++;
                }
            }
            cbcrdr.removeAllItems();
            qry = "SELECT crdr.ID, crdr.cr_dr FROM crdr;";
            try (PreparedStatement loadcombo = conn.prepareStatement(qry);ResultSet rslc = loadcombo.executeQuery()) {
                int i=0;
                crdrid = new int[2];
                while (rslc.next()) {
                   cbcrdr.insertItemAt(rslc.getString("cr_dr"), i);
                   crdrid[i] = rslc.getInt("ID");
                   i++;
                }
            }            
        } catch (SQLException ex) {
            Logger.getLogger(accounttype.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void emptyctr() {
        txtkhateid.setText(null);
        cbmainacctype.setSelectedIndex(-1);
        txtkhateprakar.setText(null);
        primaryacc.setSelected(false);
        secondaryacc.setSelected(false);
        cbsecondaryacc.setSelectedIndex(-1);
        calcacc.setSelected(false);
        chkusedfor_PL.setSelected(false);
        cbcrdr.setSelectedIndex(-1);
        txtcurrentacctypebalance.setText("0.0");
    }

    private void ED_BUTTONS(boolean f1, boolean f2) {
        txtkhateid.setEditable(f2); // true
        txtcurrentacctypebalance.setEditable(f1); // false
        cbmainacctype.setEnabled(f1);
        txtkhateprakar.setEditable(f1);
        primaryacc.setEnabled(f1);
        secondaryacc.setEnabled(f1);
        chkusedfor_PL.setEnabled(f1);
        cbsecondaryacc.setEnabled(f1);
        calcacc.setEnabled(f1);
        cbcrdr.setEnabled(f1);
        
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtkhateid = new javax.swing.JTextField();
        txtkhateprakar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        primaryacc = new javax.swing.JCheckBox();
        secondaryacc = new javax.swing.JCheckBox();
        cbsecondaryacc = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        calcacc = new javax.swing.JCheckBox();
        cbmainacctype = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        chkusedfor_PL = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        txtcurrentacctypebalance = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbcrdr = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("Account Type");

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
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(146, Short.MAX_VALUE))
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

        jLabel3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel3.setText("खाते आय. डी. :");

        txtkhateid.setEditable(false);
        txtkhateid.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        txtkhateprakar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel2.setText("खाते प्रकार :");

        primaryacc.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(primaryacc);
        primaryacc.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        primaryacc.setText("प्रायमरी खाते");

        secondaryacc.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(secondaryacc);
        secondaryacc.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        secondaryacc.setText("सेकंडरी खाते");

        cbsecondaryacc.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbsecondaryacc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cbsecondaryacc.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel4.setText("सेकंडरी खाते :");

        calcacc.setBackground(new java.awt.Color(255, 255, 255));
        calcacc.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        calcacc.setText("कॅलकुलेशन साठी वापरण्यात यावा (उदा. टॅक्स, डीस्कौंट, ई.)");

        cbmainacctype.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbmainacctype.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cbmainacctype.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel5.setText("मुख्य खाते प्रकार :");

        chkusedfor_PL.setBackground(new java.awt.Color(255, 255, 255));
        chkusedfor_PL.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        chkusedfor_PL.setText("नफा तोटा पत्रकासाठी वापरण्यात यावा");

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel6.setText("सध्याची शिल्लक :");

        txtcurrentacctypebalance.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtcurrentacctypebalance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtcurrentacctypebalance.setText("0.0");

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel7.setText("जमा / नावे :");

        cbcrdr.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbcrdr.setBorder(null);
        cbcrdr.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcurrentacctypebalance, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbcrdr, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(calcacc)
                                    .addComponent(chkusedfor_PL)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cbsecondaryacc, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtkhateid, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbmainacctype, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtkhateprakar, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(primaryacc)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                                            .addComponent(secondaryacc))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkhateid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbmainacctype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkhateprakar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(primaryacc)
                            .addComponent(secondaryacc))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbsecondaryacc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(calcacc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkusedfor_PL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbcrdr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtcurrentacctypebalance, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)))
                        .addGap(18, 39, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        framestate = 1;
        ED_BUTTONS(true, false);
        emptyctr();
        cbmainacctype.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        framestate = 2;
        ED_BUTTONS(true, false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String msg="रेकॉर्ड कायमचा काढून टाकणे?";
        int ans;
        if(!txtkhateid.getText().equals("")) {
            ans = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
            if(ans == JOptionPane.YES_OPTION){
                try {
                    if (conn!=null) {
                        conn.close();
                    }
                    conn = methods.getConnection();                    
                    String deleteqry = "DELETE * FROM accounttype WHERE ID="+txtkhateid.getText()+";";
                    PreparedStatement DELETE_QRY = conn.prepareStatement(deleteqry);
                    DELETE_QRY.execute();
                    JOptionPane.showMessageDialog(null, "रेकॉर्ड काढून टाकण्यात आला आहे.", title, JOptionPane.INFORMATION_MESSAGE);
                    emptyctr();
                    displayrecord(4);
                } catch (SQLException ex) {
                    Logger.getLogger(accounttype.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "रेकॉर्ड नाही..!!", title, JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            
            if (conn!=null) {
                conn.close();
            }
            conn = methods.getConnection();            
            // Insert new Record
            if(framestate==1){
                String insertqry = "INSERT INTO accounttype (accountname,mainaccountid,primaryacc,"+
                        "secondaryacc,primaryaccid,ufcalc,PL_used,curr_balance,crdr_id) VALUES (?,?,?,?,?,?,?,?,?)";
                PreparedStatement INSERT_QRY = conn.prepareStatement(insertqry);
                INSERT_QRY.setString(1, txtkhateprakar.getText());
                INSERT_QRY.setInt(2, mainaccid[cbmainacctype.getSelectedIndex()]);
                INSERT_QRY.setBoolean(3, primaryacc.isSelected());
                INSERT_QRY.setBoolean(4, secondaryacc.isSelected());
                if(secondaryacc.isSelected()==true){
                    INSERT_QRY.setInt(5, acctypeid[cbsecondaryacc.getSelectedIndex()]);
                } else {
                    INSERT_QRY.setString(5, null);
                }
                INSERT_QRY.setBoolean(6, calcacc.isSelected());
                INSERT_QRY.setBoolean(7, chkusedfor_PL.isSelected());
                INSERT_QRY.setDouble(8, Double.parseDouble(txtcurrentacctypebalance.getText()));
                if (cbcrdr.getSelectedIndex()!= -1) {
                    INSERT_QRY.setInt(9, crdrid[cbcrdr.getSelectedIndex()]);
                } else {
                    INSERT_QRY.setString(9, null);
                }
                
                INSERT_QRY.execute();
            }
            if(framestate==2){
                String updateqry = "UPDATE accounttype SET accountname = ?, mainaccountid=?,"+
                        "primaryacc=?,secondaryacc=?,primaryaccid=?,ufcalc=?,PL_used=?,curr_balance=?,crdr_id=? "+
                        "WHERE ID = "+txtkhateid.getText()+";";
                PreparedStatement UPDATE_QRY = conn.prepareStatement(updateqry);
                UPDATE_QRY.setString(1, txtkhateprakar.getText());
                UPDATE_QRY.setInt(2, mainaccid[cbmainacctype.getSelectedIndex()]);
                UPDATE_QRY.setBoolean(3, primaryacc.isSelected());
                UPDATE_QRY.setBoolean(4, secondaryacc.isSelected());
                if(secondaryacc.isSelected()==true){
                    UPDATE_QRY.setInt(5, acctypeid[cbsecondaryacc.getSelectedIndex()]);
                } else {
                    UPDATE_QRY.setString(5, null);
                }
                UPDATE_QRY.setBoolean(6, calcacc.isSelected());                
                UPDATE_QRY.setBoolean(7, chkusedfor_PL.isSelected());
                UPDATE_QRY.setDouble(8, Double.parseDouble(txtcurrentacctypebalance.getText()));
                if (cbcrdr.getSelectedIndex()!= -1) {
                    UPDATE_QRY.setInt(9, crdrid[cbcrdr.getSelectedIndex()]);
                } else {
                    UPDATE_QRY.setString(9, null);
                }
                
                UPDATE_QRY.execute();
            }

            String msg = "रेकॉर्ड सेव्ह झाला.";
            JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

            framestate=3;
            ED_BUTTONS(false, true);
            loadcombo();            
            displayrecord(4);

            } catch (SQLException ex) {
            Logger.getLogger(accounttype.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(framestate==3){
            dispose();
            if (conn!=null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(accounttype.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else {
            framestate = 3;
            ED_BUTTONS(false, true);
            displayrecord(4);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        displayrecord(1);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        displayrecord(2);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        displayrecord(3);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void displayrecord(int flag) {
        if(framestate==3){
            try {
                String qry = null;
                if(flag==4){
                   qry = "SELECT * FROM accounttype WHERE ID=(SELECT max(ID) FROM accounttype);";
                }
                if(flag==1){
                   qry = "SELECT * FROM accounttype WHERE ID=(SELECT min(ID) FROM accounttype);";
                }
                if(flag==2){
                   qry = "SELECT * FROM accounttype WHERE ID<"+txtkhateid.getText()+" ORDER BY ID DESC;";
                }
                if(flag==3){
                   qry = "SELECT * FROM accounttype WHERE ID>"+txtkhateid.getText()+" ORDER BY ID;";
                }
                if (conn!=null) {
                    conn.close();
                }
                conn = methods.getConnection();                
                PreparedStatement selectqry = conn.prepareStatement(qry);
                rs = selectqry.executeQuery();
                while(rs.next()){
                    emptyctr();
                    txtkhateid.setText(rs.getString("ID"));
                    txtkhateprakar.setText(rs.getString("accountname"));
                    cbmainacctype.setSelectedIndex(rs.getInt("mainaccountid")-1);
                    primaryacc.setSelected(rs.getBoolean("primaryacc"));
                    secondaryacc.setSelected(rs.getBoolean("secondaryacc"));
                    int accid = rs.getInt("primaryaccid");
                    //System.out.println(accid);
                    int k;
                    if (accid != 0) {
                        for (k=0; k<cbsecondaryacc.getItemCount(); k++) {
                            if(acctypeid[k]==accid) {
                                cbsecondaryacc.setSelectedIndex(k);
                                break;
                            }
                        }
                    } else {
                        cbsecondaryacc.setSelectedIndex(-1);
                    }
                    calcacc.setSelected(rs.getBoolean("ufcalc"));
                    
                    break;
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(accounttype.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        displayrecord(4);
    }//GEN-LAST:event_jButton10ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox calcacc;
    private javax.swing.JComboBox cbcrdr;
    private javax.swing.JComboBox cbmainacctype;
    private javax.swing.JComboBox cbsecondaryacc;
    private javax.swing.JCheckBox chkusedfor_PL;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JCheckBox primaryacc;
    private javax.swing.JCheckBox secondaryacc;
    private javax.swing.JTextField txtcurrentacctypebalance;
    private javax.swing.JTextField txtkhateid;
    private javax.swing.JTextField txtkhateprakar;
    // End of variables declaration//GEN-END:variables
}