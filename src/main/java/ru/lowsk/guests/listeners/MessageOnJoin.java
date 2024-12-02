package ru.lowsk.guests.listeners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class MessageOnJoin extends Template implements Listener {

    /**
     * Сообщение дс
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String onJoinMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("MessageOnJoin.on-join-message-to-guest");

    /**
     * Регистрирует заход на сервер
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        sendTemplate(getOnJoinMessage(), guest::sendMessage, true, guest.getName());
    }
}
