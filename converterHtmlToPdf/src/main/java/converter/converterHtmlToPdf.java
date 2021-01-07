package converter;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class converterHtmlToPdf {

    public static void main(String[] args) {
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                converterHtmlToPdf.generatePDFFromHTML(ruta);
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

    private static void generatePDFFromHTML(String filename) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/xampp/htdocs/Converter/converter/files/html.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
        document.close();
    }

}
