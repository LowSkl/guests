package ru.lowsk.guests.data;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Config;

/**
 * Вся необходимая плагин дата
 *
 * @author lowsk
 */
@Getter
@Builder
public final class PluginData {
    private final Guests instance;
    private final Config mainConfig;
    private final Config formattingConfig;
    private final Config listenersConfig;
    private final Config configDataBase;
    private final YamlConfiguration backendPluginInfo;
}
