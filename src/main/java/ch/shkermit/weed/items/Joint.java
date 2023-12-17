package ch.shkermit.weed.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ch.shkermit.weed.Main;
import ch.shkermit.weed.utils.CommandUtils;
import ch.shkermit.weed.utils.ItemUtils;

public class Joint implements Listener, CommandExecutor, Item {
    private Marijuana marijuana = new Marijuana();
    private final String name = this.getClass().getSimpleName().toLowerCase();;
    private final String displayName = "§rJoint";
    private List<Player> playersHigh = new ArrayList<Player>();

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack item = playerInteractEvent.getItem();
        if(item == null) return;
        
        if(isSimilar(item) && playerInteractEvent.getAction().name().contains("RIGHT") && !playersHigh.contains(player)) {
            List<PotionEffect> effect = new ArrayList<PotionEffect>();

            playerInteractEvent.setCancelled(true);

            effect.add(PotionEffectType.LEVITATION.createEffect(20 * 30, 255));
            effect.add(PotionEffectType.SLOW_FALLING.createEffect(20 * 30, 255));

            player.addPotionEffects(effect);
            
            ItemUtils.smoke(player.getWorld(), player.getLocation());

            playerInteractEvent.getItem().setAmount(playerInteractEvent.getItem().getAmount() - 1);

            playersHigh.add(player);

            Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), new setPlayerMoreHighAfterDelay(player), 20 * 30);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandUtils.getItemCommands(sender, args, getItemStack());
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
    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey) {
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, getItemStack());
        recipe.shape("MP");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(marijuana.getItemStack()));
        return recipe;
    }

    @Override
    public boolean isSimilar(ItemStack itemStack) {
        return ItemUtils.isSimilar(itemStack, getName());
    }

    @Override
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
                Material.BLAZE_ROD,
                amount,
                displayName,
                name,
                "§r§7A rolled marijuana cigarette",
                "§r§7to get high... Smoke it.",
                "§r§7You will be good my men.");
    }

    private class setPlayerMoreHighAfterDelay implements Runnable {
        private Player player;

        public setPlayerMoreHighAfterDelay(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            List<PotionEffect> effect = new ArrayList<PotionEffect>();
            
            effect.add(PotionEffectType.SLOW_FALLING.createEffect(20 * 15, 255));
            effect.add(PotionEffectType.CONFUSION.createEffect(20 * 15, 255));

            player.addPotionEffects(effect);

            ItemUtils.smoke(player.getWorld(), player.getLocation());

            playersHigh.remove(player);
        }
    }
}
