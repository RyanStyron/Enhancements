package net.heliosphere.enhancements.utils;

import org.bukkit.ChatColor;

public class MessageUtils {
    
    public static String convertChatColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}