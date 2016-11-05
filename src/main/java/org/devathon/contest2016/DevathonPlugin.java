package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.blocks.PipettePipe;
import org.devathon.contest2016.blocks.PipetteDestination;
import org.devathon.contest2016.blocks.PipetteInjector;
import org.devathon.contest2016.etc.SignListener;

import java.util.ArrayList;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class DevathonPlugin extends JavaPlugin {

    public ArrayList<PipettePipe> pipettePipeBlocks = new ArrayList<>();
    public ArrayList<PipetteDestination> pipetteDestinationBlocks = new ArrayList<>();
    public ArrayList<PipetteInjector> pipetteInjectionBlocks = new ArrayList<>();

    public static DevathonPlugin instance;

    public void onEnable() {
        instance = this;

        //TODO less retarded start message
        getLogger().info("Pipette shooting some pipes...");

        Bukkit.getPluginManager().registerEvents(new SignListener(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            pipettePipeBlocks.forEach(PipettePipe::update);
            pipetteDestinationBlocks.forEach(PipetteDestination::update);
            pipetteInjectionBlocks.forEach(PipetteInjector::update);
        }, 0, 20);
    }

    public void onDisable() {
        //TODO less retarded stop message
        getLogger().info("Stabilizing all the things");
    }

}

