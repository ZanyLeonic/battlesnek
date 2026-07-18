package uk.pilk.snek.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import uk.pilk.snek.Tile;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

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
                grid[tileToGrid(tile)].setImpassable();
            }

            //No need to look at anything else if it's yourself?
            if (snake.getId().equals(self.getId())) {
                continue;
            }
            //Desire to go head to head or not
            if (snake.getLength() >= self.getLength()) {
                safeGridTileUpdate(snake.getHead(), Dir.UP, -5);
                safeGridTileUpdate(snake.getHead(), Dir.RIGHT, -5);
                safeGridTileUpdate(snake.getHead(), Dir.DOWN, -5);
                safeGridTileUpdate(snake.getHead(), Dir.LEFT, -5);
            }
        }
    }

    private static int foodMax = 100;

    private void populateFoodDesire(Snek self) {
        for(Tile tile : food) {
            iterFoodWeight(tile);
        }
    }

    private void recFoodWeight(HashSet<Tile> set, Tile current, int val){
        if(set.contains(current)) {
            return;
        }
        current.modifyWeight(val, foodMax);
        set.add(current);
        Tile temp = getTile(current,Dir.UP);
        if(temp != null) {
            recFoodWeight(set, temp, val-1);
        }
        temp = getTile(current,Dir.RIGHT);
        if(temp != null) {
            recFoodWeight(set, temp, val-1);
        }
        temp = getTile(current,Dir.DOWN);
        if(temp != null) {
            recFoodWeight(set, temp, val-1);
        }
        temp = getTile(current,Dir.LEFT);
        if(temp != null) {
            recFoodWeight(set, temp, val-1);
        }
    }

    private void iterFoodWeight(Tile start){
        HashSet<Tile> set = new HashSet<>();
        PriorityQueue<ThisIsNotAnIntPairRenameItPlease> toDo = new  PriorityQueue<>();
        toDo.add(new ThisIsNotAnIntPairRenameItPlease(start, foodMax));
        Tile temp;
        ThisIsNotAnIntPairRenameItPlease pair;
        while(!toDo.isEmpty()) {
            pair = toDo.poll();
            if(set.contains(pair.getPos())){
                continue;
            }
            pair.getPos().modifyWeight(pair.getVal(), foodMax);
            set.add(pair.getPos());

            temp = getTile(pair.getPos(),Dir.UP);
            if(temp != null) {
                toDo.add(new ThisIsNotAnIntPairRenameItPlease(temp, pair.getVal()-1));
            }
            temp = getTile(pair.getPos(),Dir.RIGHT);
            if(temp != null) {
                toDo.add(new ThisIsNotAnIntPairRenameItPlease(temp, pair.getVal()-1));
            }
            temp = getTile(pair.getPos(),Dir.DOWN);
            if(temp != null) {
                toDo.add(new ThisIsNotAnIntPairRenameItPlease(temp, pair.getVal()-1));
            }
            temp = getTile(pair.getPos(),Dir.LEFT);
            if(temp != null) {
                toDo.add(new ThisIsNotAnIntPairRenameItPlease(temp, pair.getVal()-1));
            }
        }
    }

    /**
     * Always goes left if equal, so maybe find a fix?
     *
     * @param head Head position
     * @return Chosen direction to move
     */
    public Direction returnChosenDirection(Tile head) {
        Direction favoured = null;
        int favourWeight = Integer.MIN_VALUE, headPos = tileToGrid(head), temp;
        Tile considered;

        if (head.getY() != 0) {
            considered = grid[headPos - width];
            if(!considered.isImpassable()) {
                temp = considered.getWeight();
                if (favourWeight < temp) {
                    favourWeight = temp;
                    favoured = Direction.DOWN;
                }
            }
        }

        temp = headPos + 1;
        if (head.getX() != width-1 && temp < grid.length) {
            considered = grid[temp];
            if(!considered.isImpassable()) {
                temp = grid[temp].getWeight();
                if (favourWeight < temp) {
                    favourWeight = temp;
                    favoured = Direction.RIGHT;
                }
            }
        }

        if (head.getY() != height-1) {
            considered = grid[headPos + width];
            if(!considered.isImpassable()) {
                temp = grid[headPos + width].getWeight();
                if (favourWeight < temp) {
                    favourWeight = temp;
                    favoured = Direction.UP;
                }
            }
        }

        temp = headPos - 1;
        if (head.getX() != 0 && temp >= 0) {
            considered = grid[temp];
            if(!considered.isImpassable()) {
                temp = grid[temp].getWeight();
                if (favourWeight < temp) {
                    favoured = Direction.LEFT;
                }
            }
        }

        return favoured;
    }

    private int tileToGrid(Tile tile) {
        return tile.getX() + tile.getY() * width;
    }

    private void safeGridSet(Tile tile, Dir direction, int mod) {
        var toModify = getTile(tile, direction);
        if (toModify != null)
            toModify.setWeight(mod);
    }

    private void safeGridTileUpdate(Tile tile, Dir direction, int mod) {
        safeGridTileUpdate(tile, direction, mod, Integer.MAX_VALUE);
    }

    private void safeGridTileUpdate(Tile tile, Dir direction, int mod, int max) {
        var toModify = getTile(tile, direction);
        if (toModify != null)
            toModify.modifyWeight(mod, max);
    }

    private Tile getTile(Tile tile, Dir direction){
        int temp = tileToGrid(tile) + switch (direction) {
            case UP -> width;
            case DOWN -> -width;
            case LEFT -> -1;
            case RIGHT -> 1;
        };
        //prevent OOB
        if (temp < 0 || temp >= grid.length)
            return null;
        if (direction == Dir.RIGHT && temp / width + 1 == width) {
            return null;
        } else if (direction == Dir.LEFT && temp % width == 0){
            return null;
        }
        return grid[temp];
    }
}
