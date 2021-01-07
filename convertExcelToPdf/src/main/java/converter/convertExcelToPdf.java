package converter;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import java.util.Iterator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.Scanner;
import javax.swing.JFileChooser;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class convertExcelToPdf {

    public static void main(String[] args) {
        Scanner entrada = null;
        JFileChooser fileChooser = new JFileChooser();
        int valor = fileChooser.showOpenDialog(fileChooser);
        if (valor == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            String ext = "." + FilenameUtils.getExtension(ruta);
            try {
                if (ext.equalsIgnoreCase(".xls")) {
                    converterXlsToPdf(ruta);
                } else if (ext.equalsIgnoreCase(".xlsx")) {
                    converterXlsxToPdf(ruta);
                }
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

    public static void converterXlsToPdf(String sourcepath) throws Exception {
        FileInputStream input_document = new FileInputStream(new File(sourcepath));
        // Read workbook into HSSFWorkbook
        HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
        // Read worksheet into HSSFSheet
        HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
        // Obtengo el número de columnas del archivo
        HSSFRow rowsc = my_worksheet.getRow(0);
        int colNum = rowsc.getLastCellNum();
        // To iterate over the rows        
        Iterator<Row> rowIterator = my_worksheet.iterator();
        //We will create output PDF document objects at this point
        Document iText_xls_2_pdf = new Document();
        PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream("C:/xampp/htdocs/Converter/converter/files/excel.pdf"));
        iText_xls_2_pdf.open();
        //Note: There are ways to make this dynamic in nature, if you want to.
        PdfPTable my_table = new PdfPTable(colNum);
        //We will use the object below to dynamically add new data to the table
        PdfPCell table_cell;
        //Loop through rows.
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); //Fetch CELL                
                switch (cell.getCellType()) { //Identify CELL type
                    //you need to add more code here based on
                    //your requirement / transformations
                    case Cell.CELL_TYPE_STRING:
                        //Push the data from Excel to PDF Cell
                        table_cell = new PdfPCell(new Phrase(cell.getStringCellValue()));
                        //feel free to move the code below to suit to your needs
                        my_table.addCell(table_cell);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        DataFormatter fmt = new DataFormatter();
                        String valueAsSeenInExcel = fmt.formatCellValue(cell);
                        table_cell = new PdfPCell(new Phrase(valueAsSeenInExcel));
                        my_table.addCell(table_cell);
                }
            }

        }
        //Finally add the table to PDF document
        iText_xls_2_pdf.add(my_table);
        iText_xls_2_pdf.close();
        //we created our pdf file..
        input_document.close(); //close xls
        System.out.println("Archivo convertido satisfactoriamente");
    }

    public static void converterXlsxToPdf(String sourcepath) throws Exception {
        FileInputStream input_document = new FileInputStream(new File(sourcepath));
        XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);
        XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
        XSSFRow rowsc = my_worksheet.getRow(0);
        int colNum = rowsc.getLastCellNum();
        Iterator<Row> rowIterator = my_worksheet.iterator();
        Document iText_xls_2_pdf = new Document();
        PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream("C:/xampp/htdocs/Converter/converter/files/excel.pdf"));
        iText_xls_2_pdf.open();
        PdfPTable my_table = new PdfPTable(colNum);
        PdfPCell table_cell;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); //Fetch CELL                
                switch (cell.getCellType()) { //Identify CELL type                  
                    case Cell.CELL_TYPE_STRING:
                        table_cell = new PdfPCell(new Phrase(cell.getStringCellValue()));
                        my_table.addCell(table_cell);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        DataFormatter fmt = new DataFormatter();
                        String valueAsSeenInExcel = fmt.formatCellValue(cell);
                        table_cell = new PdfPCell(new Phrase(valueAsSeenInExcel));
                        my_table.addCell(table_cell);
                }
            }
        }
        iText_xls_2_pdf.add(my_table);
        iText_xls_2_pdf.close();
        input_document.close();
        System.out.println("Archivo convertido satisfactoriamente");
    }
}
