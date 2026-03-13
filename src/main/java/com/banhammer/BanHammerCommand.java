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

import java.util.Arrays;
import java.util.List;

public class BanHammerCommand implements CommandExecutor {

    private final BanHammer plugin;

    public BanHammerCommand(BanHammer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("banhammer.give")) {
            sender.sendMessage("§cNo permission!");
            return true;
        }

        if (args.length < 2 || !args[0].equalsIgnoreCase("give")) {
            sender.sendMessage("§cUsage: /banhammer give <player> [reason] [ipban:true/false]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found!");
            return true;
        }

        String reason = plugin.getConfig().getString("ban-reason", "Banned by BanHammer!");
        boolean ipBan = plugin.getConfig().getBoolean("ip-ban", false);

        if (args.length > 2) {
            String lastArg = args[args.length - 1];
            if (lastArg.equalsIgnoreCase("true")) {
                ipBan = true;
            } else if (lastArg.equalsIgnoreCase("false")) {
                ipBan = false;
            }
            reason = args.length > 3 ? String.join(" ", Arrays.copyOfRange(args, 2, args.length - 1)) : plugin.getConfig().getString("ban-reason", "Banned by BanHammer!");
        }

        ItemStack banHammer = createBanHammer(reason, ipBan);
        target.getInventory().addItem(banHammer);
        sender.sendMessage("§aGave BanHammer to " + target.getName() + " (IP Ban: " + ipBan + ")");
        target.sendMessage("§eYou received the BanHammer!");

        return true;
    }

    private ItemStack createBanHammer(String reason, boolean ipBan) {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c§lBan Hammer");
        meta.setLore(Arrays.asList("§7Infinite damage", "§7Kills → BAN!", "§7Reason: " + reason, "§7IP Ban: " + ipBan, "§8Unbreakable"));
        meta.setUnbreakable(true);

        // Store data
        meta.getPersistentDataContainer().set(plugin.getNamespaceKey("banhammer"), PersistentDataType.STRING, "true");
        meta.getPersistentDataContainer().set(plugin.getNamespaceKey("reason"), PersistentDataType.STRING, reason);
        meta.getPersistentDataContainer().set(plugin.getNamespaceKey("ipban"), PersistentDataType.BOOLEAN, ipBan);

        item.setItemMeta(meta);
        return item;
    }
}

