package net.heliosphere.enhancements.environment;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.heliosphere.enhancements.Enhancements;

public class DamageModifier implements Listener {

    private Enhancements instance = Enhancements.getInstance();
    private FileConfiguration config = instance.getConfig();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        double damage = event.getDamage();
        DamageCause cause = event.getCause();
        ArrayList<DamageCause> exemptedCauses = new ArrayList<>();
        ArrayList<World> exemptedWorlds = new ArrayList<>();

        for (DamageCause damageCause : DamageCause.values()) {
            for (String causes : config.getStringList("exempted-causes"))
                if (causes.toString().contains(damageCause.toString()))
                    exemptedCauses.add(damageCause);
        }

        for (String world : config.getStringList("exempted-worlds"))
            exemptedWorlds.add(Bukkit.getWorld(world));

        if (!exemptedWorlds.contains(event.getEntity().getWorld())) {
            if (!exemptedCauses.contains(cause))
                event.setDamage(damage * config.getInt("modifier"));
            if (cause == DamageCause.VOID)
                event.setDamage(1000);
        }
    }
}