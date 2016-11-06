package org.devathon.contest2016.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.devathon.contest2016.etc.Pipette;
import org.devathon.contest2016.etc.PipetteMode;
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

    public boolean update() {
        if (getBlock().getType() != Material.SIGN_POST && getBlock().getType() != Material.WALL_SIGN) return false;

        SignUtil.setTextOnSign(getBlock().getLocation(),
                ChatColor.translateAlternateColorCodes('&', "&4&l[&c&lPIPETTE&4&l]"),
                ChatColor.translateAlternateColorCodes('&', "&4&lMode: &c&l" + mode.name()),
                ChatColor.translateAlternateColorCodes('&', "&4&lLinked: &c&l" + (targets.size() > 0 || targetsMe.size() > 0)),
                ChatColor.translateAlternateColorCodes('&', "&4&lOwner: &c&l" + ownerName)
        );
        return true;
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
