package net.gahvila.aula.ServerSelector;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.gahvila.aula.Aula;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerSelectorCommand {

    private final ServerSelectorMenu serverSelectorMenu;

    public ServerSelectorCommand(ServerSelectorMenu serverSelectorMenu) {
        this.serverSelectorMenu = serverSelectorMenu;
    }

    public void registerCommands(Aula plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(create());
        });
    }

    private LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("serverselector")
                .executes(this::execute)
                .build();
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (sender instanceof Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 0.6F, 1F);
            serverSelectorMenu.show(player);
        }
        return Command.SINGLE_SUCCESS;
    }
}
