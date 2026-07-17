package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.Tile;

import java.util.List;

@Data
@AllArgsConstructor
public class Board {

    private final int height, width;

    private List<Tile> tiles;
    private List<Tile> food;
    private List<Tile> hazards;
    private List<Snek> snakes;
}
