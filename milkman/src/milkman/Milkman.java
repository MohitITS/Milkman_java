/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
//import com.liquid.LiquidLookAndFeel;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Rakesh
 */
public class Milkman {
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) throws Exception {
        
        InetAddress ip;
        String macid = null;
        String act_key;
        
        try{
            
            //selectdairy sd = new selectdairy();
            //sd.show();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            
            //String motherBoardSerial = methods.getMotherBoardSerial();
            
            // Get default path 
            methods.databasepath = new java.io.File("").getAbsolutePath();
            //System.out.println(methods.databasepath);
            selectdairy sd = new selectdairy();
            //sd.show();
            
//            methods.maintaindatabase = 1;
//            methods.database1name = "milkdata";
//          
//            macid = motherBoardSerial;
//            //System.out.println(macid);
//            
//            // Generate act_key
//            act_key = methods.calculateSecurityHash(macid, "MD2") +
//                    methods.calculateSecurityHash(macid, "MD5") +
//                    methods.calculateSecurityHash(macid, "SHA1");
//            
//            act_key = ""
//                    + act_key.charAt(32)  + act_key.charAt(76)
//                    + act_key.charAt(100) + act_key.charAt(50) + "-"
//                    + act_key.charAt(2)   + act_key.charAt(91)
//                    + act_key.charAt(73)  + act_key.charAt(72) + "-"
//                    + act_key.charAt(98)  + act_key.charAt(47) 
//                    + act_key.charAt(65)  + act_key.charAt(18) + "-"
//                    + act_key.charAt(85)  + act_key.charAt(27)  
//                    + act_key.charAt(53)  + act_key.charAt(102) + "-"
//                    + act_key.charAt(15)  + act_key.charAt(99);
//            
//            String srno = macid;
//            //String srno = act_key;
//            int i = 0;
//            
//            //JOptionPane.showMessageDialog(null, "Serial No : "+srno);
//            Connection con = null;
//            ResultSet rs;
//            String swcopyType = null;
//            String refno = null;
//            String actcode = null;
//            
//            if(con!=null) {
//                con.close();
//            }
//            con = methods.getConnection();
//            String qry = "Select swcopycode, refno, licenceno from config;";
//            PreparedStatement conf;
//            conf = con.prepareStatement(qry);
//            rs = conf.executeQuery();
//            while(rs.next()) {
//                swcopyType = rs.getString("swcopycode");
//                refno = rs.getString("refno");
//                actcode = rs.getString("licenceno");
//            }            
//            rs.close();
//            con.close();
//            
//            // Check copy is for Demo or activated
//            if ("DEMO".equals(swcopyType) && "ABCD-EFGH".equals(refno) && "1234-5678".equals(actcode)) {
//                Boolean demo_exp = false;
//                Boolean values_correct = true;
//                int run_count = 0;
//                Boolean flag = methods.thirdpatyFile("Demo", run_count);
////                System.out.println(flag);
//                if (flag) {
//                    WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3", ""+(run_count+1));
//                } else {
//                    System.err.println("Activation Prob - Demo.");
//                    JOptionPane.showMessageDialog(null, "Activation error. Please contact auther. ( Contact : 9673134555 / 8208079038 )", "Milkman - License", JOptionPane.ERROR_MESSAGE);
//                    System.exit(0);
//                }
//                
//                SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//                // Check registry
//                // Get registration date
//                String value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K1");
//                if (dmy.format(new Date()).compareTo(""+dmy.parse(value)) > 0) {
//                    values_correct = true;
//                } else {
//                    values_correct = false;
//                }
//                ////System.out.println(demo_exp);
//                ////System.out.println(values_correct);
//                
//                // Get software activation type
//                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K2");
//                if ("EM".compareTo(value) == 0) {
//                    values_correct = true;
//                } else {
//                    values_correct = false;
//                }
//                ////System.out.println(demo_exp);
//                ////System.out.println(values_correct);
//                
//                // Get software run count on same system
//                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3");
//                run_count = Integer.parseInt(value);
//                if (Integer.parseInt(value) < 100) {
//                    values_correct = true;
//                } else {
//                    demo_exp = true;
//                    values_correct = false;
//                }
//                ////System.out.println(demo_exp);
//                ////System.out.println(values_correct);
//                
//                // Get demo expiry date
//                value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K4");
//                Date d1 = dmy.parse(dmy.format(new Date()));
//                Date d2 = dmy.parse(value);
//                ////System.out.println((d1).compareTo(d2));
//                if ((d1).compareTo(d2) < 0) {
//                    values_correct = true;
//                } else {
//                    demo_exp = true;
//                    values_correct = false;
//                }
//                ////System.out.println(demo_exp);
//                ////System.out.println(values_correct);
//                
//                if (demo_exp || !values_correct) {
//                    activation act;
//                    act = new activation(srno);
//                    act.show();
//                } else {
////                    Boolean flag = methods.thirdpatyFile("Demo", run_count);
////                    //System.out.println("Flag : " + flag);
////                    if (flag) {
//                        WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3", ""+(run_count+1));
//                        login l;
//                        l = new login();
//                        l.setVisible(true);
//                        
////                    } else {
////                        activation act;
////                        act = new activation(srno);
////                        act.show();
////                    }
//                    
//                }
//
//            } else if ("Activated".equals(swcopyType)) {
//                //System.err.println("Activated");
//                String value = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3");
//                Integer run_count = Integer.parseInt(value);
//                //if (run_count >= 100) {
//                    // Read the third party file and check the activation
//                    Boolean flag = methods.thirdpatyFile("Activated", run_count);
////                    System.out.println(flag);
//                    if (flag) {
//                        WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3", ""+(run_count+1));
//                    } else {
//                        System.err.println("Activation Prob.");
//                        JOptionPane.showMessageDialog(null, "Activation error. Please contact auther. ( Contact : 9673134555 / 8208079038 )", "Milkman - License", JOptionPane.ERROR_MESSAGE);
//                        System.exit(0);
//                    }
//
//                    //get activation code
//                    //ref code = AC25-3C16
////                    String newsrno = "";
////                    for(i=0;i<srno.length();i++){
////                        String chr = srno.substring(i, i+1);
////                        //////System.out.println("Single chr : "+chr);
////                        int asccode = chr.hashCode();
////                        //////System.out.println("Ascii Code : "+asccode);
////                        if (asccode >= 65 && asccode <= 85) {
////                            asccode = asccode + 5;
////                        } else if (asccode == 86) {
////                            asccode = 65;
////                        } else if (asccode == 87) {
////                            asccode = 66;
////                        } else if (asccode == 88) {
////                            asccode = 67;
////                        } else if (asccode == 89) {
////                            asccode = 68;
////                        } else if (asccode == 90) {
////                            asccode = 69;
////                        } else if (asccode >= 48 && asccode <= 52) {
////                            asccode = asccode + 5;
////                        } else if (asccode == 53) {
////                            asccode = 48;
////                        } else if (asccode == 54) {
////                            asccode = 49;
////                        } else if (asccode == 55) {
////                            asccode = 50;
////                        } else if (asccode == 56) {
////                            asccode = 51;
////                        } else if (asccode == 57) {
////                            asccode = 52;
////                        } else if (asccode == 45) {
////                            asccode = 45;
////                        }
////
////                        char newchr = (char)asccode;
////                        newsrno = newsrno + newchr;
////                        //////System.out.println("New chr : "+newchr);
////                    }
//                    //System.out.println("srno  : "+srno);
//                    //System.out.println("refno : "+refno);
//                    //JOptionPane.showMessageDialog(null, "New serial key : "+newsrno);
//                    if (srno == null ? refno == null : srno.equals(refno)) {
//                        System.out.println(refno);
//                        //System.out.println(actcode);
//                        if (actcode == null) {
//                            //JOptionPane.showMessageDialog(null, "Please activate the s/w.");
//                            activation act;
//                            act = new activation(srno);
//                            act.show();
//                            //System.exit(0);
//                            //JOptionPane.showMessageDialog(null, "act code is null");
//                        } else if (actcode.equals(act_key)) {
//
//                            login l;
//                            l = new login();
//                            l.setVisible(true);
//
//        //                    main m;
//        //                    m = new main();
//        //                    m.setVisible(true);   
//
//                            //JOptionPane.showMessageDialog(null, "activated");
//                        } else {
//                            JOptionPane.showMessageDialog(null, "Serial Key does not match. Please contact author");
//                            System.exit(0);
//                        }
//                    } else if (refno == null) {
//                        System.out.println("Ref code does not match.");
//                        activation act;
//                        act = new activation(srno);
//                        act.show();
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Serial Key does not match. Please contact author");
//                        System.exit(0);
//                    }
//////                } else {
//////                    activation act;
//////                    act = new activation(srno);
//////                    act.show();
//////                }
//            } else {
//                activation act;
//                act = new activation(srno);
//                act.show();
//            }
            
        } catch(UnsupportedLookAndFeelException e) { //| IOException e ClassNotFoundException | InstantiationException | IllegalAccessException | 
            JOptionPane.showMessageDialog(null, "Error : " + e.toString());
        }
    }
}