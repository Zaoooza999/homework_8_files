package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import guru.qa.model.Glossary;
import guru.qa.model.GlossaryForJackson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilesParsingTest {

    private ClassLoader cl = FilesParsingTest.class.getClassLoader();
    private static final Gson gson = new Gson();
    private static final ObjectMapper om = new ObjectMapper();

    @BeforeAll
    static void setup() {
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void pdfFileParsingTest() throws Exception {
        open("https://docs.junit.org/current/user-guide/");
        File downloaded = $("[href*='_exports/junit-user-guide-6.0.3.pdf#overview']").download();
        PDF pdf = new PDF(downloaded);
        assertEquals("JUnit", pdf.title);
    }

    @Test
    void xlsParsingTest() throws Exception {
        open("https://filesamples.com/formats/xls");
        File downloaded = $("[href='/samples/document/xls/sample2.xls']").download();
        XLS xls = new XLS(downloaded);
        String value = xls.excel.getSheetAt(0).getRow(1).getCell(4).getStringCellValue();
        assertTrue(value.contains("This is a sample description"));
    }

    @Test
    void csvFileParsingTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("example.csv");
             CSVReader csvReader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            List<String[]> data = csvReader.readAll();
            assertEquals(2, data.size());
                    assertThat(data).contains(
                    new String[]{"Max", " Maximov"},
                    new String[]{"Ivan", " Ivanov"}
            );
        }
    }

    @Test
    void zipFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("goodbyedpi.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println("123" + entry.getName() + "123");
            }
        }
    }

    @Test
    void jsonFileParsingTest() throws Exception {
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("glossary.json"))) {
            JsonObject actual = gson.fromJson(reader, JsonObject.class);
            Assertions.assertEquals("example glossary", actual.get("title").getAsString());
            Assertions.assertEquals(234234, actual.get("ID").getAsInt());
            JsonObject inner = actual.get("glossary").getAsJsonObject();
            Assertions.assertEquals("SGML", inner.get("Acronym").getAsString());
            Assertions.assertEquals("SGML", inner.get("SortAs").getAsString());
            Assertions.assertEquals("Standard Generalized Markup Language", inner.get("GlossTerm").getAsString());
        }
    }

    @Test
    void jsonFileParsingImprovedTest() throws Exception {
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("glossary.json"))) {
            Glossary actual = gson.fromJson(reader, Glossary.class);
            Assertions.assertEquals("example glossary", actual.getTitle());
            Assertions.assertEquals(234234, actual.getId());
            Assertions.assertEquals("Standard Generalized Markup Language", actual.getGlossary().getGlossTerm());
            Assertions.assertEquals("SGML", actual.getGlossary().getSortAs());
            Assertions.assertEquals("SGML", actual.getGlossary().getAcronym());
        }
    }
    @Test
    void jsonFileParsingTestWithJackson() throws Exception {
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("glossary.json"))) {
            JsonNode actual = om.readValue(reader, JsonNode.class);
            Assertions.assertEquals("example glossary", actual.get("title").asText());
            Assertions.assertEquals(234234, actual.get("ID").asInt());
            JsonNode inner = actual.get("glossary");
            Assertions.assertEquals("SGML", inner.get("Acronym").asText());
            Assertions.assertEquals("SGML", inner.get("SortAs").asText());
            Assertions.assertEquals("Standard Generalized Markup Language", inner.get("GlossTerm").asText());
        }
    }
    @Test
    void jsonFileParsingImprovedTestWithJackson() throws Exception {
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("glossary.json"))) {
            GlossaryForJackson actual = om.readValue(reader, GlossaryForJackson.class);
            Assertions.assertEquals("example glossary", actual.getTitle());
            Assertions.assertEquals(234234, actual.getId());
            Assertions.assertEquals("Standard Generalized Markup Language", actual.getGlossary().getGlossTerm());
            Assertions.assertEquals("SGML", actual.getGlossary().getSortAs());
            Assertions.assertEquals("SGML", actual.getGlossary().getAcronym());
        }
    }
}