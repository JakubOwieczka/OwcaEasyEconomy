package pl.bloxycola.ekonomia.economy;

import pl.bloxycola.ekonomia.config.EconomyConfig;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberFormatter {

    private final EconomyConfig config;

    public NumberFormatter(EconomyConfig config) {
        this.config = config;
    }

    public String formatStandard(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("#,###.##", symbols);
        return df.format(value);
    }

    public String formatKMB(double value) {
        int decimals = config.getDecimals();
        String pattern = "#,##0." + "0".repeat(Math.max(0, decimals));
        DecimalFormat df = new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.US));

        double abs = Math.abs(value);
        if (abs >= 1_000_000_000) {
            return df.format(value / 1_000_000_000D) + "b";
        } else if (abs >= 1_000_000) {
            return df.format(value / 1_000_000D) + "m";
        } else if (abs >= 1_000) {
            return df.format(value / 1_000D) + "k";
        } else {
            return df.format(value);
        }
    }

    public double parseAmount(String input) throws NumberFormatException {
        input = input.trim().toLowerCase(Locale.ROOT).replace(",", "");

        double multiplier = 1.0;
        if (input.endsWith("k")) {
            multiplier = 1_000D;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("m")) {
            multiplier = 1_000_000D;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("b")) {
            multiplier = 1_000_000_000D;
            input = input.substring(0, input.length() - 1);
        }

        double base = Double.parseDouble(input);
        return base * multiplier;
    }
}
