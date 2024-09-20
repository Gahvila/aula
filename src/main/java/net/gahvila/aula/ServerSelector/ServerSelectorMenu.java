package net.gahvila.aula.ServerSelector;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.gahvilacore.Utils.MiniMessageUtils.toMM;
import static net.gahvila.gahvilacore.Utils.MiniMessageUtils.toUndecoratedMM;

public class ServerSelectorMenu {

    public void showGUI(Player player) {
        ChestGui gui = new ChestGui(3, ComponentHolder.of(toMM("<dark_purple><b>Palvelinvalikko")));
        gui.show(player);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);

        ItemStack backgroundItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundItemMeta = backgroundItem.getItemMeta();
        backgroundItemMeta.setHideTooltip(true);
        backgroundItemMeta.displayName(toMM(""));
        backgroundItem.setItemMeta(backgroundItemMeta);

        background.addItem(new GuiItem(backgroundItem));

        background.setRepeat(true);

        gui.addPane(background);

        StaticPane navigationPane = new StaticPane(3, 1, 3, 1);

        NamespacedKey key = new NamespacedKey(instance, "aula");


        ItemStack survival = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta survivalMeta = survival.getItemMeta();
        survivalMeta.displayName(toUndecoratedMM("<#85FF00><b>Survival</b></#85FF00>"));
        survivalMeta.lore(List.of(toUndecoratedMM("<white>Klikkaa liittyäksesi")));
        survivalMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "survival");
        survival.setItemMeta(survivalMeta);
        navigationPane.addItem(new GuiItem(survival), 0, 0);

        ItemStack luova = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta luovaMeta = luova.getItemMeta();
        luovaMeta.displayName(toUndecoratedMM("<gold><b>Luova</b></gold>"));
        luovaMeta.lore(List.of(toUndecoratedMM("<white>Klikkaa liittyäksesi")));
        luovaMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "luova");
        luova.setItemMeta(luovaMeta);
        navigationPane.addItem(new GuiItem(luova), 2, 0);

        navigationPane.setOnClick(event -> {
            if (event.getCurrentItem().getItemMeta().isHideTooltip()) return;
            String server = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.6F, 1F);
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
        });

        gui.addPane(navigationPane);

        gui.update();
    }
}
