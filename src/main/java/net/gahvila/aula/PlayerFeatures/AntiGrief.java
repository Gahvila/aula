package net.gahvila.aula.PlayerFeatures;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class AntiGrief implements Listener {

    @EventHandler
    public void onPlace(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER){
            Player p = (Player) e.getEntity();
            if (p.getGameMode() == GameMode.ADVENTURE) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlace(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.ADVENTURE) {
            e.setCancelled(true);
        }
    }
}