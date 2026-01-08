package pl.bloxycola.ekonomia.vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import pl.bloxycola.ekonomia.config.EconomyConfig;
import pl.bloxycola.ekonomia.economy.EconomyService;

import java.util.List;
import java.util.UUID;

public class VaultEconomyProvider implements Economy {

    private final EconomyService economyService;
    private final EconomyConfig config;

    public VaultEconomyProvider(EconomyService economyService, EconomyConfig config) {
        this.economyService = economyService;
        this.config = config;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "OwcaEasyEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return -1;
    }

    @Override
    public String format(double amount) {
        return economyService.formatStandard(amount) + " " + config.getCurrencySymbol();
    }

    @Override
    public String currencyNamePlural() {
        return config.getCurrencySymbol();
    }

    @Override
    public String currencyNameSingular() {
        return config.getCurrencySymbol();
    }

    @Override
    public boolean hasAccount(String playerName) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(playerName);
        return p != null && (p.hasPlayedBefore() || p.isOnline());
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return player != null && (player.hasPlayedBefore() || player.isOnline());
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(playerName);
        if (p == null) return 0.0;
        return economyService.getBalance(p.getUniqueId());
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        if (player == null) return 0.0;
        return economyService.getBalance(player.getUniqueId());
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(playerName);
        if (p == null) {
            return new EconomyResponse(0, 0,
                    EconomyResponse.ResponseType.FAILURE, "Player null");
        }
        return withdrawPlayer(p, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        UUID uuid = player.getUniqueId();
        if (!economyService.has(uuid, amount)) {
            return new EconomyResponse(0, economyService.getBalance(uuid),
                    EconomyResponse.ResponseType.FAILURE, "Brak srodkow");
        }
        economyService.withdraw(uuid, amount);
        return new EconomyResponse(amount, economyService.getBalance(uuid),
                EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(playerName);
        if (p == null) {
            return new EconomyResponse(0, 0,
                    EconomyResponse.ResponseType.FAILURE, "Player null");
        }
        return depositPlayer(p, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        UUID uuid = player.getUniqueId();
        economyService.deposit(uuid, amount);
        return new EconomyResponse(amount, economyService.getBalance(uuid),
                EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    // --- Banki (nieużywane, ale wymagane przez interfejs) ---

    @Override
    public EconomyResponse createBank(String name, String player) {
        return notSupported();
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return notSupported();
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return notSupported();
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return notSupported();
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return notSupported();
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return notSupported();
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return notSupported();
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return notSupported();
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return notSupported();
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return notSupported();
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return notSupported();
    }

    @Override
    public List<String> getBanks() {
        return List.of();
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return true;
    }

    private EconomyResponse notSupported() {
        return new EconomyResponse(0, 0,
                EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banki nie sa wspierane");
    }
}
