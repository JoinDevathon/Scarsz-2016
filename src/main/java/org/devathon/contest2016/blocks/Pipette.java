package org.devathon.contest2016.blocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
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
    public String world;

    public Pipette(byte data, Location location, Material material, PipetteMode mode, String ownerName, UUID ownerUuid) {
        this.data = data;
        this.location = new int[]{ location.getBlockX(), location.getBlockY(), location.getBlockZ() };
        this.material = material;
        this.mode = mode;
        this.ownerName = ownerName;
        this.ownerUuid = ownerUuid;
        this.world = location.getWorld().getName();
    }

    public void update() {
        Block block = Bukkit.getWorld(world).getBlockAt(location[0], location[1], location[2]);
        if (!(block instanceof Sign)) {
            System.out.println("NOT A SIGN YA TARD");
            DevathonPlugin.instance().pipetteBlocks.remove(this);
            return;
        }
        System.out.println(block.getClass().getSimpleName());
        BlockState state = block.getState();
        System.out.println(state);
        Sign sign = (Sign) state;
        System.out.println(sign);
        sign.setLine(0, ChatColor.BLUE + "[PIPETTE]");
        sign.setLine(1, ChatColor.BLUE + "Mode: " + ChatColor.WHITE + mode);
        sign.setLine(2, ChatColor.BLUE + "Linked: " + ChatColor.WHITE + (targets.size() > 0));
        sign.setLine(3, ChatColor.BLUE + "Owner: " + ChatColor.WHITE + ownerName);
        sign.update(true);
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
