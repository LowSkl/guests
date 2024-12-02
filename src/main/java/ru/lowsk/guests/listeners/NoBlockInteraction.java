package ru.lowsk.guests.listeners;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class NoBlockInteraction extends Template implements Listener {
    /**
     * Сообщение сбора блока
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String harvestMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("NoBlockInteraction.on-harvest-message");

    /**
     * Сообщение разрушения блока
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String breakMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("NoBlockInteraction.on-break-message");

    /**
     * Сообщение установки блока
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String placeMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("NoBlockInteraction.on-place-message");

    @EventHandler
    public void onHarvest(PlayerHarvestBlockEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getHarvestMessage() != null && !getHarvestMessage().isEmpty())
            sendTemplate(getHarvestMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getBreakMessage() != null && !getBreakMessage().isEmpty())
            sendTemplate(getBreakMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getPlaceMessage() != null && !getPlaceMessage().isEmpty())
            sendTemplate(getPlaceMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }
}
