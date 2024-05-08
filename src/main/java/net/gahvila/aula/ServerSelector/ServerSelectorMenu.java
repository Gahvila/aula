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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;
import static net.gahvila.aula.Utils.MiniMessageUtils.toUndecoratedMM;

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

        ItemStack survival = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta survivalMeta = survival.getItemMeta();
        survivalMeta.displayName(toUndecoratedMM("<#85FF00><b>Survival</b></#85FF00>"));
        survivalMeta.lore(List.of(toUndecoratedMM("<white>Klikkaa liittyäksesi")));
        survival.setItemMeta(survivalMeta);

        navigationPane.addItem(new GuiItem(survival, event -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("survival");
            player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
        }), 0, 0);

        ItemStack luova = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta luovaMeta = luova.getItemMeta();
        luovaMeta.displayName(toUndecoratedMM("<gold><b>Luova</b></gold>"));
        luovaMeta.lore(List.of(toUndecoratedMM("<white>Klikkaa liittyäksesi")));
        luova.setItemMeta(luovaMeta);

        navigationPane.addItem(new GuiItem(luova, event -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("luova");
            player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
        }), 2, 0);

        gui.addPane(navigationPane);

        gui.update();
    }
}
