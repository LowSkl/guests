package ru.lowsk.guests.listeners;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class NoInventoryChange extends Template implements Listener {
    /**
     * Сообщение взаимодействия с блоком
     */
    @Getter(lazy = true)
    private static final String changeMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("NoInventoryChange.on-change-message");

    /**
     * Сообщение взаимодействия с exp
     */
    @Getter(lazy = true)
    private static final String expMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("NoInventoryChange.on-exp-message");

    @EventHandler
    public void onSlot(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;

        Player guest;
        if (!DataBase.isGuest(guest = player))
            return;

        if (getChangeMessage() != null && !getChangeMessage().isEmpty())
            sendTemplate(getChangeMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getChangeMessage() != null && !getChangeMessage().isEmpty())
            sendTemplate(getChangeMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onPick(PlayerPickItemEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getChangeMessage() != null && !getChangeMessage().isEmpty())
            sendTemplate(getChangeMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerAttemptPickupItemEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getChangeMessage() != null && !getChangeMessage().isEmpty())
            sendTemplate(getChangeMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onExp(PlayerPickupExperienceEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getExpMessage() != null && !getExpMessage().isEmpty())
            sendTemplate(getExpMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }
}
