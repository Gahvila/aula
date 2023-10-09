package net.gahvila.aula.PlayerFeatures;

import com.interactiveboard.InteractiveBoard;
import com.interactiveboard.data.board.BoardData;
import com.interactiveboard.data.board.BoardDataProvider;
import com.interactiveboard.data.player.BoardView;
import com.interactiveboard.data.player.PlayerData;
import com.interactiveboard.data.player.PlayerDataProvider;
import net.gahvila.aula.Aula;
import net.gahvila.aula.Events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.geysermc.floodgate.api.FloodgateApi;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static net.gahvila.aula.Aula.instance;

public class Themes implements CommandExecutor, Listener {

    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();

    public static void openGui(Player p){
        Inventory inv = Bukkit.createInventory(p, 9, "§8» §6§lAsetukset §8| §d§lTeema" );
        updateGui(p, inv);
        p.openInventory(inv);
    }
    public static void updateGui(Player player, Inventory inventory) {
        inventory.setItem(0, createItem(Material.GRASS_BLOCK, "§eMinecraft (taivas)", Arrays.asList("§fNapsauta vaihtaaksesi teemaan")));
        inventory.setItem(1, createItem(Material.STONE, "§eHelsinki", Arrays.asList("§fNapsauta vaihtaaksesi teemaan")));
        inventory.setItem(2, createItem(Material.GLASS, "§eKansainvälinen avaruusasema", Arrays.asList("§fNapsauta vaihtaaksesi teemaan")));
        inventory.setItem(3, createItem(Material.GLOWSTONE_DUST, "§eLinnunrata", Arrays.asList("§fNapsauta vaihtaaksesi teemaan")));
        inventory.setItem(4, createItem(Material.RED_SAND, "§eGrand Canyon", Arrays.asList("§fNapsauta vaihtaaksesi teemaan")));
        inventory.setItem(5, createItem(Material.DIRT, "§eLempäälä", Arrays.asList("§fNapsauta vaihtaaksesi teemaan")));
        inventory.setItem(6, createItem(Material.BARRIER, "§eSuunnitteilla...", Arrays.asList("§7Kerro toki ideoita.")));
        inventory.setItem(7, createItem(Material.BARRIER, "§eSuunnitteilla...", Arrays.asList("§7Kerro toki ideoita.")));
        inventory.setItem(8, createItem(Material.BARRIER, "§eSuunnitteilla...", Arrays.asList("§7Kerro toki ideoita.")));
    }
    private static ItemStack createItem(Material material, String displayname, List<String> lore){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            switch (cmd.getName()) {
                case "teema":
                    if (!cooldown.containsKey(p.getUniqueId())) {
                        if (p.hasPermission("aula.teema")){
                            openGui(p);
                        }else{
                            p.sendMessage("Tarvitset teeman vaihtoon VIP-arvon.");
                        }
                        break;

                    }else{
                        long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + 10) - (System.currentTimeMillis() / 1000);
                        p.sendMessage("Voit vaihtaa teemaa uudelleen §e" + secondsleft + " §fsekuntin päästä.");
                    }
                    break;
            }
        }return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (getTheme(p) != null) {
            Bukkit.getServer().getScheduler().runTaskLater(Aula.instance, new Runnable() {
                @Override
                public void run() {
                    changeScene(p, getTheme(p));
                }
            }, 1);
        } else {
            Bukkit.getServer().getScheduler().runTaskLater(Aula.instance, new Runnable() {
                @Override
                public void run() {
                    changeScene(p, "jointheme");
                }
            }, 1);
        }
    }
    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        switch (e.getView().getTitle()) {
            case "§8» §6§lAsetukset §8| §d§lTeema":
                e.setCancelled(true);
                if (e.getCurrentItem() == null || (e.getCurrentItem().getType().equals(Material.AIR))) {
                    return;
                }
                switch (e.getSlot()) {
                    case 0:
                        if (!cooldown.containsKey(p.getUniqueId())) {
                            cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                            Bukkit.getScheduler().runTaskLater(instance, () -> cooldown.remove(p.getUniqueId()), 200);

                            p.closeInventory();
                            changeScene(p, "jointheme");
                            setTheme(p, "jointheme");
                            p.sendMessage("Teema valittu ja tallennettu. Saatat kokea lyhyen pätkäisyn yhteydessäsi.");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        }else{
                            p.closeInventory();
                            long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + 10) - (System.currentTimeMillis() / 1000);
                            p.sendMessage("Voit vaihtaa teemaa uudelleen §e" + secondsleft + " §fsekuntin päästä.");
                        }
                        break;
                    case 1:
                        if (!cooldown.containsKey(p.getUniqueId())) {
                            cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                            Bukkit.getScheduler().runTaskLater(instance, () -> cooldown.remove(p.getUniqueId()), 200);

                            p.closeInventory();
                            changeScene(p, "theme1");
                            setTheme(p, "theme1");
                            p.sendMessage("Teema valittu ja tallennettu. Saatat kokea lyhyen pätkäisyn yhteydessäsi.");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        }else{
                            p.closeInventory();
                            long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + 10) - (System.currentTimeMillis() / 1000);
                            p.sendMessage("Voit vaihtaa teemaa uudelleen §e" + secondsleft + " §fsekuntin päästä.");
                        }
                        break;
                    case 2:
                        if (!cooldown.containsKey(p.getUniqueId())) {
                            cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                            Bukkit.getScheduler().runTaskLater(instance, () -> cooldown.remove(p.getUniqueId()), 200);

                            p.closeInventory();
                            changeScene(p, "theme2");
                            setTheme(p, "theme2");
                            p.sendMessage("Teema valittu ja tallennettu. Saatat kokea lyhyen pätkäisyn yhteydessäsi.");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                            break;
                        }else{
                            p.closeInventory();
                            long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + 10) - (System.currentTimeMillis() / 1000);
                            p.sendMessage("Voit vaihtaa teemaa uudelleen §e" + secondsleft + " §fsekuntin päästä.");
                        }
                        break;
                    case 3:
                        if (!cooldown.containsKey(p.getUniqueId())) {
                            cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                            Bukkit.getScheduler().runTaskLater(instance, () -> cooldown.remove(p.getUniqueId()), 200);

                            p.closeInventory();
                            changeScene(p, "theme3");
                            setTheme(p, "theme3");
                            p.sendMessage("Teema valittu ja tallennettu. Saatat kokea lyhyen pätkäisyn yhteydessäsi.");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        }else{
                            p.closeInventory();
                            long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + 10) - (System.currentTimeMillis() / 1000);
                            p.sendMessage("Voit vaihtaa teemaa uudelleen §e" + secondsleft + " §fsekuntin päästä.");
                        }
                        break;
                    case 4:
                        if (!cooldown.containsKey(p.getUniqueId())) {
                            cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                            Bukkit.getScheduler().runTaskLater(instance, () -> cooldown.remove(p.getUniqueId()), 200);

                            p.closeInventory();
                            changeScene(p, "theme4");
                            setTheme(p, "theme4");
                            p.sendMessage("Teema valittu ja tallennettu. Saatat kokea lyhyen pätkäisyn yhteydessäsi.");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        }else{
                            p.closeInventory();
                            long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + 10) - (System.currentTimeMillis() / 1000);
                            p.sendMessage("Voit vaihtaa teemaa uudelleen §e" + secondsleft + " §fsekuntin päästä.");
                        }
                        break;
                    case 5:
                        if (!cooldown.containsKey(p.getUniqueId())) {
                            cooldown.put(p.getUniqueId(), System.currentTimeMillis());
                            Bukkit.getScheduler().runTaskLater(instance, () -> cooldown.remove(p.getUniqueId()), 200);

                            p.closeInventory();
                            changeScene(p, "theme5");
                            setTheme(p, "theme5");
                            p.sendMessage("Teema valittu ja tallennettu. Saatat kokea lyhyen pätkäisyn yhteydessäsi.");
                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        }else{
                            p.closeInventory();
                            long secondsleft = ((cooldown.get(p.getUniqueId()) / 1000) + 10) - (System.currentTimeMillis() / 1000);
                            p.sendMessage("Voit vaihtaa teemaa uudelleen §e" + secondsleft + " §fsekuntin päästä.");
                        }
                        break;
                    case 6:
                        p.closeInventory();
                        p.sendMessage("Tulossa... joskus, ehkä?");
                        //changeScene(p, "theme6");
                        //p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        break;
                    case 7:
                        p.closeInventory();
                        p.sendMessage("Tulossa... joskus, ehkä?");
                        //changeScene(p, "theme7");
                        //p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        break;
                    case 8:
                        p.closeInventory();
                        p.sendMessage("Tulossa... joskus, ehkä?");
                        //changeScene(p, "theme8");
                        //p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100, false, false));
                        break;
                    default:
                        e.setCancelled(true);
                        break;

                }
                break;
        }
    }


    public void changeScene(Player player, String sceneName){


        InteractiveBoard plugin = (InteractiveBoard) Bukkit.getPluginManager().getPlugin("InteractiveBoard");


        PlayerData playerData = plugin.getPlayerDataProvider().getPlayer(player);

        //

        BoardData boardData = plugin.getBoardDataProvider().getBoard("down");
        BoardView boardView = boardData.getBoardView(playerData, boardData.getBoardDisplays().get("default"));

        boardView.setBoardScene(sceneName);

        //

        BoardData boardData2 = plugin.getBoardDataProvider().getBoard("east");
        BoardView boardView2 = boardData2.getBoardView(playerData, boardData2.getBoardDisplays().get("default"));

        boardView2.setBoardScene(sceneName);

        //

        BoardData boardData3 = plugin.getBoardDataProvider().getBoard("north");
        BoardView boardView3 = boardData3.getBoardView(playerData, boardData3.getBoardDisplays().get("default"));

        boardView3.setBoardScene(sceneName);

        //

        BoardData boardData4 = plugin.getBoardDataProvider().getBoard("south");
        BoardView boardView4 = boardData4.getBoardView(playerData, boardData4.getBoardDisplays().get("default"));

        boardView4.setBoardScene(sceneName);

        //

        BoardData boardData5 = plugin.getBoardDataProvider().getBoard("up");
        BoardView boardView5 = boardData5.getBoardView(playerData, boardData5.getBoardDisplays().get("default"));

        boardView5.setBoardScene(sceneName);

        //

        BoardData boardData6 = plugin.getBoardDataProvider().getBoard("west");
        BoardView boardView6 = boardData6.getBoardView(playerData, boardData6.getBoardDisplays().get("default"));

        boardView6.setBoardScene(sceneName);

        //
    }

    public static String setTheme(Player player, String s) {
        File data = new File(instance.getDataFolder(), "playerdata.yml");
        FileConfiguration f = YamlConfiguration.loadConfiguration(data);
        try {
            String uuid = player.getUniqueId().toString();
            f.set(uuid + "." + ".theme", s);
            f.save(data);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static String getTheme(Player player) {
        File data = new File(instance.getDataFolder(), "playerdata.yml");
        FileConfiguration f = YamlConfiguration.loadConfiguration(data);
        String uuid = player.getUniqueId().toString();
        String savedTheme = f.getString(uuid + ".theme");
        return savedTheme;
    }
}
