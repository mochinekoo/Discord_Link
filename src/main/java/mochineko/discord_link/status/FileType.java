package mochineko.discord_link.status;

import mochineko.discord_link.manager.DeserializedJson;
import mochineko.discord_link.manager.DiscordLinkData;

public enum FileType {
    CONFIG("discord_link.json", DiscordLinkData.class);

    private final String name;
    private final Class<? extends DeserializedJson> deserialized_class;

    FileType(String name, Class<? extends DeserializedJson> deserialized_class) {
        this.name = name;
        this.deserialized_class = deserialized_class;
    }

    public String getName() {
        return name;
    }

    public Class<? extends DeserializedJson> getDeserializedClass() {
        return deserialized_class;
    }
}
