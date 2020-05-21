
package me.iloveeatmuffin.backpack;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public final class Backpack extends JavaPlugin implements Listener {

    private Connection connection;
    public String host, database, username, password, table;
    public int port;
    FileManager fm = FileManager.getInstance();
    public HashMap<UUID, Inventory> backpacks = new HashMap<UUID, Inventory>();


    @Override
    public void onEnable() {
        FileManager.getInstance().setup(this);
        this.getServer().getPluginManager().registerEvents(new BackPackEvent(), this);
        getCommand("backpack").setExecutor(new BackPackEvent());
        loadConfig();
        mysqlSetup();

    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        for (Map.Entry<UUID, Inventory> entry : backpacks.entrySet()) {
            if (!fm.getConfig().contains("backpacks." + entry.getKey())) {
                fm.getConfig().createSection("backpacks." + entry.getKey());
            }

            char c = 'a';
            for (ItemStack itemStack : entry.getValue()) {
                if (itemStack != null) {
                    saveItem(fm.getConfig().createSection("backpacks." + entry.getKey() + "." + c++), itemStack);
                }
            }

            try {
                fm.saveConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveItem(ConfigurationSection section, ItemStack itemStack) {
        section.set("itemstack", itemStack);


    }

    public ItemStack loadItem(ConfigurationSection section) {

        return new ItemStack(Objects.requireNonNull(section.getItemStack("itemstack")));


    }






    public void mysqlSetup(){
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        table = this.getConfig().getString("table");
        try{

            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + this.host + ":"
                        + this.port + "/" + this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL連接了");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
    public void setConnection(Connection connection){
        this.connection = connection;
    }

}