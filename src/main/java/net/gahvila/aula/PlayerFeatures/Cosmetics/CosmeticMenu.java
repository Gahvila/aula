package net.gahvila.aula.PlayerFeatures.Cosmetics;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;
import static net.gahvila.aula.Utils.MiniMessageUtils.toUndecoratedMM;

public class CosmeticMenu {

    public void showGUI(Player player) {
        ChestGui gui = new ChestGui(3, ComponentHolder.of(toMM("<dark_purple><b>Kosmetiikka")));
        gui.show(player);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);

        ItemStack backgroundItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundItemMeta = backgroundItem.getItemMeta();
        backgroundItemMeta.displayName(toMM(""));
        backgroundItem.setItemMeta(backgroundItemMeta);

        background.addItem(new GuiItem(backgroundItem));

        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(1, 1, 7, 1);

        ItemStack craft = new ItemStack(Material.BARRIER);
        ItemMeta craftMeta = craft.getItemMeta();
        craftMeta.displayName(toUndecoratedMM("???"));
        craftMeta.lore(List.of(toUndecoratedMM("???")));
        craft.setItemMeta(craftMeta);

        navigationPane.addItem(new GuiItem(craft, event -> {
            //do something
        }));


        gui.addPane(navigationPane);

        gui.update();
    }
}
