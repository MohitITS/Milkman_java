package milkman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class selectdairy extends javax.swing.JFrame {
    
    InetAddress ip;
    String macid = null;
    String act_key;
    
    public selectdairy() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            initComponents();
            
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x=(screenSize.width-getSize().width)/2;
            int y=(screenSize.height-getSize().height)/2;
            setLocation(x,y);

            BufferedReader br = null;

            // Get default path 
            String p;
            p = new java.io.File("").getAbsolutePath();
            //JOptionPane.showMessageDialog(null, ""+p);
            String sCurrentLine;
            String[] parts;
            String part1;
            String part2;
            //String part3;

            br = new BufferedReader(new FileReader(p+"\\milkman.jar.config"));
            while ((sCurrentLine = br.readLine()) != null) {
                parts = sCurrentLine.split("=");
                part1 = parts[0];
                part2 = parts[1];
                //part3 = parts[2];
                
                //System.out.println(sCurrentLine+" part1 : "+part1+" part2 : "+part2);

                switch (part1) {
                    case "database": 
                        methods.maintaindatabase = Integer.parseInt(part2);
                    case "data1": 
                        methods.database1name = part2;
                    case "data2": 
                        methods.database2name = part2;
                    case "data3": 
                        methods.database3name = part2;
                    case "databasepath":
                        methods.databasepath = part2;
                }
            }
            cmbdairy.addItem("Select Dairy");
            switch (methods.maintaindatabase) {
                case 1:
                    cmbdairy.addItem(methods.database1name);
                    cmbdairy.setSelectedItem(methods.database1name);
                    jButton1.doClick();
                    //jButton1ActionPerformed()
                    break;
                case 2:
                    cmbdairy.addItem(methods.database1name);
                    cmbdairy.addItem(methods.database2name);
                    break;
                default:
                    cmbdairy.addItem(methods.database1name);
                    cmbdairy.addItem(methods.database2name);
                    cmbdairy.addItem(methods.database3name);
                    break;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(selectdairy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(selectdairy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(selectdairy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cmbdairy = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Select Dairy");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(113, 168, 168));

        jLabel2.setFont(new java.awt.Font("Mangal", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("स्वागत");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 87, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(13, 13, 13)))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        cmbdairy.setFont(new java.awt.Font("Mangal", 1, 18)); // NOI18N
        cmbdairy.setForeground(new java.awt.Color(255, 0, 51));

        jLabel1.setFont(new java.awt.Font("Mangal", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("कृपया डेअरी निवडा :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbdairy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbdairy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(113, 168, 168));

        jButton1.setFont(new java.awt.Font("Mangal", 1, 14)); // NOI18N
        jButton1.setText("पुढे");
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (cmbdairy.getSelectedIndex() != 0 ) {
            methods.currdatabase = ""+cmbdairy.getSelectedItem();
            
            String value;
            //System.out.println(value);
            value = WinRegistry.readString (
                WinRegistry.HKEY_CURRENT_USER,                             //HKEY
                "Software\\ODBC\\ODBC.INI\\milkdata",           //Key
                "DBQ");
            
            
            
            String setvalue = methods.databasepath+methods.currdatabase+".mdb";
            WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER,
                "Software\\ODBC\\ODBC.INI\\milkdata",
                "DBQ", setvalue);
            value = WinRegistry.readString (
                WinRegistry.HKEY_CURRENT_USER,                             //HKEY
                "Software\\ODBC\\ODBC.INI\\milkdata",           //Key
                "DBQ");
            //System.out.println(value);
            
            //dispose();
            
            
//            // Execute command
//            String command = "cmd /c vol";
//            Runtime rt = Runtime.getRuntime();
//            Process child = rt.exec(command);
//            BufferedReader stdInput = new BufferedReader(new InputStreamReader(child.getInputStream()));
//            String ss = null;
//            String srno = null;
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
//            
//            //JOptionPane.showMessageDialog(null, "Serial No : "+srno);
//            Connection con = null;
//            ResultSet rs;
//            String refno = null;
//            String actcode = null;
//            
//            if(con!=null) {
//                con.close();
//            }
//            con = methods.getConnection();
//            String qry = "Select * from config;";
//            PreparedStatement conf;
//            conf = con.prepareStatement(qry);
//            rs = conf.executeQuery();
//            while(rs.next()) {
//                refno = rs.getString("refno");
//                actcode = rs.getString("licenceno");
//            }            
//            rs.close();
//            con.close();
//            
////            get activation code
////            ref code = AC25-3C16
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
//                    login l;
//                    l = new login();
//                    l.setVisible(true);
//
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

//            login l;
//            l = new login();            
//            l.setVisible(true);

            //methods.maintaindatabase = 1;
            //methods.database1name = "milkdata";
            String motherBoardSerial = methods.getMotherBoardSerial();
            macid = motherBoardSerial;
            //System.out.println(macid);
            
            // Generate act_key
            act_key = methods.calculateSecurityHash(macid, "MD2") +
                    methods.calculateSecurityHash(macid, "MD5") +
                    methods.calculateSecurityHash(macid, "SHA1");
            
            act_key = ""
                    + act_key.charAt(32)  + act_key.charAt(76)
                    + act_key.charAt(100) + act_key.charAt(50) + "-"
                    + act_key.charAt(2)   + act_key.charAt(91)
                    + act_key.charAt(73)  + act_key.charAt(72) + "-"
                    + act_key.charAt(98)  + act_key.charAt(47) 
                    + act_key.charAt(65)  + act_key.charAt(18) + "-"
                    + act_key.charAt(85)  + act_key.charAt(27)  
                    + act_key.charAt(53)  + act_key.charAt(102) + "-"
                    + act_key.charAt(15)  + act_key.charAt(99);
            
            String srno = macid;
            //String srno = act_key;
            int i = 0;
            
            //JOptionPane.showMessageDialog(null, "Serial No : "+srno);
            Connection con = null;
            ResultSet rs;
            String swcopyType = null;
            String refno = null;
            String actcode = null;
            
            if(con!=null) {
                con.close();
            }
            con = methods.getConnection();
            String qry = "Select swcopycode, refno, licenceno from config;";
            PreparedStatement conf;
            conf = con.prepareStatement(qry);
            rs = conf.executeQuery();
            while(rs.next()) {
                swcopyType = rs.getString("swcopycode");
                refno = rs.getString("refno");
                actcode = rs.getString("licenceno");
            }            
            rs.close();
            con.close();
            
            // Check copy is for Demo or activated
            if ("DEMO".equals(swcopyType) && "ABCD-EFGH".equals(refno) && "1234-5678".equals(actcode)) {
                Boolean demo_exp = false;
                Boolean values_correct = true;
                int run_count = 0;
                Boolean flag = methods.thirdpatyFile("Demo", run_count);
//                System.out.println(flag);
                if (flag) {
                    WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3", ""+(run_count+1));
                } else {
                    System.err.println("Activation Prob - Demo.");
                    JOptionPane.showMessageDialog(null, "Activation error. Please contact auther. ( Contact : 9673134555 / 8208079038 )", "Milkman - License", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                
                SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
                // Check registry
                // Get registration date
                //String value;
                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K1");
                if (dmy.format(new Date()).compareTo(""+dmy.parse(value)) > 0) {
                    values_correct = true;
                } else {
                    values_correct = false;
                }
                ////System.out.println(demo_exp);
                ////System.out.println(values_correct);
                
                // Get software activation type
                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K2");
                if ("EM".compareTo(value) == 0) {
                    values_correct = true;
                } else {
                    values_correct = false;
                }
                ////System.out.println(demo_exp);
                ////System.out.println(values_correct);
                
                // Get software run count on same system
                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3");
                run_count = Integer.parseInt(value);
                if (Integer.parseInt(value) < 100) {
                    values_correct = true;
                } else {
                    demo_exp = true;
                    values_correct = false;
                }
                ////System.out.println(demo_exp);
                ////System.out.println(values_correct);
                
                // Get demo expiry date
                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K4");
                Date d1 = dmy.parse(dmy.format(new Date()));
                Date d2 = dmy.parse(value);
                ////System.out.println((d1).compareTo(d2));
                if ((d1).compareTo(d2) < 0) {
                    values_correct = true;
                } else {
                    demo_exp = true;
                    values_correct = false;
                }
                ////System.out.println(demo_exp);
                ////System.out.println(values_correct);
                
                if (demo_exp || !values_correct) {
                    activation act;
                    act = new activation(srno);
                    act.show();
                } else {
//                    Boolean flag = methods.thirdpatyFile("Demo", run_count);
//                    //System.out.println("Flag : " + flag);
//                    if (flag) {
                        WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3", ""+(run_count+1));
                        login l;
                        l = new login();
                        l.setVisible(true);
                        dispose();
//                    } else {
//                        activation act;
//                        act = new activation(srno);
//                        act.show();
//                    }
                    
                }

            } else if ("Activated".equals(swcopyType)) {
                //System.err.println("Activated");
                //String value;
                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3");
                Integer run_count = Integer.parseInt(value);
                //if (run_count >= 100) {
                    // Read the third party file and check the activation
                    Boolean flag = methods.thirdpatyFile("Activated", run_count);
//                    System.out.println(flag);
                    if (flag) {
                        WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3", ""+(run_count+1));
                    } else {
                        System.err.println("Activation Prob.");
                        JOptionPane.showMessageDialog(null, "Activation error. Please contact auther. ( Contact : 9673134555 / 8208079038 )", "Milkman - License", JOptionPane.ERROR_MESSAGE);
                        System.exit(0);
                    }

                    //get activation code
                    //ref code = AC25-3C16
//                    String newsrno = "";
//                    for(i=0;i<srno.length();i++){
//                        String chr = srno.substring(i, i+1);
//                        //////System.out.println("Single chr : "+chr);
//                        int asccode = chr.hashCode();
//                        //////System.out.println("Ascii Code : "+asccode);
//                        if (asccode >= 65 && asccode <= 85) {
//                            asccode = asccode + 5;
//                        } else if (asccode == 86) {
//                            asccode = 65;
//                        } else if (asccode == 87) {
//                            asccode = 66;
//                        } else if (asccode == 88) {
//                            asccode = 67;
//                        } else if (asccode == 89) {
//                            asccode = 68;
//                        } else if (asccode == 90) {
//                            asccode = 69;
//                        } else if (asccode >= 48 && asccode <= 52) {
//                            asccode = asccode + 5;
//                        } else if (asccode == 53) {
//                            asccode = 48;
//                        } else if (asccode == 54) {
//                            asccode = 49;
//                        } else if (asccode == 55) {
//                            asccode = 50;
//                        } else if (asccode == 56) {
//                            asccode = 51;
//                        } else if (asccode == 57) {
//                            asccode = 52;
//                        } else if (asccode == 45) {
//                            asccode = 45;
//                        }
//
//                        char newchr = (char)asccode;
//                        newsrno = newsrno + newchr;
//                        //////System.out.println("New chr : "+newchr);
//                    }
                    //System.out.println("srno  : "+srno);
                    //System.out.println("refno : "+refno);
                    //JOptionPane.showMessageDialog(null, "New serial key : "+newsrno);
                    if (srno == null ? refno == null : srno.equals(refno)) {
                        System.out.println(refno);
                        //System.out.println(actcode);
                        if (actcode == null) {
                            //JOptionPane.showMessageDialog(null, "Please activate the s/w.");
                            activation act;
                            act = new activation(srno);
                            act.show();
                            dispose();
                            //System.exit(0);
                            //JOptionPane.showMessageDialog(null, "act code is null");
                        } else if (actcode.equals(act_key)) {

                            login l;
                            l = new login();
                            l.setVisible(true);
                            dispose();
        //                    main m;
        //                    m = new main();
        //                    m.setVisible(true);   

                            //JOptionPane.showMessageDialog(null, "activated");
                        } else {
                            JOptionPane.showMessageDialog(null, "Serial Key does not match. Please contact author");
                            System.exit(0);
                        }
                    } else if (refno == null) {
                        System.out.println("Ref code does not match.");
                        activation act;
                        act = new activation(srno);
                        act.show();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Serial Key does not match. Please contact author");
                        System.exit(0);
                    }
////                } else {
////                    activation act;
////                    act = new activation(srno);
////                    act.show();
////                }
            } else {
                activation act;
                act = new activation(srno);
                act.show();
                dispose();
            }
            this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please Select Dairy");
            }
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchAlgorithmException | SQLException | ParseException | IOException | InterruptedException ex) {
            Logger.getLogger(selectdairy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbdairy;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
