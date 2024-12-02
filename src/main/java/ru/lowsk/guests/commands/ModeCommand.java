package ru.lowsk.guests.commands;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Debug;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Команда помощи и доброты
 *
 * @author lowsk
 */
public final class ModeCommand extends AbstractCommand {
    /**
     * Отправитель
     */
    public static final String NAME = "mode";

    /**
     * Описание команды
     */
    public static final String DESCRIPTION = "Смена режима игрока";
    /**
     * Сообщение при добавлении гостя
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String myUsageMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("Help.mode");
    /**
     * Сообщение при добавлении гостя
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> setGuestMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("ModeCommand.set-guest-message");
    /**
     * Сообщение при удалении гостя
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> removeGuestMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("ModeCommand.remove-guest-message");
    /**
     * Сообщение если человек уже гость
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> alreadyGuestMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("ModeCommand.already-guest-message");
    /**
     * Сообщение если человек уже не гость
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> alreadyNotGuestMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("ModeCommand.already-not-guest-message");
    /**
     * Конструктор
     *
     * @param sender отправитель
     */
    public ModeCommand(CommandSender sender) {
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

        if (args.length < 3) {
            sendUsage();
            return;
        }

        switch (args[1]) {
            case "set" -> {
                for (int i = 2; i < args.length; ++i) {
                    if (!this.hasPermission("set")) {
                        Debug.print(getNoPermissionsMessage(), sender::sendMessage);
                        return;
                    }

                    if (DataBase.isGuest(args[i]))
                        Debug.print(getAlreadyGuestMessage(), sender::sendMessage, args[i]);
                    else {
                        DataBase.delPlayer(args[i]);
                        Debug.print(getSetGuestMessage(), sender::sendMessage, args[i]);
                    }
                }
            }
            case "remove" -> {
                if (!this.hasPermission("remove")) {
                    Debug.print(getNoPermissionsMessage(), sender::sendMessage);
                    return;
                }

                for (int i = 2; i < args.length; ++i) {
                    if (!DataBase.isGuest(args[i]))
                        Debug.print(getAlreadyNotGuestMessage(), sender::sendMessage, args[i]);
                    else {
                        Player guest;
                        if ((guest = Bukkit.getPlayerExact(args[i])) != null) {
                            guest.setCollidable(true);
                            guest.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
                        }

                        DataBase.addPlayer(args[i]);
                        Debug.print(getRemoveGuestMessage(), sender::sendMessage, args[i]);
                    }
                }
            }
            default -> sendUsage();
        }
    }
}
