package uk.pilk.snek.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SnekInfo {
    @JsonProperty("apiversion")
    private String apiVersion;
    private String author;
    private String color;
    private String head;
    private String tail;
}
