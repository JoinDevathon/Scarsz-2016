package org.devathon.contest2016.old;

import org.bukkit.block.BlockFace;

import java.util.ArrayList;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static ArrayList<Direction> parse(String toParse) {
        ArrayList<Direction> foundDirections = new ArrayList<>();
        for (char c : toParse.toCharArray()) {
            switch (String.valueOf(c).toLowerCase()) {
                case "^":
                    foundDirections.add(Direction.UP);
                    break;
                case "v":
                    foundDirections.add(Direction.DOWN);
                    break;
                case "<":
                    foundDirections.add(Direction.LEFT);
                    break;
                case ">":
                    foundDirections.add(Direction.RIGHT);
                    break;
            }
        }
        return foundDirections;
    }

    @Override
    public String toString() {
        return "Direction{" + this.name().toLowerCase() + "}";
    }

    public static BlockFace getBlockFaceFromDirection(Direction direction) {
        switch (direction) {
            case UP: return BlockFace.UP;
            case DOWN: return BlockFace.DOWN;
            case LEFT: return BlockFace.EAST;
            case RIGHT: return BlockFace.WEST;
            default: return null;
        }
    }
}
