package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameState {
    private String id;
    private Board map;
    private long timeout;
    private String source;
    private RuleInfo ruleset;
}
