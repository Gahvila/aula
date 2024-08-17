package net.gahvila.aula.Spawn;

import net.gahvila.aula.Teleport.TeleportManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnTeleport implements Listener {

    private final TeleportManager teleportManager;

    public SpawnTeleport(TeleportManager teleportManager) {
        this.teleportManager = teleportManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(teleportManager.getTeleport("spawn"));
    }
}
