package net.gahvila.aula.Music;

import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Sound;

public class MusicCommand {

    private final MusicMenu musicMenu;

    public MusicCommand(MusicMenu musicMenu) {
        this.musicMenu = musicMenu;
    }

    public void registerCommands() {
        new CommandAPICommand("music")
            .executesPlayer((p, args) -> {
                p.playSound(p.getLocation(), Sound.ENTITY_LLAMA_SWAG, 0.6F, 1F);
                musicMenu.showGUI(p);
            })
            .register();
    }
}
