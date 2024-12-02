package ru.lowsk.guests.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.lowsk.guests.commands.AbstractCommand;
import ru.lowsk.guests.commands.HelpCommand;
import ru.lowsk.guests.commands.ModeCommand;
import ru.lowsk.guests.commands.ReloadCommand;

/**
 * Простенький слушатель команд чтоб не
 * плодить 100500 перегруженных функций
 *
 * @author lowsk
 */
public final class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        AbstractCommand cmd = new HelpCommand(sender);

        // Выводим help
        if (args.length == 0) {
            cmd.execute(sender, command, label, args);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help" -> cmd = new HelpCommand(sender);
            case "reload" -> cmd = new ReloadCommand(sender);
            case "mode" -> cmd = new ModeCommand(sender);
        }

        cmd.execute(sender, command, label, args);
        return true;
    }
}
