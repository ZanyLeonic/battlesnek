package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RulesetSettings {
    private int foodSpawnChance;
    private int minimumFood;
    private int hazardDamagePerTurn;
    private RoyaleSettings royale;
    private SquadSettings squad;
}
