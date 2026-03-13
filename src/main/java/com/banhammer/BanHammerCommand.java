package com.banhammer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.ChatColor;
import java.util.Arrays;
import java.util.List;

public class BanHammerCommand implements CommandExecutor {

    private final BanHammer plugin;
    private final List<String> hammerTypes = Arrays.asList("normal", "ip", "temp", "mass");

    public BanHammerCommand(BanHammer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("banhammer.admin")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.GOLD + "§6§lBanHammer PRO v5.0");
            sender.sendMessage(ChatColor.YELLOW + "Subcommands:");
            sender.sendMessage(ChatColor.GRAY + "• give <player> <type> [reason]");
            sender.sendMessage(ChatColor.GRAY + "• list");
            sender.sendMessage(ChatColor.GRAY + "• reload");
            sender.sendMessage(ChatColor.GRAY + "• stats");
            sender.sendMessage(ChatColor.GRAY + "• gui");
            sender.sendMessage(ChatColor.GRAY + "• config");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "give":
                return giveHammer(sender, args);
            case "list":
                sender.sendMessage(ChatColor.GREEN + "Hammer types: " + String.join(ChatColor.GRAY + ", ", hammerTypes));
                return true;
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
                return true;
            case "stats":
                sender.sendMessage(ChatColor.GOLD + "Stats - Bans: " + plugin.getConfig().getInt("stats.bans", 0));
                return true;
            case "gui":
                if (sender instanceof Player) {
                    ((Player) sender).openInventory(BanHammerGUI.createGUI());
                } else {
                    sender.sendMessage(ChatColor.RED + "GUI only for players!");
                }
                return true;
            case "config":
                if (sender instanceof Player) {
                    ((Player) sender).openInventory(BanHammerConfigGUI.createConfigGUI());
                } else {
                    sender.sendMessage(ChatColor.RED + "GUI only for players!");
                }
                return true;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown: " + args[0] + ". Use /banhammer help");
        }

        return true;
    }

    private boolean giveHammer(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Usage: /banhammer give <player> <normal|ip|temp> [reason] [duration]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not online!");
            return true;
        }

        String type = args[2].toLowerCase();
        if (!hammerTypes.contains(type)) {
            sender.sendMessage(ChatColor.RED + "Type: " + String.join(", ", hammerTypes));
            return true;
        }

        String reason = args.length > 3 ? String.join(" ", Arrays.copyOfRange(args, 3, args.length)) : plugin.getConfig().getString("ban-reason", "Banned");
        boolean ipBan = type.equals("ip");
        String hammerName = ChatColor.translateAlternateColorCodes('&', switch(type) {
            case "normal" -> "&c&lBan Hammer";
            case "ip" -> "&4&lIP Ban Hammer";
            case "temp" -> "&6&lTemp Ban Hammer";
            case "mass" -> "&0&lMass Ban Hammer";
            default -> "&c&lBan Hammer";
        });

        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(hammerName);
        meta.setLore(Arrays.asList(
            ChatColor.GRAY + "Type: " + type.toUpperCase(),
            ChatColor.GRAY + "Reason: " + reason,
            ChatColor.RED + "Right click to ban!"
        ));
        meta.setUnbreakable(true);

        // Data
        meta.getPersistentDataContainer().set(plugin.getNamespaceKey("banhammer"), PersistentDataType.STRING, "true");
        meta.getPersistentDataContainer().set(plugin.getNamespaceKey("reason"), PersistentDataType.STRING, reason);
        meta.getPersistentDataContainer().set(plugin.getNamespaceKey("ipban"), PersistentDataType.BOOLEAN, ipBan);
        meta.getPersistentDataContainer().set(plugin.getNamespaceKey("type"), PersistentDataType.STRING, type);

        item.setItemMeta(meta);
        target.getInventory().addItem(item);

        sender.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.GOLD + type + ChatColor.GREEN + " hammer to " + target.getName());
        target.sendMessage(ChatColor.GREEN + "You received a " + hammerName + ChatColor.GREEN + "!");
        return true;
    }
}

