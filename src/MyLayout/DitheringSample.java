package MyLayout;/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

import MyLayout.Model.PrintableData;
import MyLayout.Model.SamplesCommon;
import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.image.*;
import com.github.anastaciocintra.output.PrinterOutputStream;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;

public class DitheringSample {
    PrintService printService;

    public DitheringSample(String printerName) {

        printService = PrinterOutputStream.getPrintServiceByName(printerName);


    }

    public void print(PrintableData printerData) {
        EscPos escpos;
        try {
            /*
             * to print one image we need to have:
             * - one BufferedImage.
             * - one bitonal algorithm to define what and how print on image.
             * - one image wrapper to determine the command set to be used on
             * image printing and how to customize it.
             */

            // creating the EscPosImage, need buffered image and algorithm.
            BufferedImage imageBufferedImage = SamplesCommon.getImage(SamplesCommon.sampleImages.logo);


            // this wrapper uses esc/pos sequence: "GS 'v' '0'"
            RasterBitImageWrapper imageWrapper = new RasterBitImageWrapper();


            escpos = new EscPos(new PrinterOutputStream(printService));

            Bitonal algorithm = new BitonalThreshold();
            EscPosImage escposImage = new EscPosImage(new CoffeeImageImpl(imageBufferedImage), algorithm);
            imageWrapper.setJustification(EscPosConst.Justification.Center);
            escpos.write(imageWrapper, escposImage);
            escpos.feed(2);
            Style title = new Style()
                    .setFontSize(Style.FontSize._2, Style.FontSize._2)
                    .setJustification(EscPosConst.Justification.Center);
            Style branding = new Style()
                    .setFontSize(Style.FontSize._1, Style.FontSize._1)
                    .setJustification(EscPosConst.Justification.Center);
            Style Add = new Style()
                    .setJustification(EscPosConst.Justification.Center);
            escpos.writeLF(title, "Ghauri Town");
            escpos.feed(1);
            escpos.writeLF(Add, "First floor Bin Khurshid Plaza Street 9b ");
            escpos.writeLF(Add, " Phase 5 Ghauri Town Islamabad").feed(1);
            escpos.writeLF(Add, "contact :  (051) 2156440");
            escpos.writeLF("---------------------------------------------").feed(1);
            escpos.writeLF("Member ID        " + printerData.getMemberid())
                    .writeLF("Name             " + printerData.getName())
                    .writeLF("Start Date       " + printerData.getStartDate())
                    .writeLF("End Date         " + printerData.getEndDate())
                    .writeLF("Package          " + printerData.getPackage())
                    .writeLF("Price            " + printerData.getAmount() + " Rs").feed(1)
                    .writeLF("---------------------------------------------")
                    .writeLF(branding, "Note: Your membership is valid till the")
                    .writeLF(branding, " above mentioned date")
                    .writeLF("---------------------------------------------")
                    .feed(1);

            escpos.writeLF(branding, printerData.getCompanyName());
            escpos.writeLF(branding, printerData.getContacts());
            escpos.feed(5);
            escpos.cut(EscPos.CutMode.PART);
            escpos.close();

        } catch (IOException ex) {
            Logger.getLogger(DitheringSample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
