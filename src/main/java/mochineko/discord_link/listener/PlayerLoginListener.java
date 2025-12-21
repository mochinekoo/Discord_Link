package mochineko.discord_link.listener;

import mochineko.discord_link.manager.VerifyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (!player.isWhitelisted()) {
            if (!VerifyManager.containVerify(player)) {
                VerifyManager.startVerify(player);
            }
            VerifyManager.Verify verify = VerifyManager.getVerify(player);
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST,
                    "認証コード:" + verify.getCode());
        }
    }
}
