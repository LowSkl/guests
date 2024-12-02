package ru.lowsk.guests.listeners;

import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.lowsk.guests.core.DataBase;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class RemoveCollision extends Template implements Listener {

    /**
     * Регистрирует заход на сервер
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player guest;
        if (DataBase.isGuest(guest = event.getPlayer()))
            guest.setCollidable(false);
        else
            guest.setCollidable(true);
    }
}
