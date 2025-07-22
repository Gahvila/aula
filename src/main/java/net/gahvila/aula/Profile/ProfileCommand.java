package net.gahvila.aula.Profile;

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

public class ProfileCommand {

    private final ProfileMenu profileMenu;

    public ProfileCommand(ProfileMenu profileMenu) {
        this.profileMenu = profileMenu;
    }

    public void registerCommands(Aula plugin) {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(create());
        });
    }

    private LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("profile")
                .executes(this::execute)
                .build();
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        if (sender instanceof Player player) {
            if (player.hasPermission("admin.profile")) {
                player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 0.6F, 1F);
                profileMenu.showGUI(player);
            } else {
                player.sendRichMessage("Tämä toiminto ei ole vielä käytössä.");
            }
        }
        return Command.SINGLE_SUCCESS;
    }
}
