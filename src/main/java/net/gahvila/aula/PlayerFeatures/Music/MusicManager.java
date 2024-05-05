package net.gahvila.aula.PlayerFeatures.Music;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import de.leonhard.storage.Json;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;

public class MusicManager {

    public MusicManager() {
    }

    public static ArrayList<Song> songs = new ArrayList<>();
    public static HashMap<String, Song> namedSong = new HashMap<>();
    public static HashMap<Player, RadioSongPlayer> songPlayers = new HashMap<>();
    public static HashMap<Player, Boolean> speakerEnabled = new HashMap<>();


    public void loadSongs() {
        if (songs != null) songs.clear();
        if (namedSong != null) namedSong.clear();

        File folder = new File(instance.getDataFolder(), "songs");
        if (folder.listFiles() == null) return;
        for (File file : folder.listFiles()) {
            Song song = NBSDecoder.parse(file);
            songs.add(song);
            namedSong.put(song.getTitle(), song);
        }

        songs.sort((song1, song2) -> song1.getTitle().compareToIgnoreCase(song2.getTitle()));
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public Song getSong(String name){
        return namedSong.get(name);
    }

    public void saveSongPlayer(Player player, RadioSongPlayer songPlayer){
        songPlayers.put(player, songPlayer);
    }

    public void clearSongPlayer(Player player){
        if (!songPlayers.containsKey(player)) return;
        if (songPlayers.get(player) == null) songPlayers.remove(player);
        RadioSongPlayer songPlayer = songPlayers.get(player);
        songPlayer.destroy();
        songPlayers.remove(player);
    }

    public boolean getSpeakerEnabled(Player player) {
        return speakerEnabled.get(player) != null && speakerEnabled.get(player);
    }

    public void setSpeakerEnabled(Player player, boolean option) {
        speakerEnabled.put(player, option);
    }

    public void saveSpeakerState(Player player) {
        Json playerData = new Json("playerdata.json", instance.getDataFolder() + "/data/");
        String uuid = player.getUniqueId().toString();
        playerData.set(uuid + "." + "radioState", getSpeakerEnabled(player));
    }
    public boolean getSpeakerState(Player player) {
        Json playerData = new Json("playerdata.json", instance.getDataFolder() + "/data/");
        String uuid = player.getUniqueId().toString();
        return playerData.getFileData().containsKey(uuid + "." + "radioState");
    }

    public void progressBar(Player player, SongPlayer songPlayer) {
        double length = songPlayer.getSong().getLength();
        BossBar progressBar = BossBar.bossBar(toMM("<aqua>" + songPlayer.getSong().getOriginalAuthor() + " - " + songPlayer.getSong().getTitle() + "</aqua>"), 0f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
        player.showBossBar(progressBar);
        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, task -> {
            double progress = (double) songPlayer.getTick() / length;
            if (progress >= 1.0 || progress < 0){
                progressBar.removeViewer(player);
                task.cancel();
                return;
            }
            progressBar.progress((float) progress);
        }, 0L, 1);
    }
}
