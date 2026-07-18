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
    private Tile head;
    @JsonProperty("body")
    private List<Tile> positions;
    private String latency;
    private int length;
    private String shout;
    private String squad;
    @JsonProperty("customizations")
    private SnakeCustomisations customisations;

    public Direction findNext(Board board){
        return board.returnChosenDirection(head);
    }
}
