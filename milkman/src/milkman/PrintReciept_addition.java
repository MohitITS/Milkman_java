/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package milkman;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;

/**
 *
 * @author Rakesh
 */
public class PrintReciept_addition implements Printable {
    
    static String printdata = "Test";
    String data;
    String data0 = methods.firmname + "\n";
    String data1 = ""; 
    String data2 = "";
    String Shift = "";
    String data3 = "";
    String data4 = "";
    String data5;
    String data11 = "";
    String data6 = "";
    String data7 = "";
    String data8 = "";
    String data9 = "";
    double amt = 0;
    String data10 = "";
    String data13 = "";
    String data12 = "";
    
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        //g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.translate((int)pf.getImageableX(), (int)pf.getImageableY());
        
        double width = pf.getImageableWidth();
        double height = pf.getImageableHeight();
        
        /* Now we perform our rendering */
        //g.drawString("Test the print dialog!", 1, 1);
        
        //g2d.drawRect(0, 0, (int)pf.getImageableWidth() - 1, (int)pf.getImageableHeight() - 1);
        Font typeface;
        if ("Marathi".equals(methods.reciept_lang)) {
            typeface = new Font("Mangal", Font.PLAIN, 10);
        } else {
            //typeface = new Font("Monospaced", Font.PLAIN, 8);
            //typeface = new Font("Serif Dot Digital-7", Font.PLAIN, 9);
            typeface = new Font("Dotrice", Font.PLAIN, 10);
        }
        FontMetrics fm = g2d.getFontMetrics(typeface);
        //g2d.setFont(g.getFont().deriveFont(10f));
        g2d.setFont(typeface);
        int fontHeight = fm.getHeight()+2;
        int fontDescent = fm.getDescent();
        int curHeight = 30;
        //String text = "top";
        //int baseline = (int) (fm.getMaxAscent() + (height - (fm.getAscent() + fm.getMaxDecent()))/2);
        //g2d.drawString(printdata, 0, fm.getAscent());
        //data = "" + data0 + data3 + data4 + data5 + data11 + data6 + data7 + data8 + data9 + data10;
        //g2d.drawString(data0, 0, fm.getAscent());
        g2d.drawString(data0, 0, curHeight);
        //text = "bottom";
        double x = 0; //width - fm.stringWidth(data0);
        double y = fm.getHeight();// + fm.getAscent();
        //g2d.drawString(printdata, (int)x, (int)y);
        curHeight += fontHeight;
        g2d.drawString(data3, 0, curHeight);
        curHeight += fontHeight;
        g2d.drawString(data4, 0, curHeight);
        curHeight += fontHeight;
        g2d.drawString(data5, 0, curHeight);
        curHeight += fontHeight;
        g2d.drawString(data6, 0, curHeight);
        curHeight += fontHeight;
        g2d.drawString(data7, 0, curHeight);
        curHeight += fontHeight;
//        if (methods.reciept_clr) {
//            g2d.drawString(data7, 0, curHeight);
//            curHeight += fontHeight;
//        }
//        if (methods.reciept_snf) {
//            g2d.drawString(data8, 0, curHeight);
//            curHeight += fontHeight;
//        }
//        g2d.drawString(data9, 0, curHeight);
//        curHeight += fontHeight;
//        g2d.drawString(data10, 0, curHeight);
//        curHeight += fontHeight;
//        if (methods.print_total_amount_in_reciept) {
//            String a[];
//            a = data12.split("-");
//            g2d.drawString(a[0], 0, curHeight);
//            curHeight += fontHeight;
//            g2d.drawString(a[1], 0, curHeight);
//        }
        //g2d.drawString(data4, 0, fm.getDescent()+5);
        
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
}