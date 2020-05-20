package me.iloveeatmuffin.backpack;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;


public class PlayerCloseInvEvent implements Listener {
    FileManager fm = FileManager.getInstance();

    @EventHandler
    public void onInvClass(InventoryCloseEvent e){
        String title = e.getView().getTitle();

        if (!title.equalsIgnoreCase(ChatColor.BLACK + "背包")) return;

        Inventory inv = e.getInventory();

        for (int i = 0; 1 <= 35; i++) {
            ItemStack item = inv.getItem(i);
            if (item == null) continue;
            if (item.getType() == Material.AIR) continue;
            fm.getConfig().set(e.getPlayer().getUniqueId().toString() + "." + i, item);

        }
        try {
            fm.saveConfig();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    }


