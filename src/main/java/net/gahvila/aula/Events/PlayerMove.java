package net.gahvila.aula.Events;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.gahvila.aula.Aula;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMove implements Listener {

    @EventHandler
    public void onMove(PlayerJumpEvent e){
        Player p = e.getPlayer();
        if (p.getWorld().equals(Bukkit.getWorld("world"))){
            if (p.getGameMode().equals(GameMode.ADVENTURE)){
                p.setWalkSpeed(0.2F);
                e.setCancelled(true);
                Bukkit.getServer().getScheduler().runTaskLater(Aula.instance, new Runnable() {
                    @Override
                    public void run() {
                        p.teleport(new Location(Bukkit.getWorld("world"), 8.50, 9.0, -5.5));

                    }
                }, 10);
            }
        }
    }
}
