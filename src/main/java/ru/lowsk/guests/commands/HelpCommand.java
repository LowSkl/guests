package ru.lowsk.guests.commands;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Debug;

import java.util.Map;

/**
 * Команда помощи и доброты
 *
 * @author lowsk
 */
public final class HelpCommand extends AbstractCommand {
    /**
     * Отправитель
     */
    public static final String NAME = "help";

    /**
     * Описание команды
     */
    public static final String DESCRIPTION = "Выводит помогающую информацию";
    /**
     * The help messages
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final Map<String, Object> helpMessages =
            Guests.getPluginData().getFormattingConfig().getConfig().getConfigurationSection("Help").getValues(false);
    /**
     * Сообщение об использовании
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String myUsageMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("Help.help");

    /**
     * Конструктор
     *
     * @param sender отправитель
     */
    public HelpCommand(CommandSender sender) {
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

        getHelpMessages().forEach((key, value) -> {
            if (this.hasPermission(String.format("guests.%s", key)))
                Debug.print(value.toString(), sender::sendMessage);
        });
    }
}
