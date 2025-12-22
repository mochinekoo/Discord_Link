package mochineko.discord_link.command.discord;

import mochineko.discord_link.manager.VerifyManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class DiscordJoinCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String cmd = event.getName();
        Member member = event.getMember();
        MessageChannelUnion channel = event.getChannel();
        OptionMapping mcid_option = event.getOption("mcid");
        OptionMapping code_option = event.getOption("code");
        if (cmd.equalsIgnoreCase("server-join")) {
            if (mcid_option != null && code_option != null) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(mcid_option.getAsString());
                boolean result = VerifyManager.stopVerify(player.getUniqueId(), code_option.getAsString());
                EmbedBuilder emebed = new EmbedBuilder();
                if (result) {
                    emebed.addField("成功", "認証に成功しました", true);
                }
                else {
                    emebed.addField("失敗", "認証に失敗しました", true);
                }
                channel.sendMessageEmbeds(emebed.build()).queue();
            }
        }
    }
}
