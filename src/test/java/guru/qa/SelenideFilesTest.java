package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideFilesTest {

    @BeforeEach void setup()
    {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
    }
    @BeforeAll
    static void setUp() {
        Configuration.fileDownload = FileDownloadMode.FOLDER;
    }

    @Test
    void downloadFileTest() throws Exception {
        open("https://github.com/junit-team/junit-framework/blob/main/README.md");
        File downloaded = $("[data-testid='download-raw-button']").download();
        try (InputStream is = new FileInputStream(downloaded))
        {
            byte[] fileContent = is.readAllBytes();
            String strContent = new String(fileContent, StandardCharsets.UTF_8);
            Assertions.assertTrue(strContent.contains("Contributions to JUnit are both welcomed and appreciated."));
        }
//        String dataAsString = FileUtils.readFileToString(downloaded, StandardCharsets.UTF_8);
//        Assertions.assertTrue(dataAsString.contains("Contributions to JUnit are both welcomed and appreciated."));

    }
    @Test
    void uploadFileTest() {
        open("https://demoqa.com/automation-practice-form");
        $("input[type='file']").uploadFromClasspath("1.png");
    }
}
