package net.gahvila.aula;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.gahvila.aula.General.Commands.AulaAdminCommand;
import net.gahvila.aula.General.Commands.FileserverCommand;
import net.gahvila.aula.General.Events.PlayerDamage;
import net.gahvila.aula.General.Events.PlayerJoin;
import net.gahvila.aula.General.Events.PlayerLeave;
import net.gahvila.aula.Hotbar.HotbarEvent;
import net.gahvila.aula.Hotbar.HotbarManager;
import net.gahvila.aula.Profile.ProfileCommand;
import net.gahvila.aula.Profile.ProfileMenu;
import net.gahvila.aula.ServerSelector.ServerSelectorCommand;
import net.gahvila.aula.ServerSelector.ServerSelectorMenu;
import net.gahvila.aula.Spawn.SpawnCommand;
import net.gahvila.aula.Spawn.SpawnTeleport;
import net.gahvila.aula.Utils.EmptyChunkGenerator;
import net.gahvila.gahvilacore.Teleport.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import static net.gahvila.gahvilacore.Utils.MiniMessageUtils.toMM;

public final class Aula extends JavaPlugin {

    public static Aula instance;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {

        //managers
        pluginManager = Bukkit.getPluginManager();
        instance = this;

        TeleportManager teleportManager = new TeleportManager();

        registerListeners(new PlayerJoin(), new PlayerLeave(), new SpawnTeleport(teleportManager),
                new PlayerDamage());

        //commandapi
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(false).silentLogs(true));

        //hotbar
        HotbarManager hotbarManager = new HotbarManager();
        registerListeners(new HotbarEvent(hotbarManager));
        hotbarManager.scheduleHotbarChecker();

        //serverselector
        ServerSelectorMenu serverSelectorMenu = new ServerSelectorMenu();
        ServerSelectorCommand serverSelectorCommand = new ServerSelectorCommand(serverSelectorMenu);
        serverSelectorCommand.registerCommands();

        //profile
        ProfileMenu profileMenu = new ProfileMenu();
        ProfileCommand profileCommand = new ProfileCommand(profileMenu);
        profileCommand.registerCommands();

        //general

        timeSyncScheduler();
        coldScheduler();

        FileserverCommand fileserverCommand = new FileserverCommand();
        fileserverCommand.registerCommands();

        AulaAdminCommand aulaAdminCommand = new AulaAdminCommand(teleportManager, hotbarManager);
        aulaAdminCommand.registerCommands();

        SpawnCommand spawnCommand = new SpawnCommand(teleportManager);
        spawnCommand.registerCommands();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
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

    public void coldScheduler() {
        Location location = new Location(Bukkit.getWorld("world"), -27.0, 77.5, -24.0);
        Bukkit.getServer().getScheduler().runTaskTimer(instance, () -> {
            Month currentMonth = ZonedDateTime.now().getMonth();
            if (currentMonth == Month.NOVEMBER || currentMonth == Month.DECEMBER || currentMonth == Month.JANUARY) {
                Bukkit.getWorld("world").spawnParticle(
                        Particle.CAMPFIRE_COSY_SMOKE,
                        location,
                        10,
                        0, 1, 0,
                        0.05
                );
            }
        }, 0L, 20);
    }
}
