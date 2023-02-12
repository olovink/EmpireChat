package com.tes.empirechat.utils;

import com.tes.empirechat.Loader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {
    public static String getFormat() {
        String format = Loader.getInstance().getConfig().getString("format");
        if (format == null) {
            Bukkit.getConsoleSender()
                    .sendMessage(ChatColor.RED + " Ошибка конфига (0x1)");
            return "<%1$s> %2$s";
        }
        format = format.replace("<name>", "%1$s");
        format = format.replace("<message>", "%2$s");
        return format;
    }

    public static String firstNChars(String str, int n) {
        if (str == null) {
            return null;
        }

        return str.length() < n ? str : str.substring(0, n);
    }
}
