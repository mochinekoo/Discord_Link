package mochineko.discord_link.manager;

import net.dv8tion.jda.api.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class DiscordLinkData implements DeserializedData {

    private UUID uuid;
    private long discordID;
    private LocalDateTime authenticated_time;

    private DiscordLinkData() {}

    public DiscordLinkData(ResultSet resultSet) throws SQLException {
        this.uuid = UUID.fromString(resultSet.getString("uuid"));
        this.discordID = resultSet.getBigDecimal("discord_id").longValue();
        this.authenticated_time = LocalDateTime.parse(resultSet.getString("authenticated_time"));
    }

    public UUID getUUID() {
        return uuid;
    }

    public long getDiscordID() {
        return discordID;
    }

    public LocalDateTime getAuthenticatedTime() {
        return authenticated_time;
    }

}
