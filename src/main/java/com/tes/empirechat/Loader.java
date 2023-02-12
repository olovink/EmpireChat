package com.tes.empirechat;

import com.tes.empirechat.handler.PlayerHandler;
import com.tes.empirechat.utils.Utils;
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
        return Utils.colorString(Loader.instance.getConfig().getString(path));
    }

    public static int getConfigInteger(String path) {
        return Loader.instance.getConfig().getInt(path);
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
