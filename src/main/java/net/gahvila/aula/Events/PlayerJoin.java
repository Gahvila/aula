package net.gahvila.aula.Events;

import net.gahvila.aula.Aula;
import net.gahvila.aula.PlayerFeatures.ServerSelector;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.geysermc.floodgate.api.FloodgateApi;

import static java.lang.Integer.MAX_VALUE;
import static org.bukkit.Bukkit.getServer;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        for (Player players: getServer().getOnlinePlayers()) {
            p.hidePlayer(players);
            players.hidePlayer(p);
        }
        if (!FloodgateApi.getInstance().isFloodgatePlayer(p.getUniqueId())){
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 255, true, false));
            p.teleport(new Location(Bukkit.getWorld("world"), 8.50, 9.0, -5.5));
        }else{

            Bukkit.getServer().getScheduler().runTaskLater(Aula.instance, new Runnable() {
                @Override
                public void run() {
                    ServerSelector.bedrockGui(p);
                }
            }, 40);

            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, MAX_VALUE, 255, true, false));
            p.teleport(new Location(Bukkit.getWorld("world"), 8.50, 23.0, -5.5));
        }
        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.setWalkSpeed(0);
        e.setJoinMessage(null);
        p.getInventory().clear();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(p.getUniqueId())) {
            giveItems(p);
        }else{
            giveItemsBedrock(p);
        }

    }

    private void giveItems(Player player){

        //settings
        ItemStack settings = new ItemStack(Material.HOPPER);
        ItemMeta metasettings = settings.getItemMeta();
        metasettings.setDisplayName("§8> §6§lTeema §8<");
        settings.setItemMeta(metasettings);
        player.getInventory().setItem(2, settings);
        //serverselector
        ItemStack serverselector = new ItemStack(Material.COMPASS);
        ItemMeta metaserverselector = serverselector.getItemMeta();
        metaserverselector.setDisplayName("§8> §6§lPalvelinvalikko §8<");
        serverselector.setItemMeta(metaserverselector);
        player.getInventory().setItem(4, serverselector);
        //musiikki
        ItemStack music = new ItemStack(Material.JUKEBOX);
        ItemMeta metamusic = music.getItemMeta();
        metamusic.setDisplayName("§8> §6§lMusiikki §8<");
        music.setItemMeta(metamusic);
        player.getInventory().setItem(6, music);

    }

    private void giveItemsBedrock(Player player){

        //serverselector
        ItemStack serverselector = new ItemStack(Material.COMPASS);
        ItemMeta metaserverselector = serverselector.getItemMeta();
        metaserverselector.setDisplayName("§8> §6§lPalvelinvalikko §8<");
        serverselector.setItemMeta(metaserverselector);
        player.getInventory().setItem(4, serverselector);

    }
}
