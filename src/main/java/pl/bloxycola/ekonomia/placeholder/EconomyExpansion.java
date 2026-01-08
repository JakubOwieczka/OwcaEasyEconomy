package pl.bloxycola.ekonomia.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.bloxycola.ekonomia.OwcaEasyEconomy;
import pl.bloxycola.ekonomia.config.EconomyConfig;
import pl.bloxycola.ekonomia.economy.EconomyService;

public class EconomyExpansion extends PlaceholderExpansion {

    private final OwcaEasyEconomy plugin;
    private final EconomyService economyService;
    private final EconomyConfig config;

    public EconomyExpansion(OwcaEasyEconomy plugin, EconomyService economyService, EconomyConfig config) {
        this.plugin = plugin;
        this.economyService = economyService;
        this.config = config;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ekonomia";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Owczaxxx";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return "";
        }

        if (params.equalsIgnoreCase("saldo")) {
            double bal = economyService.getBalance(player.getUniqueId());
            return economyService.formatStandard(bal);
        }

        if (params.equalsIgnoreCase("saldo_kmb")) {
            double bal = economyService.getBalance(player.getUniqueId());
            return economyService.formatKMB(bal);
        }

        return null;
    }
}
