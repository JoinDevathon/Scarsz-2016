package org.devathon.contest2016.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.etc.Pipette;
import org.devathon.contest2016.listeners.PipetteMode;
import org.devathon.contest2016.util.SignUtil;

import java.util.UUID;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class PipettePipe extends Pipette {

    public PipettePipe(byte data, Location location, Material material, PipetteMode mode, String ownerName, UUID ownerUuid) {
        super(data, location, material, mode, ownerName, ownerUuid);
    }

    public void update() {
        if (getBlock().getType() != Material.SIGN_POST && getBlock().getType() != Material.WALL_SIGN) {
            DevathonPlugin.instance.pipetteBlocks.remove(this);
            return;
        }
//        BlockState state = block.getState();
//        Sign sign = (Sign) state;
//        sign.setLine(0, ChatColor.DARK_RED + "" + ChatColor.BOLD + "[PIPETTE]");
//        sign.setLine(1, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Mode: " + ChatColor.WHITE + "" + ChatColor.BOLD + mode);
//        sign.setLine(2, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Linked: " + ChatColor.WHITE + "" + ChatColor.BOLD + (targets.size() > 0));
//        sign.setLine(3, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Owner: " + ChatColor.WHITE + "" + ChatColor.BOLD + ownerName);
//        sign.update(true);

        SignUtil.setTextOnSign(getBlock().getLocation(),
                ChatColor.translateAlternateColorCodes('&', "&4&l[&c&lPIPETTE&4&l]"),
                ChatColor.translateAlternateColorCodes('&', "&4&lMode: &c&l" + mode.name()),
                ChatColor.translateAlternateColorCodes('&', "&4&lLinked: &c&l" + (targets.size() > 0 || targetsMe.size() > 0)),
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
