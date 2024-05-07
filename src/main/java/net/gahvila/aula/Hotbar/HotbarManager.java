package net.gahvila.aula.Hotbar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

import static net.gahvila.aula.Utils.MiniMessageUtils.toUndecoratedMM;

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
        giveHotbar(player, Hotbar.DEFAULT);
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
    }

    protected static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.values());
        itemMeta.displayName(toUndecoratedMM(name));
        item.setItemMeta(itemMeta);
        return item;
    }
}
