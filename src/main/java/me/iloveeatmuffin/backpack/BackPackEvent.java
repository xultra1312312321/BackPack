package me.iloveeatmuffin.backpack;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


import java.io.IOException;


public class BackPackEvent implements Listener, CommandExecutor {
    FileManager fm = FileManager.getInstance();
    Backpack plugin = (Backpack)Backpack.getPlugin(Backpack.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Inventory inv = Bukkit.getServer().createInventory(e.getPlayer(), InventoryType.CHEST, "Backpack");

        if (fm.getConfig().contains("backpacks." + e.getPlayer().getUniqueId())) {
            for (String item : fm.getConfig().getConfigurationSection("backpacks." + e.getPlayer().getUniqueId()).getKeys(false)) {
                inv.addItem(this.plugin.loadItem(fm.getConfig().getConfigurationSection("backpacks." + e.getPlayer().getUniqueId() + "." + item)));
            }
        }

        this.plugin.backpacks.put(e.getPlayer().getUniqueId(), inv);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (!fm.getConfig().contains("backpacks." + e.getPlayer().getUniqueId())) {
            fm.getConfig().createSection("backpacks." + e.getPlayer().getUniqueId());
        }

        char c = 'a';
        for (ItemStack itemStack : this.plugin.backpacks.get(e.getPlayer().getUniqueId())) {
            if (itemStack != null) {
                this.plugin.saveItem(fm.getConfig().createSection("backpacks." + e.getPlayer().getUniqueId() + "." + c++), itemStack);
            }
        }

        try {
            fm.saveConfig();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (cmd.getName().equalsIgnoreCase("backpack")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "後台不能有背包辣");
                    return true;
                }

                if (!(sender.hasPermission("backpack.use"))) {
                    sender.sendMessage(ChatColor.RED + "你沒有權限");
                    return true;
                }
                Player player = (Player) sender;

                if (cmd.getName().equalsIgnoreCase("backpack")) {
                    player.openInventory(this.plugin.backpacks.get(player.getUniqueId()));
                }

                return true;
            }
            return true;}
    }


