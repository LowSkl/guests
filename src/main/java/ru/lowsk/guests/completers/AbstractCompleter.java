package ru.lowsk.guests.completers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Абстрактный комплиттер чтобы было удобнее
 *
 * @author lowsk
 */
@Getter @AllArgsConstructor
public abstract class AbstractCompleter {
    /**
     * Комплиттер
     *
     * @param sender  отправитель
     * @param command команда
     * @param label   название команды
     * @param args    аргументы
     * @return список вариантов
     */
    @Nullable
    public abstract List<String> complete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
}
