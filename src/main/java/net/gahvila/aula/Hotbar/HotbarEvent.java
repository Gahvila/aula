package net.gahvila.aula.Hotbar;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class HotbarEvent implements Listener {

    private final HotbarManager hotbarManager;
    public HotbarEvent(HotbarManager hotbarManager) {
        this.hotbarManager = hotbarManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (hotbarManager.getHotbarEnabled(player)) {
            player.getInventory().setHeldItemSlot(4);
            hotbarManager.giveHotbar(player, Hotbar.DEFAULT);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (hotbarManager.getHotbarEnabled(player)) {
            event.setCancelled(true);
            switch (hotbarManager.getCurrentHotbar(player)) {
                case DEFAULT:
                    int slot = player.getInventory().getHeldItemSlot();
                    if (slot == 1) {
                        //nothing
                    } else if (slot == 4) {
                        player.performCommand("serverselector");
                    } else if (slot == 7) {
                        player.performCommand("music");
                    }
                    break;
                case PARKOUR:
                    break;
                case KOKKIKERHO:
                    break;
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != event.getWhoClicked().getInventory()) return;
        if (hotbarManager.getHotbarEnabled(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHandSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (hotbarManager.getHotbarEnabled(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (hotbarManager.getHotbarEnabled(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (hotbarManager.getHotbarEnabled(player)) {
                event.setCancelled(true);
            }
        }
    }
}
