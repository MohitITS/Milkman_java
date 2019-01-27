package milkman;

import java.awt.*;
import java.awt.print.*;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.util.ArrayList;

public class PrintReceipt_bill implements Printable {
    
    //String[] reciept_data;
    ArrayList<String> reciept_data = new ArrayList<String>();
    
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        
//        Font typeface = new Font("Dotrice", Font.PLAIN, 10);
//        g.setFont(typeface);      
//        g.setColor(Color.green);      
//        g.drawString("Page " + (pageIndex+1), 100, 100);      
//        return Printable.PAGE_EXISTS;
        
        if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D)g;
        g2d.translate((int)pf.getImageableX(), (int)pf.getImageableY());
        
        double width = pf.getImageableWidth();
        double height = pf.getImageableHeight();
        
        Font typeface = new Font("Dotrice", Font.PLAIN, 10);
        FontMetrics fm = g2d.getFontMetrics(typeface);
        g2d.setFont(typeface);
        int fontHeight = fm.getHeight();
        int fontDescent = fm.getDescent();
        int curHeight = 30;
        double x = 0; //width - fm.stringWidth(data0);
        double y = fm.getHeight();// + fm.getAscent();
        //g2d.drawString(printdata, (int)x, (int)y);
        for (String reciept_data1 : reciept_data) {
            if (reciept_data1 != null) {
                //g2d.drawGlyphVector(reciept_data1, 0, curHeight);
                g2d.drawString(reciept_data1, 0, curHeight);
                curHeight += fontHeight;
            }
        }
        
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
    
}
