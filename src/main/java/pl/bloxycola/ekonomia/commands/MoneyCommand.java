package pl.bloxycola.ekonomia.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bloxycola.ekonomia.config.EconomyConfig;
import pl.bloxycola.ekonomia.economy.EconomyService;

import java.util.UUID;

public class MoneyCommand implements CommandExecutor {

    private final EconomyService economyService;
    private final EconomyConfig config;

    public MoneyCommand(EconomyService economyService, EconomyConfig config) {
        this.economyService = economyService;
        this.config = config;
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // /money
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Ta komenda tylko dla graczy.");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission("OwcaEasyEconomy.balance.player")) {
                player.sendMessage(ChatColor.RED + "Nie masz uprawnien.");
                return true;
            }

            UUID uuid = player.getUniqueId();
            double balance = economyService.getBalance(uuid);
            String formatted = economyService.formatStandard(balance);

            String msg = config.getMsgBalanceSelf()
                    .replace("{amount}", formatted)
                    .replace("{currency}", config.getCurrencySymbol());
            player.sendMessage(color(msg));
            return true;
        }

        // Subkomendy admina: /money set|give|remove <gracz> <kwota>
        if (args.length >= 3 &&
                (args[0].equalsIgnoreCase("set")
                        || args[0].equalsIgnoreCase("give")
                        || args[0].equalsIgnoreCase("remove"))) {

            if (!sender.hasPermission("OwcaEasyEconomy.admin")) {
                sender.sendMessage(ChatColor.RED + "Nie masz uprawnien.");
                return true;
            }

            String sub = args[0].toLowerCase();
            String targetName = args[1];

            OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
            if (target == null || (target.getName() == null && !target.hasPlayedBefore())) {
                sender.sendMessage(ChatColor.RED + "Ten gracz nigdy nie byl na serwerze.");
                return true;
            }

            double amount;
            try {
                amount = economyService.parseAmount(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Niepoprawna kwota.");
                return true;
            }

            UUID uuid = target.getUniqueId();
            double current = economyService.getBalance(uuid);

            switch (sub) {
                case "set":
                    economyService.setBalance(uuid, amount);
                    sender.sendMessage(ChatColor.GREEN + "Ustawiono saldo gracza "
                            + target.getName() + " na "
                            + economyService.formatStandard(amount) + " "
                            + config.getCurrencySymbol() + ".");
                    break;

                case "give":
                    economyService.deposit(uuid, amount);
                    sender.sendMessage(ChatColor.GREEN + "Dodano graczowi "
                            + target.getName() + " "
                            + economyService.formatStandard(amount) + " "
                            + config.getCurrencySymbol() + ". Nowe saldo: "
                            + economyService.formatStandard(economyService.getBalance(uuid)) + ".");
                    break;

                case "remove":
                    double newBal = Math.max(0, current - amount);
                    economyService.setBalance(uuid, newBal);
                    sender.sendMessage(ChatColor.GREEN + "Zabrano graczowi "
                            + target.getName() + " "
                            + economyService.formatStandard(amount) + " "
                            + config.getCurrencySymbol() + ". Nowe saldo: "
                            + economyService.formatStandard(newBal) + ".");
                    break;

                default:
                    sender.sendMessage(ChatColor.RED + "Nieznana subkomenda.");
                    break;
            }

            return true;
        }

        // /money <gracz> – sprawdzanie salda innego gracza
        if (!sender.hasPermission("OwcaEasyEconomy.balance.others")) {
            sender.sendMessage(ChatColor.RED + "Nie masz uprawnien.");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null || (target.getName() == null && !target.hasPlayedBefore())) {
            sender.sendMessage(ChatColor.RED + "Ten gracz nigdy nie byl na serwerze.");
            return true;
        }

        UUID uuid = target.getUniqueId();
        double balance = economyService.getBalance(uuid);
        String formatted = economyService.formatStandard(balance);

        String msg = config.getMsgBalanceOther()
                .replace("{player}", target.getName() == null ? "Gracz" : target.getName())
                .replace("{amount}", formatted)
                .replace("{currency}", config.getCurrencySymbol());

        sender.sendMessage(color(msg));
        return true;
    }
}
