package ch.shkermit.weed.event;

import java.io.UnsupportedEncodingException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class texturePackAutoAdd implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws UnsupportedEncodingException {
        Player player = event.getPlayer();

        player.setResourcePack("https://github.com/Compagnie-Hydromel/minecraft-weed-plugin/releases/download/1.0.3/weed-pack-1.0.3.zip");
    }
}
