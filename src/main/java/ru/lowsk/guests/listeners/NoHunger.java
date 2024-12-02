package ru.lowsk.guests.listeners;

import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import ru.lowsk.guests.core.DataBase;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class NoHunger extends Template implements Listener {

    /**
     * Регистрирует заход на сервер
     */
    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        Player guest;
        if (!DataBase.isGuest(guest = player))
            return;

        event.setCancelled(true);
    }
}
