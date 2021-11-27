package net.heliosphere.enhancements;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import net.heliosphere.enhancements.environment.DamageModifier;
import net.heliosphere.enhancements.moshpit.EventCanceler;
import net.heliosphere.enhancements.moshpit.RegenSoup;
import net.heliosphere.enhancements.moshpit.RespawnDelay;
import net.heliosphere.enhancements.moshpit.Scheduler;
import net.heliosphere.enhancements.moshpit.statistics.PlayerData;
import net.heliosphere.enhancements.utils.FileManager;

public class Register {

    private Plugin plugin;
    private PluginManager manager;

    public Register(Plugin plugin) {
        this.plugin = plugin;
        this.manager = plugin.getServer().getPluginManager();

        plugin.saveDefaultConfig();
        new FileManager("messages");

        registerCommands();
        registerEvents();
    }

    private void registerCommands() {
    }

    private void registerEvents() {
        /** Environment */
        manager.registerEvents(new DamageModifier(), plugin);

        /** Moshpit */
        Scheduler.enable();
        manager.registerEvents(new PlayerData(), plugin);
        manager.registerEvents(new EventCanceler(), plugin);
        manager.registerEvents(new RegenSoup(), plugin);
        manager.registerEvents(new RespawnDelay(), plugin);
    }
}