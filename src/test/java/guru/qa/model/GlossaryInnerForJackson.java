package guru.qa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GlossaryInnerForJackson {
    @JsonProperty("SortAs")
    private String sortAs;
    @JsonProperty("GlossTerm")
    private String glossTerm;
    @JsonProperty("Acronym")
    private String acronym;

    public String getSortAs() {
        return sortAs;
    }

    public String getGlossTerm() {
        return glossTerm;
    }

    public String getAcronym() {
        return acronym;
    }
}
