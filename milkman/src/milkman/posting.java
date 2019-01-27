/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Rakesh
 */
public class posting extends javax.swing.JInternalFrame {

    private DefaultTableModel dtm;
    Connection conn;
    SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat onedf = new DecimalFormat("#.0#");
    DecimalFormat twodf = new DecimalFormat("#0.00");
    private Date date = new Date();
    String qry;
    
    /**
     * Creates new form posting
     */
    public posting() throws SQLException {
        initComponents();
        if(conn!=null){
            conn.close();
        }
        conn = methods.getConnection();
        dtpdate.setDate(date);
        
        int maxrows;
        //"दुध प्रकार", 
        String colheader[] = {"चलन नं", "व्हाउचर प्रकार", "खाते", "जमा", "नावे", "शेरा", "पास / अनापास"};

        maxrows = 500;  //rsshiftreport.
        dtm = new DefaultTableModel(colheader, maxrows);                    
        table_transaction.setModel(dtm);

        table_transaction.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                return c;
            }
        });       
        //table_transaction.getColumnModel().getColumn(0).setWidth(10);
        //table_transaction.getColumnModel().getColumn(1).setWidth(10);
        //table_transaction.getColumnModel().getColumn(2).setWidth(200);
        //table_transaction.getColumnModel().getColumn(3).setWidth(10);
        //table_transaction.getColumnModel().getColumn(4).setWidth(10);
        //table_transaction.getColumnModel().getColumn(5).setWidth(100);
        
        
        gettodaystrn(2);
    }

     private void gettodaystrn(int flag) {    // 1 = posted 2 = unposted 3 = all
        try {
            int r;
            int c;
            for(r=0; r<table_transaction.getRowCount(); r++) {
                for (c=0; c<table_transaction.getColumnCount(); c++) {
                    table_transaction.setValueAt("", r, c);
                }
            }            
            
            if (flag == 1) { // pass voucher
                qry = "SELECT dailytrn.chalanno, vouchertype.vtype, dailytrn.accid,"+
                        " producer.pro_name, account.accname, dailytrn.cramt, dailytrn.dramt,"+
                        " dailytrn.naration, dailytrn.posting, dailytrn.trndate, dailytrn.shiftid" +
                        " FROM (vouchertype INNER JOIN (account INNER JOIN dailytrn ON account.ID"+
                        " = dailytrn.accid) ON vouchertype.ID = dailytrn.voucherid) LEFT JOIN producer"+
                        " ON dailytrn.prodid = producer.ID" +
                        " WHERE (((dailytrn.posting)=True) AND"+
                        " ((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#))" +
                        " ORDER BY dailytrn.chalanno;";
            } else if (flag == 2) {  // unpass vouchar
                qry = "SELECT dailytrn.chalanno, vouchertype.vtype, dailytrn.accid,"+
                        " producer.pro_name, account.accname, dailytrn.cramt, dailytrn.dramt,"+
                        " dailytrn.naration, dailytrn.posting, dailytrn.trndate, dailytrn.shiftid" +
                        " FROM (vouchertype INNER JOIN (account INNER JOIN dailytrn ON account.ID"+
                        " = dailytrn.accid) ON vouchertype.ID = dailytrn.voucherid) LEFT JOIN producer"+
                        " ON dailytrn.prodid = producer.ID" +
                        " WHERE (((dailytrn.posting)=False) AND"+
                        " ((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#))" +
                        " ORDER BY dailytrn.chalanno;";
            } else { // all vouchar
                qry = "SELECT dailytrn.chalanno, vouchertype.vtype, dailytrn.accid," +
                        " producer.pro_name, account.accname, dailytrn.cramt, dailytrn.dramt,"+
                        " dailytrn.naration, dailytrn.posting, dailytrn.trndate, dailytrn.shiftid" +
                        " FROM (vouchertype INNER JOIN (account INNER JOIN dailytrn ON account.ID"+
                        " = dailytrn.accid) ON vouchertype.ID = dailytrn.voucherid) LEFT JOIN producer"+
                        " ON dailytrn.prodid = producer.ID" +
                        " WHERE (((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#))"+
                        " ORDER BY dailytrn.chalanno;";
            }
             
            System.out.println(qry);
            try (PreparedStatement todays_trn = conn.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);ResultSet rstodaytrn = todays_trn.executeQuery()) {
                if(rstodaytrn.next()) {
                    rstodaytrn.beforeFirst();
                    int i = 0;
                    int j;
                    while(rstodaytrn.next()) { // i loop
                        for (j=1; j<=table_transaction.getColumnCount(); j++) {
                            // if account Id is 18 then Display the producer name otherwise display the account name
                            if (j == 3 && rstodaytrn.getInt(j) == 18) {
                                //j = 4;
                                table_transaction.setValueAt(rstodaytrn.getString(4), i, j-1);
                            } else if (j == 3 && rstodaytrn.getInt(j) != 18) {
                                //j = 5;
                                table_transaction.setValueAt(rstodaytrn.getString(5), i, j-1);
                            } else if (j > 3 & j < 7){
                                table_transaction.setValueAt(rstodaytrn.getString(j+2), i, j-1);
                            } else if (j == 7) {
                                if (rstodaytrn.getBoolean(9) == true) {
                                    System.out.println("in if loop");
                                    table_transaction.setValueAt("पास", i, j-1);
                                } else {
                                    table_transaction.setValueAt("अनपास", i, j-1);
                                }
                            } else {
                                table_transaction.setValueAt(rstodaytrn.getString(j), i, j-1);
                            } 
                            //System.out.println("i = "+i+ " j = "+j);
                        }
                        i++;
                    }
                } 
            }
            gettotalamount(flag);
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_transaction = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        dtpdate = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("पोस्टिंग");

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 24)); // NOI18N
        jLabel5.setText("व्यवहार पोस्टिंग");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(113, 168, 168));

        jButton1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton1.setText("पास");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton2.setText("बाहेर");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton6.setText("खोडणे");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton7.setText("शोधणे");

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 204));
        jLabel7.setText("एकूण जमा रक्कम :");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 204));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("०.००");

        jLabel8.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setText("एकूण नावे रक्कम :");

        jTextField2.setEditable(false);
        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 0, 0));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setText("०.००");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addComponent(jTextField1)
                    .addComponent(jTextField2))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        table_transaction.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        table_transaction.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_transaction.setRowHeight(24);
        table_transaction.setSelectionForeground(new java.awt.Color(153, 0, 0));
        table_transaction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_transactionMouseClicked(evt);
            }
        });
        table_transaction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_transactionKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table_transaction);

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 204));
        jLabel6.setText("दिनांक :");

        dtpdate.setFocusable(false);
        dtpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dtpdate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpdatePropertyChange(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Mangal", 1, 12)); // NOI18N
        jButton3.setText("सर्व");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Mangal", 1, 12)); // NOI18N
        jButton4.setText("अनपास");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Mangal", 1, 12)); // NOI18N
        jButton5.setText("पास");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Mangal", 1, 12)); // NOI18N
        jButton8.setText("सकाळ संकलन पोस्टिंग");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Mangal", 1, 12)); // NOI18N
        jButton9.setText("संध्याकाळ संकलन पोस्टिंग");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(dtpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton4)
                        .addComponent(jButton5)
                        .addComponent(jButton8)
                        .addComponent(jButton9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void table_transactionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_transactionMouseClicked
        //System.out.println(table_transaction.getSelectedRow());
        if (table_transaction.getSelectedRow()!=-1) {
        }
    }//GEN-LAST:event_table_transactionMouseClicked

    private void table_transactionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_transactionKeyReleased
        //System.out.println(evt.getKeyCode());

        if (table_transaction.getSelectedRow()!=-1) {
        } else {
        }
    }//GEN-LAST:event_table_transactionKeyReleased

    private void dtpdatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpdatePropertyChange
        gettodaystrn(2);
    }//GEN-LAST:event_dtpdatePropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        gettodaystrn(1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        gettodaystrn(3);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            //first check todays collection shiftwise is pass or not 
            //if pass then display the paased transaction and if not then show unpassed trnsaction
            boolean collectionpass = false;
            boolean flag = false;
            boolean collectionentry = false;
            qry = "SELECT dailytrn.trndate, dailytrn.accid, dailytrn.shiftid, dailytrn.posting FROM dailytrn " +
                "WHERE (((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#)"+
                " AND ((dailytrn.accid)="+methods.milkpurchasedefaultlgrid+") AND (dailytrn.shiftid)=1);";
            PreparedStatement STMT = conn.prepareStatement(qry);
            ResultSet rs = STMT.executeQuery();
            if (rs.next()){
                 //&& !rs.getString(1) == null) 
                //System.out.println(rs.getString(1));
                collectionentry = true;
                if (rs.getBoolean("posting") == true) {
                    collectionpass = true;
                } else {
                    collectionpass = false;
                }
                
            } else {
                collectionentry = false;
                collectionpass = false;
            }
            rs.close();
            STMT.close();
            
            // insert milk collection data when collectionentry is false other wise display the entry with pass unpass status
            if (collectionentry == false) {
                double totalamt = 0.0;
                int chno = getchalanno();
                
                qry = "SELECT mlkCollection.trn_date, mlkCollection.prod_code,"+
                        "[mlkCollection]![weight]*[mlkCollection]![rate] AS amt " +
                        "FROM mlkCollection " +
                        "WHERE (((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#)  AND ((mlkCollection.shift_id)=1)) " +
                        "ORDER BY mlkCollection.prod_code;";
                STMT = conn.prepareStatement(qry);
                rs = STMT.executeQuery();
                String insert_qry = "INSERT INTO dailytrn (trndate,voucherid,chalanno,accid,prodid,dramt,cramt,naration,shiftid)"+
                       " VALUES (?,?,?,?,?,?,?,?,?)";
                try (PreparedStatement insert_stmt = conn.prepareStatement(insert_qry)) {
                    while (rs.next()){
                        
                        //System.out.println("in while loop : "+rs.getString(1));
                        flag = true;
                        // producr cr entry
                        
                        insert_stmt.setString(1, ""+dmy.format(dtpdate.getDate()));
                        insert_stmt.setInt(2, 1);
                        insert_stmt.setInt(3, chno);
                        insert_stmt.setInt(4, methods.producerdefaultlgrid);
                        int prodid = rs.getInt("prod_code");
                        insert_stmt.setInt(5, prodid);
                        insert_stmt.setDouble(6, 0.0);
                        double amt = rs.getDouble("amt");
                        totalamt = totalamt + amt;
                        insert_stmt.setDouble(7, amt);
                        insert_stmt.setString(8, "दुध संकलन");
                        insert_stmt.setInt(9, 1);
                        
                        insert_stmt.execute();
                    }
                    
                    if (flag == true) {
                        // milk purchase acc dr entry
                        insert_stmt.setString(1, ""+dmy.format(dtpdate.getDate()));
                        insert_stmt.setInt(2, 1);
                        insert_stmt.setInt(3, chno);
                        insert_stmt.setInt(4, methods.milkpurchasedefaultlgrid);
                        insert_stmt.setString(5, null);
                        insert_stmt.setDouble(6, totalamt);
                        insert_stmt.setDouble(7, 0.0);
                        insert_stmt.setString(8, "दुध संकलन");
                        insert_stmt.setInt(9, 1);

                        insert_stmt.execute();       
                    } else {
                        JOptionPane.showMessageDialog(null,"सकाळचे दूध संकलन नाही.","Milkman", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                rs.close();
                STMT.close();                                
                gettodaystrn(2);
            } else {
                if (collectionpass == true) {
                    JOptionPane.showMessageDialog(null,"सकाळचे दूध संकलन पास झाले आहे.","Milkman", JOptionPane.INFORMATION_MESSAGE);
                    gettodaystrn(1);
                } else {
                    JOptionPane.showMessageDialog(null,"सकाळचे दूध संकलन अनपास झाले आहे.","Milkman", JOptionPane.INFORMATION_MESSAGE);
                    gettodaystrn(2);                    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(posting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    // method for geting
    public int getchalanno() {
        int cno = 0;
        try {
            String chalanqry = "SELECT Max(dailytrn.chalanno) AS MaxOfchalanno FROM dailytrn;";
            try (PreparedStatement stm = conn.prepareStatement(chalanqry)) {
                ResultSet rschno = stm.executeQuery();
                while (rschno.next()) {
                    cno = rschno.getInt("MaxOfchalanno");
                }
                cno = cno + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cno;
    }
    
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        try {
            //first check todays collection shiftwise is pass or not 
            //if pass then display the paased transaction and if not then show unpassed trnsaction
            boolean collectionpass = false;
            boolean flag = false;
            boolean collectionentry = false;
            qry = "SELECT dailytrn.trndate, dailytrn.accid, dailytrn.shiftid, dailytrn.posting FROM dailytrn " +
                "WHERE (((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#)"+
                " AND ((dailytrn.accid)="+methods.milkpurchasedefaultlgrid+") AND (dailytrn.shiftid)=2);";
            PreparedStatement STMT = conn.prepareStatement(qry);
            ResultSet rs = STMT.executeQuery();
            if (rs.next()) {
                collectionentry = true;
                if (rs.getBoolean("posting") == true) {
                    collectionpass = true;
                } else {
                    collectionpass = false;
                }
                
            } else {
                collectionentry = false;
                collectionpass = false;
            }
            rs.close();
            STMT.close();
            
            // insert milk collection data
            if (collectionentry == false) {
                double totalamt = 0.0;
                int chno = getchalanno();
                
                qry = "SELECT mlkCollection.trn_date, mlkCollection.prod_code,"+
                        "[mlkCollection]![weight]*[mlkCollection]![rate] AS amt " +
                        "FROM mlkCollection " +
                        "WHERE (((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#)  AND ((mlkCollection.shift_id)=2)) " +
                        "ORDER BY mlkCollection.prod_code;";
                STMT = conn.prepareStatement(qry);
                rs = STMT.executeQuery();
                String insert_qry = "INSERT INTO dailytrn (trndate,voucherid,chalanno,accid,prodid,dramt,cramt,naration,shiftid)"+
                       " VALUES (?,?,?,?,?,?,?,?,?)";
                try (PreparedStatement insert_stmt = conn.prepareStatement(insert_qry)) {
                    while (rs.next()){
                        flag = true;
                        // producr cr entry
                        
                        insert_stmt.setString(1, ""+dmy.format(dtpdate.getDate()));
                        insert_stmt.setInt(2, 1);
                        insert_stmt.setInt(3, chno);
                        insert_stmt.setInt(4, methods.producerdefaultlgrid);
                        int prodid = rs.getInt("prod_code");
                        insert_stmt.setInt(5, prodid);
                        insert_stmt.setDouble(6, 0.0);
                        double amt = rs.getDouble("amt");
                        totalamt = totalamt + amt;
                        insert_stmt.setDouble(7, amt);
                        insert_stmt.setString(8, "दुध संकलन");
                        insert_stmt.setInt(9, 2);
                        
                        insert_stmt.execute();
                    }
                    
                    if (flag == true) {
                        // milk purchase acc dr entry
                        insert_stmt.setString(1, ""+dmy.format(dtpdate.getDate()));
                        insert_stmt.setInt(2, 1);
                        insert_stmt.setInt(3, chno);
                        insert_stmt.setInt(4, methods.milkpurchasedefaultlgrid);
                        insert_stmt.setString(5, null);
                        insert_stmt.setDouble(6, totalamt);
                        insert_stmt.setDouble(7, 0.0);
                        insert_stmt.setString(8, "दुध संकलन");
                        insert_stmt.setInt(9, 2);
                        
                        insert_stmt.execute();       
                    } else {
                        JOptionPane.showMessageDialog(null,"संध्याकाळचे दूध संकलन नाही.","Milkman", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                rs.close();
                STMT.close();                
                gettodaystrn(2);
            } else {
                if (collectionpass == true) {
                    JOptionPane.showMessageDialog(null,"संध्याकाळचे दूध संकलन पास झाले आहे.","Milkman", JOptionPane.INFORMATION_MESSAGE);
                    gettodaystrn(1);
                } else {
                    JOptionPane.showMessageDialog(null,"संध्याकाळचे दूध संकलन अनपास झाले आहे.","Milkman", JOptionPane.INFORMATION_MESSAGE);
                    gettodaystrn(2);                    
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(posting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        gettodaystrn(2);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Object chno = table_transaction.getValueAt(table_transaction.getSelectedRow(), 0);
        System.out.println(chno);
        //int chalano = Integer.parseInt(chno);
        String qry = "Update dailytrn set posting = true where chalanno = "+chno;
        try (PreparedStatement updatepass = conn.prepareStatement(qry)) {
            updatepass.execute();
            JOptionPane.showMessageDialog(null,"चलन नं "+ chno +" पास करण्यात आले आहे.","Milkman", JOptionPane.INFORMATION_MESSAGE);
            gettodaystrn(1);
        } catch (SQLException ex) {
            Logger.getLogger(posting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Object chno = table_transaction.getValueAt(table_transaction.getSelectedRow(), 0);
        System.out.println(chno);
        //int chalano = Integer.parseInt(chno);
        String qry1 = "DELETE * FROM dailytrn WHERE chalanno = "+chno;
        try (PreparedStatement updatepass = conn.prepareStatement(qry1)) {
            updatepass.execute();
        } catch (SQLException ex) {
            Logger.getLogger(posting.class.getName()).log(Level.SEVERE, null, ex);
        }
        qry1 = "DELETE * FROM milkdispatch WHERE chalanno = " + chno;
        try (PreparedStatement updatepass = conn.prepareStatement(qry1)) {
            updatepass.execute();
        } catch (SQLException ex) {
            Logger.getLogger(posting.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null,"चलन नं "+ chno +" खोडण्यात आले आहे.","Milkman", JOptionPane.INFORMATION_MESSAGE);
        gettodaystrn(3);
    }//GEN-LAST:event_jButton6ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dtpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable table_transaction;
    // End of variables declaration//GEN-END:variables

    private void gettotalamount(int flag) {
        double totaldramt = 0.0;
        double totalcramt = 0.0;
        String qry = "";
        
        if (flag == 1) { // pass voucher
            qry = "SELECT Sum(dailytrn.dramt) AS SumOfdramt, Sum(dailytrn.cramt) AS SumOfcramt" +
                  " FROM dailytrn" +
                  " WHERE (((dailytrn.posting)=True) AND ((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#));";
        } else if (flag == 2) { // unpass voucher
            qry = "SELECT Sum(dailytrn.dramt) AS SumOfdramt, Sum(dailytrn.cramt) AS SumOfcramt" +
                  " FROM dailytrn" +
                  " WHERE (((dailytrn.posting)=False) AND ((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#));";        
        } else if (flag == 3) { // all voucher
            qry = "SELECT Sum(dailytrn.dramt) AS SumOfdramt, Sum(dailytrn.cramt) AS SumOfcramt" +
                  " FROM dailytrn" +
                  " WHERE ((dailytrn.trndate)=#"+mdy.format(dtpdate.getDate())+"#);";            
        }
        try (PreparedStatement vouchertotal = conn.prepareStatement(qry);ResultSet rsgettotal = vouchertotal.executeQuery()) {
            
            if (rsgettotal.next()) {
                jTextField2.setText(""+twodf.format(rsgettotal.getDouble(2)));
                jTextField1.setText(""+twodf.format(rsgettotal.getDouble(1)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(posting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
