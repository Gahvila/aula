package net.gahvila.aula.Music;

import dev.jorel.commandapi.CommandAPICommand;

public class MusicCommand {

    private final MusicMenu musicMenu;

    public MusicCommand(MusicMenu musicMenu) {
        this.musicMenu = musicMenu;
    }

    public void registerCommands() {
        new CommandAPICommand("music")
            .executesPlayer((p, args) -> {
                musicMenu.showGUI(p);
            })
            .register();
    }
}
