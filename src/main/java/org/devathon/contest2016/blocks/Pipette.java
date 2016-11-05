package org.devathon.contest2016.blocks;

import org.bukkit.block.Block;
import org.devathon.contest2016.listeners.PipetteMode;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class Pipette {

    public ArrayList<Block> targets;
    public PipetteMode mode;
    public String ownerName;
    public UUID ownerUuid;

    public Pipette(ArrayList<Block> targets, PipetteMode mode, String ownerName, UUID ownerUuid) {
        this.targets = targets;
        this.mode = mode;
        this.ownerName = ownerName;
        this.ownerUuid = ownerUuid;
    }

    @Override
    public String toString() {
        return "Pipette{" +
                "targets=" + targets +
                ", mode=" + mode +
                ", ownerName='" + ownerName + '\'' +
                ", ownerUuid=" + ownerUuid +
                '}';
    }
}
