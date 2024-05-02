package net.gahvila.aula.Global;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.TimeZone;

import static net.gahvila.aula.Aula.instance;

public class TimeSync {

    public void timeSyncScheduler() {
        Bukkit.getServer().getScheduler().runTaskTimer(instance, () -> {
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Helsinki")); // Get the time instance
            long time = (1000 * cal.get(Calendar.HOUR_OF_DAY)) + (16 * cal.get(Calendar.MINUTE)) - 6000;

            Bukkit.getWorld("world").setTime(time);
        }, 0L, 20);

    }
}
