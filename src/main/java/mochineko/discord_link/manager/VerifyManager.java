package mochineko.discord_link.manager;

import mochineko.discord_link.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

public class VerifyManager {

    private static final Map<UUID, Verify> verifyMap = new HashMap<>();

    public static void startVerify(Player player) {
        verifyMap.put(player.getUniqueId(), new Verify(player));
    }

    public static boolean stopVerify(UUID uuid, String code) {
        Verify verify = verifyMap.get(uuid);
        if (verify != null) {
            return verify.stopVerify(code);
        }
        return false;
    }

    @Nullable
    public static Verify getVerify(Player player) {
        return verifyMap.get(player.getUniqueId());
    }

    public static boolean containVerify(OfflinePlayer player) {
        return verifyMap.containsKey(player.getUniqueId());
    }

    public static class Verify {
        private static final Random random = new Random();
        private OfflinePlayer player;
        private String code;
        private BukkitTask task;

        public Verify(OfflinePlayer player) {
            this.player = player;
            this.code = String.format("%04d", random.nextInt(1000));
            this.task = new BukkitRunnable() {
                private int count;
                @Override
                public void run() {
                    if (count >= 300) {
                        code = String.format("%04d", random.nextInt(1000));
                        count = 0;
                    }
                    count++;
                }
            }.runTaskTimer(Main.getPlugin(Main.class), 0L, 20L);
        }

        public void changeCode() {
            code = String.format("%04d", random.nextInt(1000));
        }

        public boolean stopVerify(String code) {
            if (this.code.equalsIgnoreCase(code)) {
                player.setWhitelisted(true);
                this.task.cancel();
                return true;
            }
            return false;
        }

        public OfflinePlayer getPlayer() {
            return player;
        }

        public String getCode() {
            return code;
        }
    }
}
