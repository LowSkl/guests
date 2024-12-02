package ru.lowsk.guests.utils;

public final class Misc {
    /**
     * Проверка на совпадения
     *
     * @param what  где искать
     * @param where что искать
     * @return совпадает?
     */
    public static boolean searchFor(String where, String what) {
        return where.contains(what) || where.toLowerCase().contains(what) || where.toUpperCase().contains(what);
    }
}
