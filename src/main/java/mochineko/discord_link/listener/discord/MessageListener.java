package mochineko.discord_link.listener.discord;

import mochineko.discord_link.Main;
import mochineko.discord_link.util.TextUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageListener extends ListenerAdapter {

    FileConfiguration config = Main.getPlugin(Main.class).getConfig();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Member member = event.getMember();
        User user = member.getUser();
        Message message = event.getMessage();

        if (!user.isBot()) {
            String format_message = getMinecraftSendFormat(event);
            Bukkit.broadcastMessage(format_message);
        }
    }

    private String getMinecraftSendFormat(MessageReceivedEvent event) {
        String format = config.getString("format.minecraft_format", "§b[Discord]§f<@{%Name}>: {%Message}");
        return format.replace("{%Name}", event.getAuthor().getName())
                .replace("{%Message}", event.getMessage().getContentRaw());
    }
}
