
import com.itextpdf.text.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.*;

public class Converter {

    public static void main(String[] args) {
        try {
            //Converter.generatePDFFromHTML("result/prueba.html");
            //Converter.generatePDFFromImage("result/imagen.jpg");
            //Converter.generatePDFFromText("result/texto.txt");
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private static void generatePDFFromHTML(String filename) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("result/pdfHtml/html.pdf"));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(filename));
        document.close();
    }

    private static void generatePDFFromImage(String filename) throws Exception {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("result/pdfImg/imagen.pdf"));
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

    public static void generatePDFFromText(String filename) throws Exception {
        Document pdfDoc = new Document(PageSize.A4);
        PdfWriter.getInstance(pdfDoc, new FileOutputStream("result/pdfText/txt.pdf")).setPdfVersion(PdfWriter.PDF_VERSION_1_7);
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
