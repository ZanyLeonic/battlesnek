package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.Tile;

import java.util.List;

@Data
@AllArgsConstructor
public class Board {

    private enum Dir {
        UP, RIGHT, DOWN, LEFT
    }

    private final int height, width;

    private List<Tile> food;
    private List<Tile> hazards;
    private List<Snek> snakes;

    private Tile[] grid;

    public void createTiled() {
        grid = new Tile[height * width];
        for(int pos = 0; pos < height * width; pos++) {
            grid[pos] = new Tile(pos%width,pos/width);
        }
        /*for (Tile tile : tiles) {
            grid[tileToGrid(tile)] = tile;
        }*/
    }

    public void populateDesires(Snek self) {
        populateSnakes(self);
        populateFoodDesire(self);
    }

    private void populateSnakes(Snek self) {
        for (Snek snake : snakes) {
            //Remove desire of crashing
            for (Tile tile : snake.getPositions()) {
                grid[tileToGrid(tile)].setWeight(-1);
            }

            //No need to look at anything else if it's yourself?
            if (snake.getId().equals(self.getId())) {
                continue;
            }
            //Desire to go head to head or not
            if (snake.getLength() >= self.getLength()) {
                safeGridSet(snake.getHead(), Dir.UP, -2);
                safeGridSet(snake.getHead(), Dir.RIGHT, -3);
                safeGridSet(snake.getHead(), Dir.DOWN, -4);
                safeGridSet(snake.getHead(), Dir.LEFT, -5);
            }
        }
    }

    private void populateFoodDesire(Snek self) {

    }

    /**
     * Always goes left if equal, so maybe find a fix?
     *
     * @param head Head position
     * @return Chosen direction to move
     */
    public String returnChosenDirection(Tile head) {
        String favoured = "error";
        int favourWeight = -1, headPos = tileToGrid(head), temp;

        if (head.getY() != 0) {
            temp = grid[headPos - width].getWeight();
            if (favourWeight < temp) {
                favourWeight = temp;
                favoured = "down";
            }
        }

        temp = headPos + 1;
        if (head.getX()-1 != width && temp < grid.length) {
            temp = grid[temp].getWeight();
            if (favourWeight < temp) {
                favourWeight = temp;
                favoured = "right";
            }
        }

        if (head.getY() != height) {
            temp = grid[headPos + width].getWeight();
            if (favourWeight < temp) {
                favourWeight = temp;
                favoured = "up";
            }
        }

        temp = headPos - 1;
        if (head.getX() != 0 && temp >= 0) {
            temp = grid[temp].getWeight();
            if (favourWeight < temp) {
                favoured = "left";
            }
        }

        return favoured;
    }

    private int tileToGrid(Tile tile) {
        return tile.getX() + tile.getY() * width;
    }

    private void safeGridSet(Tile tile, Dir direction, int mod) {
        int temp;
        switch (direction) {
            case UP:
                temp = tileToGrid(tile) + width;
                if (temp >= grid.length) {
                    return;
                }
                return;
            case RIGHT:
                temp = tileToGrid(tile) + 1;
                if (temp / width + 1 == width || temp >= grid.length) {
                    return;
                }
                break;
            case DOWN:
                temp = tileToGrid(tile) - width;
                if (temp <= 0) {
                    return;
                }
                break;
            case LEFT:
                temp = tileToGrid(tile) - 1;
                if (temp % width == 0 || temp <= 0) {
                    return;
                }
                break;
            default:
                return;

        }
        grid[temp].setWeight(mod);
    }

    private void safeGridModify(Tile tile, Dir direction, int mod) {
        int temp;
        switch (direction) {
            case UP:
                temp = tileToGrid(tile) + width;
                if (temp >= grid.length) {
                    return;
                }
                return;
            case RIGHT:
                temp = tileToGrid(tile) + 1;
                if (temp / width + 1 == width || temp >= grid.length) {
                    return;
                }
                break;
            case DOWN:
                temp = tileToGrid(tile) - width;
                if (temp < 0) {
                    return;
                }
                break;
            case LEFT:
                temp = tileToGrid(tile) - 1;
                if (temp % width == 0 || temp < 0) {
                    return;
                }
                break;
            default:
                return;

        }
        grid[temp].modifyWeight(mod);
    }
}
