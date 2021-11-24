package net.heliosphere.enhancements;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import net.heliosphere.enhancements.environment.DamageModifier;
import net.heliosphere.enhancements.moshpit.EventCanceler;
import net.heliosphere.enhancements.moshpit.RegenSoup;
import net.heliosphere.enhancements.moshpit.Scheduler;
import net.heliosphere.enhancements.moshpit.statistics.PlayerData;

public class Register {

    private Plugin plugin;
    private PluginManager manager;

    public Register(Plugin plugin) {
        this.plugin = plugin;
        manager = plugin.getServer().getPluginManager();

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
    }
}