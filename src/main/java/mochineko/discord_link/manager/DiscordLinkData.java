package mochineko.discord_link.manager;

import net.dv8tion.jda.api.entities.User;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class DiscordLinkData implements DeserializedData {

    private Map<String, LinkData> linkDataMap;

    public LinkData getLinkDataMap(UUID uuid) {
        return linkDataMap.get(uuid.toString());
    }

    public static class LinkData {
        private String name;
        private long discordID;
        private String authenticated_time;

        public String getName() {
            return name;
        }

        public long getDiscordID() {
            return discordID;
        }

        public LocalDateTime getAuthenticatedTime() {
            return LocalDateTime.parse(authenticated_time);
        }
    }
}
