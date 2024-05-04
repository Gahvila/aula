package net.gahvila.aula.PlayerFeatures.Music;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class MusicEvents implements Listener {

    private final MusicManager musicManager;

    public MusicEvents(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        musicManager.clearSongPlayer(player);
    }
}
