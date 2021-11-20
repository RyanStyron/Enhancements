package net.heliosphere.enhancements.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldUtils {

    public static class Moshpit {
        public static World world() {
            return Bukkit.getWorld("hs_moshpit");
        }
    }
}