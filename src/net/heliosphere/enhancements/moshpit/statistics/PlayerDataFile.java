package net.heliosphere.enhancements.moshpit.statistics;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.heliosphere.enhancements.utils.FileManager;

public class PlayerDataFile {

    public enum Data {
        COINS, KILLS, DEATHS, STREAK, HIGHSTREAK;
    }

    private UUID playerId;
    private FileManager manager;
    private FileConfiguration file;

    public PlayerDataFile(Player player) {
        this.playerId = player.getUniqueId();
        this.manager = new FileManager("PlayerData");
        this.file = manager.getData();
    }

    public void setDefaults() {
        if (file.getString(playerId.toString()) == null) {
            file.set(playerId + ".coins", 0);
            file.set(playerId + ".kills", 0);
            file.set(playerId + ".deaths", 0);
            file.set(playerId + ".streak", 0);
            file.set(playerId + ".highstreak", 0);
            manager.saveData();
        }
    }

    public void setData(Data data, int setValue) {
        file.set(playerId + "." + data.toString().toLowerCase(), setValue);
        manager.saveData();
    }

    public int getData(Data data) {
        return file.getInt(playerId + "." + data.toString().toLowerCase());
    }
}
