package com.tes.empirechat;

import com.tes.empirechat.handler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Loader extends JavaPlugin {

    public static Loader instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("Initialization...");
        registerEvents();
        loadConfig();
    }

    public static Loader getInstance() {
        return instance;
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
    }

    public static String getColoredString(String path) {
        return colorString(Loader.instance.getConfig().getString(path));
    }

    public static String getColoredBoolean(String path) {
        return colorString(String.valueOf(Loader.instance.getConfig().getBoolean(path)));
    }

    public static int getColoredInteger(String path) {
        return Loader.instance.getConfig().getInt(path);
    }

    public static String colorString(String string) {

        if (string == null)
            return null;

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled.");
    }

    public void loadConfig() {
        this.saveDefaultConfig();
        this.saveResource("config.yml", true);
    }
}
