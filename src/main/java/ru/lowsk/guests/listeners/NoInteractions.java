package ru.lowsk.guests.listeners;

import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;

/**
 * Класс запрета взаимодействия с блоками
 *
 * @author lowsk
 */
@NoArgsConstructor
public final class NoInteractions extends Template implements Listener {
    /**
     * Сообщение взаимодействия с блоком
     */
    @Getter(lazy = true)
    private static final String interactMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("NoInteractions.on-interaction-message");

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        Block point = event.getClickedBlock();
        if (point == null)
            return;

        Material blockType = point.getType();
        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK:
                if (!blockType.asBlockType().isInteractable())
                    return;
            case PHYSICAL:
                if (blockType.toString().contains("PRESSURE"))
                    return;
                break;
            case RIGHT_CLICK_BLOCK:
                if (!blockType.asBlockType().isInteractable() ||
                        blockType.toString().contains("CHEST") ||
                        blockType.toString().contains("LECTERN") ||
                        blockType.toString().contains("BARREL") ||
                        blockType.toString().contains("DOOR"))
                    return;
        }

        if (getInteractMessage() != null && !getInteractMessage().isEmpty())
            sendTemplate(getInteractMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (event.getRightClicked() instanceof Player)
            return;

        if (getInteractMessage() != null && !getInteractMessage().isEmpty())
            sendTemplate(getInteractMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void atEntityInteract(PlayerInteractAtEntityEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (event.getRightClicked() instanceof Player)
            return;

        if (getInteractMessage() != null && !getInteractMessage().isEmpty())
            sendTemplate(getInteractMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onFrameInteract(PlayerItemFrameChangeEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getInteractMessage() != null && !getInteractMessage().isEmpty())
            sendTemplate(getInteractMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onLecternBookInteract(PlayerTakeLecternBookEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getInteractMessage() != null && !getInteractMessage().isEmpty())
            sendTemplate(getInteractMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getInteractMessage() != null && !getInteractMessage().isEmpty())
            sendTemplate(getInteractMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player guest;
        if (!DataBase.isGuest(guest = event.getPlayer()))
            return;

        if (getInteractMessage() != null && !getInteractMessage().isEmpty())
            sendTemplate(getInteractMessage(), guest::sendMessage, false);

        event.setCancelled(true);
    }
}
