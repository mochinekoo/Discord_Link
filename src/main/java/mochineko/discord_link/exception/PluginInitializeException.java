package mochineko.discord_link.exception;

public class PluginInitializeException extends RuntimeException {
    public PluginInitializeException(String message) {
        super(message);
    }

    public PluginInitializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
