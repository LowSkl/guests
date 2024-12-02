package ru.lowsk.guests.commands;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Debug;

import java.util.List;

/**
 * Команда помощи и доброты
 *
 * @author lowsk
 */
public final class ReloadCommand extends AbstractCommand {
    /**
     * Отправитель
     */
    public static final String NAME = "reload";

    /**
     * Описание команды
     */
    public static final String DESCRIPTION = "Перезагружает конфиги";
    /**
     * Сообщение при добавлении гостя
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> configReloadMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("ReloadCommand.config-reload-message");
    /**
     * Сообщение использования
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String myUsageMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("Help.reload");

    /**
     * Конструктор
     *
     * @param sender отправитель
     */
    public ReloadCommand(CommandSender sender) {
        super(sender, NAME, DESCRIPTION, getMyUsageMessage());
    }

    /**
     * Запустить команду
     *
     * @param sender  отправитель
     * @param command команда
     * @param label   имя команды
     * @param args    аргументики
     */
    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            Debug.print(getNoPermissionsMessage(), sender::sendMessage);
            return;
        }

        Guests.getPluginData().getConfigDataBase().reload();
        Debug.print(getConfigReloadMessage(), sender::sendMessage, Guests.getPluginData().getConfigDataBase().getConfigFile().getName());

        Guests.getPluginData().getMainConfig().reload();
        Debug.print(getConfigReloadMessage(), sender::sendMessage, Guests.getPluginData().getMainConfig().getConfigFile().getName());

        Guests.getPluginData().getFormattingConfig().reload();
        Debug.print(getConfigReloadMessage(), sender::sendMessage, Guests.getPluginData().getFormattingConfig().getConfigFile().getName());

        Guests.getPluginData().getListenersConfig().reload();
        Debug.print(getConfigReloadMessage(), sender::sendMessage, Guests.getPluginData().getListenersConfig().getConfigFile().getName());
    }
}
