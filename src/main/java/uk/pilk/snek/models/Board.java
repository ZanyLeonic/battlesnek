package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.Tile;

import java.util.List;

@Data
@AllArgsConstructor
public class Board {

    private final int height, width;

    private List<Tile> tiles;
    private List<Tile> food;
    private List<Tile> hazards;
    private List<Snek> snakes;

    private Tile[] grid = new Tile[height * width];

    public void createTiled() {
        for (Tile tile : tiles) {
            grid[tile.getX() + tile.getY() * width] = tile;
        }
    }

    /**
     * Always goes left if equal, so maybe find a fix?
     * @param head Head position
     * @return Chosen direction to move
     */
    public String returnChosenDirection(Tile head) {
        String favoured = "";
        int favourWeight = -1, headPos = head.getX() + head.getY()*width,temp;

        temp = headPos-width;
        if(temp > 0){
            temp = grid[temp].getWeight();
            if(favourWeight > temp){
                favourWeight = temp;
                favoured = "up";
            }
        }

        temp = headPos+1;
        if(headPos/width + 1 != width){
            temp = grid[temp].getWeight();
            if(favourWeight > temp){
                favourWeight = temp;
                favoured = "right";
            }
        }

        temp = headPos+width;
        if(temp < grid.length){
            temp = grid[temp].getWeight();
            if(favourWeight > temp){
                favourWeight = temp;
                favoured = "down";
            }
        }

        temp = headPos-1;
        if(temp % width != 0){
            temp = grid[temp].getWeight();
            if(favourWeight > temp){
                favourWeight = temp;
                favoured = "left";
            }
        }

        return favoured;
    }
}
