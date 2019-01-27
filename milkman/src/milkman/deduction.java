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
public class deduction extends javax.swing.JInternalFrame {

    /**
     * Creates new form deduction
     */
    
    private Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
    DecimalFormat onedf = new DecimalFormat("#.#");
    DecimalFormat twodf = new DecimalFormat("#0.##");
    Connection conn;
    //private PreparedStatement INSERT_QRY;
    String qry = "";
    int framemode;
    private DefaultTableModel dtm;
    private int[] grid;
    
    public deduction() {
        try {
            initComponents();
            clearscreen();
            
            dtpfromdate.setDate(date);
            dtptodate.setDate(date);
            txtproducercode.requestFocus();

            if(conn!=null){
                conn.close();
            }
            conn = methods.getConnection();

            String colheader[] = {"ID", "उ. कोड", "नाव", "एकूण कपात"};
            
            dtm = new DefaultTableModel(colheader, 500);                    
            table_deduction.setModel(dtm);

            table_deduction.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
            {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
                {
                    final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                    return c;
                }
            }); 
            
            (table_deduction.getColumnModel().getColumn(0)).setPreferredWidth(40);
            (table_deduction.getColumnModel().getColumn(1)).setPreferredWidth(45);
            (table_deduction.getColumnModel().getColumn(2)).setPreferredWidth(250);
            (table_deduction.getColumnModel().getColumn(3)).setPreferredWidth(100);
            
            grid = new int[30];
            //cmbgroup.insertItemAt("सर्व", 0);
            //grid[0] = 0;
            qry = "SELECT producergroup.ID, producergroup.grname\n" +
                    "FROM producergroup\n" +
                    "ORDER BY producergroup.ID;";
            PreparedStatement loadqry;            
            loadqry = conn.prepareStatement(qry);
            ResultSet rscombo;
            int i = 0;
            rscombo = loadqry.executeQuery();
            while(rscombo.next()) {
                int gid = rscombo.getInt("ID");
                cmbgroup.insertItemAt(rscombo.getString("grname"), i);
                grid[i] = gid;
                i++;
            }
            cmbgroup.setSelectedIndex(0);
            rscombo.close();
            loadqry.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(deduction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getdeductionreport() {
        try {
            int r;
            int c;
            for(r=0; r<table_deduction.getRowCount(); r++) {
                for (c=0; c<table_deduction.getColumnCount(); c++) {
                    table_deduction.setValueAt("", r, c);
                }
            }         
            
//            String q = "SELECT deduction.ID, deduction.pro_code, producer.pro_name,"+
//                " [deduction]![pashukhadya]+[deduction]![advance]+[deduction]![otherded]+[deduction]![doctorfee] AS Expr1,"+
//                " deduction.dedfromdate, deduction.dedtodate" +
//                " FROM producer INNER JOIN deduction ON producer.ID=deduction.pro_code" +
//                " WHERE (((deduction.dedfromdate)>=#"+mdy.format(dtpfromdate.getDate())+"#) AND"+
//                " ((deduction.dedtodate)<=#"+mdy.format(dtptodate.getDate())+"#));";
            String q = "SELECT deduction.ID, deduction.pro_code, producer.pro_name," +
                    " [deduction]![pashukhadya]+[deduction]![advance]+[deduction]![otherded]+[deduction]"+
                    "![doctorfee] AS Expr1, deduction.dedfromdate, deduction.dedtodate " +
                    "FROM producer INNER JOIN deduction ON producer.prod_id = deduction.pro_code " +
                    "WHERE (((deduction.dedfromdate)>=#"+mdy.format(dtpfromdate.getDate())+"#)" +
                    "AND ((deduction.dedtodate)<=#"+mdy.format(dtptodate.getDate())+"#));";
            //System.out.println(q);
            try (PreparedStatement DED_REPORT = conn.prepareStatement(q, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ResultSet rs;
                rs = DED_REPORT.executeQuery();
                int i=0;
                int j=0;
                while(rs.next()) {
                    for (j=0; j<table_deduction.getColumnCount(); j++) {
                        table_deduction.setValueAt(rs.getString(j+1), i, j);
                    }
                    i++;                    
                }
                rs.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(deduction.class.getName()).log(Level.SEVERE, null, ex);
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
        dtptodate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        dtpfromdate = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtproducercode = new javax.swing.JFormattedTextField();
        txtproducername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txttotalmilk = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtavgrate = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtamt = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtanamat = new javax.swing.JFormattedTextField();
        txtrebate = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtpashukhadya = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtadvance = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtdocfee = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        txtother = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnsave = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        btnrefresh = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_deduction = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        txttotaldeduction = new javax.swing.JFormattedTextField();
        txtpayableamt = new javax.swing.JFormattedTextField();
        txtchilingcharges = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtvehicalcharges = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        txtproducercode1 = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        txtadvancebalance = new javax.swing.JFormattedTextField();
        cmbgroup = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createCompoundBorder());
        setClosable(true);
        setTitle("कपात");
        setToolTipText("");
        setNextFocusableComponent(txtproducercode);

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 24)); // NOI18N
        jLabel5.setText("उत्पादकाची कपात एन्ट्री");

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

        dtptodate.setFocusable(false);
        dtptodate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dtptodate.setNextFocusableComponent(txtproducercode);

        jLabel8.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel8.setText("ते");

        dtpfromdate.setFocusable(false);
        dtpfromdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dtpfromdate.setNextFocusableComponent(dtptodate);

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel6.setText("बिल कालावधी :");

        jLabel3.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel3.setText("उत्पादक कोड :");

        txtproducercode.setForeground(new java.awt.Color(51, 51, 255));
        txtproducercode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtproducercode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtproducercode.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtproducercode.setNextFocusableComponent(txtpashukhadya);
        txtproducercode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproducercodeActionPerformed(evt);
            }
        });
        txtproducercode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtproducercodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtproducercodeFocusLost(evt);
            }
        });
        txtproducercode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtproducercodeKeyReleased(evt);
            }
        });

        txtproducername.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtproducername.setForeground(new java.awt.Color(51, 51, 255));
        txtproducername.setFocusable(false);

        jLabel4.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel4.setText("एकूण दुध :");

        txttotalmilk.setForeground(new java.awt.Color(51, 51, 255));
        txttotalmilk.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txttotalmilk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalmilk.setFocusable(false);
        txttotalmilk.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel9.setText("ली.");

        txtavgrate.setForeground(new java.awt.Color(51, 51, 255));
        txtavgrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtavgrate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtavgrate.setFocusable(false);
        txtavgrate.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel7.setText("सरासरी दर :");

        jLabel10.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel10.setText("रक्कम :");

        txtamt.setForeground(new java.awt.Color(51, 51, 255));
        txtamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtamt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtamt.setFocusable(false);
        txtamt.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel11.setText("अनामत :");

        txtanamat.setForeground(new java.awt.Color(204, 0, 0));
        txtanamat.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtanamat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtanamat.setFocusable(false);
        txtanamat.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtrebate.setForeground(new java.awt.Color(204, 0, 0));
        txtrebate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtrebate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrebate.setFocusable(false);
        txtrebate.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel12.setText("रिबेट :");

        txtpashukhadya.setForeground(new java.awt.Color(51, 51, 255));
        txtpashukhadya.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtpashukhadya.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtpashukhadya.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtpashukhadya.setNextFocusableComponent(txtdocfee);
        txtpashukhadya.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtpashukhadyaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtpashukhadyaFocusLost(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel13.setText("पशु खाद्य :");

        txtadvance.setForeground(new java.awt.Color(51, 51, 255));
        txtadvance.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtadvance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtadvance.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtadvance.setNextFocusableComponent(txtother);
        txtadvance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtadvanceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtadvanceFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel14.setText("अडव्हांस (उचल) :");

        txtdocfee.setForeground(new java.awt.Color(51, 51, 255));
        txtdocfee.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtdocfee.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtdocfee.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtdocfee.setNextFocusableComponent(txtadvance);
        txtdocfee.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtdocfeeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtdocfeeFocusLost(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel15.setText("डॉक्टर फी :");

        txtother.setForeground(new java.awt.Color(51, 51, 255));
        txtother.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtother.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtother.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtother.setNextFocusableComponent(btnsave);
        txtother.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtotherFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtotherFocusLost(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel16.setText("इतर :");

        jLabel17.setBackground(new java.awt.Color(204, 255, 51));
        jLabel17.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("कपात तपशील");
        jLabel17.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(113, 168, 168));

        btnsave.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        btnsave.setText("सेव्ह");
        btnsave.setNextFocusableComponent(btnexit);
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });

        btnexit.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        btnexit.setText("बाहेर");
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });

        btnrefresh.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        btnrefresh.setText("रेफ्रेश");
        btnrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrefreshActionPerformed(evt);
            }
        });

        btndelete.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        btndelete.setText("डिलीट");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnexit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnrefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btndelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnsave, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        table_deduction.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        table_deduction.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_deduction.setRequestFocusEnabled(false);
        table_deduction.setRowHeight(24);
        table_deduction.setSelectionForeground(new java.awt.Color(153, 0, 0));
        jScrollPane1.setViewportView(table_deduction);

        jLabel18.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel18.setText("एकूण कपात :");

        txttotaldeduction.setForeground(new java.awt.Color(51, 51, 255));
        txttotaldeduction.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txttotaldeduction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotaldeduction.setFocusable(false);
        txttotaldeduction.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtpayableamt.setForeground(new java.awt.Color(51, 51, 255));
        txtpayableamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtpayableamt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtpayableamt.setFocusable(false);
        txtpayableamt.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtchilingcharges.setForeground(new java.awt.Color(204, 0, 0));
        txtchilingcharges.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtchilingcharges.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtchilingcharges.setFocusable(false);
        txtchilingcharges.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel19.setText("ची. चार्जेस :");

        jLabel20.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel20.setText("गाडी भाडे :");

        txtvehicalcharges.setForeground(new java.awt.Color(204, 0, 0));
        txtvehicalcharges.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtvehicalcharges.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtvehicalcharges.setFocusable(false);
        txtvehicalcharges.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel21.setText("दे. रक्कम :");

        txtproducercode1.setForeground(new java.awt.Color(51, 51, 255));
        txtproducercode1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtproducercode1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtproducercode1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtproducercode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproducercode1ActionPerformed(evt);
            }
        });
        txtproducercode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtproducercode1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtproducercode1FocusLost(evt);
            }
        });
        txtproducercode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtproducercode1KeyReleased(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel22.setText("बाकी :");

        txtadvancebalance.setForeground(new java.awt.Color(204, 0, 0));
        txtadvancebalance.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtadvancebalance.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtadvancebalance.setFocusable(false);
        txtadvancebalance.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        cmbgroup.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cmbgroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbgroupActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel2.setText("ग्रुप :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(txttotalmilk, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel7))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGap(121, 121, 121)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtchilingcharges, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtvehicalcharges, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtavgrate)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtproducercode, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtproducercode1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtproducername))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtamt, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dtpfromdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dtptodate, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtother, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtadvance, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txttotaldeduction, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtrebate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(13, 13, 13)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel22)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtadvancebalance, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel21)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtpayableamt, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtdocfee, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpashukhadya, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtanamat, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dtpfromdate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(dtptodate, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cmbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtproducercode)
                                    .addComponent(txtproducername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtproducercode1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txttotalmilk, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtavgrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtamt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtanamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtchilingcharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpashukhadya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtvehicalcharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtdocfee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtrebate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtadvancebalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtadvance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtother, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttotaldeduction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpayableamt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtproducercodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtproducercodeFocusGained
        txtproducercode.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtproducercodeFocusGained

    private void txtproducercodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtproducercodeFocusLost
        txtproducercode.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtproducercodeFocusLost
 
    private String getprodname(int id) throws SQLException {
        String pname = "";
        double totmilk;
        double totmilkrate;
        double avgrate;
        double anamat_per = 0;      // per liter
        double rebate_per = 0;      // per liter
        double vehcharg_per = 0;    // per liter
        double chilcharg_per = 0;   // per liter
        double anamat;      // per liter
        double rebate;      // per liter
        double vehcharg;    // per liter
        double chilcharg;   // per liter        
        double totalAdvance = 0;
        double totalDeduction = 0;
        
        String qry2 = "SELECT producer.pro_name, producer.rtc_no, producer.ID," +
                " producer.share_amt, producer.ribet, producer.chiling_amt, producer.veh_exp, producer.prod_id" +
                " FROM producer" +
                " WHERE (((producer.ID)="+id+") AND producergroupid = " + grid[cmbgroup.getSelectedIndex()] + ");";
        PreparedStatement prname;
        prname = conn.prepareStatement(qry2);
        ResultSet rspname;
        rspname = prname.executeQuery();
        while(rspname.next()) {
            pname = rspname.getString("pro_name");
            txtproducercode1.setText(rspname.getString("prod_id"));
            if(!pname.equals("")) {
//                System.out.println(pname);
                anamat_per = rspname.getDouble("share_amt");
                rebate_per = rspname.getDouble("ribet");
                chilcharg_per = rspname.getDouble("chiling_amt");
                vehcharg_per = rspname.getDouble("veh_exp");
            } else {
                pname = "";
                return pname;
            }
        }
//        System.out.println(anamat_per);
//        System.out.println(rebate_per);
//        System.out.println(chilcharg_per);
//        System.out.println(vehcharg_per);
        
        rspname.close();
        prname.close();
        
        // calculate total milk, avg rate and total amount
        qry2 = "SELECT Sum(mlkCollection.weight) AS SumOfweight,"+
               " Sum([mlkCollection]![weight]*[mlkCollection]![rate]) AS Expr1" +
               " FROM mlkCollection" +
               " WHERE (((mlkCollection.prod_code)="+id+") AND"+
               " ((mlkCollection.trn_date)>=#"+mdy.format(dtpfromdate.getDate())+"# And"+
               " (mlkCollection.trn_date)<=#"+mdy.format(dtptodate.getDate())+"#));";

        PreparedStatement getmilkdetail;
        getmilkdetail = conn.prepareStatement(qry2);
        rspname = getmilkdetail.executeQuery();
        while(rspname.next()) {

            totmilk = rspname.getDouble("SumOfweight");
            if(totmilk==0) {

                txttotalmilk.setText("0.00");
                txtavgrate.setText("0.00");
                txtamt.setText("0.00");

                txtanamat.setText("0.00");
                txtrebate.setText("0.00");
                txtvehicalcharges.setText("0.00");
                txtchilingcharges.setText("0.00");

                JOptionPane.showMessageDialog(null, "उत्पादकाचे दुध संकलन नाही..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
                
                clearscreen();
                txtproducercode.requestFocus();
                
                return pname;

            } else {
                
                    totmilkrate = rspname.getDouble("Expr1");
                    avgrate = totmilkrate / totmilk;
                    
                    anamat = totmilk * anamat_per;
                    rebate = totmilk * rebate_per;
                    chilcharg = totmilk * chilcharg_per;
                    vehcharg = totmilk * vehcharg_per;
                    
                    txttotalmilk.setText(""+twodf.format(totmilk));
                    txtavgrate.setText(""+twodf.format(avgrate));
                    txtamt.setText(""+twodf.format(totmilkrate));
                    
                    txtanamat.setText(""+twodf.format(anamat));
                    txtrebate.setText(""+twodf.format(rebate));
                    txtvehicalcharges.setText(""+twodf.format(vehcharg));
                    txtchilingcharges.setText(""+twodf.format(chilcharg));
                    
            }
            break;
        }
        rspname.close();
        getmilkdetail.close();
        
        // calculate advance
        qry2 = "SELECT Sum(addition.amount) AS SumOfamount, Max(addition.ded_amt) AS MaxOfded_amt" +
                " FROM producer INNER JOIN addition ON producer.prod_id = addition.pro_code" +     
                " WHERE (((addition.pro_code)="+txtproducercode1.getText()+") and add_type like 'Uchal' and deduction_type <> 'Auto');";

        //PreparedStatement getmilkdetail;
        getmilkdetail = conn.prepareStatement(qry2);
        rspname = getmilkdetail.executeQuery();
        while(rspname.next()) {
            totalAdvance = rspname.getDouble("SumOfamount");
            txtadvance.setText(""+twodf.format(rspname.getDouble("MaxOfded_amt")));
            break;
        }
        rspname.close();
        getmilkdetail.close();
        
        // calculate pashukhadya
        qry2 = "SELECT Sum(addition.amount) AS SumOfamount, Max(addition.ded_amt) AS MaxOfded_amt" +
                " FROM producer INNER JOIN addition ON producer.prod_id = addition.pro_code" +     
                " WHERE (((addition.pro_code)="+txtproducercode1.getText()+") and add_type like 'Pashukhadya Vikri' and deduction_type <> 'Auto');";

        //PreparedStatement getmilkdetail;
        getmilkdetail = conn.prepareStatement(qry2);
        rspname = getmilkdetail.executeQuery();
        while(rspname.next()) {
            txtpashukhadya.setText(""+twodf.format(rspname.getDouble("MaxOfded_amt")));
            //txtadvance.setText(""+twodf.format(rspname.getDouble("MaxOfded_amt")));
            break;
        }
        rspname.close();
        getmilkdetail.close();
        
        // calculate deduction
        qry2 = "SELECT Sum(deduction.advance) AS SumOfadvance FROM deduction" +
             " WHERE (((deduction.pro_code)="+txtproducercode1.getText()+"));";

        //PreparedStatement getmilkdetail;
        getmilkdetail = conn.prepareStatement(qry2);
        rspname = getmilkdetail.executeQuery();
        while(rspname.next()) {
            totalDeduction = rspname.getDouble("SumOfadvance");
            break;
        }
        rspname.close();
        getmilkdetail.close();
        
        double bal = 0;
        System.out.println(totalAdvance);
        System.out.println(totalDeduction);
        
        bal = totalAdvance - totalDeduction;
        System.out.println(bal);
        
        txtadvancebalance.setText(""+twodf.format(bal));
        
        getpaybleamt();
        
        return pname;
    }
    
    private void txtproducercodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproducercodeKeyReleased
        if(evt.getKeyCode()==10){
//            if(!txtproducercode.getText().equals("")) {
//                int pid = Integer.parseInt(txtproducercode.getText());
//                String pname;
//                try {
//                    pname = getprodname(pid);
//                    txtproducername.setText(pname);
//                    if(pname.equals("")) {
//                        JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!", "Milkman", JOptionPane.ERROR_MESSAGE);
//                        txtproducercode.requestFocus();
//                        txtproducercode.setText(null);
//                    } else {
//                        
//                    }
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!","Milkman", JOptionPane.ERROR_MESSAGE);
//                    txtproducercode.requestFocus();
//                    txtproducercode.setText(null);                    
//                    Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
//                    JOptionPane.showMessageDialog(null, ex,"Milkman", JOptionPane.ERROR_MESSAGE);
//                }
//            }
        }
    }//GEN-LAST:event_txtproducercodeKeyReleased

    private void clearscreen() {
        txtproducercode.setText(null);
        txtproducername.setText(null);
        txttotalmilk.setText(null);
        txtavgrate.setText(null);
        txtamt.setText(null);
        txtanamat.setText(null);
        txtanamat.setText("0.00");
        txtpashukhadya.setText("0.00");
        txtrebate.setText("0.00");
        txtadvance.setText("0.00");
        txtother.setText("0.00");
        txtdocfee.setText("0.00");
        txtpayableamt.setText("0.00");
        txttotaldeduction.setText("0.00");
        txtchilingcharges.setText("0.00");
        txtvehicalcharges.setText("0.00");
        txtadvancebalance.setText("0.00");
    }

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(deduction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dispose();
    }//GEN-LAST:event_btnexitActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        try {
            if(conn==null){
                conn = methods.getConnection();            
            }
            
            PreparedStatement INSERT_QRY;
            // Prepate statement for inserting record
            qry = "INSERT INTO deduction (dedfromdate, dedtodate, pro_code," +
                    " pashukhadya, advance, otherded, doctorfee)" + 
                    " VALUES (?,?,?,?,?,?,?)";
            INSERT_QRY = conn.prepareStatement(qry);            
            INSERT_QRY.setString(1, ""+sdf.format(dtpfromdate.getDate()));
            INSERT_QRY.setString(2, ""+sdf.format(dtptodate.getDate()));
            INSERT_QRY.setString(3, ""+txtproducercode1.getText());
            System.out.println(txtproducercode1.getText());
            INSERT_QRY.setString(4, ""+txtpashukhadya.getText());
            INSERT_QRY.setString(5, ""+txtadvance.getText());
            INSERT_QRY.setString(6, ""+txtother.getText());
            INSERT_QRY.setString(7, ""+txtdocfee.getText());
            
            INSERT_QRY.execute();
            
            System.out.println("am here");
            JOptionPane.showMessageDialog(null, "रेकॉर्ड सेव्ह झाला..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
            clearscreen();
            txtproducercode.requestFocus();
            
        } catch (SQLException ex) {
            Logger.getLogger(deduction.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }//GEN-LAST:event_btnsaveActionPerformed

    private void txtpashukhadyaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtpashukhadyaFocusLost
        getpaybleamt();
    }//GEN-LAST:event_txtpashukhadyaFocusLost

    private void txtdocfeeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtdocfeeFocusLost
        getpaybleamt();
    }//GEN-LAST:event_txtdocfeeFocusLost

    private void txtadvanceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtadvanceFocusLost
        getpaybleamt();
    }//GEN-LAST:event_txtadvanceFocusLost

    private void txtotherFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtotherFocusLost
        getpaybleamt();
    }//GEN-LAST:event_txtotherFocusLost

    private void txtpashukhadyaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtpashukhadyaFocusGained
        txtpashukhadya.selectAll();
    }//GEN-LAST:event_txtpashukhadyaFocusGained

    private void txtdocfeeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtdocfeeFocusGained
        txtdocfee.selectAll();
    }//GEN-LAST:event_txtdocfeeFocusGained

    private void txtadvanceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtadvanceFocusGained
        txtadvance.selectAll();
    }//GEN-LAST:event_txtadvanceFocusGained

    private void txtotherFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtotherFocusGained
        txtother.selectAll();
    }//GEN-LAST:event_txtotherFocusGained

    private void btnrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrefreshActionPerformed
        clearscreen();
        getdeductionreport();
        txtproducercode.requestFocus();
    }//GEN-LAST:event_btnrefreshActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        try {
            int ans = JOptionPane.showConfirmDialog(null, "रेकॉर्ड नक्की काढण्यात यावे?", "Milkman", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION && table_deduction.getSelectedRow() != -1) {
                int id = Integer.parseInt("0"+table_deduction.getValueAt(table_deduction.getSelectedRow(), 0));
                qry = "Delete * from deduction where ID = "+id+";";
                PreparedStatement delete_qry = conn.prepareStatement(qry);
                delete_qry.execute();
                JOptionPane.showMessageDialog(null, "रेकॉर्ड काढण्यात आला..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
                clearscreen();
                getdeductionreport();
                txtproducercode.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(deduction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    private void txtproducercode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproducercode1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducercode1ActionPerformed

    private void txtproducercode1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtproducercode1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducercode1FocusGained

    private void txtproducercode1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtproducercode1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducercode1FocusLost

    private void txtproducercode1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproducercode1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtproducercode1KeyReleased

    private void txtproducercodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproducercodeActionPerformed
            if(!txtproducercode.getText().equals("")) {
                int pid = Integer.parseInt(txtproducercode.getText());
                String pname;
                try {
                    pname = getprodname(pid);
                    txtproducername.setText(pname);
                    if(pname.equals("")) {
                        JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!", "Milkman", JOptionPane.ERROR_MESSAGE);
                        txtproducercode.requestFocus();
                        txtproducercode.setText(null);
                    } else {
                        
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!","Milkman", JOptionPane.ERROR_MESSAGE);
                    txtproducercode.requestFocus();
                    txtproducercode.setText(null);                    
                    Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex,"Milkman", JOptionPane.ERROR_MESSAGE);
                }
            }
    }//GEN-LAST:event_txtproducercodeActionPerformed

    private void cmbgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbgroupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbgroupActionPerformed

    private void getpaybleamt() {
        
        if(!txtanamat.getText().equals("") && !txtrebate.getText().equals("") && !txtchilingcharges.getText().equals("")
                && !txtvehicalcharges.getText().equals("") && !txtpashukhadya.getText().equals("")
                && !txtdocfee.getText().equals("") && !txtother.getText().equals("")
                && !txtadvance.getText().equals("")) {
            
        double p_amt;
        double tot_ded;
        
        tot_ded = Double.parseDouble(txtanamat.getText()) + Double.parseDouble(txtrebate.getText()) +
                Double.parseDouble(txtchilingcharges.getText()) + Double.parseDouble(txtvehicalcharges.getText()) +
                Double.parseDouble(txtpashukhadya.getText()) + Double.parseDouble(txtdocfee.getText()) +
                Double.parseDouble(txtother.getText()) + Double.parseDouble(txtadvance.getText());
        
        p_amt = Double.parseDouble(txtamt.getText()) - tot_ded;
        
        txttotaldeduction.setText(""+twodf.format(tot_ded));
        txtpayableamt.setText(""+twodf.format(p_amt));
        
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnrefresh;
    private javax.swing.JButton btnsave;
    private javax.swing.JComboBox cmbgroup;
    private com.toedter.calendar.JDateChooser dtpfromdate;
    private com.toedter.calendar.JDateChooser dtptodate;
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
    private javax.swing.JTable table_deduction;
    private javax.swing.JFormattedTextField txtadvance;
    private javax.swing.JFormattedTextField txtadvancebalance;
    private javax.swing.JFormattedTextField txtamt;
    private javax.swing.JFormattedTextField txtanamat;
    private javax.swing.JFormattedTextField txtavgrate;
    private javax.swing.JFormattedTextField txtchilingcharges;
    private javax.swing.JFormattedTextField txtdocfee;
    private javax.swing.JFormattedTextField txtother;
    private javax.swing.JFormattedTextField txtpashukhadya;
    private javax.swing.JFormattedTextField txtpayableamt;
    private javax.swing.JFormattedTextField txtproducercode;
    private javax.swing.JFormattedTextField txtproducercode1;
    private javax.swing.JTextField txtproducername;
    private javax.swing.JFormattedTextField txtrebate;
    private javax.swing.JFormattedTextField txttotaldeduction;
    private javax.swing.JFormattedTextField txttotalmilk;
    private javax.swing.JFormattedTextField txtvehicalcharges;
    // End of variables declaration//GEN-END:variables
}
