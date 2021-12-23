package net.heliosphere.enhancements.moshpit.statistics;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.heliosphere.enhancements.Enhancements;
import net.heliosphere.enhancements.moshpit.statistics.PlayerDataFile.Data;
import net.heliosphere.enhancements.utils.EconomyUtils;
import net.heliosphere.enhancements.utils.WorldUtils.Moshpit;

public class PlayerData implements Listener {

    private Enhancements plugin = Enhancements.getInstance();
    private FileConfiguration config = plugin.getConfig();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getWorld() == Moshpit.world()) {
            PlayerDataFile data = new PlayerDataFile(player);

            data.setData(Data.DEATHS, data.getData(Data.DEATHS) + 1);
            data.setData(Data.STREAK, 0);

            if (EconomyUtils.getBalance(player) != data.getData(Data.COINS))
                data.setData(Data.COINS, EconomyUtils.getBalance(player));

            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                data = new PlayerDataFile(killer);

                data.setData(Data.KILLS, data.getData(Data.KILLS) + 1);
                data.setData(Data.STREAK, data.getData(Data.STREAK) + 1);

                if (data.getData(Data.STREAK) > data.getData(Data.HIGHSTREAK))
                    data.setData(Data.HIGHSTREAK, data.getData(Data.STREAK));

                if (EconomyUtils.getBalance(killer) != data.getData(Data.COINS))
                    data.setData(Data.COINS, EconomyUtils.getBalance(killer));
                int coinsGained = config.getInt("coins-per-kill");

                data.setData(Data.COINS, data.getData(Data.COINS) + coinsGained);

                if (coinsGained != 0 && config.getBoolean("coin-stacking-enabled"))
                    data.setData(Data.COINS, data.getData(Data.COINS) + data.getData(Data.STREAK) - 1);
                EconomyUtils.setBalance(killer, data.getData(Data.COINS));
            }
        }
    }
}
