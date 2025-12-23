package mochineko.discord_link;

import mochineko.discord_link.listener.PlayerLoginListener;
import mochineko.discord_link.manager.DiscordManager;
import mochineko.discord_link.manager.JsonManager;
import mochineko.discord_link.status.FileType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //リスナー登録
        PluginManager plm = getServer().getPluginManager();
        plm.registerEvents(new PlayerLoginListener(), this);

        saveDefaultConfig(); //デフォルトConfigを生成
        DiscordManager.startBot(); //discordBotを起動

        //JSONを新規作成する
        for (FileType type : FileType.values()) {
            JsonManager jsonManager = new JsonManager(type);
            jsonManager.createJson();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        DiscordManager.stopBot();
    }
}
