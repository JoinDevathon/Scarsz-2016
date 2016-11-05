package org.devathon.contest2016.blocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.etc.Pipette;
import org.devathon.contest2016.listeners.PipetteMode;
import org.devathon.contest2016.util.SignUtil;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class PipettePipe extends Pipette {

    public byte data;
    public int[] location;
    public Material material;
    public PipetteMode mode;
    public String ownerName;
    public UUID ownerUuid;
    public ArrayList<Block> targets = new ArrayList<>();
    public String world;

    public PipettePipe(byte data, Location location, Material material, PipetteMode mode, String ownerName, UUID ownerUuid) {
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
        if (block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN) {
            DevathonPlugin.instance.pipettePipeBlocks.remove(this);
            return;
        }
//        BlockState state = block.getState();
//        Sign sign = (Sign) state;
//        sign.setLine(0, ChatColor.DARK_RED + "" + ChatColor.BOLD + "[PIPETTE]");
//        sign.setLine(1, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Mode: " + ChatColor.WHITE + "" + ChatColor.BOLD + mode);
//        sign.setLine(2, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Linked: " + ChatColor.WHITE + "" + ChatColor.BOLD + (targets.size() > 0));
//        sign.setLine(3, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Owner: " + ChatColor.WHITE + "" + ChatColor.BOLD + ownerName);
//        sign.update(true);

        SignUtil.setTextOnSign(world, location[0], location[1], location[2],
                ChatColor.translateAlternateColorCodes('&', "&4&l[&c&lPIPETTE&4&l]"),
                ChatColor.translateAlternateColorCodes('&', "&4&lMode: &c&l" + mode.name()),
                ChatColor.translateAlternateColorCodes('&', "&4&lLinked: &c&l" + (targets.size() > 0)),
                ChatColor.translateAlternateColorCodes('&', "&4&lOwner: &c&l" + ownerName)
        );
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
