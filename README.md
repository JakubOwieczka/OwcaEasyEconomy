# OwcaEasyEconomy - en_EN

OwcaEasyEconomy is a simple and efficient economy plugin for Paper/Spigot 1.16.5–1.21.x with full Vault and PlaceholderAPI support.  
It provides `/money` and `/pay` commands, supports amount formats like `1k`, `1.25m`, `2.5b`, and integrates easily with other plugins through Vault and placeholders. [file:9][file:10][web:45]

## Features

- Player balance:
  - `/money` – shows your own balance.
  - `/money <player>` – shows another player’s balance (permission `OwcaEasyEconomy.balance.others`). [file:10]
- Admin balance management:
  - `/money set <player> <amount>` – set balance.
  - `/money give <player> <amount>` – add money.
  - `/money remove <player> <amount>` – remove money. [file:10]
- Player-to-player payments:
  - `/pay <player> <amount>` – send money to another player.
  - Supports formats: `1000`, `1k`, `1.120k`, `10m`, `1.5b`, etc.
  - Minimum amount and confirmation threshold are configurable in `config.yml`.
  - Large payments above the threshold require `/pay confirm` (Polish variant in code: `/pay potwierdz`). [file:9]
- Storage:
  - SQLite (`economy.db`) – automatically creates `balances` table.
- Integrations:
  - **Vault** – can act as the main economy provider for other plugins. [web:12]
  - **PlaceholderAPI** – built-in expansion with balance placeholders. [web:16][web:48]

## Requirements

- Server: Paper / Spigot 1.16.5–1.21.x. [web:29][web:49]  
- Java 17 (recommended for modern Paper versions). [web:29]  
- Installed plugins:
  - [Vault](https://www.spigotmc.org/resources/vault.34315/)
  - [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Installation

1. Download the latest `OwcaEasyEconomy-x.y.z.jar`.
2. Place the JAR file into the `plugins/` folder on your server.
3. Make sure Vault and PlaceholderAPI are also present in the `plugins/` folder.
4. Fully restart the server (do **not** use `/reload`).
5. Adjust `config.yml` to your needs (currency symbol, starting balance, minimum pay amount, confirmation threshold, etc.). [file:9]

## Configuration (config.yml)

Key options: [file:9]

- `currency-symbol` – currency symbol used in messages (e.g. `zł`).
- `starting-balance` – starting balance for new players.
- `minimum-pay` – minimum `/pay` amount.
- `confirm-pay-above` – payments above this value require confirmation.
- `number-format.decimals` – number of decimal places for K/M/B format.
- `database.type` – currently `SQLITE`.
- `database.file` – database file name (`economy.db`).
- `messages:` section – all player-facing messages with placeholders:
  - `{amount}` – money amount,
  - `{currency}` – currency symbol,
  - `{player}` – player name.

## PlaceholderAPI

The plugin registers a custom PlaceholderAPI expansion with the identifier `ekonomia`. [web:16][web:48]

Available placeholders:

- `%ekonomia_saldo%` – player balance in standard format: `1,000`, `10,000`, `100,000`, etc.
- `%ekonomia_saldo_kmb%` – player balance in short K/M/B format: `1.12k`, `10.09m`, `100.52k`, etc. [file:9]

These placeholders can be used in any plugin that supports PlaceholderAPI (scoreboards, tablists, GUIs, chat formats, etc.). [web:45]


# OwcaEasyEconomy - pl_PL

OwcaEasyEconomy to prosty i wydajny plugin na ekonomię bez żadnych dodatkowych funkcji dla Paper/Spigot 1.16.5–1.21.x z pełną obsługą Vault i PlaceholderAPI.
Zapewnia polecenia `/money` i `/pay`, obsługuje formaty kwot, takie jak `1k`, `1.25m`, `2.5b` i łatwo integruje się z innymi wtyczkami za pośrednictwem Vault i placeholderów. [file:9][file:10][web:45]

## Funkcje

- Saldo gracza:
- `/money` – pokazuje Twoje saldo.
- `/money <player>` – pokazuje saldo innego gracza (uprawnienia `OwcaEasyEconomy.balance.others`). [file:10]
- Zarządzanie saldem przez administratora:
- `/money set <player> <amount>` – ustawia saldo.
- `/money give <gracz> <kwota>` – dodaj pieniądze.
- `/money remove <gracz> <kwota>` – usuń pieniądze. [plik:10]
- Płatności między graczami:
- `/pay <gracz> <kwota>` – wyślij pieniądze innemu graczowi.
- Obsługuje formaty: `1000`, `1k`, `1.120k`, `10m`, `1.5b` itd.
- Minimalna kwota i próg potwierdzenia można skonfigurować w pliku `config.yml`.
- Duże płatności powyżej progu wymagają komendy `/pay confirm` (polski wariant w kodzie: `/pay potwierdz`). [plik:9]
- Przechowywanie:
- SQLite (`economy.db`) – automatycznie tworzy tabelę `balances`.
- Integracje:
- **Vault** – może pełnić rolę głównego dostawcy ekonomii dla innych wtyczek. [web:12]
- **PlaceholderAPI** – wbudowane rozszerzenie z symbolami zastępczymi salda. [web:16][web:48]

## Wymagania

- Serwer: Paper / Spigot 1.16.5–1.21.x. [web:29][web:49]
- Java 17 (zalecana dla nowoczesnych wersji Paper). [web:29]
- Zainstalowane wtyczki:
- [Vault](https://www.spigotmc.org/resources/vault.34315/)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Instalacja

1. Pobierz najnowszą wersję pliku `OwcaEasyEconomy-x.y.z.jar`.
2. Umieść plik JAR w folderze `plugins/` na serwerze.
3. Upewnij się, że Vault i PlaceholderAPI znajdują się również w folderze `plugins/`.
4. Zrestartuj serwer (**nie** używaj `/reload`).
5. Dostosuj plik `config.yml` do swoich potrzeb (symbol waluty, saldo początkowe, minimalna kwota płatności, próg potwierdzenia itp.). [file:9]

## Konfiguracja (config.yml)

Kluczowe opcje: [file:9]

- `currency-symbol` – symbol waluty używany w wiadomościach (np. `zł`).
- `starting-balance` – saldo początkowe dla nowych graczy.
- `minimum-pay` – minimalna kwota `/pay`.
- `confirm-pay-above` – płatności powyżej tej wartości wymagają potwierdzenia.
- `number-format.decimals` – liczba miejsc po przecinku dla formatu K/M/B.
- `database.type` – obecnie `SQLITE`.
- `database.file` – nazwa pliku bazy danych (`economy.db`).
- sekcja `messages:` – wszystkie wiadomości skierowane do gracza z placeholderami:
- `{amount}` – kwota,
- `{currency}` – symbol waluty,
- `{player}` – nazwa gracza.

## PlaceholderAPI

Plugin rejestruje niestandardowe rozszerzenie PlaceholderAPI o identyfikatorze `ekonomia`. [web:16][web:48]

Dostępne placeholdery:

- `%ekonomia_saldo%` – saldo gracza w formacie standardowym: `1000`, `10000`, `100000` itd.
- `%ekonomia_saldo_kmb%` – saldo gracza w skróconym formacie K/M/B: `1,12k`, `10,09m`, `100,52b` itd. [file:9]

Te placeholdery można używać w dowolnym pluginie obsługującym PlaceholderAPI (scoreboardy, tablisty, GUI, chat itp.). [web:45]
