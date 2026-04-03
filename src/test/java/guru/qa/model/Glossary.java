package guru.qa.model;

import com.google.gson.annotations.SerializedName;

public class Glossary {
    private String title;
    @SerializedName("ID")
    private Integer id;
    private GlossaryInner glossary;

    public String getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }

    public GlossaryInner getGlossary() {
        return glossary;
    }
}
