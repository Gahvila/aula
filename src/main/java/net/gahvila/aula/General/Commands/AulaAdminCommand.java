package net.gahvila.aula.General.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.gahvila.aula.Aula;
import net.gahvila.aula.Hotbar.HotbarManager;
import net.gahvila.gahvilacore.GahvilaCore;
import net.gahvila.gahvilacore.Teleport.TeleportManager;
import org.bukkit.entity.Player;

public class AulaAdminCommand {

    private final HotbarManager hotbarManager;

    public AulaAdminCommand(HotbarManager hotbarManager) {
        this.hotbarManager = hotbarManager;
    }

    public void registerCommands(Aula plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(create());
        });
    }

    private LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("adminaula")
                .requires(source -> source.getSender().hasPermission("aula.admin"))
                .then(Commands.literal("togglehotbar")
                        .executes(context -> {
                            if (context.getSource().getSender() instanceof Player player
                                    && player.hasPermission("aula.admin")) {
                                hotbarManager.setHotbarEnabled(player);
                                player.sendMessage("Hotbar: " + hotbarManager.getHotbarEnabled(player));
                            }
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .build();
    }
}
