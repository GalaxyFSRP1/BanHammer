package com.banhammer;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class BanHammer extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("banhammer").setExecutor(new BanHammerCommand(this));
        getServer().getPluginManager().registerEvents(new BanHammerListener(this), this);
        getLogger().info("§6BanHammer §aenabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("§6BanHammer §cdisabled!");
    }

    public NamespacedKey getNamespaceKey(String key) {
        return new NamespacedKey(this, key);
    }
}

