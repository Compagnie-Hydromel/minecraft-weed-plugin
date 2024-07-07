package ch.shkermit.weed.items;

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
import org.bukkit.potion.PotionEffectType;

import ch.shkermit.weed.abilities.Glide;
import ch.shkermit.weed.utils.CommandUtils;
import ch.shkermit.weed.utils.ItemUtils;

public class MatchaCookie implements Listener, CommandExecutor, CraftableItem {
    private Marijuana marijuana = new Marijuana();
    private final String name = this.getClass().getSimpleName().toLowerCase();
    private final String displayName = "§r§aMatcha Cookie";
    private Glide glide;

    public MatchaCookie(Glide glide) {
        this.glide = glide;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return CommandUtils.getItemCommands(sender, args, getItemStack());
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent playerItemConsumeEvent) {
        ItemStack item = playerItemConsumeEvent.getItem();
        Player player = playerItemConsumeEvent.getPlayer();

        if (isSimilar(item)) {
            playerItemConsumeEvent.setCancelled(true);
            ItemUtils.eatItem(player, playerItemConsumeEvent.getHand(), item, 6, 1f);
            
            int effectTimeInSeconds = 5 * 60;

            player.addPotionEffects(ItemUtils.getEffects(
                PotionEffectType.NAUSEA.createEffect(20 * 15, 5),
                PotionEffectType.WEAKNESS.createEffect(20 * 15, 5)));
            glide.addPlayer(player, effectTimeInSeconds);
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
        return new ShapedRecipe(namespacedKey, getItemStack(3))
            .shape("MMM", "WCW")
            .setIngredient('W', Material.WHEAT)
            .setIngredient('C', Material.COCOA_BEANS)
            .setIngredient('M', new RecipeChoice.ExactChoice(marijuana.getItemStack()));
    }

    @Override
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
            13,
            Material.COOKIE,
            amount,
            displayName,
            "§r§7A cookie with matcha 😉😉");
    }

}
