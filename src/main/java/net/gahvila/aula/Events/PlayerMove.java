package net.gahvila.aula.Events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.gahvila.aula.Aula;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.geysermc.floodgate.api.FloodgateApi;

public class PlayerMove implements Listener {

    @EventHandler
    public void onMove(PlayerJumpEvent e){
        Player p = e.getPlayer();
        if (p.getWorld().equals(Bukkit.getWorld("world"))){
            if (p.getGameMode().equals(GameMode.ADVENTURE)){

                e.setCancelled(true);
                Bukkit.getServer().getScheduler().runTaskLater(Aula.instance, new Runnable() {
                    @Override
                    public void run() {
                        p.teleport(new Location(Bukkit.getWorld("world"), 8.50, 23.0, -5.5));
                    }
                }, 10);
            }
        }
    }
}
