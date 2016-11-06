package org.devathon.contest2016.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.etc.Pipette;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null ||
            event.getItem().getType() != Material.STICK || // make sure it's a stick
            event.getItem().getItemMeta().getDisplayName() == null || // make sure it has an actual name
            !event.getItem().getItemMeta().getDisplayName().contains("Wand of Linking") || // make sure it's our wand
            event.getItem().getItemMeta().getEnchantLevel(Enchantment.DIG_SPEED) != 100 || // make sure it has the op enchantment
            (event.getClickedBlock() != null && event.getClickedBlock().getType() != Material.SIGN_POST && event.getClickedBlock().getType() != Material.WALL_SIGN) || // make sure they interacted with a sign
            event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        event.setCancelled(true);

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            // player wants to have this sign link to another
            DevathonPlugin.instance.playersLinks.put(event.getPlayer().getUniqueId(), DevathonPlugin.instance.getPipetteAtLocation(event.getClickedBlock().getLocation()).uuid);
            event.getPlayer().sendMessage(ChatColor.RED + "You've copied this pipette's properties to your clipboard. Paste them by right clicking another pipette!");
        } else {
            // player wants this sign to be linked as a target to another
            if (!DevathonPlugin.instance.playersLinks.containsKey(event.getPlayer().getUniqueId())) {
                event.getPlayer().sendMessage(ChatColor.RED + "You don't have any pipette information in your clipboard! Copy it from another pipette by left clicking it.");
            } else {
                Pipette source = DevathonPlugin.instance.getLinkingPipetteFromPlayerUuid(event.getPlayer().getUniqueId());
                Pipette target = DevathonPlugin.instance.getPipetteAtLocation(event.getClickedBlock().getLocation());
                if (source.targets.contains(event.getClickedBlock())) {
                    source.targets.remove(event.getClickedBlock());
                    target.recalculateTargetsMe();
                    event.getPlayer().sendMessage(ChatColor.RED + "You've removed this pipette from another pipette's targets! The pipettes are now unlinked.");
                } else {
                    source.targets.add(event.getClickedBlock());
                    target.targetsMe.add(source.getBlock());
                    event.getPlayer().sendMessage(ChatColor.RED + "You've pasted another pipette's information to this pipette! The pipettes are now linked.");
                }
            }
        }
    }

}
