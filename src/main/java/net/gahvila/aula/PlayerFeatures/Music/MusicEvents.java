package net.gahvila.aula.PlayerFeatures.Music;

import com.xxmicloxx.NoteBlockAPI.event.SongEndEvent;
import com.xxmicloxx.NoteBlockAPI.event.SongNextEvent;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import net.gahvila.aula.General.Events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;

public class MusicEvents implements Listener {

    private final MusicManager musicManager;

    public MusicEvents(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        musicManager.setSpeakerEnabled(player, musicManager.getSpeakerState(player));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        musicManager.clearSongPlayer(player);
        musicManager.saveSpeakerState(player);
    }

    @EventHandler
    public void songNextEvent(SongNextEvent event) {
        SongPlayer songPlayer = event.getSongPlayer();
        Set<UUID> playerUUIDs = songPlayer.getPlayerUUIDs();
        for (UUID uuid : playerUUIDs) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                songPlayer.setPlaying(false);
                player.sendMessage(toMM("Nyt soi: <yellow>" + songPlayer.getSong().getTitle()));
                musicManager.progressBar(player, songPlayer);
                Bukkit.getScheduler().runTaskLater(instance, () -> songPlayer.setPlaying(true), 20);
            }
        }
    }
}
