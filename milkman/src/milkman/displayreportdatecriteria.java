/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rakesh
 */
public class displayreportdatecriteria extends javax.swing.JInternalFrame {

    /**
     * Creates new form displayreportdatecriteria
     */
    
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    String reporttodispalay;
    Connection conn;
    String qry = "";
    int[] sanghid;
    int[] prodgrid;
    private Date date = new Date();
    SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dm = new SimpleDateFormat("dd/MM");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    
    public displayreportdatecriteria(String reporttodisplay) {
        try {
            initComponents();
    //        setLocation((d.width-getWidth())/2, (d.height-getHeight())/2);
            if(conn!=null){
                conn.close();
            }
            conn = methods.getConnection();  
            dtpfromdate.setDate(date);
            dtptodate.setDate(date);
            this.reporttodispalay = reporttodisplay;
            switch (reporttodisplay) {
                case "Dispatch Register":
                case "MT Register":
                    try {
                        ResultSet rscombo;
                        //String qry;        
                        qry = "SELECT sangh.ID, sangh.sname FROM sangh ORDER BY sangh.ID;";
                        PreparedStatement ratechartid = conn.prepareStatement(qry);
                        rscombo = ratechartid.executeQuery();
                        sanghid = new int[30];
                        int i = 0;
                        while(rscombo.next()) {
                           cmbsangh.insertItemAt(rscombo.getString("sname"), i);
                           sanghid[i] = rscombo.getInt("ID");
                           i++;
                        }
                        cmbsangh.setEnabled(true);
                        chkall.setSelected(true);
                        cmbsangh.setSelectedIndex(0);
                        rscombo.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(displayreportdatecriteria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "Bachat Register":
                case "Producer Milk Bill":
                case "Producer Milk Bill - Bank Register (Namuna - 1)":
                case "Producer Milk Bill - Bank Register (Namuna - 2)":
                case "Kapat Register":
                    try {
                        jLabel7.setText("उत्पादक :");
                        ResultSet rscombo;
                        //String qry;        
                        qry = "SELECT producergroup.ID, producergroup.grname FROM producergroup ORDER BY producergroup.ID;";
                        PreparedStatement ratechartid = conn.prepareStatement(qry);
                        rscombo = ratechartid.executeQuery();
                        prodgrid = new int[30];
                        int i = 0;
                        while(rscombo.next()) {
                           cmbprodgroup.insertItemAt(rscombo.getString("grname"), i);
                           prodgrid[i] = rscombo.getInt("ID");
                           i++;
                        }
                        cmbsangh.setEnabled(true);
                        chkall.setSelected(true);
//                        qry = "SELECT producer.prod_id, [producer]![ID] & ' - ' & [producer]![pro_name] AS Expr1," +
//                                " producer.producergroupid FROM producer" +
//                                //" WHERE (((producer.producergroupid)=1))" +
//                                " ORDER BY producer.ID;";
//                        System.out.println(qry);
//                        PreparedStatement ratechartid = conn.prepareStatement(qry);
//                        rscombo = ratechartid.executeQuery();
//                        sanghid = new int[10000];
//                        int i = 0;
//                        while(rscombo.next()) {
//                           cmbsangh.insertItemAt(rscombo.getString("Expr1"), i);
//                           sanghid[i] = rscombo.getInt("prod_id");
//                           i++;
//                        }
//                        cmbsangh.setSelectedIndex(0);
                        rscombo.close();
                        chksummary.setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(displayreportdatecriteria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                
            }
        } catch (SQLException ex) {
            Logger.getLogger(displayreportdatecriteria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dtpfromdate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dtptodate = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        cmbsangh = new javax.swing.JComboBox();
        chkall = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        chksummary = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        cmbprodgroup = new javax.swing.JComboBox();
        chksummary1 = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("तारखे प्रमाणे रिपोर्ट बघणे");

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 24)); // NOI18N
        jLabel5.setText("तारखे प्रमाणे रिपोर्ट बघणे");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5)
                .addContainerGap(374, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("तारखे पासून :");

        dtpfromdate.setFocusable(false);
        dtpfromdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel8.setText("ते");

        dtptodate.setFocusable(false);
        dtptodate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("संघ :");

        cmbsangh.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cmbsangh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbsanghActionPerformed(evt);
            }
        });

        chkall.setBackground(new java.awt.Color(255, 255, 255));
        chkall.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        chkall.setText("सर्व");

        jPanel2.setBackground(new java.awt.Color(113, 168, 168));

        jButton1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton1.setText("रिपोर्ट बघणे");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton2.setText("बाहेर");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chksummary.setBackground(new java.awt.Color(255, 255, 255));
        chksummary.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        chksummary.setText("समरी");
        chksummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chksummaryActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("उत्पादक ग्रुप :");

        cmbprodgroup.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cmbprodgroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbprodgroupActionPerformed(evt);
            }
        });

        chksummary1.setBackground(new java.awt.Color(255, 255, 255));
        chksummary1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        chksummary1.setText("रिसिप्ट साईज");
        chksummary1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chksummary1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(43, 43, 43)
                                    .addComponent(jLabel6)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(dtpfromdate, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(dtptodate, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cmbprodgroup, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(chkall, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(41, 41, 41)
                                    .addComponent(chksummary)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chksummary1))
                                .addComponent(cmbsangh, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtpfromdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(dtptodate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbprodgroup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbsangh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkall)
                    .addComponent(chksummary)
                    .addComponent(chksummary1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(displayreportdatecriteria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String cond_qry;
        if(this.reporttodispalay.equals("Dispatch Register") && chkall.isSelected() == true) {
            cond_qry = "milkdispatch.disp_datetime >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkdispatch.disp_datetime2 <= #"+ mdy.format(dtptodate.getDate()) +"#";
            try {
                methods.displayreport("dispatchreport", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(this.reporttodispalay.equals("Dispatch Register") && chkall.isSelected() == false) {
            cond_qry = "milkdispatch.disp_datetime >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkdispatch.disp_datetime2 <= #"+ mdy.format(dtptodate.getDate()) +"# and milkdispatch.scode = " + sanghid[cmbsangh.getSelectedIndex()];
            try {
                methods.displayreport("dispatchreport", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("MT Register") && chkall.isSelected() == true) {
            cond_qry = "moneytransfer.mtdate >= #"+ mdy.format(dtpfromdate.getDate()) +"# and moneytransfer.mtdate <= #"+ mdy.format(dtptodate.getDate()) +"#";
            try {
                methods.displayreport("moneytransfer_reg", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(this.reporttodispalay.equals("MT Register") && chkall.isSelected() == false) {
            cond_qry = "moneytransfer.mtdate >= #"+ mdy.format(dtpfromdate.getDate()) +"# and moneytransfer.mtdate <= #"+ mdy.format(dtptodate.getDate()) +"# and moneytransfer.snaghcode = " + sanghid[cmbsangh.getSelectedIndex()] + " ORDER BY moneytransfer.mtdate";
            try {
                methods.displayreport("moneytransfer_reg", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Bachat Register") && chkall.isSelected() == true && chksummary.isSelected() == false) {
            // Display all producer records page wise
            cond_qry = "prodbilldeduction.dedfromdate >= #"+ mdy.format(dtpfromdate.getDate()) +"# and prodbilldeduction.dedtodate <= #"+ mdy.format(dtptodate.getDate()) +"#";
            try {
                methods.displayreport("shetkaribachat", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(this.reporttodispalay.equals("Bachat Register") && chkall.isSelected() == false && chksummary.isSelected() == false) {
            // Display one producer records 
            cond_qry = "prodbilldeduction.dedfromdate >= #"+ mdy.format(dtpfromdate.getDate()) +"# and prodbilldeduction.dedtodate <= #"+ mdy.format(dtptodate.getDate()) +"# and prodbilldeduction.pro_code = " + sanghid[cmbsangh.getSelectedIndex()];
            try {
                methods.displayreport("shetkaribachat", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Bachat Register") && chksummary.isSelected() == true && chkall.isSelected() == false) {
            // Display only summary
            cond_qry = "prodbilldeduction.dedfromdate >= #"+ mdy.format(dtpfromdate.getDate()) +"# and prodbilldeduction.dedtodate <= #"+ mdy.format(dtptodate.getDate()) +"#";
            try {
                methods.displayreport("rebatedeductionsummary", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill") && chkall.isSelected() == true && chksummary1.isSelected() == false) {
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()];
            System.out.println(cond_qry);
            try {
                //milkbill
                methods.displayreport_subreport("milkbill", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill") && chkall.isSelected() == false && chksummary1.isSelected() == false) {
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and milkCollectionBill.prod_id = " + sanghid[cmbsangh.getSelectedIndex()] + " and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()];
            System.out.println(cond_qry);
            try {
                // rptmilkbill_recieptsize
                methods.displayreport_subreport("milkbill", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill - Bank Register (Namuna - 1)") && chkall.isSelected() == true) {
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()] + " and producer.bankid IS NOT null AND producer.acno IS NOT null AND milkCollectionBill.amt_payable <> 0";
            System.out.println(cond_qry);
            try {
                //milkbill
                methods.displayreport_subreport("bankpaymentreg", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill - Bank Register (Namuna - 2)") && chkall.isSelected() == true) {
            cond_qry = "qry_bank_payment_reg_namuna_2.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and qry_bank_payment_reg_namuna_2.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and qry_bank_payment_reg_namuna_2.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()] + " and qry_bank_payment_reg_namuna_2.bankid IS NOT null AND qry_bank_payment_reg_namuna_2.acno IS NOT null AND qry_bank_payment_reg_namuna_2.amt_payable <> 0";
            System.out.println(cond_qry);
            try {
                //milkbill
                methods.displayreport_subreport("bankpaymentreg_namuna_2", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill") && chkall.isSelected() == true && chksummary1.isSelected() == true) {
            getRecieptSizeBill();
        } else if(this.reporttodispalay.equals("Kapat Register") && chkall.isSelected() == true) {
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"#";
            System.out.println(cond_qry);
            try {
                //milkbill
                methods.displayreport_subreport("deductionregister", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private String leftpad(String text, int length) {
        return String.format("%" + length + "." + length + "s", text);
    }

    private String rightpad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }
    
    private static String center_string(String text, int len){
        String out = String.format("%"+len+"s%s%"+len+"s", "",text,"");
        float mid = (out.length()/2);
        float start = mid - (len/2);
        float end = start + len; 
        return out.substring((int)start, (int)end);
    }
    
    private void getRecieptSizeBill() {
        int prod_id = 0;
        int prod_code = 0;
        String prod_name;
        double comm;
        double commission = 0;
        double totmilk = 0;
        double avg_fat = 0;
        double avg_snf = 0;
        double avg_rate = 0;
        double tot_amt = 0;
        double anamat_per = 0;      // per liter
        double rebate_per = 0;      // per liter
        double vehcharg_per = 0;    // per liter
        double chilcharg_per = 0;   // per liter
        double anamat = 0;      // per liter
        double rebate = 0;      // per liter
        double vehcharg = 0;    // per liter
        double chilcharg = 0;   // per liter
        double pashukhadya = 0;
        double milkbilladv = 0;
        double milkbilladvbal = 0;
        double docfee = 0;
        double otherded = 0;
        double totdeduction = 0;
        double amt_payable = 0;
        double saurmilkamt = 0;
        
        ArrayList<String> obj = new ArrayList<String>();
         
        String compname = methods.firmname;
        String line_break = "---------------------------------";
        String bill_period =  dmy.format(dtpfromdate.getDate()) + " To " + dmy.format(dtptodate.getDate());
        
        try {
            
            if (cmbprodgroup.getSelectedIndex()==0) {
                qry = "SELECT milkCollectionBill.prod_code, "
                          + "milkCollectionBill.prod_name, "
                          + "milkCollectionBill.tot_milk, "
                          + "milkCollectionBill.avg_FAT, "
                          + "milkCollectionBill.avg_rate, "
                          + "milkCollectionBill.tot_milkamount, "
                          + "milkCollectionBill.tot_commision, "
                          + "milkCollectionBill.tot_sh_anamat, "
                          + "milkCollectionBill.tot_ribet, "
                          + "milkCollectionBill.chiling_charges, "
                          + "milkCollectionBill.travalingexp, "
                          + "milkCollectionBill.pashukhadya, "
                          + "milkCollectionBill.mlkbilladv, "
                          + "milkCollectionBill.docfee, "
                          + "milkCollectionBill.sourmlkamt, "
                          + "milkCollectionBill.amt_payable, "
                          + "milkCollectionBill.advancebal, "
                          + "milkCollectionBill.prod_id, "
                          + "milkCollectionBill.from_date, "
                          + "milkCollectionBill.to_date, "
                          + "milkCollectionBill.otherded, "
                          + "producer.producergroupid "
                          + "FROM producer "
                          + "INNER JOIN milkCollectionBill "
                          + "ON producer.prod_id = milkCollectionBill.prod_id "
                          + "WHERE (((milkCollectionBill.from_date)>=#"+mdy.format(dtpfromdate.getDate())+"#) "
                          + "AND ((milkCollectionBill.to_date)<=#"+mdy.format(dtptodate.getDate())+"#)) "
                          + "ORDER BY milkCollectionBill.prod_code, producer.producergroupid;";
            } else {
                  qry = "SELECT milkCollectionBill.prod_code, "
                          + "milkCollectionBill.prod_name, "
                          + "milkCollectionBill.tot_milk, "
                          + "milkCollectionBill.avg_FAT, "
                          + "milkCollectionBill.avg_rate, "
                          + "milkCollectionBill.tot_milkamount, "
                          + "milkCollectionBill.tot_commision, "
                          + "milkCollectionBill.tot_sh_anamat, "
                          + "milkCollectionBill.tot_ribet, "
                          + "milkCollectionBill.chiling_charges, "
                          + "milkCollectionBill.travalingexp, "
                          + "milkCollectionBill.pashukhadya, "
                          + "milkCollectionBill.mlkbilladv, "
                          + "milkCollectionBill.docfee, "
                          + "milkCollectionBill.sourmlkamt, "
                          + "milkCollectionBill.amt_payable, "
                          + "milkCollectionBill.otherded, "
                          + "milkCollectionBill.advancebal, "
                          + "milkCollectionBill.prod_id, "
                          + "milkCollectionBill.from_date, "
                          + "milkCollectionBill.to_date, "
                          + "producer.producergroupid "
                          + "FROM producer "
                          + "INNER JOIN milkCollectionBill "
                          + "ON producer.prod_id = milkCollectionBill.prod_id "
                          + "WHERE (((milkCollectionBill.from_date)>=#"+mdy.format(dtpfromdate.getDate())+"#) "
                          + "AND ((milkCollectionBill.to_date)<=#"+mdy.format(dtptodate.getDate())+"#) "
                          + "AND ((producer.producergroupid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+")) "
                          + "ORDER BY milkCollectionBill.prod_code;";
            }
            
            //System.out.println(qry);
                    
            PreparedStatement getproducer;
            getproducer = conn.prepareStatement(qry);
            
            ResultSet rs_prod;
            ResultSet rs_prod_bill;
            rs_prod = getproducer.executeQuery();
            int rows = 0;
            while (rs_prod.next()) {
                
                // If yes, add headers
                prod_code = rs_prod.getInt("prod_code");
                obj.add(center_string(compname, 33));
                obj.add(center_string(bill_period, 33));
                obj.add(line_break);
                obj.add("CODE : " + prod_code + " - " + rs_prod.getString("prod_name"));
                obj.add(line_break);
                obj.add(rightpad("DATE", 8) + leftpad("WT ", 6) + rightpad("FAT", 4) + rightpad("SNF", 4) + rightpad("RATE", 5) + leftpad("AMT", 6));
                obj.add(line_break);
                rows = rows + 7;
                qry = "SELECT mlkCollection.trn_date, "
                        + "mlkCollection.shift_id, "
                        + "mlkCollection.prod_code, "
                        + "mlkCollection.mlkType_id, "
                        + "mlkCollection.weight, "
                        + "mlkCollection.fat, "
                        + "mlkCollection.degree, "
                        + "mlkCollection.snf, "
                        + "mlkCollection.rate, "
                        + "mlkCollection.prodgrid "
                        + "FROM mlkCollection "
                        + "WHERE (((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"# "
                        + "And (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) "
                        + "AND ((mlkCollection.prod_code)="+prod_code+") "
                        + "AND ((mlkCollection.prodgrid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+")) ORDER BY mlkCollection.trn_date;";
                
                PreparedStatement prod_bill_detail;
                prod_bill_detail = conn.prepareStatement(qry);
                
                rs_prod_bill = prod_bill_detail.executeQuery();
                while (rs_prod_bill.next()) {
                    String shift = rs_prod_bill.getInt("shift_id") == 1 ? "M" : "E";
                    String collectionentry = "";
                    collectionentry = rightpad(dm.format(rs_prod_bill.getDate("trn_date")) + " " + shift, 8);
                    double wt = rs_prod_bill.getDouble("weight");
                    collectionentry = collectionentry + leftpad(wt+" ", 6);
                    collectionentry = collectionentry + rightpad(""+rs_prod_bill.getDouble("fat"), 4);
                    collectionentry = collectionentry + rightpad(""+rs_prod_bill.getDouble("snf"), 4);
                    double rate = rs_prod_bill.getDouble("rate");
                    collectionentry = collectionentry + rightpad(""+rate, 5);
                    double amt = wt * rate;
                    collectionentry = collectionentry + leftpad(""+amt, 6);
                    obj.add(collectionentry);
                    rows = rows + 1;
                }
                obj.add(line_break);
                obj.add(leftpad("Total Milk : ", 25) + leftpad(""+rs_prod.getDouble("tot_milk"), 8));
                obj.add(leftpad("Average FAT : ", 25) + leftpad(""+rs_prod.getDouble("avg_FAT"), 8));
                obj.add(leftpad("Average RATE : ", 25) + leftpad(""+rs_prod.getDouble("avg_rate"), 8));
                obj.add(leftpad("Total Milk AMT : ", 25) + leftpad(""+rs_prod.getDouble("tot_milkamount"), 8));
                obj.add(leftpad("Total Addition : ", 25) + leftpad(""+rs_prod.getDouble("tot_commision"), 8));
                double tot_ded =  rs_prod.getDouble("tot_sh_anamat")
                                  + rs_prod.getDouble("tot_ribet")
                                  + rs_prod.getDouble("chiling_charges")
                        + rs_prod.getDouble("travalingexp")
                        + rs_prod.getDouble("pashukhadya")
                        + rs_prod.getDouble("mlkbilladv")
                        + rs_prod.getDouble("otherded")
                        + rs_prod.getDouble("docfee");
                obj.add(leftpad("Total Deduction : ", 25) + leftpad(""+tot_ded, 8));
                obj.add(leftpad("Total Payable AMT : ", 25) + leftpad(""+rs_prod.getDouble("amt_payable"), 8));
                //obj.add(leftpad("Total Payable AMT : ", 25) + leftpad(""+rs_prod.getDouble("amt_payable"), 8));
                obj.add(line_break);
                rows = rows + 9;
            }
            System.out.println("Total # rows - " + rows);
            rs_prod.close();
            getproducer.close();
            PrinterJob job = PrinterJob.getPrinterJob();
            //--- Create a new book to add pages to
            Book book = new Book();
            PageFormat pf = job.defaultPage();//new PageFormat();
            Paper paper = new Paper(); //pf.getPaper();
            double width = 4d * 72d;
            //double height = 4.4d * 72d;
            //double height = paper.getHeight();
            double height = rows * 15;
            double margin = 0.1d * 72d;
            paper.setSize(width, height);
            paper.setImageableArea(
                        margin,
                        margin,
                        width - (margin * 2),
                        height - (margin * 2));
            pf.setOrientation(PageFormat.PORTRAIT);
            pf.setPaper(paper);
            PrintReceipt_bill pr = new PrintReceipt_bill();
            pr.reciept_data = obj;
            book.append(pr, pf);
            job.setPrintable(pr, pf);
            //job.setPageable(book);
            boolean ok = job.printDialog();
            if (ok) {
                try {
                     job.print();
                } catch (PrinterException ex) {
                 /* The job did not successfully complete */
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(producermilkbill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cmbprodgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbprodgroupActionPerformed
        // TODO add your handling code here:
            if( this.reporttodispalay.equals("Producer Milk Bill") || 
                this.reporttodispalay.equals("Producer Milk Bill - Bank Register") ||
                this.reporttodispalay.equals("Bachat Register") || 
                this.reporttodispalay.equals("Kapat Register")) {
                
            
                    try {
                        cmbsangh.removeAllItems();
                        //jLabel7.setText("उत्पादक :");
                        ResultSet rscombo;
                        //String qry;        
    ////                    qry = "SELECT producer.ID, [producer]![ID] & ' - ' & [producer]![pro_name] AS Expr1 " +
    ////                        "FROM producer " +
    ////                        "ORDER BY producer.ID
        
                        qry = "SELECT producer.prod_id, [producer]![ID] & ' - ' & [producer]![pro_name] AS Expr1," +
                                " producer.producergroupid FROM producer" +
                                " WHERE (((producer.producergroupid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+"))" +
                                " ORDER BY producer.ID;";
                        System.out.println(qry);
                        PreparedStatement ratechartid = conn.prepareStatement(qry);
                        rscombo = ratechartid.executeQuery();
                        sanghid = new int[10000];
                        int i = 0;
                        while(rscombo.next()) {
                           cmbsangh.insertItemAt(rscombo.getString("Expr1"), i);
                           sanghid[i] = rscombo.getInt("prod_id");
                           i++;
                        }
                        if (i!=0) { cmbsangh.setSelectedIndex(0); }
                        rscombo.close();
                        chksummary.setVisible(true);
                    } catch (SQLException ex) {
                        Logger.getLogger(displayreportdatecriteria.class.getName()).log(Level.SEVERE, null, ex);
                    }
            } // If         
    }//GEN-LAST:event_cmbprodgroupActionPerformed

    private void chksummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chksummaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chksummaryActionPerformed

    private void cmbsanghActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbsanghActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbsanghActionPerformed

    private void chksummary1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chksummary1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chksummary1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkall;
    private javax.swing.JCheckBox chksummary;
    private javax.swing.JCheckBox chksummary1;
    private javax.swing.JComboBox cmbprodgroup;
    private javax.swing.JComboBox cmbsangh;
    private com.toedter.calendar.JDateChooser dtpfromdate;
    private com.toedter.calendar.JDateChooser dtptodate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}