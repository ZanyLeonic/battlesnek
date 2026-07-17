package uk.pilk.snek;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Tile {

    private final int x,y;
    private int weight = -1;
}
