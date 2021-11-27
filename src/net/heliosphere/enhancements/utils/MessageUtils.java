package net.heliosphere.enhancements.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageUtils {

    private static FileManager manager = new FileManager("messages");
    private static FileConfiguration messageFile = manager.getData();

    public static String convertChatColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(convertChatColors(message));
    }

    /**
     * @param sender     Indicates the user or console to which the message will be
     *                   sent.
     * @param configPath A message path in the messages.yml file.
     * @param arguments  Replaces the expressions denoted by angular brackets in
     *                   messages.yml with another string. If part of an expression
     *                   reads "<player>," then the third argument of
     *                   {@link #sendMessage(CommandSender, String, String...)}
     *                   should be entered as "<player>" and the fourth would be the
     *                   player's name.
     */
    public static void sendMessage(CommandSender sender, String configPath, String... arguments) {
        String message = messageFile.getString(configPath);

        if (arguments != null)
            for (int i = 0; i < arguments.length; i++)
                if (i % 2 == 0)
                    message = message.replace(arguments[i], arguments[i + 1]);
        sendMessage(sender, message);
    }
}