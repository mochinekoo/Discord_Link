package mochineko.discord_link;

import mochineko.discord_link.listener.PlayerLoginListener;
import mochineko.discord_link.manager.DiscordManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager plm = getServer().getPluginManager();

        saveDefaultConfig();
        DiscordManager.startBot();
        plm.registerEvents(new PlayerLoginListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DiscordManager.stopBot();
    }
}
