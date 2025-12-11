import com.codeborne.selenide.Configuration;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.awt.SystemColor.text;

public class SelenideFilesTest {

    @BeforeEach void setup()
    {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
    }

    @Test
    void downloadFileTest() throws Exception {
open("https://github.com/junit-team/junit-examples/blob/main/README.md");
        File downloaded = $("[data-testid=raw-button").download();
        try (InputStream is = new FileInputStream(downloaded);) {
            byte[] fileContent = is.readAllBytes();
            String strContent = new String(fileContent, StandardCharsets.UTF_8);
            Assertions.assertTrue(strContent.contains("More complex setups how to integrate various parts of \"JUnit 5\" including a\n" +
                    "possible migration path for JUnit 3 or 4 based projects."));
        }
//        String dataAsString = FileUtils.readFileToString(downloaded, StandardCharsets.UTF_8);
//        Assertions.assertTrue(dataAsString.contains("More complex setups how to integrate various parts of \"JUnit 5\" including a\n" +
//                "possible migration path for JUnit 3 or 4 based projects."));

    }
    @Test
    void uploadFileTest() {
        open("https://demoqa.com/automation-practice-form");
        $("input[type='file']").uploadFromClasspath("1.png");
    }
}
