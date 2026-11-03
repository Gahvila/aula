package net.gahvila.aula;

import net.gahvila.aula.General.Commands.AulaAdminCommand;
import net.gahvila.aula.General.Commands.FileserverCommand;
import net.gahvila.aula.General.Events.PlayerDamage;
import net.gahvila.aula.General.Events.PlayerJoin;
import net.gahvila.aula.General.Events.PlayerLeave;
import net.gahvila.aula.Hotbar.HotbarEvent;
import net.gahvila.aula.Hotbar.HotbarManager;
import net.gahvila.aula.ServerSelector.ServerSelectorCommand;
import net.gahvila.aula.ServerSelector.ServerSelectorMenu;
import net.gahvila.aula.Utils.EmptyChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public final class Aula extends JavaPlugin {

    public static Aula instance;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {

        //managers
        pluginManager = Bukkit.getPluginManager();
        instance = this;

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        registerListeners(new PlayerJoin(), new PlayerLeave(), new PlayerDamage());

        //hotbar
        HotbarManager hotbarManager = new HotbarManager();
        registerListeners(new HotbarEvent(hotbarManager));
        hotbarManager.scheduleHotbarChecker();

        //serverselector
        ServerSelectorMenu serverSelectorMenu = new ServerSelectorMenu();
        ServerSelectorCommand serverSelectorCommand = new ServerSelectorCommand(serverSelectorMenu);
        serverSelectorCommand.registerCommands(this);

        //general
        timeSyncScheduler();
        coldScheduler();

        FileserverCommand fileserverCommand = new FileserverCommand();
        fileserverCommand.registerCommands(this);

        AulaAdminCommand aulaAdminCommand = new AulaAdminCommand(hotbarManager);
        aulaAdminCommand.registerCommands(this);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
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
            long time = (1000L * cal.get(Calendar.HOUR_OF_DAY)) + (16L * cal.get(Calendar.MINUTE)) - 6000L;
            if (time < 0) {
                time += 24000L;
            }
            World world = Bukkit.getWorld("world");
            if (world != null) {
                world.setTime(time);
            }
        }, 0L, 200L);
    }

    public void coldScheduler() {
        Location location = new Location(Bukkit.getWorld("world"), -27.0, 77.5, -24.0);
        Bukkit.getServer().getScheduler().runTaskTimer(instance, () -> {
            Month currentMonth = ZonedDateTime.now().getMonth();
            if (currentMonth == Month.NOVEMBER || currentMonth == Month.DECEMBER || currentMonth == Month.JANUARY) {
                World world = Bukkit.getWorld("world");
                if (world != null) {
                    world.spawnParticle(
                            Particle.CAMPFIRE_COSY_SMOKE,
                            location,
                            10,
                            0, 1, 0,
                            0.05
                    );
                }
            }
        }, 0L, 20L);
    }
}