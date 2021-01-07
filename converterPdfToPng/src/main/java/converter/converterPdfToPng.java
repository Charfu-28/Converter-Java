package converter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class converterPdfToPng {

    public static void main(String[] args) {

        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                converterPdfToPng(ruta);
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

    public static void converterPdfToPng(String sourceDir) {
        try {
            String destinationDir = "C:/xampp/htdocs/Converter/converter/files/"; // converted images from pdf document are saved here
            File sourceFile = new File(sourceDir);
            File destinationFile = new File(destinationDir);
            if (!destinationFile.exists()) {
                destinationFile.mkdir();
                System.out.println("Carpeta creada -> " + destinationFile.getAbsolutePath());
            }
            if (sourceFile.exists()) {
                System.out.println("Imagenes copiadas a la carpeta: " + destinationFile.getName());
                PDDocument document = PDDocument.load(sourceDir);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                System.out.println("Total archivos a convertir -> " + list.size());
                String fileName = sourceFile.getName().replace(".pdf", "");
                int pageNumber = 1;
                for (PDPage page : list) {
                    BufferedImage image = page.convertToImage();
                    File outputfile = new File(destinationDir + fileName + "_" + pageNumber + ".png");
                    System.out.println("Imagen creada -> " + outputfile.getName());
                    ImageIO.write(image, "png", outputfile);
                    pageNumber++;
                }
                document.close();
            } else {
                System.err.println(sourceFile.getName() + "El archivo seleccionado no existe.");
            }

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
