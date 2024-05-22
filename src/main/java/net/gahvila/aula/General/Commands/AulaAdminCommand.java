package net.gahvila.aula.General.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import net.gahvila.aula.General.Managers.TeleportManager;
import net.gahvila.aula.Hotbar.HotbarManager;
import net.gahvila.aula.Music.MusicManager;

public class AulaAdminCommand {

    private final TeleportManager teleportManager;
    private final MusicManager musicManager;
    private final HotbarManager hotbarManager;

    public AulaAdminCommand(TeleportManager teleportManager, MusicManager musicManager, HotbarManager hotbarManager) {
        this.teleportManager = teleportManager;
        this.musicManager = musicManager;
        this.hotbarManager = hotbarManager;
    }

    public void registerCommands() {
        new CommandAPICommand("adminaula")
                .withPermission("aula.admin")
                .withSubcommand(new CommandAPICommand("togglehotbar")
                        .executesPlayer((p, args) -> {
                            hotbarManager.setHotbarEnabled(p);
                            p.sendMessage("Hotbar: " + hotbarManager.getHotbarEnabled(p));
                        }))
                .withSubcommand(new CommandAPICommand("setspawn")
                        .executesPlayer((p, args) -> {
                            teleportManager.saveTeleport("spawn", p.getLocation());
                            p.sendMessage("Asetit spawnin uuden sijainnin.");
                        }))
                .withSubcommand(new CommandAPICommand("musicreload")
                        .executesPlayer((p, args) -> {
                            musicManager.loadSongs();
                            p.sendMessage("Ladattu musiikit uudelleen.");
                        }))
                .register();
    }
}
