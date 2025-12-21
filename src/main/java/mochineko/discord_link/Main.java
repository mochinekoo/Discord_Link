package mochineko.discord_link;

import mochineko.discord_link.manager.DiscordManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();
        DiscordManager.startBot();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DiscordManager.stopBot();
    }
}
