package mochineko.discord_link.listener.discord;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Member member = event.getMember();
        User user = member.getUser();
        Message message = event.getMessage();

        if (!user.isBot()) {
            String format = "[Discord]<" + user.getName() + ">:" + message.getContentRaw();
            Bukkit.broadcastMessage(format);
        }
    }
}
