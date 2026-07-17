package uk.pilk.snek.models.ingest;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.models.Board;
import uk.pilk.snek.models.GameBoard;
import uk.pilk.snek.models.Snek;

@Data
@AllArgsConstructor
public class StartSnek {
    private GameBoard game;
    private int turn;
    private Board board;
    private Snek you;
}
