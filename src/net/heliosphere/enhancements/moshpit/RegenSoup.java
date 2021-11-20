package net.heliosphere.enhancements.moshpit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.heliosphere.enhancements.Enhancements;
import net.heliosphere.enhancements.utils.ItemUtils;
import net.heliosphere.enhancements.utils.MathUtils;
import net.heliosphere.enhancements.utils.WorldUtils.Moshpit;

public class RegenSoup implements Listener {

    private Enhancements instance = Enhancements.getInstance();
    private HashMap<Player, List<Integer>> missingSoupMap = new HashMap<Player, List<Integer>>();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();
        PlayerInventory inventory = player.getInventory();

        if (world == Moshpit.world()) {
            if (location.distanceSquared(world.getSpawnLocation()) > 361) {
                if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                    ItemStack item = inventory.getItemInMainHand();

                    if (item == null)
                        return;
                    Material material = item.getType();

                    if (material == null)
                        return;

                    if (material.equals(Material.MUSHROOM_STEW)) {
                        double playerMaxHealth = player.getMaxHealth();
                        double playerHealth = player.getHealth();
                        int inventorySlot = inventory.getHeldItemSlot();
                        ItemStack bowl = new ItemStack(Material.BOWL, 1);
                        int regenHealth = instance.getConfig().getInt("soup-regen");

                        if (playerHealth + regenHealth <= playerMaxHealth)
                            player.setHealth(playerHealth + regenHealth);
                        else
                            player.setHealth(playerHealth + (playerMaxHealth - playerHealth));
                        inventory.setItem(inventorySlot, bowl);

                        if (missingSoupMap.get(player) == null) {
                            List<Integer> inventorySlots = new ArrayList<Integer>();

                            missingSoupMap.put(player, inventorySlots);
                        }
                        missingSoupMap.get(player).add(inventorySlot);

                        for (Player onlinePlayer : Moshpit.world().getPlayers())
                            onlinePlayer.playSound(location, Sound.ENTITY_GENERIC_EAT, 1.0F, 1.0F);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {
                            @Override
                            public void run() {
                                /*
                                 * The following line does not use the world or location variable because the
                                 * Moshpit is already the stored world as is their location, so it would always
                                 * return true.
                                 */
                                if (player.getWorld() == Moshpit.world()) {
                                    if (missingSoupMap.get(player).contains(inventorySlot)) {
                                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                                        inventory.setItem(inventorySlot, ItemUtils.moshpitStewItem());
                                        missingSoupMap.get(player).remove(
                                                MathUtils.findIndexInList(missingSoupMap.get(player), inventorySlot));
                                    }
                                } else
                                    missingSoupMap.remove(player);
                            }
                        }, (instance.getConfig().getInt("time-delay")) * 20);
                    }
                }
            }
        }
    }

    /**
     * Replaces an empty soup in the inventory of a player when they kill an
     * opponent.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getWorld() == Moshpit.world()) {
            if (missingSoupMap.get(player) != null)
                missingSoupMap.get(player).clear();

            if (player.getKiller() != null) {
                Player killer = player.getKiller();
                List<Integer> killerInventorySlots = missingSoupMap.get(killer);

                if (killerInventorySlots != null && killerInventorySlots.size() > 0) {
                    int inventorySlot = killerInventorySlots.get(0);

                    killer.playSound(killer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                    killer.getInventory().setItem(inventorySlot, ItemUtils.moshpitStewItem());
                    killerInventorySlots.remove(MathUtils.findIndexInList(missingSoupMap.get(killer), inventorySlot));
                }
            }
        }
    }

    public static void setSoups(Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();

        if (location.getWorld() == Moshpit.world()) {
            if (location.distanceSquared(world.getSpawnLocation()) <= 361)
                for (int slot = 3; slot <= 6; slot++)
                    player.getInventory().setItem(slot, ItemUtils.moshpitStewItem());
        }
    }
}