package net.gahvila.aula.General.Managers;

import de.leonhard.storage.Json;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;

import static net.gahvila.aula.Aula.instance;

public class TeleportManager {

    public HashMap<String, Location> teleportCache = new HashMap<>();

    public void saveTeleport(String type, Location location) {
        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            Json tpData = new Json("teleportdata.json", instance.getDataFolder() + "/data/");
            tpData.getFileData().insert(type + ".world", location.getWorld().getName());
            tpData.getFileData().insert(type + ".x", location.getX());
            tpData.getFileData().insert(type + ".y", location.getY());
            tpData.getFileData().insert(type + ".z", location.getZ());
            tpData.getFileData().insert(type + ".yaw", location.getYaw());
            tpData.set(type + ".pitch", location.getPitch());
        });
        teleportCache.put(type, location);
    }

    public Location getTeleport(String type) {
        return teleportCache.get(type);
    }

    public Location getTeleportFromStorage(String type) {
        Json homeData = new Json("teleportdata.json", instance.getDataFolder() + "/data/");
        if (homeData.getFileData().containsKey(type)) {
            //String name = homeData.getString(warp + "." + ".owner");
            World world = Bukkit.getWorld(homeData.getString(type + ".world"));
            double x = homeData.getDouble(type + ".x");
            double y = homeData.getDouble(type + ".y");
            double z = homeData.getDouble(type + ".z");
            float yaw = (float) homeData.getDouble(type + ".yaw");
            float pitch = (float) homeData.getDouble(type + ".pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);
            return location;
        }
        return null;
    }

    public ArrayList<String> getTeleportsFromStorage() {
        Json tpData = new Json("teleportdata.json", instance.getDataFolder() + "/data/");
        ArrayList<String> teleports = new ArrayList<>();
        teleports.addAll(tpData.getFileData().singleLayerKeySet());
        return teleports;
    }
    public void putTeleportsIntoCache(){
        ArrayList<String> teleportTypes = getTeleportsFromStorage();
        for (String type : teleportTypes) {
            Location location = getTeleportFromStorage(type);
            if (location != null) {
                teleportCache.put(type, location);
            }
        }
    }
}
