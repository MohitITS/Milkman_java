/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.awt.FontFormatException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.util.Date;
import javax.swing.JFrame;

/**
 * @author Rakesh
 */
public class main extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    private Date date = new Date();
    
    public main() throws Exception {
        //this.date = new Date();
        initComponents();
        
        try {
            
//            // Execute command
//            String command = "cmd /c vol";
//            Runtime rt = Runtime.getRuntime();
//            Process child = rt.exec(command);
//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(child.getInputStream()));
//            String ss = null;
//            String srno = null;
//            String refno = null;
//            String actcode = null;
//            #
//            int i = 0;
//            while ((ss = stdInput.readLine()) != null) {
//                //System.out.println("Line "+ (i+1) +" : "+ss);
//                if (i==1) {
//                    int ln;
//                    ln = ss.length();
//                    srno = ss.substring(ln-9, ln);
//                }
//                i++;
//            }    
            
            
            // Hide the menu
            
            jMenuItem3.setVisible(false);
            mnuMilkSell.setVisible(false);
            jMenuItem12.setVisible(false);
            jMenuItem13.setVisible(false);
            jMenuItem14.setVisible(false);
            jMenuItem15.setVisible(false);
            jMenuItem33.setVisible(false);
            jMenuItem36.setVisible(false);
            jCheckBoxMenuItem1.setVisible(false);
            jMenu9.setVisible(false);
            jSeparator32.setVisible(false);
            jSeparator29.setVisible(false);
            jSeparator23.setVisible(false);
            jSeparator3.setVisible(true);
            
            Connection con = null;
            ResultSet rs;
            
            if(con!=null) {
                con.close();
            }
            con = methods.getConnection();
            String qry = "Select * from config;";
            PreparedStatement conf;
            conf = con.prepareStatement(qry);
            rs = conf.executeQuery();
            while(rs.next()) {
               methods.firmid = rs.getInt("ID");
               methods.firmname = rs.getString("firmname");
               methods.firmadd = rs.getString("address");
               methods.firmcontactno = rs.getString("contatno");
               methods.regpersonname = rs.getString("regpersonname");
               methods.curryearfrom = rs.getString("curryearfrom");
               methods.curryearto = rs.getString("curryearto");
               methods.currdate = date;
               methods.firmregno = rs.getString("firmregno");
               methods.swlang = rs.getString("swlang");
               methods.reportpath = rs.getString("reportspath");
               methods.bankopbal = rs.getInt("bankopbal");
               methods.databasepath= rs.getString("databasepath");
               methods.prod_seperate_for_C_M= rs.getString("prod_seperate_for_C_M");
               methods.ratechart_asper_time= rs.getString("ratechart_asper_time");
               String backimage = rs.getString("backroundimage");
               if (backimage == null || "".equals(backimage)) {
                     
               } else {
                     ImageIcon ii = new ImageIcon(backimage);
                     lbllogo.setIcon(ii);
                     lbllogo.setHorizontalAlignment((int) JFrame.LEFT_ALIGNMENT);
                     lbllogo.setVerticalAlignment((int) JFrame.BOTTOM_ALIGNMENT);
                     lbllogo.setBounds(0, 0, this.desktopPane.getWidth(), this.desktopPane.getHeight());
               }
               //methods.producerdefaultlgrid = rs.getInt("producerdefaultlgrid");
               //methods.milkpurchasedefaultlgrid = rs.getInt("milkpurchasedefaultlgrid");
               //methods.milkselldefaultlgrid = rs.getInt("milkselldefaultlgrid");
               //methods.cashdefaultlgrid = rs.getInt("cashdefaultlgrid");
               //methods.sanghdefaultlgrid = rs.getInt("sanghdefaultid");
               //methods.CSCS_command = rs.getBoolean("CSCS_command");
               
               break;   
            }
            rs.close();

//            String newsrno = "";
//            for(i=0;i<srno.length();i++){
//                String chr = srno.substring(i, i+1);
//                //System.out.println("Single chr : "+chr);
//                int asccode = chr.hashCode();
//                //System.out.println("Ascii Code : "+asccode);
//                if (asccode >= 65 && asccode <= 85) {
//                    asccode = asccode + 5;
//                } else if (asccode == 86) {
//                    asccode = 65;
//                } else if (asccode == 87) {
//                    asccode = 66;
//                } else if (asccode == 88) {
//                    asccode = 67;
//                } else if (asccode == 89) {
//                    asccode = 68;
//                } else if (asccode == 90) {
//                    asccode = 69;
//                } else if (asccode >= 48 && asccode <= 52) {
//                    asccode = asccode + 5;
//                } else if (asccode == 53) {
//                    asccode = 48;
//                } else if (asccode == 54) {
//                    asccode = 49;
//                } else if (asccode == 55) {
//                    asccode = 50;
//                } else if (asccode == 56) {
//                    asccode = 51;
//                } else if (asccode == 57) {
//                    asccode = 52;
//                } else if (asccode == 45) {
//                    asccode = 45;
//                }
//                
//                char newchr = (char)asccode;
//                newsrno = newsrno + newchr;
//                //System.out.println("New chr : "+newchr);
//            }
//            //System.out.println("New serial key : "+newsrno);
//            //JOptionPane.showMessageDialog(null, "New serial key : "+newsrno);
//            if (srno.equals(refno)) {
//                if (actcode == null) {
//                    //JOptionPane.showMessageDialog(null, "Please activate the s/w.");
//                    activation act;
//                    act = new activation(srno);
//                    act.show();
//                    //System.exit(0);
//                    //JOptionPane.showMessageDialog(null, "act code is null");
//                } else if (actcode.equals(newsrno)) {
////                    main m;
////                    m = new main();
////                    m.setVisible(true);   
//                    
//                    //JOptionPane.showMessageDialog(null, "activated");
//                } else {
//                    JOptionPane.showMessageDialog(null, "Serial Key does not match. Please contact author");
//                    System.exit(0);
//                }
//            } else if (refno == null) {
//                
//                activation act;
//                act = new activation(srno);
//                act.show();
//            } else {
//                JOptionPane.showMessageDialog(null, "Serial Key does not match. Please contact author");
//                System.exit(0);
//            }            
            
            qry = "select * from collectionsetting;";
            PreparedStatement collectionsetting = con.prepareStatement(qry);
            rs = collectionsetting.executeQuery();
            while(rs.next()) {
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
                methods.sms_authkey = rs.getString("sms_authkey");
                //methods.comportno = rs.getString("comportno");
                methods.smssendby = rs.getString("smssendby");
                methods.sms_senderid = rs.getString("sms_senderid");
                methods.sms_username = rs.getString("sms_userid");
                methods.sms_password = rs.getString("sms_password");

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
                
                break;
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setTitle(methods.firmname + " - Milkman - Verson 3.5.1");
        this.desktopPane.setBounds(0, 0, this.getWidth(), this.getHeight());
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSeparator34 = new javax.swing.JSeparator();
        desktopPane = new javax.swing.JDesktopPane();
        lbllogo = new javax.swing.JLabel(new ImageIcon("backlogo.jpg") );
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton6 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jSeparator33 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator27 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        jMenuItem19 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        mnuMilkSell = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator37 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator39 = new javax.swing.JPopupMenu.Separator();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator23 = new javax.swing.JPopupMenu.Separator();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jSeparator16 = new javax.swing.JPopupMenu.Separator();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jSeparator25 = new javax.swing.JPopupMenu.Separator();
        jMenuItem36 = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JPopupMenu.Separator();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem25 = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        jMenuItem26 = new javax.swing.JMenuItem();
        jSeparator21 = new javax.swing.JPopupMenu.Separator();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jSeparator22 = new javax.swing.JPopupMenu.Separator();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem44 = new javax.swing.JMenuItem();
        jSeparator38 = new javax.swing.JPopupMenu.Separator();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jSeparator26 = new javax.swing.JPopupMenu.Separator();
        jMenuItem21 = new javax.swing.JMenuItem();
        jSeparator28 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jSeparator32 = new javax.swing.JPopupMenu.Separator();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem37 = new javax.swing.JMenuItem();
        jSeparator30 = new javax.swing.JPopupMenu.Separator();
        jMenuItem38 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jSeparator31 = new javax.swing.JPopupMenu.Separator();
        jMenuItem40 = new javax.swing.JMenuItem();
        jSeparator29 = new javax.swing.JPopupMenu.Separator();
        jMenu4 = new javax.swing.JMenu();
        mnuusermanagement = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mnusetting = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        jMenuItem32 = new javax.swing.JMenuItem();
        jSeparator24 = new javax.swing.JPopupMenu.Separator();
        jMenuItem35 = new javax.swing.JMenuItem();
        jSeparator36 = new javax.swing.JPopupMenu.Separator();
        jMenuItem43 = new javax.swing.JMenuItem();
        jSeparator35 = new javax.swing.JPopupMenu.Separator();
        jMenuItem45 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MilkMan version 3.5");
        setBackground(new java.awt.Color(0, 0, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setExtendedState(MAXIMIZED_BOTH);
        setFont(new java.awt.Font("Mangal", 0, 14)); // NOI18N
        setName("mainframe"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        desktopPane.setBackground(new java.awt.Color(255, 255, 255));
        desktopPane.setBorder(new javax.swing.border.MatteBorder(null));
        desktopPane.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                desktopPaneAncestorResized(evt);
            }
        });

        lbllogo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbllogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/milkman/Milk_River12.jpg"))); // NOI18N
        lbllogo.setToolTipText("Mohit ITS, Nashik. Proprietor Rakesh Pawar");
        lbllogo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lbllogo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        desktopPane.add(lbllogo);
        lbllogo.setBounds(10, 0, 680, 540);
        lbllogo.getAccessibleContext().setAccessibleDescription("Mohit ITS, Nashik");

        jToolBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jToolBar1.setName(""); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 153, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/milkman/om.jpg"))); // NOI18N
        jButton1.setText("संकलन"); // NOI18N
        jButton1.setToolTipText("");
        buttonGroup1.add(jButton1);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setIconTextGap(8);
        jButton1.setName(""); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator1);

        jButton2.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(153, 153, 255));
        jButton2.setText("वितरण");
        buttonGroup1.add(jButton2);
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMaximumSize(new java.awt.Dimension(167, 37));
        jButton2.setMinimumSize(new java.awt.Dimension(167, 37));
        jButton2.setPreferredSize(new java.awt.Dimension(200, 37));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator2);

        jButton6.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(153, 153, 255));
        jButton6.setText("कपात");
        buttonGroup1.add(jButton6);
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(167, 37));
        jButton6.setMinimumSize(new java.awt.Dimension(167, 37));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);
        jToolBar1.add(jSeparator3);

        jButton7.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(153, 153, 255));
        jButton7.setText("उचल / पशुखाद्य");
        buttonGroup1.add(jButton7);
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setMaximumSize(new java.awt.Dimension(167, 37));
        jButton7.setMinimumSize(new java.awt.Dimension(167, 37));
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);
        jToolBar1.add(jSeparator33);

        jButton4.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(153, 153, 255));
        jButton4.setText("उत्पादक बील ");
        buttonGroup1.add(jButton4);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMaximumSize(new java.awt.Dimension(167, 37));
        jButton4.setMinimumSize(new java.awt.Dimension(167, 37));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator27);

        jButton5.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(153, 153, 255));
        jButton5.setText("संघ बील");
        buttonGroup1.add(jButton5);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMaximumSize(new java.awt.Dimension(167, 37));
        jButton5.setMinimumSize(new java.awt.Dimension(167, 37));
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);
        jToolBar1.add(jSeparator4);

        menuBar.setForeground(new java.awt.Color(204, 0, 51));
        menuBar.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        fileMenu.setMnemonic('f');
        fileMenu.setText("   मिल्कमॅन   ");
        fileMenu.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        exitMenuItem.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("बंद करणे");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("   मास्टर्स   ");
        editMenu.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        cutMenuItem.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("उत्पादक");
        cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(cutMenuItem);

        copyMenuItem.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("ग्रुप");
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyMenuItem);
        editMenu.add(jSeparator5);

        jMenu5.setText("रेट चार्ट");
        jMenu5.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem1.setText("फॅट स्लॅब");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        jMenuItem18.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem18.setText("एस. एन. एफ. स्लॅब");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem18);

        jMenuItem41.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem41.setText("सी. एल.आर. स्लॅब");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem41);
        jMenu5.add(jSeparator15);

        jMenuItem19.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem19.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem19.setText("रेट चार्ट");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem19);

        editMenu.add(jMenu5);
        editMenu.add(jSeparator6);

        jMenuItem2.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem2.setText("संघ");
        jMenuItem2.setToolTipText("");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem3.setText("डीलर");
        editMenu.add(jMenuItem3);
        editMenu.add(jSeparator7);

        jMenuItem4.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem4.setText("वाहन");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem5.setText("शहर");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem5);
        editMenu.add(jSeparator8);

        jMenuItem6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem6.setText("बँक");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem6);

        jMenuItem17.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem17.setText("बँक खाते");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem17);
        editMenu.add(jSeparator13);

        jMenuItem12.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem12.setText("खाते");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem12);

        jMenuItem13.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem13.setText("खाते प्रकार");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem13);

        menuBar.add(editMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("   दैनिक व्यवहार   ");
        helpMenu.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        contentMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        contentMenuItem.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("संकलन");
        contentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(contentMenuItem);

        mnuMilkSell.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        mnuMilkSell.setMnemonic('a');
        mnuMilkSell.setText("किरकोळ विक्री");
        mnuMilkSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMilkSellActionPerformed(evt);
            }
        });
        helpMenu.add(mnuMilkSell);
        helpMenu.add(jSeparator11);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem7.setText("वितरण");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem7);
        helpMenu.add(jSeparator12);

        jMenuItem8.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem8.setText("कपात");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem8);
        helpMenu.add(jSeparator37);

        jMenuItem9.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem9.setText("उचल / पशुखाद्य");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem9);
        helpMenu.add(jSeparator14);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem10.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem10.setText("उत्पादक बिल");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem10);
        helpMenu.add(jSeparator39);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem11.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem11.setText("संघ बिल");
        helpMenu.add(jMenuItem11);
        helpMenu.add(jSeparator10);

        jMenuItem14.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItem14.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem14.setText("रोजकीर्द व्यवहार");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem14);

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem15.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem15.setText("नावे");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem15);
        helpMenu.add(jSeparator23);

        jMenuItem33.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8, 0));
        jMenuItem33.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem33.setText("मनी ट्रान्सफर");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem33);

        menuBar.add(helpMenu);

        jMenu1.setText("   रिपोर्टस   ");
        jMenu1.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        jMenu6.setText("रजिस्टर");
        jMenu6.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N

        jMenuItem16.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem16.setText("शिफ्ट रिपोर्ट");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem16);

        jMenuItem24.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem24.setText("डीसप्याच रिपोर्ट");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem24);
        jMenu6.add(jSeparator16);

        jMenuItem20.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem20.setText("कपात रजिस्टर");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem20);

        jMenuItem42.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem42.setText("अतिरिक्त रजिस्टर");
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem42);
        jMenu6.add(jSeparator17);

        jMenuItem22.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem22.setText("उत्पादक रजिस्टर");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem22);

        jMenuItem23.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem23.setText("संघ रजिस्टर");
        jMenu6.add(jMenuItem23);
        jMenu6.add(jSeparator25);

        jMenuItem36.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem36.setText("एम. टी. रजिस्टर");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem36);

        jMenu1.add(jMenu6);
        jMenu1.add(jSeparator20);

        jMenu7.setText("पावती");
        jMenu7.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N

        jMenuItem25.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem25.setText("दुध संकलन पावती");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem25);
        jMenu7.add(jSeparator19);

        jMenuItem26.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem26.setText("दुध डीसप्याच पावती");
        jMenu7.add(jMenuItem26);

        jMenu1.add(jMenu7);
        jMenu1.add(jSeparator21);

        jMenu8.setText("दुध बिल");
        jMenu8.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N

        jMenuItem27.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem27.setText("उत्पादक बिल");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem27);

        jMenuItem28.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem28.setText("संघ बिल");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem28);
        jMenu8.add(jSeparator22);

        jMenuItem29.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem29.setText("पेमेंट रजिस्टर");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem29);

        jMenuItem30.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem30.setText("बँक पेमेंट रजिस्टर (नमुना - १)");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem30);

        jMenuItem44.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem44.setText("बँक पेमेंट रजिस्टर (नमुना - २)");
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem44);
        jMenu8.add(jSeparator38);

        jMenuItem34.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem34.setText("बँक पेमेंट नोटपॅड फाईल");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem34);

        jMenuItem31.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem31.setText("पेमेंट रजिस्टर बँक अकौंट व्यतरिक्त");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem31);

        jMenu1.add(jMenu8);
        jMenu1.add(jSeparator26);

        jMenuItem21.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem21.setText("शेतकरी बचत (उत्पादाकानुसार)");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem21);
        jMenu1.add(jSeparator28);

        jCheckBoxMenuItem1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jCheckBoxMenuItem1.setText("रोजकीर्द");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxMenuItem1);
        jMenu1.add(jSeparator32);

        jMenu9.setText("आर्थिक पत्रके");
        jMenu9.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N

        jMenuItem37.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem37.setText("तेरीज पत्रक");
        jMenu9.add(jMenuItem37);
        jMenu9.add(jSeparator30);

        jMenuItem38.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem38.setText("व्यापारी पत्रक");
        jMenu9.add(jMenuItem38);

        jMenuItem39.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem39.setText("नफा तोटा पत्रक");
        jMenu9.add(jMenuItem39);
        jMenu9.add(jSeparator31);

        jMenuItem40.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem40.setText("ताळेबंद");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem40);

        jMenu1.add(jMenu9);
        jMenu1.add(jSeparator29);

        menuBar.add(jMenu1);

        jMenu4.setText("   युटिलिटी   ");
        jMenu4.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N

        mnuusermanagement.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        mnuusermanagement.setMnemonic('o');
        mnuusermanagement.setText("युजर मॅनेजमेंट");
        mnuusermanagement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuusermanagementActionPerformed(evt);
            }
        });
        jMenu4.add(mnuusermanagement);
        jMenu4.add(jSeparator9);

        mnusetting.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        mnusetting.setMnemonic('a');
        mnusetting.setText("डेअरी सेटिंग");
        mnusetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnusettingActionPerformed(evt);
            }
        });
        jMenu4.add(mnusetting);
        jMenu4.add(jSeparator18);

        jMenuItem32.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem32.setText("डाटा ब्याकअप");
        jMenuItem32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem32ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem32);
        jMenu4.add(jSeparator24);

        jMenuItem35.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem35.setText("संकलन सेटिंग");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem35);
        jMenu4.add(jSeparator36);

        jMenuItem43.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem43.setText("अपडेट रेट्स");
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem43);
        jMenu4.add(jSeparator35);

        jMenuItem45.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jMenuItem45.setText("सॉंफ्टवेअर विषयी");
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem45);

        menuBar.add(jMenu4);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
            .addComponent(desktopPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        milkcollection mc;
        mc = new milkcollection();
        this.desktopPane.add(mc);
        setframe_size(this.desktopPane, mc);
        mc.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void desktopPaneAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_desktopPaneAncestorResized
        // TODO add your handling code here:
        lbllogo.setBounds(0, 0, desktopPane.getWidth(), desktopPane.getHeight());
    }//GEN-LAST:event_desktopPaneAncestorResized

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            milkdispatch md;
            md = new milkdispatch();
            this.desktopPane.add(md);
            setframe_size(desktopPane, md);
            md.show();
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void mnuusermanagementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuusermanagementActionPerformed
        // TODO add your handling code here:
        usermodule um = new usermodule();
        this.desktopPane.add(um);
        um.show();
    }//GEN-LAST:event_mnuusermanagementActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("Dispatch Register");
        this.desktopPane.add(dsr);
        dsr.show();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        producermilkbill pmb;
        pmb = new producermilkbill();
        setframe_size(this.desktopPane, pmb);
        this.desktopPane.add(pmb);
        pmb.show();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuItemActionPerformed
        try {
            // TODO add your handling code here:
            producer p;
            p = new producer();
            this.desktopPane.add(p);
            p.show();
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cutMenuItemActionPerformed

    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
        // TODO add your handling code here:
        producergroup pg;
        pg=new producergroup();
        this.desktopPane.add(pg);
        pg.show();
    }//GEN-LAST:event_copyMenuItemActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        bank b;
        b = new bank();
        this.desktopPane.add(b);
        b.show();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        bankaccount ba;
        ba = new bankaccount();
        this.desktopPane.add(ba);
        ba.show();
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        city c;
        c = new city();
        this.desktopPane.add(c);
        c.show();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        vehical v;
        v = new vehical();
        this.desktopPane.add(v);
        v.show();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        fatslab fs;
        fs = new fatslab();
        this.desktopPane.add(fs);
        fs.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        snfslab ss;
        ss = new snfslab();
        this.desktopPane.add(ss);
        ss.show();
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        ratechart rtc;
        rtc = new ratechart();
        this.desktopPane.add(rtc);
        setframe_size(this.desktopPane, rtc);
        rtc.show();
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void contentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentMenuItemActionPerformed
        // TODO add your handling code here:
        milkcollection mc;
        mc = new milkcollection();
        this.desktopPane.add(mc);
        setframe_size(this.desktopPane, mc);
        mc.show();
    }//GEN-LAST:event_contentMenuItemActionPerformed

    private void mnusettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnusettingActionPerformed
        // TODO add your handling code here:
        firmsetting s;
        s=new firmsetting();
        this.desktopPane.add(s);
        s.show();
    }//GEN-LAST:event_mnusettingActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        try {
            Connection conn = null;
            if(conn!=null) {
                conn.close();
            }
            conn = methods.getConnection();
            methods.displayreport("Producerlist", conn);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        deduction d;
        d = new deduction();
        this.desktopPane.add(d);
        setframe_size(desktopPane, d);
        d.setMaximizable(true);
        
        d.show();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        
        displayreportdatecriteria dsr = new displayreportdatecriteria("Producer Milk Bill - Bank Register (Namuna - 1)");
        this.desktopPane.add(dsr);
        dsr.show(); 

//        try {
//          
//            
//            Connection conn;
//            conn = methods.getConnection();
//            methods.displayreport("bankpaymentreg", conn);
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
//        try {
//            Connection conn;
//            conn = methods.getConnection();
//            methods.displayreport("paymentregister", conn);
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
//        }
        producermilkbill pmb;
        pmb = new producermilkbill();
        setframe_size(this.desktopPane, pmb);
        this.desktopPane.add(pmb);
        pmb.show();        
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        try {
            Connection conn;
            conn = methods.getConnection();
            methods.displayreport("paymentregisterwithoutbankac", conn);
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("Producer Milk Bill");
        this.desktopPane.add(dsr);
        dsr.show();       
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        sangh s;
        s = new sangh();
        this.desktopPane.add(s);
        s.show();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
       producermilkbill pmb;
       pmb = new producermilkbill();
       setframe_size(this.desktopPane, pmb);
       this.desktopPane.add(pmb);
       pmb.show();        
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("Dispatch Register");
        this.desktopPane.add(dsr);
        dsr.show();
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        displayreportsingledate dsr = null;
        try {
            dsr = new displayreportsingledate("Shift Report");
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.desktopPane.add(dsr);
        dsr.show();
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            milkdispatch md;
            md = new milkdispatch();
            this.desktopPane.add(md);
            setframe_size(desktopPane, md);
            md.show();
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        moneytransfer mt;
        mt = new moneytransfer();
        this.desktopPane.add(mt);
        setframe_size(desktopPane, mt);
        mt.show();
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        displayreportsingaledatecriteria drsdc = new displayreportsingaledatecriteria("Bank Excel File");
        this.desktopPane.add(drsdc);
        drsdc.show();
        //        try {
//            Connection conn;
//            double damt = 0;
//            conn = methods.getConnection();
//            String qry = "SELECT Sum(milkCollectionBill.amt_payable) AS SumOfamt_payable " +
//                "FROM producer INNER JOIN milkCollectionBill ON producer.ID = milkCollectionBill.prod_code " +
//                "WHERE (((producer.bankid) Is Not Null)) OR (((producer.bankid)<>''));";
//            PreparedStatement dramt;
//            dramt = conn.prepareStatement(qry);
//            ResultSet rsdramt;
//            rsdramt = dramt.executeQuery();
//            while(rsdramt.next()) {
//                damt = rsdramt.getDouble("SumOfamt_payable");
//            }
//            rsdramt.close();
//            dramt.close();
//            //methods.displayreport("bankpaymentreg_Excel", conn, damt);
//            methods.displayreport("bankpaymentreg_Excel", conn, damt, date);
//            conn.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem32ActionPerformed
        backup bk;
        bk = new backup();
        this.desktopPane.add(bk);
        bk.show();
    }//GEN-LAST:event_jMenuItem32ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        collectionsetting cs;
        cs = new collectionsetting();
        this.desktopPane.add(cs);
        cs.show();
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("MT Register");
        this.desktopPane.add(dsr);
        dsr.show();
    }//GEN-LAST:event_jMenuItem36ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("Bachat Register");
        this.desktopPane.add(dsr);
        dsr.show();
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        accounttype at;
        at = new accounttype();
        this.desktopPane.add(at);
        at.show();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        account acc;
        acc = new account();
        this.desktopPane.add(acc);
        acc.show();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        displayreportsingledate dsr = null;
        try {
            dsr = new displayreportsingledate("reciept");
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.desktopPane.add(dsr);
        dsr.show();
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        jama j = new jama(1);
        this.desktopPane.add(j);
        j.show();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        jama j = new jama(2);
        this.desktopPane.add(j);
        j.show();        
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        displayreportsingledate dsr = null;
        try {
            dsr = new displayreportsingledate("Rojkird");
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.desktopPane.add(dsr);
        dsr.show();        
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
        clrslab ss;
        ss = new clrslab();
        this.desktopPane.add(ss);
        ss.show();
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        addition d;
        d = new addition();
        this.desktopPane.add(d);
        setframe_size(desktopPane, d);
        d.setMaximizable(true);
        
        d.show();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem42ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("Kapat Register");
        this.desktopPane.add(dsr);
        dsr.show(); 
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("Producer Milk Bill - Bank Register (Namuna - 2)");
        this.desktopPane.add(dsr);
        dsr.show(); 
    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void mnuMilkSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMilkSellActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuMilkSellActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jMenuItem8ActionPerformed(evt);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        jMenuItem9ActionPerformed(evt);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem43ActionPerformed
        UpdateRates cs;
        cs = new UpdateRates();
        this.desktopPane.add(cs);
        cs.show();
    }//GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        AboutSoftware cs;
        cs = new AboutSoftware();
        this.desktopPane.add(cs);
        cs.show();
    }//GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        displayreportdatecriteria dsr = new displayreportdatecriteria("Dispatch Register");
        this.desktopPane.add(dsr);
        dsr.show();
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private static void setframe_size(JDesktopPane jdp, JInternalFrame jif) {
        jif.setSize(jdp.getWidth(), jdp.getHeight());
        jif.setLocation(0,0);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new main().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    public javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator16;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator20;
    private javax.swing.JPopupMenu.Separator jSeparator21;
    private javax.swing.JPopupMenu.Separator jSeparator22;
    private javax.swing.JPopupMenu.Separator jSeparator23;
    private javax.swing.JPopupMenu.Separator jSeparator24;
    private javax.swing.JPopupMenu.Separator jSeparator25;
    private javax.swing.JPopupMenu.Separator jSeparator26;
    private javax.swing.JToolBar.Separator jSeparator27;
    private javax.swing.JPopupMenu.Separator jSeparator28;
    private javax.swing.JPopupMenu.Separator jSeparator29;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator30;
    private javax.swing.JPopupMenu.Separator jSeparator31;
    private javax.swing.JPopupMenu.Separator jSeparator32;
    private javax.swing.JToolBar.Separator jSeparator33;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JPopupMenu.Separator jSeparator35;
    private javax.swing.JPopupMenu.Separator jSeparator36;
    private javax.swing.JPopupMenu.Separator jSeparator37;
    private javax.swing.JPopupMenu.Separator jSeparator38;
    private javax.swing.JPopupMenu.Separator jSeparator39;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbllogo;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mnuMilkSell;
    private javax.swing.JMenuItem mnusetting;
    private javax.swing.JMenuItem mnuusermanagement;
    // End of variables declaration//GEN-END:variables
}
