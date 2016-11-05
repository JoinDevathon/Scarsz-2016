package org.devathon.contest2016.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.devathon.contest2016.DevathonPlugin;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class SignUtil {

    public static void setTextOnSign(String world, int x, int y, int z, String... lines) {
        setTextOnSign(new Location(Bukkit.getWorld(world), x, y, z), lines);
    }
    public static void setTextOnSign(Location location, String... lines) {
        if (!(location.getBlock().getState() instanceof Sign)) {
            DevathonPlugin.instance.getLogger().warning("Tried setting lines of sign to a location that wasn't a sign :OOOO");
            return;
        }

        Sign sign = (Sign) location.getBlock().getState();
        if (lines.length >= 1 && lines[0] != null) sign.setLine(0, lines[0]);
        if (lines.length >= 2 && lines[1] != null) sign.setLine(1, lines[1]);
        if (lines.length >= 3 && lines[2] != null) sign.setLine(2, lines[2]);
        if (lines.length >= 4 && lines[3] != null) sign.setLine(3, lines[3]);
        sign.update(true);
    }

}
