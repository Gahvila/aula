package net.gahvila.aula.PlayerFeatures;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.gahvila.aula.Aula;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ServerSelector implements CommandExecutor, Listener {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        openGui((Player) sender);
        return true;
    }

    public static void openGui(Player p){
        Inventory inv = Bukkit.createInventory(p, 9, "§8» §6§lPalvelinvalikko" );
        updateGui(p, inv);
        p.openInventory(inv);
    }
    public static void updateGui(Player player, Inventory inventory) {
        //ITEMIT
        inventory.setItem(3, createItem(Material.GRASS_BLOCK, "§a§lSurvival", Arrays.asList("§7Liity klikkaamalla.")));
        inventory.setItem(5, createItem(Material.PINK_WOOL, "§6§lLuova", Arrays.asList("§7Liity klikkaamalla.")));
    }
    private static ItemStack createItem(Material material, String displayname, List<String> lore){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        //Palvelinvalikko
        if (e.getView().getTitle().equals("§8» §6§lPalvelinvalikko")){
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            int i = e.getSlot();
            switch (i) {
                case 3:
                    e.setCancelled(true);
                    out.writeUTF("survival");
                    p.sendPluginMessage(Aula.instance, "BungeeCord", out.toByteArray());
                    break;
                case 5:
                    e.setCancelled(true);
                    out.writeUTF("luova");
                    p.sendPluginMessage(Aula.instance, "BungeeCord", out.toByteArray());
                    break;
                default:
                    e.setCancelled(true);
                    break;
            }
        }
    }
}
