package ch.shkermit.weed.abilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import ch.shkermit.weed.Main;

public class Glide implements Listener {
    private List<UUID> players = new ArrayList<>();
    private Map<UUID, LocalDateTime> playerTimeToStopEffect = new HashMap<>();
    private PersistentDataContainer dataContainer;

    public Glide() {
        this.dataContainer = Bukkit.getServer().getWorlds().get(0).getPersistentDataContainer();
        String stringPlayerTimeToStopEffect = dataContainer.getOrDefault(new NamespacedKey(Main.getPlugin(Main.class), "flying-player-time"), PersistentDataType.STRING, "");

        String keyValuePairs = stringPlayerTimeToStopEffect.replace("{", "").replace("}", "");
        String[] pairs = keyValuePairs.split(",");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                UUID key = UUID.fromString(keyValue[0]);
                LocalDateTime value = LocalDateTime.parse(keyValue[1]);
                playerTimeToStopEffect.put(key, value);
            }
        }

        String stringListOfPlayerUUID = dataContainer.getOrDefault(new NamespacedKey(Main.getPlugin(Main.class), "flying-player"), PersistentDataType.STRING, "");
        for(String playerUUID : stringListOfPlayerUUID.split(",")) {
            try {
                UUID.fromString(playerUUID);
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player joiningPlayer = playerJoinEvent.getPlayer();
        for(UUID flyingPlayer : players) {
            if (flyingPlayer.equals(joiningPlayer.getUniqueId())) {
                players.remove(flyingPlayer);
                addPlayer(flyingPlayer);
            }
        }
    }
    
    @EventHandler
    private void onPlayerMovementCheck(PlayerMoveEvent playerMoveEvent) {
        Player player = playerMoveEvent.getPlayer();
        if(players.contains(player.getUniqueId()) && playerMoveEvent.getFrom().getY() != playerMoveEvent.getTo().getY()) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setFlySpeed(0.04f);
            Location location = playerMoveEvent.getTo();
            location.setY(playerMoveEvent.getFrom().getY());
            player.teleport(location);
        }
        if (playerTimeToStopEffect.containsKey(player.getUniqueId()) && LocalDateTime.now().isAfter(playerTimeToStopEffect.get(player.getUniqueId()))) {
            removePlayer(player);
        }
    }

    @EventHandler
    private void onPlayerDeathEvent(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        if(players.contains(player.getUniqueId())) {  
            removePlayer(player);
        }
    }

    public void addPlayer(Player player) {
        addPlayer(player.getUniqueId(), 0);
    }

    public void addPlayer(Player player, int effectTimeInSeconds) {
        addPlayer(player.getUniqueId(), effectTimeInSeconds);
    }

    public void addPlayer(UUID playerUUID) {
        addPlayer(playerUUID, 0);
    }

    public void addPlayer(UUID playerUUID, int effectTimeInSeconds) {
        if (!players.contains(playerUUID)) {
            players.add(playerUUID);
        }

        if(effectTimeInSeconds > 0) {
            if (playerTimeToStopEffect.containsKey(playerUUID)) 
                playerTimeToStopEffect.replace(playerUUID, playerTimeToStopEffect.get(playerUUID).plusSeconds(effectTimeInSeconds));
            else 
                playerTimeToStopEffect.put(playerUUID, LocalDateTime.now().plusSeconds(effectTimeInSeconds));
        }

        saveFlyingPlayers();
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.setFlySpeed(0.1f);
        if (!Arrays.asList(GameMode.CREATIVE, GameMode.SPECTATOR).contains(player.getGameMode())) {
            player.setFlying(false);
            player.setAllowFlight(false);
        }
        playerTimeToStopEffect.remove(player.getUniqueId());
        saveFlyingPlayers();
    }

    private void saveFlyingPlayers() {
        StringBuilder stringListOfPlayerUUID = new StringBuilder();
        for(UUID player : players) {
            stringListOfPlayerUUID.append(player.toString()).append(",");
        }
        dataContainer.set(new NamespacedKey(Main.getPlugin(Main.class), "flying-player"), PersistentDataType.STRING, stringListOfPlayerUUID.toString());
        dataContainer.set(new NamespacedKey(Main.getPlugin(Main.class), "flying-player-time"), PersistentDataType.STRING, playerTimeToStopEffect.toString());
    }
}
