package net.heliosphere.enhancements.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import net.heliosphere.enhancements.Enhancements;

public class WorldUtils {

    private static Enhancements plugin = Enhancements.getInstance();
    private static FileConfiguration config = plugin.getConfig();

    public static class Moshpit {
        public static World world() {
            return Bukkit.getWorld(config.getString("moshpit-world"));
        }

        public static int spawnDistance() {
            return config.getInt("spawn-distance");
        }

        public static int spawnDistanceSquared() {
            return spawnDistance() * spawnDistance();
        }
    }
}