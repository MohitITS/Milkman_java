package milkman;
import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class milkdispatch extends javax.swing.JInternalFrame {

    private int shiftid;
    private Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
    SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat onedf = new DecimalFormat("#.#");
    DecimalFormat twodf = new DecimalFormat("#0.##");
    Connection conn;
    PreparedStatement INSERT_QRY;
    int prod_ratechartno;
    String qry = "";
    int framemode;
    PreparedStatement SHIFT_REPORT = null;
    private DefaultTableModel dtm;  
    int[] vid;
    int trnchalanno;
    private int fatnumber = 0;
    posting post;
    int chno;
    
    public milkdispatch() throws SQLException {
        this.post = new posting();
        initComponents();
        try {
            // Prepare Connection
            if(conn!=null){
                conn.close();
            }
            conn = methods.getConnection();

            // Prepate statement for inserting record
            qry = "INSERT INTO milkdispatch (disp_datetime, shift_id, chalanno, vid, scode," +
                    " mlkType_id, qty1, qty2, qty3, fat1, fat2, fat3, clr1, clr2, clr3, snf1, snf2, snf3, rate1, rate2, rate3, " +
                    " amt1, amt2, amt3, saurmilk, saurmilkamt, AddedByFK, trnchalanno, disp_datetime2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            INSERT_QRY = conn.prepareStatement(qry);

            this.shiftid = 1;
            this.framemode = 1;
            
            dtpdate.setDate(date);
            dtpdate1.setDate(date);
            
            String daate = sdf.format(date).toString();
            StringTokenizer st_time = new StringTokenizer(daate," ");
            String timeStr[] = new String[7];
            int i=0;
            while (st_time.hasMoreElements()) {   
                timeStr[i++] = st_time.nextToken();   
            }
            if(timeStr[1].equals("AM")) {
                optmorning.setSelected(true);
                shiftid = 1;
            } else {
                optevening.setSelected(true);
                shiftid = 2;
            }
            
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);

            clearscreen();
           
            txtsanghcode.requestFocus();

            gettotalcollection();
            
            int maxrows;
            //"दुध प्रकार", 
            String colheader[] = {"चलन नं", "संघ कोड", "वजन (ली)", "फॅट", "एस. एन. एफ.", "सी. एल. आर", "दर", "रक्कम"};
            
            maxrows = 50;//rsshiftreport.
            dtm = new DefaultTableModel(colheader, maxrows);
            table_shiftreport.setModel(dtm);

            table_shiftreport.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
            {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
                {
                    final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                    return c;
                }
            }); 
            
//            table_shiftreport.getColumnModel().getColumn(0).setWidth(20);
//            table_shiftreport.getColumnModel().getColumn(1).setWidth(30);
//            table_shiftreport.getColumnModel().getColumn(2).setWidth(50);
//            table_shiftreport.getColumnModel().getColumn(3).setWidth(20);
//            table_shiftreport.getColumnModel().getColumn(4).setWidth(20);
//            table_shiftreport.getColumnModel().getColumn(5).setWidth(20);
//            table_shiftreport.getColumnModel().getColumn(6).setWidth(20);
//            table_shiftreport.getColumnModel().getColumn(7).setWidth(20);
            
            ResultSet rscombo;
            String qry;        
            qry = "SELECT vehical.ID, vehical.vehicalname\n" +
                "FROM vehical;";
            PreparedStatement ratechartid = conn.prepareStatement(qry);
            rscombo = ratechartid.executeQuery();
            vid = new int[30];
            i = 0;
            while(rscombo.next()) {
               cmbvehical.insertItemAt(rscombo.getString("vehicalname"), i);
               vid[i] = rscombo.getInt("ID");
               i++;
            }
            cmbvehical.setSelectedIndex(0);
            rscombo.close();
            
            getshiftreport();
            
        } catch (SQLException ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        dtpdate = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        optmorning = new javax.swing.JRadioButton();
        optevening = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        txtsanghcode = new javax.swing.JFormattedTextField();
        txtsanghname = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtweight = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtfat = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtclr = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txtsnf = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtrate = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        txtamount = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtweight1 = new javax.swing.JFormattedTextField();
        txtfat1 = new javax.swing.JFormattedTextField();
        txtclr1 = new javax.swing.JFormattedTextField();
        txtsnf1 = new javax.swing.JFormattedTextField();
        txtrate1 = new javax.swing.JFormattedTextField();
        txtamount1 = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtsaurmilk = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cmbmilktype = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        btnsave = new javax.swing.JButton();
        btnsetting = new javax.swing.JButton();
        btnpreventry = new javax.swing.JButton();
        btnclearscreen = new javax.swing.JButton();
        btnreport = new javax.swing.JButton();
        btnprintreciept = new javax.swing.JButton();
        btnclose = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_shiftreport = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtsaurmilkamt = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        txtsampleno = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbvehical = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        txtweight2 = new javax.swing.JFormattedTextField();
        txtfat2 = new javax.swing.JFormattedTextField();
        txtclr2 = new javax.swing.JFormattedTextField();
        txtsnf2 = new javax.swing.JFormattedTextField();
        txtrate2 = new javax.swing.JFormattedTextField();
        txtamount2 = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        dtpdate1 = new com.toedter.calendar.JDateChooser();
        jLabel19 = new javax.swing.JLabel();
        optmorning1 = new javax.swing.JRadioButton();
        optevening1 = new javax.swing.JRadioButton();
        optevening2 = new javax.swing.JRadioButton();
        optevening3 = new javax.swing.JRadioButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("दुध वितरण");

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 24)); // NOI18N
        jLabel5.setText("दुध वितरण एन्ट्री");

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
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel1.setText("दिनांक पासून :");

        dtpdate.setFocusable(false);
        dtpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dtpdate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpdatePropertyChange(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel2.setText("शिफ्ट :");

        optmorning.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(optmorning);
        optmorning.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        optmorning.setForeground(new java.awt.Color(51, 51, 255));
        optmorning.setText("सकाळ");
        optmorning.setFocusable(false);
        optmorning.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optmorningStateChanged(evt);
            }
        });
        optmorning.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optmorningActionPerformed(evt);
            }
        });

        optevening.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(optevening);
        optevening.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        optevening.setForeground(new java.awt.Color(51, 51, 255));
        optevening.setText("संध्याकाळ");
        optevening.setFocusable(false);
        optevening.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                opteveningStateChanged(evt);
            }
        });
        optevening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opteveningActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel3.setText("संघ कोड :");

        txtsanghcode.setForeground(new java.awt.Color(51, 51, 255));
        txtsanghcode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtsanghcode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsanghcode.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsanghcode.setNextFocusableComponent(txtweight);
        txtsanghcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsanghcodeActionPerformed(evt);
            }
        });
        txtsanghcode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsanghcodeFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsanghcodeFocusGained(evt);
            }
        });
        txtsanghcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsanghcodeKeyReleased(evt);
            }
        });

        txtsanghname.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsanghname.setForeground(new java.awt.Color(51, 51, 255));
        txtsanghname.setFocusable(false);

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel7.setText("लिटर :");

        txtweight.setForeground(new java.awt.Color(51, 51, 255));
        txtweight.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtweight.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtweight.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtweight.setNextFocusableComponent(txtfat);
        txtweight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtweightActionPerformed(evt);
            }
        });
        txtweight.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtweightFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtweightFocusLost(evt);
            }
        });
        txtweight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtweightKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel8.setText("फॅट :");

        txtfat.setForeground(new java.awt.Color(51, 51, 255));
        txtfat.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtfat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfat.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtfat.setNextFocusableComponent(txtclr);
        txtfat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfatActionPerformed(evt);
            }
        });
        txtfat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfatFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfatFocusGained(evt);
            }
        });
        txtfat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfatKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel9.setText("सी. एल. आर. :");

        txtclr.setForeground(new java.awt.Color(51, 51, 255));
        txtclr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtclr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtclr.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtclr.setNextFocusableComponent(txtrate);
        txtclr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclrActionPerformed(evt);
            }
        });
        txtclr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtclrFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtclrFocusGained(evt);
            }
        });
        txtclr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtclrKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel10.setText("एस. एन. एफ. :");

        txtsnf.setForeground(new java.awt.Color(51, 51, 255));
        txtsnf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtsnf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsnf.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsnf.setNextFocusableComponent(txtrate);
        txtsnf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsnfFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsnfFocusLost(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel11.setText("दर :");

        txtrate.setForeground(new java.awt.Color(51, 51, 255));
        txtrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtrate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrate.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtrate.setNextFocusableComponent(txtamount);
        txtrate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtrateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtrateFocusLost(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel12.setText("रक्कम :");

        txtamount.setForeground(new java.awt.Color(51, 51, 255));
        txtamount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtamount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtamount.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtamount.setNextFocusableComponent(txtweight1);
        txtamount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtamountActionPerformed(evt);
            }
        });
        txtamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtamountKeyReleased(evt);
            }
        });

        txtweight1.setForeground(new java.awt.Color(51, 51, 255));
        txtweight1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtweight1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtweight1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtweight1.setNextFocusableComponent(txtfat1);
        txtweight1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtweight1ActionPerformed(evt);
            }
        });
        txtweight1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtweight1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtweight1FocusLost(evt);
            }
        });
        txtweight1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtweight1KeyReleased(evt);
            }
        });

        txtfat1.setForeground(new java.awt.Color(51, 51, 255));
        txtfat1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtfat1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfat1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtfat1.setNextFocusableComponent(txtclr1);
        txtfat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfat1ActionPerformed(evt);
            }
        });
        txtfat1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfat1FocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfat1FocusGained(evt);
            }
        });
        txtfat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfat1KeyReleased(evt);
            }
        });

        txtclr1.setForeground(new java.awt.Color(51, 51, 255));
        txtclr1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtclr1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtclr1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtclr1.setNextFocusableComponent(txtrate2);
        txtclr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclr1ActionPerformed(evt);
            }
        });
        txtclr1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtclr1FocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtclr1FocusGained(evt);
            }
        });
        txtclr1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtclr1KeyReleased(evt);
            }
        });

        txtsnf1.setForeground(new java.awt.Color(51, 51, 255));
        txtsnf1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtsnf1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsnf1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsnf1.setNextFocusableComponent(txtrate1);
        txtsnf1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsnf1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsnf1FocusLost(evt);
            }
        });

        txtrate1.setForeground(new java.awt.Color(51, 51, 255));
        txtrate1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtrate1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrate1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtrate1.setNextFocusableComponent(txtamount1);
        txtrate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrate1ActionPerformed(evt);
            }
        });
        txtrate1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtrate1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtrate1FocusLost(evt);
            }
        });
        txtrate1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrate1KeyReleased(evt);
            }
        });

        txtamount1.setForeground(new java.awt.Color(51, 51, 255));
        txtamount1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtamount1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtamount1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtamount1.setNextFocusableComponent(txtweight2);
        txtamount1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtamount1ActionPerformed(evt);
            }
        });
        txtamount1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtamount1KeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel13.setText("नाश दुध :");

        txtsaurmilk.setForeground(new java.awt.Color(51, 51, 255));
        txtsaurmilk.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtsaurmilk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsaurmilk.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsaurmilk.setNextFocusableComponent(txtsaurmilkamt);
        txtsaurmilk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsaurmilkFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsaurmilkFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 0, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("पाठवलेले दुध");

        jLabel15.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 0, 51));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("ग्रेड १ दुध (KG)");

        cmbmilktype.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        cmbmilktype.setForeground(new java.awt.Color(51, 51, 255));
        cmbmilktype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "गाय", "म्हैस" }));
        cmbmilktype.setEnabled(false);

        jPanel2.setBackground(new java.awt.Color(113, 168, 168));

        btnsave.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnsave.setText("सेव्ह");
        btnsave.setNextFocusableComponent(txtsanghcode);
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });
        btnsave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnsaveFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnsaveFocusLost(evt);
            }
        });

        btnsetting.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnsetting.setText("सेटिंग");
        btnsetting.setFocusable(false);

        btnpreventry.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnpreventry.setText("मागील एन्ट्री");
        btnpreventry.setFocusable(false);
        btnpreventry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpreventryActionPerformed(evt);
            }
        });

        btnclearscreen.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnclearscreen.setText("रिफ्रेश");
        btnclearscreen.setFocusable(false);
        btnclearscreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearscreenActionPerformed(evt);
            }
        });

        btnreport.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnreport.setText("डीसप्याच रिपोर्ट");
        btnreport.setFocusable(false);
        btnreport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnreportActionPerformed(evt);
            }
        });

        btnprintreciept.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnprintreciept.setText("पावती");
        btnprintreciept.setFocusable(false);
        btnprintreciept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprintrecieptActionPerformed(evt);
            }
        });

        btnclose.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnclose.setText("बंद");
        btnclose.setFocusable(false);
        btnclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnsetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnpreventry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnclearscreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnreport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnprintreciept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnclose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnsave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnsetting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnpreventry)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnclearscreen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnreport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnprintreciept)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnclose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        table_shiftreport.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        table_shiftreport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_shiftreport.setRowHeight(24);
        table_shiftreport.setSelectionForeground(new java.awt.Color(153, 0, 0));
        table_shiftreport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_shiftreportMouseClicked(evt);
            }
        });
        table_shiftreport.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                table_shiftreportKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table_shiftreport);

        jLabel16.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel16.setText("नाश दुध रक्कम :");

        txtsaurmilkamt.setForeground(new java.awt.Color(51, 51, 255));
        txtsaurmilkamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtsaurmilkamt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsaurmilkamt.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsaurmilkamt.setNextFocusableComponent(btnsave);
        txtsaurmilkamt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsaurmilkamtActionPerformed(evt);
            }
        });
        txtsaurmilkamt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsaurmilkamtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsaurmilkamtFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel4.setText("चलन नंबर :");

        txtsampleno.setEditable(false);
        txtsampleno.setForeground(new java.awt.Color(51, 51, 255));
        txtsampleno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtsampleno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsampleno.setFocusable(false);
        txtsampleno.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel6.setText("वाहन :");

        cmbvehical.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 0, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("ग्रेड २ दुध (KG)");

        txtweight2.setForeground(new java.awt.Color(51, 51, 255));
        txtweight2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtweight2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtweight2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtweight2.setNextFocusableComponent(txtfat2);
        txtweight2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtweight2ActionPerformed(evt);
            }
        });
        txtweight2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtweight2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtweight2FocusLost(evt);
            }
        });
        txtweight2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtweight2KeyReleased(evt);
            }
        });

        txtfat2.setForeground(new java.awt.Color(51, 51, 255));
        txtfat2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtfat2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfat2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtfat2.setNextFocusableComponent(txtclr2);
        txtfat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfat2ActionPerformed(evt);
            }
        });
        txtfat2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfat2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfat2FocusLost(evt);
            }
        });
        txtfat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfat2KeyReleased(evt);
            }
        });

        txtclr2.setForeground(new java.awt.Color(51, 51, 255));
        txtclr2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtclr2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtclr2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtclr2.setNextFocusableComponent(txtrate2);
        txtclr2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclr2ActionPerformed(evt);
            }
        });
        txtclr2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtclr2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtclr2FocusLost(evt);
            }
        });
        txtclr2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtclr2KeyReleased(evt);
            }
        });

        txtsnf2.setForeground(new java.awt.Color(51, 51, 255));
        txtsnf2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtsnf2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsnf2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsnf2.setNextFocusableComponent(txtrate1);
        txtsnf2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsnf2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsnf2FocusLost(evt);
            }
        });

        txtrate2.setForeground(new java.awt.Color(51, 51, 255));
        txtrate2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtrate2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrate2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtrate2.setNextFocusableComponent(txtamount2);
        txtrate2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrate2ActionPerformed(evt);
            }
        });
        txtrate2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtrate2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtrate2FocusLost(evt);
            }
        });
        txtrate2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtrate2KeyReleased(evt);
            }
        });

        txtamount2.setForeground(new java.awt.Color(51, 51, 255));
        txtamount2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtamount2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtamount2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtamount2.setNextFocusableComponent(btnsave);
        txtamount2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtamount2ActionPerformed(evt);
            }
        });
        txtamount2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtamount2KeyReleased(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("दिनांक पर्यंत :");

        dtpdate1.setFocusable(false);
        dtpdate1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dtpdate1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpdate1PropertyChange(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel19.setText("शिफ्ट :");

        optmorning1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(optmorning1);
        optmorning1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        optmorning1.setForeground(new java.awt.Color(51, 51, 255));
        optmorning1.setText("सकाळ");
        optmorning1.setFocusable(false);
        optmorning1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optmorning1StateChanged(evt);
            }
        });
        optmorning1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optmorning1ActionPerformed(evt);
            }
        });

        optevening1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(optevening1);
        optevening1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        optevening1.setForeground(new java.awt.Color(51, 51, 255));
        optevening1.setText("संध्याकाळ");
        optevening1.setFocusable(false);
        optevening1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optevening1StateChanged(evt);
            }
        });
        optevening1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optevening1ActionPerformed(evt);
            }
        });

        optevening2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(optevening2);
        optevening2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        optevening2.setForeground(new java.awt.Color(51, 51, 255));
        optevening2.setText("BOTH");
        optevening2.setFocusable(false);
        optevening2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optevening2StateChanged(evt);
            }
        });
        optevening2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optevening2ActionPerformed(evt);
            }
        });

        optevening3.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(optevening3);
        optevening3.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        optevening3.setForeground(new java.awt.Color(51, 51, 255));
        optevening3.setText("BOTH");
        optevening3.setFocusable(false);
        optevening3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optevening3StateChanged(evt);
            }
        });
        optevening3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optevening3ActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtsaurmilk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtamount, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtclr, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtsnf, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtrate, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtfat, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtweight, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtamount1)
                                            .addComponent(txtclr1)
                                            .addComponent(txtsnf1)
                                            .addComponent(txtrate1)
                                            .addComponent(txtfat1)
                                            .addComponent(txtweight1)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txtsaurmilkamt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtrate2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtsnf2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtclr2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtfat2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(txtweight2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtamount2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbmilktype, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dtpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(optmorning, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(optevening)
                                        .addGap(18, 18, 18)
                                        .addComponent(optevening2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtsanghcode, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtsanghname, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtsampleno, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbvehical, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dtpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(14, 14, 14)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(optmorning1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(optevening1)
                                        .addGap(18, 18, 18)
                                        .addComponent(optevening3)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(optmorning, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(optevening, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(optevening2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtpdate1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(optmorning1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(optevening1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19)
                                .addComponent(optevening3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtsanghname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtsanghcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtsampleno)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbvehical)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtweight)
                                    .addComponent(txtweight1)
                                    .addComponent(txtweight2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtfat)
                                    .addComponent(txtfat1)
                                    .addComponent(txtfat2)
                                    .addComponent(cmbmilktype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtclr)
                                    .addComponent(txtclr1)
                                    .addComponent(txtclr2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtsnf)
                                    .addComponent(txtsnf1)
                                    .addComponent(txtsnf2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtrate)
                                    .addComponent(txtrate1)
                                    .addComponent(txtrate2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtamount)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtamount1)
                                    .addComponent(txtamount2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtsaurmilk)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtsaurmilkamt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void clearscreen() {
        txtsanghcode.setText("");
        txtsanghname.setText("");
        txtweight.setText("0.0");
        txtfat.setText("0.0");
        txtclr.setText("0.0");
        txtsnf.setText("0.0");
        txtrate.setText("0.0");
        txtamount.setText("0.0");
        txtweight1.setText("0.0");
        txtfat1.setText("0.0");
        txtclr1.setText("0.0");
        txtsnf1.setText("0.0");
        txtrate1.setText("0.0");
        txtamount1.setText("0.0");
        txtweight2.setText("0.0");
        txtfat2.setText("0.0");
        txtclr2.setText("0.0");
        txtsnf2.setText("0.0");
        txtrate2.setText("0.0");
        txtamount2.setText("0.0");        
        txtsaurmilk.setText("0.0");
        txtsaurmilkamt.setText("0.0");
        
        fatnumber = 0;
    }
    
    // Method to get sample no 
    private int getsampleno() throws SQLException {
        int sno = 1;
        if (dtpdate.getDate()!=null) {
        if (conn!=null) {
            String qry1 = "SELECT Max(milkdispatch.chalanno) AS MaxOfchalanno " +
                    "FROM milkdispatch;";
//            System.out.println(conn);
            PreparedStatement sampleno = conn.prepareStatement(qry1);
            ResultSet rssampleno;
            rssampleno = sampleno.executeQuery();
            while(rssampleno.next()) {
                        //System.out.println(rssampleno.getInt("MaxOfsample_no"));
                int sn = rssampleno.getInt("MaxOfchalanno");
                //System.out.println(sn);
                if(sn == 0) {
                    sno = 1;
                } else {
                    sno = sn;
                    sno = sno + 1;
                }
            }
            rssampleno.close();
        }
        }
        return sno;
    }
    
    // Method to get Producer no
    private String getprodname(int id) throws SQLException {
        String pname = "";
        if (conn!=null) {
            String qry2 = "SELECT sangh.sname, sangh.rtc_no, sangh.ID" +
                    " FROM sangh" +
                    " WHERE (((sangh.ID)="+id+"));";
            PreparedStatement prname = conn.prepareStatement(qry2);
            ResultSet rspname;
            rspname = prname.executeQuery();
            while(rspname.next()) {
                pname = rspname.getString("sname");
                prod_ratechartno = rspname.getInt("rtc_no");
                if(!pname.equals("")) {
                    // do nothing return pname
                } else {
                    pname = "";
                }
            }
            rspname.close();
        }
        return pname;
    }
    
    private boolean getrate() throws SQLException {
        boolean ret = false;
        double rate;
//        rate = 0.0;
        double calcsnf;
        double calclr = 0;
        double calfat = 0;
        calcsnf = 0.0;
        String qry1;
        int milkid = cmbmilktype.getSelectedIndex();
        milkid = milkid + 1;
        
//        if(!txtfat.getText().equals("") || txtfat.getText().trim()!=null) {
//            calfat = Double.parseDouble(txtfat.getText());
//            if(!txtclr.getText().equals("")) { calclr = Double.parseDouble(txtclr.getText());  }
//            if(txtfat.getText().equals(""+0)) {
//                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
//                txtsnf.setText(""+onedf.format(calcsnf));
//            } else if(methods.fix_clr==false && methods.fix_snf==false) {
//                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
//                txtsnf.setText(""+onedf.format(calcsnf));
//            } else if(methods.fix_clr==true && methods.fix_snf==false) {
//                if(cmbmilktype.getSelectedIndex()==0){
//                    txtclr.setText(""+methods.std_CMCLR);
//                } else {
//                    txtclr.setText(""+methods.std_BMCLR);
//                }
//                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
//                txtsnf.setText(""+onedf.format(calcsnf));
//            } else if(methods.fix_clr==false && methods.fix_snf==true) {
//                if(cmbmilktype.getSelectedIndex()==0){
//                    txtsnf.setText(""+methods.std_CMSNF);
//                } else {
//                    txtsnf.setText(""+methods.std_BMSNF);
//                }            
//                calcsnf = Double.parseDouble(txtsnf.getText());
//                calclr = (((calcsnf - 0.36) - (0.21 * calfat)) / 0.25);
//                txtclr.setText(""+onedf.format(calclr));
//            }
//        }

        if (!methods.is_clr_not_mandatory) {
            calfat = Double.parseDouble(txtfat.getText());
            
            if (!txtclr.getText().equals("")) {
                calclr = Double.parseDouble(txtclr.getText());
            }
            if (txtfat.getText().equals("" + 0)) {
                calcsnf = 0;
                txtsnf.setText("" + onedf.format(calcsnf));
            } else if (methods.fix_clr == false && methods.fix_snf == false) {
                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
                txtsnf.setText("" + onedf.format(calcsnf));
            } else if (methods.fix_clr == true && methods.fix_snf == false) {
                if (cmbmilktype.getSelectedIndex() == 0) {
                    txtclr.setText("" + methods.std_CMCLR);
                } else {
                    txtclr.setText("" + methods.std_BMCLR);
                }
                calclr = Double.parseDouble(txtclr.getText());
                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
                txtsnf.setText("" + onedf.format(calcsnf));
            } else if (methods.fix_clr == false && methods.fix_snf == true) {
                if (cmbmilktype.getSelectedIndex() == 0) {
                    txtsnf.setText("" + methods.std_CMSNF);
                } else {
                    txtsnf.setText("" + methods.std_BMSNF);
                }
                calcsnf = Double.parseDouble(txtsnf.getText());
                calclr = (((calcsnf - 0.36) - (0.21 * calfat)) / 0.25);
                txtclr.setText("" + onedf.format(calclr));
            }

        } else {

            calfat = Double.parseDouble(txtfat.getText());
            calcsnf = Double.parseDouble(txtsnf.getText());
            calclr = (calcsnf - ((0.21 * calfat) + 0.36)) * 4;

            txtclr.setText("" + onedf.format(calclr));

        }
        
        if(!"".equals(txtfat.getText().trim()) && !"".equals(txtclr.getText().trim())) {
            System.out.println(methods.ratechartcalcbase_CM + " " + methods.ratechartcalcbase_BM);
            if ("FAT-SNF".equals(methods.ratechartcalcbase_BM) && "FAT-SNF".equals(methods.ratechartcalcbase_CM)) {
                qry1 = "SELECT rateChart.rate" +
                    " FROM rateChart" +
                    " WHERE (((rateChart.milktype_id)="+milkid+") AND" +
                    " ((rateChart.rtc_no)="+ prod_ratechartno +") AND" +
                    " ((rateChart.fat)="+onedf.format(calfat)+") AND ((rateChart.snf)="+onedf.format(calcsnf)+"));";
            } else {
                qry1 = "SELECT rateChart.rate" +
                    " FROM rateChart" +
                    " WHERE (((rateChart.milktype_id)="+milkid+") AND" +
                    " ((rateChart.rtc_no)="+ prod_ratechartno +") AND" +
                    " ((rateChart.fat)="+onedf.format(calfat)+") AND ((rateChart.clr)="+onedf.format(calclr)+"));";
            }
            System.out.println(qry1);
            PreparedStatement stmt_rate = conn.prepareStatement(qry1);
            ResultSet rsrate;
            rsrate = stmt_rate.executeQuery();
            if(rsrate.next()) {
                rate = rsrate.getDouble("rate");
                System.out.println(rate);
                txtrate.setText(""+ twodf.format(rate));
                double amt;
                double wt = Double.parseDouble(txtweight.getText());
                amt = (rate * wt);
                txtamount.setText(""+twodf.format(amt));                
                ret = true;
            } else {
                JOptionPane.showMessageDialog(null, "कृपया योग्य माहिती भरा...!!!", "Milkman", JOptionPane.ERROR_MESSAGE);
                ret = false;
            }
            rsrate.close();
        } 
        
        return ret;
    }
    
    private boolean getrate1() throws SQLException {
        boolean ret = false;
        double rate;
//        rate = 0.0;
        double calcsnf;
        double calclr = 0;
        double calfat = 0;
        calcsnf = 0.0;
        String qry1;
        int milkid = cmbmilktype.getSelectedIndex();
        milkid = milkid + 1;
        
//        if(!txtfat1.getText().equals("") || txtfat1.getText().trim()!=null) {
//            calfat = Double.parseDouble(txtfat1.getText());
//            if(!txtclr1.getText().equals("")) { calclr = Double.parseDouble(txtclr1.getText());  }
//            if(txtfat1.getText().equals(""+0)) {
//                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
//                txtsnf1.setText(""+onedf.format(calcsnf));
//            } else if(methods.fix_clr==false && methods.fix_snf==false) {
//                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
//                txtsnf1.setText(""+onedf.format(calcsnf));
//            } else if(methods.fix_clr==true && methods.fix_snf==false) {
//                if(cmbmilktype.getSelectedIndex()==0){
//                    txtclr1.setText(""+methods.std_CMCLR);
//                } else {
//                    txtclr1.setText(""+methods.std_BMCLR);
//                }
//                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
//                txtsnf1.setText(""+onedf.format(calcsnf));
//            } else if(methods.fix_clr==false && methods.fix_snf==true) {
//                if(cmbmilktype.getSelectedIndex()==0){
//                    txtsnf1.setText(""+methods.std_CMSNF);
//                } else {
//                    txtsnf1.setText(""+methods.std_BMSNF);
//                }            
//                calcsnf = Double.parseDouble(txtsnf.getText());
//                calclr = (((calcsnf - 0.36) - (0.21 * calfat)) / 0.25);
//                txtclr1.setText(""+onedf.format(calclr));
//            }
//        }
        

        if (!methods.is_clr_not_mandatory) {
            calfat = Double.parseDouble(txtfat1.getText());
            
            if (!txtclr1.getText().equals("")) {
                calclr = Double.parseDouble(txtclr1.getText());
            }
            if (txtfat1.getText().equals("" + 0)) {
                calcsnf = 0;
                txtsnf1.setText("" + onedf.format(calcsnf));
            } else if (methods.fix_clr == false && methods.fix_snf == false) {
                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
                txtsnf1.setText("" + onedf.format(calcsnf));
            } else if (methods.fix_clr == true && methods.fix_snf == false) {
                if (cmbmilktype.getSelectedIndex() == 0) {
                    txtclr1.setText("" + methods.std_CMCLR);
                } else {
                    txtclr1.setText("" + methods.std_BMCLR);
                }
                calclr = Double.parseDouble(txtclr1.getText());
                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
                txtsnf1.setText("" + onedf.format(calcsnf));
            } else if (methods.fix_clr == false && methods.fix_snf == true) {
                if (cmbmilktype.getSelectedIndex() == 0) {
                    txtsnf1.setText("" + methods.std_CMSNF);
                } else {
                    txtsnf1.setText("" + methods.std_BMSNF);
                }
                calcsnf = Double.parseDouble(txtsnf1.getText());
                calclr = (((calcsnf - 0.36) - (0.21 * calfat)) / 0.25);
                txtclr1.setText("" + onedf.format(calclr));
            }
            System.out.println(calclr);

        } else {

            calfat = Double.parseDouble(txtfat1.getText());
            calcsnf = Double.parseDouble(txtsnf1.getText());
            calclr = (calcsnf - ((0.21 * calfat) + 0.36)) * 4;

            txtclr1.setText("" + onedf.format(calclr));

        }

        if (calfat>0 && calcsnf>5) {
            if(!"".equals(txtfat1.getText().trim()) && !"".equals(txtclr1.getText().trim())) {

                if ("FAT-SNF".equals(methods.ratechartcalcbase_BM) && "FAT-SNF".equals(methods.ratechartcalcbase_CM)) {
                    qry1 = "SELECT rateChart.rate" +
                        " FROM rateChart" +
                        " WHERE (((rateChart.milktype_id)="+milkid+") AND" +
                        " ((rateChart.rtc_no)="+ prod_ratechartno +") AND" +
                        " ((rateChart.fat)="+onedf.format(calfat)+") AND ((rateChart.snf)="+onedf.format(calcsnf)+"));";
                } else {
                    qry1 = "SELECT rateChart.rate" +
                        " FROM rateChart" +
                        " WHERE (((rateChart.milktype_id)="+milkid+") AND" +
                        " ((rateChart.rtc_no)="+ prod_ratechartno +") AND" +
                        " ((rateChart.fat)="+onedf.format(calfat)+") AND ((rateChart.clr)="+onedf.format(calclr)+"));";
                }
                System.out.println(qry1);
                PreparedStatement stmt_rate = conn.prepareStatement(qry1);
                ResultSet rsrate;
                rsrate = stmt_rate.executeQuery();
                if(rsrate.next()) {
                    rate = rsrate.getDouble("rate");
                    txtrate1.setText(""+ twodf.format(rate));
                    double amt;
                    double wt = Double.parseDouble(txtweight1.getText());
                    amt = (rate * wt);
                    txtamount1.setText(""+twodf.format(amt));                
                    ret = true;
                } else {
                    JOptionPane.showMessageDialog(null, "कृपया योग्य माहिती भरा...!!!", "Milkman", JOptionPane.ERROR_MESSAGE);
                    ret = false;
                }
                rsrate.close();
            } 
        }
        return ret;
    }
    
private boolean getrate2() throws SQLException {
        boolean ret = false;
        double rate;
//        rate = 0.0;
        double calcsnf;
        double calclr = 0;
        double calfat = 0;
        calcsnf = 0.0;
        String qry1;
        int milkid = cmbmilktype.getSelectedIndex();
        milkid = milkid + 1;
        
        if(!txtfat2.getText().equals("") || txtfat2.getText().trim()!=null) {
            calfat = Double.parseDouble(txtfat2.getText());
            if(!txtclr2.getText().equals("")) { calclr = Double.parseDouble(txtclr2.getText());  }
            if(txtfat2.getText().equals(""+0)) {
                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
                txtsnf2.setText(""+onedf.format(calcsnf));
            } else if(methods.fix_clr==false && methods.fix_snf==false) {
                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
                txtsnf2.setText(""+onedf.format(calcsnf));
            } else if(methods.fix_clr==true && methods.fix_snf==false) {
                if(cmbmilktype.getSelectedIndex()==0){
                    txtclr2.setText(""+methods.std_CMCLR);
                } else {
                    txtclr2.setText(""+methods.std_BMCLR);
                }
                calcsnf = 0.21 * calfat + 0.25 * calclr + 0.36;
                txtsnf2.setText(""+onedf.format(calcsnf));
            } else if(methods.fix_clr==false && methods.fix_snf==true) {
                if(cmbmilktype.getSelectedIndex()==0){
                    txtsnf2.setText(""+methods.std_CMSNF);
                } else {
                    txtsnf2.setText(""+methods.std_BMSNF);
                }            
                calcsnf = Double.parseDouble(txtsnf.getText());
                calclr = (((calcsnf - 0.36) - (0.21 * calfat)) / 0.25);
                txtclr2.setText(""+onedf.format(calclr));
            }
        }
        
        if (calfat>0 && calcsnf>5) {
        if(!"".equals(txtfat2.getText().trim()) && !"".equals(txtclr2.getText().trim())) {
        
            qry1 = "SELECT rateChart.rate" +
                " FROM rateChart" +
                " WHERE (((rateChart.milktype_id)="+milkid+") AND" +
                " ((rateChart.rtc_no)="+ prod_ratechartno +") AND" +
                " ((rateChart.fat)="+onedf.format(calfat)+") AND ((rateChart.snf)="+onedf.format(calcsnf)+"));";
            PreparedStatement stmt_rate = conn.prepareStatement(qry1);
            ResultSet rsrate;
            rsrate = stmt_rate.executeQuery();
            if(rsrate.next()) {
                rate = rsrate.getDouble("rate");
                txtrate2.setText(""+ twodf.format(rate));
                double amt;
                double wt = Double.parseDouble(txtweight2.getText());
                amt = (rate * wt);
                txtamount2.setText(""+twodf.format(amt));                
                ret = true;
            } else {
                JOptionPane.showMessageDialog(null, "कृपया योग्य माहिती भरा...!!!", "Milkman", JOptionPane.ERROR_MESSAGE);
                ret = false;
            }
            rsrate.close();
        } 
        }
        return ret;
    }    
    
    private void getshiftreport() {
        shiftid = optevening1.isShowing() ? 1 : 2;
        try {
            
            String condition = "";
            
            shiftid = optmorning1.isSelected() ? 1 : 2;
            System.out.println(shiftid);
            if (optmorning.isSelected() && optmorning1.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=1)) OR "
                        + "(((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=1))";
            } else if (optmorning.isSelected() && optevening1.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=1)) OR "
                        + "(((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=2))";
            } else if (optmorning.isSelected() && optevening3.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=1)) "
                        + "OR (((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate1.getDate())+"#))";
            } 
            
            if (optevening.isSelected() && optmorning1.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=2)) OR "
                        + "(((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=1))";
            } else if (optevening.isSelected() && optevening1.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=2)) OR "
                        + "(((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=2))";
            } else if (optevening.isSelected() && optevening3.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=2)) "
                        + "OR (((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#))";
            }
            
            if (optevening2.isSelected() && optmorning1.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#)) "
                        + "OR (((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=1))";
            } else if (optevening2.isSelected() && optevening1.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#)) "
                        + "OR (((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((milkdispatch.shift_id)=2))";
            } else if (optevening2.isSelected() && optevening3.isSelected()) {
                condition = "(((milkdispatch.disp_datetime)=#"+mdy.format(dtpdate.getDate())+"#)) "
                        + "OR (((milkdispatch.disp_datetime2)=#"+mdy.format(dtpdate1.getDate())+"#))";
            }
            
            System.out.println("Shift : " + condition);
            
            int r;
            int c;
            for(r=0; r<table_shiftreport.getRowCount(); r++) {
                for (c=0; c<table_shiftreport.getColumnCount(); c++) {
                    table_shiftreport.setValueAt("", r, c);
                }
            }            
            qry = "SELECT milkdispatch.chalanno, milkdispatch.scode, milkdispatch.qty2,"+
                    "milkdispatch.fat2, milkdispatch.snf2, milkdispatch.clr2,"+
                    "milkdispatch.rate2, milkdispatch.amt2 " +
                    "FROM milkdispatch " +
                    "WHERE ("+ condition +") " +
                    "ORDER BY milkdispatch.chalanno;";
            SHIFT_REPORT = conn.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            try (ResultSet rsshiftreport = SHIFT_REPORT.executeQuery()) {
                if(rsshiftreport.next()) {
                    rsshiftreport.beforeFirst();
                    int i = 0;
                    int j;
                    while(rsshiftreport.next()) { // i loop
                        for (j=1; j<=table_shiftreport.getColumnCount(); j++) {
                            table_shiftreport.setValueAt(rsshiftreport.getString(j), i, j-1);
                        }
                        i++;
                    }
                } 
            }
            SHIFT_REPORT.close();
            gettotalcollection();
        } catch (SQLException ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    private void dtpdatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpdatePropertyChange
        try {
            //methods.currdate = dtpdate.getDate();
            clearscreen();
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dtpdatePropertyChange

    private void optmorningStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optmorningStateChanged
//        if(optmorning.isSelected()==true){
//            shiftid = 1;
//        } else {
//            shiftid = 2;
//        }
//        clearscreen();
//        try {
//            chno = post.getchalanno();
//            txtsampleno.setText(""+chno);
//            gettotalcollection();
//            getshiftreport();
//        } catch (Exception ex) {
//            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_optmorningStateChanged

    private void opteveningStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_opteveningStateChanged
//        if(optevening.isSelected()==true){
//            shiftid = 2;
//        } else {
//            shiftid = 1;
//        }
//        clearscreen();
//        try {
//            chno = post.getchalanno();
//            txtsampleno.setText(""+chno);
//            gettotalcollection();
//            getshiftreport();
//        } catch (Exception ex) {
//            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_opteveningStateChanged

    // method to get total collection and average fat snf
    private void gettotalcollection() {
        try {
            String coll_qry;
            double tot_collectionmilk = 0;
            double tot_dispatchmilk = 0;
            double bal_milk = 0;
            double avg_fat = 0;
            double avg_clr = 0;
            double avg_snf = 0;
            double avg_rate = 0;
            double tot_amt = 0;
            ResultSet rscollection;
            PreparedStatement totcoll;
            String condition = "";
            
            shiftid = optmorning1.isSelected() ? 1 : 2;
            //System.out.println(shiftid);
            if (optmorning.isSelected() && optmorning1.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=1)) OR "
                        + "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=1))";
            } else if (optmorning.isSelected() && optevening1.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=1)) OR "
                        + "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=2))";
            } else if (optmorning.isSelected() && optevening3.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=1)) "
                        + "OR (((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#))";
            } 
            
            if (optevening.isSelected() && optmorning1.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=2)) OR "
                        + "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=1))";
            } else if (optevening.isSelected() && optevening1.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=2)) OR "
                        + "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=2))";
            } else if (optevening.isSelected() && optevening3.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=2)) "
                        + "OR (((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#))";
            }
            
            if (optevening2.isSelected() && optmorning1.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#)) "
                        + "OR (((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=1))";
            } else if (optevening2.isSelected() && optevening1.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#)) "
                        + "OR (((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#) "
                        + "AND ((mlkCollection.shift_id)=2))";
            } else if (optevening2.isSelected() && optevening3.isSelected()) {
                condition = "(((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#)) "
                        + "OR (((mlkCollection.trn_date)=#"+mdy.format(dtpdate1.getDate())+"#))";
            }
            
            //System.err.println(condition);
            
            if ((dtpdate.getDate() != null || !dtpdate.getDate().equals("")) 
                    && (dtpdate1.getDate() != null || !dtpdate1.getDate().equals(""))) {
                
                //System.out.println(dtpdate.getDate());
                
                coll_qry = "SELECT Sum(milkdispatch.qty1) AS SumOfqty1 " +
                    "FROM milkdispatch " +
                    "WHERE (((milkdispatch.disp_datetime)=#"+ mdy.format(dtpdate1.getDate()) +"#) "+
                    "AND ((milkdispatch.shift_id)="+shiftid+"));";
                totcoll = conn.prepareStatement(coll_qry);
                rscollection = totcoll.executeQuery();
                if(rscollection.next()) {
                    tot_dispatchmilk = rscollection.getDouble("SumOfqty1");
                } else {
                    tot_dispatchmilk = 0;
                }            
                //System.out.println(tot_dispatchmilk);
                rscollection.close();
                totcoll.close();

//                coll_qry = "SELECT Sum(mlkCollection.weight) AS SumOfweight,"+
//                        " Sum([mlkCollection]![weight]*[mlkCollection]![fat]) AS Expr1,"+
//                        " Sum([mlkCollection]![weight]*[mlkCollection]![degree]) AS Expr2,"+
//                        " Sum([mlkCollection]![weight]*[mlkCollection]![snf]) AS Expr3,"+
//                        " Sum([mlkCollection]![weight]*[mlkCollection]![rate]) AS Expr4" +
//                        " FROM mlkCollection" +
//                        " WHERE (((mlkCollection.trn_date)>=#"+mdy.format(dtpdate.getDate())+"#)"+
//                        " AND ((mlkCollection.shift_id)="+shiftid+"));";
                coll_qry = "SELECT Sum(mlkCollection.weight) AS SumOfweight,"+
                        " Sum([mlkCollection]![weight]*[mlkCollection]![fat]) AS Expr1,"+
                        " Sum([mlkCollection]![weight]*[mlkCollection]![degree]) AS Expr2,"+
                        " Sum([mlkCollection]![weight]*[mlkCollection]![snf]) AS Expr3,"+
                        " Sum([mlkCollection]![weight]*[mlkCollection]![rate]) AS Expr4" +
                        " FROM mlkCollection" +
                        " WHERE "+condition+";";
                //System.out.println(coll_qry);
                totcoll = conn.prepareStatement(coll_qry);
                rscollection = totcoll.executeQuery();
                if(rscollection.next()) {
                    tot_collectionmilk = rscollection.getDouble("SumOfweight");
                    if (tot_collectionmilk != 0) {
                        avg_fat = rscollection.getDouble("Expr1") / tot_collectionmilk;
                        avg_clr = rscollection.getDouble("Expr2") / tot_collectionmilk;
                        avg_clr = Math.round(avg_clr * 2) / 2.0;
                        avg_snf = rscollection.getDouble("Expr3") / tot_collectionmilk;
                        tot_amt = rscollection.getDouble("Expr4");
                        avg_rate = tot_amt / tot_collectionmilk;
                        bal_milk = tot_collectionmilk - tot_dispatchmilk;
                        tot_amt = bal_milk * avg_rate;
                    }
                } else {
                    tot_collectionmilk = 0;
                    avg_fat = 0;
                    avg_clr = 0;
                    avg_snf = 0;
                    tot_amt = 0;
                    avg_rate = 0;
                }
                rscollection.close();
                totcoll.close();
                
            }
            
            txtweight.setText(""+twodf.format(bal_milk));
            txtfat.setText(""+onedf.format(avg_fat));
            txtclr.setText(""+onedf.format(avg_clr));
            txtsnf.setText(""+onedf.format(avg_snf));
            txtrate.setText(""+twodf.format(avg_rate));
            txtamount.setText(""+twodf.format(tot_amt));            
            
        } catch (SQLException ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Date addDays(Date date, int days) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            cal.add(Calendar.DATE, days);

            return cal.getTime();
    }
    
    private void generatereciept() {
        // code to be done
    }    
    
    private void txtsanghcodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsanghcodeFocusGained
        txtsanghcode.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtsanghcodeFocusGained

    private void txtsanghcodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsanghcodeFocusLost
        txtsanghcode.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtsanghcodeFocusLost

    private void txtsanghcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsanghcodeKeyReleased
        if(evt.getKeyCode()==10){
//            if(!txtsanghcode.getText().equals("")) {
//                int pid = Integer.parseInt(txtsanghcode.getText());
//                String pname;
//                try {
//                    pname = getprodname(pid);
//                    txtsanghname.setText(pname);
//                    if(pname.equals("")) {
//                        JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!","Milkman", JOptionPane.ERROR_MESSAGE);
//                        txtsampleno.setText(""+getsampleno());
//                        txtsanghcode.requestFocus();
//                        txtsanghcode.setText(null);
//                    } else {
//                        txtweight.requestFocus();
//                    }
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!","Milkman", JOptionPane.ERROR_MESSAGE);
//                    Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
//                    JOptionPane.showMessageDialog(null, ex,"Milkman", JOptionPane.ERROR_MESSAGE);
//                }
//            }
        }
    }//GEN-LAST:event_txtsanghcodeKeyReleased

    private void txtweightFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweightFocusGained
        txtweight.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtweightFocusGained

    private void txtweightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweightFocusLost
        txtweight.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtweightFocusLost

    private void txtweightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtweightKeyReleased
        if(evt.getKeyCode()==10){
//            KeyboardFocusManager manager;
//            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//            manager.focusNextComponent();
        }
    }//GEN-LAST:event_txtweightKeyReleased

    private void txtfatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfatFocusGained
        txtfat.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtfatFocusGained

    private void txtfatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfatFocusLost
        txtfat.setBackground(Color.WHITE);
        if(!txtfat.getText().equals("")) {
            try {
                if(!getrate()) { }
            } catch (SQLException ex) {
                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtfatFocusLost

    private void txtfatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfatKeyReleased
        if(evt.getKeyCode()==10){
//            if(!txtfat.getText().equals("") || txtfat.getText().trim()==null){
//                double ft = Double.parseDouble(txtfat.getText());
//                if(ft >= methods.min_CMFAT && ft <= methods.max_CMFAT) {
//                    cmbmilktype.setSelectedIndex(0);
//                } else if(ft >= methods.min_BMFAT && ft <= methods.max_BMFAT) {
//                    cmbmilktype.setSelectedIndex(1);
//                }
//            }            
//            KeyboardFocusManager manager;
//            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//            manager.focusNextComponent();
        } else if(evt.getKeyCode() >=48 && evt.getKeyCode() <= 57 && evt.getKeyCode() != 46) {
            if(fatnumber < 1) {
                fatnumber++;
                String fatno = txtfat.getText();
                fatno = fatno + ".";
                txtfat.setText(fatno);
            }
        } else if(evt.getKeyCode() == 46) {
            JOptionPane.showMessageDialog(null, "कृपया फक्त नंबर टाका...!!!", title, JOptionPane.INFORMATION_MESSAGE);
            txtfat.setText("0.0");
            txtfat.requestFocus();
            txtfat.selectAll();
        } else if (evt.getKeyCode() == 8) {
            txtfat.setText("");
            fatnumber = 0;
        }
    }//GEN-LAST:event_txtfatKeyReleased

    private void txtclrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclrFocusGained
        txtclr.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtclrFocusGained

    private void txtclrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclrFocusLost
        txtclr.setBackground(Color.WHITE);
        if(!txtclr.getText().equals("")){
            try {
                if(!getrate()) {
                    clearscreen();
                    chno = post.getchalanno();
                    txtsampleno.setText(""+chno);
                    txtsanghcode.requestFocus();
                }
            } catch (SQLException ex) {
                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtclrFocusLost

    private void txtclrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclrKeyReleased
        if(evt.getKeyCode()==10){
            KeyboardFocusManager manager;
            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            manager.focusNextComponent();
        }
    }//GEN-LAST:event_txtclrKeyReleased

    private void txtsnfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnfFocusGained
        txtsnf.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtsnfFocusGained

    private void txtsnfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnfFocusLost
        txtsnf.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtsnfFocusLost

    private void txtrateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrateFocusGained
        txtrate.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtrateFocusGained

    private void txtrateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrateFocusLost
        txtrate.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtrateFocusLost

    private void txtweight1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweight1FocusGained
        txtweight1.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtweight1FocusGained

    private void txtweight1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweight1FocusLost
        txtweight1.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtweight1FocusLost

    private void txtweight1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtweight1KeyReleased
        if(evt.getKeyCode()==10){
            KeyboardFocusManager manager;
            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            manager.focusNextComponent();
        }
    }//GEN-LAST:event_txtweight1KeyReleased

    private void txtfat1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfat1FocusGained
        txtfat1.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtfat1FocusGained

    private void txtfat1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfat1FocusLost
        txtfat1.setBackground(Color.WHITE);
//        if(!txtfat1.getText().equals("")) {
//            try {
//                if(!getrate1()) { }
//            } catch (SQLException ex) {
//                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }        
    }//GEN-LAST:event_txtfat1FocusLost

    private void txtfat1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfat1KeyReleased
        if(evt.getKeyCode()==10){
//            if(!txtfat1.getText().equals("") || txtfat1.getText().trim()==null){
//                double ft = Double.parseDouble(txtfat1.getText());
//                if(ft >= methods.min_CMFAT && ft <= methods.max_CMFAT) {
//                    cmbmilktype.setSelectedIndex(0);
//                } else if(ft >= methods.min_BMFAT && ft <= methods.max_BMFAT) {
//                    cmbmilktype.setSelectedIndex(1);
//                }
//            } 
//            KeyboardFocusManager manager;
//            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//            manager.focusNextComponent();
        } else if(evt.getKeyCode() >=48 && evt.getKeyCode() <= 57 && evt.getKeyCode() != 46) {
            if(fatnumber < 1) {
                fatnumber++;
                String fatno = txtfat1.getText();
                fatno = fatno + ".";
                txtfat1.setText(fatno);
            }
        } else if(evt.getKeyCode() == 46) {
            JOptionPane.showMessageDialog(null, "कृपया फक्त नंबर टाका...!!!", title, JOptionPane.INFORMATION_MESSAGE);
            txtfat1.setText("0.0");
            txtfat1.requestFocus();
            txtfat1.selectAll();
        } else if (evt.getKeyCode() == 8) {
            txtfat1.setText("");
            fatnumber = 0;
        }
    }//GEN-LAST:event_txtfat1KeyReleased

    private void txtclr1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclr1FocusGained
        txtclr1.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtclr1FocusGained

    private void txtclr1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclr1FocusLost
        txtclr1.setBackground(Color.WHITE);
        if(!txtclr1.getText().equals("")){
            try {
                if(!getrate1()) {
                    clearscreen();
                    chno = post.getchalanno();
                    txtsampleno.setText(""+chno);
                    txtsanghcode.requestFocus();
                }
            } catch (SQLException ex) {
                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }//GEN-LAST:event_txtclr1FocusLost

    private void txtclr1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclr1KeyReleased
        if(evt.getKeyCode()==10){
//            KeyboardFocusManager manager;
//            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//            manager.focusNextComponent();
        }
    }//GEN-LAST:event_txtclr1KeyReleased

    private void txtsnf1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnf1FocusGained
        txtsnf1.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtsnf1FocusGained

    private void txtsnf1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnf1FocusLost
        txtsnf1.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtsnf1FocusLost

    private void txtrate1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrate1FocusGained
        txtrate1.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtrate1FocusGained

    private void txtrate1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrate1FocusLost
        txtrate1.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtrate1FocusLost

    private void txtsaurmilkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsaurmilkFocusGained
        txtsaurmilk.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtsaurmilkFocusGained

    private void txtsaurmilkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsaurmilkFocusLost
        txtsaurmilk.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtsaurmilkFocusLost

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        //System.out.println(this.framemode);
        if(this.framemode == 1) {
                     
            if(!txtsanghcode.getText().equals("") && !txtfat1.getText().equals("") && !txtclr1.getText().equals("") && !txtweight1.getText().equals("") && !txtrate1.getText().equals("")) {
                try {
                    int milkid = cmbmilktype.getSelectedIndex();
                    int v_id = vid[cmbvehical.getSelectedIndex()];
                    milkid++;
                    INSERT_QRY.setString(1, ""+sdf2.format(dtpdate.getDate()));
                    INSERT_QRY.setInt(2, shiftid);
                    INSERT_QRY.setInt(3, Integer.parseInt(txtsampleno.getText()));
                    INSERT_QRY.setInt(4, v_id);
                    INSERT_QRY.setInt(5, Integer.parseInt(txtsanghcode.getText()));
                    INSERT_QRY.setInt(6, milkid);
                    INSERT_QRY.setDouble(7, Double.parseDouble(txtweight.getText()));
                    INSERT_QRY.setDouble(8, Double.parseDouble(txtweight1.getText()));
                    INSERT_QRY.setDouble(9, Double.parseDouble(txtweight2.getText()));
                    INSERT_QRY.setDouble(10, Double.parseDouble(txtfat.getText()));
                    INSERT_QRY.setDouble(11, Double.parseDouble(txtfat1.getText()));
                    INSERT_QRY.setDouble(12, Double.parseDouble(txtfat2.getText()));
                    INSERT_QRY.setDouble(13, Double.parseDouble(txtclr.getText()));
                    INSERT_QRY.setDouble(14, Double.parseDouble(txtclr1.getText()));
                    INSERT_QRY.setDouble(15, Double.parseDouble(txtclr2.getText()));
                    INSERT_QRY.setDouble(16, Double.parseDouble(txtsnf.getText()));
                    INSERT_QRY.setDouble(17, Double.parseDouble(txtsnf1.getText()));
                    INSERT_QRY.setDouble(18, Double.parseDouble(txtsnf2.getText()));
                    INSERT_QRY.setDouble(19, Double.parseDouble(txtrate.getText()));
                    INSERT_QRY.setDouble(20, Double.parseDouble(txtrate1.getText()));
                    INSERT_QRY.setDouble(21, Double.parseDouble(txtrate2.getText()));
                    INSERT_QRY.setDouble(22, Double.parseDouble(txtamount.getText()));
                    INSERT_QRY.setDouble(23, Double.parseDouble(txtamount1.getText()));
                    INSERT_QRY.setDouble(24, Double.parseDouble(txtamount2.getText()));
                    INSERT_QRY.setDouble(25, Double.parseDouble(txtsaurmilk.getText()));
                    INSERT_QRY.setDouble(26, Double.parseDouble(txtsaurmilkamt.getText()));
                    INSERT_QRY.setInt(27, methods.userid);
                    INSERT_QRY.setInt(28, chno);
                    INSERT_QRY.setString(29, ""+sdf2.format(dtpdate1.getDate()));

                    INSERT_QRY.execute();
                    
                    
                    //****** Insert transaction entry
                   
                    String insert_qry = "INSERT INTO dailytrn (trndate,voucherid,chalanno,accid,prodid,dramt,cramt,naration,shiftid)"+
                       " VALUES (?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement insert_stmt = conn.prepareStatement(insert_qry)) {
                        for (int i=0;i<2;i++) {

                            if (i==0) {
                                insert_stmt.setString(1, ""+dmy.format(dtpdate.getDate()));
                                insert_stmt.setInt(2, 1);
                                insert_stmt.setInt(3, chno);
//                                System.out.println(methods.sanghdefaultlgrid);
                                insert_stmt.setInt(4, methods.sanghdefaultlgrid);
                                int sanghid = Integer.parseInt(txtsanghcode.getText());
                                insert_stmt.setInt(5, sanghid);
                                insert_stmt.setDouble(6, 0.0);
                                double amt = Double.parseDouble(txtamount1.getText()) + Double.parseDouble(txtamount2.getText());
                                //totalamt = totalamt + amt;
                                insert_stmt.setDouble(7, amt);
                                insert_stmt.setString(8, "दुध वितरण");
                                insert_stmt.setInt(9, shiftid);
                                
                                insert_stmt.execute();
                            } else {
                                insert_stmt.setString(1, ""+dmy.format(dtpdate.getDate()));
                                insert_stmt.setInt(2, 1);
                                insert_stmt.setInt(3, chno);
                                insert_stmt.setInt(4, methods.milkselldefaultlgrid);
                                insert_stmt.setString(5, null);
                                double amt = Double.parseDouble(txtamount1.getText()) + Double.parseDouble(txtamount2.getText());
                                insert_stmt.setDouble(6, amt);
                                insert_stmt.setDouble(7, 0.0);
                                insert_stmt.setString(8, "दुध वितरण");
                                insert_stmt.setInt(9, shiftid);

                                insert_stmt.execute();       
                            }
                        } 
                        JOptionPane.showMessageDialog(null, "चलन नं "+ chno + " तयार करण्यात आलेले आहे. कृपया पोस्टिंग करा..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
                    //******
                    

                } catch (SQLException ex) {
                    Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "कृपया माहिती पूर्ण भरा..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if(this.framemode == 2) {
            if(!txtsanghcode.getText().equals("") && !txtfat.getText().equals("") && !txtclr.getText().equals("") && !txtweight.getText().equals("") && !txtrate.getText().equals("")) {
                try {
                    // Prepate statement for inserting record
                    qry = "UPDATE milkdispatch SET vid=?," +
                    " scode=?, mlktype_id=?, qty2=?, fat2=?, clr2=?, snf2=?, rate2=?, amt2 = ?," +
                    " saurmilk=?, saurmilkamt =? , LastUserFK=?, DateModified=?, qty3=?, fat3=?," + 
                    " clr3=?, snf3=?, rate3=?, amt3=?" + 
                    " Where disp_datetime=#"+mdy.format(dtpdate.getDate())+"# and" +
                    " shift_id="+shiftid+" and chalanno = "+Integer.parseInt(txtsampleno.getText())+";";
                    PreparedStatement update_qry = conn.prepareStatement(qry);
                    int milkid = cmbmilktype.getSelectedIndex();
                    milkid++;
                    int v_id = vid[cmbvehical.getSelectedIndex()];
                    update_qry.setInt(1, v_id);
                    update_qry.setInt(2, Integer.parseInt(txtsanghcode.getText()));
                    update_qry.setInt(3, milkid);
                    update_qry.setDouble(4, Double.parseDouble(txtweight1.getText()));
                    update_qry.setDouble(5, Double.parseDouble(txtfat1.getText()));
                    update_qry.setDouble(6, Double.parseDouble(txtclr1.getText()));
                    update_qry.setDouble(7, Double.parseDouble(txtsnf1.getText()));
                    update_qry.setDouble(8, Double.parseDouble(txtrate1.getText()));
                    update_qry.setDouble(9, Double.parseDouble(txtamount1.getText()));
                    update_qry.setDouble(10, Double.parseDouble(txtsaurmilk.getText()));
                    update_qry.setDouble(11, Double.parseDouble(txtsaurmilkamt.getText()));
                    update_qry.setInt(12, methods.userid);
                    update_qry.setString(13, ""+sdf2.format(date));
                    update_qry.setDouble(14, Double.parseDouble(txtweight2.getText()));
                    update_qry.setDouble(15, Double.parseDouble(txtfat2.getText()));
                    update_qry.setDouble(16, Double.parseDouble(txtclr2.getText()));
                    update_qry.setDouble(17, Double.parseDouble(txtsnf2.getText()));
                    update_qry.setDouble(18, Double.parseDouble(txtrate2.getText()));
                    update_qry.setDouble(19, Double.parseDouble(txtamount2.getText()));

                    update_qry.execute();
                    //System.out.println("Updated Successfully");
                } catch (SQLException ex) {
                    Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "कृपया माहिती पूर्ण भरा..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        //if(methods.drp==true) generatereciept();

        clearscreen();

        try {
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtsanghcode.requestFocus();

        //        gettotalcollection();
        //        getshiftreport();

        this.framemode = 1;
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btnsaveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnsaveFocusGained
        btnsave.setBackground(Color.CYAN);
    }//GEN-LAST:event_btnsaveFocusGained

    private void btnsaveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnsaveFocusLost
        btnsave.setBackground(new Color(240,240,240));
    }//GEN-LAST:event_btnsaveFocusLost

    private void btnpreventryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpreventryActionPerformed
        if(!txtsampleno.getText().trim().equals(""+1)) {
            int sampno = Integer.parseInt(txtsampleno.getText());
            sampno--;
            displayforediting(sampno);
        }
    }//GEN-LAST:event_btnpreventryActionPerformed

    private void btnclearscreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearscreenActionPerformed
        clearscreen();
        try {
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();
            txtsanghcode.requestFocus();
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.framemode = 1;
    }//GEN-LAST:event_btnclearscreenActionPerformed

    private void btnreportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnreportActionPerformed
//        try {
//            displayreportdatecriteria dsr = new displayreportdatecriteria("Dispatch Register");
//            main m = new main();
//            m.desktopPane.add(dsr);
//            dsr.show();
//        } catch (Exception ex) {
//            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_btnreportActionPerformed

    private void btnprintrecieptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintrecieptActionPerformed
        generatereciept();
    }//GEN-LAST:event_btnprintrecieptActionPerformed

    private void btncloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseActionPerformed
//        System.out.println("Exit is clicked");
        this.dispose();
        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btncloseActionPerformed

    private void table_shiftreportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_shiftreportMouseClicked
        //System.out.println(table_shiftreport.getSelectedRow());
        if (table_shiftreport.getSelectedRow()!=-1) {
            int row_sampleno;
            //System.out.println(table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0));
            //if(table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0)!=null
                //   || !table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0).equals("")) {
                row_sampleno = Integer.parseInt("0"+table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0));
                //            System.out.println("Row Sample No : "+row_sampleno);
                if (row_sampleno != 0) {
                    //displayforediting(row_sampleno);
                } else {
                    clearscreen();
                    try {
                        chno = post.getchalanno();
                        txtsampleno.setText(""+chno);
                    } catch (Exception ex) {
                        Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
    }//GEN-LAST:event_table_shiftreportMouseClicked

    private void displayforediting(int samp_no) {
        try {
            // set the framemode to 2 
            this.framemode = 2;
            String qry_edit;
            qry_edit = "SELECT milkdispatch.*, milkdispatch.chalanno\n" +
                        "FROM milkdispatch\n" +
                        "WHERE (((milkdispatch.chalanno)<="+samp_no+")) ORDER BY milkdispatch.chalanno DESC;";
            try (PreparedStatement editqry = conn.prepareStatement(qry_edit); ResultSet rsedit = editqry.executeQuery()) {
                if(rsedit.next()){
                    dtpdate.setDate(rsedit.getDate("disp_datetime"));
                    int s_id = rsedit.getInt("shift_id");
                    if (s_id == 1) {
                        optmorning.setSelected(true);
                    } else {
                        optevening.setSelected(true);
                    }
                    txtsanghcode.setText(rsedit.getString("scode"));
                    txtsanghname.setText(getprodname(Integer.parseInt(txtsanghcode.getText())));
                    //getprodname(Integer.parseInt(txtsanghcode.getText()));
                    txtsampleno.setText(rsedit.getString("chalanno"));
                    int v_id = rsedit.getInt("vid");
                    int i;
                    for (i=0; i<cmbvehical.getItemCount(); i++) {
                        if(vid[i] == v_id) {
                            cmbvehical.setSelectedIndex(i);
                            break;
                        }
                    }
                    txtweight.setText(rsedit.getString("qty1"));
                    txtweight1.setText(rsedit.getString("qty2"));
                    txtweight2.setText(rsedit.getString("qty3"));
                    if(rsedit.getInt("mlkType_id")==1) {
                        cmbmilktype.setSelectedIndex(0);
                    } else {
                        cmbmilktype.setSelectedIndex(1);
                    }
                    txtfat.setText(rsedit.getString("fat1"));
                    txtfat1.setText(rsedit.getString("fat2"));
                    txtfat2.setText(rsedit.getString("fat3"));
                    txtclr.setText(rsedit.getString("clr1"));
                    txtclr1.setText(rsedit.getString("clr2"));
                    txtclr2.setText(rsedit.getString("clr3"));
                    txtsnf.setText(rsedit.getString("snf1"));
                    txtsnf1.setText(rsedit.getString("snf2"));
                    txtsnf2.setText(rsedit.getString("snf3"));
                    txtrate.setText(rsedit.getString("rate1"));
                    txtrate1.setText(rsedit.getString("rate2"));
                    txtrate2.setText(rsedit.getString("rate3"));
                    txtamount.setText(rsedit.getString("amt1"));
                    txtamount1.setText(rsedit.getString("amt2"));
                    txtamount2.setText(rsedit.getString("amt3"));
                    txtsaurmilk.setText(rsedit.getString("saurmilk"));
                    txtsaurmilkamt.setText(rsedit.getString("saurmilkamt"));
                    
                } else {
                    JOptionPane.showMessageDialog(null, "संकलनाची एन्ट्री नाही..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void table_shiftreportKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_shiftreportKeyReleased
        //System.out.println(evt.getKeyCode());

        if (table_shiftreport.getSelectedRow()!=-1) {
            int row_sampleno;
            //System.out.println(table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0));
            if(table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0)!=null
                || !table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0).equals("")) {
                row_sampleno = Integer.parseInt("0"+table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0));

                if( evt.getKeyCode() == 127 ) {
                    try {
                        int ans = JOptionPane.showConfirmDialog(null, "रेकॉर्ड नक्की काढण्यात यावे?", "Milkman", JOptionPane.YES_NO_OPTION);
                        if (ans == JOptionPane.YES_OPTION) {
                            String del_qry = "DELETE * FROM milkdispatch WHERE disp_datetime = #"+ mdy.format(dtpdate.getDate()) +"# and "+
                            " shift_id = "+ shiftid +" and chalanno = "+ row_sampleno +";";
                            PreparedStatement delete_qry =  conn.prepareStatement(del_qry);
                            delete_qry.execute();

                            getshiftreport();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                clearscreen();
            }
        }
    }//GEN-LAST:event_table_shiftreportKeyReleased

    private void txtsaurmilkamtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsaurmilkamtFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsaurmilkamtFocusGained

    private void txtsaurmilkamtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsaurmilkamtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsaurmilkamtFocusLost

    private void txtsaurmilkamtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsaurmilkamtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsaurmilkamtActionPerformed

    private void optmorningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optmorningActionPerformed
        try {
            //methods.currdate = dtpdate.getDate();
            shiftid = 1;
            clearscreen();
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();        
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_optmorningActionPerformed

    private void opteveningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opteveningActionPerformed
        try {
            //methods.currdate = dtpdate.getDate();
            shiftid = 2;
            clearscreen();
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();        
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_opteveningActionPerformed

    private void txtrate1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrate1KeyReleased
        if(!"".equals(txtweight1.getText()) && !"".equals(txtrate1.getText())) {
            double wt = Double.parseDouble(txtweight1.getText());
            double rt = Double.parseDouble(txtrate1.getText());
            double amt = wt * rt;
            txtamount1.setText(""+twodf.format(amt));
        }
    }//GEN-LAST:event_txtrate1KeyReleased

    private void txtamountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtamountKeyReleased
        if(evt.getKeyCode()==10){
//            if(("".equals(txtweight.getText()) && txtweight.getText() == null) && (txtamount.getText() == null && "".equals(txtamount.getText()))) {
//            } else {
//
//                double totamt = Double.parseDouble(""+txtamount.getText());
//                double totweight = Double.parseDouble(""+txtweight.getText());
//                double avgrate = totamt / totweight;
//
//                txtrate.setText(""+avgrate);
//            }
//            KeyboardFocusManager manager;
//            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//            manager.focusNextComponent();            
        }
    }//GEN-LAST:event_txtamountKeyReleased

    private void txtamount1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtamount1KeyReleased
        if(evt.getKeyCode()==10){
//            if(("".equals(txtweight1.getText()) && txtweight1.getText() == null) && (txtamount1.getText() == null && "".equals(txtamount1.getText()))) {
//            } else {
//
//                double totamt = Double.parseDouble(""+txtamount1.getText());
//                double totweight = Double.parseDouble(""+txtweight1.getText());
//                double avgrate = totamt / totweight;
//
//                txtrate1.setText(""+avgrate);
//            }
//            KeyboardFocusManager manager;
//            manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//            manager.focusNextComponent();            
        }
    }//GEN-LAST:event_txtamount1KeyReleased

    private void txtsanghcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsanghcodeActionPerformed
        if(!txtsanghcode.getText().equals("")) {
            int pid = Integer.parseInt(txtsanghcode.getText());
            String pname;
            try {
                pname = getprodname(pid);
                txtsanghname.setText(pname);
                if(pname.equals("")) {
                    JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!","Milkman", JOptionPane.ERROR_MESSAGE);
                    chno = post.getchalanno();
                    txtsampleno.setText(""+chno);
                    txtsanghcode.requestFocus();
                    txtsanghcode.setText(null);
                } else {
                    txtweight.requestFocus();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!","Milkman", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex,"Milkman", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtsanghcodeActionPerformed

    private void txtweight2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweight2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtweight2FocusGained

    private void txtweight2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweight2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtweight2FocusLost

    private void txtweight2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtweight2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtweight2KeyReleased

    private void txtfat2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfat2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfat2FocusGained

    private void txtfat2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfat2FocusLost
        txtfat2.setBackground(Color.WHITE);
        if(!txtfat2.getText().equals("")) {
            try {
                if(!getrate2()) { }
            } catch (SQLException ex) {
                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtfat2FocusLost

    private void txtfat2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfat2KeyReleased
        if(evt.getKeyCode() >=48 && evt.getKeyCode() <= 57 && evt.getKeyCode() != 46) {
            if(fatnumber < 1) {
                fatnumber++;
                String fatno = txtfat2.getText();
                fatno = fatno + ".";
                txtfat2.setText(fatno);
            }
        } else if(evt.getKeyCode() == 46) {
            JOptionPane.showMessageDialog(null, "कृपया फक्त नंबर टाका...!!!", title, JOptionPane.INFORMATION_MESSAGE);
            txtfat2.setText("0.0");
            txtfat2.requestFocus();
            txtfat2.selectAll();
        } else if (evt.getKeyCode() == 8) {
            txtfat2.setText("");
            fatnumber = 0;
        }
    }//GEN-LAST:event_txtfat2KeyReleased

    private void txtclr2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclr2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclr2FocusGained

    private void txtclr2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclr2FocusLost
        txtclr2.setBackground(Color.WHITE);
        if(!txtclr2.getText().equals("")){
            try {
                if(!getrate2()) {
//                    clearscreen();
//                    chno = post.getchalanno();
//                    txtsampleno.setText(""+chno);
//                    txtsanghcode.requestFocus();
                }
            } catch (SQLException ex) {
                Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }//GEN-LAST:event_txtclr2FocusLost

    private void txtclr2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclr2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclr2KeyReleased

    private void txtsnf2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnf2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsnf2FocusGained

    private void txtsnf2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnf2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsnf2FocusLost

    private void txtrate2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrate2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrate2FocusGained

    private void txtrate2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrate2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrate2FocusLost

    private void txtrate2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtrate2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrate2KeyReleased

    private void txtamount2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtamount2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtamount2KeyReleased

    private void txtweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtweightActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtweightActionPerformed

    private void txtfatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfatActionPerformed
        if(!txtfat.getText().equals("") || txtfat.getText().trim()==null){
            double ft = Double.parseDouble(txtfat.getText());
            if(ft >= methods.min_CMFAT && ft <= methods.max_CMFAT) {
                cmbmilktype.setSelectedIndex(0);
            } else if(ft >= methods.min_BMFAT && ft <= methods.max_BMFAT) {
                cmbmilktype.setSelectedIndex(1);
            }
        }            
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtfatActionPerformed

    private void txtclrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclrActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtclrActionPerformed

    private void txtamountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtamountActionPerformed
        if(("".equals(txtweight.getText()) && txtweight.getText() == null) && (txtamount.getText() == null && "".equals(txtamount.getText()))) {
        } else {

            double totamt = Double.parseDouble(""+txtamount.getText());
            double totweight = Double.parseDouble(""+txtweight.getText());
            double avgrate = totamt / totweight;

            txtrate.setText(""+avgrate);
        }
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();            
    }//GEN-LAST:event_txtamountActionPerformed

    private void txtweight1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtweight1ActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtweight1ActionPerformed

    private void txtfat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfat1ActionPerformed
        if(!txtfat1.getText().equals("") || txtfat1.getText().trim()==null){
            double ft = Double.parseDouble(txtfat1.getText());
            if(ft >= methods.min_CMFAT && ft <= methods.max_CMFAT) {
                cmbmilktype.setSelectedIndex(0);
            } else if(ft >= methods.min_BMFAT && ft <= methods.max_BMFAT) {
                cmbmilktype.setSelectedIndex(1);
            }
            if(!txtfat1.getText().equals("")) {
                try {
                    if(!getrate1()) { }
                } catch (SQLException ex) {
                    Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }              
        } 
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtfat1ActionPerformed

    private void txtclr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclr1ActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtclr1ActionPerformed

    private void txtrate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrate1ActionPerformed
        if(!"".equals(txtweight1.getText()) && !"".equals(txtrate1.getText())) {
            double wt = Double.parseDouble(txtweight1.getText());
            double rt = Double.parseDouble(txtrate1.getText());
            double amt = wt * rt;
            txtamount1.setText(""+twodf.format(amt));
        }
    }//GEN-LAST:event_txtrate1ActionPerformed

    private void txtamount1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtamount1ActionPerformed
        if(("".equals(txtweight1.getText()) && txtweight1.getText() == null) && (txtamount1.getText() == null && "".equals(txtamount1.getText()))) {
        } else {

            double totamt = Double.parseDouble(""+txtamount1.getText());
            double totweight = Double.parseDouble(""+txtweight1.getText());
            double avgrate = totamt / totweight;

            txtrate1.setText(""+avgrate);
        }
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtamount1ActionPerformed

    private void txtweight2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtweight2ActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtweight2ActionPerformed

    private void txtfat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfat2ActionPerformed
        if(!txtfat1.getText().equals("") || txtfat1.getText().trim()==null){
//            double ft = Double.parseDouble(txtfat1.getText());
//            if(ft >= methods.min_CMFAT && ft <= methods.max_CMFAT) {
//                cmbmilktype.setSelectedIndex(0);
//            } else if(ft >= methods.min_BMFAT && ft <= methods.max_BMFAT) {
//                cmbmilktype.setSelectedIndex(1);
//            }
            if(!txtfat1.getText().equals("")) {
                try {
                    if(!getrate1()) { }
                } catch (SQLException ex) {
                    Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }              
        }        
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtfat2ActionPerformed

    private void txtclr2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclr2ActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtclr2ActionPerformed

    private void txtrate2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrate2ActionPerformed
        if(!"".equals(txtweight2.getText()) && !"".equals(txtrate2.getText())) {
            double wt = Double.parseDouble(txtweight2.getText());
            double rt = Double.parseDouble(txtrate2.getText());
            double amt = wt * rt;
            txtamount2.setText(""+twodf.format(amt));
        }
    }//GEN-LAST:event_txtrate2ActionPerformed

    private void txtamount2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtamount2ActionPerformed
        if(("".equals(txtweight2.getText()) && txtweight2.getText() == null) && (txtamount2.getText() == null && "".equals(txtamount2.getText()))) {
        } else {

            double totamt = Double.parseDouble(""+txtamount2.getText());
            double totweight = Double.parseDouble(""+txtweight2.getText());
            double avgrate = totamt / totweight;

            txtrate2.setText(""+avgrate);
        }
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtamount2ActionPerformed

    private void dtpdate1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpdate1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dtpdate1PropertyChange

    private void optmorning1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optmorning1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optmorning1StateChanged

    private void optmorning1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optmorning1ActionPerformed
        try {
            //methods.currdate = dtpdate.getDate();
            //shiftid = 1;
            clearscreen();
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();        
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_optmorning1ActionPerformed

    private void optevening1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optevening1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optevening1StateChanged

    private void optevening1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optevening1ActionPerformed
        try {
            //methods.currdate = dtpdate.getDate();
            //shiftid = 0;
            clearscreen();
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();        
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_optevening1ActionPerformed

    private void optevening2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optevening2StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optevening2StateChanged

    private void optevening2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optevening2ActionPerformed
        try {
            //methods.currdate = dtpdate.getDate();
            //shiftid = 0;
            clearscreen();
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();        
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_optevening2ActionPerformed

    private void optevening3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optevening3StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optevening3StateChanged

    private void optevening3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optevening3ActionPerformed
        try {
            //methods.currdate = dtpdate.getDate();
            shiftid = 0;
            clearscreen();
            chno = post.getchalanno();
            txtsampleno.setText(""+chno);
            gettotalcollection();
            getshiftreport();        
        } catch (Exception ex) {
            Logger.getLogger(milkdispatch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_optevening3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclearscreen;
    private javax.swing.JButton btnclose;
    private javax.swing.JButton btnpreventry;
    private javax.swing.JButton btnprintreciept;
    private javax.swing.JButton btnreport;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsetting;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cmbmilktype;
    private javax.swing.JComboBox cmbvehical;
    private com.toedter.calendar.JDateChooser dtpdate;
    private com.toedter.calendar.JDateChooser dtpdate1;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JRadioButton optevening;
    private javax.swing.JRadioButton optevening1;
    private javax.swing.JRadioButton optevening2;
    private javax.swing.JRadioButton optevening3;
    private javax.swing.JRadioButton optmorning;
    private javax.swing.JRadioButton optmorning1;
    private javax.swing.JTable table_shiftreport;
    private javax.swing.JFormattedTextField txtamount;
    private javax.swing.JFormattedTextField txtamount1;
    private javax.swing.JFormattedTextField txtamount2;
    private javax.swing.JFormattedTextField txtclr;
    private javax.swing.JFormattedTextField txtclr1;
    private javax.swing.JFormattedTextField txtclr2;
    private javax.swing.JFormattedTextField txtfat;
    private javax.swing.JFormattedTextField txtfat1;
    private javax.swing.JFormattedTextField txtfat2;
    private javax.swing.JFormattedTextField txtrate;
    private javax.swing.JFormattedTextField txtrate1;
    private javax.swing.JFormattedTextField txtrate2;
    private javax.swing.JFormattedTextField txtsampleno;
    private javax.swing.JFormattedTextField txtsanghcode;
    private javax.swing.JTextField txtsanghname;
    private javax.swing.JFormattedTextField txtsaurmilk;
    private javax.swing.JFormattedTextField txtsaurmilkamt;
    private javax.swing.JFormattedTextField txtsnf;
    private javax.swing.JFormattedTextField txtsnf1;
    private javax.swing.JFormattedTextField txtsnf2;
    private javax.swing.JFormattedTextField txtweight;
    private javax.swing.JFormattedTextField txtweight1;
    private javax.swing.JFormattedTextField txtweight2;
    // End of variables declaration//GEN-END:variables
}
