package net.gahvila.aula.PlayerFeatures.Music;

import dev.jorel.commandapi.CommandAPICommand;
import net.gahvila.aula.General.Managers.TeleportManager;

public class MusicCommand {

    private final MusicMenu musicMenu;
    private final MusicManager musicManager;

    public MusicCommand(MusicMenu musicMenu, MusicManager musicManager) {
        this.musicMenu = musicMenu;
        this.musicManager = musicManager;
    }

    public void registerCommands() {
        new CommandAPICommand("music")
            .executesPlayer((p, args) -> {
                musicMenu.showGUI(p);
            })
            .register();
        new CommandAPICommand("radio")
                .executesPlayer((p, args) -> {
                    musicManager.setSpeakerEnabled(p, true);
                    p.sendMessage("Kytkit radion.");
                })
                .register();
    }
}
