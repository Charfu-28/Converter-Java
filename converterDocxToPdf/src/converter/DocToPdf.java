package converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import javax.swing.JFileChooser;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocToPdf {

    public static void main(String[] args) {
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                DocToPdf cwoWord = new DocToPdf();
                cwoWord.ConvertToPDF(ruta);
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

    public void ConvertToPDF(String docPath) {
        try {
            //taking input from docx file
            InputStream doc = new FileInputStream(new File(docPath));
            //process for creating pdf started
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File("C:/xampp/htdocs/Converter/converter/files/docx.pdf"));
            PdfConverter.getInstance().convert(document, out, options);
            System.out.println("Archivo covertido con exito.");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
