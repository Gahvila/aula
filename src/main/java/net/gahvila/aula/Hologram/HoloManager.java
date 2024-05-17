package net.gahvila.aula.Hologram;

import de.oliver.fancyholograms.api.Hologram;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.HologramType;
import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.events.HologramCreateEvent;
import de.oliver.fancyholograms.api.events.HologramDeleteEvent;
import net.gahvila.aula.Utils.WorldGuardRegionChecker;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static de.oliver.fancyholograms.api.FancyHologramsPlugin.*;
import static net.gahvila.aula.Aula.instance;

public class HoloManager implements Listener {

    private static final List<Hologram> holograms = new ArrayList<>();

    public void showHolograms() {
        holograms.addAll(get().getHologramManager().getHolograms());
        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, task -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!WorldGuardRegionChecker.isInRegion(onlinePlayer, "spawn")){
                    for (Hologram hologram : holograms) {
                        hologram.showHologram(onlinePlayer);
                    }
                } else {
                    for (Hologram hologram : holograms) {
                        hologram.hideHologram(onlinePlayer);
                    }
                }
            }
        }, 0L, 10);
    }

    @EventHandler
    public void onCreate(HologramCreateEvent event) {
        holograms.clear();
        holograms.addAll(get().getHologramManager().getHolograms());
    }

    @EventHandler
    public void onDelete(HologramDeleteEvent event) {
        holograms.clear();
        holograms.addAll(get().getHologramManager().getHolograms());
    }
}
