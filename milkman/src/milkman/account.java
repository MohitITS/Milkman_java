package milkman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class account extends javax.swing.JInternalFrame {

    int framestate; // 1=addmode 2=Editmode 3=viewmode
    Connection conn;
    ResultSet rs;
    int mainaccid[];
    int acctypeid[];
    int crdrid[];
    
    public account() {
        try {
            initComponents();

            if(conn!=null) {
                    conn.close();
            }            
            conn = methods.getConnection();        
            
            loadcombo();
            
            framestate=3;
            ED_BUTTONS(false, true);
            displayrecord(4);        
        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadcombo() {
        try {
            cbacctype.removeAll();
            String qry = "SELECT * FROM accounttype ORDER BY accountname;";
            try (PreparedStatement loadcombo = conn.prepareStatement(qry);ResultSet rslc = loadcombo.executeQuery()) {
                int i=0;
                acctypeid = new int[100];
                while (rslc.next()) {
                   cbacctype.insertItemAt(rslc.getString("accountname"), i);
                   acctypeid[i] = rslc.getInt("ID");
                   i++;
                }
            }
            cbcrdr.removeAll();
            qry = "SELECT crdr.ID, crdr.cr_dr FROM crdr ORDER BY ID;";
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
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void emptyctr() {
        txtkhateid.setText(null);
        cbacctype.setSelectedIndex(-1);
        txtaccname.setText(null);
        txtopbal.setText("0.0");
        txtperc.setText("0.0");
        cbcrdr.setSelectedIndex(-1);
    }

    private void ED_BUTTONS(boolean f1, boolean f2) {
        txtkhateid.setEditable(f2); // true
        cbacctype.setEnabled(f1);
        txtaccname.setEditable(f1);
        txtopbal.setEditable(f1);
        txtperc.setEditable(f1);
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
        jLabel2 = new javax.swing.JLabel();
        txtaccname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbacctype = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtopbal = new javax.swing.JTextField();
        cbcrdr = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtperc = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("Account");

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

        jLabel3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel3.setText("खाते आय. डी. :");

        txtkhateid.setEditable(false);
        txtkhateid.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel2.setText("खाते नाव :");

        txtaccname.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel4.setText("खाते प्रकार :");

        cbacctype.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbacctype.setBorder(null);
        cbacctype.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel5.setText("ओपनिंग ब्यालांस :");

        txtopbal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtopbal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtopbal.setText("0.0");
        txtopbal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtopbalActionPerformed(evt);
            }
        });

        cbcrdr.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbcrdr.setBorder(null);
        cbcrdr.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel6.setText("जमा / नावे :");

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel7.setText("% :");

        txtperc.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtperc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtperc.setText("0.0");
        txtperc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpercActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtkhateid, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtaccname, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel7)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtperc, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(84, 84, 84))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel5)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtopbal, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)))
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cbcrdr, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbacctype, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 120, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtkhateid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtaccname, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbacctype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtopbal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(cbcrdr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtperc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        framestate = 1;
        ED_BUTTONS(true, false);
        emptyctr();
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
                    String deleteqry = "DELETE * FROM account WHERE ID="+txtkhateid.getText()+";";
                    PreparedStatement DELETE_QRY = conn.prepareStatement(deleteqry);
                    DELETE_QRY.execute();
                    JOptionPane.showMessageDialog(null, "रेकॉर्ड काढून टाकण्यात आला आहे.", title, JOptionPane.INFORMATION_MESSAGE);
                    emptyctr();
                    displayrecord(4);
                } catch (SQLException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
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
                String insertqry = "INSERT INTO account (accname,acctypeid,opn_bal,crdr,perc) VALUES (?,?,?,?,?)";
                PreparedStatement INSERT_QRY = conn.prepareStatement(insertqry);
                INSERT_QRY.setString(1, txtaccname.getText());
                INSERT_QRY.setInt(2, acctypeid[cbacctype.getSelectedIndex()]);
//                System.out.println(txtopbal.getText());
                INSERT_QRY.setDouble(3, Double.parseDouble(txtopbal.getText()));
                INSERT_QRY.setInt(4, crdrid[cbcrdr.getSelectedIndex()]);
                INSERT_QRY.setDouble(5, Double.parseDouble(txtperc.getText()));

                INSERT_QRY.execute();
            }
            if(framestate==2){
                String updateqry = "UPDATE account SET accname = ?, acctypeid=?,opn_bal=?,crdr=?,perc=? WHERE ID = "+txtkhateid.getText()+";";
                PreparedStatement UPDATE_QRY = conn.prepareStatement(updateqry);
                UPDATE_QRY.setString(1, txtaccname.getText());
                UPDATE_QRY.setInt(2, acctypeid[cbacctype.getSelectedIndex()]);
                UPDATE_QRY.setDouble(3, Double.parseDouble(txtopbal.getText()));
                UPDATE_QRY.setInt(4, crdrid[cbcrdr.getSelectedIndex()]);
                UPDATE_QRY.setDouble(5, Double.parseDouble(txtperc.getText()));
                UPDATE_QRY.execute();
            }

            String msg = "रेकॉर्ड सेव्ह झाला.";
            JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

            framestate=3;
            ED_BUTTONS(false, true);
            displayrecord(4);

        } catch (SQLException ex) {
            Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(framestate==3){
            dispose();
            if (conn!=null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        displayrecord(4);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void txtopbalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtopbalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtopbalActionPerformed

    private void txtpercActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpercActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpercActionPerformed

    private void displayrecord(int flag) {
        if(framestate==3){
            try {
                String qry = null;
                if(flag==4){
                   qry = "SELECT * FROM account WHERE ID=(SELECT max(ID) FROM account);";
                }
                if(flag==1){
                   qry = "SELECT * FROM account WHERE ID=(SELECT min(ID) FROM account);";
                }
                if(flag==2){
                   qry = "SELECT * FROM account WHERE ID<"+txtkhateid.getText()+" ORDER BY ID DESC;";
                }
                if(flag==3){
                   qry = "SELECT * FROM account WHERE ID>"+txtkhateid.getText()+" ORDER BY ID;";
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
                    txtaccname.setText(rs.getString("accname"));
                    int accid = rs.getInt("acctypeid");
                    int k;
                    if (accid != 0) {
                        for (k=0; k<cbacctype.getItemCount(); k++) {
                            if(acctypeid[k]==accid) {
                                cbacctype.setSelectedIndex(k);
                                break;
                            }
                        }
                    }                    
                    txtopbal.setText(rs.getString("opn_bal"));
                    cbcrdr.setSelectedIndex(rs.getInt("crdr")-1);
                    txtperc.setText(rs.getString("perc"));
                    
                    break;
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(account.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbacctype;
    private javax.swing.JComboBox cbcrdr;
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
    private javax.swing.JTextField txtaccname;
    private javax.swing.JTextField txtkhateid;
    private javax.swing.JTextField txtopbal;
    private javax.swing.JTextField txtperc;
    // End of variables declaration//GEN-END:variables
}
