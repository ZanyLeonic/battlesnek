package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameOver {
    private GameBoard game;
    private int turn;
    private Board board;
    private Snek you;
}
