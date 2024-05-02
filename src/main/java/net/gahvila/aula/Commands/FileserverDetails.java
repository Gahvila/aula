package net.gahvila.aula.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FileserverDetails implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        p.sendMessage("§fGahvilan latauspalvelimen tunnukset §8(§ehttps://download.gahvila.net§8)");
        p.sendMessage("§fKäyttäjänimi: §egahvila");
        p.sendMessage("§fSalasana: §egahvilafiles");
        return true;
    }
}