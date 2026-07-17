package uk.pilk.snek.models.ingest;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.models.GameState;
import uk.pilk.snek.models.Snek;

@Data
@AllArgsConstructor
public class MoveSnek {
    private GameState game;
    private int turn;
    private GameState board;
    private Snek you;
}
