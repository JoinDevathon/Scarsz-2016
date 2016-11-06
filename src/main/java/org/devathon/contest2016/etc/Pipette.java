package org.devathon.contest2016.etc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.devathon.contest2016.DevathonPlugin;

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

    /**
     * Update the pipette's sign with information regarding it
     * @return whether or not the update was successful. a false value should indicate the pipette should be unregistered.
     */
    public boolean update() { return true; }

    public Block getBlock() {
        return Bukkit.getWorld(world).getBlockAt(location[0], location[1], location[2]);
    }

    public void recalculateTargets() {
        ArrayList<Block> newTargets = new ArrayList<>();
        for (Block target : targets) {
            if (DevathonPlugin.instance.pipetteBlocks.contains(DevathonPlugin.instance.getPipetteAtLocation(target.getLocation())))
                newTargets.add(target);
        }
        targets = newTargets;
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
