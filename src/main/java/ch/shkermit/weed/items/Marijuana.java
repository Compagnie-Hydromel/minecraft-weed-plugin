package ch.shkermit.weed.items;

import java.util.Random;

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

public class Marijuana implements Listener, CommandExecutor, CraftableItem {
    private final String name = this.getClass().getSimpleName().toLowerCase();
    private final String displayName = "§rMarijuana";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandUtils.getItemCommands(sender, args, getItemStack());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        Block theBlock = blockBreakEvent.getBlock();
        
        if (theBlock.getType() == Material.GRASS && new Random().nextInt(1000) < 10) {
            theBlock.getWorld().dropItem(theBlock.getLocation(), getItemStack());
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent playerItemConsumeEvent) {
        ItemStack item = playerItemConsumeEvent.getItem();
        Player player = playerItemConsumeEvent.getPlayer();

        if (isSimilar(item)) {
            playerItemConsumeEvent.setCancelled(true);
            ItemUtils.eatItem(player, playerItemConsumeEvent.getHand(), item, 1, 0.5f);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean isSimilar(ItemStack itemStack) {
        return ItemUtils.isSimilar(itemStack, getItemStack());
    }

    @Override
    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey) {
        return new ShapedRecipe(namespacedKey, getItemStack(2))
            .shape("BBB", "BMB", "BBB")
            .setIngredient('M', new RecipeChoice.ExactChoice(getItemStack()))
            .setIngredient('B', Material.BONE_MEAL);
    }

    @Override
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
            10,
            Material.CARROT, 
            amount, 
            displayName,
            "§r§7A plant that can be smoked", 
            "§r§7to get high.");
    }
}
