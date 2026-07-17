package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveOutput {
    private String move;
    private String shout;
}
