package guru.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GlossaryForJackson {
    private String title;
    @JsonProperty("ID")
    private Integer id;
    private GlossaryInnerForJackson glossary;
    public String getTitle() {
        return title;
    }
    public int getId() {
        return id;
    }
    public GlossaryInnerForJackson getGlossary() {
        return glossary;
    }
}
