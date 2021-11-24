package net.heliosphere.enhancements.moshpit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.heliosphere.enhancements.Enhancements;
import net.heliosphere.enhancements.moshpit.statistics.PlayerDataFile;
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

                        PlayerDataFile dataFile = new PlayerDataFile(player);

                        dataFile.setDefaults();
                    }
                }
            }
        }, 0L, 20L);
    }
}
