package org.devathon.contest2016.etc;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class PrettyLocation {

    private Location location;
    public PrettyLocation(String world, int[] coordinates) {
        this(new Location(Bukkit.getWorld(world), coordinates[0], coordinates[1], coordinates[2]));
    }
    public PrettyLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Location{X=" + location.getBlockX() + ", Y=" + location.getBlockY() + ", Z=" + location.getBlockZ() + ", world=" + location.getWorld().getName() + "}";
    }

}
