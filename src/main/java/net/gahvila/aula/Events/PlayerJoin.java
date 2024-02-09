package net.gahvila.aula.Events;

import net.gahvila.aula.Aula;
import net.gahvila.aula.PlayerFeatures.ServerSelector;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static java.lang.Integer.MAX_VALUE;
import static org.bukkit.Bukkit.getServer;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        for (Player players: getServer().getOnlinePlayers()) {
            p.hidePlayer(Aula.instance, players);
            players.hidePlayer(Aula.instance, p);
            //send playerlist to player that is joining
            ServerPlayer serverPlayer1 = ((CraftPlayer) players).getHandle();
            ServerPlayer serverPlayer2 = ((CraftPlayer) p).getHandle();
            fixTab(p, serverPlayer1);
            fixTab(players, serverPlayer2);
        }
        p.teleport(new Location(Bukkit.getWorld("world"), 8.50, 9.0, -5.5));
        p.setGameMode(GameMode.ADVENTURE);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.setWalkSpeed(0);
        e.setJoinMessage(null);
        p.getInventory().clear();
        giveItems(p);
    }



    private void giveItems(Player player){

        //serverselector
        ItemStack serverselector = new ItemStack(Material.COMPASS);
        ItemMeta metaserverselector = serverselector.getItemMeta();
        metaserverselector.setDisplayName("§8> §6§lPalvelinvalikko §8<");
        serverselector.setItemMeta(metaserverselector);
        player.getInventory().setItem(4, serverselector);

    }

    private void fixTab(Player p1, ServerPlayer p2){
        ((CraftPlayer) p1).getHandle().connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, p2));
        ((CraftPlayer) p1).getHandle().connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LISTED, p2));
        ((CraftPlayer) p1).getHandle().connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE, p2));

    }
}
