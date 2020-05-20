package me.iloveeatmuffin.backpack;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class Backpack extends JavaPlugin implements Listener {
    private Connection connection;
    public String host,database,username,password,table;
    public int port;
    FileManager fm = FileManager.getInstance();


    @Override
    public void onEnable() {
        FileManager.getInstance().setup(this);
        this.getServer().getPluginManager().registerEvents(new PlayerCloseInvEvent(), this);
        getCommand("backpack").setExecutor(new BackPackCommand());
        loadConfig();
        mysqlSetup();

    }
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
