package pl.bloxycola.ekonomia.storage;

import pl.bloxycola.ekonomia.OwcaEasyEconomy;
import pl.bloxycola.ekonomia.config.EconomyConfig;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private final OwcaEasyEconomy plugin;
    private final EconomyConfig config;

    private Connection connection;

    public DatabaseManager(OwcaEasyEconomy plugin, EconomyConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void init() {
        if (!"SQLITE".equalsIgnoreCase(config.getDatabaseType())) {
            plugin.getLogger().warning("Obecnie wspierane jest tylko SQLITE. Ustawione: " + config.getDatabaseType());
        }

        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }

            File dbFile = new File(dataFolder, config.getDatabaseFile());
            String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();

            connection = DriverManager.getConnection(url);
            plugin.getLogger().info("Polaczono z baza SQLite: " + dbFile.getName());
        } catch (SQLException e) {
            plugin.getLogger().severe("Blad podczas laczenia z baza SQLite: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            init();
        }
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
