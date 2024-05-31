package net.gahvila.aula.Utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.stream.Collectors;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.aula.Utils.MiniMessageUtils.toUndecoratedMM;

public class MenuItemCreator {

    public static ItemStack itemCreator(Material material, String name) {
        return itemCreator(material, name, List.of(), false);
    }

    public static ItemStack itemCreator(Material material, String name, boolean hideTooltip) {
        return itemCreator(material, name, List.of(), hideTooltip);
    }

    public static ItemStack itemCreator(Material material, String name, List<String> lore, boolean hideTooltip) {
        return itemCreator(material, name, lore, null, hideTooltip);
    }

    public static ItemStack itemCreator(Material material, String name, List<String> lore, String pdc, boolean hideTooltip) {
        return createItem(material, name, lore, pdc, hideTooltip);
    }

    private static ItemStack createItem(Material material, String name, List<String> lore, String pdc, boolean hideTooltip) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(toUndecoratedMM(name));
        if (!lore.isEmpty()) {
            List<Component> loreComponents = lore.stream()
                    .map(MiniMessageUtils::toUndecoratedMM)
                    .collect(Collectors.toList());
            meta.lore(loreComponents);
        }
        if (pdc != null) {
            NamespacedKey key = new NamespacedKey(instance, "aula");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, pdc);
        }

        meta.setHideTooltip(hideTooltip);
        item.setItemMeta(meta);

        return item;
    }

}
