
package milkman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class collectionsetting extends javax.swing.JInternalFrame {

    private Date date = new Date();
    Connection conn;
    PreparedStatement STATEMENT = null;
    String qry = "";    
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    int[] firmid;
    int i;
    
    public collectionsetting() {
        try {
            initComponents();
            if(conn!=null){
                conn.close();
            }
            conn = methods.getConnection();
            qry = "select ID, firmname from config;";
            try (PreparedStatement dairyname = conn.prepareStatement(qry); ResultSet rs = dairyname.executeQuery()) {
                cbfirm.removeAllItems();
                i = 0;
                firmid = new int[10];
                while (rs.next()) {
                    cbfirm.addItem(rs.getString("firmname"));
                    firmid[i] = rs.getInt("ID");
                }
            }
            
            qry = "Select * from collectionsetting where firmid = " + firmid[cbfirm.getSelectedIndex()];
            try (PreparedStatement getcollsett = conn.prepareStatement(qry); ResultSet rs = getcollsett.executeQuery()) {
                while (rs.next()) {
                    jTextField1.setText(rs.getString("ID"));
                    methods.collonline = rs.getBoolean("collectiononline");
                    methods.pigmyinerface = rs.getBoolean("pigmyinterfacrreq");
                    methods.drp = rs.getBoolean("drp");
                    methods.deductrebateinbill = rs.getBoolean("deductrebateinbill");
                    methods.rebateperltr = rs.getDouble("rebateperltr");
                    methods.CM_Sellrate = rs.getDouble("CMSellrate");
                    methods.BM_Sellrate = rs.getDouble("BMSellrate");
                    methods.fix_snf = rs.getBoolean("fixsnf");
                    methods.std_CMSNF = rs.getDouble("standard_CMSNF");
                    methods.std_BMSNF = rs.getDouble("standard_BMSNF");
                    methods.std_CMFAT = rs.getDouble("standard_CMFAT");
                    methods.std_BMFAT = rs.getDouble("standard_BMFAT");                
                    methods.fix_clr = rs.getBoolean("fixclr");
                    methods.std_CMCLR = rs.getDouble("standard_CMCLR");
                    methods.std_BMCLR = rs.getDouble("standard_BMCLR");                
                    methods.mor_collectionrateinc = rs.getDouble("mor_coll_rate_increment");
                    methods.eve_collectionrateinc = rs.getDouble("eve_coll_rate_increment");
                    methods.min_CMFAT = rs.getDouble("min_CMFAT");
                    methods.min_BMFAT = rs.getDouble("min_BMFAT");
                    methods.max_CMFAT = rs.getDouble("max_CMFAT");
                    methods.max_BMFAT = rs.getDouble("max_BMFAT");
                    methods.min_CMSNF = rs.getDouble("min_CMSNF");
                    methods.min_BMSNF = rs.getDouble("min_BMSNF");
                    methods.max_CMSNF = rs.getDouble("max_CMSNF");
                    methods.max_BMSNF = rs.getDouble("max_BMSNF");
                    methods.manualrateentry = rs.getBoolean("manualrateentry");
                    methods.bill_period = rs.getInt("billperiod");     
                    methods.prodcodegroupwise = rs.getBoolean("prodcodegrwise");
                    methods.prodcodemanual = rs.getBoolean("prodcodemanual");
                    methods.smsfacilityreq = rs.getBoolean("smsfacilityreq");
                    methods.sms_senderid = rs.getString("sms_senderid");
                    methods.smssendby = rs.getString("smssendby");                    
                    methods.ratechartcalcbase_CM = rs.getString("ratechartcalcbase_CM");
                    methods.max_cmclr = rs.getDouble("max_cmclr");
                    methods.min_cmclr = rs.getDouble("min_cmclr");
                    methods.max_bmclr = rs.getDouble("max_bmclr");
                    methods.min_bmclr = rs.getDouble("min_bmclr");
                    methods.ratechartcalcbase_BM = rs.getString("ratechartcalcbase_BM");
                    methods.clrdiff = rs.getDouble("clrdiff");
                    
                    onlinecollection.setSelected(methods.collonline);
                    pigmyinterface.setSelected(methods.pigmyinerface);
                    drp.setSelected(methods.drp);
                    deductrebate.setSelected(methods.deductrebateinbill);
                    rebateperltr.setValue(methods.rebateperltr);
                    
                    
                    cmsellrate.setValue(methods.CM_Sellrate);
                    bmsellrate.setValue(methods.BM_Sellrate);
                    
                    fixedsnf.setSelected(methods.fix_snf);
                    cmssnf.setValue(methods.std_CMSNF);
                    bmssnf.setValue(methods.std_BMSNF);
                    cmsf.setValue(methods.std_CMFAT);
                    bmsf.setValue(methods.std_BMFAT);
                    fixedclr.setSelected(methods.fix_clr);
                    cmsclr.setValue(methods.std_CMCLR);
                    bmsclr.setValue(methods.std_BMCLR);
                    
                    morningcollrateinc.setValue(methods.mor_collectionrateinc);
                    eveningcollrateinc.setValue(methods.eve_collectionrateinc);
                    
                    cmminfat.setValue(methods.min_CMFAT);
                    bmminfat.setValue(methods.min_BMFAT);
                    cmmaxfat.setValue(methods.max_CMFAT);
                    bmmaxfat.setValue(methods.max_BMFAT);
                    cmminsnf.setValue(methods.min_CMSNF);
                    bmminsnf.setValue(methods.min_BMSNF);
                    cmmaxsnf.setValue(methods.max_CMSNF);
                    bmmaxsnf.setValue(methods.max_BMSNF);
                    
                    manualrate.setSelected(methods.manualrateentry);
                    billperiod.setValue(methods.bill_period);
                    chkprodcodegrwise.setSelected(methods.prodcodegroupwise);
                    chkprodcodmanual.setSelected(methods.prodcodemanual);
                    chkprodcodmanual1.setSelected(methods.smsfacilityreq);
                    jTextField2.setText(methods.sms_senderid);
                    jTextField3.setText(methods.smssendby);
                    
                    bmmaxCLR.setValue(methods.max_bmclr);
                    bmminCLR.setValue(methods.min_bmclr);
                    cmmaxCLR.setValue(methods.max_cmclr);
                    cmminCLR.setValue(methods.min_cmclr);
                    switch (methods.ratechartcalcbase_CM) {
                        case "FAT-CLR":
                            cmb_CMRATECHARTBASE.setSelectedIndex(2);
                            break;
                        case "FAT-SNF":
                            cmb_CMRATECHARTBASE.setSelectedIndex(1);
                            break;
                        default:
                            cmb_CMRATECHARTBASE.setSelectedIndex(0);
                            break;
                    }
                    switch (methods.ratechartcalcbase_BM) {
                        case "FAT-CLR":
                            cmb_CMRATECHARTBASE.setSelectedIndex(2);
                            break;
                        case "FAT-SNF":
                            cmb_CMRATECHARTBASE.setSelectedIndex(1);
                            break;
                        default:
                            cmb_CMRATECHARTBASE.setSelectedIndex(0);
                            break;
                    }
                    clrdiff.setValue(methods.clrdiff);
                    is_clr_not_mandatory.setSelected(methods.is_clr_not_mandatory);
                    deduct_rebate_from_bill.setSelected(methods.deduct_rebate_from_bill);
                    print_total_amount_in_reciept.setSelected(methods.print_total_amount_in_reciept);
                    reciept_lang.setSelectedIndex("Marathi".equals(methods.reciept_lang) ? 0 : 1);
                    reciept_clr.setSelected(methods.reciept_clr);
                    reciept_snf.setSelected(methods.reciept_snf);
                }
            }
        } catch (SQLException ex) {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbfirm = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        onlinecollection = new javax.swing.JCheckBox();
        pigmyinterface = new javax.swing.JCheckBox();
        drp = new javax.swing.JCheckBox();
        deductrebate = new javax.swing.JCheckBox();
        rebateperltr = new javax.swing.JSpinner();
        fixedsnf = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmssnf = new javax.swing.JSpinner();
        bmssnf = new javax.swing.JSpinner();
        fixedclr = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        cmsclr = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        bmsclr = new javax.swing.JSpinner();
        manualrate = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        cmsf = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        bmsf = new javax.swing.JSpinner();
        is_clr_not_mandatory = new javax.swing.JCheckBox();
        nofixedsnfclr = new javax.swing.JCheckBox();
        deduct_rebate_from_bill = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        reciept_clr = new javax.swing.JCheckBox();
        print_total_amount_in_reciept = new javax.swing.JCheckBox();
        reciept_snf = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmsellrate = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        bmsellrate = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        billperiod = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        morningcollrateinc = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        eveningcollrateinc = new javax.swing.JSpinner();
        chkprodcodegrwise = new javax.swing.JCheckBox();
        chkprodcodmanual = new javax.swing.JCheckBox();
        chkprodcodmanual1 = new javax.swing.JCheckBox();
        jTextField2 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        cmminfat = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        bmminfat = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        cmmaxfat = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        bmmaxfat = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        cmminsnf = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        bmminsnf = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        cmmaxsnf = new javax.swing.JSpinner();
        jLabel21 = new javax.swing.JLabel();
        bmmaxsnf = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        bmminCLR = new javax.swing.JSpinner();
        jLabel26 = new javax.swing.JLabel();
        bmmaxCLR = new javax.swing.JSpinner();
        jLabel27 = new javax.swing.JLabel();
        cmminCLR = new javax.swing.JSpinner();
        jLabel28 = new javax.swing.JLabel();
        cmmaxCLR = new javax.swing.JSpinner();
        jLabel29 = new javax.swing.JLabel();
        cmb_CMRATECHARTBASE = new javax.swing.JComboBox();
        clrdiff = new javax.swing.JSpinner();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        reciept_lang = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("Collection Setting");
        setOpaque(true);

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

        jLabel1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("डेअरी :");

        cbfirm.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cbfirm.setForeground(new java.awt.Color(255, 0, 51));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        onlinecollection.setBackground(new java.awt.Color(255, 255, 255));
        onlinecollection.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        onlinecollection.setText("Collection Online");

        pigmyinterface.setBackground(new java.awt.Color(255, 255, 255));
        pigmyinterface.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        pigmyinterface.setText("Pigmy interface required");
        pigmyinterface.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pigmyinterfaceActionPerformed(evt);
            }
        });

        drp.setBackground(new java.awt.Color(255, 255, 255));
        drp.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("milkman/Bundle"); // NOI18N
        drp.setText(bundle.getString("collectionsetting.drp.text")); // NOI18N

        deductrebate.setBackground(new java.awt.Color(255, 255, 255));
        deductrebate.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        deductrebate.setText("Deduct following amount per liter to all producer during bill");
        deductrebate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deductrebateActionPerformed(evt);
            }
        });

        rebateperltr.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        rebateperltr.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 9.0d, 0.1d));

        fixedsnf.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(fixedsnf);
        fixedsnf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        fixedsnf.setText("Fixed SNF");
        fixedsnf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixedsnfActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("CM standard SNF :");

        jLabel4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("BM standard SNF :");

        cmssnf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmssnf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        bmssnf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmssnf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        fixedclr.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(fixedclr);
        fixedclr.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        fixedclr.setText("Fixed CLR");
        fixedclr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixedclrActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("CM standard CLR :");

        cmsclr.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmsclr.setModel(new javax.swing.SpinnerNumberModel(21.0d, 20.0d, 35.0d, 0.1d));

        jLabel8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("BM standard CLR :");

        bmsclr.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmsclr.setModel(new javax.swing.SpinnerNumberModel(21.0d, 20.0d, 35.0d, 0.1d));

        manualrate.setBackground(new java.awt.Color(255, 255, 255));
        manualrate.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        manualrate.setText("Manual rate entry required in collection form");

        jLabel12.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("CM standard FAT :");

        cmsf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmsf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel13.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("BM standard FAT :");

        bmsf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmsf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        is_clr_not_mandatory.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(is_clr_not_mandatory);
        is_clr_not_mandatory.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        is_clr_not_mandatory.setText(bundle.getString("collectionsetting.is_clr_not_mandatory.text")); // NOI18N
        is_clr_not_mandatory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                is_clr_not_mandatoryActionPerformed(evt);
            }
        });

        nofixedsnfclr.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(nofixedsnfclr);
        nofixedsnfclr.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        nofixedsnfclr.setText(bundle.getString("collectionsetting.nofixedsnfclr.text")); // NOI18N
        nofixedsnfclr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nofixedsnfclrActionPerformed(evt);
            }
        });

        deduct_rebate_from_bill.setBackground(new java.awt.Color(255, 255, 255));
        deduct_rebate_from_bill.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        deduct_rebate_from_bill.setText(bundle.getString("collectionsetting.deduct_rebate_from_bill.text")); // NOI18N

        jLabel2.setText(bundle.getString("collectionsetting.jLabel2.text")); // NOI18N

        reciept_clr.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reciept_clr.setText(bundle.getString("collectionsetting.reciept_clr.text")); // NOI18N

        print_total_amount_in_reciept.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        print_total_amount_in_reciept.setText(bundle.getString("collectionsetting.print_total_amount_in_reciept.text")); // NOI18N

        reciept_snf.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reciept_snf.setText(bundle.getString("collectionsetting.reciept_snf.text")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(reciept_clr)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reciept_snf))
                        .addComponent(print_total_amount_in_reciept)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reciept_clr)
                    .addComponent(reciept_snf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(print_total_amount_in_reciept)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmsclr, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bmsclr, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(manualrate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(fixedclr)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(nofixedsnfclr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmsf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmssnf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bmsf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bmssnf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(is_clr_not_mandatory)
                                    .addComponent(fixedsnf)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(drp)
                                    .addComponent(onlinecollection)
                                    .addComponent(pigmyinterface)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(rebateperltr, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(deduct_rebate_from_bill))
                                    .addComponent(deductrebate))
                                .addGap(13, 13, 13)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(onlinecollection)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pigmyinterface)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(drp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deductrebate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rebateperltr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(deduct_rebate_from_bill)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(is_clr_not_mandatory, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(cmsf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(bmsf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmssnf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(bmssnf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fixedsnf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmsclr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(bmsclr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fixedclr))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manualrate)
                    .addComponent(nofixedsnfclr))
                .addGap(8, 8, 8))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel5.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("CM Sell Rate :");

        cmsellrate.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmsellrate.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 99.0d, 0.1d));

        jLabel6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("BM Sell Rate :");

        bmsellrate.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmsellrate.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 99.0d, 0.1d));

        jLabel9.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Bill Period :");

        billperiod.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        billperiod.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 99.0d, 0.1d));

        jLabel10.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Morning collection rate increment :");

        morningcollrateinc.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        morningcollrateinc.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 3.0d, 0.1d));

        jLabel11.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Evening collection rate increment :");

        eveningcollrateinc.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        eveningcollrateinc.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 3.0d, 0.1d));

        chkprodcodegrwise.setBackground(new java.awt.Color(255, 255, 255));
        chkprodcodegrwise.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        chkprodcodegrwise.setText(bundle.getString("collectionsetting.chkprodcodegrwise.text")); // NOI18N
        chkprodcodegrwise.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkprodcodegrwise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkprodcodegrwiseActionPerformed(evt);
            }
        });

        chkprodcodmanual.setBackground(new java.awt.Color(255, 255, 255));
        chkprodcodmanual.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        chkprodcodmanual.setText(bundle.getString("collectionsetting.chkprodcodmanual.text")); // NOI18N
        chkprodcodmanual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkprodcodmanual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkprodcodmanualActionPerformed(evt);
            }
        });

        chkprodcodmanual1.setBackground(new java.awt.Color(255, 255, 255));
        chkprodcodmanual1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        chkprodcodmanual1.setText(bundle.getString("collectionsetting.chkprodcodmanual1.text")); // NOI18N
        chkprodcodmanual1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkprodcodmanual1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkprodcodmanual1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 0, 51));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText(bundle.getString("collectionsetting.jTextField2.text")); // NOI18N

        jLabel23.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText(bundle.getString("collectionsetting.jLabel23.text")); // NOI18N

        jLabel24.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText(bundle.getString("collectionsetting.jLabel24.text")); // NOI18N

        jTextField3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 0, 51));
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextField3.setText(bundle.getString("collectionsetting.jTextField3.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmsellrate, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bmsellrate, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(billperiod, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(chkprodcodegrwise, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkprodcodmanual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(morningcollrateinc, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(eveningcollrateinc, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(chkprodcodmanual1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3)))
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmsellrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(bmsellrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(billperiod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(morningcollrateinc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(eveningcollrateinc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkprodcodegrwise)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkprodcodmanual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkprodcodmanual1)
                    .addComponent(jTextField2)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel5.getAccessibleContext().setAccessibleName("");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel14.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("CM minimum FAT :");

        cmminfat.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmminfat.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel15.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("BM minimum FAT :");

        bmminfat.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmminfat.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel16.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("CM maximum FAT :");

        cmmaxfat.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmmaxfat.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("BM maximum FAT :");

        bmmaxfat.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmmaxfat.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel18.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("CM minimum SNF :");

        cmminsnf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmminsnf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel19.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("BM minimum SNF :");

        bmminsnf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmminsnf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel20.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("CM maximum SNF :");

        cmmaxsnf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmmaxsnf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel21.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("BM maximum SNF :");

        bmmaxsnf.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmmaxsnf.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel22.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 51));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("SNF = (0.21 * FAT Reading) + (0.25 * CLR Reading) + 0.36");

        jLabel25.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText(bundle.getString("collectionsetting.jLabel25.text")); // NOI18N

        bmminCLR.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmminCLR.setModel(new javax.swing.SpinnerNumberModel(20.0d, 20.0d, 40.0d, 0.1d));

        jLabel26.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText(bundle.getString("collectionsetting.jLabel26.text")); // NOI18N

        bmmaxCLR.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        bmmaxCLR.setModel(new javax.swing.SpinnerNumberModel(21.0d, 20.0d, 40.0d, 0.1d));

        jLabel27.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText(bundle.getString("collectionsetting.jLabel27.text")); // NOI18N

        cmminCLR.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmminCLR.setModel(new javax.swing.SpinnerNumberModel(21.0d, 20.0d, 40.0d, 0.1d));

        jLabel28.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText(bundle.getString("collectionsetting.jLabel28.text")); // NOI18N

        cmmaxCLR.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        cmmaxCLR.setModel(new javax.swing.SpinnerNumberModel(21.0d, 20.0d, 40.0d, 0.1d));

        jLabel29.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText(bundle.getString("collectionsetting.jLabel29.text")); // NOI18N

        cmb_CMRATECHARTBASE.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmb_CMRATECHARTBASE.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "FAT-SNF", "FAT-CLR" }));

        clrdiff.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        clrdiff.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 12.0d, 0.1d));

        jLabel31.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText(bundle.getString("collectionsetting.jLabel31.text")); // NOI18N

        jLabel32.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText(bundle.getString("collectionsetting.jLabel32.text")); // NOI18N

        reciept_lang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Marathi", "English" }));
        reciept_lang.setToolTipText(bundle.getString("collectionsetting.reciept_lang.toolTipText")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmmaxsnf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmminsnf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmmaxfat, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmminfat, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bmminfat, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bmmaxfat, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bmminsnf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bmmaxsnf, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmmaxCLR, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(cmminCLR, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(bmmaxCLR, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bmminCLR, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel29)
                                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmb_CMRATECHARTBASE, 0, 125, Short.MAX_VALUE)
                                    .addComponent(clrdiff, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(reciept_lang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(jLabel22))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cmminfat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(bmminfat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(bmminCLR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(cmmaxfat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(bmmaxfat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(bmmaxCLR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(cmb_CMRATECHARTBASE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clrdiff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmminsnf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(bmminsnf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(cmminCLR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(reciept_lang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmmaxsnf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(bmmaxsnf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(cmmaxCLR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addContainerGap())
        );

        jLabel15.getAccessibleContext().setAccessibleName("");
        jLabel22.getAccessibleContext().setAccessibleName(bundle.getString("collectionsetting.jLabel22.AccessibleContext.accessibleName")); // NOI18N

        jTextField1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 0, 51));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText(bundle.getString("collectionsetting.jTextField1.text")); // NOI18N
        jTextField1.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(cbfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            qry = "UPDATE collectionsetting SET "
                    + "firmid = ?, "
                    + "collectiononline = ?, "
                    + "pigmyinterfacrreq = ? ," 
                    + "drp = ?, "
                    + "deductrebateinbill = ?, "
                    + "rebateperltr = ?, "
                    + "CMSellrate = ?, "
                    + "BMSellrate = ?, "
                    + "fixsnf = ?, "
                    + "standard_CMSNF = ?, " 
                    + "standard_BMSNF = ?, "
                    + "standard_CMFAT = ?, "
                    + "standard_BMFAT = ?, "
                    + "fixclr = ?, "
                    + "standard_CMCLR = ?, "
                    + "standard_BMCLR = ?, " 
                    + "mor_coll_rate_increment = ?, "
                    + "eve_coll_rate_increment = ?, "
                    + "min_CMFAT = ?, "
                    + "min_BMFAT = ?, "
                    + "max_CMFAT = ?, "
                    + "max_BMFAT = ?, " 
                    + "min_CMSNF = ?, "
                    + "min_BMSNF = ?, "
                    + "max_CMSNF = ?, "
                    + "max_BMSNF = ?, "
                    + "manualrateentry = ?, "
                    + "billperiod = ?, "
                    + "prodcodegrwise = ?, "
                    + "prodcodemanual = ?, "
                    + "smsfacilityreq = ?, "
                    + "sms_senderid = ?, "
                    + "smssendby = ?, "
                    + "ratechartcalcbase_CM = ?, " 
                    + "max_cmclr = ?, "
                    + "min_cmclr = ?, "
                    + "max_bmclr = ?, " 
                    + "min_bmclr = ?, "
                    + "ratechartcalcbase_BM = ?, "
                    + "clrdiff = ?, "
                    + "is_clr_not_mandatory = ?, "
                    + "deduct_rebate_from_bill = ?, "
                    + "print_total_amount_in_reciept = ?, "
                    + "reciept_lang = ?, "
                    + "reciept_clr = ?, "
                    + "reciept_snf = ? WHERE ID = " + jTextField1.getText();
            //System.out.println(qry);
            STATEMENT = conn.prepareStatement(qry);
            
            STATEMENT.setString(1, ""+firmid[cbfirm.getSelectedIndex()]);
            STATEMENT.setBoolean(2, onlinecollection.isSelected());
            STATEMENT.setBoolean(3, pigmyinterface.isSelected());
            STATEMENT.setBoolean(4, drp.isSelected());
            STATEMENT.setBoolean(5, deductrebate.isSelected());
            STATEMENT.setString(6, ""+rebateperltr.getValue());
            STATEMENT.setString(7, ""+cmsellrate.getValue());
            STATEMENT.setString(8, ""+bmsellrate.getValue());
            STATEMENT.setBoolean(9, fixedsnf.isSelected());
            STATEMENT.setString(10, ""+cmssnf.getValue());
            STATEMENT.setString(11, ""+bmssnf.getValue());
            STATEMENT.setString(12, ""+cmsf.getValue());
            STATEMENT.setString(13, ""+bmsf.getValue());
            STATEMENT.setBoolean(14, fixedclr.isSelected());
            STATEMENT.setString(15, ""+cmsclr.getValue());
            STATEMENT.setString(16, ""+bmsclr.getValue());
            STATEMENT.setString(17, ""+morningcollrateinc.getValue());
            STATEMENT.setString(18, ""+eveningcollrateinc.getValue());
            STATEMENT.setString(19, ""+cmminfat.getValue());
            STATEMENT.setString(20, ""+bmminfat.getValue());
            STATEMENT.setString(21, ""+cmmaxfat.getValue());
            STATEMENT.setString(22, ""+bmmaxfat.getValue());
            STATEMENT.setString(23, ""+cmminsnf.getValue());
            STATEMENT.setString(24, ""+bmminsnf.getValue());
            STATEMENT.setString(25, ""+cmmaxsnf.getValue());
            STATEMENT.setString(26, ""+bmmaxsnf.getValue());
            STATEMENT.setBoolean(27, manualrate.isSelected());
            STATEMENT.setString(28, ""+billperiod.getValue());
            STATEMENT.setBoolean(29, chkprodcodegrwise.isSelected());
            STATEMENT.setBoolean(30, chkprodcodmanual.isSelected());
            STATEMENT.setBoolean(31, chkprodcodmanual1.isSelected());
            STATEMENT.setString(32, ""+jTextField2.getText());
            STATEMENT.setString(33, ""+jTextField3.getText());
            if (cmb_CMRATECHARTBASE.getSelectedIndex()==1) {
                STATEMENT.setString(34, "FAT-SNF");
            } else if (cmb_CMRATECHARTBASE.getSelectedIndex()==2) {
                STATEMENT.setString(34, "FAT-CLR");
            }
            STATEMENT.setString(35, ""+cmmaxCLR.getValue());
            STATEMENT.setString(36, ""+cmminCLR.getValue());
            STATEMENT.setString(37, ""+bmmaxCLR.getValue());
            STATEMENT.setString(38, ""+bmminCLR.getValue());
            if (cmb_CMRATECHARTBASE.getSelectedIndex()==1) {
                STATEMENT.setString(39, "FAT-SNF");
            } else if (cmb_CMRATECHARTBASE.getSelectedIndex()==2) {
                STATEMENT.setString(39, "FAT-CLR");
            }
            STATEMENT.setString(40, ""+clrdiff.getValue());
            //System.out.println(is_clr_not_mandatory.isSelected());
            STATEMENT.setBoolean(41, is_clr_not_mandatory.isSelected());
            STATEMENT.setBoolean(42, deduct_rebate_from_bill.isSelected());
            STATEMENT.setBoolean(43, print_total_amount_in_reciept.isSelected());
            STATEMENT.setString(44, (String) reciept_lang.getSelectedItem());
            STATEMENT.setBoolean(45, reciept_clr.isSelected());
            STATEMENT.setBoolean(46, reciept_snf.isSelected());
            
            STATEMENT.execute();
            
            JOptionPane.showMessageDialog(null, "माहिती अपडेट झाली..!!", title, JOptionPane.INFORMATION_MESSAGE);

            qry = "Select * from collectionsetting where firmid = " + firmid[cbfirm.getSelectedIndex()];
            try (PreparedStatement getcollsett = conn.prepareStatement(qry); ResultSet rs = getcollsett.executeQuery()) {
                while (rs.next()) {
                    jTextField1.setText(rs.getString("ID"));
                    methods.collonline = rs.getBoolean("collectiononline");
                    methods.pigmyinerface = rs.getBoolean("pigmyinterfacrreq");
                    methods.drp = rs.getBoolean("drp");
                    methods.deductrebateinbill = rs.getBoolean("deductrebateinbill");
                    methods.rebateperltr = rs.getDouble("rebateperltr");
                    methods.CM_Sellrate = rs.getDouble("CMSellrate");
                    methods.BM_Sellrate = rs.getDouble("BMSellrate");
                    methods.fix_snf = rs.getBoolean("fixsnf");
                    methods.std_CMSNF = rs.getDouble("standard_CMSNF");
                    methods.std_BMSNF = rs.getDouble("standard_BMSNF");
                    methods.std_CMFAT = rs.getDouble("standard_CMFAT");
                    methods.std_BMFAT = rs.getDouble("standard_BMFAT");                
                    methods.fix_clr = rs.getBoolean("fixclr");
                    methods.std_CMCLR = rs.getDouble("standard_CMCLR");
                    methods.std_BMCLR = rs.getDouble("standard_BMCLR");                
                    methods.mor_collectionrateinc = rs.getDouble("mor_coll_rate_increment");
                    methods.eve_collectionrateinc = rs.getDouble("eve_coll_rate_increment");
                    methods.min_CMFAT = rs.getDouble("min_CMFAT");
                    methods.min_BMFAT = rs.getDouble("min_BMFAT");
                    methods.max_CMFAT = rs.getDouble("max_CMFAT");
                    methods.max_BMFAT = rs.getDouble("max_BMFAT");
                    methods.min_CMSNF = rs.getDouble("min_CMSNF");
                    methods.min_BMSNF = rs.getDouble("min_BMSNF");
                    methods.max_CMSNF = rs.getDouble("max_CMSNF");
                    methods.max_BMSNF = rs.getDouble("max_BMSNF");
                    methods.manualrateentry = rs.getBoolean("manualrateentry");
                    methods.bill_period = rs.getInt("billperiod"); 
                    methods.prodcodegroupwise = rs.getBoolean("prodcodegrwise");
                    methods.prodcodemanual = rs.getBoolean("prodcodemanual");
                    methods.smsfacilityreq = rs.getBoolean("smsfacilityreq");
                    methods.sms_senderid = rs.getString("sms_senderid");
                    methods.smssendby = rs.getString("smssendby");
                    methods.ratechartcalcbase_CM = rs.getString("ratechartcalcbase_CM");
                    methods.max_cmclr = rs.getDouble("max_cmclr");
                    methods.min_cmclr = rs.getDouble("min_cmclr");
                    methods.max_bmclr = rs.getDouble("max_bmclr");
                    methods.min_bmclr = rs.getDouble("min_bmclr");
                    methods.ratechartcalcbase_BM = rs.getString("ratechartcalcbase_BM");
                    methods.clrdiff = rs.getDouble("clrdiff");
                    methods.is_clr_not_mandatory = rs.getBoolean("is_clr_not_mandatory");
                    methods.deduct_rebate_from_bill = rs.getBoolean("deduct_rebate_from_bill");
                    methods.print_total_amount_in_reciept = rs.getBoolean("print_total_amount_in_reciept");
                    methods.reciept_lang = rs.getString("reciept_lang");
                    methods.reciept_clr = rs.getBoolean("reciept_clr");
                    methods.reciept_snf = rs.getBoolean("reciept_snf");
                }
            }
            
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(collectionsetting.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        
            dispose();
            
        } catch (SQLException ex) {
            Logger.getLogger(collectionsetting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void fixedsnfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixedsnfActionPerformed
        if(fixedsnf.isSelected()==true) {
            fixedclr.setSelected(false);
        } else {
            fixedclr.setSelected(true);   
        }
    }//GEN-LAST:event_fixedsnfActionPerformed

    private void fixedclrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixedclrActionPerformed
        if(fixedclr.isSelected()==true) {
            fixedsnf.setSelected(false);
        } else {
            fixedsnf.setSelected(true);   
        }
    }//GEN-LAST:event_fixedclrActionPerformed

    private void nofixedsnfclrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nofixedsnfclrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nofixedsnfclrActionPerformed

    private void deductrebateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deductrebateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deductrebateActionPerformed

    private void pigmyinterfaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pigmyinterfaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pigmyinterfaceActionPerformed

    private void chkprodcodegrwiseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkprodcodegrwiseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkprodcodegrwiseActionPerformed

    private void chkprodcodmanualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkprodcodmanualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkprodcodmanualActionPerformed

    private void chkprodcodmanual1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkprodcodmanual1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkprodcodmanual1ActionPerformed

    private void is_clr_not_mandatoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_is_clr_not_mandatoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_is_clr_not_mandatoryActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner billperiod;
    private javax.swing.JSpinner bmmaxCLR;
    private javax.swing.JSpinner bmmaxfat;
    private javax.swing.JSpinner bmmaxsnf;
    private javax.swing.JSpinner bmminCLR;
    private javax.swing.JSpinner bmminfat;
    private javax.swing.JSpinner bmminsnf;
    private javax.swing.JSpinner bmsclr;
    private javax.swing.JSpinner bmsellrate;
    private javax.swing.JSpinner bmsf;
    private javax.swing.JSpinner bmssnf;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbfirm;
    private javax.swing.JCheckBox chkprodcodegrwise;
    private javax.swing.JCheckBox chkprodcodmanual;
    private javax.swing.JCheckBox chkprodcodmanual1;
    private javax.swing.JSpinner clrdiff;
    private javax.swing.JComboBox cmb_CMRATECHARTBASE;
    private javax.swing.JSpinner cmmaxCLR;
    private javax.swing.JSpinner cmmaxfat;
    private javax.swing.JSpinner cmmaxsnf;
    private javax.swing.JSpinner cmminCLR;
    private javax.swing.JSpinner cmminfat;
    private javax.swing.JSpinner cmminsnf;
    private javax.swing.JSpinner cmsclr;
    private javax.swing.JSpinner cmsellrate;
    private javax.swing.JSpinner cmsf;
    private javax.swing.JSpinner cmssnf;
    private javax.swing.JCheckBox deduct_rebate_from_bill;
    private javax.swing.JCheckBox deductrebate;
    private javax.swing.JCheckBox drp;
    private javax.swing.JSpinner eveningcollrateinc;
    private javax.swing.JCheckBox fixedclr;
    private javax.swing.JCheckBox fixedsnf;
    private javax.swing.JCheckBox is_clr_not_mandatory;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JCheckBox manualrate;
    private javax.swing.JSpinner morningcollrateinc;
    private javax.swing.JCheckBox nofixedsnfclr;
    private javax.swing.JCheckBox onlinecollection;
    private javax.swing.JCheckBox pigmyinterface;
    private javax.swing.JCheckBox print_total_amount_in_reciept;
    private javax.swing.JSpinner rebateperltr;
    private javax.swing.JCheckBox reciept_clr;
    private javax.swing.JComboBox<String> reciept_lang;
    private javax.swing.JCheckBox reciept_snf;
    // End of variables declaration//GEN-END:variables
}
