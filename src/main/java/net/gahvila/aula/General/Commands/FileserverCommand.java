package net.gahvila.aula.General.Commands;

import dev.jorel.commandapi.CommandAPICommand;

import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;

public class FileserverCommand {
    public void registerCommands() {
        new CommandAPICommand("latauspalvelin")
                .executesPlayer((p, args) -> {
                    p.sendRichMessage("<white>Gahvilan latauspalvelimen tunnukset</white> <dark_gray>(<yellow>https://download.gahvila.net</yellow></dark_gray><white>)</white>");
                    p.sendRichMessage("<white>Käyttäjänimi:</white> <yellow>gahvila</yellow>");
                    p.sendRichMessage("<white>Salasana:</white> <yellow>gahvilafiles</yellow>");
                })
                .register();
    }
}