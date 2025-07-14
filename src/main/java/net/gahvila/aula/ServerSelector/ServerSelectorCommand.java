package net.gahvila.aula.ServerSelector;

import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Sound;

public class ServerSelectorCommand {

    private final ServerSelectorMenu serverSelectorMenu;

    public ServerSelectorCommand(ServerSelectorMenu serverSelectorMenu) {
        this.serverSelectorMenu = serverSelectorMenu;
    }

    public void registerCommands() {
        new CommandAPICommand("serverselector")
                .withAliases("palvelinvalikko")
                .executesPlayer((p, args) -> {
                    p.playSound(p.getLocation(), Sound.ENTITY_LLAMA_SWAG, 0.6F, 1F);
                    serverSelectorMenu.show(p);
                })
                .register();
    }
}
