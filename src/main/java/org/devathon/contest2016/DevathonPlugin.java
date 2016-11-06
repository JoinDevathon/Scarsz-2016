package org.devathon.contest2016;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.blocks.PipetteSender;
import org.devathon.contest2016.etc.Pipette;
import org.devathon.contest2016.etc.PipetteMode;
import org.devathon.contest2016.etc.PrettyLocation;
import org.devathon.contest2016.listeners.InteractListener;
import org.devathon.contest2016.listeners.SignListener;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Made by Scarsz
 *
 * @in /dev/hell
 * @at 11/5/2016
 */
public class DevathonPlugin extends JavaPlugin {

    private static final boolean debug = false;

    public ArrayList<Object> pipetteBlocks = new ArrayList<>();
    public Map<UUID, UUID> playersLinks = new HashMap<>();

    //TODO make this actually work
    public Map<String, Object> statistics = new HashMap<String, Object>() {{
        put("Items transported", 723856);
    }};

    public static DevathonPlugin instance;
    public static Random random = new Random();

    public void onEnable() {
        // set static instance of the plugin to use in other classes
        instance = this;

        //TODO less retarded start message
        getLogger().info("Pipette shooting some pipes...");

        Bukkit.getPluginManager().registerEvents(new InteractListener(), this); // interact events (for linking stuff)
        Bukkit.getPluginManager().registerEvents(new SignListener(), this); // sign edits

        // setup task to automatically update all signs
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            List<Pipette> pipettesToRemove = new ArrayList<>();
            pipetteBlocks.forEach(pipetteObject -> {
                Pipette pipette = (Pipette) pipetteObject;
                if (!pipette.update()) { // pipette was broken or something, clean it up
                    getLogger().info("Cleaning up pipette @ " + new PrettyLocation(pipette.getBlock().getLocation()));
                    pipettesToRemove.add(pipette);
                    pipette.targetsMe.forEach(block -> ((Pipette) block).recalculateTargets());
                }
            });
            pipetteBlocks.removeAll(pipettesToRemove);
        }, 0, 100); // 100 ticks / 20 TPS = 5 seconds

        // setup task to automatically update all signs every 5 seconds
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> pipetteBlocks.forEach(pipetteObject -> {
            Pipette pipette = (Pipette) pipetteObject;
            if (pipette.mode == PipetteMode.SEND) { // pipette is a sender
                long startTime = System.nanoTime(); // get start time before pipette tick
                ((PipetteSender) pipette).tick(); // tick pipette to send bath of items
                long elapsedNanos = System.nanoTime() - startTime; // find out how much time has elapsed
                if (DevathonPlugin.debug) getLogger().info("Tick for " + pipette + " took " + elapsedNanos + "ns [" + ((TimeUnit.NANOSECONDS.toMillis(elapsedNanos) / 50d) * 100d) + "% of a tick; 20 total ticks per second]");
            }
        }), 0, 20); // 20 ticks / 20 TPS = 1 second

        getCommand("pinksheep").setExecutor((commandSender, command, label, args) -> {
            String message = "B";
            int aCount = random.nextInt(100);
            for (int i = 0; i < aCount; i++) message += "a";

            commandSender.sendMessage(ChatColor.values()[random.nextInt(16)] + message);

            List<Player> targets = new ArrayList<>();
            if (!(commandSender instanceof Player)) {
                targets.addAll(Bukkit.getOnlinePlayers());
                return true;
            } else targets.add((Player) commandSender);

            for (Player target : targets) ((Sheep) target.getWorld().spawnEntity(target.getLocation(), EntityType.SHEEP)).setColor(DyeColor.PINK);

            return true;
        });
        //TODO make header not duplicate code
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

    public Pipette getPipetteAtLocation(Location location) {
        for (Object pipetteBlock : pipetteBlocks) {
            Pipette pipette = (Pipette) pipetteBlock;
            if (pipette.getBlock().getLocation().getBlockX() == location.getBlockX() &&
                pipette.getBlock().getLocation().getBlockY() == location.getBlockY() &&
                pipette.getBlock().getLocation().getBlockZ() == location.getBlockZ()) {
                return pipette;
            }
        }
        return null;
    }

    public Pipette getLinkingPipetteFromPlayerUuid(UUID playerUuid) {
        return getPipetteFromPipetteUuid(playersLinks.get(playerUuid));
    }

    public Pipette getPipetteFromPipetteUuid(UUID uuid) {
        if (uuid == null) return null;
        for (Object pipetteBlock : pipetteBlocks) {
            Pipette pipette = (Pipette) pipetteBlock;
            if (pipette.uuid == uuid) return pipette;
        }
        return null;
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

