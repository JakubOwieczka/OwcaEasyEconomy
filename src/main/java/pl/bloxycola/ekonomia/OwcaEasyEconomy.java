package pl.bloxycola.ekonomia;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import pl.bloxycola.ekonomia.config.EconomyConfig;
import pl.bloxycola.ekonomia.storage.DatabaseManager;
import pl.bloxycola.ekonomia.storage.SQLiteEconomyStorage;
import pl.bloxycola.ekonomia.economy.EconomyService;
import pl.bloxycola.ekonomia.commands.MoneyCommand;
import pl.bloxycola.ekonomia.commands.PayCommand;
import pl.bloxycola.ekonomia.placeholder.EconomyExpansion;
import pl.bloxycola.ekonomia.vault.VaultEconomyProvider;

import net.milkbowl.vault.economy.Economy;

public class OwcaEasyEconomy extends JavaPlugin {

    private static OwcaEasyEconomy instance;

    private EconomyConfig economyConfig;
    private DatabaseManager databaseManager;
    private SQLiteEconomyStorage storage;
    private EconomyService economyService;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        this.economyConfig = new EconomyConfig(getConfig());
        this.databaseManager = new DatabaseManager(this, economyConfig);
        this.databaseManager.init();
        this.storage = new SQLiteEconomyStorage(databaseManager);
        this.storage.initTables();

        this.economyService = new EconomyService(economyConfig, storage);

        getCommand("money").setExecutor(new MoneyCommand(economyService, economyConfig));
        getCommand("pay").setExecutor(new PayCommand(economyService, economyConfig));

        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            VaultEconomyProvider provider = new VaultEconomyProvider(economyService, economyConfig);
            Bukkit.getServicesManager().register(Economy.class, provider, this, ServicePriority.Highest);
            getLogger().info("Zarejestrowano Vault Economy provider.");
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new EconomyExpansion(this, economyService, economyConfig).register();
            getLogger().info("Zarejestrowano PlaceholderAPI expansion: %ekonomia_*%");
        }

        getLogger().info("OwcaEasyEconomy wlaczony.");
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.close();
        }
        getLogger().info("OwcaEasyEconomy wylaczony.");
    }

    public static OwcaEasyEconomy getInstance() {
        return instance;
    }

    public EconomyService getEconomyService() {
        return economyService;
    }

    public EconomyConfig getEconomyConfig() {
        return economyConfig;
    }

    public void ensureAccount(Player player) {
        economyService.ensureAccount(player.getUniqueId());
    }
}
