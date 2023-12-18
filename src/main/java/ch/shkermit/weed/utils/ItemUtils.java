package ch.shkermit.weed.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class ItemUtils {

    public static ItemStack createItem(int itemCustomModelData, Material material, int amount, String displayName, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        List<String> loreList = Arrays.asList(lore);
        itemMeta.setLore(loreList);
        itemMeta.setCustomModelData(itemCustomModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void eatItem(Player eater, EquipmentSlot equipmentSlot, ItemStack item, int foodLevelToAdd, float saturationLevelToAdd) {
        if(item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
            eater.getInventory().setItem(equipmentSlot, item);
        } else eater.getInventory().setItem(equipmentSlot, null);
        eater.setFoodLevel(eater.getFoodLevel() + foodLevelToAdd);
        eater.setSaturation(eater.getSaturation() + saturationLevelToAdd);
    }

    public static boolean isSimilar(ItemStack itemStack, ItemStack itemExcepted) {
        if (itemStack == null || itemStack.getItemMeta() == null || itemStack.getType() == null || !itemStack.getItemMeta().hasCustomModelData() || !itemExcepted.getItemMeta().hasCustomModelData()) return false;
        return itemStack.getItemMeta().getCustomModelData() == itemExcepted.getItemMeta().getCustomModelData() && itemStack.getType() == itemExcepted.getType();
    }

    public static void smoke(World world, Location location) {
        location.setY(location.getY() + 1);
        for (int i = 0; i < 10; i++) {
            world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0);
            location.setY(location.getY() + 0.2);
        }
    }

    public static List<PotionEffect> getEffects(PotionEffect... potionEffects) {
        return Arrays.asList(potionEffects);
    }
}
