package com.banhammer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BanHammerConfigGUI {
    
    private static final Map<Player, String> pendingReason = new HashMap<>();
    private static final Map<Player, Boolean> pendingIP = new HashMap<>();
    
    public static Inventory createConfigGUI() {
        Inventory gui = Bukkit.createInventory(null, 27, "§6§lConfigure BanHammer");
        
        // Reason anvil
        ItemStack reason = new ItemStack(Material.ANVIL);
        ItemMeta reasonMeta = reason.getItemMeta();
        reasonMeta.setDisplayName("§eSet Reason");
        reasonMeta.setLore(Arrays.asList("§7Click to set custom ban reason"));
        reason.setItemMeta(reasonMeta);
        gui.setItem(10, reason);
        
        // IP Toggle
        ItemStack ipToggle = new ItemStack(Material.REDSTONE);
        ItemMeta ipMeta = ipToggle.getItemMeta();
        ipMeta.setDisplayName("§cIP Ban: OFF");
        ipMeta.setLore(Arrays.asList("§7Click to toggle IP ban"));
        ipToggle.setItemMeta(ipMeta);
        gui.setItem(12, ipToggle);
        
        // Give hammer
        ItemStack give = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta giveMeta = give.getItemMeta();
        giveMeta.setDisplayName("§cGive Hammer");
        giveMeta.setLore(Arrays.asList("§7Give configured hammer to player"));
        give.setItemMeta(giveMeta);
        gui.setItem(14, give);
        
        return gui;
    }
    
    // Add conversation handlers for anvil clicks...
}

