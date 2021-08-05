package net.heliosphere.enhancements.environment;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

// TODO: Update modifier to make all events' damage configurable; optimize.
public class DamageModifier implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        double damage = event.getDamage();
        DamageCause cause = event.getCause();
        int multiplier = 1;
        ArrayList<DamageCause> entityDamageCauses = new ArrayList<>();

        entityDamageCauses.add(DamageCause.ENTITY_ATTACK);
        entityDamageCauses.add(DamageCause.ENTITY_EXPLOSION);

        if (!entityDamageCauses.contains(cause)) {
            multiplier = 5;

            event.setDamage(damage * multiplier);
        }
        if (cause == DamageCause.VOID)
            event.setDamage(1000);
    }
}