package net.gahvila.aula.Spawn;

import dev.jorel.commandapi.CommandAPICommand;
import net.gahvila.aula.General.Managers.TeleportManager;
import org.bukkit.Location;
import org.bukkit.Sound;

import static java.lang.Float.MAX_VALUE;

public class SpawnCommand {

    private final TeleportManager teleportManager;

    public SpawnCommand(TeleportManager teleportManager) {
        this.teleportManager = teleportManager;

    }

    public void registerCommands() {
        new CommandAPICommand("spawn")
                .executesPlayer((p, args) -> {
                    Location loc = teleportManager.getTeleport("spawn");
                    p.teleport(loc);
                    p.sendMessage("Teleporttasit spawnille.");
                    p.playSound(loc, Sound.ENTITY_PLAYER_TELEPORT, MAX_VALUE, 1F);
                })
                .register();
    }
}
