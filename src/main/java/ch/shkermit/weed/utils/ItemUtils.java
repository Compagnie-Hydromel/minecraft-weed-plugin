package ch.shkermit.weed.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import ch.shkermit.weed.Main;

public class ItemUtils {

    public static ItemStack createItem(Material material, int amount, String displayName, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        List<String> loreList = Arrays.asList(lore);
        itemMeta.setLore(loreList);
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(Main.getPlugin(Main.class), name), PersistentDataType.STRING, "weed_type: " + name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void eatItem(Player eater, EquipmentSlot equipmentSlot, ItemStack item, int foodLevelToAdd, float saturationLevelToAdd) {
        if(item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
            eater.getInventory().setItem(equipmentSlot, item);
        } else eater.getInventory().setItem(equipmentSlot, item);
        eater.setFoodLevel(eater.getFoodLevel() + foodLevelToAdd);
        eater.setSaturation(eater.getSaturation() + saturationLevelToAdd);
    }

    public static boolean isSimilar(ItemStack itemStack, String name) {
        return itemStack.getItemMeta().getPersistentDataContainer().has(getNamespacedKey(name), PersistentDataType.STRING) && itemStack.getItemMeta().getPersistentDataContainer().get(getNamespacedKey(name), PersistentDataType.STRING).equals("weed_type: " + name);
    }

    public static void smoke(World world, Location location) {
        location.setY(location.getY() + 1);
        for (int i = 0; i < 10; i++) {
            world.spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, location, 0);
            location.setY(location.getY() + 0.2);
        }
    }

    private static NamespacedKey getNamespacedKey(String name) {
        return new NamespacedKey(Main.getPlugin(Main.class), name);
    }
}
