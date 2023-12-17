package ch.shkermit.weed.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ch.shkermit.weed.utils.CommandUtils;
import ch.shkermit.weed.utils.ItemUtils;

public class MatchaCookie implements Listener, CommandExecutor, Item {
    private Marijuana marijuana = new Marijuana();
    private final String name = this.getClass().getSimpleName().toLowerCase();
    private final String displayName = "§r§aMatcha Cookie";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandUtils.getItemCommands(sender, args, getItemStack());
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent playerItemConsumeEvent) {
        ItemStack item = playerItemConsumeEvent.getItem();
        Player player = playerItemConsumeEvent.getPlayer();

        if (isSimilar(item)) {
            List<PotionEffect> effect = new ArrayList<PotionEffect>();
            int effectTime = 20 * 60 * 5;

            playerItemConsumeEvent.setCancelled(true);
            ItemUtils.eatItem(player, playerItemConsumeEvent.getHand(), item, 6, 1f);

            effect.add(PotionEffectType.CONFUSION.createEffect(20 * 15, 5));
            effect.add(PotionEffectType.WEAKNESS.createEffect(20 * 15, 5));
            effect.add(PotionEffectType.LEVITATION.createEffect(effectTime, 255));
            effect.add(PotionEffectType.SLOW_FALLING.createEffect(effectTime, 255));

            player.addPotionEffects(effect);
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
        return ItemUtils.isSimilar(itemStack, getName());
    }

    @Override
    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey) {
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, getItemStack(3));

        recipe.shape("MMM", "WCW");
        recipe.setIngredient('W', Material.WHEAT);
        recipe.setIngredient('C', Material.COCOA_BEANS);
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(marijuana.getItemStack()));
        return recipe;
    }

    @Override
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
                Material.COOKIE,
                amount,
                displayName,
                name,
                "§r§7A cookie with matcha 😉😉");
    }

}
