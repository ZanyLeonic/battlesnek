package uk.pilk.snek.models;

import uk.pilk.snek.Tile;

public class Board {

    private int height, width;

    private Tile[] tiles;
    private int[] food;

    public Board(int x, int y) {
        tiles = new Tile[x * y];
    }

    /**
     * Convert coordinates {x, y} to board pos
     * @param x
     * @param y
     * @return board array pos
     */
    private int pointToBoard(int x, int y) {
        return x + y * width;
    }

    /**
     * Don't call unless you NEED this
     * @param pos board pos
     * @return Converted to coordinates {x, y}
     */
    private int[] boardToPoint(int pos) {
        return new int[]{pos % width, pos / width};
    }

}
