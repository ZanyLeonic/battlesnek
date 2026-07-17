package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameBoard {
    private String id;
    private String map;
    private long timeout;
    private String source;
    private RuleInfo ruleset;
}
