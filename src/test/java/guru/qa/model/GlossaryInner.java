package guru.qa.model;

import com.google.gson.annotations.SerializedName;

public class GlossaryInner {
    @SerializedName("SortAs")
    private String sortAs;
    @SerializedName("GlossTerm")
    private String glossTerm;
    @SerializedName("Acronym")
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
