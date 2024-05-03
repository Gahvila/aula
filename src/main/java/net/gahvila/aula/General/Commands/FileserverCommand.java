package net.gahvila.aula.General.Commands;

import dev.jorel.commandapi.CommandAPICommand;

public class FileserverCommand {
    public void registerCommands() {
        new CommandAPICommand("latauspalvelin")
                .executesPlayer((p, args) -> {
                    p.sendMessage("§fGahvilan latauspalvelimen tunnukset §8(§ehttps://download.gahvila.net§8)");
                    p.sendMessage("§fKäyttäjänimi: §egahvila");
                    p.sendMessage("§fSalasana: §egahvilafiles");
                })
                .register();
    }
}