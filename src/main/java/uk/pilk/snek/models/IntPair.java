package uk.pilk.snek.models;

import lombok.Data;
import org.jspecify.annotations.NonNull;
import uk.pilk.snek.Tile;

@Data
public class IntPair implements Comparable<IntPair>{

    private int val;
    private Tile pos;

    public IntPair(Tile pos, int val){
        this.val = val;
        this.pos = pos;
    }

    @Override
    public int compareTo(@NonNull IntPair intPair) {
        return -Integer.compare(this.val, intPair.val);
    }
}
