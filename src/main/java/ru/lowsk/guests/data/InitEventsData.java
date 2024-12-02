package ru.lowsk.guests.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Debug;

import java.util.List;
import java.util.Objects;

/**
 * Класс-парсер ивентов в файлик
 *
 * @author lowsk
 */
public final class InitEventsData {
    /**
     * Сообщение запуска лисенирна
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> ListenerStartMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("InitEventsData.listener-start-message");

    /**
     * Сообщение скипа лисенирна
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> ListenerSkipMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("InitEventsData.listener-skip-message");

    /**
     * Сообщение парсера
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> SettingsParseMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("InitEventsData.settings-parse-message");

    public static void update() {
        unregister();
        Guests.getPluginData().getListenersConfig().getConfig().getKeys(false).forEach(InitEventsData::parseListener);
    }

    public static void unregister() {
        HandlerList.unregisterAll(Guests.getPluginData().getInstance());
    }

    /**
     * Зарегистрировать лисенира
     *
     * @param key название лисенира
     */
    @SneakyThrows
    private static void parseListener(String key) {
        FileConfiguration config = Guests.getPluginData().getListenersConfig().getConfig();

        if (!config.getBoolean(String.format("%s.enableListener", key))) {
            if (getListenerSkipMessage() != null && !getListenerSkipMessage().isEmpty())
                Debug.msg(getListenerSkipMessage(), Debug.LogLevel.INFO, key);
        } else {
            if (getListenerStartMessage() != null && !getListenerStartMessage().isEmpty())
                Debug.msg(getListenerStartMessage(), Debug.LogLevel.INFO, key);

            Listener instance = (Listener) Class.forName(String.format("ru.lowsk.guests.listeners.%s", key)).getConstructor().newInstance();
            if (config.contains(String.format("%s.settings", key)))
                parseListenerSettings(Objects.requireNonNull(config.getConfigurationSection(String.format("%s.settings", key))), instance);

            Guests.getServerData().getPluginManager().registerEvents(instance, Guests.getPluginData().getInstance());
        }
    }

    /**
     * Вывести инфу о лисенере из конфига
     *
     * @param section  секция с настройками
     * @param listener сам класс куда это парсить
     */
    private static void parseListenerSettings(ConfigurationSection section, Listener listener) {
        section.getValues(false).forEach((key, value) -> new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                if (getSettingsParseMessage() != null && !getSettingsParseMessage().isEmpty())
                    Debug.msg(getSettingsParseMessage(), Debug.LogLevel.INFO, key, value.toString());

                listener.getClass().getMethod(String.format("set%s", key), value.getClass()).invoke(listener, value);
            }
        }.run());
    }
}
