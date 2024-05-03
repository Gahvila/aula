package net.gahvila.aula.PlayerFeatures.Music;

import dev.jorel.commandapi.CommandAPICommand;
import net.gahvila.aula.General.Managers.TeleportManager;

public class MusicCommand {

    private final MusicMenu musicMenu;

    public MusicCommand(MusicMenu musicMenu) {
        this.musicMenu = musicMenu;
    }

    public void registerCommands() {
        new CommandAPICommand("music")
            .executesPlayer((p, args) -> {
                musicMenu.showGUI(p);
                p.sendMessage("Avasit musiikkivalikon.");
            })
            .register();
    }
}
