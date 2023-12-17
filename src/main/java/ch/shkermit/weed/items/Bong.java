package ch.shkermit.weed.items;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffectType;

import ch.shkermit.weed.utils.CommandUtils;
import ch.shkermit.weed.utils.ItemUtils;

public class Bong implements Listener, CommandExecutor, CraftableItem {
    private Marijuana marijuana = new Marijuana();
    private final String name = this.getClass().getSimpleName().toLowerCase();;
    private final String displayName = "§rBong";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandUtils.getItemCommands(sender, args, getItemStack());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack item = playerInteractEvent.getItem();
        if(item == null) return;
        
        if(isSimilar(item) && playerInteractEvent.getAction().name().contains("RIGHT")) {
            playerInteractEvent.setCancelled(true);
            if(marijuana.isSimilar(player.getInventory().getItemInOffHand())) {
                ItemStack itemInInventory = player.getInventory().getItemInOffHand();
                itemInInventory.setAmount(itemInInventory.getAmount() - 1);
                player.addPotionEffects(ItemUtils.getEffects(
                    PotionEffectType.SLOW_FALLING.createEffect(20 * 45, 255),
                    PotionEffectType.LEVITATION.createEffect(20 * 45, 255)
                ));

                ItemUtils.smoke(player.getWorld(), player.getLocation());
            }else {
                player.sendMessage("§r§cPut marijuana in your second hand to smoke it");
            }
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
        return ItemUtils.containSpecifiedIdTag(itemStack, getName());
    }

    @Override
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
            Material.GLASS_BOTTLE, 
            amount, 
            displayName,
            name,
            "§r§7A bong to smoke weed with",
            "§r§7Brooooo");
    }

    @Override
    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey) {
        return new ShapedRecipe(namespacedKey, getItemStack())
            .shape("Gaa", "aGG", "aGA")
            .setIngredient('G', Material.GLASS)
            .setIngredient('A', Material.AMETHYST_SHARD)
            .setIngredient('a', Material.AIR);
    }
}
