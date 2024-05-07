package net.gahvila.aula.Hotbar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class HotbarEvent implements Listener {

    private final HotbarManager hotbarManager;
    public HotbarEvent(HotbarManager hotbarManager) {
        this.hotbarManager = hotbarManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (hotbarManager.getHotbarEnabled(player)) {
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
                    if (slot == 1){
                        //nothing
                    } else if (slot == 4) {
                        //nothing
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
        if (hotbarManager.getHotbarEnabled(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (hotbarManager.getHotbarEnabled(player)) {
            event.setCancelled(true);
        }
    }
}
