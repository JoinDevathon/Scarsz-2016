package org.devathon.contest2016;

import org.bukkit.Location;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
class PrettyLocation {

    private Location location;
    PrettyLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Location{X=" + location.getBlockX() + ", Y=" + location.getBlockY() + ", Z=" + location.getBlockZ() + "}";
    }

}
