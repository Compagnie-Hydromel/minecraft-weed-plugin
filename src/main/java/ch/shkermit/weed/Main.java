package ch.shkermit.weed;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ch.shkermit.weed.items.Item;
import ch.shkermit.weed.items.Marijuana;
import ch.shkermit.weed.items.MatchaCookie;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		// news items
		Marijuana marijuana = new Marijuana();
		MatchaCookie matchaCookie = new MatchaCookie();
		List<? extends Item> items = Arrays.asList(marijuana, matchaCookie);
		
		for(Item item : items) {
			getServer().getPluginManager().registerEvents((Listener) item, this);
			Bukkit.addRecipe(item.getCraftingRecipe(new NamespacedKey(this, item.getName())));
			getCommand(item.getName()).setExecutor((CommandExecutor) item);
		}

		super.onEnable();
	}
}
