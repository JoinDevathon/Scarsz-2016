package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
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

    ArrayList<PipetteInjector> pipetteInjectionBlocks = new ArrayList<>();
    ArrayList<PipetteDestination> pipetteDestinationBlocks = new ArrayList<>();

    public static DevathonPlugin instance;
    public static DevathonPlugin instance() {
        return instance;
    }

    public void onEnable() {
        instance = this;

        //TODO less retarded start message
        getLogger().info("Pipette shooting some pipes...");

        Bukkit.getPluginManager().registerEvents(new SignListener(), this);

    }

    public void onDisable() {
        //TODO less retarded stop message
        getLogger().info("Stabilizing all the things");
    }

}

