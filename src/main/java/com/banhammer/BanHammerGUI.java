package com.banhammer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BanHammerGUI {
    
    public static Inventory createGUI() {
        Inventory gui = Bukkit.createInventory(null, 27, "§c§lBanHammer Menu");
        
        // Normal
        ItemStack normal = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta normalMeta = normal.getItemMeta();
        normalMeta.setDisplayName("§cNormal Ban Hammer");
        normalMeta.setLore(Arrays.asList("§7Name ban", "§7Right click to ban"));
        normal.setItemMeta(normalMeta);
        gui.setItem(10, normal);
        
        // IP Ban
        ItemStack ip = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta ipMeta = ip.getItemMeta();
        ipMeta.setDisplayName("§4IP Ban Hammer");
        ipMeta.setLore(Arrays.asList("§7IP ban", "§7Stronger"));
        ip.setItemMeta(ipMeta);
        gui.setItem(12, ip);
        
        // Temp Ban
        ItemStack temp = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta tempMeta = temp.getItemMeta();
        tempMeta.setDisplayName("§6Temp Ban Hammer");
        tempMeta.setLore(Arrays.asList("§71 hour temp ban"));
        temp.setItemMeta(tempMeta);
        gui.setItem(14, temp);
        
        // Stats
        ItemStack stats = new ItemStack(Material.BOOK);
        ItemMeta statsMeta = stats.getItemMeta();
        statsMeta.setDisplayName("§eStats");
        statsMeta.setLore(Arrays.asList("§7View ban stats"));
        stats.setItemMeta(statsMeta);
        gui.setItem(16, stats);
        
        return gui;
    }
}

