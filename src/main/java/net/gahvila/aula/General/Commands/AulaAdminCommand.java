package net.gahvila.aula.General.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import net.gahvila.aula.General.Managers.TeleportManager;
import net.gahvila.aula.PlayerFeatures.Music.MusicManager;

public class AulaAdminCommand {

    private final TeleportManager teleportManager;
    private final MusicManager musicManager;

    public AulaAdminCommand(TeleportManager teleportManager, MusicManager musicManager) {
        this.teleportManager = teleportManager;
        this.musicManager = musicManager;
    }

    public void registerCommands() {
        new CommandAPICommand("adminaula")
                .withPermission("aula.admin")
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
