package org.devathon.contest2016.old;

import org.bukkit.block.BlockFace;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class DirectionParser {


    static Direction getDirectionFromBlockFace(BlockFace facing) {
        BlockFace actualBlockFace = BlockFace.valueOf(facing.name().split("_")[0]);

        // don't even remember what I was doing

        switch (actualBlockFace.name()) {
            case "NORTH": return Direction.UP;
            case "SOUTH": return Direction.DOWN;
            case "WEST": return Direction.LEFT;
            case "EAST": return Direction.RIGHT;
        }
        return null;
    }


}
