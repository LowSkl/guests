package ru.lowsk.guests.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Конфиг надстройка
 *
 * @author lowsk
 */
@Getter
public final class Config {
    /**
     * Сообщение при создании конфига
     */
    @Getter
    @Setter
    private static String InitMessage;
    /**
     * Сам конфиг
     */
    private final FileConfiguration config;
    /**
     * Файл конфига
     */
    private final File configFile;

    /**
     * Инициализация конфига
     *
     * @param name   название конфига
     * @param plugin плагин - владелец
     */
    public Config(String name, Plugin plugin) {
        if (!(this.configFile = new File(plugin.getDataFolder(), name)).exists())
            plugin.saveResource(name, true);
        config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    /**
     * Перезагрузка конфига
     */
    @SneakyThrows
    public void reload() {
        this.config.load(this.configFile);
    }
}
