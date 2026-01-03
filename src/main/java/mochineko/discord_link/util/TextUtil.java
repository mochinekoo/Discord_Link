package mochineko.discord_link.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TextUtil {

    public static final String PLUGIN_PREFIX = ChatColor.AQUA + "[DiscordLink] " + ChatColor.RESET;
    public static final String TEXT_ERROR = PLUGIN_PREFIX + ChatColor.RED + "[エラー] " + ChatColor.RESET;

    public static void sendInfoMessage(CommandSender sender, String message) {
        sender.sendMessage(PLUGIN_PREFIX + message);
    }

    public static void sendErrorMessage(CommandSender sender, String message) {
        sender.sendMessage(TEXT_ERROR + message);
    }

    public static void sendGlobalInfoMessage(String message) {
        Bukkit.broadcastMessage(PLUGIN_PREFIX + message);
    }

    public static void sendGlobalErrorMessage(String message) {
        Bukkit.broadcastMessage(TEXT_ERROR + message);
    }
}
