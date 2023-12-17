package ch.shkermit.weed.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public interface CraftableItem extends Item {
    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey);
}
