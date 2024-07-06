package net.gahvila.aula.General.Events;

import net.gahvila.aula.General.Managers.TeleportManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoad implements Listener {
    private final TeleportManager teleportManager;

    public WorldLoad(TeleportManager teleportManager) {
        this.teleportManager = teleportManager;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        teleportManager.putTeleportsIntoCache();
    }
}
