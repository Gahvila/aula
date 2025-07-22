package net.gahvila.aula.General.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.gahvila.aula.Aula;
import org.bukkit.command.CommandSender;

public class FileserverCommand {

    public void registerCommands(Aula plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(create());
        });
    }

    private LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("latauspalvelin")
                .executes(this::execute)
                .build();
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        sender.sendRichMessage("<white>Gahvilan latauspalvelimen tunnukset</white> <dark_gray>(<yellow>https://download.gahvila.net</yellow></dark_gray><white>)</white>");
        sender.sendRichMessage("<white>Käyttäjänimi:</white> <yellow>gahvila</yellow>");
        sender.sendRichMessage("<white>Salasana:</white> <yellow>gahvilafiles</yellow>");
        return Command.SINGLE_SUCCESS;
    }
}