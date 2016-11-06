package org.devathon.contest2016.etc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.devathon.contest2016.DevathonPlugin;
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

    public byte data;
    public int[] location;
    public Material material;
    public PipetteMode mode;
    public String ownerName;
    public UUID ownerUuid;
    public ArrayList<Block> targets = new ArrayList<>();
    public ArrayList<Block> targetsMe = new ArrayList<>();
    public UUID uuid;
    public String world;

    public Pipette(byte data, Location location, Material material, PipetteMode mode, String ownerName, UUID ownerUuid) {
        this.data = data;
        this.location = new int[]{ location.getBlockX(), location.getBlockY(), location.getBlockZ() };
        this.material = material;
        this.mode = mode;
        this.ownerName = ownerName;
        this.ownerUuid = ownerUuid;
        this.uuid = UUID.randomUUID();
        this.world = location.getWorld().getName();
    }

    public void update() {}

    public Block getBlock() {
        return Bukkit.getWorld(world).getBlockAt(location[0], location[1], location[2]);
    }

    public void recalculateTargetsMe() {
        ArrayList<Block> newTargetsMe = new ArrayList<>();
        for (Object pipetteBlock : DevathonPlugin.instance.pipetteBlocks) {
            Pipette pipette = (Pipette) pipetteBlock;
            if (pipette.targets.contains(getBlock())) newTargetsMe.add(pipette.getBlock());
        }
        targetsMe = newTargetsMe;
    }

}
