package ru.lowsk.guests.completers;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.FastParser;
import ru.lowsk.guests.utils.Misc;

import javax.annotation.Nullable;
import java.util.*;

/**
 * Помогатор с командами конфига
 *
 * @author lowsk
 */
public final class ModeCompleter extends AbstractCompleter {
    /**
     * Все возможные команды
     */
    @Getter(lazy = true)
    private static final List<String> commands = new ArrayList<>(FastParser.parseKeys(
            Guests.getPluginData().getBackendPluginInfo().getConfigurationSection("permissions.guests.mode"),
            new ArrayList<>() {{
                add("children");
                add("default");
                add("description");
            }},
            false));

    @Override @Nullable
    public List<String> complete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 2)
            return null;

        List<String> guess = new ArrayList<>();

        getCommands().stream()
                .filter(possibleCommand -> Misc.searchFor(possibleCommand, args[1]))
                .filter(possibleCommand -> sender.isOp() ||
                        sender.hasPermission("guests.admin") ||
                        sender.hasPermission(String.format("guests.mode.%s", possibleCommand)))
                .forEach(guess::add);

        return guess;
    }
}
