package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
public class MoveOutput {
    private Direction move;
    private String shout;
}
