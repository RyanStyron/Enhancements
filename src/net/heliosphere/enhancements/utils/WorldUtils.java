package net.heliosphere.enhancements.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class WorldUtils {

    public static class Moshpit {
        public static World world() {
            return Bukkit.getWorld("world_moshpit");
        }

        public static Location spawnLocation() {
            return world().getSpawnLocation();
        }

        @SuppressWarnings("deprecation")
        public static void setInitialValues(Player player) {
            Scoreboard scoreboard = player.getScoreboard();

            if (scoreboard.getObjective("mpkills") == null)
                scoreboard.getObjective("mpkills").getScore(player).setScore(0);
            if (scoreboard.getObjective("mpstreak") == null)
                scoreboard.getObjective("mpstreak").getScore(player).setScore(0);
            if (scoreboard.getObjective("mpcoins") == null)
                scoreboard.getObjective("mpcoins").getScore(player).setScore(0);
        }
    }

    public static class MoshpitClassic {
        public static World world() {
            return Bukkit.getWorld("world_moshpit_classic");
        }

        public static Location spawnLocation() {
            return world().getSpawnLocation();
        }

        @SuppressWarnings("deprecation")
        public static void setInitialValues(Player player) {
            Scoreboard scoreboard = player.getScoreboard();

            if (scoreboard.getObjective("mpckills") == null)
                scoreboard.getObjective("mpckills").getScore(player).setScore(0);
            if (scoreboard.getObjective("mpcstreak") == null)
                scoreboard.getObjective("mpcstreak").getScore(player).setScore(0);
        }
    }
}