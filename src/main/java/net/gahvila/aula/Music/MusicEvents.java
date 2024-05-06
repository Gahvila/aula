package net.gahvila.aula.Music;

import com.xxmicloxx.NoteBlockAPI.event.SongNextEvent;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;
import java.util.UUID;

import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;

public class MusicEvents implements Listener {

    private final MusicManager musicManager;

    public MusicEvents(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        musicManager.setSpeakerEnabled(player, false);
        musicManager.setAutoEnabled(player, musicManager.getSavedAutoState(player));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        musicManager.clearSongPlayer(player);
        musicManager.saveAutoState(player);

        MusicManager.speakerEnabled.remove(player);
        MusicManager.autoEnabled.remove(player);
    }

    @EventHandler
    public void songNextEvent(SongNextEvent event) {
        SongPlayer songPlayer = event.getSongPlayer();
        Set<UUID> playerUUIDs = songPlayer.getPlayerUUIDs();
        for (UUID uuid : playerUUIDs) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage(toMM("Nyt soi: <yellow>" + songPlayer.getSong().getTitle()));
                musicManager.songPlayerSchedule(player, songPlayer);
            }
        }
    }
}
