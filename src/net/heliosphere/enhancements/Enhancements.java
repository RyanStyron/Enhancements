package net.heliosphere.enhancements;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Enhancements extends JavaPlugin {

    private static Enhancements instance;

    public static Enhancements getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        new Register(this);
        Bukkit.getLogger().info("Enhancements loaded.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Enhancements disabled.");
    }
}