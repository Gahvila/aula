package net.gahvila.aula.General.Commands;

import dev.jorel.commandapi.CommandAPICommand;

import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;

public class FileserverCommand {
    public void registerCommands() {
        new CommandAPICommand("latauspalvelin")
                .executesPlayer((p, args) -> {
                    p.sendMessage(toMM("<white>Gahvilan latauspalvelimen tunnukset</white> <dark_gray>(§ehttps://download.gahvila.net</dark_gray><white>)</white>"));
                    p.sendMessage("<white>Käyttäjänimi:</white> <yellow>gahvila</yellow>");
                    p.sendMessage("<white>Salasana:</white> <yellow>gahvilafiles</yellow>");
                })
                .register();
    }
}