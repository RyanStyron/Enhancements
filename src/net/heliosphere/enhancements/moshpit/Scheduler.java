package net.heliosphere.enhancements.moshpit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.heliosphere.enhancements.Enhancements;
import net.heliosphere.enhancements.utils.WorldUtils.Moshpit;

public class Scheduler {

    private static Enhancements instance = Enhancements.getInstance();

    @SuppressWarnings("deprecation")
    public static void enable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld() == Moshpit.world()) {
                        player.setMaxHealth(instance.getConfig().getDouble("max-health"));

                        RegenSoup.setSoups(player);

                        // new Objectives(player);
                    }
                }
            }
        }, 0L, 20L);
    }

    // public static class Objectives {
    // private Player player;

    // public Objectives(Player player) {
    // this.player = player;
    // setInitialValues();
    // }

    // @SuppressWarnings("deprecation")
    // public HashMap<Objective, Score> getObjectives() {
    // HashMap<Objective, Score> playerMap = new HashMap<>();
    // Scoreboard scoreboard = player.getScoreboard();

    // Objective kills = scoreboard.getObjective("mpkills");
    // Objective streak = scoreboard.getObjective("mpstreak");
    // Objective coins = scoreboard.getObjective("mpcoins");

    // playerMap.put(kills, kills.getScore(player));
    // playerMap.put(streak, streak.getScore(player));
    // playerMap.put(coins, coins.getScore(player));

    // return playerMap;
    // }

    // @SuppressWarnings("deprecation")
    // public void setInitialValues() {
    // Scoreboard scoreboard = player.getScoreboard();

    // if (scoreboard.getObjective("mpkills").getScore(player) == null)
    // scoreboard.getObjective("mpkills").getScore(player).setScore(0);
    // if (scoreboard.getObjective("mpstreak").getScore(player) == null)
    // scoreboard.getObjective("mpstreak").getScore(player).setScore(0);
    // if (scoreboard.getObjective("mpcoins").getScore(player) == null)
    // scoreboard.getObjective("mpcoins").getScore(player).setScore(0);
    // }
    // }
}
