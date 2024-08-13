package net.gahvila.aula.Profile;

import dev.jorel.commandapi.CommandAPICommand;
import net.gahvila.aula.ServerSelector.ServerSelectorMenu;
import org.bukkit.Sound;

public class ProfileCommand {

    private final ProfileMenu profileMenu;

    public ProfileCommand(ProfileMenu profileMenu) {
        this.profileMenu = profileMenu;
    }

    public void registerCommands() {
        new CommandAPICommand("profile")
                .withAliases("profiili")
                .executesPlayer((p, args) -> {
                    if (p.hasPermission("admin.profile")) {
                        p.playSound(p.getLocation(), Sound.ENTITY_LLAMA_SWAG, 0.6F, 1F);
                        profileMenu.showGUI(p);
                    } else {
                        p.sendRichMessage("Tämä toiminto ei ole vielä käytössä.");
                    }
                })
                .register();
    }
}
