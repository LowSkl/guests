package ru.lowsk.guests.listeners;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class CancelHits extends Template implements Listener {
    /**
     * Сообщение атаки
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String onAttackMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("CancelHits.on-entity-attack");

    @EventHandler
    public void onEntityAttack(PrePlayerAttackEntityEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getOnAttackMessage() != null && !getOnAttackMessage().isEmpty())
            sendTemplate(getOnAttackMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onHangingAttack(HangingBreakByEntityEvent event) {
        if (!(event.getRemover() instanceof Player player))
            return;

        Player guest;
        if (!DataBase.isGuest(guest = player))
            return;

        if (getOnAttackMessage() != null && !getOnAttackMessage().isEmpty())
            sendTemplate(getOnAttackMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        Player guest;
        if (!DataBase.isGuest(guest = player))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        event.setCancelled(true);
    }

}
