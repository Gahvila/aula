package net.gahvila.aula.Hotbar;

import net.gahvila.gahvilacore.Utils.WorldGuardRegionChecker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.gahvilacore.Utils.MiniMessageUtils.toUndecoratedMM;

public class HotbarManager {

    public static HashMap<Player, Boolean> hotbarEnabled = new HashMap<>();
    public static HashMap<Player, Hotbar> currentHotbar = new HashMap<>();

    public boolean getHotbarEnabled(Player player) {
        return hotbarEnabled.getOrDefault(player, true);
    }

    public void setHotbarEnabled(Player player) {
        if (getHotbarEnabled(player)) {
            hotbarEnabled.put(player, false);
            player.getInventory().clear();
            return;
        }
        hotbarEnabled.put(player, true);
        giveHotbar(player, getCurrentHotbar(player));
    }

    public Hotbar getCurrentHotbar(Player player) {
        return currentHotbar.getOrDefault(player, Hotbar.DEFAULT);
    }

    public void giveHotbar(Player player, Hotbar hotbarType) {
        for(int i = 0; i < hotbarType.getItems().length; i++) {
            ItemStack temppa = hotbarType.getItems()[i];
            if (hotbarType.getItems()[i].getType() == Material.PLAYER_HEAD) {
                temppa = temppa.clone();
                SkullMeta meta = (SkullMeta) temppa.getItemMeta();
                meta.setOwningPlayer(player);
                temppa.setItemMeta(meta);
            }
            player.getInventory().setItem(i, temppa);
        }
        currentHotbar.put(player, hotbarType);
    }

    protected static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.displayName(toUndecoratedMM(name));
        item.setItemMeta(itemMeta);
        return item;
    }

    public void scheduleHotbarChecker() {
        Bukkit.getScheduler().runTaskTimer(instance, task -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!getHotbarEnabled(player)) {
                    continue;
                }

                Hotbar requiredHotbar = WorldGuardRegionChecker.isInRegion(player, "spawn") ? Hotbar.SPAWN : Hotbar.DEFAULT;
                Hotbar currentHotbar = getCurrentHotbar(player);

                if (currentHotbar != requiredHotbar) {
                    giveHotbar(player, requiredHotbar);
                }
            }
        }, 0L, 15L);
    }
}
