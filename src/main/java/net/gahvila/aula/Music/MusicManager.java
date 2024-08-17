package net.gahvila.aula.Music;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import de.leonhard.storage.Json;
import net.gahvila.aula.Utils.WorldGuardRegionChecker;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;

public class MusicManager {

    public MusicManager() {
    }

    public static ArrayList<Song> songs = new ArrayList<>();
    public static HashMap<String, Song> namedSong = new HashMap<>();
    public static HashMap<Player, SongPlayer> songPlayers = new HashMap<>();
    public static HashMap<Player, Boolean> speakerEnabled = new HashMap<>();
    public static HashMap<Player, Boolean> autoEnabled = new HashMap<>();


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

    public void saveSongPlayer(Player player, SongPlayer songPlayer){
        songPlayers.put(player, songPlayer);
    }

    public SongPlayer getSongPlayer(Player player){
        return songPlayers.get(player);
    }

    public void clearSongPlayer(Player player){
        if (!songPlayers.containsKey(player)) return;
        if (songPlayers.get(player) == null) songPlayers.remove(player);
        SongPlayer songPlayer = songPlayers.get(player);
        songPlayer.destroy();
        songPlayers.remove(player);
    }

    //SPEAKER MODE
    public boolean getSpeakerEnabled(Player player) {
        return speakerEnabled.get(player) != null && speakerEnabled.get(player);
    }

    public void setSpeakerEnabled(Player player, boolean option) {
        speakerEnabled.put(player, option);
    }
    //

    //AUTO PLAY
    public boolean getAutoEnabled(Player player) {
        return autoEnabled.get(player) != null && autoEnabled.get(player);
    }

    public void setAutoEnabled(Player player, boolean option) {
        autoEnabled.put(player, option);
    }

    public void saveAutoState(Player player) {
        Json playerData = new Json("playerdata.json", instance.getDataFolder() + "/data/");
        String uuid = player.getUniqueId().toString();
        playerData.set(uuid + "." + "radioState", getAutoEnabled(player));
    }
    public boolean getSavedAutoState(Player player) {
        Json playerData = new Json("playerdata.json", instance.getDataFolder() + "/data/");
        String uuid = player.getUniqueId().toString();
        return playerData.getFileData().containsKey(uuid + "." + "radioState");
    }


    public void songPlayerSchedule(Player player, SongPlayer songPlayer) {
        double length = songPlayer.getSong().getLength();
        BossBar progressBar = BossBar.bossBar(toMM("<aqua>" + songPlayer.getSong().getOriginalAuthor() + " - " +
                songPlayer.getSong().getTitle() + "</aqua>"), 0f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);
        player.showBossBar(progressBar);
        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, task -> {
            double progress = (double) songPlayer.getTick() / length;
            if (progress >= 1.0 || progress < 0){
                progressBar.removeViewer(player);
                task.cancel();
                return;
            }
            progressBar.progress((float) progress);

            if (!songPlayer.isPlaying()){
                progressBar.name(toMM("<red>" + songPlayer.getSong().getOriginalAuthor() + " - " +
                        songPlayer.getSong().getTitle() + "</red>"));
                progressBar.color(BossBar.Color.RED);
            } else {
                progressBar.name(toMM("<aqua>" + songPlayer.getSong().getOriginalAuthor() + " - " +
                        songPlayer.getSong().getTitle() + "</aqua>"));
                progressBar.color(BossBar.Color.BLUE);
            }
        }, 0, 1);

        if (songPlayer instanceof EntitySongPlayer){
            Bukkit.getScheduler().runTaskTimer(instance, task2 -> {
                double progress = (double) songPlayer.getTick() / length;
                if (progress >= 1.0 || progress < 0){
                    task2.cancel();
                    return;
                }
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (!WorldGuardRegionChecker.isInRegion(onlinePlayer, "spawn")){
                        songPlayer.addPlayer(onlinePlayer);
                        onlinePlayer.spawnParticle(Particle.NOTE, player.getLocation().add(0, 2, 0), 1);
                    } else {
                        songPlayer.removePlayer(onlinePlayer);
                    }
                }
            }, 10L, 10);
        }
    }
}
