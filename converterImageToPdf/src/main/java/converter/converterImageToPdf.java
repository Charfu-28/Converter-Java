package converter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class converterImageToPdf {

    public static void main(String[] args) {
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                converterImageToPdf.generatePDFFromImage(ruta);
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

    private static void generatePDFFromImage(String filename) throws Exception {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("C:/xampp/htdocs/Converter/converter/files/image.pdf"));
            doc.open();
            Image image = Image.getInstance(filename);
            doc.add(image);
            System.setProperty("http.agent", "Chrome");
        } catch (DocumentException | IOException e) {
            e.printStackTrace(System.out);
        } finally {
            doc.close();
        }
    }
}
