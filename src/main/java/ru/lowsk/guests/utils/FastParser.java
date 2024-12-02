package ru.lowsk.guests.utils;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FastParser {
    public static Set<String> parseKeys(ConfigurationSection section, List<String> exclude, boolean deep) {
        Set<String> keys = section.getKeys(deep);
        exclude.forEach(keys::remove);
        return keys;
    }

    public static Set<String> parseKeys(ConfigurationSection section, String exclude, boolean deep) {
        Set<String> keys = section.getKeys(deep);
        keys.remove(exclude);
        return keys;
    }

    public static Set<String> parseKeys(ConfigurationSection section, boolean deep) {
        return section.getKeys(deep);
    }

    public static Map<String, Object> parseValues(ConfigurationSection section, List<String> exclude, boolean deep) {
        Map<String, Object> values = section.getValues(deep);
        exclude.forEach(values::remove);
        return values;
    }

    public static Map<String, Object> parseValues(ConfigurationSection section, String exclude, boolean deep) {
        Map<String, Object> values = section.getValues(deep);
        values.remove(exclude);
        return values;
    }

    public static Map<String, Object> parseValues(ConfigurationSection section, boolean deep) {
        return section.getValues(deep);
    }
}
