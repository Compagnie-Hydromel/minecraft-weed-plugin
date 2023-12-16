package ch.shkermit.weed;

import org.bukkit.plugin.java.JavaPlugin;

import ch.shkermit.weed.items.Marijuana;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		System.out.println(" 🚬 Weed is enabled");

		// news items
		Marijuana marijuana = new Marijuana();
		
		// Event Listeners
		getServer().getPluginManager().registerEvents(marijuana, this);

		// Commands
		getCommand("marijuana").setExecutor(marijuana);

		super.onEnable();
	}
}
