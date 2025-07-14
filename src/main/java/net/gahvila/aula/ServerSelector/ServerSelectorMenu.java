package net.gahvila.aula.ServerSelector;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.gahvila.gahvilacore.Profiles.Prefix.Backend.Enum.PrefixTypes;
import net.gahvila.inventoryframework.adventuresupport.ComponentHolder;
import net.gahvila.inventoryframework.gui.GuiItem;
import net.gahvila.inventoryframework.gui.type.ChestGui;
import net.gahvila.inventoryframework.pane.OutlinePane;
import net.gahvila.inventoryframework.pane.Pane;
import net.gahvila.inventoryframework.pane.StaticPane;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.gahvilacore.Utils.MiniMessageUtils.toMM;
import static net.gahvila.gahvilacore.Utils.MiniMessageUtils.toUndecoratedMM;

public class ServerSelectorMenu {

    public void show(Player player) {
        player.showDialog(createServerSelectorDialog(player));
    }
    private Dialog createServerSelectorDialog(Player player) {
        return Dialog.create(builder -> builder.empty()
                .base(DialogBase.builder(toMM("<b>Palvelinvalikko</b>"))
                        .body(Arrays.asList(
                                DialogBody.plainMessage(toMM("Valitse pelimuoto mihin haluat liittyä")),
                                DialogBody.plainMessage(toMM("<st>                                           </st>"))
                        ))
                        .build())
                .type(DialogType.multiAction(
                        List.of(
                                ActionButton.builder(toMM("<#85FF00><b>Survival</b></#85FF00>"))
                                        .tooltip(toMM("<white>Klikkaa liittyäksesi"))
                                        .action(DialogAction.customClick((response, audience) -> {
                                                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                                                    out.writeUTF("Connect");
                                                    out.writeUTF("Survival");
                                                    player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
                                                },
                                                ClickCallback.Options.builder().build()
                                        ))
                                        .build(),
                                ActionButton.builder(toMM("<gold><b>Luova</b></gold>"))
                                        .tooltip(toMM("<white>Klikkaa liittyäksesi"))
                                        .action(DialogAction.customClick((response, audience) -> {
                                                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                                                    out.writeUTF("Connect");
                                                    out.writeUTF("Luova");
                                                    player.sendPluginMessage(instance, "BungeeCord", out.toByteArray());
                                                },
                                                ClickCallback.Options.builder().build()
                                        ))
                                        .build()
                        ),
                        ActionButton.builder(Component.text("Sulje valikko")).build(),
                        1
                )));
    }
}
