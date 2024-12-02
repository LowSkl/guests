package ru.lowsk.guests.listeners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Debug;

import java.util.List;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class AdventureOnJoin extends Template implements Listener {

    /**
     * Сообщение ГМ
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> GMSetMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("AdventureOnJoin.gm-set-message");

    /**
     * Сообщение дс
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String onJoinMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("AdventureOnJoin.on-join-message");

    /**
     * Регистрирует заход на сервер
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (DataBase.isGuest(player)) {
            if (getOnJoinMessage() != null && !getOnJoinMessage().isEmpty())
                sendTemplate(getOnJoinMessage(), player::sendMessage, false);

            if (getGMSetMessage() != null && !getGMSetMessage().isEmpty())
                Debug.msg(getGMSetMessage(), Debug.LogLevel.INFO, "ADVENTURE", player.getName());

            player.setGameMode(GameMode.ADVENTURE);
        } else if (player.getGameMode() == GameMode.ADVENTURE) {
            if (getGMSetMessage() != null && !getGMSetMessage().isEmpty())
                Debug.msg(getGMSetMessage(), Debug.LogLevel.INFO, "SURVIVAL", player.getName());

            player.setGameMode(GameMode.SURVIVAL);
        }
    }
}
