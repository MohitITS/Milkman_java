package milkman;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package sms_send;


/**
 *
 * @author Rakesh
 */
public class SMS_SEND {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       SMSClient sc = new SMSClient(1);
       sc.sendMessage("09673134555", "Ltr:1 FAT:3.5 SNF:8.5 RATE:20 AMT:20");
       sc.run();
    }
}