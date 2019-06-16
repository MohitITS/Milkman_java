/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.awt.Color;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class milkcollection extends javax.swing.JInternalFrame {

    private int shiftid;
    private Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
    SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat onedf = new DecimalFormat("#.#");
    DecimalFormat twodf = new DecimalFormat("#0.000");
    Connection conn;
    PreparedStatement INSERT_QRY;
    int prod_ratechartno;
    String qry = "";
    int framemode;
    PreparedStatement SHIFT_REPORT = null;
    private DefaultTableModel dtm;
    private int fatnumber = 0;
    private int[] grid;
    private String contactno = null;
    //String Type Array use to Load Records into File.
    String rowData[][];
    private String prod_milktype = null;
    Vector sms_vector;
    private String current_period_from_date;
    private String current_period_to_date;
    
    class way2sms_user {
        String way2sms_username;
        String way2sms_password;
        String way2sms_quota_complete;
        
        public way2sms_user (String way2sms_username,
                                String way2sms_password) {
            this.way2sms_username = way2sms_username;
            this.way2sms_password = way2sms_password;
            this.way2sms_quota_complete = "No";
        }
        
        public String getWay2SMS_username () {
            return way2sms_username;
        }
        
        public String getWay2SMS_password () {
            return way2sms_password;
        }
        
        public String getWay2SMS_quota () {
            return way2sms_quota_complete;
        }
        
        public void setWay2SMS_quota (String value) {
            this.way2sms_quota_complete = value;
        }
        
        public String toString() {
            return way2sms_username;
        }
    }
    
    public milkcollection() {
        this.sms_vector = new Vector();
        initComponents();
        try {
            // Prepare Connection
            if (conn != null) {
                conn.close();
            }
            conn = methods.getConnection();

            // producer group
            qry = "SELECT producergroup.ID, producergroup.grid, producergroup.grname"
                    + " FROM producergroup;";
            PreparedStatement loadqry = conn.prepareStatement(qry);
            int i = 0;
            try (ResultSet rscombo = loadqry.executeQuery()) {
                grid = new int[6];
                while (rscombo.next()) {
                    cmbgroup.insertItemAt(rscombo.getString("grname"), i);
                    grid[i] = rscombo.getInt("ID");
                    i++;
                }
            }
            cmbgroup.setSelectedIndex(0);

            // Prepate statement for inserting record
            qry = "INSERT INTO mlkCollection (trn_date, shift_id, prod_code,"
                    + " sample_no, mlkType_id, weight, fat, degree, snf, rate,"
                    + " smw, smr, AddedByFK, prod_id, prodgrid) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            INSERT_QRY = conn.prepareStatement(qry);

            this.shiftid = 1;
            this.framemode = 1;

            dtpdate.setDate(date);

            String daate = sdf.format(date);
            StringTokenizer st_time = new StringTokenizer(daate, " ");
            String timeStr[] = new String[7];
            i = 0;
            while (st_time.hasMoreElements()) {
                timeStr[i++] = st_time.nextToken();
            }
            if (timeStr[1].equals("AM")) {
                optmorning.setSelected(true);
                shiftid = 1;
            } else {
                optevening.setSelected(true);
                shiftid = 2;
            }
            if (methods.fix_snf == false) {
            }
            if (methods.manualrateentry == true) {
                txtrate.setFocusable(true);
                txtclr.setNextFocusableComponent(txtrate);
                txtrate.setNextFocusableComponent(btnsave);
            }
            
            txtsampleno.setText("" + getsampleno());

            clearscreen();

            txtproducercode.requestFocus();
            
            if (methods.is_clr_not_mandatory) {
                txtclr.setFocusable(false);
                txtsnf.setFocusable(true);
            } 

            gettotalcollection();

            int maxrows;
            //"दुध प्रकार", 
            String colheader[] = {"सॅम्पल नं", "उ. कोड", "वजन (ली)", "फॅट", "एस. एन. एफ.", "सी. एल. आर", "दर", "रक्कम"};

            maxrows = 500;//rsshiftreport.
            dtm = new DefaultTableModel(colheader, maxrows);
            table_shiftreport.setModel(dtm);

            table_shiftreport.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                    return c;
                }
            });

            getshiftreport();
            
            txtproducercode.requestFocus();
            
            // Get way2smsusername
            qry = "SELECT mobno, password FROM way2smsm_user;";
            PreparedStatement way2sms = conn.prepareStatement(qry);
            i = 0;
            try (ResultSet rscombo = way2sms.executeQuery()) {
                
                while (rscombo.next()) {
                    sms_vector.addElement(
                            new way2sms_user(rscombo.getString("mobno"), rscombo.getString("password"))
                    );
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void displayforediting(int samp_no) {
        try {
            // set the framemode to 2 
            this.framemode = 2;
            String qry_edit;
            qry_edit = "SELECT mlkCollection.sample_no, mlkCollection.prod_code,"
                    + "mlkCollection.mlkType_id, mlkCollection.weight, mlkCollection.fat,"
                    + "mlkCollection.degree, mlkCollection.snf, mlkCollection.rate, mlkCollection.smw, mlkCollection.smr\n"
                    + "FROM mlkCollection\n"
                    + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#)"
                    + " AND ((mlkCollection.shift_id)=" + shiftid + ") AND"
                    + " ((mlkCollection.sample_no)=" + samp_no + "));";
            try (PreparedStatement editqry = conn.prepareStatement(qry_edit); ResultSet rsedit = editqry.executeQuery()) {
                if (rsedit.next()) {

                    txtproducercode.setText(rsedit.getString("prod_code"));
                    //txtproducercode1.setText(rsedit.getString("prod_id"));
                    getprodname(Integer.parseInt(txtproducercode.getText()));
                    txtsampleno.setText(rsedit.getString("sample_no"));
                    txtweight.setText(rsedit.getString("weight"));
                    if (rsedit.getInt("mlkType_id") == 1) {
                        cmbmilktype.setSelectedIndex(0);
                    } else {
                        cmbmilktype.setSelectedIndex(1);
                    }
                    txtfat.setText(rsedit.getString("fat"));
                    txtclr.setText(rsedit.getString("degree"));
                    txtsnf.setText(rsedit.getString("snf"));
                    txtrate.setText(rsedit.getString("rate"));
                    txtsaurmilk.setText(rsedit.getString("smw"));
                    txtsaurmilkrate.setText(rsedit.getString("smr"));

                    txtproducername.setText(getprodname(Integer.parseInt(txtproducercode.getText())));

                } else {
                    JOptionPane.showMessageDialog(null, "संकलनाची एन्ट्री नाही..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearscreen() {
        txtproducercode.setText(null);
        txtproducercode1.setText(null);
        txtproducername.setText(null);
        txtweight.setText("0.0");
        txtfat.setText("0.0");
        txtclr.setText("0.0");
        txtsnf.setText("0.0");
        txtrate.setText("0.0");
        txtamount.setText("0.0");
        txtsaurmilk.setText("0.0");
        txtsaurmilkrate.setText("0.0");
        txtproducercode.requestFocus();
        
        txtweight1.setText("0.0");
        txtfat1.setText("0.0");
        txtclr1.setText("0.0");
        txtsnf1.setText("0.0");
        txtrate1.setText("0.0");
        
    }

    // Method to get sample no 
    private int getsampleno() throws SQLException {

        int sno = 1;
        //System.out.println(dtpdate.getDate());
        if (dtpdate.getDate() != null || !dtpdate.getDate().equals("")) {
            if (conn != null) {
                String qry1 = "SELECT Max(mlkCollection.sample_no) AS MaxOfsample_no "
                        + "FROM mlkCollection "
                        + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) "
                        + "AND ((mlkCollection.shift_id)=" + shiftid + "));";
                //System.out.println(qry1);
                ////System.out.println(conn.get);
                PreparedStatement sampleno = conn.prepareStatement(qry1);
                ResultSet rssampleno;
                rssampleno = sampleno.executeQuery();
                while (rssampleno.next()) {
                    ////System.out.println(rssampleno.getInt("MaxOfsample_no"));
                    int sn = rssampleno.getInt("MaxOfsample_no");
                    ////System.out.println(sn);
                    if (sn == 0) {
                        sno = 1;
                    } else {
                        sno = sn;
                        sno = sno + 1;
                    }
                }
                rssampleno.close();
                fatnumber = 0;
            }
        }
        return sno;
    }

    // Method to get Producer no
    private String getprodname(int id) throws SQLException {
        String pname = "";
        if (conn != null) {
            String qry2 = "SELECT producer.pro_name, producer.rtc_no, producer.ID, producer.prod_id, producer.contact_no, producer.prod_milktype"
                    + " FROM producer"
                    + " WHERE (((producer.ID)=" + id + ") and producergroupid = " + grid[cmbgroup.getSelectedIndex()] + ");";
            PreparedStatement prname = conn.prepareStatement(qry2);
            ResultSet rspname;
            rspname = prname.executeQuery();
            while (rspname.next()) {
                pname = rspname.getString("pro_name");
                prod_ratechartno = rspname.getInt("rtc_no");
                txtproducercode1.setText("" + rspname.getInt("prod_id"));
                prod_milktype = rspname.getString("prod_milktype");
                if (prod_milktype.equals("COW")) {
                    cmbmilktype.setSelectedIndex(0);
                } else {
                    cmbmilktype.setSelectedIndex(1);
                }
                if (!pname.equals("")) {
                    // do nothing return pname
                } else {
                    pname = "";
                }
                contactno = rspname.getString("contact_no");
            }
            rspname.close();
        }
        return pname;
    }

    private boolean getrate() throws SQLException, ParseException {
        boolean ret = false;
        double rate;
        double calcsnf;
        double calclr = 0;
        double calfat = 0;
        calcsnf = 0.0;
        String qry1 = null;
        int milkid = cmbmilktype.getSelectedIndex();
        milkid = milkid + 1;

        if (!txtfat.getText().equals("") || txtfat.getText().trim() != null) {
            // get the fat
            calfat = Double.parseDouble(txtfat.getText());
            calclr = Double.parseDouble(txtclr.getText());
            if (!methods.is_clr_not_mandatory) {
            
                if (!txtclr.getText().equals(""+0)) {
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
                //System.out.println(calclr);
                //System.out.println(calcsnf);
            } else {
                
                calcsnf = Double.parseDouble(txtsnf.getText());
                calclr = (calcsnf - ((0.21 * calfat) + 0.36)) * 4;
                
                txtclr.setText("" + onedf.format(calclr));
                
            }
        }

        if (calfat > 0 && (calcsnf >= methods.min_CMFAT || calcsnf >= methods.min_BMFAT) && (calclr >= methods.min_cmclr || calclr >= methods.min_bmclr)) {
            //if (!"".equals(txtfat.getText().trim()) && !"".equals(txtclr.getText().trim())) {
                if ("Yes".equals(methods.ratechart_asper_time)) {
                    
                    prod_ratechartno = 1;
                    
                    // Get the current time
                    String currtime = sdf3.format(new Date());
                    //System.out.println(currtime);
                    
                    String qry_ratechart = "SELECT DISTINCT rateChart.rtc_no, rateChart.fromtime, rateChart.totime FROM rateChart;";
                    PreparedStatement stmt_ratechart = conn.prepareStatement(qry_ratechart);
                    ResultSet rsratechart;
                    rsratechart = stmt_ratechart.executeQuery();
                    while (rsratechart.next()) {
                        
//                        System.out.println(currtime);
//                        System.out.println(sdf3.format(rsratechart.getTime("fromtime")));
//                        System.out.println(sdf3.format(rsratechart.getTime("totime")));
                        
                        String str1 = sdf3.format(rsratechart.getTime("fromtime"));
                        Date t1 = new SimpleDateFormat("HH:mm").parse(str1);
                        Calendar c1 = Calendar.getInstance();
                        c1.setTime(t1);

                        String str2 = sdf3.format(rsratechart.getTime("totime"));
                        Date t2 = new SimpleDateFormat("HH:mm").parse(str2);
                        Calendar c2= Calendar.getInstance();
                        c2.setTime(t2);
                        c2.add(Calendar.DATE, 1);

                        String currenttime = currtime;
                        Date d = new SimpleDateFormat("HH:mm").parse(currenttime);
                        Calendar c3 = Calendar.getInstance();
                        c3.setTime(d);
                        c3.add(Calendar.DATE, 1);

                        Date x = c3.getTime();
                        //System.out.println(x);
                        //System.out.println(c1.getTime());
                        //System.out.println(c2.getTime());
                        if (x.after(c1.getTime()) && x.before(c2.getTime()))
                        {
                            //System.out.println("Criteria match");
                            prod_ratechartno = rsratechart.getInt("rtc_no");
                            break;
                        }
                        
                    }
                    //System.out.println(prod_ratechartno);

                    switch (methods.ratechartcalcbase_CM) {
                        case "FAT-SNF":
                            qry1 = "SELECT rateChart.rate"
                                    + " FROM rateChart"
                                    + " WHERE (((rateChart.milktype_id)=" + milkid + ") AND"
                                    + " ((rateChart.rtc_no)=" + prod_ratechartno + ") AND"
                                    + " ((rateChart.fat)=" + onedf.format(calfat) + ") AND ((rateChart.snf)=" + onedf.format(calcsnf) + "));";
                            break;
                        case "FAT-CLR":
                            qry1 = "SELECT rateChart.rate"
                                    + " FROM rateChart"
                                    + " WHERE (((rateChart.milktype_id)=" + milkid + ") AND"
                                    + " ((rateChart.rtc_no)=" + prod_ratechartno + ") AND"
                                    + " ((rateChart.fat)=" + onedf.format(calfat) + ") AND ((rateChart.clr)=" + onedf.format(calclr) + "));";
                            break;
                    }
                } else {
                    switch (methods.ratechartcalcbase_CM) {
                        case "FAT-SNF":
                            qry1 = "SELECT rateChart.rate"
                                    + " FROM rateChart"
                                    + " WHERE (((rateChart.milktype_id)=" + milkid + ") AND"
                                    + " ((rateChart.rtc_no)=" + prod_ratechartno + ") AND"
                                    + " ((rateChart.fat)=" + onedf.format(calfat) + ") AND ((rateChart.snf)=" + onedf.format(calcsnf) + "));";
                            break;
                        case "FAT-CLR":
                            qry1 = "SELECT rateChart.rate"
                                    + " FROM rateChart"
                                    + " WHERE (((rateChart.milktype_id)=" + milkid + ") AND"
                                    + " ((rateChart.rtc_no)=" + prod_ratechartno + ") AND"
                                    + " ((rateChart.fat)=" + onedf.format(calfat) + ") AND ((rateChart.clr)=" + onedf.format(calclr) + "));";
                            break;
                    }
                }
                //System.out.println(qry1);
                PreparedStatement stmt_rate = conn.prepareStatement(qry1);
                ResultSet rsrate;
                rsrate = stmt_rate.executeQuery();
                if (rsrate.next()) {
                    rate = rsrate.getDouble("rate");
                    if (shiftid == 1) {
                        rate = rate + methods.mor_collectionrateinc;
                    } else {
                        rate = rate + methods.eve_collectionrateinc;
                    }
                    txtrate.setText("" + twodf.format(rate));
                    double amt;
                    double wt = Double.parseDouble(txtweight.getText());
                    amt = (rate * wt);
                    txtamount.setText("" + twodf.format(amt));
                    ret = true;
                } else {
                    JOptionPane.showMessageDialog(null, "कृपया योग्य माहिती भरा...!!!", "Milkman Get Rate", JOptionPane.ERROR_MESSAGE);
                    ret = false;
                }
                rsrate.close();
            //}
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        optmorning = new javax.swing.JRadioButton();
        optevening = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtproducercode = new javax.swing.JFormattedTextField();
        txtproducername = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtsampleno = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbmilktype = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbgroup = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        txtweight = new javax.swing.JFormattedTextField();
        txtfat = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtclr = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txtsnf = new javax.swing.JFormattedTextField();
        txtrate = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtamount = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtsaurmilk = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtsaurmilkrate = new javax.swing.JFormattedTextField();
        btngetweight = new javax.swing.JButton();
        btngetfat = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnsave = new javax.swing.JButton();
        btnsetting = new javax.swing.JButton();
        btnpreventry = new javax.swing.JButton();
        btnclearscreen = new javax.swing.JButton();
        btntotalcollection = new javax.swing.JButton();
        btnprintreciept = new javax.swing.JButton();
        btnclose = new javax.swing.JButton();
        btntotalcollection1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txttotalcowmilk = new javax.swing.JFormattedTextField();
        txttotalbuffmilk = new javax.swing.JFormattedTextField();
        txtcowmilkfat = new javax.swing.JFormattedTextField();
        txtbuffmilkfat = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtcowsnf = new javax.swing.JFormattedTextField();
        txtbuffsnf = new javax.swing.JFormattedTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtavgcrate = new javax.swing.JFormattedTextField();
        txtavgbrate = new javax.swing.JFormattedTextField();
        jLabel24 = new javax.swing.JLabel();
        txtcamt = new javax.swing.JFormattedTextField();
        txtbamt = new javax.swing.JFormattedTextField();
        dtpdate = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_shiftreport = new javax.swing.JTable();
        txtproducercode1 = new javax.swing.JFormattedTextField();
        optevening1 = new javax.swing.JRadioButton();
        txtrate1 = new javax.swing.JFormattedTextField();
        txtsnf1 = new javax.swing.JFormattedTextField();
        txtclr1 = new javax.swing.JFormattedTextField();
        txtfat1 = new javax.swing.JFormattedTextField();
        txtweight1 = new javax.swing.JFormattedTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setTitle("दुध संकलन");
        setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel1.setText("दिनांक :");

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

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel2.setText("शिफ्ट :");

        jLabel3.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel3.setText("उत्पादक कोड :");

        txtproducercode.setForeground(new java.awt.Color(51, 51, 255));
        txtproducercode.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtproducercode.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtproducercode.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtproducercode.setNextFocusableComponent(txtweight);
        txtproducercode.setPreferredSize(null);
        txtproducercode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproducercodeActionPerformed(evt);
            }
        });
        txtproducercode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtproducercodeFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtproducercodeFocusGained(evt);
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
        jLabel4.setText("सॅम्पल नंबर :");

        txtsampleno.setEditable(false);
        txtsampleno.setForeground(new java.awt.Color(51, 51, 255));
        txtsampleno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtsampleno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsampleno.setFocusable(false);
        txtsampleno.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsampleno.setPreferredSize(null);
        txtsampleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsamplenoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel6.setText("दुध प्रकार :");

        cmbmilktype.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        cmbmilktype.setForeground(new java.awt.Color(51, 51, 255));
        cmbmilktype.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "गाय", "म्हैस" }));
        cmbmilktype.setEnabled(false);
        cmbmilktype.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbmilktypeActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jLabel5.setFont(new java.awt.Font("Mangal", 1, 24)); // NOI18N
        jLabel5.setText("दुध संकलन एन्ट्री : कृपया उत्पादक ग्रुप निवडा :");

        cmbgroup.setFont(new java.awt.Font("Mangal", 0, 12)); // NOI18N
        cmbgroup.setFocusable(false);
        cmbgroup.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbgroupItemStateChanged(evt);
            }
        });
        cmbgroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbgroupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbgroup, 0, 123, Short.MAX_VALUE)
                .addGap(216, 216, 216))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbgroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel7.setText("लिटर :");

        txtweight.setForeground(new java.awt.Color(51, 51, 255));
        txtweight.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtweight.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtweight.setText("0.00");
        txtweight.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtweight.setNextFocusableComponent(txtfat);
        txtweight.setSelectionEnd(txtweight.getText().length());
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

        txtfat.setForeground(new java.awt.Color(51, 51, 255));
        txtfat.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtfat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfat.setText("0.0");
        txtfat.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtfat.setNextFocusableComponent(txtclr);
        txtfat.setSelectionEnd(txtfat.getText().length());
        txtfat.setValue(0.0);
        txtfat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfatActionPerformed(evt);
            }
        });
        txtfat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfatFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfatFocusLost(evt);
            }
        });
        txtfat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfatKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfatKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel8.setText("फॅट :");

        jLabel9.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel9.setText("सी. एल. आर. :");

        txtclr.setForeground(new java.awt.Color(51, 51, 255));
        txtclr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtclr.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtclr.setText("0.0");
        txtclr.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtclr.setNextFocusableComponent(txtamount);
        txtclr.setPreferredSize(null);
        txtclr.setSelectionEnd(4);
        txtclr.setSelectionStart(0);
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
        txtsnf.setText("0.0");
        txtsnf.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsnf.setPreferredSize(null);
        txtsnf.setSelectionStart(1);
        txtsnf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsnfActionPerformed(evt);
            }
        });
        txtsnf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsnfFocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsnfFocusGained(evt);
            }
        });

        txtrate.setForeground(new java.awt.Color(51, 51, 255));
        txtrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.000"))));
        txtrate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrate.setText("0.0");
        txtrate.setFocusable(false);
        txtrate.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtrate.setNextFocusableComponent(btnsave);
        txtrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtrateActionPerformed(evt);
            }
        });
        txtrate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtrateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtrateFocusLost(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel11.setText("दर :");

        txtamount.setForeground(new java.awt.Color(51, 51, 255));
        txtamount.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtamount.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtamount.setText("0.00");
        txtamount.setFocusable(false);
        txtamount.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtamount.setNextFocusableComponent(btnsave);
        txtamount.setPreferredSize(null);
        txtamount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtamountActionPerformed(evt);
            }
        });
        txtamount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtamountFocusGained(evt);
            }
        });
        txtamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtamountKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel12.setText("रक्कम :");

        jLabel13.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel13.setText("नाश दुध :");

        txtsaurmilk.setForeground(new java.awt.Color(51, 51, 255));
        txtsaurmilk.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtsaurmilk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsaurmilk.setText("0.0");
        txtsaurmilk.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsaurmilk.setPreferredSize(null);
        txtsaurmilk.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsaurmilkFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsaurmilkFocusLost(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel14.setText("दर :");

        txtsaurmilkrate.setForeground(new java.awt.Color(51, 51, 255));
        txtsaurmilkrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtsaurmilkrate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsaurmilkrate.setText("0.00");
        txtsaurmilkrate.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsaurmilkrate.setPreferredSize(null);

        btngetweight.setText("WET");
        btngetweight.setFocusable(false);

        btngetfat.setText("FAT");
        btngetfat.setFocusable(false);

        jPanel2.setBackground(new java.awt.Color(113, 168, 168));

        btnsave.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnsave.setText("दुध स्वीकृत करणे");
        btnsave.setNextFocusableComponent(txtproducercode);
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

        btntotalcollection.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btntotalcollection.setText("SR-1");
        btntotalcollection.setFocusable(false);
        btntotalcollection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntotalcollectionActionPerformed(evt);
            }
        });

        btnprintreciept.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btnprintreciept.setText("पावती प्रिंट करणे");
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

        btntotalcollection1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        btntotalcollection1.setText("SR-2");
        btntotalcollection1.setFocusable(false);
        btntotalcollection1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntotalcollection1ActionPerformed(evt);
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
                    .addComponent(btnprintreciept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnclose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnsave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btntotalcollection, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btntotalcollection1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addComponent(btntotalcollection)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnprintreciept)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnclose))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(btntotalcollection1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(113, 168, 168));

        jLabel15.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel15.setText("गाय :");

        jLabel16.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel16.setText("म्हैस :");

        jLabel17.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel17.setText("वजन");

        txttotalcowmilk.setForeground(new java.awt.Color(51, 51, 255));
        txttotalcowmilk.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txttotalcowmilk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalcowmilk.setText("0.0");
        txttotalcowmilk.setFocusable(false);
        txttotalcowmilk.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txttotalbuffmilk.setForeground(new java.awt.Color(51, 51, 255));
        txttotalbuffmilk.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txttotalbuffmilk.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txttotalbuffmilk.setText("0.0");
        txttotalbuffmilk.setFocusable(false);
        txttotalbuffmilk.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtcowmilkfat.setForeground(new java.awt.Color(51, 51, 255));
        txtcowmilkfat.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtcowmilkfat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcowmilkfat.setFocusable(false);
        txtcowmilkfat.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtbuffmilkfat.setForeground(new java.awt.Color(51, 51, 255));
        txtbuffmilkfat.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtbuffmilkfat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtbuffmilkfat.setFocusable(false);
        txtbuffmilkfat.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel18.setText("फॅट");

        txtcowsnf.setForeground(new java.awt.Color(51, 51, 255));
        txtcowsnf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtcowsnf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcowsnf.setFocusable(false);
        txtcowsnf.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtbuffsnf.setForeground(new java.awt.Color(51, 51, 255));
        txtbuffsnf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtbuffsnf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtbuffsnf.setFocusable(false);
        txtbuffsnf.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel22.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel22.setText("एस.एन.एफ.");

        jLabel23.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel23.setText("दर");

        txtavgcrate.setForeground(new java.awt.Color(51, 51, 255));
        txtavgcrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtavgcrate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtavgcrate.setFocusable(false);
        txtavgcrate.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtavgbrate.setForeground(new java.awt.Color(51, 51, 255));
        txtavgbrate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtavgbrate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtavgbrate.setFocusable(false);
        txtavgbrate.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jLabel24.setText("रक्कम");

        txtcamt.setForeground(new java.awt.Color(51, 51, 255));
        txtcamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtcamt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcamt.setFocusable(false);
        txtcamt.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        txtbamt.setForeground(new java.awt.Color(51, 51, 255));
        txtbamt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtbamt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtbamt.setFocusable(false);
        txtbamt.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txttotalcowmilk, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                            .addComponent(txttotalbuffmilk))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)
                        .addGap(33, 33, 33)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtcowmilkfat, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addComponent(txtbuffmilkfat))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtcowsnf, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addComponent(txtbuffsnf))
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel23))
                    .addComponent(txtavgcrate, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(txtavgbrate))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtcamt)
                            .addComponent(txtbamt))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24)
                        .addGap(52, 52, 52))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcowmilkfat)
                            .addComponent(txtcowsnf)
                            .addComponent(txtavgcrate)
                            .addComponent(txtcamt))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbuffmilkfat)
                            .addComponent(txtbuffsnf)
                            .addComponent(txtavgbrate)
                            .addComponent(txtbamt)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txttotalcowmilk)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txttotalbuffmilk)))))
                .addContainerGap())
        );

        dtpdate.setFocusable(false);
        dtpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dtpdate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dtpdatePropertyChange(evt);
            }
        });

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

        txtproducercode1.setForeground(new java.awt.Color(51, 51, 255));
        txtproducercode1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        txtproducercode1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtproducercode1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtproducercode1.setNextFocusableComponent(txtweight);
        txtproducercode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtproducercode1ActionPerformed(evt);
            }
        });
        txtproducercode1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtproducercode1FocusLost(evt);
            }
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtproducercode1FocusGained(evt);
            }
        });
        txtproducercode1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtproducercode1KeyReleased(evt);
            }
        });

        optevening1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(optevening1);
        optevening1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        optevening1.setForeground(new java.awt.Color(51, 51, 255));
        optevening1.setText("एकत्र");
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

        txtrate1.setForeground(new java.awt.Color(51, 51, 255));
        txtrate1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.000"))));
        txtrate1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtrate1.setText("0.0");
        txtrate1.setFocusable(false);
        txtrate1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtrate1.setNextFocusableComponent(btnsave);
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

        txtsnf1.setForeground(new java.awt.Color(51, 51, 255));
        txtsnf1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtsnf1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsnf1.setText("0.0");
        txtsnf1.setFocusable(false);
        txtsnf1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtsnf1.setPreferredSize(null);
        txtsnf1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtsnf1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtsnf1FocusLost(evt);
            }
        });

        txtclr1.setForeground(new java.awt.Color(51, 51, 255));
        txtclr1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtclr1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtclr1.setText("0.0");
        txtclr1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtclr1.setNextFocusableComponent(txtamount);
        txtclr1.setPreferredSize(null);
        txtclr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclr1ActionPerformed(evt);
            }
        });
        txtclr1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtclr1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtclr1FocusLost(evt);
            }
        });
        txtclr1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtclr1KeyReleased(evt);
            }
        });

        txtfat1.setForeground(new java.awt.Color(51, 51, 255));
        txtfat1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.0"))));
        txtfat1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtfat1.setText("0.0");
        txtfat1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtfat1.setNextFocusableComponent(txtclr);
        txtfat1.setSelectionEnd(txtfat.getText().length());
        txtfat1.setValue(0.0);
        txtfat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfat1ActionPerformed(evt);
            }
        });
        txtfat1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtfat1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtfat1FocusLost(evt);
            }
        });
        txtfat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtfat1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtfat1KeyReleased(evt);
            }
        });

        txtweight1.setForeground(new java.awt.Color(51, 51, 255));
        txtweight1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        txtweight1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtweight1.setText("0.0");
        txtweight1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        txtweight1.setNextFocusableComponent(txtfat);
        txtweight1.setPreferredSize(null);
        txtweight1.setSelectionEnd(txtweight.getText().length());
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dtpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(optmorning, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(optevening)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(optevening1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtproducercode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtproducercode1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtproducername, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtsaurmilk, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel14)
                                                .addGap(11, 11, 11)
                                                .addComponent(txtsaurmilkrate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(txtrate, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtsnf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                                    .addComponent(txtclr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(txtfat)
                                                    .addComponent(txtweight)
                                                    .addComponent(txtsampleno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(txtrate1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(txtsnf1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(txtclr1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(txtfat1)
                                                            .addComponent(txtweight1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(btngetweight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btngetfat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addComponent(cmbmilktype, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(59, 59, 59)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtamount, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtclr, txtfat, txtproducercode, txtproducercode1, txtrate, txtsampleno, txtsnf, txtweight});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                .addComponent(dtpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(optmorning, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(optevening, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2)
                                .addComponent(optevening1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtproducercode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtproducercode1))
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtproducername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtsampleno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6)
                                    .addComponent(cmbmilktype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtweight)
                                        .addComponent(btngetweight, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtweight1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtfat)
                                    .addComponent(txtfat1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtclr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtsnf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtrate)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtclr1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtsnf1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtrate1)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtamount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtsaurmilk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtsaurmilkrate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(btngetfat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncloseActionPerformed
        this.dispose();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btncloseActionPerformed

    private void txtproducercodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtproducercodeFocusGained
        txtproducercode.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtproducercodeFocusGained

    private void txtproducercodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtproducercodeFocusLost
        txtproducercode.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtproducercodeFocusLost

    private void getshiftreport() {
        try {
            if (dtpdate.getDate() != null) {
                int r;
                int c;
                for (r = 0; r < table_shiftreport.getRowCount(); r++) {
                    for (c = 0; c < table_shiftreport.getColumnCount(); c++) {
                        table_shiftreport.setValueAt("", r, c);
                    }
                }
                if (cmbgroup.getSelectedIndex() == 0) {
                    qry = "SELECT mlkCollection.sample_no, mlkCollection.prod_code, mlkCollection.weight, mlkCollection.fat, mlkCollection.snf,"
                            + " mlkCollection.degree, mlkCollection.rate, [mlkCollection]![rate]*[mlkCollection]![weight] AS amt "
                            + " FROM producer INNER JOIN mlkCollection ON producer.prod_id = mlkCollection.prod_id"
                            + " WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND"
                            + " ((mlkCollection.shift_id)=" + shiftid + ")) ORDER BY mlkCollection.prod_code;";
                } else {
                    qry = "SELECT mlkCollection.sample_no, mlkCollection.prod_code, mlkCollection.weight, mlkCollection.fat, mlkCollection.snf,"
                            + " mlkCollection.degree, mlkCollection.rate, [mlkCollection]![rate]*[mlkCollection]![weight] AS amt "
                            + " FROM producer INNER JOIN mlkCollection ON producer.prod_id = mlkCollection.prod_id"
                            + " WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND"
                            + " ((mlkCollection.shift_id)=" + shiftid + ") AND"
                            + " ((mlkCollection.prodgrid)=" + grid[cmbgroup.getSelectedIndex()] + ")) ORDER BY mlkCollection.prod_code;";
                            // cmbgroup.getSelectedItem() 
                }
                SHIFT_REPORT = conn.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                try (ResultSet rsshiftreport = SHIFT_REPORT.executeQuery()) {
                    if (rsshiftreport.next()) {
                        rsshiftreport.beforeFirst();
                        int i = 0;
                        int j;
                        while (rsshiftreport.next()) { // i loop
                            for (j = 1; j <= table_shiftreport.getColumnCount(); j++) {
                                table_shiftreport.setValueAt(rsshiftreport.getString(j), i, j - 1);
                            }
                            i++;
                        }
                    }
                }
                SHIFT_REPORT.close();
                gettotalcollection();
            }
        } catch (Exception ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void txtproducercodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtproducercodeKeyReleased
    }//GEN-LAST:event_txtproducercodeKeyReleased

    private void txtweightKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtweightKeyReleased
    }//GEN-LAST:event_txtweightKeyReleased

    private void txtfatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfatKeyReleased
//        try {
//            if (evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57 && evt.getKeyCode() != 46) {
//                if (fatnumber < 1) {
//                    fatnumber++;
//                    String fatno = txtfat.getText();
//                    fatno = fatno + ".";
//                    txtfat.setText(fatno);
//                }
//            } else if (evt.getKeyCode() == 46) {
//                JOptionPane.showMessageDialog(null, "कृपया फक्त नंबर टाका...!!!", title, JOptionPane.INFORMATION_MESSAGE);
//                txtfat.setText("0.0");
//                txtfat.requestFocus();
//                txtfat.selectAll();
//            } else if (evt.getKeyCode() == 8) {
//                txtfat.setText("");
//                fatnumber = 0;
//            }
//        } catch (NumberFormatException nfe) {
//            JOptionPane.showMessageDialog(null, "कृपया योग्य माहिती भरा...!!!", "Milkman", JOptionPane.ERROR_MESSAGE);
//            clearscreen();
//        }
    }//GEN-LAST:event_txtfatKeyReleased

    private void txtclrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclrKeyReleased
    }//GEN-LAST:event_txtclrKeyReleased

    private void btnsaveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnsaveFocusGained
        btnsave.setBackground(Color.CYAN);
    }//GEN-LAST:event_btnsaveFocusGained

    private void btnsaveFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnsaveFocusLost
        btnsave.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnsaveFocusLost

    private void txtweightFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweightFocusGained
        int selend = txtweight.getText().length();
        txtweight.select(0, selend);
        //txtweight.selectAll();
        txtweight.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtweightFocusGained

    private void txtweightFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweightFocusLost
        txtweight.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtweightFocusLost

    private void txtfatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfatFocusGained
        txtfat.setBackground(Color.CYAN);
        txtfat.selectAll();
    }//GEN-LAST:event_txtfatFocusGained

    private void txtfatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfatFocusLost
        txtfat.setBackground(Color.WHITE);
        try {
            if (!txtfat.getText().equals("") || txtfat.getText().trim() == null) {
                try {
                    switch (methods.prod_seperate_for_C_M) {
                        case "No":
                            double ft = Double.parseDouble(txtfat.getText());
                            if (ft >= methods.min_CMFAT && ft <= methods.max_CMFAT) {
                                cmbmilktype.setSelectedIndex(0);
                            } else if (ft >= methods.min_BMFAT && ft <= methods.max_BMFAT) {
                                cmbmilktype.setSelectedIndex(1);
                            }
                            break;
                        case "Yes":
                            //System.out.println(prod_milktype);
                            if (prod_milktype.equals("COW")) {
                                cmbmilktype.setSelectedIndex(0);
                            } else {
                                cmbmilktype.setSelectedIndex(1);
                            }
                            break;
                    }
                    if (!getrate()) {
                    }
                    
                } catch (SQLException | ParseException ex) {
                    Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NumberFormatException nfe) {
        }
    }//GEN-LAST:event_txtfatFocusLost

    private void txtclrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclrFocusGained
        txtclr.setBackground(Color.CYAN);
        txtclr.selectAll();
    }//GEN-LAST:event_txtclrFocusGained

    private void txtclrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclrFocusLost
        txtclr.setBackground(Color.WHITE);
        if (!txtclr.getText().equals("")) {
            try {
                if (!getrate()) {
                    clearscreen();
                    txtsampleno.setText("" + getsampleno());
                    txtproducercode.requestFocus();
                }
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtclrFocusLost

    private void optmorningStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optmorningStateChanged
        if (optmorning.isSelected() == true) {
            shiftid = 1;
        } else {
            shiftid = 2;
        }
        clearscreen();
        try {
            txtsampleno.setText("" + getsampleno());
            gettotalcollection();
            getshiftreport();
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_optmorningStateChanged

    private void opteveningStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_opteveningStateChanged
        if (optevening.isSelected() == true) {
            shiftid = 2;
        } else {
            shiftid = 1;
        }
        clearscreen();
        try {
            txtsampleno.setText("" + getsampleno());
            gettotalcollection();
            getshiftreport();
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_opteveningStateChanged

    private void btnclearscreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearscreenActionPerformed
        clearscreen();
        try {
            txtsampleno.setText("" + getsampleno());
            gettotalcollection();
            getshiftreport();
            fatnumber = 0;
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.framemode = 1;
    }//GEN-LAST:event_btnclearscreenActionPerformed

    // method to get total collection and average fat snf
    private void gettotalcollection() {
        String coll_qry = "";
        double tot_cm = 0;
        double tot_bm = 0;
        double tot_cmwf = 0;
        double tot_bmwf = 0;
        double avg_cmf = 0;
        double avg_bmf = 0;
        double tot_cmws = 0;
        double tot_bmws = 0;
        double avg_cms = 0;
        double avg_bms = 0;
        double CM_amt = 0;
        double BM_amt = 0;
        double CM_avgrate = 0;
        double BM_avgrate = 0;

        try {
            //--- Getting total weight
            if (cmbgroup.getSelectedIndex() == 0) {
                coll_qry = "SELECT mlkCollection.mlkType_id, Sum(mlkCollection.weight) AS SumOfweight "
                        + "FROM mlkCollection "
                        + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND ((mlkCollection.shift_id)=" + shiftid + ")) "
                        + "GROUP BY mlkCollection.mlkType_id;";
            } else {
                coll_qry = "SELECT mlkCollection.mlkType_id, Sum(mlkCollection.weight) AS SumOfweight "
                        + "FROM mlkCollection "
                        + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND ((mlkCollection.shift_id)=" + shiftid + ") AND ((mlkCollection.prodgrid)=" + grid[cmbgroup.getSelectedIndex()] + ")) "
                        + "GROUP BY mlkCollection.mlkType_id;";

            }
            PreparedStatement tot_coll = conn.prepareStatement(coll_qry);
            try (ResultSet rstotcoll = tot_coll.executeQuery()) {
                while (rstotcoll.next()) {
                    if (rstotcoll.getInt("mlkType_id") == 1) {
                        tot_cm = rstotcoll.getDouble("SumOfweight");
                    } else {
                        tot_bm = rstotcoll.getDouble("SumOfweight");
                    }
                }
            }
            tot_coll.close();
            txttotalcowmilk.setText("" + twodf.format(tot_cm));
            txttotalbuffmilk.setText("" + twodf.format(tot_bm));
            //--- end Getting total weight

            //--- Getting total avg fat
            coll_qry = "SELECT Sum([mlkCollection]![fat]*[mlkCollection]![weight]) AS Expr1, mlkCollection.mlkType_id\n"
                    + "FROM mlkCollection\n"
                    + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND ((mlkCollection.shift_id)=" + shiftid + ") AND ((mlkCollection.prodgrid)=" + grid[cmbgroup.getSelectedIndex()] + "))\n"
                    + "GROUP BY mlkCollection.mlkType_id;";
            tot_coll = conn.prepareStatement(coll_qry);
            try (ResultSet rstotcoll = tot_coll.executeQuery()) {
                while (rstotcoll.next()) {
                    if (rstotcoll.getInt("mlkType_id") == 1) {
                        tot_cmwf = rstotcoll.getDouble("Expr1");
                    } else {
                        tot_bmwf = rstotcoll.getDouble("Expr1");
                    }
                }
            }
            tot_coll.close();
            if (tot_cmwf != 0) {
                avg_cmf = tot_cmwf / tot_cm;
            }
            if (tot_bmwf != 0) {
                avg_bmf = tot_bmwf / tot_bm;
            }
            txtcowmilkfat.setText("" + onedf.format(avg_cmf));
            txtbuffmilkfat.setText("" + onedf.format(avg_bmf));
            //--- Getting avg fat

            //--- Getting total avg snf
            coll_qry = "SELECT Sum([mlkCollection]![snf]*[mlkCollection]![weight]) AS Expr1, mlkCollection.mlkType_id\n"
                    + "FROM mlkCollection\n"
                    + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND ((mlkCollection.shift_id)=" + shiftid + ") AND ((mlkCollection.prodgrid)=" + grid[cmbgroup.getSelectedIndex()] + "))\n"
                    + "GROUP BY mlkCollection.mlkType_id;";
            tot_coll = conn.prepareStatement(coll_qry);
            try (ResultSet rstotcoll = tot_coll.executeQuery()) {
                while (rstotcoll.next()) {
                    if (rstotcoll.getInt("mlkType_id") == 1) {
                        tot_cmws = rstotcoll.getDouble("Expr1");
                    } else {
                        tot_bmws = rstotcoll.getDouble("Expr1");
                    }
                }
            }
            tot_coll.close();
            if (tot_cmws != 0) {
                avg_cms = tot_cmws / tot_cm;
            }
            if (tot_bmws != 0) {
                avg_bms = tot_bmws / tot_bm;
            }
            txtcowsnf.setText("" + onedf.format(avg_cms));
            txtbuffsnf.setText("" + onedf.format(avg_bms));
            //--- Getting avg snf

            //--- Getting total amot and avg rate
            coll_qry = "SELECT Sum([mlkCollection]![rate]*[mlkCollection]![weight]) AS Expr1, mlkCollection.mlkType_id\n"
                    + "FROM mlkCollection\n"
                    + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND ((mlkCollection.shift_id)=" + shiftid + ") AND ((mlkCollection.prodgrid)=" + grid[cmbgroup.getSelectedIndex()] + "))\n"
                    + "GROUP BY mlkCollection.mlkType_id;";
            tot_coll = conn.prepareStatement(coll_qry);
            try (ResultSet rstotcoll = tot_coll.executeQuery()) {
                while (rstotcoll.next()) {
                    if (rstotcoll.getInt("mlkType_id") == 1) {
                        CM_amt = rstotcoll.getDouble("Expr1");
                    } else {
                        BM_amt = rstotcoll.getDouble("Expr1");
                    }
                }
            }
            tot_coll.close();
            if (CM_amt != 0) {
                CM_avgrate = CM_amt / tot_cm;
            }
            if (BM_amt != 0) {
                BM_avgrate = BM_amt / tot_bm;
            }
            txtavgcrate.setText("" + twodf.format(CM_avgrate));
            txtavgbrate.setText("" + twodf.format(BM_avgrate));
            txtcamt.setText("" + twodf.format(CM_amt));
            txtbamt.setText("" + twodf.format(BM_amt));

            //--- end Getting total amot and avg rate

        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void generatereciept() {
//        String cond_qry = "mlkCollection.shift_id = " + shiftid + " and mlkCollection.trn_date = #" + mdy.format(dtpdate.getDate()) + "# and mlkCollection.sample_no=" + txtsampleno.getText() + "";
//        //System.out.println(cond_qry);
//        try {
//
//            Map<String, Object> params;
//            params = new HashMap<>();
//            params.put("wherecondition", cond_qry);
//
//            JasperPrint jasperprint;
//            String rname = methods.reportpath + "//rptmilkbillreciept.jrxml";
//            String outputFileName = methods.reportpath + "//rptmilkbillreciept.txt";
//            JasperReport jasperreport;
//            jasperreport = JasperCompileManager.compileReport(rname);
//            jasperprint = JasperFillManager.fillReport(jasperreport, params, conn);
//
//            //            JasperViewer jv;            
////            jv = new JasperViewer(jasperprint, false);
////            jv.setExtendedState(JFrame.MAXIMIZED_BOTH);
////            jv.setVisible(true);
//
//            //JasperPrintManager.printReport(jasperprint,false);
//
//            //JasperReport jasperReport = JasperCompileManager.compileReport(rname);
//            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
//
//            JRTextExporter exporter = new JRTextExporter();
//
//            exporter.setParameter(JRTextExporterParameter.PAGE_WIDTH, 160);
//            exporter.setParameter(JRTextExporterParameter.PAGE_HEIGHT, 20);
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);
//            exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Float(7));
//            //exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, 7.33);
//            exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Float(11));
//            //exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, 11.33);
//
//            exporter.exportReport();
//
//
//
//            //methods.displayreport("rptmilkbillreciept", conn, cond_qry);
////        } catch (SQLException ex) {            
////            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JRException ex) {
//            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public class sendsms_way2sms extends Thread {
        private String smsstr;
        
        public sendsms_way2sms (String smsstr) {
            this.smsstr = smsstr;
            //System.out.println("Lenght : " + smsstr.length());
        }
        
        public void run() {
//            HttpURLConnection connection = null;
//            try {
//                
//                String url1 = "http://login.wishbysms.com/api/sendhttp.php";
//                String urlParameters = "authkey=243672AJn19mBZUrh95bcaed39&mobiles="+contactno+"&message="+ smsstr +"&sender=MohITS&route=4&country=91";
//                //Create connection
//                URL url = new URL(url1);
//                System.out.println(url);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//                
//                connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
//                connection.setRequestProperty("Content-Language", "en-US");  
//
//                connection.setUseCaches(false);
//                connection.setDoOutput(true);
//                
//                connection.connect();
//                
//                //Send request
//                try (DataOutputStream wr = new DataOutputStream (connection.getOutputStream())) {
//                    wr.writeBytes(urlParameters);
//                }

//                //Get Response  
//                InputStream is = connection.getInputStream();
//                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
//                String line;
//                while ((line = rd.readLine()) != null) {
//                    response.append(line);
//                    response.append('\r');
//                }
//                rd.close();
                //return response.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//                //return null;
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//            }
            
            //for (int i = 0; i < sms_vector.size(); i++) {
                //way2sms_user obj = (way2sms_user)sms_vector.get(i);
                boolean smssent;
                smssent = false;                                                            
                //if ("No".equals(obj.getWay2SMS_quota())) {
                
                    try {
                        //String url = "http://localhost/way2sms_2/sendsms.php?msg="+ URLEncoder.encode(smsstr, "UTF-8") +"&rec="+contactno+"&username="+obj.getWay2SMS_username()+"&password="+obj.getWay2SMS_password();
                        //String url = "http://login.wishbysms.com/api/sendhttp.php?authkey="+methods.sms_authkey+"&mobiles="+contactno+"&message="+ URLEncoder.encode(smsstr, "UTF-8") +"&sender=" + methods.sms_senderid + "&route=4&country=91";
                        //String url = "http://smslogin.pcexpert.in/api/mt/SendSMS?user="+methods.sms_username+"&password="+methods.sms_password+"&senderid=" + methods.sms_senderid + "&channel=Trans&DCS=0&flashsms=0&number="+contactno+"&text="+ URLEncoder.encode(smsstr, "UTF-8") +"&route=02";
                        String url = "http://smslogin.pcexpert.in/api/mt/SendSMS?user="+methods.sms_username.replace(" ", "%20")+"&password="+methods.sms_password+"&senderid="+methods.sms_senderid+"&channel=Trans&DCS=0&flashsms=0&number="+contactno+"&text="+URLEncoder.encode(smsstr, "UTF-8")+"&route02";
                        URL sms = new URL(url);
                        URLConnection yc = sms.openConnection();
                        System.out.println(url);
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                        yc.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            //System.out.println(inputLine);
                            //smssent = "true".equals(inputLine);
                            smssent = true;
                        }
                        in.close();
                        
                        //System.out.println(smssent);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                //} 
//                if (smssent) {
//                    break;
//                } else {
//                    //obj.setWay2SMS_quota("Yes");
//                }
            //}
        }
    }
    
    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        int milkid = cmbmilktype.getSelectedIndex();
        milkid++;
        if (this.framemode == 1) {
            if (!txtproducercode.getText().equals("") && !txtfat.getText().equals("") && !txtclr.getText().equals("") && !txtweight.getText().equals("") && !txtrate.getText().equals("")) {
                try {
                    INSERT_QRY.setString(1, "" + sdf2.format(dtpdate.getDate()));
                    INSERT_QRY.setInt(2, shiftid);
                    INSERT_QRY.setInt(3, Integer.parseInt(txtproducercode.getText()));
                    INSERT_QRY.setInt(4, Integer.parseInt(txtsampleno.getText()));
                    INSERT_QRY.setInt(5, milkid);
                    INSERT_QRY.setDouble(6, Double.parseDouble(txtweight.getText()));
                    INSERT_QRY.setDouble(7, Double.parseDouble(txtfat.getText()));
                    INSERT_QRY.setDouble(8, Double.parseDouble(txtclr.getText()));
                    INSERT_QRY.setDouble(9, Double.parseDouble(txtsnf.getText()));
                    INSERT_QRY.setDouble(10, Double.parseDouble(txtrate.getText()));
                    INSERT_QRY.setDouble(11, Double.parseDouble(txtsaurmilk.getText()));
                    INSERT_QRY.setDouble(12, Double.parseDouble(txtsaurmilkrate.getText()));
                    INSERT_QRY.setInt(13, methods.userid);
                    INSERT_QRY.setInt(14, Integer.parseInt(txtproducercode1.getText()));
                    INSERT_QRY.setInt(15, Integer.parseInt("" + grid[cmbgroup.getSelectedIndex()])); //  cmbgroup.getSelectedItem() 

                    INSERT_QRY.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "कृपया माहिती पूर्ण भरा..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (this.framemode == 2) {
            if (!txtproducercode.getText().equals("") && !txtfat.getText().equals("") && !txtclr.getText().equals("") && !txtweight.getText().equals("") && !txtrate.getText().equals("")) {
                try {
                    // Prepate statement for inserting record
                    qry = "UPDATE mlkCollection SET prod_code=?,"
                            + " mlkType_id=?, weight=?, fat=?, degree=?, snf=?, rate=?,"
                            + " smw=?, smr=?, LastUserFK=?, DateModified=?, prod_id = ?, prodgrid=? Where trn_date=#" + mdy.format(dtpdate.getDate()) + "# and"
                            + " shift_id=" + shiftid + " and sample_no = " + Integer.parseInt(txtsampleno.getText()) + ";";
                    PreparedStatement update_qry = conn.prepareStatement(qry);
                    update_qry.setInt(1, Integer.parseInt(txtproducercode.getText()));
                    update_qry.setInt(2, milkid);
                    update_qry.setDouble(3, Double.parseDouble(txtweight.getText()));
                    update_qry.setDouble(4, Double.parseDouble(txtfat.getText()));
                    update_qry.setDouble(5, Double.parseDouble(txtclr.getText()));
                    update_qry.setDouble(6, Double.parseDouble(txtsnf.getText()));
                    update_qry.setDouble(7, Double.parseDouble(txtrate.getText()));
                    update_qry.setDouble(8, Double.parseDouble(txtsaurmilk.getText()));
                    update_qry.setDouble(9, Double.parseDouble(txtsaurmilkrate.getText()));
                    update_qry.setInt(10, methods.userid);
                    update_qry.setString(11, "" + sdf2.format(date));
                    update_qry.setInt(12, Integer.parseInt(txtproducercode1.getText()));
                    update_qry.setInt(13, Integer.parseInt("" + grid[cmbgroup.getSelectedIndex()])); //  cmbgroup.getSelectedItem() 

                    update_qry.execute();
                    //System.out.println("Updated Successfully");


                } catch (SQLException ex) {
                    Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "कृपया माहिती पूर्ण भरा..!!", "Milkman", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        //System.out.println(methods.smsfacilityreq);
        if (methods.smsfacilityreq == true) {
            String smsstr = "";
            String shft, mtype;
            shft = shiftid == 1 ? "M" : "E";
            mtype = milkid == 1 ? "COW" : "BUFELOW";
            smsstr = methods.smssendby + "\n" + dmy.format(dtpdate.getDate()) + " " + shft + "\nCode-" + txtproducercode.getText() + "\nMilk-" + mtype + "\nLtr-" + Double.parseDouble(txtweight.getText()) + "\nFAT-" + Double.parseDouble(txtfat.getText()) + "\nSNF-" + Double.parseDouble((txtsnf.getText())) + "\nRate-" + Double.parseDouble(txtrate.getText()) + "\nAMT-" + Double.parseDouble(txtamount.getText());
            Runnable sms = new sendsms_way2sms (smsstr);
            new Thread(sms).start();
        }
        
        if (methods.drp == true) {
            //PrintWriter writer = null;
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pf = job.defaultPage();
            //Paper paper = pf.getPaper();
            Paper paper = new Paper();
            //double width = 4d * 72d;
            //double height = 3.9d * 72d;
            //double margin = 0.25d * 72d;
            
            double width = 4 * 72;
            double height = 4 * 72;
            double margin = 0.25 * 72;
            
            paper.setSize(width, height);
            paper.setImageableArea(
                    margin,
                    margin,
                    width - (margin * 2),
                    height - (margin * 2));
            pf.setOrientation(PageFormat.PORTRAIT);
            pf.setPaper(paper);
            
            //System.out.println("Page Height - "+pf.getHeight());
            PrintReceipt pr = new PrintReceipt();
            if ("Marathi".equals(methods.reciept_lang)) {
                pr.data0 = methods.firmname; // + "\n";
                pr.data1 = ""; // " + methods.firmadd + "\n";
                pr.data2 = ""; //      saMpak : " + methods.firmcontactno + "\n";
                if (shiftid == 1) {
                    pr.Shift = "सकाळ";
                } else {
                    pr.Shift = "संध्याकाळ";
                }
                SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                pr.data3 = "दिनांक : " + dateformat.format(dtpdate.getDate()) + " -: " + pr.Shift + "";
                pr.data4 = "उत्पादक : " + txtproducercode.getText() + " - " + txtproducername.getText() + "";
                //String data5;
                if (cmbmilktype.getSelectedIndex() == 0) {
                    pr.data5 = "दुध प्रकार : गाय" + "";
                } else {
                    pr.data5 = "दुध प्रकार : म्हैस" + "";
                }
                pr.data11 = "वजन : " + txtweight.getText();
                pr.data6 = "फॅट : " + txtfat.getText();
                pr.data7 = "सी.एल.आर. : " + txtclr.getText() + "";
                pr.data8 = "एस.एन.एफ. : " + txtsnf.getText() + "";
                pr.data9 = "दर : " + txtrate.getText() + "";
                pr.amt = Double.parseDouble(txtweight.getText()) * Double.parseDouble(txtrate.getText());
                //txtamount.setText("" + );
                pr.data10 = "रक्कम : " + twodf.format(pr.amt);
                if (methods.print_total_amount_in_reciept) {
                    try {
                        pr.data12 = getTotalMilkForCurrentPeriod("M");
                    } catch (SQLException ex) {
                        Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if ("English".equals(methods.reciept_lang)){
                pr.data0 = methods.firmname; // + "\n";
                pr.data1 = ""; // " + methods.firmadd + "\n";
                pr.data2 = ""; //      saMpak : " + methods.firmcontactno + "\n";
                if (shiftid == 1) {
                    pr.Shift = "M";
                } else {
                    pr.Shift = "E";
                }
                SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                pr.data3 = "DATE : " + dateformat.format(dtpdate.getDate()) + " - : " + pr.Shift + "";
                pr.data4 = "CODE : " + txtproducercode.getText() + " - " + txtproducername.getText() + "";
                //String data5;
                if (cmbmilktype.getSelectedIndex() == 0) {
                    pr.data5 = "MILK : COW" + "";
                } else {
                    pr.data5 = "MILK : BUFELLOW" + "";
                }
                pr.data11 = "QTY  : " + txtweight.getText();
                pr.data6 = "FAT  : " + txtfat.getText();
                pr.data7 = "CLR  : " + txtclr.getText() + "";
                pr.data8 = "SNF  : " + txtsnf.getText() + "";
                pr.data9 = "RATE : " + twodf.format(Double.parseDouble(txtrate.getText())) + "";
                pr.amt = Double.parseDouble(txtweight.getText()) * Double.parseDouble(txtrate.getText());
                //txtamount.setText("" + );
                pr.data10 = "AMT  : " + twodf.format(pr.amt);
                if (methods.print_total_amount_in_reciept) {
                    try {
                        pr.data12 = getTotalMilkForCurrentPeriod("E");
                        //System.err.println(pr.data12);
                    } catch (SQLException ex) {
                        Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            job.setPrintable(pr, pf);
            boolean ok = job.printDialog();
            if (ok) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    /* The job did not successfully complete */
                }
            }
            //writer.close();
        }

        clearscreen();

        try {
            txtsampleno.setText("" + getsampleno());
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }

        fatnumber = 0;
        txtproducercode.requestFocus();

        gettotalcollection();   
        getshiftreport();

        this.framemode = 1;
    }//GEN-LAST:event_btnsaveActionPerformed

    private String getTotalMilkForCurrentPeriod(String Lang) throws SQLException {
        
        double totalmilk = 0;
        double totalAmt = 0;
        String return_statement = "";
        String qry = "SELECT Sum(mlkCollection.weight) AS SumOfweight, Sum([mlkCollection]![weight]*[mlkCollection]![rate]) AS totAmt FROM mlkCollection " +
                    "WHERE (((mlkCollection.trn_date)>=#"+this.current_period_from_date+"# "
                + "And (mlkCollection.trn_date)<=#"+this.current_period_to_date+"#) "
                + "AND ((mlkCollection.prod_id)="+txtproducercode1.getText()+"))";
        System.out.println(qry);
        try (PreparedStatement editqry = conn.prepareStatement(qry); ResultSet rsedit = editqry.executeQuery()) {
                if (rsedit.next()) {
                    
                    totalmilk = rsedit.getDouble("SumOfweight");
                    totalAmt = rsedit.getDouble("totAmt");

                } 
        }
        
        if (Lang == "M") {
            return_statement = "एकूण दुध : " + twodf.format(totalmilk) + "-एकूण रक्कम : " + twodf.format(totalAmt);
        } else {
            return_statement = "TOT MILK : " + twodf.format(totalmilk) + "-PAYBLE AMT : " + twodf.format(totalAmt);
        }
        return return_statement;
        
    }
    
    private void dtpdatePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dtpdatePropertyChange
        if (dtpdate.getDate() != null || !dtpdate.getDate().equals("")) {
            try {
                methods.currdate = dtpdate.getDate();
                clearscreen();
                
                Calendar Cal = Calendar.getInstance();
                Calendar Cal1 = Calendar.getInstance();
                
                Cal.set(Calendar.DAY_OF_MONTH, dtpdate.getDate().getDate());
                
                // get the date
                int date_int = Cal.get(Calendar.DAY_OF_MONTH);
                //int curr_year = Cal.get(Calendar.YEAR);
                //int curr_month = Cal.get(Calendar.MONTH);
                //System.out.println("Day of Month is " + date_int);
                int month_days = Cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                // System.out.println("Total Days in month " + month_days);
                //System.err.println("Bill period : " + methods.bill_period);
                // Get the total bill period
                int tot_bill_period = (int)Math.round((double)month_days / methods.bill_period);
                //System.out.println((double)month_days / methods.bill_period);
                //System.out.println("Total Bill Period " + tot_bill_period);
                //tot_bill_period = Math.round(tot_bill_period);
                //System.out.println("Total Bill Period " + tot_bill_period);

                switch ((int)tot_bill_period) {
                    case 1:
                        // Means Monthly Bill
                        // set the format to first of month and todate to last of month
                        //this.current_period_from_date = mdy.format(Cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                        //this.current_period_to_date = mdy.format(Cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        Cal1.set(Calendar.DAY_OF_MONTH, 1);
                        this.current_period_from_date = mdy.format(Cal1.getTime());
                        Cal.set(Calendar.DATE, Cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                        this.current_period_to_date = mdy.format(Cal.getTime());

                        break;
                    case 2:
                        // Means Half monthly Bill 15 days period
                        if (date_int >= 1 && date_int <= 15) {
//                            this.current_period_from_date = mdy.format(Cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                            Cal1.set(Calendar.DAY_OF_MONTH, 1);
                            this.current_period_from_date = mdy.format(Cal1.getTime());
                            Cal1.set(Calendar.DAY_OF_MONTH, 15);
                            this.current_period_to_date = mdy.format(Cal1.getTime());
                        } else {
                            Cal1.set(Calendar.DAY_OF_MONTH, 16);
                            this.current_period_from_date = mdy.format(Cal1.getTime());
                            //this.current_period_to_date = mdy.format(Cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Cal.set(Calendar.DATE, Cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                            this.current_period_to_date = mdy.format(Cal.getTime());
                        }
                        break;
                    case 3:
                        System.out.println("Date int : " + date_int);
                        // Means 10 days bill period
                        if (date_int >= 1 && date_int <= 10) {
//                            this.current_period_from_date = mdy.format(Cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//                            Cal1.set(Calendar.DAY_OF_MONTH, 10);
//                            this.current_period_to_date = mdy.format(Cal1.getTime());
                            Cal1.set(Calendar.DAY_OF_MONTH, 1);
                            this.current_period_from_date = mdy.format(Cal1.getTime());
                            Cal1.set(Calendar.DAY_OF_MONTH, 10);
                            this.current_period_to_date = mdy.format(Cal1.getTime());
                        } else if (date_int >= 11 && date_int <= 20) {
                            Cal1.set(Calendar.DAY_OF_MONTH, 11);
                            this.current_period_from_date = mdy.format(Cal1.getTime());
                            Cal1.set(Calendar.DAY_OF_MONTH, 20);
                            this.current_period_to_date = mdy.format(Cal1.getTime());
                        } else {
                            Cal1.set(Calendar.DAY_OF_MONTH, 21);
                            this.current_period_from_date = mdy.format(Cal1.getTime());
                            //this.current_period_to_date = mdy.format(Cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                            Cal.set(Calendar.DATE, Cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                            this.current_period_to_date = mdy.format(Cal.getTime());
                        }
                        break;
                    default:
                        break;
                }

                System.out.println("Current Period is : " + this.current_period_from_date + " TO " + this.current_period_to_date );
            
                txtsampleno.setText("" + getsampleno());
                gettotalcollection();
                getshiftreport();
            } catch (SQLException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_dtpdatePropertyChange

    private void btnprintrecieptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprintrecieptActionPerformed
        String cond_qry = "mlkCollection.shift_id = " + shiftid + " and mlkCollection.trn_date = #" + mdy.format(dtpdate.getDate()) + "# and mlkCollection.sample_no=" + txtsampleno.getText() + "";
        try {
            methods.displayreport("rptmilkbillreciept", conn, cond_qry);
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
        }
        //generatereciept();
    }//GEN-LAST:event_btnprintrecieptActionPerformed

    private void btnpreventryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpreventryActionPerformed
        if (!txtsampleno.getText().trim().equals("" + 1)) {
            int sampno = Integer.parseInt(txtsampleno.getText());
            sampno--;
            displayforediting(sampno);
        }
    }//GEN-LAST:event_btnpreventryActionPerformed

    private void txtsnfFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnfFocusGained
        txtsnf.setBackground(Color.CYAN);
        txtsnf.selectAll();
    }//GEN-LAST:event_txtsnfFocusGained

    private void txtsnfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnfFocusLost
//        txtsnf.setBackground(Color.WHITE);
//        if (!txtsnf.getText().equals("")) {
//            try {
//                if (!getrate()) {
//                    clearscreen();
//                    txtsampleno.setText("" + getsampleno());
//                    txtproducercode.requestFocus();
//                }
//            } catch (SQLException | ParseException ex) {
//                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }//GEN-LAST:event_txtsnfFocusLost

    private void txtrateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrateFocusGained
        txtrate.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtrateFocusGained

    private void txtrateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrateFocusLost
        txtrate.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtrateFocusLost

    private void txtsaurmilkFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsaurmilkFocusGained
        txtsaurmilk.setBackground(Color.CYAN);
    }//GEN-LAST:event_txtsaurmilkFocusGained

    private void txtsaurmilkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsaurmilkFocusLost
        txtsaurmilk.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtsaurmilkFocusLost

    private void table_shiftreportKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_shiftreportKeyReleased
        ////System.out.println(evt.getKeyCode());

        if (table_shiftreport.getSelectedRow() != -1) {
            int row_sampleno;
            ////System.out.println(table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0));
            if (table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0) != null
                    || !table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0).equals("")) {
                row_sampleno = Integer.parseInt("0" + table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0));

                if (evt.getKeyCode() == 127) {
                    try {
                        int ans = JOptionPane.showConfirmDialog(null, "रेकॉर्ड नक्की काढण्यात यावे?", "Milkman", JOptionPane.YES_NO_OPTION);
                        if (ans == JOptionPane.YES_OPTION) {
                            String del_qry = "DELETE * FROM mlkCollection WHERE trn_date = #" + mdy.format(dtpdate.getDate()) + "# and "
                                    + " shift_id = " + shiftid + " and sample_no = " + row_sampleno + ";";
                            PreparedStatement delete_qry = conn.prepareStatement(del_qry);
                            delete_qry.execute();

//                            getshiftreport();
                            btnclearscreenActionPerformed(null);


                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(milkcollection.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                clearscreen();
            }
        }
    }//GEN-LAST:event_table_shiftreportKeyReleased

    private void table_shiftreportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_shiftreportMouseClicked
        if (table_shiftreport.getSelectedRow() != -1) {
            int row_sampleno;
            row_sampleno = Integer.parseInt("0" + table_shiftreport.getValueAt(table_shiftreport.getSelectedRow(), 0));
            if (row_sampleno != 0) {
                displayforediting(row_sampleno);
            } else {
                clearscreen();
                try {
                    txtsampleno.setText("" + getsampleno());


                } catch (SQLException ex) {
                    Logger.getLogger(milkcollection.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_table_shiftreportMouseClicked

    private void btntotalcollectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntotalcollectionActionPerformed
        String cond_qry;
        if (cmbgroup.getSelectedItem() != "ALL") {
            cond_qry = "mlkCollection.shift_id = " + shiftid + " and mlkCollection.trn_date = #" + mdy.format(dtpdate.getDate()) + "# and producergroupid = " + grid[cmbgroup.getSelectedIndex()]+ "";
        } else {
            cond_qry = "mlkCollection.shift_id = " + shiftid + " and mlkCollection.trn_date = #" + mdy.format(dtpdate.getDate()) + "#";
        }
        try {
            methods.displayreport("shiftreport", conn, cond_qry);
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btntotalcollectionActionPerformed

    private void optmorningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optmorningActionPerformed
        try {
            methods.currdate = dtpdate.getDate();
            shiftid = 1;
            clearscreen();
            txtsampleno.setText("" + getsampleno());
            gettotalcollection();
            getshiftreport();
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_optmorningActionPerformed

    private void opteveningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opteveningActionPerformed
        try {
            methods.currdate = dtpdate.getDate();
            shiftid = 2;
            clearscreen();
            txtsampleno.setText("" + getsampleno());
            gettotalcollection();
            getshiftreport();


        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_opteveningActionPerformed

    private void txtamountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtamountKeyReleased
        if (evt.getKeyCode() == 10) {
        }
    }//GEN-LAST:event_txtamountKeyReleased

    private void txtamountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtamountFocusGained
        txtamount.selectAll();
    }//GEN-LAST:event_txtamountFocusGained

    private void txtfatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfatKeyPressed
    }//GEN-LAST:event_txtfatKeyPressed

    private void txtproducercodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtproducercodeActionPerformed
        if (!txtproducercode.getText().equals("")) {
            int pid = Integer.parseInt(txtproducercode.getText());
            String pname;
            try {
                pname = getprodname(pid);
                txtproducername.setText(pname);
                if (pname.equals("")) {
                    JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!", "Milkman", JOptionPane.ERROR_MESSAGE);
                    txtsampleno.setText("" + getsampleno());
                    txtproducercode.requestFocus();
                    txtproducercode.setText(null);
                } else {
                    // check if allready collection is entered
                    if (conn != null) {
//                        String qry2 = "SELECT mlkCollection.prod_code, mlkCollection.trn_date,"+
//                                " mlkCollection.shift_id" +
//                                " FROM mlkCollection" +
//                                " WHERE (((mlkCollection.prod_code)="+pid+") AND" +
//                                " ((mlkCollection.trn_date)=#"+mdy.format(dtpdate.getDate())+"#) AND"+
//                                " ((mlkCollection.shift_id)="+shiftid+"));";
                        String qry2 = "SELECT mlkCollection.prod_code"
                                + " FROM producer INNER JOIN mlkCollection ON producer.prod_id = mlkCollection.prod_id"
                                + " WHERE (((mlkCollection.prod_code)=" + pid + ") AND"
                                + " ((mlkCollection.trn_date)=#" + mdy.format(dtpdate.getDate()) + "#) AND"
                                + " ((mlkCollection.shift_id)=" + shiftid + ")"
                                + " AND ((producer.producergroupid)=" + grid[cmbgroup.getSelectedIndex()] + "));";
//                        //System.out.println(qry2);

                        PreparedStatement prname = conn.prepareStatement(qry2);
                        ResultSet rspname;
                        rspname = prname.executeQuery();
                        if (rspname.next()) {
                            int ans = JOptionPane.showConfirmDialog(null, "सदर उत्पादकाने पहिले दुध दिले आहे. दुध स्वीकरणे?", "Milkman", JOptionPane.YES_NO_OPTION);
                            if (ans == JOptionPane.YES_OPTION) {
                                txtweight.requestFocus();
                            } else {
                                btnclearscreenActionPerformed(evt);
                            }
                        } else {
                            txtweight.requestFocus();
                        }
                        rspname.close();
                        
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE, -1);
                        //System.out.println("Yesterday's date was "+mdy.format(cal.getTime())); 
                        
                        String qry_edit;
                        qry_edit = "SELECT mlkCollection.sample_no, mlkCollection.prod_code,"
                                + "mlkCollection.mlkType_id, mlkCollection.weight, mlkCollection.fat,"
                                + "mlkCollection.degree, mlkCollection.snf, mlkCollection.rate, mlkCollection.smw, mlkCollection.smr\n"
                                + "FROM mlkCollection "
                                + "WHERE (((mlkCollection.trn_date)=#" + mdy.format(cal.getTime()) + "#)"
                                + " AND ((mlkCollection.shift_id)=" + shiftid + ") AND ((mlkCollection.prod_code)=" + txtproducercode.getText().trim() + "));";
                        System.out.println(qry_edit);
                        PreparedStatement editqry = conn.prepareStatement(qry_edit);
                        ResultSet rsedit = editqry.executeQuery();
                        if (rsedit.next()) {
                            txtweight1.setText(rsedit.getString("weight"));
                            txtfat1.setText(rsedit.getString("fat"));
                            txtclr1.setText(rsedit.getString("degree"));
                            txtsnf1.setText(rsedit.getString("snf"));
                            txtrate1.setText(rsedit.getString("rate"));
                        }
                        rsedit.close();
                    }
//                    txtweight.requestFocus();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "कृपया उत्पादकाचा कोड तपासा..!!", "Milkman", JOptionPane.ERROR_MESSAGE);
                Logger
                        .getLogger(milkcollection.class
                        .getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(
                        null, ex, "Milkman", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtproducercodeActionPerformed

    private void txtweightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtweightActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtweightActionPerformed

    private void txtfatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfatActionPerformed
        if (!txtfat.getText().equals("") || txtfat.getText().trim() == null) {
            //System.out.println(methods.prod_seperate_for_C_M);
            switch (methods.prod_seperate_for_C_M) {
                case "No":
                    double ft = Double.parseDouble(txtfat.getText());
                    if (ft >= methods.min_CMFAT && ft <= methods.max_CMFAT) {
                        cmbmilktype.setSelectedIndex(0);
                    } else if (ft >= methods.min_BMFAT && ft <= methods.max_BMFAT) {
                        cmbmilktype.setSelectedIndex(1);
                    }
                    break;
                case "Yes":
                    //System.out.println(prod_milktype);
                    if (prod_milktype.equals("COW")) {
                        cmbmilktype.setSelectedIndex(0);
                    } else {
                        cmbmilktype.setSelectedIndex(1);
                    }
                    break;
            }
        }
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();        
        //System.out.println(methods.is_clr_not_mandatory);
        if (methods.is_clr_not_mandatory) {
            //System.out.println("in loop");
            txtsnf.requestFocus();
            //manager.focusNextComponent();
            //manager.focusNextComponent(txtsnf);
        } else {
            manager.focusNextComponent();
        }
    }//GEN-LAST:event_txtfatActionPerformed

    private void txtsamplenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsamplenoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsamplenoActionPerformed

    private void txtclrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclrActionPerformed
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtclrActionPerformed

    private void txtamountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtamountActionPerformed
        if (("".equals(txtweight.getText()) && txtweight.getText() == null) && (txtamount.getText() == null && "".equals(txtamount.getText()))) {
        } else {

            double totamt = Double.parseDouble("" + txtamount.getText());
            double totweight = Double.parseDouble("" + txtweight.getText());
            double avgrate = totamt / totweight;
            
            txtrate.setText("" + twodf.format(avgrate));
        }
        KeyboardFocusManager manager;
        manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.focusNextComponent();
    }//GEN-LAST:event_txtamountActionPerformed

    private void txtrateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrateActionPerformed
        double amt = Double.parseDouble(txtweight.getText()) * Double.parseDouble(txtrate.getText());
        txtamount.setText("" + twodf.format(amt));
    }//GEN-LAST:event_txtrateActionPerformed

    private void cmbgroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbgroupActionPerformed
////        if(cmbgroup.getItemCount() > 0 && framestate != 3 && framestate != 2) {
////            int prodid = getprodid();
////            txtproducerid.setText(""+prodid);
////        } else if (framestate == 3){
////            //displayrecord(4);
////        }
    }//GEN-LAST:event_cmbgroupActionPerformed

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

    private void cmbgroupItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbgroupItemStateChanged
        getshiftreport();
    }//GEN-LAST:event_cmbgroupItemStateChanged

    private void optevening1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optevening1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optevening1StateChanged

    private void optevening1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optevening1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optevening1ActionPerformed

    private void txtrate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtrate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrate1ActionPerformed

    private void txtrate1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrate1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrate1FocusGained

    private void txtrate1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtrate1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtrate1FocusLost

    private void txtsnf1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnf1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsnf1FocusGained

    private void txtsnf1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtsnf1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsnf1FocusLost

    private void txtclr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclr1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclr1ActionPerformed

    private void txtclr1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclr1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclr1FocusGained

    private void txtclr1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclr1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclr1FocusLost

    private void txtclr1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclr1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclr1KeyReleased

    private void txtfat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfat1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfat1ActionPerformed

    private void txtfat1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfat1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfat1FocusGained

    private void txtfat1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtfat1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfat1FocusLost

    private void txtfat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfat1KeyPressed

    private void txtfat1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtfat1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfat1KeyReleased

    private void txtweight1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtweight1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtweight1ActionPerformed

    private void txtweight1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweight1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtweight1FocusGained

    private void txtweight1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtweight1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtweight1FocusLost

    private void txtweight1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtweight1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtweight1KeyReleased

    private void cmbmilktypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbmilktypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbmilktypeActionPerformed

    private void txtsnfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsnfActionPerformed

        txtsnf.setBackground(Color.WHITE);
        if (!txtsnf.getText().equals("")) {
            try {
                if (!getrate()) {
                    //System.out.println("rate not get");
                    clearscreen();
                    txtsampleno.setText("" + getsampleno());
                    txtproducercode.requestFocus();
                } else {
                    //System.out.println("rate get");
//                    KeyboardFocusManager manager;
//                    manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//                    manager.focusNextComponent();    
                    btnsave.requestFocus();
                }
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(milkcollection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_txtsnfActionPerformed

    private void btntotalcollection1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntotalcollection1ActionPerformed
        String cond_qry;
        if (cmbgroup.getSelectedItem() != "ALL") {
            cond_qry = "mlkCollection.shift_id = " + shiftid + " and mlkCollection.trn_date = #" + mdy.format(dtpdate.getDate()) + "# and producergroupid = " + grid[cmbgroup.getSelectedIndex()]+ "";
        } else {
            cond_qry = "mlkCollection.shift_id = " + shiftid + " and mlkCollection.trn_date = #" + mdy.format(dtpdate.getDate()) + "#";
        }
        try {
            //methods.displayreport("shiftreport", conn, cond_qry);
            methods.displayreport("shiftreportRecieptSize", conn, cond_qry);
        } catch (SQLException ex) {
            Logger.getLogger(milkcollection.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btntotalcollection1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclearscreen;
    private javax.swing.JButton btnclose;
    private javax.swing.JButton btngetfat;
    private javax.swing.JButton btngetweight;
    private javax.swing.JButton btnpreventry;
    private javax.swing.JButton btnprintreciept;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnsetting;
    private javax.swing.JButton btntotalcollection;
    private javax.swing.JButton btntotalcollection1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbgroup;
    private javax.swing.JComboBox cmbmilktype;
    private com.toedter.calendar.JDateChooser dtpdate;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton optevening;
    private javax.swing.JRadioButton optevening1;
    private javax.swing.JRadioButton optmorning;
    private javax.swing.JTable table_shiftreport;
    private javax.swing.JFormattedTextField txtamount;
    private javax.swing.JFormattedTextField txtavgbrate;
    private javax.swing.JFormattedTextField txtavgcrate;
    private javax.swing.JFormattedTextField txtbamt;
    private javax.swing.JFormattedTextField txtbuffmilkfat;
    private javax.swing.JFormattedTextField txtbuffsnf;
    private javax.swing.JFormattedTextField txtcamt;
    private javax.swing.JFormattedTextField txtclr;
    private javax.swing.JFormattedTextField txtclr1;
    private javax.swing.JFormattedTextField txtcowmilkfat;
    private javax.swing.JFormattedTextField txtcowsnf;
    private javax.swing.JFormattedTextField txtfat;
    private javax.swing.JFormattedTextField txtfat1;
    private javax.swing.JFormattedTextField txtproducercode;
    private javax.swing.JFormattedTextField txtproducercode1;
    private javax.swing.JTextField txtproducername;
    private javax.swing.JFormattedTextField txtrate;
    private javax.swing.JFormattedTextField txtrate1;
    private javax.swing.JFormattedTextField txtsampleno;
    private javax.swing.JFormattedTextField txtsaurmilk;
    private javax.swing.JFormattedTextField txtsaurmilkrate;
    private javax.swing.JFormattedTextField txtsnf;
    private javax.swing.JFormattedTextField txtsnf1;
    private javax.swing.JFormattedTextField txttotalbuffmilk;
    private javax.swing.JFormattedTextField txttotalcowmilk;
    private javax.swing.JFormattedTextField txtweight;
    private javax.swing.JFormattedTextField txtweight1;
    // End of variables declaration//GEN-END:variables
}