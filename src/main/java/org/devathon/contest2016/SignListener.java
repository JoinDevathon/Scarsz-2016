package org.devathon.contest2016;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Made by Scarsz
 * @in /dev/hell
 * @at 11/5/2016
 */
class SignListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!event.getLine(0).equalsIgnoreCase("[pipe]")) return;

        if (event.getPlayer().hasPermission("pipette.build") && !event.getPlayer().isOp()) {
            event.getPlayer().sendMessage(ChatColor.BLUE + "You lack the " + ChatColor.WHITE + "pipette.build" + ChatColor.BLUE + " permission to build pipettes!");
            return;
        }

        Direction direction = Direction.parse(event.getLine(1));
        if (direction == null) {
            event.getPlayer().sendMessage(ChatColor.BLUE + "You need to specify a valid direction on line two of the sign!\n" +
                    "Valid directions are " + ChatColor.WHITE + "^, v, <, > " + ChatColor.BLUE + "(characters, not spelled out)");
            return;
        }

        DevathonPlugin.instance.getLogger().info("Player " + event.getPlayer().getName() + " has created a pipette going towards " + direction + " at " + event.getBlock().getLocation());
    }

}
