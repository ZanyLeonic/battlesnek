package uk.pilk.snek.models;

import lombok.Data;
import org.jspecify.annotations.NonNull;
import uk.pilk.snek.Tile;

@Data
public class ThisIsNotAnIntPairRenameItPlease implements Comparable<ThisIsNotAnIntPairRenameItPlease>{

    private int val;
    private Tile pos;

    public ThisIsNotAnIntPairRenameItPlease(Tile pos, int val){
        this.val = val;
        this.pos = pos;
    }

    @Override
    public int compareTo(@NonNull ThisIsNotAnIntPairRenameItPlease intPair) {
        return Integer.compare(this.val, intPair.val);
    }
}
