package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Snek {

    private String id;
    private String name;
    private int health;
    private int head;
    private int[] positions;

}
