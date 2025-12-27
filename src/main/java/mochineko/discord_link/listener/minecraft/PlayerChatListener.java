package mochineko.discord_link.listener.minecraft;

import mochineko.discord_link.Main;
import mochineko.discord_link.manager.DiscordManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = Main.getPlugin(Main.class).getConfig();
        long sendChannelID = config.getLong("send_channelID");
        JDA jda = DiscordManager.getJda();
        if (config.contains("send_channelID")) {
            final String format = "<" + player.getName() + ">" + " : " + event.getMessage();
            TextChannel sendChannel = jda.getChannelById(TextChannel.class, sendChannelID);
            sendChannel.sendMessage(format).queue();
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {

    }
}
