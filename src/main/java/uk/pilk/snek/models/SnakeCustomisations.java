package uk.pilk.snek.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SnakeCustomisations {

    @JsonProperty("color")
    private String colour;
    private String head;
    private String tail;

}
