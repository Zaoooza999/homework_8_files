package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FilesParsingTest {

    @BeforeAll
    static void setup()
    {
        Configuration.browserSize = "1920x1080";
    }

    @Test
    void pdfFileParsingTest() throws Exception {
        open("https://docs.junit.org/current/user-guide/");
        File downloaded = $("[href*='junit-user-guide-6.0.1.pdf']").download();
            PDF pdf = new PDF(downloaded);
        Assertions.assertEquals("...", pdf.author);
    }
}
