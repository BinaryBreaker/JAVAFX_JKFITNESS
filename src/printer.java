import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.*;
import java.awt.print.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
class ReceiptPrint implements Printable {

    SimpleDateFormat df = new SimpleDateFormat();
    String receiptDetailLine;
    public static final String pspace = "               ";//15-spaces

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        df.applyPattern("dd/MM/yyyy HH:mm:ss");
        String lineDot = "----------------------------------------------------------------------------------";
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        int line = 10;

        Font font = new Font("Lucida Handwriting", Font.BOLD, 10);
        //Font font = new Font("MS Gothic", Font.PLAIN, 10);

        if (pageIndex < 0 || pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        g2d.setFont(font);
        g2d.drawString(String.format("%-25s", "The LunaTech"), 1, line);
        line += 13;
        font = new Font("MS Gothic", Font.PLAIN, 9);
        g2d.setFont(font);

        g2d.drawString(String.format("%-25s", "Islamabad"), 1, line);
        line += 13;
        g2d.drawString(String.format("%-25s", "Powered by Lunatech"), 1, line);
        line += 13;
        g2d.drawString(String.format("%-25s", "-"), 1, line);

        return Printable.PAGE_EXISTS;
    }

}


public class printer {

    private boolean done = false;
    private static PageFormat format = new PageFormat();
    private static Paper paper = new Paper();
    private static PrintRequestAttributeSet aset;

    private static void initPrinter() {
        double paperWidth = 3;//3.25
        double paperHeight = 500.69;//11.69
        double leftMargin = 0.0;//0.12;
        double rightMargin = 0.0;//0.10;
        double topMargin = 0;
        double bottomMargin = 0.01;
        paper.setSize(paperWidth * 200, paperHeight * 200);

        paper.setImageableArea(leftMargin * 200, topMargin * 200,
                (paperWidth - leftMargin - rightMargin) * 200,
                (paperHeight - topMargin - bottomMargin) * 200);

        format.setPaper(paper);

        aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.PORTRAIT);

//testing

    }

    public static void main(String ar[]) {

        initPrinter();


        PrinterJob printerJob = PrinterJob.getPrinterJob();
        Printable printable = new ReceiptPrint();

        format = printerJob.validatePage(format);
        printerJob.setPrintable(printable, format);
        try {
            printerJob.print(aset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}