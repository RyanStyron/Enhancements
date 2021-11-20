package net.heliosphere.enhancements.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.heliosphere.enhancements.Enhancements;

public class FileManager {

    private Enhancements plugin = Enhancements.getInstance();
    private FileConfiguration data;
    private File dataFile;
    private String name;

    public FileManager(String name) {
        this.name = name;
        setup();
    }

    public void setup() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        dataFile = new File(plugin.getDataFolder(), name + ".yml");

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().severe("Could not create " + name + ".yml!");
            }
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(dataFile);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save " + name + ".yml!");
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dataFile);
    }
}
