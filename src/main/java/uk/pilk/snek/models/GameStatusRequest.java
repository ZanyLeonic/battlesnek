package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStatusRequest {
    private GameState game;
    private int turn;
    private GameState board;
    private Snek you;
}
