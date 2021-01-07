package converter;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;
import org.apache.commons.io.FilenameUtils;

public class PPTToPDF {

    public static void main(String[] args) {
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            String ext = "." + FilenameUtils.getExtension(ruta);
            try {
                PPTToPDF.convertPPTToPDF(ruta, ext);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            if (entrada != null) {
                entrada.close();
            }
        } else {
            System.out.println("No se ha seleccionado ningún fichero");
        }
    }

    public static void convertPPTToPDF(String sourcepath, String fileType) {
        double zoom = 2;
        AffineTransform at = new AffineTransform();
        at.setToScale(zoom, zoom);
        Document pdfDocument = new Document();
        byte[] barr = new byte[0];
        try (FileInputStream inputStream = new FileInputStream(sourcepath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter pdfWriter = PdfWriter.getInstance(pdfDocument, baos);
            PdfPTable table = new PdfPTable(1);
            pdfWriter.open();
            pdfDocument.open();
            Dimension pgsize = null;
            com.lowagie.text.Image slideImage = null;
            BufferedImage img = null;
            if (fileType.equalsIgnoreCase(".ppt")) {
                SlideShow ppt = new SlideShow(inputStream);
                pgsize = ppt.getPageSize();
                Slide slide[] = ppt.getSlides();
                pdfDocument.setPageSize(new com.lowagie.text.Rectangle((float) pgsize.getWidth(), (float) pgsize.getHeight()));
                pdfWriter.open();
                pdfDocument.open();
                for (int i = 0; i < slide.length; i++) {
                    img = new BufferedImage((int) Math.ceil(pgsize.width * zoom), (int) Math.ceil(pgsize.height * zoom), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    graphics.setTransform(at);

                    graphics.setPaint(Color.white);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                    slide[i].draw(graphics);
                    graphics.getPaint();
                    slideImage = com.lowagie.text.Image.getInstance(img, null);
                    table.addCell(new PdfPCell(slideImage, true));
                }
            }
            if (fileType.equalsIgnoreCase(".pptx")) {
                XMLSlideShow ppt = new XMLSlideShow(inputStream);
                pgsize = ppt.getPageSize();
                XSLFSlide[] slide = ppt.getSlides();
                pdfDocument.setPageSize(new com.lowagie.text.Rectangle((float) pgsize.getWidth(), (float) pgsize.getHeight()));
                pdfWriter.open();
                pdfDocument.open();
                for (XSLFSlide slid : slide) {
                    img = new BufferedImage((int) Math.ceil(pgsize.width * zoom), (int) Math.ceil(pgsize.height * zoom), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = img.createGraphics();
                    graphics.setTransform(at);

                    graphics.setPaint(Color.white);
                    graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                    slid.draw(graphics);
                    graphics.getPaint();
                    slideImage = com.lowagie.text.Image.getInstance(img, null);
                    table.addCell(new PdfPCell(slideImage, true));
                }
            }
            pdfDocument.add(table);
            pdfDocument.close();
            barr = baos.toByteArray();
            pdfWriter.close();
            System.out.println("Archivo convertido satisfactoriamente");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        try (FileOutputStream outputStream = new FileOutputStream(new File("C:/xampp/htdocs/Converter/converter/files/ppt(x).pdf"))) {
            outputStream.write(barr);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
