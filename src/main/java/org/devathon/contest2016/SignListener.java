package org.devathon.contest2016;

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
        boolean isUs = event.getLine(0).equalsIgnoreCase("[pipe]") && (
                event.getLine(1).equalsIgnoreCase("<") ||
                event.getLine(1).equalsIgnoreCase(">") ||
                event.getLine(1).equalsIgnoreCase("^") ||
                event.getLine(1).equalsIgnoreCase("v")
        );
        if (!isUs) return;

        Direction direction = Direction.parse(event.getLine(1));
        
    }

}
