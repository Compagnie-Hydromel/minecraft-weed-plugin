package ch.shkermit.weed.items;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import ch.shkermit.weed.utils.CommandUtils;
import ch.shkermit.weed.utils.ItemUtils;

public class JointCul implements Listener, CommandExecutor, Item {
    private final String name = this.getClass().getSimpleName().toLowerCase();;
    private final String displayName = "§rCul du joint";

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
            item.setAmount(item.getAmount() - 1);

            int effectTime = 20 * 15;

            player.addPotionEffects(ItemUtils.getEffects(
                PotionEffectType.SLOW_FALLING.createEffect(effectTime, 1),
                PotionEffectType.DARKNESS.createEffect(effectTime, 1)));

            if(player.getHealth() < 20) player.setHealth(player.getHealth() + 1);
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
    public ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Override
    public ItemStack getItemStack(int amount) {
        return ItemUtils.createItem(
                Material.STICK,
                amount,
                displayName,
                name,
                "§r§7You exaclty know what you gonna smoke bro",
                "§r§7You fucking druggo");
    }
}
