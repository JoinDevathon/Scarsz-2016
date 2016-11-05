package org.devathon.contest2016;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction parse(String toParse) {
        switch (toParse.substring(0, 1)) {
            case "^":
                return UP;
            case "v":
                return DOWN;
            case "<":
                return LEFT;
            case ">":
                return RIGHT;
            default:
                return null;
        }
    }
}
