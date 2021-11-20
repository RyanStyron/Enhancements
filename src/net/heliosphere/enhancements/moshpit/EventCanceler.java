package net.heliosphere.enhancements.moshpit;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import net.heliosphere.enhancements.utils.WorldUtils.Moshpit;

public class EventCanceler implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();
        World world = location.getWorld();

        if (world == Moshpit.world())
            if (location.distanceSquared(world.getSpawnLocation()) <= 361)
                event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Location location = damager.getLocation();
        World world = location.getWorld();

        if (world == Moshpit.world())
            if (location.distanceSquared(world.getSpawnLocation()) <= 361)
                event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        if (world == Moshpit.world())
            if (player.getGameMode() != GameMode.CREATIVE)
                event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            World world = player.getWorld();

            if (world == Moshpit.world())
                if (player.getGameMode() != GameMode.CREATIVE)
                    event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        Entity entity = event.getEntity();
        Location location = entity.getLocation();
        World world = location.getWorld();

        if (world == Moshpit.world())
            if (location.distanceSquared(world.getSpawnLocation()) <= 361)
                event.setCancelled(true);
    }
}
