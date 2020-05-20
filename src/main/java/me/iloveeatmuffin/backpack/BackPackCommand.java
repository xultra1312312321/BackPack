package me.iloveeatmuffin.backpack;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BackPackCommand implements CommandExecutor {
    FileManager fm = FileManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(cmd.getName().equalsIgnoreCase("backpack")){
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "後台不能有背包辣");
                return true;
            }

            if(!(sender.hasPermission("backpack.use"))){
                sender.sendMessage(ChatColor.RED + "你沒有權限");
                return true;
            }
            Player player = (Player) sender;
            Inventory inv = Bukkit.createInventory(null,36,ChatColor.BLACK + "背包");
            if(fm.getConfig().contains(player.getUniqueId().toString())){
                for(String i : fm.getConfig().getConfigurationSection(player.getUniqueId().toString()).getKeys(false)){
                    ItemStack item = (ItemStack) fm.getConfig().getConfigurationSection(player.getUniqueId().toString() + "." + i);
                    inv.addItem(item);
                }
            }
            player.openInventory(inv);
            return true;
        }
        return true;
    }
}




















