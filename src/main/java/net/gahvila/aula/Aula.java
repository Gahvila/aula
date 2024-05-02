package net.gahvila.aula;

import net.gahvila.aula.Commands.FileserverDetails;
import net.gahvila.aula.Events.*;
import net.gahvila.aula.Global.TimeSync;
import net.gahvila.aula.PlayerFeatures.ServerSelector;
import net.gahvila.aula.Utils.EmptyChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Aula extends JavaPlugin {

    public static Aula instance;
    private PluginManager pluginManager;
    @Override
    public void onEnable() {
        pluginManager = Bukkit.getPluginManager();
        instance = this;

        TimeSync timeSync = new TimeSync();
        timeSync.timeSyncScheduler();

        registerListeners(new PlayerJoin(), new ServerSelector());

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getCommand("latauspalvelin").setExecutor(new FileserverDetails());
        getCommand("palvelinvalikko").setExecutor(new ServerSelector());

        //CONFIG
        getConfig().options().copyDefaults();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
}
