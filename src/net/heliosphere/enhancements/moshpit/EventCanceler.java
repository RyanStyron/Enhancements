package net.heliosphere.enhancements.moshpit;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
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

    private boolean isCancellable(boolean spawnRegionOnly, Location location) {
        boolean cancellable = false;
        World world = location.getWorld();

        if (world == Moshpit.world()) {
            if (!spawnRegionOnly)
                cancellable = true;
            else if (location.distanceSquared(world.getSpawnLocation()) <= Moshpit.spawnDistanceSquared())
                cancellable = true;
        }
        return cancellable;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (isCancellable(true, event.getEntity().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (isCancellable(true, event.getEntity().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (isCancellable(true, event.getEntity().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (isCancellable(false, event.getPlayer().getLocation()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
                if (isCancellable(false, event.getWhoClicked().getLocation()))
                    event.setCancelled(true);
            }
        }
    }
}
