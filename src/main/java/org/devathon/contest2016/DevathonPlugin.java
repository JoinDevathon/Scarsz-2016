package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.blocks.PipetteDestination;
import org.devathon.contest2016.blocks.PipetteInjector;
import org.devathon.contest2016.blocks.PipettePipe;
import org.devathon.contest2016.etc.SignListener;

import java.util.*;

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

    public static Map<String, Object> statistics = new HashMap<String, Object>() {{
        put("Items transported", 723856);
    }};

    public static DevathonPlugin instance;

    public void onEnable() {
        // set static instance of the plugin to use in other classes
        instance = this;

        //TODO less retarded start message
        getLogger().info("Pipette shooting some pipes...");

        // register sign edit events
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);

        // setup task to automatically update all signs every 5 seconds
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            pipettePipeBlocks.forEach(PipettePipe::update);
            pipetteDestinationBlocks.forEach(PipetteDestination::update);
            pipetteInjectionBlocks.forEach(PipetteInjector::update);
        }, 0, 100); // 100 ticks / 20 TPS = 5 seconds

        getCommand("pipette").setExecutor((commandSender, command, label, args) -> {
            commandSender.sendMessage(new String[] {
                    ChatColor.translateAlternateColorCodes('&', "&4===================================================="),
                    ChatColor.translateAlternateColorCodes('&', "&4               Pipette, &cyo&4."),
                    ChatColor.translateAlternateColorCodes('&', "&4                            v" + getDescription().getVersion() + " by Scarsz/@ScarszRawr"),
                    ChatColor.translateAlternateColorCodes('&', ""),
                    ChatColor.translateAlternateColorCodes('&', "&4 /pipewand &c" + getCommand("pipewand").getDescription()),
                    ChatColor.translateAlternateColorCodes('&', ""),
                    ChatColor.translateAlternateColorCodes('&', "&4===================================================="),
            });
            return true;
        });
        getCommand("pipewand").setExecutor((commandSender, command, label, args) -> {
            if (commandSender instanceof Player) givePlayerWandOfLinking((Player) commandSender);
            else getLogger().info(ChatColor.RED + "This is a command that can only be ran by players.");
            return true;
        });
        // TODO fix stats displaying
        getCommand("pipestats").setExecutor((commandSender, command, label, args) -> {
            List<String> stats = new LinkedList<>();
            statistics.forEach((s, o) -> ChatColor.translateAlternateColorCodes('&', "&4" + s + ": &c" + o));
            commandSender.sendMessage(new String[] {
                    ChatColor.translateAlternateColorCodes('&', "&4===================================================="),
                    ChatColor.translateAlternateColorCodes('&', "&4               Pipette, &cyo&4."),
                    ChatColor.translateAlternateColorCodes('&', "&4                            v" + getDescription().getVersion() + " by Scarsz/@ScarszRawr"),
                    ChatColor.translateAlternateColorCodes('&', ""),
                    ChatColor.translateAlternateColorCodes('&', String.join("\n", stats)),
                    ChatColor.translateAlternateColorCodes('&', ""),
                    ChatColor.translateAlternateColorCodes('&', "&4===================================================="),
            });
            return true;
        });
    }

    public void onDisable() {
        //TODO less retarded stop message
        getLogger().info("Stabilizing all the things...");
    }

    private static void givePlayerWandOfLinking(Player player) {
        ItemStack wand = new ItemStack(Material.STICK, 1);
        ItemMeta metadata = wand.getItemMeta();
        metadata.setDisplayName(ChatColor.translateAlternateColorCodes('&', "Wand of Linking"));
        //TODO figure out why lore isn't setting
        metadata.setLore(new LinkedList<String>() {{
            ChatColor.translateAlternateColorCodes('&', "&cRight click Pipette signs to link them together");
            ChatColor.translateAlternateColorCodes('&', "&cRight click the destination pipe then right click the target pipe");
            ChatColor.translateAlternateColorCodes('&', "&cIf the pipes get linked you'll get a message in the chat");
        }});
        wand.setItemMeta(metadata);
        wand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 100);
        player.getInventory().addItem(wand);
    }

}

