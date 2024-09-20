package net.gahvila.aula.General.Commands;

import dev.jorel.commandapi.CommandAPICommand;
import net.gahvila.aula.Hotbar.HotbarManager;
import net.gahvila.aula.Teleport.TeleportManager;

public class AulaAdminCommand {

    private final TeleportManager teleportManager;
    private final HotbarManager hotbarManager;

    public AulaAdminCommand(TeleportManager teleportManager, HotbarManager hotbarManager) {
        this.teleportManager = teleportManager;
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
                .register();
    }
}
