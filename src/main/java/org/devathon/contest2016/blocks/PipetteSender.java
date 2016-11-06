package org.devathon.contest2016.blocks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
public class PipetteSender extends Pipette {

    private String targetBlockFaceName;

    public PipetteSender(Location location, PipetteMode mode, String ownerName, UUID ownerUuid, BlockFace targetBlockFace) {
        super(location, mode, ownerName, ownerUuid);
        this.targetBlockFaceName = targetBlockFace.name();
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

    public Inventory getInventory() {
        Inventory inventory = null;
        switch (getBlock().getRelative(getInventoryBlockFace()).getType()) {
            case BEACON: inventory = ((Beacon) getBlock().getRelative(getInventoryBlockFace()).getState()).getInventory(); break;
            case BREWING_STAND: inventory = ((BrewingStand) getBlock().getRelative(getInventoryBlockFace()).getState()).getInventory(); break;
            case CHEST: inventory = ((Chest) getBlock().getRelative(getInventoryBlockFace()).getState()).getInventory(); break;
            case DISPENSER: inventory = ((Dispenser) getBlock().getRelative(getInventoryBlockFace()).getState()).getInventory(); break;
            case DROPPER: inventory = ((Dropper) getBlock().getRelative(getInventoryBlockFace()).getState()).getInventory(); break;
            case FURNACE: inventory = ((Furnace) getBlock().getRelative(getInventoryBlockFace()).getState()).getInventory(); break;
            case HOPPER: inventory = ((Hopper) getBlock().getRelative(getInventoryBlockFace()).getState()).getInventory(); break;
        }
        return inventory;
    }
    public BlockFace getInventoryBlockFace() {
        return BlockFace.valueOf(targetBlockFaceName);
    }

    public void tick() {
        //TODO implement maximum amount of items from itemstack to move at a time instead of the entire stack all at once, maybe implement limits per material or something

        // don't try moving things if there's nothing to move to
        if (targets.size() == 0) return;

        ItemStack itemStackToMove = null;
        for (ItemStack itemStack : getInventory().getContents()) {
            if (itemStack == null) continue;
            itemStackToMove = itemStack;
            break;
        }

        // don't try moving things if there's nothing to move
        if (itemStackToMove == null) return;

        getInventory().remove(itemStackToMove);
        passItemStackOn(itemStackToMove);
    }

}
