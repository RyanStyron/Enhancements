package net.heliosphere.enhancements.moshpit;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.heliosphere.enhancements.Enhancements;
import net.heliosphere.enhancements.utils.MessageUtils;
import net.heliosphere.enhancements.utils.WorldUtils.Moshpit;

public class RespawnDelay implements Listener {

    private Enhancements plugin = Enhancements.getInstance();
    private FileConfiguration config = plugin.getConfig();
    private HashMap<Player, Integer> taskMap = new HashMap<>();
    private HashMap<Player, Integer> timeMap = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getWorld() == Moshpit.world()) {
            timeMap.put(player, config.getInt("respawn-delay"));

            taskMap.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Location location = player.getLocation();

                    if (player.getWorld() == Moshpit.world()) {
                        Location spawn = player.getWorld().getSpawnLocation();

                        if (timeMap.get(player) != null) {
                            int time = timeMap.get(player);

                            if (!Moshpit.isWithinSpawn(location)) {
                                player.teleport(spawn);
                                MessageUtils.sendMessage(player, "respawn-error", "<remaining-seconds>", time
                                        + "");
                            }
                            timeMap.put(player, time - 1);

                            if (time - 1 == 0)
                                timeMap.remove(player);
                        } else
                            Bukkit.getScheduler().cancelTask(taskMap.get(player));
                    }
                }
            }, 20, 20));
        }
    }
}
