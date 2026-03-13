package com.banhammer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BanHammerListener implements Listener {

    private final BanHammer plugin;

    public BanHammerListener(BanHammer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker) || !(event.getEntity() instanceof Player victim)) return;

        ItemStack mainHand = attacker.getInventory().getItemInMainHand();
        if (!mainHand.hasItemMeta()) return;
        ItemMeta meta = mainHand.getItemMeta();
        if (meta == null || !meta.getPersistentDataContainer().has(plugin.getNamespaceKey("banhammer"), PersistentDataType.STRING)) return;

        // INSTANT BAN on HIT (1 hit = ban)
        String reason = meta.getPersistentDataContainer().get(plugin.getNamespaceKey("reason"), PersistentDataType.STRING);
        if (reason == null) reason = plugin.getConfig().getString("ban-reason", "Banned by BanHammer!");
        Boolean ipBanObj = meta.getPersistentDataContainer().get(plugin.getNamespaceKey("ipban"), PersistentDataType.BOOLEAN);
        boolean ipBan = ipBanObj != null ? ipBanObj : plugin.getConfig().getBoolean("ip-ban", false);

        if (ipBan) {
            Bukkit.getBanList(org.bukkit.BanList.Type.IP).addBan(victim.getAddress().getAddress().getHostAddress(), reason, null, attacker.getName());
            Bukkit.broadcastMessage("§c§l" + victim.getName() + " §7was IP banned by §c§l" + attacker.getName() + "§7! §eReason: §f" + reason);
        } else {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(victim.getName(), reason, null, attacker.getName());
            Bukkit.broadcastMessage("§c§l" + victim.getName() + " §7was banned by §c§l" + attacker.getName() + "§7! §eReason: §f" + reason);
        }
        
        event.setCancelled(true); // Don't actually kill - just ban
        attacker.sendMessage("§a§lBanned " + victim.getName() + "§7!");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer == null) return;

        ItemStack mainHand = killer.getInventory().getItemInMainHand();
        if (!mainHand.hasItemMeta()) return;
        ItemMeta meta = mainHand.getItemMeta();
        if (meta == null || !meta.getPersistentDataContainer().has(plugin.getNamespaceKey("banhammer"), PersistentDataType.STRING)) return;

        String reason = meta.getPersistentDataContainer().get(plugin.getNamespaceKey("reason"), PersistentDataType.STRING);
        if (reason == null) reason = plugin.getConfig().getString("ban-reason", "Banned by BanHammer!");
        Boolean ipBanObj = meta.getPersistentDataContainer().get(plugin.getNamespaceKey("ipban"), PersistentDataType.BOOLEAN);
        boolean ipBan = ipBanObj != null ? ipBanObj : plugin.getConfig().getBoolean("ip-ban", false);

        if (ipBan) {
            Bukkit.getBanList(org.bukkit.BanList.Type.IP).addBan(victim.getAddress().getAddress().getHostAddress(), reason, null, killer.getName());
            Bukkit.broadcastMessage("§c§l" + victim.getName() + " §7was IP banned by §c§l" + killer.getName() + "§7! §eReason: §f" + reason);
        } else {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(victim.getName(), reason, null, killer.getName());
            Bukkit.broadcastMessage("§c§l" + victim.getName() + " §7was banned by §c§l" + killer.getName() + "§7! §eReason: §f" + reason);
        }

        killer.sendMessage("§a§lBanned " + victim.getName() + "§7!");
    }
}

