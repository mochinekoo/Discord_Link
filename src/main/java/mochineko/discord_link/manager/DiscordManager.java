package mochineko.discord_link.manager;

import mochineko.discord_link.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class DiscordManager extends ListenerAdapter {
    
    private static JDA jda;

    public static void startBot() {
        if (jda == null) {
            FileConfiguration config = Main.getPlugin(Main.class).getConfig();
            String token = config.getString("bot_token");
            jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                    .addEventListeners(new DiscordManager())
                    .build();

            jda.updateCommands()
                    .addCommands(Commands.slash("server-join", "サーバーに参加するコマンド")
                            .addOptions(
                                    new OptionData(OptionType.STRING, "mcid", "マインクラフトID"),
                                    new OptionData(OptionType.INTEGER, "code", "認証コード")
                            )
                    )
                    .queue();

            try {
                jda.awaitReady();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Bukkit.getLogger().info("Botの準備ができました!");
        }
    }

    public static void stopBot() {
        if (jda != null) {
            jda.shutdownNow();
        }
    }

}
