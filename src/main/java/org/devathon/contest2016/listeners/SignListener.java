package org.devathon.contest2016.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.blocks.PipetteDestination;
import org.devathon.contest2016.blocks.PipettePipe;
import org.devathon.contest2016.blocks.PipetteSender;
import org.devathon.contest2016.etc.Pipette;
import org.devathon.contest2016.etc.PipetteMode;
import org.devathon.contest2016.etc.PrettyLocation;

import java.util.UUID;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class SignListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSignChange(SignChangeEvent event) {
        // make sure the sign creation is suppose to be for us at all
        if (!event.getLine(0).equalsIgnoreCase("[pipe]") && !event.getLine(0).equalsIgnoreCase("[p]")) return;

        // make sure player has permission to be building pipetteBlocks
        if (event.getPlayer().hasPermission("pipette.build") && !event.getPlayer().isOp()) {
            event.getPlayer().sendMessage(ChatColor.DARK_RED + "You lack the " + ChatColor.WHITE + "pipette.build" + ChatColor.DARK_RED + " permission to build pipetteBlocks!");
            return;
        }

        // get requested pipette mode from line 2
        PipetteMode mode = PipetteMode.parse(event.getLine(1));

        // get ownership information for knowing who created pipes that lagged the server out
        String ownerName = event.getPlayer().getName();
        UUID ownerUuid = event.getPlayer().getUniqueId();

        // special checks if pipette is in a input/output mode
        BlockFace targetFace = null;
        Block attachedToBlock = null;
        boolean blockHasAnInventory = false;
        if (mode == PipetteMode.DESTINATION || mode == PipetteMode.SEND) {
            targetFace = ((org.bukkit.material.Sign) event.getBlock().getState().getData()).getAttachedFace();
            attachedToBlock = event.getBlock().getRelative(targetFace);
            blockHasAnInventory = InventoryHolder.class.isAssignableFrom(attachedToBlock.getState().getClass());
        }
        // special checks if pipette is in destination mode
        if (mode == PipetteMode.DESTINATION) {
            if (event.getBlock().getType() != Material.WALL_SIGN) {
                event.getPlayer().sendMessage(ChatColor.DARK_RED + "Pipettes in " + ChatColor.RED + "destination" + ChatColor.DARK_RED + " mode have to be attached to a container.");
                return;
            } else {
                if (!blockHasAnInventory) {
                    event.getPlayer().sendMessage(ChatColor.DARK_RED + "The block you attached this sign to doesn't have an inventory to send items to.");
                    return;
                }

                Inventory inventory = null;
                switch (attachedToBlock.getType()) {
                    case BEACON: inventory = ((Beacon) attachedToBlock.getState()).getInventory(); break;
                    case BREWING_STAND: inventory = ((BrewingStand) attachedToBlock.getState()).getInventory(); break;
                    case CHEST: inventory = ((Chest) attachedToBlock.getState()).getInventory(); break;
                    case DISPENSER: inventory = ((Dispenser) attachedToBlock.getState()).getInventory(); break;
                    case DROPPER: inventory = ((Dropper) attachedToBlock.getState()).getInventory(); break;
                    case FURNACE: inventory = ((Furnace) attachedToBlock.getState()).getInventory(); break;
                    case HOPPER: inventory = ((Hopper) attachedToBlock.getState()).getInventory(); break;
                }

                if (inventory == null) {
                    event.getPlayer().sendMessage(ChatColor.DARK_RED + "The container you're trying to use isn't supported. Go bitch @ScarszRawr to update his plugin.");
                    return;
                }
            }
        }
        // special checks if pipette is in send mode
        if (mode == PipetteMode.SEND) {
            if (event.getBlock().getType() != Material.WALL_SIGN) {
                event.getPlayer().sendMessage(ChatColor.DARK_RED + "Pipettes in " + ChatColor.RED + "send" + ChatColor.DARK_RED + " mode have to be attached to a container.");
                return;
            } else {
                if (!blockHasAnInventory) {
                    event.getPlayer().sendMessage(ChatColor.DARK_RED + "The block you attached this sign to doesn't have an inventory to draw items from.");
                    return;
                }

                Inventory inventory = null;
                switch (attachedToBlock.getType()) {
                    case BEACON: inventory = ((Beacon) attachedToBlock.getState()).getInventory(); break;
                    case BREWING_STAND: inventory = ((BrewingStand) attachedToBlock.getState()).getInventory(); break;
                    case CHEST: inventory = ((Chest) attachedToBlock.getState()).getInventory(); break;
                    case DISPENSER: inventory = ((Dispenser) attachedToBlock.getState()).getInventory(); break;
                    case DROPPER: inventory = ((Dropper) attachedToBlock.getState()).getInventory(); break;
                    case FURNACE: inventory = ((Furnace) attachedToBlock.getState()).getInventory(); break;
                    case HOPPER: inventory = ((Hopper) attachedToBlock.getState()).getInventory(); break;
                }

                if (inventory == null) {
                    event.getPlayer().sendMessage(ChatColor.DARK_RED + "The container you're trying to use isn't supported. Go bitch @ScarszRawr to update his plugin.");
                    return;
                }
            }
        }

        // create pipette
        Pipette createdPipette = null;
        switch (mode) {
            case ITEM:
            case LIQUID:
            case BOTH:
                createdPipette = new PipettePipe(event.getBlock().getLocation(), mode, ownerName, ownerUuid);
                break;
            case DESTINATION:
                createdPipette = new PipetteDestination(event.getBlock().getLocation(), mode, ownerName, ownerUuid, targetFace);
                break;
            case SEND:
                createdPipette = new PipetteSender(event.getBlock().getLocation(), mode, ownerName, ownerUuid, targetFace);
                break;
        }

        DevathonPlugin.instance.pipetteBlocks.add(createdPipette);
        DevathonPlugin.instance.getLogger().info("Player " + event.getPlayer().getName() + " has created a pipette in " + mode + " at " + new PrettyLocation(event.getBlock().getLocation()));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Pipette target = DevathonPlugin.instance.getPipetteAtLocation(event.getBlock().getLocation());
        if (target == null) return;
        DevathonPlugin.instance.pipetteBlocks.remove(target);
        for (Block block : target.targets) DevathonPlugin.instance.getPipetteAtLocation(block.getLocation()).recalculateTargetsMe();
        for (Block block : target.targetsMe) DevathonPlugin.instance.getPipetteAtLocation(block.getLocation()).recalculateTargets();
    }

}
