package ru.lowsk.guests.listeners;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.TrialSpawnerSpawnEvent;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.utils.Debug;


/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class CancelMobsEvents extends Template implements Listener {
    /**
     * Регистрирует спавн фантомов
     */
    @EventHandler
    public void onPhantom(PhantomPreSpawnEvent event) {
        if (event.getSpawningEntity() instanceof Player guest && DataBase.isGuest(guest)) {
            event.setShouldAbortSpawn(true);  //TODO: проверь разницу между этим и setcanceled
            event.setCancelled(true);
        }
    }

    /**
     * Регистрирует спавн спавнеров
     */
    @EventHandler
    public void onPhantom(TrialSpawnerSpawnEvent event) {
        if (event.getTrialSpawner().getTrackedPlayers().stream().allMatch(DataBase::isGuest))
            event.setCancelled(true);
    }

    /**
     * Регистрирует агр
     */
    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent event) {
        if (event.getEntity() instanceof Enemy && event.getTarget() instanceof Player guest && DataBase.isGuest(guest)) {
            event.setTarget(null);
            event.setCancelled(true);
        }
    }
}
