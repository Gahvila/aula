package net.gahvila.aula.Events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.gahvila.aula.Aula;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerInteract implements Listener {
    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        if (e.getItem() == null) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!cooldown.containsKey(p.getUniqueId())) {
                if (e.getItem().hasItemMeta() && e.getItem().getItemMeta().getDisplayName().equals("§8> §6§lPalvelinvalikko §8<")) {
                    e.setCancelled(true);
                    p.performCommand("palvelinvalikko");
                }
            }
        }
    }
}
