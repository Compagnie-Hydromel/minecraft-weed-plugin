package ch.shkermit.weed.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandUtils {
    public static boolean getItemCommands(CommandSender sender, String[] args, ItemStack item) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Player target = player;

            if (args.length > 0) {
                target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    player.sendMessage("§r§cPlayer not found.");
                    return true;
                }
            }
            
            target.getInventory().addItem(item);
            return true;
        }
        return false;
    }
}
