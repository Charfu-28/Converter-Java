package converter;

import com.itextpdf.text.*;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class converterTextToPdf {

    public static void main(String[] args) {
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                converterTextToPdf.generatePDFFromText(ruta);
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

    public static void generatePDFFromText(String filename) throws Exception {
        Document pdfDoc = new Document(PageSize.A4);
        PdfWriter.getInstance(pdfDoc, new FileOutputStream("C:/xampp/htdocs/Converter/converter/files/txt.pdf")).setPdfVersion(PdfWriter.PDF_VERSION_1_7);
        pdfDoc.open();

        Font myfont = new Font();
        myfont.setStyle(Font.NORMAL);
        myfont.setSize(11);
        pdfDoc.add(new Paragraph("\n"));

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            Paragraph para = new Paragraph(strLine + "\n", myfont);
            para.setAlignment(Element.ALIGN_JUSTIFIED);
            pdfDoc.add(para);
        }
        pdfDoc.close();
        br.close();
    }

}
