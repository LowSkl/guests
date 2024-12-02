package ru.lowsk.guests.core;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lowsk.guests.completers.AbstractCompleter;
import ru.lowsk.guests.completers.ModeCompleter;
import ru.lowsk.guests.utils.FastParser;
import ru.lowsk.guests.utils.Misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Комплиттер команд, вызывает кастомные комплиттеры
 *
 * @author lowsk
 */
public final class CompleterHandler implements TabCompleter {
    /**
     * Все возможные команды
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> commands = new ArrayList<>(FastParser.parseKeys(
            Guests.getPluginData().getBackendPluginInfo().getConfigurationSection("permissions.guests"),
            "admin", false));

    @Override
    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0)
            return null;

        List<String> guess = new ArrayList<>();

        if (args.length == 1) {
            getCommands().stream()
                    .filter(possibleCommand -> Misc.searchFor(possibleCommand, args[0]))
                    .filter(possibleCommand -> sender.isOp() ||
                            sender.hasPermission("guests.admin") ||
                            sender.hasPermission(String.format("guests.%s", possibleCommand)))
                    .forEach(guess::add);
        }

        if (args.length >= 2) {
            AbstractCompleter completer = null;

            switch (args[0]) {
                case "mode" -> completer = new ModeCompleter();
            }

            if (completer == null)
                return null;

            return completer.complete(sender, command, label, args);
        }

        return guess;
    }
}
