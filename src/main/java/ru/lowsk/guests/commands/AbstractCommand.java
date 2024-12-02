package ru.lowsk.guests.commands;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Debug;
import ru.lowsk.guests.utils.FastParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Абстрактные команды для удобства
 *
 * @author lowsk
 */
@Getter
public abstract class AbstractCommand {
    /**
     * Сообщение если нет прав
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String noPermissionsMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("Error.no-permissions-message");
    /**
     * Сообщение использования
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> usageMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("AbstractCommand.usage-message");
    /**
     * Отправитель
     */
    private final CommandSender sender;
    /**
     * Имя команды
     */
    private final String name;
    /**
     * Описание команды
     */
    private final String description;
    /**
     * Использование команды
     */
    private final String usage;
    /**
     * Права на команду
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private final String permission =
            Guests.getPluginData().getBackendPluginInfo().getConfigurationSection("permissions.guests").getString(name);
    /**
     * Доп права
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private final List<String> subPermissions =
            new ArrayList<>(FastParser.parseKeys(
                    Guests.getPluginData().getBackendPluginInfo().getConfigurationSection(
                            String.format("permissions.guests.%s", name)), false));

    public AbstractCommand(CommandSender sender, String name, String description, String usage) {
        this.sender = sender;
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    /**
     * Проверка прав
     *
     * @return у отправителя они есть?
     */
    public boolean hasPermission() {
        return this.sender.isOp() ||
                this.sender.hasPermission("admin") ||
                this.sender.hasPermission(this.getPermission()) ||
                this.sender instanceof ConsoleCommandSender ||
                this.sender instanceof RemoteConsoleCommandSender;
    }

    /**
     * Проверка под-прав
     *
     * @param sub под-право
     * @return у отправителя они есть?
     */
    public boolean hasPermission(String sub) {
        String permission = this.permission + "." + sub;

        return this.sender.isOp() ||
                this.sender.hasPermission("admin") ||
                this.sender.hasPermission(permission) ||
                this.sender instanceof ConsoleCommandSender ||
                this.sender instanceof RemoteConsoleCommandSender;
    }

    /**
     * Вывести полное описание команды
     */
    public void sendUsage() {
        Debug.print(getUsageMessage(), sender::sendMessage, this.name, this.description, this.usage);
    }

    /**
     * Запустить команду
     *
     * @param sender  отправитель
     * @param command команда
     * @param label   имя команды
     * @param args    аргументики
     */
    public abstract void execute(CommandSender sender, Command command, String label, String[] args);
}
