package ch.shkermit.weed.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public interface Item {
    public String getName();
    
    public String getDisplayName();

    public ShapedRecipe getCraftingRecipe(NamespacedKey namespacedKey);

    public ItemStack getItemStack();

    public ItemStack getItemStack(int amount);
}
