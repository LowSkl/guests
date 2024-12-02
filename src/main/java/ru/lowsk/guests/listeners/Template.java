package ru.lowsk.guests.listeners;

import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import ru.lowsk.guests.core.Guests;
import ru.lowsk.guests.utils.Debug;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;

public abstract class Template {
    /**
     * Шаблон
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final List<String> templateMessage =
            Guests.getPluginData().getFormattingConfig().getConfig().getStringList("Template.template-message");
    /**
     * Шаблон
     */
    @Getter(lazy = true, value = AccessLevel.PROTECTED)
    private static final long timer =
            Guests.getPluginData().getFormattingConfig().getConfig().getLong("Template.send-per-messages");
    private Instant lastSentTime = Instant.now();

    /**
     * Шаблон
     */
    public void sendTemplate(String text, Consumer<Component> printFunction, boolean ignoreTimer, Object... args) {
        Instant now = Instant.now();

        if (ignoreTimer)
            Debug.print(Debug.getWithArgs(getTemplateMessage(), text), printFunction, args);
        else if (Duration.between(lastSentTime, now).toMillis() >= getTimer()) {
            Debug.print(Debug.getWithArgs(getTemplateMessage(), text), printFunction, args);
            lastSentTime = now;
        }
    }
}
