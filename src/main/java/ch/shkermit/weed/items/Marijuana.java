package ch.shkermit.weed.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import ch.shkermit.weed.Main;

public class Marijuana implements Listener, CommandExecutor {
    public Marijuana() {
        super();
        Bukkit.addRecipe(getCraftingRecipe(new NamespacedKey(Main.getPlugin(Main.class), "marijuana")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
            
            target.getInventory().addItem(getMarijuanaItem());
            return true;
        }
        return false;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        Block theBlock = blockBreakEvent.getBlock();
        
        if (theBlock.getType() == Material.GRASS && Math.random() < 0.5) {
            theBlock.getWorld().dropItem(theBlock.getLocation(), getMarijuanaItem());
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent playerItemConsumeEvent) {
        ItemStack item = playerItemConsumeEvent.getItem();
        Player player = playerItemConsumeEvent.getPlayer();
        if (item.isSimilar(getMarijuanaItem())) {
            playerItemConsumeEvent.setCancelled(true);
            if(item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
                player.getInventory().setItem(playerItemConsumeEvent.getHand(), item);
            } else player.getInventory().remove(item);
            player.setFoodLevel(player.getFoodLevel() + 1);
        }
    }

    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey) {
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, getMarijuanaItem(2));
        recipe.shape(
                "BBB",
                "BMB",
                "BBB");
        recipe.setIngredient('M', new RecipeChoice.MaterialChoice.ExactChoice(getMarijuanaItem()));
        recipe.setIngredient('B', Material.BONE_MEAL);
        return recipe;
    }

    private ItemStack getMarijuanaItem() {
        return getMarijuanaItem(1);
    }

    private ItemStack getMarijuanaItem(int amount) {
        ItemStack marijuana = new ItemStack(Material.CARROT);
        marijuana.setAmount(amount);
        ItemMeta marijuanaMeta = marijuana.getItemMeta();
        marijuanaMeta.setDisplayName("§rMarijuana");
        List<String> lore = new ArrayList<String>();
        lore.add("§r§7A plant that can be smoked");
        lore.add("§r§7to get high.");
        marijuanaMeta.setLore(lore);
        marijuana.setItemMeta(marijuanaMeta);
        return marijuana;
    }
}
