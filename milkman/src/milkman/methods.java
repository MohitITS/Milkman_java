/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import java.util.Date;
import java.util.Scanner;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

//import java.util.Date;
/**
 *
 * @author Rakesh
 */
public class methods {
    
    Connection cn;
    
    static int userid = 1;
    static int usertype = 1;
    
    // firm detail
    static int firmid;
    static String firmname;
    static String firmadd;
    static String firmcontactno;
    static String regpersonname;
    static String curryearfrom;
    static String curryearto;
    static java.util.Date currdate;
    static String firmregno;
    static String swlang;
    static String reportpath;
    static String databasepath;
    static int producerdefaultlgrid;
    static int milkpurchasedefaultlgrid;
    static int milkselldefaultlgrid;
    static int cashdefaultlgrid;
    static int sanghdefaultlgrid;
    
    // rate chart setting
    static boolean collonline;
    static boolean pigmyinerface;
    static boolean drp;
    static boolean deductrebateinbill;
    static double rebateperltr;
    static double CM_Sellrate;
    static double BM_Sellrate;
    static boolean fix_snf;
    static double std_CMSNF;
    static double std_BMSNF;
    static double std_CMFAT;
    static double std_BMFAT;
    static boolean fix_clr;
    static double std_CMCLR;
    static double std_BMCLR;
    static double mor_collectionrateinc;
    static double eve_collectionrateinc;
    static double min_CMFAT;
    static double max_CMFAT;
    static double min_CMSNF;
    static double max_CMSNF;
    static double min_BMFAT;
    static double max_BMFAT;
    static double min_BMSNF;
    static double max_BMSNF;
    static double bankopbal;
    static boolean manualrateentry;
    static int bill_period;
    static int maintaindatabase;
    static String database1name;
    static String database2name;
    static String currdatabase;
    static boolean prodcodegroupwise;
    static boolean prodcodemanual;
    static boolean smsfacilityreq;
    static String sms_authkey;
    static String comportno;
    static String sms_senderid;
    static String sms_username;
    static String sms_password;
    static String smssendby;
    static String ratechartcalcbase_CM;
    static double max_cmclr;
    static double min_cmclr;
    static double max_bmclr;
    static double min_bmclr;
    static String ratechartcalcbase_BM;
    static double clrdiff;
    static boolean CSCS_command = false;
    static String prod_seperate_for_C_M = null;
    static String ratechart_asper_time = null;
    static boolean is_clr_not_mandatory = false;
    static String reciept_lang = "Marathi";
    static boolean reciept_clr = false;
    static boolean reciept_snf = false;
    
    private static Process      cmd;
    private static InputStream  cmdOutput;
    private static PrintStream  cmdInput;
    
    public static boolean deduct_rebate_from_bill = false;
    public static boolean print_total_amount_in_reciept = false;
    
    public static Connection getConnection() throws SQLException
    {
        if (currdatabase != null || !"".equals(currdatabase)) currdatabase = "milkdata";
        String url = ("jdbc:odbc:"+currdatabase); //milkdata
        //String url = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ=";
        String path = new File("").getAbsolutePath();
        ////System.out.println("Direcoty : "+path);
        //url = url + path + "Database\\milkdata.mdb";
        ////System.out.println(url);
        //String url1 = "";
        Connection con = null;
    	try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            //System.out.println("Driver Loaded");
            Properties p = new Properties();
            p.put("charSet", "UTF-8"); 
            p.put("username", "Admin");
            p.put("password", "Milkman@2017");
            ////System.out.println("Properties loaded...");
            //DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=/"+currdatabase+".accdb");
            //url1 = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+ databasepath + "/Database/"+ currdatabase+".accdb";
            con = DriverManager.getConnection(url, p);
            //con = DriverManager.getConnection(url, "Admin", "Milkman@2017");
            //System.out.println("Connected to database...");
           
	}
	catch(ClassNotFoundException e) {
            System.out.println("SQL error: "+e.toString());
        }
        return con;
    }
    
    /**
     * Method for get Windows Machine CPU Serial Number
     * @return 
     */
    public static String getWindowsCPU_SerialNumber() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto",".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            
            String vbs1=
              "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                + "Set colItems = objWMIService.ExecQuery _ \n"
                + "   (\"Select * from Win32_Processor\") \n"
                + "For Each objItem in colItems \n"
                + "    Wscript.Echo objItem.ProcessorId \n"
                + "    exit for  ' do the first cpu only! \n"
                + "Next \n";
              

            fw.write(vbs1);
            fw.close();

            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
               result += line;
            }
            input.close();
        }
        catch(Exception E){
             System.err.println("Windows CPU Exp : "+E.getMessage());
        }
        result = result.trim();
        result = new StringBuilder(result).insert(4, '-').toString();
        result = new StringBuilder(result).insert(9, '-').toString();
        result = new StringBuilder(result).insert(14, '-').toString();
        //result = new StringBuilder(result).insert(4, '-').toString();
        return result;
    }
    
    // get the motherboard id
    public static String getMotherBoardSerial() {
        // wmic command for diskdrive id: wmic DISKDRIVE GET SerialNumber
        // wmic command for cpu id : wmic cpu get ProcessorId
        Process process = null;
        String serial = null;
        String newserial = null;
        String property = null;
        
        try {
            
            process = Runtime.getRuntime().exec("wmic bios get SerialNumber");
            //process = Runtime.getRuntime().exec(command);
            Scanner sc = new Scanner(process.getInputStream());
            if (sc.next().equals("")) {
                property = sc.next();
                serial = sc.next();
            } else {
                serial = "";
            }
            
            //System.out.println(property + ": " + serial);
            process = Runtime.getRuntime().exec("wmic DISKDRIVE get SerialNumber");
            sc = new Scanner(process.getInputStream());
//            if (sc.next().equals("")) {
//                property = sc.next();
//                serial = serial+sc.next();
//            } else {
//                serial = "";
//            }
            property = sc.next();
            serial = serial+sc.next();
            
            //System.out.println(property + ": " + serial);            
            process = Runtime.getRuntime().exec("wmic cpu get ProcessorId");
            sc = new Scanner(process.getInputStream());
            property = sc.next();
            serial = serial+sc.next();
            //System.out.println(property + ": " + serial);            
//            process = Runtime.getRuntime().exec(new String[] { "wmic", "baseboard", "get", "SerialNumber" });
//            sc = new Scanner(process.getInputStream());
//            property = sc.next();
//            serial = sc.next();
//            System.out.println(property + ": " + serial);            
            process.getOutputStream().close();
            serial = serial.replace("-", "");
            int serial_length = serial.length();
            
            //System.out.println("Serial lenght : "+serial.length());
            if (serial_length >= 48) {
                newserial = serial.substring(3, 7) + "-" + serial.substring(15, 19) + "-" + serial.substring(24, 28) + "-" + serial.substring(33, 37) + "-" + serial.substring(44, 48);
            } else {
                newserial = serial.substring(1, 18);
                newserial = new StringBuilder(newserial).insert(4, '-').insert(9, "-").insert(13, "-").insert(17, "-").toString();
            }
        } catch (Exception ex) {
            Logger.getLogger("getMotherBoardSerial").log(Level.SEVERE, null, ex);
            return getWindowsCPU_SerialNumber();
        }
//        Scanner sc = new Scanner(process.getInputStream());
//        String property = sc.next();
//        String serial = sc.next();
//        System.out.println(property + ": " + newserial);
        return newserial;
    }
    
    public void runWndCommand(String cmd) throws IOException, InterruptedException
    {
        cmdInput.println(cmd);
        cmdInput.flush();
    }
    
    // Generate activation key from serial key or mac id
    public static String calculateSecurityHash(String stringInput, String algorithmName) throws java.security.NoSuchAlgorithmException {
        String hexMessageEncode = "";
        byte[] buffer = stringInput.getBytes();
        java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance(algorithmName);
        messageDigest.update(buffer);   
        byte[] messageDigestBytes = messageDigest.digest();
        for (int index=0; index < messageDigestBytes.length ; index ++) {
            int countEncode = messageDigestBytes[index] & 0xff;
            if (Integer.toHexString(countEncode).length() == 1)
                hexMessageEncode = hexMessageEncode + "0";
            hexMessageEncode = hexMessageEncode + Integer.toHexString(countEncode);
        }
        return hexMessageEncode;
    }
    
    // Method to call Jaspeer Report
    public static void displayreport(String reportname, Connection cn) throws SQLException {
        try {
            JasperPrint jasperprint;
            String rname = methods.reportpath+"//"+reportname+".jrxml";
            JasperReport jasperreport; 
            jasperreport = JasperCompileManager.compileReport(rname);
            jasperprint = JasperFillManager.fillReport(jasperreport, null, cn);
            JasperViewer jv;   
            jv = new JasperViewer (jasperprint,false);
            jv.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public static void directprinttoprinter(String reportname, Connection cn) throws SQLException {
        try {
            JasperPrint jasperprint;
            String rname = methods.reportpath+"//"+reportname+".jrxml";
            JasperReport jasperreport; 
            
            jasperreport = JasperCompileManager.compileReport(rname);
            jasperprint = JasperFillManager.fillReport(jasperreport, null, cn);
            JasperPrintManager.printReport(jasperprint, false);

        } catch (JRException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }    

    public static void displayreport(String reportname, Connection cn, String qry) throws SQLException {
        try {
//            //System.out.println(qry);
            Map<String, Object> params;
            params = new HashMap<>();
            params.put("wherecondition", qry);
            
            JasperPrint jasperprint;
            String rname = methods.reportpath+"//"+reportname+".jrxml";
            JasperReport jasperreport; 
            jasperreport = JasperCompileManager.compileReport(rname);
            
            jasperprint = JasperFillManager.fillReport(jasperreport, params, cn);
            JasperViewer jv;            
            jv = new JasperViewer(jasperprint, false);
            jv.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jv.setVisible(true);

            
        } catch (JRException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static void displayreport(String reportname, Connection cn, double dramt, Date dt) throws SQLException {
        try {
            ////System.out.println(qry);
            Map<String, Object> params;
            params = new HashMap<>();
            params.put("parmdramt", dramt);
            params.put("pardate", dt);
            
            JasperPrint jasperprint;
            String rname = methods.reportpath+"//"+reportname+".jrxml";
            JasperReport jasperreport; 
            jasperreport = JasperCompileManager.compileReport(rname);
            jasperprint = JasperFillManager.fillReport(jasperreport, params, cn);
            JOptionPane.showMessageDialog(null, "Ok Done!");
//            JasperViewer jv;            
//            jv = new JasperViewer(jasperprint, false);
//            jv.setExtendedState(JFrame.MAXIMIZED_BOTH);
//            jv.setVisible(true);
            
            //JExcelApiExporter exporterXLS = new JExcelApiExporter();
            
            //JasperExportManager.exportRepor (jasperprint, "E:/bank.xls");
            File outputFile = new File("E:/bank.xls");
            FileOutputStream fos = new FileOutputStream(outputFile);
            JRXlsExporter exporterXLS = new JRXlsExporter();
            JOptionPane.showMessageDialog(null, "Done!");
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperprint);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "E:/bank2.xls");
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, fos);
            exporterXLS.exportReport();
           
//            File f=new File("E:/bank.xls");
//            FileInputStream fin = new FileInputStream(f);
//            //ServletOutputStream outStream;
//            FileOutputStream outStream;
//            // SET THE MIME TYPE.
//            //response.setContentType("application/vnd.ms-excel");
//            // set content dispostion to attachment in with file name.
//            // case the open/save dialog needs to appear.
//            //response.setHeader("Content-Disposition", "attachment;filename="+title+".xls");
//            outStream = new FileOutputStream(f);
//
//            byte[] buffer = new byte[1024];
//            int n = 0;
//            while ((n = fin.read(buffer)) != -1) {
//            outStream.write(buffer, 0, n);
//            //System.out.println(buffer);
//            }
//
//            outStream.flush();
//            fin.close();
//            outStream.close();
            
        } catch (JRException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        } catch (Exception e) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e);
        } 
    }
    
    public static void displayreport(String reportname, Connection cn, String qry, String fdate, String tdate) throws SQLException {
        try {
            System.out.println(qry);
            Map<String, Object> params;
            params = new HashMap<>();
            params.put("wherecondition", qry);
            params.put("fdate", fdate);
            params.put("tdate", tdate);
            
            JasperPrint jasperprint;
            String rname = methods.reportpath+"//"+reportname+".jrxml";
            JasperReport jasperreport; 
            jasperreport = JasperCompileManager.compileReport(rname);
            jasperprint = JasperFillManager.fillReport(jasperreport, params, cn);
            JasperViewer jv;            
            jv = new JasperViewer(jasperprint, false);
            jv.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jv.setVisible(true);
            
        } catch (JRException ex) {
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }    
    
    public static void displayreport_subreport(String reportname, Connection cn, String qry, String fdate, String tdate) throws SQLException {
        try {
            //System.out.println(qry);
            Map<String, Object> params;
            params = new HashMap<>();
            params.put("fdate", fdate);
            params.put("tdate", tdate);
            params.put("ROOT_DIR",methods.reportpath);
            params.put("SUBREPORT_DIR",methods.reportpath);
            //System.out.println("ROOT_DIR :"+methods.reportpath);
            params.put("wherecondition", qry);

            JasperPrint jasperprint;
            String rname = methods.reportpath+"//"+reportname+".jrxml";
            JasperReport jasperreport; 
            jasperreport = JasperCompileManager.compileReport(rname);
            jasperprint = JasperFillManager.fillReport(jasperreport, params, cn);
            JasperViewer jv;            
            jv = new JasperViewer(jasperprint, false);
            jv.setExtendedState(JFrame.MAXIMIZED_BOTH);
            jv.setVisible(true);
            
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(methods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Boolean thirdpatyFile(String checkFor, int count) throws IOException, InterruptedException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String sysdir = System.getenv("LOCALAPPDATA"); // + "\\system32";
        
        //File f = new File("C:\\Users\\Public\\Documents\\AMJ.txt");
        File f = new File(sysdir + "\\AMJ.conf");
        Process p = Runtime.getRuntime().exec("attrib -H " + f.getPath());
        p.waitFor();
        Boolean returnValue = false;
        if(f.exists() && !f.isDirectory()) {
            //System.out.println("conf file exists.");
            if (f.length() == 0) {
                //System.out.println("Config file length is 0.");
                returnValue = false;
            } else {
                // do something
                //System.out.println("Check for - " + checkFor);
                
                if ("Demo".equals(checkFor)) {
                    //System.out.println("Check for demo");
                    String val = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3");
                    Integer val1 = Integer.parseInt(val);
                    // increase count in file
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                            //if(!"".equals(br.readLine())) {
                                ////System.out.println("File is not empty");
                                String sCurrentLine;

                                while ((sCurrentLine = br.readLine()) != null) {
                                        ////System.out.println(sCurrentLine);
                                    String lic = sCurrentLine.substring(0, 1);
                                    Integer l = 0;
                                    Integer run = 0;
                                    ////System.out.println(lic);
                                    switch (lic) {
                                        case "D":
                                            // Ok
//                                            l = sCurrentLine.length();
//                                            run = Integer.parseInt(sCurrentLine.substring(1, l));
//                                            //System.out.println("Run : " + run);
//                                            // Increase count
//                                            if (val1 == run) {
//                                                if (run > 100 ) {
//                                                    ////System.out.println("if");
//                                                    // demo expired
//                                                    returnValue = false;
//                                                } else {
//                                                    ////System.out.println("Else");
//                                                    FileWriter fw = new FileWriter(f);
//                                                    fw.write("D" + (run+1));
//                                                    fw.close();
//                                                    returnValue = true;
//                                                }
//                                            } else {
//                                                returnValue = false;
//                                            }
                                            returnValue = true;
                                            break;
                                            
                                        default:
                                            returnValue = false;
                                            break;
                                    }
                                    ////System.out.println("1 = "+returnValue);   
                                    break;
                                    
                                }
                            //} else {
                            //    //System.out.println("File is empty");
                            //    returnValue = false;
                            //}
                                 
                    } catch (IOException e) {
                            //System.out.println("exceptional error");
                            e.printStackTrace();
                    }
                } else if ("Activated".equals(checkFor)) {
                    String val = WinRegistry.readString(WinRegistry.HKEY_CURRENT_USER, "AMJ0000\\System", "K3");
                    int val1 = Integer.parseInt(val);
                    // increase count in file
                    try (BufferedReader br = new BufferedReader(new FileReader(f))) {

                            //if(!"".equals(br.readLine())) {
                                ////System.out.println("File is not empty");
                                String sCurrentLine;

                                while ((sCurrentLine = br.readLine()) != null) {
                                        ////System.out.println(sCurrentLine);
                                    String lic = sCurrentLine.substring(0, 1);
                                    int l = 0;
                                    int run = 0;
                                    ////System.out.println(lic);
                                    switch (lic) {
                                        case "Z":
//                                            l = sCurrentLine.length();
//                                            run = Integer.parseInt(sCurrentLine.substring(1, l));
//                                            if (val1 == run) {
//                                                ////System.out.println(run);
//                                                // Increase count
//                                                FileWriter fw = new FileWriter(f);
//                                                fw.write("Z" + (run+1));
//                                                fw.close();
//
//                                                // SW is activated
//                                                returnValue = true;
//                                            } else {
//                                                returnValue = false;
//                                            }
                                            returnValue = true;
                                            break;
                                        default:
                                            returnValue = false;
                                            break;
                                    }
                                }
                            //} else {
                            //    //System.out.println("File is empty");
                            //    returnValue = false;
                            //}

                    } catch (IOException e) {
                            //System.out.println("exceptional error");
                            e.printStackTrace();
                    }
                }
            }
        } else {
            //System.out.println("File prob");
            returnValue = false;
        }
        
        p = Runtime.getRuntime().exec("attrib +H " + f.getPath());
        p.waitFor();
        
        ////System.out.println("Method : " + returnValue);
        
        return returnValue;
    }
    
//    public static setHiddenProperty(File file, Boolean show) throws InterruptedException, IOException {
//        if (show) {
//            Process p = Runtime.getRuntime().exec("attrib +H " + file.getPath());
//            p.waitFor();
//        } else {
//            Process p = Runtime.getRuntime().exec("attrib -H " + file.getPath());
//            p.waitFor();
//        }
//    }


}
