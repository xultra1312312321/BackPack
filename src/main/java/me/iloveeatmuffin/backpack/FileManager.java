package me.iloveeatmuffin.backpack;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FileManager {

    static FileManager instance = new FileManager();
    //Make a new file
    FileConfiguration config;
    File cfile;

    public static FileManager getInstance() {
        return instance;
    }

    //Setup the files
    public void setup(Plugin p) {
        //The name of the file
        cfile = new File(p.getDataFolder(), "backpackitem.yml");
        config = YamlConfiguration.loadConfiguration(cfile);

        if (!(cfile.exists())) {
            config.options().header("作者:ILoveEatMuffin\n所有物品會save在這裡owob");
            try {
                config.save(cfile);
            } catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage("錯誤!!!不能生成 backpackitem.yml");
            }
        }

    }

    //Use this to do like fm.getData().getStringList();
    public FileConfiguration getConfig() {
        return config;
    }

    //Save the file
    public void saveConfig() throws IOException {
        config.save(cfile);
    }

    //Reload the file
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }
}

