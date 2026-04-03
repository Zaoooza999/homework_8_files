package guru.qa.homework;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import guru.qa.homework.model.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Homework {
    private ClassLoader cl = Homework.class.getClassLoader();
    private static final ObjectMapper om = new ObjectMapper();
    @Test void orderJsonContainsCorrectValues() throws Exception {
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("json1.json"))) {
            Order actual = om.readValue(reader, Order.class);
            assertEquals("ORD-2025-0001", actual.getOrderId());
            assertEquals("Maxim Petrov", actual.getCustomer().getName());
            assertEquals("maxim.petrov@example.com", actual.getCustomer().getEmail());
            //items
            assertEquals(2, actual.getItems().size());
            assertEquals(101, actual.getItems().get(0).getProductId());
            assertEquals("Mechanical Keyboard", actual.getItems().get(0).getName());
            assertEquals(1, actual.getItems().get(0).getQuantity());
            assertEquals(7990.50, actual.getItems().get(0).getPrice());
            assertEquals(202, actual.getItems().get(1).getProductId());
            assertEquals("Gaming Mouse", actual.getItems().get(1).getName());
            assertEquals(2, actual.getItems().get(1).getQuantity());
            assertEquals(2590.00, actual.getItems().get(1).getPrice());
            assertEquals(true, actual.getPaid());
            assertEquals("Novosibirsk, Red Avenue 1", actual.getDelivery().getAddress());
            assertEquals("2026-04-10", actual.getDelivery().getDeliveryDate());
        }
    }
    @Test void pdfInZipShouldHaveCertainText() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("zip.zip")))
        {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                if (entry.getName().equals("pdf.pdf")){
                    PDF pdf = new PDF(zis);
                    assertTrue(pdf.text.contains("The JUnit Platform serves"));
                    return;
                }
            }
            Assertions.fail("pdf.pdf not found in zip");
        }
    }
    @Test void xlsInZipShouldHaveCertainValue() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("zip.zip")))
        {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                if (entry.getName().equals("xls.xls")){
                    XLS xls = new XLS(zis);
                    String value = xls.excel.getSheetAt(0).getRow(3).getCell(7).getStringCellValue();
                    System.out.println(value);
                    assertTrue(value.contains("Some other Gift"));
                    return;
                }
            }
            Assertions.fail("xls.xls not found in zip");
        }
    }
    @Test void csvInZipShouldHaveCertainText() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("zip.zip")))
        {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null){
                if (entry.getName().equals("csv.csv")){
                    CSVReader csv = new CSVReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
                    List<String[]> data = csv.readAll();
                    assertEquals(2, data.size());
                    assertThat(data).contains(
                            new String[]{"Max", " Maximov"},
                            new String[]{"Ivan", " Ivanov"}
                    );
                    return;
                }
            }
            Assertions.fail("csv.csv not found in zip");
        }
    }
}
