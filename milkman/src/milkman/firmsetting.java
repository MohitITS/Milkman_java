/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Rakesh
 */
public class firmsetting extends javax.swing.JInternalFrame {

    /**
     * Creates new form firmsetting
     */
    
    private Date date = new Date();
    Connection conn;
    PreparedStatement STATEMENT = null;
    String qry = "";    
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    int acctypeid[];int acctypeid1[];int acctypeid2[];int acctypeid3[];
    int acctypeid4[];
    
    public firmsetting() {
        try {
            initComponents();
            if(conn!=null){
                conn.close();
            }   
            conn = methods.getConnection();
            
            cbacctype.removeAll();
            cbacctype1.removeAll();
            cbacctype2.removeAll();
            cbacctype3.removeAll();
            cbacctype4.removeAll();
            String qry = "SELECT account.accname, account.ID " +
                "FROM account " +
                "ORDER BY account.ID;";
            int accid;
            String accname;
            try (PreparedStatement loadcombo = conn.prepareStatement(qry);ResultSet rslc = loadcombo.executeQuery()) {
                int i=0;
                acctypeid = new int[100];
                acctypeid1 = new int[100];
                acctypeid2 = new int[100];
                acctypeid3 = new int[100];
                acctypeid4 = new int[100];
                while (rslc.next()) {
                   accname = rslc.getString("accname");
                   cbacctype.insertItemAt(accname, i);
                   cbacctype1.insertItemAt(accname, i);
                   cbacctype3.insertItemAt(accname, i);
                   cbacctype2.insertItemAt(accname, i);
                   cbacctype4.insertItemAt(accname, i);
                   accid = rslc.getInt("ID");
                   acctypeid[i] = accid;
                   acctypeid1[i] = accid;
                   acctypeid3[i] = accid;
                   acctypeid2[i] = accid;
                   acctypeid4[i] = accid;
                   i++;
                }
            }            

//            qry = "SELECT * FROM accounttype ORDER BY ID;";
//            try (PreparedStatement loadcombo = conn.prepareStatement(qry);ResultSet rslc = loadcombo.executeQuery()) {
//                int i=0;
//                acctypeid2 = new int[100];
//                while (rslc.next()) {
//                   accname = rslc.getString("accountname");
//                   cbacctype2.insertItemAt(accname, i);
//                   accid = rslc.getInt("ID");
//                   acctypeid2[i] = accid;
//
//                   i++;
//                }
//            } 
            
            
            qry = "Select * from config"; 
            STATEMENT = conn.prepareStatement(qry);            
            ResultSet rs;
            rs = STATEMENT.executeQuery();
            if(rs.next()) {
                txtfirmid.setText(rs.getString("ID"));
                txtfirmname.setText(rs.getString("firmname"));
                txtaddress.setText(rs.getString("address"));
                txtcontacno.setText(rs.getString("contatno"));
                chkplant.setSelected(rs.getBoolean("plantorcenter"));
                txtownername.setText(rs.getString("regpersonname"));
                txtregno.setText(rs.getString("firmregno"));
                dtpfromdate.setDate(rs.getDate("curryearfrom"));
                dtptodate.setDate(rs.getDate("curryearto"));
                txtlang.setText(rs.getString("swlang"));
                txtbackpath.setText(rs.getString("reportspath"));
                txtbank.setText(rs.getString("bank"));
                txtbankacno.setText(rs.getString("bankacno"));
                txtbackgrounimage.setText(rs.getString("backroundimage"));
                txtdatabasepath.setText(rs.getString("databasepath"));
                
                cbacctype.setSelectedIndex(0);
                cbacctype1.setSelectedIndex(1);
                cbacctype2.setSelectedIndex(3);
                cbacctype3.setSelectedIndex(2);
                cbacctype4.setSelectedIndex(4);
                
            }
            rs.close();
            STATEMENT.close();
        } catch (SQLException ex) {
            Logger.getLogger(firmsetting.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtfirmid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtfirmname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtcontacno = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaddress = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        chkplant = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        txtownername = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dtpfromdate = new com.toedter.calendar.JDateChooser();
        dtptodate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtregno = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtlang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtbackpath = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtbank = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtbankacno = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtbackgrounimage = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtdatabasepath = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbacctype = new javax.swing.JComboBox();
        cbacctype1 = new javax.swing.JComboBox();
        cbacctype2 = new javax.swing.JComboBox();
        cbacctype3 = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        cbacctype4 = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("दुध डेअरी सेटिंग");
        setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel1.setText("डेअरी नं :");

        txtfirmid.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel2.setText("डेअरीचे नाव :");

        txtfirmname.setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel3.setText("संपर्क नंबर :");

        txtcontacno.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel4.setText("पत्ता :");

        txtaddress.setColumns(20);
        txtaddress.setRows(5);
        jScrollPane1.setViewportView(txtaddress);

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel5.setText("प्लांट :");

        chkplant.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel6.setText("वर्ष पासून :");

        txtownername.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel7.setText("मालकाचे नाव :");

        dtpfromdate.setFocusable(false);
        dtpfromdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        dtptodate.setFocusable(false);
        dtptodate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel8.setText("ते");

        jLabel9.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel9.setText("डेअरीचे रजि. नं :");

        txtregno.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel10.setText("भाषा :");

        txtlang.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        txtlang.setText("मराठी");

        jLabel11.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel11.setText("रिपोर्ट पाथ :");

        txtbackpath.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jButton2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton2.setText("सेव्ह");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton3.setText("बाहेर");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel12.setText("बँक :");

        txtbank.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel13.setText("बँक खाते नं :");

        txtbankacno.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel14.setText("बॅकग्राउंड इमेज :");

        txtbackgrounimage.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jButton4.setText("jButton1");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel15.setText("डाटाबेस पाथ :");

        txtdatabasepath.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N

        jButton5.setText("jButton1");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 204));
        jLabel16.setText("डीफॉल्ट खाते:");

        jLabel17.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel17.setText("दुध खरेदी खाते :");

        jLabel18.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel18.setText("हातावरील रोख शिल्लक :");

        jLabel19.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel19.setText("उत्पादक खाते :");

        jLabel20.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel20.setText("दुध विक्री खाते :");

        cbacctype.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbacctype.setBorder(null);
        cbacctype.setOpaque(false);

        cbacctype1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbacctype1.setBorder(null);
        cbacctype1.setOpaque(false);

        cbacctype2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbacctype2.setBorder(null);
        cbacctype2.setOpaque(false);

        cbacctype3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbacctype3.setBorder(null);
        cbacctype3.setOpaque(false);

        jLabel21.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel21.setText("संघ खाते :");

        cbacctype4.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbacctype4.setBorder(null);
        cbacctype4.setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbank, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtfirmname, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1)
                                    .addComponent(txtcontacno, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(dtpfromdate, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(26, 26, 26)
                                            .addComponent(jLabel8)
                                            .addGap(18, 18, 18)
                                            .addComponent(dtptodate, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtownername, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtregno, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtfirmid, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(chkplant, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtlang, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbackpath, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbankacno, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtbackgrounimage, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtdatabasepath, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(jLabel16))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel19))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbacctype, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbacctype1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbacctype2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbacctype3, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbacctype4, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtfirmid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chkplant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtlang, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jLabel16)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtfirmname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtcontacno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtownername, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtregno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(dtpfromdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtptodate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbackpath, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbank, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbankacno, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbackgrounimage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtdatabasepath, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(cbacctype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(cbacctype1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(cbacctype2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(cbacctype3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(cbacctype4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        //jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File f = jfc.getCurrentDirectory();
        String fp = f.getAbsolutePath();
        txtbackpath.setText(fp);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(firmsetting.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
//            qry = "UPDATE config SET firmname=?, address=?, contatno=?, plantorcenter=?,"+
//                    " regpersonname=?, curryearfrom=?, curryearto=?, firmregno=?, swlang=?,"+
//                    " reportspath=?, bank=?, bankacno=?, backroundimage=?, databasepath=?,"+
//                    " producerdefaultlgrid=?, milkpurchasedefaultlgrid=?, milkselldefaultlgrid=?,"+
//                    " cashdefaultlgrid=?,sanghdefaultid=? WHERE ID="+Integer.parseInt(txtfirmid.getText())+";";
            qry = "UPDATE config SET firmname=?, address=?, contatno=?, plantorcenter=?,"+
                    " regpersonname=?, curryearfrom=?, curryearto=?, firmregno=?, swlang=?,"+
                    " reportspath=?, bank=?, bankacno=?, backroundimage=?, databasepath=?,"
                    + " producerdefaultlgrid=?, milkpurchasedefaultlgrid=?, milkselldefaultlgrid=?,"
                    + " cashdefaultlgrid=?, sanghdefaultid=?"+
                    " WHERE ID="+Integer.parseInt(txtfirmid.getText())+";";
            STATEMENT = conn.prepareStatement(qry);
            STATEMENT.setString(1, txtfirmname.getText());
            STATEMENT.setString(2, txtaddress.getText());
            STATEMENT.setString(3, txtcontacno.getText());
            STATEMENT.setBoolean(4, chkplant.isSelected());
            STATEMENT.setString(5, txtownername.getText());
            STATEMENT.setString(6, ""+dmy.format(dtpfromdate.getDate()));
            STATEMENT.setString(7, ""+dmy.format(dtptodate.getDate()));
            STATEMENT.setString(8, txtregno.getText());
            STATEMENT.setString(9, txtlang.getText());
            STATEMENT.setString(10, txtbackpath.getText());
            STATEMENT.setString(11, txtbank.getText());
            STATEMENT.setString(12, txtbankacno.getText());
            STATEMENT.setString(13, txtbackgrounimage.getText());
            STATEMENT.setString(14, txtdatabasepath.getText());
            int acctid = acctypeid[cbacctype.getSelectedIndex()];
            //System.out.println(acctid);   
            STATEMENT.setInt(15, acctypeid2[acctid]);
            //acctid = acctypeid2[cbacctype2.getSelectedIndex()];
            //System.out.println(acctid);   
            STATEMENT.setInt(16, acctypeid[cbacctype.getSelectedIndex()]);
            //acctid = acctypeid3[cbacctype3.getSelectedIndex()];
            //System.out.println(acctid);   
            STATEMENT.setInt(17, acctypeid3[cbacctype3.getSelectedIndex()]);
            //acctid = acctypeid1[cbacctype1.getSelectedIndex()];
            //System.out.println(acctid);   
            STATEMENT.setInt(18, acctypeid1[cbacctype1.getSelectedIndex()]);
            STATEMENT.setInt(19, acctypeid4[cbacctype4.getSelectedIndex()]);

            
            System.out.println("milkman.firmsetting.jButton2ActionPerformed() : " + qry);
            
            STATEMENT.execute();
            STATEMENT.close();
            
            JOptionPane.showMessageDialog(null, "माहिती अपडेट झाली..!! कृपया सोफ्टवेअर पुन्हा चालू करा. धन्यवाद.!!", title, JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
            
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(firmsetting.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(firmsetting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        //jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File f = jfc.getSelectedFile();
        String fp = f.getAbsolutePath();
        txtbackgrounimage.setText(fp);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        //jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        File f = jfc.getSelectedFile();
        String fp = f.getAbsolutePath();
        txtdatabasepath.setText(fp);
    }//GEN-LAST:event_jButton5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbacctype;
    private javax.swing.JComboBox cbacctype1;
    private javax.swing.JComboBox cbacctype2;
    private javax.swing.JComboBox cbacctype3;
    private javax.swing.JComboBox cbacctype4;
    private javax.swing.JCheckBox chkplant;
    private com.toedter.calendar.JDateChooser dtpfromdate;
    private com.toedter.calendar.JDateChooser dtptodate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtaddress;
    private javax.swing.JTextField txtbackgrounimage;
    private javax.swing.JTextField txtbackpath;
    private javax.swing.JTextField txtbank;
    private javax.swing.JTextField txtbankacno;
    private javax.swing.JTextField txtcontacno;
    private javax.swing.JTextField txtdatabasepath;
    private javax.swing.JTextField txtfirmid;
    private javax.swing.JTextField txtfirmname;
    private javax.swing.JTextField txtlang;
    private javax.swing.JTextField txtownername;
    private javax.swing.JTextField txtregno;
    // End of variables declaration//GEN-END:variables
}
