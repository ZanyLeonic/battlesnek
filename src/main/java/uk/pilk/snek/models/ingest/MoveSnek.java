package uk.pilk.snek.models.ingest;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.models.GameBoard;
import uk.pilk.snek.models.Snek;

@Data
@AllArgsConstructor
public class MoveSnek {
    private GameBoard game;
    private int turn;
    private GameBoard board;
    private Snek you;
}
