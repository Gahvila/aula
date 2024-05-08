package net.gahvila.aula.Hotbar;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Hotbar {
    DEFAULT(
            new ItemStack[] {
                    new ItemStack(Material.AIR),
                    HotbarManager.createItem(Material.PLAYER_HEAD, "<b><white>Profiili"),
                    new ItemStack(Material.AIR),
                    new ItemStack(Material.AIR),
                    HotbarManager.createItem(Material.RECOVERY_COMPASS, "<b><white>Palvelinvalikko"),
                    new ItemStack(Material.AIR),
                    new ItemStack(Material.AIR),
                    HotbarManager.createItem(Material.MUSIC_DISC_5, "<b><white>Musiikkivalikko"),
                    new ItemStack(Material.AIR)

            }
    ),
    PARKOUR(
            new ItemStack[] {
                    // Lisää PARKOUR-hotbarin esineet tähän
            }
    ),
    KOKKIKERHO(
            new ItemStack[] {
                    // Lisää KOKKIKERHO-hotbarin esineet tähän
            }
    );

    private final ItemStack[] items;

    Hotbar(ItemStack[] items) {
        this.items = items;
    }
    public ItemStack[] getItems() {
        return items;
    }
}
