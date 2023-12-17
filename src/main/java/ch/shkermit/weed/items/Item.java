package ch.shkermit.weed.items;

import org.bukkit.inventory.ItemStack;

public interface Item {
    public String getName();
    
    public String getDisplayName();

    public boolean isSimilar(ItemStack itemStack);

    public ItemStack getItemStack();

    public ItemStack getItemStack(int amount);
}
