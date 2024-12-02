package ru.lowsk.guests.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Хуйня бд
 *
 * @author lowsk
 */
public final class DataBase {
    /**
     * Метод базы данных
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String method = Guests.getPluginData().getMainConfig().getConfig().getString("method");

    /**
     * Адресс бд
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String address = Guests.getPluginData().getMainConfig().getConfig().getString("address");

    /**
     * Имя бд
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String name = Guests.getPluginData().getMainConfig().getConfig().getString("name");

    /**
     * Имя юзера
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String userName = Guests.getPluginData().getMainConfig().getConfig().getString("user-name");

    /**
     * Пароль юзера
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String userPassword = Guests.getPluginData().getMainConfig().getConfig().getString("user-password");
    private static final HashMap<String, Boolean> guestsCache = new HashMap<>();
    /**
     * Коннект к бд
     */
    @Getter
    private static Connection connection;

    /**
     * Обновить инфу о бд
     */
    @SneakyThrows
    public static void update() {
        switch (getMethod().toUpperCase()) {
            case "MYSQL" -> {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(String.format("jdbc:mysql://%s/%s?autoReconnect=true", getAddress(), getName()), getUserName(), getUserPassword());
                createTable();
            }
            case "SQLITE" -> {
                connection = DriverManager.getConnection(String.format("jdbc:sqlite://%s%s%s.db",
                        Guests.getPluginData().getInstance().getDataFolder().getAbsolutePath(), File.separator, getName()));
                createTable();
            }
            default -> {
            }
        }
    }

    /**
     * Создание таблицы
     */
    @SneakyThrows
    private static void createTable() {
        connection.createStatement().execute("CREATE TABLE IF NOT EXISTS players (name TEXT NOT NULL);");
    }

    /**
     * Закрытие соединения
     */
    @SneakyThrows
    public static void close() {
        if (connection != null && !connection.isClosed())
            connection.close();
        guestsCache.clear();
    }

    /**
     * Добавление игрока
     */
    @SneakyThrows
    public static void addPlayer(Player player) {
        String lPlayerName = player.getName().toLowerCase();
        switch (getMethod().toUpperCase()) {
            case "MYSQL", "SQLITE" ->
                    connection.createStatement().execute(String.format("INSERT INTO players (name) VALUES ('%s');", lPlayerName));
            case "YAML" -> {
                List<String> players = Guests.getPluginData().getConfigDataBase().getConfig().getStringList("AllowedPlayers");
                players.add(lPlayerName);
                Guests.getPluginData().getConfigDataBase().getConfig().set("AllowedPlayers", players);
                Guests.getPluginData().getConfigDataBase().reload();
            }
        }
        if (guestsCache.containsKey(lPlayerName))
            guestsCache.replace(lPlayerName, false);
    }

    /**
     * Добавление игрока
     */
    @SneakyThrows
    public static void addPlayer(String playerName) {
        String lPlayerName = playerName.toLowerCase();
        switch (getMethod().toUpperCase()) {
            case "MYSQL", "SQLITE" ->
                    connection.createStatement().execute(String.format("INSERT INTO players (name) VALUES ('%s');", lPlayerName));
            case "YAML" -> {
                List<String> players = Guests.getPluginData().getConfigDataBase().getConfig().getStringList("AllowedPlayers");
                players.add(lPlayerName);
                Guests.getPluginData().getConfigDataBase().getConfig().set("AllowedPlayers", players);
                Guests.getPluginData().getConfigDataBase().reload();
            }
        }
        if (guestsCache.containsKey(lPlayerName))
            guestsCache.replace(lPlayerName, false);
    }

    /**
     * Удаление игрока
     */
    @SneakyThrows
    public static void delPlayer(Player player) {
        String lPlayerName = player.getName().toLowerCase();
        switch (getMethod().toUpperCase()) {
            case "MYSQL", "SQLITE" ->
                    connection.createStatement().execute(String.format("DELETE FROM players WHERE name = '%s';", lPlayerName));
            case "YAML" -> {
                List<String> players = Guests.getPluginData().getConfigDataBase().getConfig().getStringList("AllowedPlayers");
                players.remove(lPlayerName);
                Guests.getPluginData().getConfigDataBase().getConfig().set("AllowedPlayers", players);
                Guests.getPluginData().getConfigDataBase().reload();
            }
        }
        if (guestsCache.containsKey(lPlayerName))
            guestsCache.replace(lPlayerName, true);
    }

    /**
     * Удаление игрока
     */
    @SneakyThrows
    public static void delPlayer(String playerName) {
        String lPlayerName = playerName.toLowerCase();
        switch (getMethod().toUpperCase()) {
            case "MYSQL", "SQLITE" ->
                    connection.createStatement().execute(String.format("DELETE FROM players WHERE name = '%s';", lPlayerName));
            case "YAML" -> {
                List<String> players = Guests.getPluginData().getConfigDataBase().getConfig().getStringList("AllowedPlayers");
                players.remove(lPlayerName);
                Guests.getPluginData().getConfigDataBase().getConfig().set("AllowedPlayers", players);
                Guests.getPluginData().getConfigDataBase().reload();
            }
        }
        if (guestsCache.containsKey(lPlayerName))
            guestsCache.replace(lPlayerName, true);
    }

    /**
     * Получение
     */
    @SneakyThrows
    public static boolean isGuest(Player player) {
        String lPlayerName = player.getName().toLowerCase();
        if (guestsCache.containsKey(lPlayerName))
            return guestsCache.get(lPlayerName);
        else {
            boolean guest = switch (getMethod().toUpperCase()) {
                case "MYSQL", "SQLITE" ->
                        !connection.createStatement().executeQuery(String.format("SELECT * FROM players WHERE name = '%s';", lPlayerName)).next();
                case "YAML" ->
                        !Objects.requireNonNull(Guests.getPluginData().getConfigDataBase().getConfig().getList("AllowedPlayers")).contains(lPlayerName);
                default -> true;
            };
            guestsCache.put(lPlayerName, guest);
            return guest;
        }
    }

    /**
     * Получение
     */
    @SneakyThrows
    public static boolean isGuest(String playerName) {
        String lPlayerName = playerName.toLowerCase();
        if (guestsCache.containsKey(lPlayerName))
            return guestsCache.get(lPlayerName);
        else {
            boolean guest = switch (getMethod().toUpperCase()) {
                case "MYSQL", "SQLITE" ->
                        connection.createStatement().execute(String.format("SELECT * FROM players WHERE name = '%s';", lPlayerName));
                case "YAML" ->
                        !Objects.requireNonNull(Guests.getPluginData().getConfigDataBase().getConfig().getList("AllowedPlayers")).contains(lPlayerName);
                default -> true;
            };
            guestsCache.put(lPlayerName, guest);
            return guest;
        }
    }
}
