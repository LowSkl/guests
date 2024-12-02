package ru.lowsk.guests.core;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import ru.lowsk.guests.data.InitEventsData;
import ru.lowsk.guests.data.PluginData;
import ru.lowsk.guests.data.ServerData;
import ru.lowsk.guests.hooks.PlaceHolderHook;
import ru.lowsk.guests.utils.Config;
import ru.lowsk.guests.utils.Debug;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

// PS: можно было сделать получение мета инфы через функции плагина, но уже похуй
/**
 * Мейн плагина
 *
 * @author lowsk
 */
public final class Guests extends JavaPlugin {
    /**
     * Тута храним всю инфу плагина (инстанс, конфиги и тд)
     */
    @Getter
    private static PluginData pluginData;
    /**
     * Сообщение при загрузке плагина
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> loadedMessage =
            pluginData.getFormattingConfig().getConfig().getStringList("Guests.loaded-message");
    /**
     * Сообщение при включении плагина
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> enabledMessage =
            pluginData.getFormattingConfig().getConfig().getStringList("Guests.enabled-message");
    /**
     * Сообщение при выключении плагина
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> disabledMessage =
            pluginData.getFormattingConfig().getConfig().getStringList("Guests.disabled-message");
    /**
     * Тута храним всю инфу сервера (сервер, плагин менеджер и тд)
     */
    @Getter
    private static ServerData serverData;

    /**
     * До загрузки мира уже инициализируем конфиги и инстанс
     */
    @Override
    public void onLoad() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        pluginData = PluginData.builder()
                .instance(this)
                .mainConfig(new Config("config.yml", this))
                .formattingConfig(new Config("formatting.yml", this))
                .listenersConfig(new Config("listeners.yml", this))
                .configDataBase(new Config("players.yml", this))
                .backendPluginInfo(Objects.requireNonNull(YamlConfiguration.loadConfiguration(
                        new InputStreamReader(Objects.requireNonNull(getResource("plugin.yml"))))))
                .build();

        DataBase.update();

        if (getLoadedMessage() != null && !getLoadedMessage().isEmpty())
            Debug.msg(getLoadedMessage(), Debug.LogLevel.INFO);
    }

    /**
     * На полной загрузке инициализируем все остальное
     */
    @Override
    public void onEnable() {
        serverData = ServerData.builder()
                .server(this.getServer())
                .pluginManager(this.getServer().getPluginManager())
                .scheduler(this.getServer().getScheduler())
                .build();

        InitEventsData.update();

        Objects.requireNonNull(this.getCommand("guests")).setExecutor(new CommandHandler());
        Objects.requireNonNull(this.getCommand("guests")).setTabCompleter(new CompleterHandler());

        if (getServerData().getPluginManager().getPlugin("PlaceholderAPI") != null)
            new PlaceHolderHook().register();

        if (getEnabledMessage() != null && !getEnabledMessage().isEmpty())
            Debug.msg(getEnabledMessage(), Debug.LogLevel.INFO);
    }

    /**
     * Закрываем все, что нужно закрыть (иначе получим по ебалу)
     */
    @Override
    public void onDisable() {
        if (getDisabledMessage() != null && !getDisabledMessage().isEmpty())
            Debug.msg(getDisabledMessage(), Debug.LogLevel.INFO);

        InitEventsData.unregister();
        DataBase.close();

        pluginData = null;
        serverData = null;
    }
}
