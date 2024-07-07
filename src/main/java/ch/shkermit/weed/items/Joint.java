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
import org.bukkit.potion.PotionEffectType;

import ch.shkermit.weed.Main;
import ch.shkermit.weed.abilities.Glide;
import ch.shkermit.weed.utils.CommandUtils;
import ch.shkermit.weed.utils.ItemUtils;

public class Joint implements Listener, CommandExecutor, CraftableItem {
    private Glide glide;
    private Marijuana marijuana = new Marijuana();
    private JointCul joinCul = new JointCul();
    private final String name = this.getClass().getSimpleName().toLowerCase();;
    private final String displayName = "§rJoint";
    private List<Player> playersHigh = new ArrayList<Player>();

    public Joint(Glide glide) {
        this.glide = glide;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack item = playerInteractEvent.getItem();
        if(item == null) return;
        
        if(isSimilar(item) && playerInteractEvent.getAction().name().contains("RIGHT") && !playersHigh.contains(player)) {
            playerInteractEvent.setCancelled(true);

            int time = 30;

            glide.addPlayer(player, time);
            
            ItemUtils.smoke(player.getWorld(), player.getLocation());

            item.setAmount(item.getAmount() - 1);

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
        return new ShapedRecipe(namespacedKey, getItemStack())
            .shape("MP")
            .setIngredient('P', Material.PAPER)
            .setIngredient('M', new RecipeChoice.ExactChoice(marijuana.getItemStack()));
    }

    @Override
    public boolean isSimilar(ItemStack itemStack) {
        return ItemUtils.isSimilar(itemStack, getItemStack());
    }

    @Override
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
            11,
            Material.BLAZE_ROD,
            amount,
            displayName,
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
            player.addPotionEffects(ItemUtils.getEffects(
                PotionEffectType.SLOW_FALLING.createEffect(20 * 15, 255),
                PotionEffectType.NAUSEA.createEffect(20 * 15, 255)));

            ItemUtils.smoke(player.getWorld(), player.getLocation());

            player.getInventory().addItem(joinCul.getItemStack());

            playersHigh.remove(player);
        }
    }
}
