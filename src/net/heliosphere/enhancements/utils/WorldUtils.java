package net.heliosphere.enhancements.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

        public static boolean isWithinSpawn(Location location) {
            List<Integer> spawnCoords = config.getIntegerList("spawn-region");
            Location border1 = new Location(world(), spawnCoords.get(0), spawnCoords.get(1), spawnCoords.get(2));
            Location border2 = new Location(world(), spawnCoords.get(3), spawnCoords.get(4), spawnCoords.get(5));

            if (isWithinRegion(location, border1, border2))
                return true;
            return false;
        }
    }

    /*
     * Determines if a location is within a cuboid region defined by opposing border
     * locations.
     */
    public static boolean isWithinRegion(Location location, Location border1, Location border2) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        int x1 = Math.min(border1.getBlockX(), border2.getBlockX());
        int y1 = Math.min(border1.getBlockY(), border2.getBlockY());
        int z1 = Math.min(border1.getBlockZ(), border2.getBlockZ());
        int x2 = Math.max(border1.getBlockX(), border2.getBlockX());
        int y2 = Math.max(border1.getBlockY(), border2.getBlockY());
        int z2 = Math.max(border1.getBlockZ(), border2.getBlockZ());

        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }
}