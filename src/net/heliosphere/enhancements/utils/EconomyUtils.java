package net.heliosphere.enhancements.utils;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.heliosphere.enhancements.Enhancements;
import net.milkbowl.vault.economy.Economy;

public class EconomyUtils {

    private static Server server = Enhancements.getInstance().getServer();
    private static Economy economy = null;
    private static RegisteredServiceProvider<Economy> provider;

    private static boolean isEnabled() {
        if (server.getPluginManager().getPlugin("Vault") == null)
            return false;
        provider = server.getServicesManager().getRegistration(Economy.class);

        if (provider == null)
            return false;
        economy = provider.getProvider();

        return economy != null;
    }

    public static int getBalance(Player player) {
        if (isEnabled())
            return (int) economy.getBalance(player);
        return 0;
    }

    public static void setBalance(Player player, int balance) {
        if (isEnabled()) {
            economy.withdrawPlayer(player, getBalance(player));
            economy.depositPlayer(player, balance);
        }
    }
}
