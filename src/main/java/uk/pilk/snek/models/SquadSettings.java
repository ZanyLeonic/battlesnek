package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SquadSettings {
    private boolean allowBodyCollisions;
    private boolean sharedElimination;
    private boolean sharedHealth;
    private boolean sharedLength;
}
