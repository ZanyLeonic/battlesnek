package uk.pilk.snek.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.Tile;

import java.util.List;

@Data
@AllArgsConstructor
public class Snek {

    private String id;
    private String name;
    private int health;
    private int head;
    @JsonProperty("body")
    private List<Tile> positions;
    private String latency;
    private String length;
    private String shout;
    private String squad;


}
