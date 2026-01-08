package pl.bloxycola.ekonomia.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import pl.bloxycola.ekonomia.config.EconomyConfig;
import pl.bloxycola.ekonomia.economy.EconomyService;

import java.util.Map;
import java.util.UUID;

public class PayCommand implements CommandExecutor {

    private final EconomyService economyService;
    private final EconomyConfig config;

    public PayCommand(EconomyService economyService, EconomyConfig config) {
        this.economyService = economyService;
        this.config = config;
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Ta komenda tylko dla graczy.");
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("OwcaEasyEconomy.pay")) {
            player.sendMessage(ChatColor.RED + "Nie masz uprawnien.");
            return true;
        }

        Map<UUID, EconomyService.PendingTransfer> pending = economyService.getPendingTransfers();

        if (args.length == 1 &&
               (args[0].equalsIgnoreCase("potwierdz")
               || args[0].equalsIgnoreCase("confirm"))) {
            EconomyService.PendingTransfer transfer = pending.remove(player.getUniqueId());
            if (transfer == null) {
                player.sendMessage(ChatColor.RED + "Nie masz zadnego przelewu do potwierdzenia.");
                return true;
            }

            if (!economyService.has(player.getUniqueId(), transfer.amount)) {
                player.sendMessage(color(config.getMsgNotEnoughMoney()));
                return true;
            }

            if (!transfer.sender.equals(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "To nie jest twoj przelew.");
                return true;
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(transfer.target);
            if (target == null || (target.getName() == null && !target.hasPlayedBefore())) {
                player.sendMessage(ChatColor.RED + "Ten gracz nigdy nie byl na serwerze.");
                return true;
            }

            economyService.withdraw(player.getUniqueId(), transfer.amount);
            economyService.deposit(transfer.target, transfer.amount);

            String sentMsg = config.getMsgPaySent()
                    .replace("{amount}", economyService.formatStandard(transfer.amount))
                    .replace("{currency}", config.getCurrencySymbol())
                    .replace("{player}", target.getName() == null ? "Gracz" : target.getName());

            String receivedMsg = config.getMsgPayReceived()
                    .replace("{amount}", economyService.formatStandard(transfer.amount))
                    .replace("{currency}", config.getCurrencySymbol())
                    .replace("{player}", player.getName());

            player.sendMessage(color(sentMsg));
            if (target.isOnline()) {
                target.getPlayer().sendMessage(color(receivedMsg));
            }

            player.sendMessage(color(config.getMsgPayConfirmed()));
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uzycie: /pay <gracz> <kwota>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target == null || (target.getName() == null && !target.hasPlayedBefore())) {
            player.sendMessage(ChatColor.RED + "Ten gracz nigdy nie byl na serwerze.");
            return true;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "Nie mozesz wyslac przelewu samemu sobie.");
            return true;
        }

        double amount;
        try {
            amount = economyService.parseAmount(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Niepoprawna kwota.");
            return true;
        }

        if (amount < config.getMinimumPay()) {
            String msg = config.getMsgMinimumPay()
                    .replace("{min}", economyService.formatStandard(config.getMinimumPay()))
                    .replace("{currency}", config.getCurrencySymbol());
            player.sendMessage(color(msg));
            return true;
        }

        if (!economyService.has(player.getUniqueId(), amount)) {
            player.sendMessage(color(config.getMsgNotEnoughMoney()));
            return true;
        }

        if (amount >= config.getConfirmPayAbove()) {
            EconomyService.PendingTransfer transfer =
                    new EconomyService.PendingTransfer(player.getUniqueId(), target.getUniqueId(), amount);
            pending.put(player.getUniqueId(), transfer);

            player.sendMessage(color(config.getMsgPayConfirmRequired()));
            return true;
        }

        economyService.withdraw(player.getUniqueId(), amount);
        economyService.deposit(target.getUniqueId(), amount);

        String sentMsg = config.getMsgPaySent()
                .replace("{amount}", economyService.formatStandard(amount))
                .replace("{currency}", config.getCurrencySymbol())
                .replace("{player}", target.getName() == null ? "Gracz" : target.getName());

        String receivedMsg = config.getMsgPayReceived()
                .replace("{amount}", economyService.formatStandard(amount))
                .replace("{currency}", config.getCurrencySymbol())
                .replace("{player}", player.getName());

        player.sendMessage(color(sentMsg));
        if (target.isOnline()) {
            target.getPlayer().sendMessage(color(receivedMsg));
        }

        return true;
    }
}
