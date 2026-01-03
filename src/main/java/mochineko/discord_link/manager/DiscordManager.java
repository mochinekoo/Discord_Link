package mochineko.discord_link.manager;

import mochineko.discord_link.Main;
import mochineko.discord_link.command.discord.DiscordJoinCommand;
import mochineko.discord_link.exception.PluginInitializeException;
import mochineko.discord_link.listener.discord.MessageListener;
import mochineko.discord_link.util.TextUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

public class DiscordManager extends ListenerAdapter {
    
    private static JDA jda;

    public static void startBot() {
        if (jda == null) {
            FileConfiguration config = Main.getPlugin(Main.class).getConfig();
            String token = config.getString("bot_token");

            try {
                jda = JDABuilder.createDefault(token)
                        .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                        .addEventListeners(new DiscordManager())
                        .addEventListeners(new DiscordJoinCommand())
                        .addEventListeners(new MessageListener())
                        .build();

                jda.updateCommands()
                        .addCommands(Commands.slash("server-join", "サーバーに参加するコマンド")
                                .addOptions(
                                        new OptionData(OptionType.STRING, "mcid", "マインクラフトID"),
                                        new OptionData(OptionType.STRING, "code", "認証コード")
                                )
                        )
                        .queue();

                jda.awaitReady();
            } catch (InterruptedException e) {
                Bukkit.getLogger().severe("エラーが発生したため、プラグインが無効になります。詳細はコンソールを確認してください。");
                Bukkit.getPluginManager().disablePlugin(Main.getPlugin(Main.class));
                throw new RuntimeException(e);
            } catch (IllegalArgumentException | InvalidTokenException e) {
                for (OfflinePlayer operators : Bukkit.getOperators()) {
                    if (operators.isOnline()) {
                        TextUtil.sendErrorMessage(operators.getPlayer(), "DiscordTokenが設定されていないか、使用できないTokenのため、プラグインを使用できません。詳細はコンソールを確認してください。");
                    }
                }
                Bukkit.getLogger().severe(
                        """
                                =======DiscordTokenの設定方法=======\s
                                1. DiscordDeveloperPortalで、Tokenを取得する。\s
                                2. config.yml の bot_tokenに、取得できたTokenを書き込む。\s
                                3. /reload を行う。
                                ========================
                             """
                );
                throw new PluginInitializeException("DiscordTokenが設定されていないか、使用できないTokenのため、プラグインを使用できません。", e);
            }


            Bukkit.getLogger().info("Botの準備ができました!");
        }
    }

    public static void stopBot() {
        if (jda != null) {
            jda.shutdownNow();
        }
    }

    public static JDA getJda() {
        return jda;
    }

}
