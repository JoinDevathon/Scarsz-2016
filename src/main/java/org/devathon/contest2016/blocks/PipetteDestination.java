package org.devathon.contest2016.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
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
public class PipetteDestination extends Pipette {

    public PipetteDestination(Location location, PipetteMode mode, String ownerName, UUID ownerUuid) {
        super(location, mode, ownerName, ownerUuid);
    }

    public boolean update() {
        if (!super.update()) return false;

        //TODO add some sort of way to view links of a pipette, maybe snag code from WorldGuard/projection shovels
        SignUtil.setTextOnSign(getBlock().getLocation(),
                ChatColor.translateAlternateColorCodes('&', "&4&l[&c&lPIPETTE&4&l]"),
                ChatColor.translateAlternateColorCodes('&', "&4&lMode: &c&l" + mode.name().substring(0, 4)),
                ChatColor.translateAlternateColorCodes('&', "&4&lLinks: &c&l" + (targets.size() + targetsMe.size())),
                ChatColor.translateAlternateColorCodes('&', "&4&lOwner: &c&l" + ownerName)
        );
        return true;
    }

}
