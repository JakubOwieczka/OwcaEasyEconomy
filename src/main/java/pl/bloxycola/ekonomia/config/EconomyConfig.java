package pl.bloxycola.ekonomia.config;

import org.bukkit.configuration.file.FileConfiguration;

public class EconomyConfig {

    private final String currencySymbol;
    private final double startingBalance;
    private final double minimumPay;
    private final double confirmPayAbove;
    private final int decimals;

    private final String databaseType;
    private final String databaseFile;

    private final String msgBalanceSelf;
    private final String msgBalanceOther;
    private final String msgPaySent;
    private final String msgPayReceived;
    private final String msgPayConfirmRequired;
    private final String msgPayConfirmed;
    private final String msgNotEnoughMoney;
    private final String msgMinimumPay;

    public EconomyConfig(FileConfiguration config) {
        this.currencySymbol = config.getString("currency-symbol", "zł");
        this.startingBalance = config.getDouble("starting-balance", 0.0);
        this.minimumPay = config.getDouble("minimum-pay", 0.5);
        this.confirmPayAbove = config.getDouble("confirm-pay-above", 100000.0);
        this.decimals = config.getInt("number-format.decimals", 2);

        this.databaseType = config.getString("database.type", "SQLITE");
        this.databaseFile = config.getString("database.file", "economy.db");

        this.msgBalanceSelf = config.getString("messages.balance-self",
                "&aTwoje saldo: &f{amount} {currency}");
        this.msgBalanceOther = config.getString("messages.balance-other",
                "&aSaldo gracza &f{player}&a: &f{amount} {currency}");
        this.msgPaySent = config.getString("messages.pay-sent",
                "&fPrzelano &a{amount} {currency} &fdo gracza &a{player}");
        this.msgPayReceived = config.getString("messages.pay-received",
                "&fOtrzymales &a{amount} {currency} &fod gracza &a{player}");
        this.msgPayConfirmRequired = config.getString("messages.pay-confirm-required",
                "&cKwota jest duza. Aby potwierdzic wpisz &a/pay potwierdz");
        this.msgPayConfirmed = config.getString("messages.pay-confirmed",
                "&aPrzelew zostal potwierdzony i wykonany.");
        this.msgNotEnoughMoney = config.getString("messages.not-enough-money",
                "&cNie masz wystarczajacych srodkow.");
        this.msgMinimumPay = config.getString("messages.minimum-pay",
                "&cMinimalna kwota przelewu to {min} {currency}.");
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public double getMinimumPay() {
        return minimumPay;
    }

    public double getConfirmPayAbove() {
        return confirmPayAbove;
    }

    public int getDecimals() {
        return decimals;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getDatabaseFile() {
        return databaseFile;
    }

    public String getMsgBalanceSelf() {
        return msgBalanceSelf;
    }

    public String getMsgBalanceOther() {
        return msgBalanceOther;
    }

    public String getMsgPaySent() {
        return msgPaySent;
    }

    public String getMsgPayReceived() {
        return msgPayReceived;
    }

    public String getMsgPayConfirmRequired() {
        return msgPayConfirmRequired;
    }

    public String getMsgPayConfirmed() {
        return msgPayConfirmed;
    }

    public String getMsgNotEnoughMoney() {
        return msgNotEnoughMoney;
    }

    public String getMsgMinimumPay() {
        return msgMinimumPay;
    }
}
