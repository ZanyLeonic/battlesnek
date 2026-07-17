package uk.pilk.snek;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Tile {

    private final int x,y;

    private int weight = 0;
    private boolean impassable = false;

    public void setImpassable(){
        this.impassable = true;
    }

    public void setWeight(int weight) {
        if(impassable) {
            return;
        }
        this.weight = weight;
    }

    public void modifyWeight(int modification, int max){
        if(impassable || weight > max) {
            return;
        }
        this.weight = Math.min(this.weight + modification, max);
    }

    public void modifyWeight(int modification){
        if(impassable) {
            return;
        }
        this.weight += modification;
    }
}
