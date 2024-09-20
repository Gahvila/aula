package net.gahvila.aula.Profile;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.aula.Utils.MenuItemCreator.itemCreator;
import static net.gahvila.gahvilacore.API.Utils.MiniMessageUtils.toMM;
import static net.gahvila.gahvilacore.API.Utils.MiniMessageUtils.toUndecoratedMM;

public class ProfileMenu {

    public void showGUI(Player player) {
        ChestGui gui = new ChestGui(3, ComponentHolder.of(toMM("<dark_purple><b>Profiili")));
        gui.show(player);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        //border
        Pattern pattern = new Pattern(
                "1111A1111",
                "1AAAAAAA1",
                "111111111"
        );
        PatternPane border = new PatternPane(0, 0, 9, 3, Pane.Priority.LOWEST, pattern);
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.displayName(toUndecoratedMM(""));
        backgroundMeta.setHideTooltip(true);
        background.setItemMeta(backgroundMeta);
        border.bindItem('1', new GuiItem(background));
        gui.addPane(border);
        //

        StaticPane navigationPane = new StaticPane(1, 1, 7, 1);
        NamespacedKey key = new NamespacedKey(instance, "aula");
        navigationPane.addItem(new GuiItem(itemCreator(Material.ENDER_CHEST, "Kosmetiikka", List.of("nii", "1"), "/cosmetics", false)), 0, 0);
        navigationPane.addItem(new GuiItem(itemCreator(Material.WRITABLE_BOOK, "Asetukset", List.of("nii", "2"), "/settings", false)), 2, 0);
        navigationPane.addItem(new GuiItem(itemCreator(Material.MAP, "Data", List.of("nii", "2"), "/data", false)), 4, 0);
        navigationPane.addItem(new GuiItem(itemCreator(Material.EMERALD, "Kauppa", List.of("nii", "2"), "/shop", false)), 6, 0);

        navigationPane.setOnClick(event -> {
            if (event.getCurrentItem() == null) return;
            String command = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.6F, 1F);
            player.performCommand(command);
        });

        gui.addPane(navigationPane);

        gui.update();
    }
}
