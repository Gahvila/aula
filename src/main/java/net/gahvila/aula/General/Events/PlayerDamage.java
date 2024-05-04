package net.gahvila.aula.General.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import static net.gahvila.aula.Aula.instance;

public class PlayerDamage implements Listener {

    @EventHandler
    public void onDMg(EntityDamageEvent e){
        if (e.getEntity() instanceof Player p){
            Bukkit.getServer().getScheduler().runTaskLater(instance, new Runnable() {
                @Override
                public void run() {
                    p.setHealth(20);
                }
            }, 0);
        }
    }

    @EventHandler
    public void onDMg(PlayerDeathEvent e){
        Player p = e.getPlayer();
        e.setCancelled(true);
        p.setHealth(20);
    }
}
