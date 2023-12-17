package ch.shkermit.weed.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    public static ItemStack createItem(Material material, int amount, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> loreList = Arrays.asList(lore);
        itemMeta.setLore(loreList);
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
}
