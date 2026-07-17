package uk.pilk.snek;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Tile {

    private final int x,y;
    private int weight = 0;

    public void setWeight(int weight) {
        if(weight < 0) {
            return;
        }
        this.weight = weight;
    }

    public void modifyWeight(int modification){
        if(weight < 0) {
            return;
        }
        this.weight += modification;
    }
}
