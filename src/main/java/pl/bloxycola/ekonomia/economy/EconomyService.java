package pl.bloxycola.ekonomia.economy;

import pl.bloxycola.ekonomia.config.EconomyConfig;
import pl.bloxycola.ekonomia.storage.SQLiteEconomyStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyService {

    private final EconomyConfig config;
    private final SQLiteEconomyStorage storage;
    private final NumberFormatter formatter;

    private final Map<UUID, Double> cache = new HashMap<>();
    private final Map<UUID, PendingTransfer> pendingTransfers = new HashMap<>();

    public EconomyService(EconomyConfig config, SQLiteEconomyStorage storage) {
        this.config = config;
        this.storage = storage;
        this.formatter = new NumberFormatter(config);
    }

    public void ensureAccount(UUID uuid) {
        getBalance(uuid);
    }

    public double getBalance(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }
        double balance = storage.getBalance(uuid, config.getStartingBalance());
        cache.put(uuid, balance);
        return balance;
    }

    public void setBalance(UUID uuid, double amount) {
        cache.put(uuid, amount);
        storage.setBalance(uuid, amount);
    }

    public boolean withdraw(UUID uuid, double amount) {
        double current = getBalance(uuid);
        if (current < amount) {
            return false;
        }
        setBalance(uuid, current - amount);
        return true;
    }

    public void deposit(UUID uuid, double amount) {
        double current = getBalance(uuid);
        setBalance(uuid, current + amount);
    }

    public boolean has(UUID uuid, double amount) {
        return getBalance(uuid) >= amount;
    }

    public String formatStandard(double value) {
        return formatter.formatStandard(value);
    }

    public String formatKMB(double value) {
        return formatter.formatKMB(value);
    }

    public double parseAmount(String input) throws NumberFormatException {
        return formatter.parseAmount(input);
    }

    public Map<UUID, PendingTransfer> getPendingTransfers() {
        return pendingTransfers;
    }

    public double getMinimumPay() {
        return config.getMinimumPay();
    }

    public double getConfirmPayAbove() {
        return config.getConfirmPayAbove();
    }

    public String getCurrencySymbol() {
        return config.getCurrencySymbol();
    }

    public static class PendingTransfer {
        public final UUID sender;
        public final UUID target;
        public final double amount;

        public PendingTransfer(UUID sender, UUID target, double amount) {
            this.sender = sender;
            this.target = target;
            this.amount = amount;
        }
    }
}
