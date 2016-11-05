package org.devathon.contest2016.etc;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.blocks.PipettePipe;
import org.devathon.contest2016.listeners.PipetteMode;

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
        if (!event.getLine(0).equalsIgnoreCase("[pipe]")) return;

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

        // create pipette
        PipettePipe createdPipettePipe = new PipettePipe(event.getBlock().getData(), event.getBlock().getLocation(), event.getBlock().getType(), mode, ownerName, ownerUuid);
        DevathonPlugin.instance.pipettePipeBlocks.add(createdPipettePipe);
        DevathonPlugin.instance.pipettePipeBlocks.get(DevathonPlugin.instance.pipettePipeBlocks.size() - 1).update();

        DevathonPlugin.instance.getLogger().info("Player " + event.getPlayer().getName() + " has created a pipette in " + mode + " at " + new PrettyLocation(event.getBlock().getLocation()));
    }

}
