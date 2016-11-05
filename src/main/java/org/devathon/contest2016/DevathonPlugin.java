package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DevathonPlugin extends JavaPlugin {

    public void onEnable() {
        //TODO less retarded start message
        getLogger().info("Pipette shooting some pipes...");

        Bukkit.getPluginManager().registerEvents(new SignListener(), this);

    }

    public void onDisable() {
        //TODO less retarded stop message
        getLogger().info("Stabilizing all the things");
    }



}

