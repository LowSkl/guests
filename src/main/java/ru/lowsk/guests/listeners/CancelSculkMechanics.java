package ru.lowsk.guests.listeners;

import lombok.NoArgsConstructor;
import org.bukkit.GameEvent;
import org.bukkit.block.SculkCatalyst;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.GenericGameEvent;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.utils.Debug;

import java.util.Collection;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class CancelSculkMechanics extends Template implements Listener {
    @EventHandler
    public void onShriek(GenericGameEvent event) {
        GameEvent gameEvent = event.getEvent();
        if (gameEvent != GameEvent.SCULK_SENSOR_TENDRILS_CLICKING && gameEvent != GameEvent.SHRIEK)
            return;

        Collection<Player> players = event.getLocation().getNearbyPlayers(event.getRadius());
        if (players.stream().allMatch(DataBase::isGuest))
            event.setCancelled(true);
    }
}
