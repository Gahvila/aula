package net.gahvila.aula.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.quitMessage(null);
    }
}
