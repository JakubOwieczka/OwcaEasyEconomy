package pl.bloxycola.ekonomia.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLiteEconomyStorage {

    private final DatabaseManager databaseManager;

    public SQLiteEconomyStorage(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void initTables() {
        String sql = "CREATE TABLE IF NOT EXISTS balances (" +
                "uuid TEXT PRIMARY KEY," +
                "balance REAL NOT NULL" +
                ");";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(UUID uuid, double defaultValue) {
        String sql = "SELECT balance FROM balances WHERE uuid = ?;";
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public void setBalance(UUID uuid, double amount) {
        String sql = "INSERT INTO balances (uuid, balance) VALUES (?, ?) " +
                "ON CONFLICT(uuid) DO UPDATE SET balance = excluded.balance;";
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid.toString());
            ps.setDouble(2, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
