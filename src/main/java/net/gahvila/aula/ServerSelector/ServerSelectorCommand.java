package net.gahvila.aula.ServerSelector;

import dev.jorel.commandapi.CommandAPICommand;

public class ServerSelectorCommand {

    private final ServerSelectorMenu serverSelectorMenu;

    public ServerSelectorCommand(ServerSelectorMenu serverSelectorMenu) {
        this.serverSelectorMenu = serverSelectorMenu;
    }

    public void registerCommands() {
        new CommandAPICommand("serverselector")
                .withAliases("palvelinvalikko")
                .executesPlayer((p, args) -> {
                    serverSelectorMenu.showGUI(p);
                })
                .register();
    }
}
