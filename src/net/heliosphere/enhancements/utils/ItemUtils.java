package net.heliosphere.enhancements.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    public static ItemStack moshpitStewItem() {
        ItemStack stew = new ItemStack(Material.MUSHROOM_STEW, 1);
        ItemMeta itemMeta = stew.getItemMeta();

        itemMeta.setDisplayName(MessageUtils.convertChatColors("&bMushroom Stew"));
        stew.setItemMeta(itemMeta);

        return stew;
    }
}