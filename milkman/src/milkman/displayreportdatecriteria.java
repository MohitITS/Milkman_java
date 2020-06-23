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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JOptionPane;

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
    int[] rateid;
    
    private Date date = new Date();
    SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dm = new SimpleDateFormat("dd/MM");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat df = new DecimalFormat("#.00");
    DecimalFormat onedf = new DecimalFormat("#.#");
    DecimalFormat twodf = new DecimalFormat("#0.##");
    
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
            
            // Duplicate Bill
            qry = "SELECT rateChart.rtc_no " +
                    "FROM rateChart " +
                    "GROUP BY rateChart.rtc_no;";
            PreparedStatement ratechartid1 = conn.prepareStatement(qry);
            ResultSet rscombo1 = ratechartid1.executeQuery();
            rateid = new int[10];
            int i = 0;
            while(rscombo1.next()) {
               cmbDuplicateRateChartNumber.insertItemAt(rscombo1.getString("rtc_no"), i);
               //rateid[i] = rscombo.getInt("rtc_no");
               i++;
            }
            rscombo1.close();
            
            lblRateChartNumber.setVisible(false);
            cmbDuplicateRateChartNumber.setVisible(false);
            
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
                        i = 0;
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
                        i = 0;
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
        chkDuplicateBill = new javax.swing.JCheckBox();
        lblRateChartNumber = new javax.swing.JLabel();
        cmbDuplicateRateChartNumber = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("तारखे प्रमाणे रिपोर्ट बघणे");

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
                .addContainerGap(138, Short.MAX_VALUE))
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

        chkDuplicateBill.setBackground(new java.awt.Color(255, 255, 255));
        chkDuplicateBill.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        chkDuplicateBill.setText("Duplicate");
        chkDuplicateBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDuplicateBillActionPerformed(evt);
            }
        });

        lblRateChartNumber.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        lblRateChartNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRateChartNumber.setText("Select Rate Chart :");

        cmbDuplicateRateChartNumber.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cmbDuplicateRateChartNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDuplicateRateChartNumberActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(chkall, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(chksummary)
                                        .addGap(60, 60, 60)
                                        .addComponent(chksummary1))
                                    .addComponent(cmbsangh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(chkDuplicateBill)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblRateChartNumber)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbDuplicateRateChartNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkDuplicateBill)
                    .addComponent(lblRateChartNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbDuplicateRateChartNumber))
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
        if(this.reporttodispalay.equals("Dispatch Register") 
                && chkall.isSelected() == true) {
            cond_qry = "milkdispatch.disp_datetime >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkdispatch.disp_datetime2 <= #"+ mdy.format(dtptodate.getDate()) +"#";
            try {
                methods.displayreport("dispatchreport", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(this.reporttodispalay.equals("Dispatch Register") 
                && chkall.isSelected() == false) {
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
        } else if(this.reporttodispalay.equals("Producer Milk Bill") 
                && chkall.isSelected() == true 
                && chksummary1.isSelected() == false 
                && chksummary.isSelected() == false 
                && chkDuplicateBill.isSelected() == false) {            
// Original Bills
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()];
            System.out.println(cond_qry);
            try {
                //milkbill
                methods.displayreport_subreport("milkbill", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill") 
                && chkall.isSelected() == false 
                && chksummary1.isSelected() == false 
                && chksummary.isSelected() == false
                && chkDuplicateBill.isSelected() == false) {
// Original Producer milk bill - selected producer
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and milkCollectionBill.prod_id = " + sanghid[cmbsangh.getSelectedIndex()] + " and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()];
            //System.out.println(cond_qry);
            try {
                // rptmilkbill_recieptsize
                methods.displayreport_subreport("milkbill", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill - Bank Register (Namuna - 1)") 
                && chkall.isSelected() == true) {
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()] + " and producer.bankid IS NOT null AND producer.acno IS NOT null AND milkCollectionBill.amt_payable <> 0";
            //System.out.println(cond_qry);
            try {
                //milkbill
                methods.displayreport_subreport("bankpaymentreg", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill - Bank Register (Namuna - 2)") 
                && chkall.isSelected() == true) {
            cond_qry = "qry_bank_payment_reg_namuna_2.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and qry_bank_payment_reg_namuna_2.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and qry_bank_payment_reg_namuna_2.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()] + " and qry_bank_payment_reg_namuna_2.bankid IS NOT null AND qry_bank_payment_reg_namuna_2.acno IS NOT null AND qry_bank_payment_reg_namuna_2.amt_payable <> 0";
            //System.out.println(cond_qry);
            try {
                //milkbill
                methods.displayreport_subreport("bankpaymentreg_namuna_2", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else if(this.reporttodispalay.equals("Producer Milk Bill") 
                && chksummary1.isSelected() == true
                && chksummary.isSelected() == false
                && chkDuplicateBill.isSelected() == false) {
// Recieprt size original bill
            getRecieptSizeBill();
        } else if (this.reporttodispalay.equals("Producer Milk Bill") 
                && chksummary1.isSelected() == false 
                && chkall.isSelected() == true 
                && chksummary.isSelected() == false 
                && chkDuplicateBill.isSelected() == true) {
// Full page duplicate bill for all producers
            GenerateDuplicateBill();
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()];
            //System.out.println(cond_qry);
            try {
                // rptmilkbill_recieptsize
                methods.displayreport_subreport_duplicate("milkbill", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (this.reporttodispalay.equals("Producer Milk Bill") 
                && chksummary1.isSelected() == false 
                && chkall.isSelected() == false 
                && chksummary.isSelected() == false 
                && chkDuplicateBill.isSelected() == true) {
// Full page duplicate bill for selected producers
            //System.out.println("All deseleted & duplicate selected");
            GenerateDuplicateBill();
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"# and milkCollectionBill.prod_id = " + sanghid[cmbsangh.getSelectedIndex()] + " and producer.producergroupid = " + prodgrid[cmbprodgroup.getSelectedIndex()];
            //System.out.println(cond_qry);
            try {
                // rptmilkbill_recieptsize
                methods.displayreport_subreport_duplicate("milkbill", conn, cond_qry, ""+dmy.format(dtpfromdate.getDate()), ""+dmy.format(dtptodate.getDate()));
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(this.reporttodispalay.equals("Kapat Register") && chkall.isSelected() == true) {
            cond_qry = "milkCollectionBill.from_date >= #"+ mdy.format(dtpfromdate.getDate()) +"# and milkCollectionBill.to_date <= #"+ mdy.format(dtptodate.getDate()) +"#";
            //System.out.println(cond_qry);
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
        
        if(chkDuplicateBill.isSelected() == true) {
            //cmbDuplicateRateChartNumber.setSelectedIndex(0);
            GenerateDuplicateBill();
        }
        
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
        
        //System.out.println(cmbprodgroup.getSelectedIndex());
        
        try {
            
            if (cmbprodgroup.getSelectedIndex() > -1) {
//                if (chkall.isSelected() == true) {
//                    qry = "SELECT milkCollectionBill.prod_code, "
//                          + "milkCollectionBill.prod_name, "
//                          + "milkCollectionBill.tot_milk, "
//                          + "milkCollectionBill.avg_FAT, "
//                          + "milkCollectionBill.avg_rate, "
//                          + "milkCollectionBill.tot_milkamount, "
//                          + "milkCollectionBill.tot_commision, "
//                          + "milkCollectionBill.tot_sh_anamat, "
//                          + "milkCollectionBill.tot_ribet, "
//                          + "milkCollectionBill.chiling_charges, "
//                          + "milkCollectionBill.travalingexp, "
//                          + "milkCollectionBill.pashukhadya, "
//                          + "milkCollectionBill.mlkbilladv, "
//                          + "milkCollectionBill.docfee, "
//                          + "milkCollectionBill.sourmlkamt, "
//                          + "milkCollectionBill.amt_payable, "
//                          + "milkCollectionBill.advancebal, "
//                          + "milkCollectionBill.prod_id, "
//                          + "milkCollectionBill.from_date, "
//                          + "milkCollectionBill.to_date, "
//                          + "milkCollectionBill.otherded, "
//                          + "producer.producergroupid "
//                          + "FROM producer "
//                          + "INNER JOIN milkCollectionBill "
//                          + "ON producer.prod_id = milkCollectionBill.prod_id "
//                          + "WHERE (((milkCollectionBill.from_date)>=#"+mdy.format(dtpfromdate.getDate())+"#) "
//                          + "AND ((milkCollectionBill.to_date)<=#"+mdy.format(dtptodate.getDate())+"#)) "
//                          + "ORDER BY milkCollectionBill.prod_code, producer.producergroupid;";
//                } else {
//                    qry = "SELECT milkCollectionBill.prod_code, "
//                          + "milkCollectionBill.prod_name, "
//                          + "milkCollectionBill.tot_milk, "
//                          + "milkCollectionBill.avg_FAT, "
//                          + "milkCollectionBill.avg_rate, "
//                          + "milkCollectionBill.tot_milkamount, "
//                          + "milkCollectionBill.tot_commision, "
//                          + "milkCollectionBill.tot_sh_anamat, "
//                          + "milkCollectionBill.tot_ribet, "
//                          + "milkCollectionBill.chiling_charges, "
//                          + "milkCollectionBill.travalingexp, "
//                          + "milkCollectionBill.pashukhadya, "
//                          + "milkCollectionBill.mlkbilladv, "
//                          + "milkCollectionBill.docfee, "
//                          + "milkCollectionBill.sourmlkamt, "
//                          + "milkCollectionBill.amt_payable, "
//                          + "milkCollectionBill.advancebal, "
//                          + "milkCollectionBill.prod_id, "
//                          + "milkCollectionBill.from_date, "
//                          + "milkCollectionBill.to_date, "
//                          + "milkCollectionBill.otherded, "
//                          + "producer.producergroupid "
//                          + "FROM producer "
//                          + "INNER JOIN milkCollectionBill "
//                          + "ON producer.prod_id = milkCollectionBill.prod_id "
//                          + "WHERE (((milkCollectionBill.from_date)>=#"+mdy.format(dtpfromdate.getDate())+"#) "
//                          + "AND ((milkCollectionBill.to_date)<=#"+mdy.format(dtptodate.getDate())+"#) "
//                          + "AND (milkCollectionBill.prod_id = "+ sanghid[cmbsangh.getSelectedIndex()] +")) "
//                          + "ORDER BY milkCollectionBill.prod_code, producer.producergroupid;";
//                }
//            } else {
                if (chkall.isSelected() == true) {
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
                          + "AND ((producer.producergroupid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+") "
                          + "AND (milkCollectionBill.prod_id = "+ sanghid[cmbsangh.getSelectedIndex()] +")) "
                          + "ORDER BY milkCollectionBill.prod_code;";
                }
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
                //obj.add(rightpad("DATE", 8) + leftpad("WT ", 6) + rightpad("FAT", 4) + rightpad("SNF", 4) + rightpad("RATE", 5) + leftpad("AMT", 6));
                obj.add(rightpad("DATE", 8) + leftpad("WT ", 6) + rightpad("FAT", 4) + rightpad("RATE", 5) + leftpad("AMT", 8));
                obj.add(line_break);
                rows = rows + 7;
                if (chkDuplicateBill.isSelected()== false) {
                    qry = "SELECT mlkCollection.trn_date, "
                        + "mlkCollection.shift_id, "
                        + "mlkCollection.prod_code, "
                        + "mlkCollection.mlkType_id, "
                        + "mlkCollection.weight, "
                        + "mlkCollection.fat, "
                        + "mlkCollection.degree, "
                        + "mlkCollection.snf, "
                        + "mlkCollection.rate AS rate, "
                        + "mlkCollection.prodgrid "
                        + "FROM mlkCollection "
                        + "WHERE (((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"# "
                        + "And (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) "
                        + "AND ((mlkCollection.prod_code)="+prod_code+") "
                        + "AND ((mlkCollection.prodgrid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+")) ORDER BY mlkCollection.trn_date;";
                } else {
                    qry = "SELECT mlkCollection.trn_date, "
                        + "mlkCollection.shift_id, "
                        + "mlkCollection.prod_code, "
                        + "mlkCollection.mlkType_id, "
                        + "mlkCollection.weight, "
                        + "mlkCollection.fat, "
                        + "mlkCollection.degree, "
                        + "mlkCollection.snf, "
                        + "mlkCollection.dummyrate As rate, "
                        + "mlkCollection.prodgrid "
                        + "FROM mlkCollection "
                        + "WHERE (((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"# "
                        + "And (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) "
                        + "AND ((mlkCollection.prod_code)="+prod_code+") "
                        + "AND ((mlkCollection.prodgrid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+")) ORDER BY mlkCollection.trn_date;";
                }
                PreparedStatement prod_bill_detail;
                prod_bill_detail = conn.prepareStatement(qry);
                
                rs_prod_bill = prod_bill_detail.executeQuery();
                while (rs_prod_bill.next()) {
                    String shift = rs_prod_bill.getInt("shift_id") == 1 ? "M" : "E";
                    String collectionentry = "";
                    collectionentry = rightpad(dm.format(rs_prod_bill.getDate("trn_date")) + " " + shift, 8);
                    double wt = rs_prod_bill.getDouble("weight");
                    collectionentry = collectionentry + leftpad(df.format(wt)+" ", 6);
                    collectionentry = collectionentry + rightpad(""+rs_prod_bill.getDouble("fat"), 4);
                    //collectionentry = collectionentry + rightpad(""+rs_prod_bill.getDouble("snf"), 4);
                    double rate = rs_prod_bill.getDouble("rate");
                    collectionentry = collectionentry + rightpad(""+df.format(rate), 5);
                    double amt = wt * rate;
                    collectionentry = collectionentry + leftpad(""+df.format(amt), 8);
                    obj.add(collectionentry);
                    rows = rows + 1;
                }
                obj.add(line_break);
                obj.add(leftpad("Total Milk : ", 20) + leftpad(""+rs_prod.getDouble("tot_milk"), 8));
                obj.add(leftpad("Average FAT : ", 20) + leftpad(""+rs_prod.getDouble("avg_FAT"), 8));
                obj.add(leftpad("Average RATE : ", 20) + leftpad(""+rs_prod.getDouble("avg_rate"), 8));
                obj.add(leftpad("Total Milk AMT : ", 20) + leftpad(""+rs_prod.getDouble("tot_milkamount"), 8));
                obj.add(leftpad("Total Addition : ", 20) + leftpad(""+rs_prod.getDouble("tot_commision"), 8));
                double tot_ded =  rs_prod.getDouble("tot_sh_anamat")
                                  + rs_prod.getDouble("tot_ribet")
                                  + rs_prod.getDouble("chiling_charges")
                        + rs_prod.getDouble("travalingexp")
                        + rs_prod.getDouble("pashukhadya")
                        + rs_prod.getDouble("mlkbilladv")
                        + rs_prod.getDouble("otherded")
                        + rs_prod.getDouble("docfee");
                obj.add(leftpad("Total Deduction : ", 20) + leftpad(""+tot_ded, 8));
                obj.add(leftpad("Total Payable AMT : ", 20) + leftpad(""+rs_prod.getDouble("amt_payable"), 8));
                //obj.add(leftpad("Total Payable AMT : ", 25) + leftpad(""+rs_prod.getDouble("amt_payable"), 8));
                obj.add(line_break);
                rows = rows + 9;
            }
            // Getting Collection completed
            
            //System.out.println("Total # rows - " + rows);
            rs_prod.close();
            getproducer.close();
            PrinterJob job = PrinterJob.getPrinterJob();
            //--- Create a new book to add pages to
            Book book = new Book();
            PageFormat pf = new PageFormat();
            Paper paper = new Paper(); //pf.getPaper();
            
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(OrientationRequested.PORTRAIT);
            
            double width = 4 * 72;
            //double height = 8 * 72;
            double height = rows * (72 / 4);
            //double margin = 0.25 * 72;
            double margin = 0;
            
//            double width = 4d * 72d;
//            //double height = 4.4d * 72d;
//            //double height = paper.getHeight();
//            double height = rows * 15;
//            double margin = 0.1d * 72d;
            paper.setSize(width, height);
            paper.setImageableArea(
                        margin,
                        margin,
                        width - (margin),
                        height - (margin));
            pf.setOrientation(PageFormat.PORTRAIT);
            pf.setPaper(paper);
            PrintReceipt_bill pr = new PrintReceipt_bill();
            pr.reciept_data = obj;
            pf = job.validatePage(pf);
            book.append(pr, pf);
            job.setPrintable(pr, pf);
            //job.setPageable(book);
            boolean ok = job.printDialog();
            if (ok) {
                try {
                     job.print(aset);
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
                        //System.out.println(qry);
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

    private void chkDuplicateBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDuplicateBillActionPerformed
        if (chkDuplicateBill.isSelected()) {
            lblRateChartNumber.setVisible(true);
            cmbDuplicateRateChartNumber.setVisible(true);
        } else {
            lblRateChartNumber.setVisible(false);
            cmbDuplicateRateChartNumber.setVisible(false);
        }
    }//GEN-LAST:event_chkDuplicateBillActionPerformed

    private void cmbDuplicateRateChartNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDuplicateRateChartNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbDuplicateRateChartNumberActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkDuplicateBill;
    private javax.swing.JCheckBox chkall;
    private javax.swing.JCheckBox chksummary;
    private javax.swing.JCheckBox chksummary1;
    private javax.swing.JComboBox cmbDuplicateRateChartNumber;
    private javax.swing.JComboBox cmbprodgroup;
    private javax.swing.JComboBox cmbsangh;
    private com.toedter.calendar.JDateChooser dtpfromdate;
    private com.toedter.calendar.JDateChooser dtptodate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblRateChartNumber;
    // End of variables declaration//GEN-END:variables

    private void GenerateDuplicateBill() {
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
        double Pashukhadyabal = 0;
        double docfee = 0;
        double otherded = 0;
        double totdeduction = 0;
        double amt_payable = 0;
        double saurmilkamt = 0;
        
        try {
            
            //System.out.println(cmbprodgroup.getSelectedIndex());
            
            // Delete old bills for the selected bill period
            if (cmbprodgroup.getSelectedIndex() > -1) {
//                qry = "DELETE * FROM milkCollectionBill where from_date>=#"+mdy.format(dtpfromdate.getDate())+"# and to_date<=#"+mdy.format(dtptodate.getDate())+"#;";
//            } else {
                if(chkall.isSelected()) {
                    qry = "DELETE milkCollectionBill.*" +
                        " FROM producer INNER JOIN milkCollectionBill ON producer.prod_id = milkCollectionBill.prod_id" +
                        " WHERE (((producer.producergroupid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+") AND "
                        + "((milkCollectionBill.from_date)>=#"+mdy.format(dtpfromdate.getDate())+"#) AND "
                        + "((milkCollectionBill.to_date)<=#"+mdy.format(dtptodate.getDate())+"#));";
                } else {
                    qry = "DELETE milkCollectionBill.*" +
                        " FROM producer INNER JOIN milkCollectionBill ON producer.prod_id = milkCollectionBill.prod_id" +
                        " WHERE (((producer.producergroupid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+") AND "
                        + "((milkCollectionBill.from_date)>=#"+mdy.format(dtpfromdate.getDate())+"#) AND "
                        + "((milkCollectionBill.to_date)<=#"+mdy.format(dtptodate.getDate())+"#) AND "
                        + "((milkCollectionBill.prod_id) = "+ sanghid[cmbsangh.getSelectedIndex()] +"));";
                }
            }
            PreparedStatement deletebill;
            deletebill = conn.prepareStatement(qry);
            deletebill.execute();
            deletebill.close();
            
            // Delete producer deduction detail for the selected bill period
            qry = "DELETE * FROM prodbilldeduction WHERE dedfromdate >= #"+ mdy.format(dtpfromdate.getDate()) +"# and dedtodate <= #"+ mdy.format(dtptodate.getDate()) +"#;";
            PreparedStatement deletedeductbill;
            deletedeductbill = conn.prepareStatement(qry);
            deletedeductbill.execute();
            deletedeductbill.close();
            
            // Insert Query for bill 
            qry = "INSERT INTO milkCollectionBill (from_date,to_date,prod_code,prod_name,"+
                    "tot_milk,avg_FAT,avg_rate,tot_milkamount,tot_commision,tot_sh_anamat,tot_ribet,"+
                    "chiling_charges,travalingexp,pashukhadya,mlkbilladv,otherded,docfee,"+
                    "sourmlkamt,amt_payable,AddedByFK, prod_id, advancebal) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement INSERT_QRY;            
            INSERT_QRY = conn.prepareStatement(qry);

            // Insert query for deduction
            String qry1 = "INSERT INTO prodbilldeduction (dedfromdate,dedtodate,pro_code,rebate,"+
                    "shareanamat,chilingcharges,AddedByFK,totamilk,amount, advance_amount) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement INSERT_DEDQRY;            
            INSERT_DEDQRY = conn.prepareStatement(qry1);            
            
            // Get Producer list for the period
            if (cmbprodgroup.getSelectedIndex() > -1) {
                
//                qry = "SELECT producer.ID, producer.pro_name, producer.comission, producer.ribet,"+
//                        " producer.share_amt, producer.chiling_amt, producer.veh_exp, producer.prod_id" +
//                        " FROM producer INNER JOIN mlkCollection ON producer.prod_id = mlkCollection.prod_id" +
//                        " WHERE (((producer.member)=True) AND ((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"#"+
//                        " And (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#))" +
//                        " GROUP BY producer.ID, producer.pro_name, producer.comission, producer.ribet,"+
//                        " producer.share_amt, producer.chiling_amt, producer.veh_exp, producer.prod_id" +
//                        " ORDER BY producer.ID;";
//
//            } else {
                if (chkall.isSelected()) {
                    qry = "SELECT producer.ID, producer.pro_name, producer.comission, producer.ribet,"+
                        " producer.share_amt, producer.chiling_amt, producer.veh_exp, producer.prod_id" +
                        " FROM producer INNER JOIN mlkCollection ON producer.prod_id = mlkCollection.prod_id" +
                        " WHERE (((producer.member)=True) " + 
                        " AND ((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"#"+
                        " And (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) " + 
                        " AND ((producer.producergroupid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+"))" +
                        " GROUP BY producer.ID, producer.pro_name, producer.comission, producer.ribet,"+
                        " producer.share_amt, producer.chiling_amt, producer.veh_exp, producer.prod_id" +
                        " ORDER BY producer.ID;";
                } else {
                    qry = "SELECT producer.ID, producer.pro_name, producer.comission, producer.ribet,"+
                        " producer.share_amt, producer.chiling_amt, producer.veh_exp, producer.prod_id" +
                        " FROM producer INNER JOIN mlkCollection ON producer.prod_id = mlkCollection.prod_id" +
                        " WHERE (((producer.member)=True) " + 
                        " AND ((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"#"+
                        " And (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) " + 
                        " AND ((producer.producergroupid)="+prodgrid[cmbprodgroup.getSelectedIndex()]+")" +
                        " AND ((producer.prod_id)="+sanghid[cmbsangh.getSelectedIndex()]+"))" +
                        " GROUP BY producer.ID, producer.pro_name, producer.comission, producer.ribet,"+
                        " producer.share_amt, producer.chiling_amt, producer.veh_exp, producer.prod_id" +
                        " ORDER BY producer.ID;";
                }
            }
            
            System.out.println(qry);
                    
            PreparedStatement getproducer;
            getproducer = conn.prepareStatement(qry);
            
            ResultSet rs_prod;
            ResultSet rs_prod_bill;
            rs_prod = getproducer.executeQuery();
            while (rs_prod.next()) {
                
                // Set default varuable
                comm = 0;
                commission = 0;
                totmilk = 0;
                avg_fat = 0;
                avg_snf = 0;
                avg_rate = 0;
                tot_amt = 0;
                anamat_per = 0;      // per liter
                rebate_per = 0;      // per liter
                vehcharg_per = 0;    // per liter
                chilcharg_per = 0;   // per liter
                anamat = 0;      // per liter
                rebate = 0;      // per liter
                vehcharg = 0;    // per liter
                chilcharg = 0;   // per liter
                pashukhadya = 0;
                milkbilladv = 0;
                milkbilladvbal = 0;
                Pashukhadyabal = 0;
                docfee = 0;
                otherded = 0;
                totdeduction = 0;
                amt_payable = 0;
                saurmilkamt = 0;                
                
                prod_id = rs_prod.getInt("prod_id");
                prod_code = rs_prod.getInt("ID");
                prod_name = rs_prod.getString("pro_name");
                comm = rs_prod.getDouble("comission");
                rebate_per = rs_prod.getDouble("ribet");
                anamat_per = rs_prod.getDouble("share_amt");
                chilcharg_per = rs_prod.getDouble("chiling_amt");
                vehcharg_per = rs_prod.getDouble("veh_exp");
                
                // -------------------------------------------------------------
                // Update Dummy rate
                // -------------------------------------------------------------
                
                System.out.println("Processiong producer - " + prod_code);
                
                // STEP - 1 GET THE COLLECTION DETAIL
                qry = "SELECT mlkCollection.ID, "
                        + "mlkCollection.fat, "
                        + "mlkCollection.degree, "
                        + "mlkCollection.snf FROM mlkCollection "
                        + "WHERE (((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"# And "
                        + "(mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) AND "
                        + "((mlkCollection.prod_id)="+prod_id+")) ORDER BY mlkCollection.ID;";
                PreparedStatement getCollectionRecord;
                getCollectionRecord = conn.prepareStatement(qry);                
                try (ResultSet rsCollectionRecord = getCollectionRecord.executeQuery()) {
                    while(rsCollectionRecord.next()) {
                        double dummyrate = 0;
                        qry = "SELECT rateChart.rate FROM rateChart "
                                + "WHERE (((rateChart.rtc_no)="+cmbDuplicateRateChartNumber.getSelectedItem()+") AND "
                                + "((rateChart.fat)="+rsCollectionRecord.getString("fat")+") AND "
                                + "((rateChart.snf)="+rsCollectionRecord.getString("snf")+"));";
                        PreparedStatement getDummyRate = conn.prepareStatement(qry);
                        try(ResultSet rsDummyRate = getDummyRate.executeQuery()) {
                            while (rsDummyRate.next()) {
                                dummyrate = rsDummyRate.getDouble("rate");
                            }
                        }
                        
                        qry = "UPDATE mlkCollection SET mlkCollection.dummyrate = "+ twodf.format(dummyrate) +" WHERE (((mlkCollection.ID)="+rsCollectionRecord.getInt("ID")+"));";
                        conn.prepareStatement(qry).execute();
                    }
                }
                
//                qry = "UPDATE rateChart INNER JOIN (producer INNER JOIN mlkCollection "
//                        + "ON producer.prod_id = mlkCollection.prod_id) "
//                        + "ON rateChart.rtc_no = producer.rtc_no "
//                        + "SET mlkCollection.dummyrate = [rateChart]![rate] " 
//                        + "WHERE (((mlkCollection.fat)=[rateChart]![fat]) AND "
//                        + "((mlkCollection.snf)=[rateChart]![snf]) AND "
//                        + "((rateChart.rtc_no)="+cmbDuplicateRateChartNumber.getSelectedItem()+") AND "
//                        + "((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"# And "
//                        + "(mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) AND "
//                        + "((mlkCollection.prod_id)="+prod_id+"));";
//                getproducer = conn.prepareStatement(qry);
//                getproducer.execute();
                
                // -------------------------------------------------------------
                // Update Dummy rate complete
                // -------------------------------------------------------------
                
                // Get collection detail for the period
                qry = "SELECT Sum(mlkCollection.weight) AS SumOfweight,"+
                      " Sum([mlkCollection]![weight]*[mlkCollection]![fat]) AS weightfat,"+
                      " Sum([mlkCollection]![weight]*[mlkCollection]![snf]) AS weightsnf,"+
                      " Sum([mlkCollection]![weight]*[mlkCollection]![dummyrate]) AS weightrate,"+
                      " Sum([mlkCollection]![smw]*[mlkCollection]![smr]) AS Expr1 FROM mlkCollection" +
                      " WHERE (((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"# And"+
                      " (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#) AND"+
                      " ((mlkCollection.prod_id)="+prod_id+"));";
                PreparedStatement getbill;
                getbill = conn.prepareStatement(qry);                
                rs_prod_bill = getbill.executeQuery();
                if(rs_prod_bill.next()) {
                    
//                    System.out.println("Am Here..!! In If " + prod_id);
//                    System.out.println("Am Here..!! In If " + rs_prod_bill.getDouble("SumOfweight"));
                    totmilk = rs_prod_bill.getDouble("SumOfweight");
                    tot_amt = rs_prod_bill.getDouble("weightrate");
                    if (totmilk != 0) {
                        avg_fat = rs_prod_bill.getDouble("weightfat") / totmilk;
                        avg_rate = tot_amt / totmilk;
                        avg_snf = rs_prod_bill.getDouble("weightsnf") / totmilk;
                    } else {
                        avg_fat = 0;
                        avg_rate = 0;
                        avg_snf = 0;
                    }
                    
                    saurmilkamt = rs_prod_bill.getDouble("Expr1");
                    
                    anamat = totmilk * anamat_per;
                    rebate = totmilk * rebate_per;
                    //System.out.println("Rebate is : " +rebate);
                    chilcharg = totmilk * chilcharg_per;
                    vehcharg = totmilk * vehcharg_per;
                    
                    commission = totmilk * comm;
                    
                } else {
                    totmilk = 0;
                    tot_amt = 0;
                    avg_fat = 0;
                    avg_rate = 0;
                    avg_snf = 0;
                    saurmilkamt = 0;
                    anamat = 0;
                    rebate = 0;
                    chilcharg = 0;
                    vehcharg = 0;
                    commission = 0;
                } 
                rs_prod_bill.close();
                getbill.close();
                
//                qry = "SELECT deduction.ID, deduction.pro_code, producer.pro_name,"+
//                      " [deduction]![pashukhadya]+[deduction]![advance]+[deduction]![otherded]+[deduction]![doctorfee] AS Expr1,"+
//                      " deduction.dedfromdate, deduction.dedtodate, deduction.pashukhadya, deduction.advance, deduction.otherded,"+
//                      " deduction.doctorfee FROM producer INNER JOIN deduction ON producer.ID=deduction.pro_code" +
//                      " WHERE (((deduction.pro_code)="+prod_id+") AND ((deduction.dedfromdate)>=#"+mdy.format(dtpfromdate.getDate())+"#) AND"+
//                      " ((deduction.dedtodate)<=#"+mdy.format(dtptodate.getDate())+"#));";

//                qry = "SELECT deduction.ID, deduction.pro_code, producer.pro_name,"+
//                      " [deduction]![pashukhadya]+[deduction]![advance]+[deduction]![otherded]+[deduction]"+
//                      "![doctorfee] AS Expr1, deduction.dedfromdate, deduction.dedtodate, deduction.pashukhadya,"+
//                      " deduction.advance, deduction.otherded, deduction.doctorfee" +
//                      " FROM producer INNER JOIN deduction ON producer.prod_id = deduction.pro_code" +
//                      " WHERE (((deduction.pro_code)="+prod_id+") AND ((deduction.dedfromdate)>=#"+mdy.format(dtpfromdate.getDate())+"#) AND"+
//                      " ((deduction.dedtodate)<=#"+mdy.format(dtptodate.getDate())+"#));";
                
                // Check advance detail
                // calculate advance
                String qry2 = "SELECT Sum(addition.amount) AS SumOfamount, Sum(addition.ded_amt) AS SumOfded_amt" +
                        " FROM addition INNER JOIN producer ON addition.pro_code = producer.prod_id" +     
                        " WHERE (((addition.pro_code)="+prod_id+") AND deduction_type like 'Auto' AND add_type like 'Uchal');";
                //System.out.println(qry2);
                PreparedStatement getmilkdetail;
                getmilkdetail = conn.prepareStatement(qry2);
                ResultSet rspname;
                rspname = getmilkdetail.executeQuery();
                double totalAdvance = 0;
                while(rspname.next()) {
                    totalAdvance = rspname.getDouble("SumOfamount");
                    milkbilladv = milkbilladv + rspname.getDouble("SumOfded_amt");
                    break;
                }
                rspname.close();
                getmilkdetail.close();
                
                // Toatal Pashukhadya 
                qry2 = "SELECT Sum(addition.amount) AS SumOfamount, Sum(addition.ded_amt) AS SumOfded_amt" +
                        " FROM addition INNER JOIN producer ON addition.pro_code = producer.prod_id" +     
                        " WHERE (((addition.pro_code)="+prod_id+") AND deduction_type like 'Auto' AND add_type like 'Pashukhadya Vikri');";
                //System.out.println(qry2);
                getmilkdetail = conn.prepareStatement(qry2);
                rspname = getmilkdetail.executeQuery();
                double totalPashukhadyaAdvance = 0;
                while(rspname.next()) {
                    totalPashukhadyaAdvance = rspname.getDouble("SumOfamount");
                    pashukhadya = pashukhadya + rspname.getDouble("SumOfded_amt");
                    break;
                }
                rspname.close();
                getmilkdetail.close();
                
                //System.out.println("Total Advance : " + totalAdvance);
                
                // Get the deduction detail for the selected period for the selected produder
                qry = "SELECT deduction.pro_code, producer.pro_name, "
                        + "Sum([deduction]![pashukhadya]+[deduction]![advance]+[deduction]![otherded]+[deduction]![doctorfee]) AS Expr1, "
                        + "Sum(deduction.pashukhadya) AS SumOfpashukhadya, "
                        + "Sum(deduction.advance) AS SumOfadvance, "
                        + "Sum(deduction.otherded) AS SumOfotherded, "
                        + "Sum(deduction.doctorfee) AS SumOfdoctorfee\n" +
                        "FROM producer INNER JOIN deduction ON producer.prod_id = deduction.pro_code\n" +
                        "WHERE (((deduction.dedfromdate)>=#"+mdy.format(dtpfromdate.getDate())+"#) AND ((deduction.dedtodate)<=#"+mdy.format(dtptodate.getDate())+"#))\n" +
                        "GROUP BY deduction.pro_code, producer.pro_name\n" +
                        "HAVING (((deduction.pro_code)="+prod_id+"));";
                //System.out.println("Deduction Query : " + qry);
                PreparedStatement getdeduction;
                getdeduction = conn.prepareStatement(qry);                
                rs_prod_bill = getdeduction.executeQuery();
                if(rs_prod_bill.next()) {
                    
                    pashukhadya = pashukhadya + rs_prod_bill.getDouble("SumOfpashukhadya");
                    otherded = rs_prod_bill.getDouble("SumOfotherded");
                    docfee = rs_prod_bill.getDouble("SumOfdoctorfee");
                    milkbilladv = milkbilladv + rs_prod_bill.getDouble("SumOfadvance");
                    
                    //totdeduction = rs_prod_bill.getDouble("Expr1");
                    totdeduction = pashukhadya + otherded + docfee + milkbilladv;
                } else {
                    //pashukhadya = 0;
                    otherded = 0;
                    docfee = 0;
                    //milkbilladv = 0;
                    totdeduction = pashukhadya + milkbilladv;
                }
                rs_prod_bill.close();
                getdeduction.close();
                
                if (methods.deduct_rebate_from_bill) {
                    amt_payable = (tot_amt + commission) - (anamat + rebate + chilcharg + vehcharg + pashukhadya + otherded + docfee);
                } else {
                    amt_payable = (tot_amt + commission) - (anamat + chilcharg + vehcharg + pashukhadya + otherded + docfee);
                }
                
//                System.out.println(twodf.format(totmilk));
//                System.out.println(twodf.format(avg_fat));
//                System.out.println(twodf.format(avg_rate));
//                System.out.println(twodf.format(tot_amt));
//                System.out.println(twodf.format(commission));
//                System.out.println(twodf.format(anamat));
//                System.out.println(twodf.format(rebate));
//                System.out.println(twodf.format(chilcharg));
//                System.out.println(twodf.format(vehcharg));
//                System.out.println(twodf.format(pashukhadya));
//                System.out.println(twodf.format(milkbilladv));
//                System.out.println(twodf.format(otherded));
//                System.out.println(twodf.format(docfee));
//                System.out.println(twodf.format(saurmilkamt));
//                System.out.println(twodf.format(amt_payable));
                
                // calculate deduction for advance and pashukhadya befor the selected period for the calculation of balance
                
                //qry2 = "SELECT]] Sum(prodbilldeduction.advance_amount) AS SumOfadvance FROM prodbilldeduction" +
                //     " WHERE (((prodbilldeduction.pro_code)="+prod_id+"));";
                qry2 = "SELECT Sum(deduction.pashukhadya) AS SumOfpashukhadya, "
                        + "Sum(deduction.advance) AS SumOfadvance"
                        + " FROM deduction "
                        + "WHERE (((deduction.dedfromdate)<#"+mdy.format(dtpfromdate.getDate())+"#) "
                        + "AND ((deduction.pro_code)="+prod_id+"));";
                //PreparedStatement getmilkdetail;
                getmilkdetail = conn.prepareStatement(qry2);
                
                
                rspname = getmilkdetail.executeQuery();
                double totalAdvDeduction = 0;
                double totalPashuDeduction = 0;
                while(rspname.next()) {
                    totalAdvDeduction = rspname.getDouble("SumOfadvance");
                    totalPashuDeduction = rspname.getDouble("SumOfpashukhadya");
                    break;
                }
                rspname.close();
                getmilkdetail.close();
                
                //System.out.println(totalAdvance);
                //System.out.println(totalDeduction);
                
                // *================================================
                // Commented for Nilesh Thorat
                // *================================================
//                if (amt_payable <= milkbilladv) {
//                    milkbilladv = amt_payable;
//                }
                milkbilladvbal = totalAdvance - (milkbilladv + totalAdvDeduction); //totalDeduction;
                Pashukhadyabal = totalPashuDeduction - (pashukhadya + totalPashukhadyaAdvance);
                
                //System.out.println(milkbilladvbal);
                amt_payable = amt_payable - milkbilladv;
                
                INSERT_QRY.setString(1, ""+dmy.format(dtpfromdate.getDate())); //from_date
                INSERT_QRY.setString(2, ""+dmy.format(dtptodate.getDate())); // to_date
                INSERT_QRY.setString(3, ""+prod_code); // prod_code
                INSERT_QRY.setString(4, prod_name); // prod_name 
                INSERT_QRY.setString(5, ""+twodf.format(totmilk)); // tot_milk
                INSERT_QRY.setString(6, ""+onedf.format(avg_fat)); // avg_FAT
                INSERT_QRY.setString(7, ""+twodf.format(avg_rate)); // avg_rate
                INSERT_QRY.setString(8, ""+twodf.format(tot_amt)); // tot_milkamount
                INSERT_QRY.setString(9, ""+twodf.format(commission)); // tot_commision
                INSERT_QRY.setString(10, ""+twodf.format(anamat)); // tot_sh_anamat
                INSERT_QRY.setString(11, ""+twodf.format(rebate)); // tot_ribet
                INSERT_QRY.setString(12, ""+twodf.format(chilcharg)); // chiling_charges
                INSERT_QRY.setString(13, ""+twodf.format(vehcharg)); // travalingexp
                INSERT_QRY.setString(14, ""+twodf.format(pashukhadya)); // pashukhadya
                INSERT_QRY.setString(15, ""+twodf.format(milkbilladv)); //  mlkbilladv
                INSERT_QRY.setString(16, ""+twodf.format(otherded)); // otherded
                INSERT_QRY.setString(17, ""+twodf.format(docfee)); // docfee
                INSERT_QRY.setString(18, ""+twodf.format(saurmilkamt)); // sourmlkamt
                INSERT_QRY.setString(19, ""+twodf.format(amt_payable)); // amt_payable
                INSERT_QRY.setString(20, ""+methods.userid); // AddedByFK
                INSERT_QRY.setString(21, ""+prod_id); // AddedByFK
                INSERT_QRY.setString(22, ""+twodf.format(milkbilladvbal)); // AddedByFK
                
                INSERT_QRY.execute();
                
                INSERT_DEDQRY.setString(1, ""+dmy.format(dtpfromdate.getDate())); //from_date
                INSERT_DEDQRY.setString(2, ""+dmy.format(dtptodate.getDate())); // to_date
                INSERT_DEDQRY.setString(3, ""+prod_id); // prod_code
                INSERT_DEDQRY.setString(4, ""+twodf.format(rebate)); // tot_sh_anamatanamat
                INSERT_DEDQRY.setString(5, ""+twodf.format(anamat)); // tot_ribet
                INSERT_DEDQRY.setString(6, ""+twodf.format(chilcharg)); // chiling_charges
                INSERT_DEDQRY.setString(7, ""+methods.userid); // AddedByFK
                INSERT_DEDQRY.setString(8, ""+twodf.format(totmilk)); // tot_milk
                INSERT_DEDQRY.setString(9, ""+twodf.format(tot_amt)); // tot_milkamount
                INSERT_DEDQRY.setString(10, ""+twodf.format(milkbilladv));
                
                INSERT_DEDQRY.execute();
                
//                System.out.println("Prod Code : "+ prod_id);
//                System.out.println("Prod Name : "+ prod_name);
//                System.out.println("Total Milk : "+ twodf.format(totmilk));
//                System.out.println("avg fat : "+ onedf.format(avg_fat));
//                System.out.println("avg rate : "+ twodf.format(avg_rate));
//                System.out.println("avg snf : "+ onedf.format(avg_snf));
//                System.out.println("Commission : "+ twodf.format(commission));
//                System.out.println("Rebate : "+ twodf.format(rebate));
//                System.out.println("Anamat : "+ twodf.format(anamat));
//                System.out.println("Vehical Charges : "+ twodf.format(vehcharg));
//                System.out.println("Chiling Charges : "+ twodf.format(chilcharg));
//                System.out.println("Pashukhadya : "+ twodf.format(pashukhadya));
//                System.out.println("Other Ded : "+ twodf.format(otherded));
//                System.out.println("Doc Fee : "+ twodf.format(docfee));
//                System.out.println("Advance : "+ twodf.format(milkbilladv));

            }
            rs_prod.close();
            getproducer.close();
            
            JOptionPane.showMessageDialog(null, "Bill generated Successfully", title, JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException ex) {
            Logger.getLogger(producermilkbill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}