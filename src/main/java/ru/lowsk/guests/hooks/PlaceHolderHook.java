package ru.lowsk.guests.hooks;

import lombok.AccessLevel;
import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lowsk.guests.core.DataBase;
import ru.lowsk.guests.core.Guests;

public final class PlaceHolderHook extends PlaceholderExpansion {
    /**
     * Плейсхолдер гостя
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String onRequestFromGuestMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("PlaceHolderHook.on-guest-placeholder-message");
    /**
     * Плейсхолдер гостя
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String onRequestToGuestMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getString("PlaceHolderHook.to-guest-placeholder-message");
    /**
     * Имя
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String pluginName = Guests.getPluginData().getBackendPluginInfo().getString("name");
    /**
     * Автор
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String pluginAuthor = Guests.getPluginData().getBackendPluginInfo().getString("author");
    /**
     * Версия плагина
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final String pluginVersion = Guests.getPluginData().getBackendPluginInfo().getString("version");

    @Override
    public @NotNull String getIdentifier() {
        return getPluginName();
    }

    @Override
    public @NotNull String getAuthor() {
        return getPluginAuthor();
    }

    @Override
    public @NotNull String getVersion() {
        return getPluginVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player != null) {
            if (DataBase.isGuest(player)) {
                switch (params.toUpperCase()) {
                    case "ISGUEST" -> {
                        return getOnRequestFromGuestMessage();
                    }
                    case "TOGUEST" -> {
                        return getOnRequestToGuestMessage();
                    }
                }
            } else return "";
        }
        return null;
    }
}
