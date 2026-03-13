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
import org.bukkit.Particle;
import org.bukkit.Sound;
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

        // INSTANT BAN on HIT
        String reason = meta.getPersistentDataContainer().get(plugin.getNamespaceKey("reason"), PersistentDataType.STRING);
        if (reason == null) reason = plugin.getConfig().getString("ban-reason", "Banned by BanHammer!");
        Boolean ipBanObj = meta.getPersistentDataContainer().get(plugin.getNamespaceKey("ipban"), PersistentDataType.BOOLEAN);
        boolean ipBan = ipBanObj != null ? ipBanObj : plugin.getConfig().getBoolean("ip-ban", false);

        // EPIC EFFECTS
        victim.playSound(victim.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0f, 0.5f);
        attacker.playSound(attacker.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2.0f);

        victim.getWorld().spawnParticle(Particle.EXPLOSION, victim.getLocation().add(0, 1, 0), 10, 0.5, 0.5, 0.5, 0.1);
        attacker.getWorld().spawnParticle(Particle.HEART, attacker.getLocation().add(0, 1, 0), 20, 0.3, 0.3, 0.3, 0.1);

        // BAN
        if (ipBan) {
            Bukkit.getBanList(org.bukkit.BanList.Type.IP).addBan(victim.getAddress().getAddress().getHostAddress(), reason, null, attacker.getName());
        } else {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(victim.getName(), reason, null, attacker.getName());
        }

        // INSTANT KICK + BAN
        if (ipBan) {
            Bukkit.getBanList(org.bukkit.BanList.Type.IP).addBan(victim.getAddress().getAddress().getHostAddress(), reason, null, attacker.getName());
        } else {
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(victim.getName(), reason, null, attacker.getName());
        }
        victim.kickPlayer("§c§lBANNED §7by BanHammer\n§eReason: §f" + reason);
        
        Bukkit.broadcastMessage("§c§l[BanHammer] §7" + victim.getName() + " §7banned by §c§l" + attacker.getName() + " §7§e" + reason);
        event.setCancelled(true);
        attacker.sendMessage("§a§lINSTANT BAN §f" + victim.getName());
    }
}

