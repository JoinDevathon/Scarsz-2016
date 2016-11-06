package org.devathon.contest2016.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.devathon.contest2016.etc.Pipette;
import org.devathon.contest2016.listeners.PipetteMode;

import java.util.UUID;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class PipetteDestination extends Pipette {

    public PipetteDestination(byte data, Location location, Material material, PipetteMode mode, String ownerName, UUID ownerUuid) {
        super(data, location, material, mode, ownerName, ownerUuid);
    }

}
