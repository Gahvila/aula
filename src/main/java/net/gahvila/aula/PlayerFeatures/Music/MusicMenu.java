package net.gahvila.aula.PlayerFeatures.Music;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.model.playmode.ChannelMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.MonoStereoMode;
import com.xxmicloxx.NoteBlockAPI.model.playmode.StereoMode;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.MAX_VALUE;
import static net.gahvila.aula.Aula.instance;
import static net.gahvila.aula.Utils.MiniMessageUtils.toMM;
import static net.gahvila.aula.Utils.MiniMessageUtils.toUndecoratedMM;

public class MusicMenu {
    private final MusicManager musicManager;


    public MusicMenu(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    public void showGUI(Player player) {
        ChestGui gui = new ChestGui(5, ComponentHolder.of(toUndecoratedMM("<dark_purple><b>Musiikkivalikko")));
        gui.show(player);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        Pattern pattern = new Pattern(
                "111111111",
                "1AAAAAAA1",
                "1AAAAAAA1",
                "1AAAAAAA1",
                "111AAA111"
        );
        PatternPane border = new PatternPane(0, 0, 9, 5, Pane.Priority.LOWEST, pattern);
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta backgroundMeta = background.getItemMeta();
        backgroundMeta.displayName(toUndecoratedMM(""));
        background.setItemMeta(backgroundMeta);
        border.bindItem('1', new GuiItem(background));
        gui.addPane(border);

        PaginatedPane pages = new PaginatedPane(1, 1, 7, 3);
        List<ItemStack> items = new ArrayList<>();
        NamespacedKey key = new NamespacedKey(instance, "aula");
        if (!musicManager.getSongs().isEmpty()){
            for (Song song : musicManager.getSongs()) {
                ItemStack item = new ItemStack(Material.MUSIC_DISC_STAL);;
                ItemMeta meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, song.getTitle());
                meta.displayName(toUndecoratedMM("<white>" + song.getTitle()));
                item.setItemMeta(meta);
                items.add(item);
            }
        }
        pages.populateWithItemStacks(items);
        gui.addPane(pages);

        pages.setOnClick(event -> {
            if (event.getCurrentItem() == null) return;
            String songName = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
            Song song = musicManager.getSong(songName);
            if (songName != null && song != null){
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, MAX_VALUE, 1F);
                musicManager.clearSongPlayer(player);

                RadioSongPlayer rsp = new RadioSongPlayer(song);
                rsp.setChannelMode(new MonoStereoMode());
                rsp.setVolume((byte) 50);
                rsp.addPlayer(player);
                rsp.setPlaying(true);

                musicManager.saveSongPlayer(player, rsp);

                player.sendMessage(toMM("<white>Laitoit kappaleen <yellow>" + songName + "</yellow> <white>soimaan."));
            }else {
                player.closeInventory();
                player.sendMessage("VIRHE: Tuota kappaletta ei ole olemassa.");
            }
        });

        StaticPane navigationPane = new StaticPane(0, 4, 9, 1);


        ItemStack pause = new ItemStack(Material.BARRIER);
        ItemMeta pauseMeta = pause.getItemMeta();
        pauseMeta.displayName(toUndecoratedMM("<red>Keskeytä"));
        pause.setItemMeta(pauseMeta);
        navigationPane.addItem(new GuiItem(pause, event -> {
            musicManager.clearSongPlayer(player);
        }), 1, 0);

        ItemStack previous = new ItemStack(Material.MANGROVE_BUTTON);
        ItemMeta previousMeta = previous.getItemMeta();
        previousMeta.displayName(toUndecoratedMM("<b>Takaisin"));
        previous.setItemMeta(previousMeta);
        navigationPane.addItem(new GuiItem(previous, event -> {
            if (pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8F, 0.7F);

                gui.update();
            }
        }), 3, 0);
        ItemStack next = new ItemStack(Material.WARPED_BUTTON);
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.displayName(toUndecoratedMM("<b>Seuraava"));
        next.setItemMeta(nextMeta);
        navigationPane.addItem(new GuiItem(next, event -> {
            if (pages.getPage() < pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8F, 0.8F);

                gui.update();
            }
        }), 5, 0);
        gui.addPane(navigationPane);

        gui.update();
    }
}
