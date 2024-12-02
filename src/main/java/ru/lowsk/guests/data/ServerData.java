package ru.lowsk.guests.data;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Вся необходимая сервер дата
 *
 * @author lowsk
 */
@Getter
@Builder
public final class ServerData {
    private final Server server;
    private final PluginManager pluginManager;
    private final BukkitScheduler scheduler;
}
