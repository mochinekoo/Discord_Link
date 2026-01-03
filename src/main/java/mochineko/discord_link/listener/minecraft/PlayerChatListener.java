package mochineko.discord_link.listener.minecraft;

import mochineko.discord_link.Main;
import mochineko.discord_link.manager.DiscordManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

    FileConfiguration config = Main.getPlugin(Main.class).getConfig();

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        long sendChannelID = config.getLong("send_channelID");
        JDA jda = DiscordManager.getJda();
        if (config.contains("send_channelID")) {
            TextChannel sendChannel = jda.getChannelById(TextChannel.class, sendChannelID);
            String format_message = getDiscordSendFormat(event);
            sendChannel.sendMessage(format_message).queue();
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {

    }

    private String getDiscordSendFormat(AsyncPlayerChatEvent event) {
        String format = config.getString("format.discord_format", "<{%Name}>:{%Message}");
        return format.replace("{%Name}", event.getPlayer().getName()).replace("{%Message}", event.getMessage());
    }
}
