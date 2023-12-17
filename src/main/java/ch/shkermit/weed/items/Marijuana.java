package ch.shkermit.weed.items;

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

import ch.shkermit.weed.utils.CommandUtils;
import ch.shkermit.weed.utils.ItemUtils;

public class Marijuana implements Listener, CommandExecutor, Item {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandUtils.getItemCommands(sender, args, getItemStack());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        Block theBlock = blockBreakEvent.getBlock();
        
        if (theBlock.getType() == Material.GRASS && Math.random() < 0.5) {
            theBlock.getWorld().dropItem(theBlock.getLocation(), getItemStack());
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent playerItemConsumeEvent) {
        ItemStack item = playerItemConsumeEvent.getItem();
        Player player = playerItemConsumeEvent.getPlayer();

        if (item.isSimilar(getItemStack())) {
            playerItemConsumeEvent.setCancelled(true);
            ItemUtils.eatItem(player, playerItemConsumeEvent.getHand(), item, 1, 0.5f);
        }
    }

    @Override
    public String getName() {
        return "marijuana";
    }

    @Override
    public String getDisplayName() {
        return "§rMarijuana";
    }

    @Override
    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey) {
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, getItemStack(2));
        recipe.shape(
                "BBB",
                "BMB",
                "BBB");
        recipe.setIngredient('M', new RecipeChoice.MaterialChoice.ExactChoice(getItemStack()));
        recipe.setIngredient('B', Material.BONE_MEAL);
        return recipe;
    }

    @Override
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
            Material.CARROT, 
            amount, 
            "§rMarijuana", 
            "§r§7A plant that can be smoked", 
            "§r§7to get high.");
    }
}
