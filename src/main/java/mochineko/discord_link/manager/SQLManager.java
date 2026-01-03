package mochineko.discord_link.manager;

import mochineko.discord_link.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.UUID;

public class SQLManager {

    private static SQLManager instance;
    private static final Main plugin = Main.getPlugin(Main.class);
    private static final FileConfiguration config = plugin.getConfig();

    //SQL
    private Connection con;

    //データベース情報
    private static String DRIVER_NAME; //ドライバーの名前
    private static String DB_HOST; //ホスト（IP）
    private static int DB_PORT; //ポート
    private static String JDBC_URL; //接続したいURL
    private static String USER_ID; //ログインしたいID
    private static String USER_PASS; //ログインしたいユーザーパスワード
    private static String DATABASE_NAME; //データベース名
    private static final String TABLE_NAME = "link_data";

    public SQLManager() {
        DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        DB_HOST = config.getString("database.host");
        DB_PORT = config.getInt("database.port", 3306);
        DATABASE_NAME = config.getString("database.database_name", "DiscordLink");
        JDBC_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DATABASE_NAME + "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
        USER_ID = config.getString("database.user_id");
        USER_PASS = config.getString("database.user_password");

        try {
            Class.forName(DRIVER_NAME);
            this.con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);
            setupDatabase();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLManager getInstance() {
        if (instance == null) {
            instance = new SQLManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return con;
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setupDatabase() {
        try {
            if (!existsSQL()) {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("USE " + DATABASE_NAME);
                stmt.executeUpdate("CREATE TABLE " + TABLE_NAME  + "(id int AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(36), discord_id BIGINT, authenticated_time DATETIME)");
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsSQL() {
        try {
            Statement stmt = con.createStatement();
            ResultSet table_selectSQL = stmt.executeQuery("SELECT * FROM " + TABLE_NAME);
            return table_selectSQL.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DiscordLinkData getData(UUID uuid) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE uuid = ?");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new DiscordLinkData(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void addData(UUID uuid, long discordID) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(uuid, discord_id, authenticated_time) VALUES (?, ?, NOW())");
            stmt.setString(1, uuid.toString());
            stmt.setBigDecimal(2, new BigDecimal(discordID));
            stmt.executeUpdate();
            stmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void convertJsonToSQL() {
        throw new UnsupportedOperationException();
    }

}
