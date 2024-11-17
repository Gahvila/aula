package net.gahvila.aula.General.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static net.gahvila.aula.Aula.instance;
import static net.gahvila.gahvilacore.Utils.MiniMessageUtils.toMM;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.joinMessage(null);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
            event.getPlayer().sendMessage(toMM("""
            Liity meidän salaseuroihin:
            <#0A7AFF><b>Bluesky</b></#0A7AFF>: <u><hover:show_text:'Klikkaa avataksesi Bluesky'><click:open_url:'https://bsky.gahvila.net'>bsky.gahvila.net</click></hover></u>
            <#FF0033><b>YouTube</b></#FF0033>: <u><hover:show_text:'Klikkaa avataksesi YouTube'><click:open_url:'https://yt.gahvila.net'>yt.gahvila.net</click></hover></u>
            <#5865F2><b>Discord</b></#5865F2>: <u><hover:show_text:'Klikkaa avataksesi Discord'><click:open_url:'https://dc.gahvila.net'>dc.gahvila.net</click></hover></u>"""));
        }, 60L); //3 sec
    }
}
