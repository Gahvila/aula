package net.gahvila.aula;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.gahvila.aula.General.Commands.AulaAdminCommand;
import net.gahvila.aula.General.Commands.FileserverCommand;
import net.gahvila.aula.General.Events.*;
import net.gahvila.aula.PlayerFeatures.Music.MusicCommand;
import net.gahvila.aula.PlayerFeatures.Music.MusicEvents;
import net.gahvila.aula.PlayerFeatures.Music.MusicManager;
import net.gahvila.aula.PlayerFeatures.Music.MusicMenu;
import net.gahvila.aula.PlayerFeatures.Spawn.SpawnCommand;
import net.gahvila.aula.PlayerFeatures.Spawn.SpawnTeleport;
import net.gahvila.aula.General.Managers.TeleportManager;
import net.gahvila.aula.PlayerFeatures.ServerSelector;
import net.gahvila.aula.Utils.EmptyChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Calendar;
import java.util.TimeZone;

public final class Aula extends JavaPlugin implements Listener{

    public static Aula instance;
    private PluginManager pluginManager;
    private TeleportManager teleportManager;
    private MusicManager musicManager;
    private MusicMenu musicMenu;



    @Override
    public void onEnable() {

        //managers
        pluginManager = Bukkit.getPluginManager();
        instance = this;
        teleportManager = new TeleportManager();
        musicManager = new MusicManager();
        musicMenu = new MusicMenu(musicManager);

        timeSyncScheduler();
        musicManager.loadSongs();

        //listeners
        registerListeners(this, new PlayerJoin(), new ServerSelector(), new SpawnTeleport(teleportManager),
                new MusicEvents(musicManager));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        //commands
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(false).silentLogs(true));

        FileserverCommand fileserverCommand = new FileserverCommand();
        fileserverCommand.registerCommands();

        AulaAdminCommand aulaAdminCommand = new AulaAdminCommand(teleportManager, musicManager);
        aulaAdminCommand.registerCommands();

        SpawnCommand spawnCommand = new SpawnCommand(teleportManager);
        spawnCommand.registerCommands();

        MusicCommand musicCommand = new MusicCommand(musicMenu);
        musicCommand.registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        teleportManager.putTeleportsIntoCache();
    }
    private void registerListeners(Listener...listeners){
        for(Listener listener : listeners){
            pluginManager.registerEvents(listener, this);
        }
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new EmptyChunkGenerator();
    }

    public void timeSyncScheduler() {
        Bukkit.getServer().getScheduler().runTaskTimer(instance, () -> {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Helsinki"));
            long time = (1000 * cal.get(Calendar.HOUR_OF_DAY)) + (16 * cal.get(Calendar.MINUTE)) - 6000;
            Bukkit.getWorld("world").setTime(time);
        }, 0L, 200);
    }
}
