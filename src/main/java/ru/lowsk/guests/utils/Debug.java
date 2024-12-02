package ru.lowsk.guests.utils;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.lowsk.guests.core.Guests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Дебаг надстройка
 *
 * @author lowsk
 */
public final class Debug {
    /**
     * Логгер плагина
     */
    private final static ComponentLogger log = Guests.getPluginData().getInstance().getComponentLogger();
    /**
     * Префикс плагина
     */
    @Getter(lazy = true)
    private static final String prefix =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("Debug.prefix");

    /**
     * Отправить сообщение в консоль (и лог файл)
     *
     * @param msg   сообщение
     * @param level уровень логирования
     * @param args  аргументы для вставки в текст
     */
    public static void msg(@NotNull String msg, @NotNull LogLevel level, Object... args) {
        switch (level) {
            case INFO -> print(msg, log::info, args);
            case WARNING -> print(msg, log::warn, args);
            case ERROR -> print(msg, log::error, args);
        }
    }

    /**
     * Отправить сообщение в консоль (и лог файл)
     *
     * @param msg   сообщения
     * @param level уровень логирования
     * @param args  аргументы для вставки в текст
     */
    public static void msg(@NotNull List<String> msg, @NotNull LogLevel level, Object... args) {
        switch (level) {
            case INFO -> print(msg, log::info, args);
            case WARNING -> print(msg, log::warn, args);
            case ERROR -> print(msg, log::error, args);
        }
    }

    /**
     * Отправить сообщение в консоль (и лог файл)
     *
     * @param msg   сообщения
     * @param level уровень логирования
     * @param args  аргументы для вставки в текст
     */
    public static void msg(@NotNull Set<String> msg, @NotNull LogLevel level, Object... args) {
        switch (level) {
            case INFO -> print(msg, log::info, args);
            case WARNING -> print(msg, log::warn, args);
            case ERROR -> print(msg, log::error, args);
        }
    }

    /**
     * Переводит цветовые кода
     *
     * @param text текст хд
     * @return переведенное сообщение
     */
    public static Component translateColorCodes(String text) {
        return LegacyComponentSerializer.legacyAmpersand().toBuilder().extractUrls().build().deserialize(text);
    }

    /**
     * Переводит цветовые кода
     *
     * @param text текст хд
     * @return переведенное сообщение
     */
    public static List<Component> translateColorCodes(List<String> text) {
        List<Component> components = new ArrayList<>();
        text.forEach(line -> components.add(LegacyComponentSerializer.legacyAmpersand().toBuilder().extractUrls().build().deserialize(line)));
        return components;
    }

    /**
     * Подставляет аргументы в строку
     *
     * @param text текст хд
     * @return строку с подставленными аргументами
     */
    public static String getWithArgs(String text, Object... args) {
        for (int i = 0; i < args.length; ++i)
            text = text.replace(String.format("{%d}", i), args[i].toString());
        return text;
    }

    /**
     * Подставляет аргументы в строку
     *
     * @param text текст хд
     * @return строку с подставленными аргументами
     */
    public static List<String> getWithArgs(List<String> text, Object... args) {
        List<String> result = new ArrayList<>();

        text.forEach(line -> {
            for (int i = 0; i < args.length; ++i)
                line = line.replace(String.format("{%d}", i), args[i].toString());

            result.add(line);
        });

        return result;
    }

    /**
     * Печатает шаблонную строку
     *
     * @param text          текст
     * @param printFunction функция вывода (void + только 1 аргумент!)
     * @param args          аргументы
     */
    public static void print(String text, Consumer<Component> printFunction, Object... args) {
        printFunction.accept(translateColorCodes(String.format("%s%s", getPrefix(), getWithArgs(text, args))));
    }

    /**
     * Печатает шаблонную строку
     *
     * @param text          много текста
     * @param printFunction функция вывода (void + только 1 аргумент!)
     * @param args          аргументы
     */
    public static void print(List<String> text, Consumer<Component> printFunction, Object... args) {
        text.forEach(line -> printFunction.accept(translateColorCodes(String.format("%s%s", getPrefix(), getWithArgs(line, args)))));
    }

    /**
     * Печатает шаблонную строку
     *
     * @param text          много текста
     * @param printFunction функция вывода (void + только 1 аргумент!)
     * @param args          аргументы
     */
    public static void print(Set<String> text, Consumer<Component> printFunction, Object... args) {
        text.forEach(line -> printFunction.accept(translateColorCodes(String.format("%s%s", getPrefix(), getWithArgs(line, args)))));
    }

    /**
     * Уровни логирования
     */
    public enum LogLevel {
        INFO,
        WARNING,
        ERROR
    }

    public static final class Chat {
        /**
         * Отправить сообщение в чат
         *
         * @param msg    сообщения
         * @param onlyOp только опкам?
         * @param args   аргументы для вставки в текст
         */
        public static void msg(@NotNull String msg, boolean onlyOp, Object... args) {
            if (onlyOp)
                Bukkit.getOnlinePlayers().stream().filter(Player::isOp).forEach(player -> print(msg, player::sendMessage, args));
            else
                print(msg, Bukkit.getServer()::sendMessage, args);
        }

        /**
         * Отправить сообщение в чат
         *
         * @param msg    сообщения
         * @param onlyOp только опкам?
         * @param args   аргументы для вставки в текст
         */
        public static void msg(@NotNull List<String> msg, boolean onlyOp, Object... args) {
            if (onlyOp)
                Bukkit.getOnlinePlayers().stream().filter(Player::isOp).forEach(player -> print(msg, player::sendMessage, args));
            else
                print(msg, Bukkit.getServer()::sendMessage, args);
        }

        /**
         * Отправить сообщение в чат
         *
         * @param msg    сообщения
         * @param onlyOp только опкам?
         * @param args   аргументы для вставки в текст
         */
        public static void msg(@NotNull Set<String> msg, boolean onlyOp, Object... args) {
            if (onlyOp)
                Bukkit.getOnlinePlayers().stream().filter(Player::isOp).forEach(player -> print(msg, player::sendMessage, args));
            else
                print(msg, Bukkit.getServer()::sendMessage, args);
        }
    }
}