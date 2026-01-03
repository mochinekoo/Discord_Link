package mochineko.discord_link.listener.minecraft;

import mochineko.discord_link.manager.DiscordManager;
import mochineko.discord_link.manager.VerifyManager;
import mochineko.discord_link.util.TextUtil;
import net.dv8tion.jda.api.JDA;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    private static final String LOGIN_FORMAT =
            TextUtil.PLUGIN_PREFIX + "Discordで認証を行ってください。 \n" +
            "認証コード：%s";

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!player.isWhitelisted()) {
            if (!VerifyManager.containVerify(player)) {
                VerifyManager.startVerify(player);
            }
            VerifyManager.Verify verify = VerifyManager.getVerify(player);
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST,
                    LOGIN_FORMAT.formatted(verify.getCode())
            );
        }
    }
}
