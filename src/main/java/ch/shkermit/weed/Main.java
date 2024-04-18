package ch.shkermit.weed;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ch.shkermit.weed.abilities.Glide;
import ch.shkermit.weed.event.texturePackAutoAdd;
import ch.shkermit.weed.items.Bong;
import ch.shkermit.weed.items.CraftableItem;
import ch.shkermit.weed.items.Item;
import ch.shkermit.weed.items.JointCul;
import ch.shkermit.weed.items.Marijuana;
import ch.shkermit.weed.items.MatchaCookie;
import ch.shkermit.weed.items.Joint;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		Glide glide = new Glide();
		List<? extends Item> items = Arrays.asList(new Marijuana(), new MatchaCookie(glide), new Joint(glide), new JointCul(), new Bong(glide));
		List<Listener> listeners = Arrays.asList(glide, new texturePackAutoAdd());
		
		for(Item item : items) {
			if(item instanceof Listener) getServer().getPluginManager().registerEvents((Listener) item, this);
			if(item instanceof CommandExecutor) getCommand(item.getName()).setExecutor((CommandExecutor) item);
			if(item instanceof CraftableItem) Bukkit.addRecipe(((CraftableItem) item).getCraftingRecipe(new NamespacedKey(this, item.getName())));
		}

		for (Listener listener : listeners) {
			getServer().getPluginManager().registerEvents(listener, this);
		}

		super.onEnable();
	}
}
