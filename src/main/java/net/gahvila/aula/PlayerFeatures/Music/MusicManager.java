package net.gahvila.aula.PlayerFeatures.Music;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static net.gahvila.aula.Aula.instance;

public class MusicManager {

    public MusicManager() {
    }

    public static ArrayList<Song> songs = new ArrayList<>();
    public static Map<String, Song> namedSong = new HashMap<>();
    public static Map<Player, RadioSongPlayer> songPlayers = new HashMap<>();

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

    public String getSongName(Song song){
        return song.getTitle();
    }

    public String getSongAuthor(Song song){
        return song.getOriginalAuthor();
    }
}
