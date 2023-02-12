package com.tes.empirechat.handler;

import com.tes.empirechat.Loader;
import com.tes.empirechat.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import java.util.Objects;

public class PlayerHandler implements Listener {



    @EventHandler
    @Deprecated
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(onEvent(event));
    }

    @EventHandler
    @Deprecated
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(onEvent(event));
    }


    public String onEvent(PlayerEvent event) {
        boolean isJoinEvent = event instanceof PlayerJoinEvent;

        String message = Loader.getColoredString(isJoinEvent ? "join" : "leave");

        message = message.replace("%player%", event.getPlayer().getName());
        return message;
    }

    @EventHandler
    @Deprecated
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String typeMessage = Utils.firstNChars(message, 1);
        if (!(event.isCancelled())) {
            if (Objects.equals(typeMessage, "!")) {
                message(player, message, "global");
            } else {
                message(player, message, "local");
            }
        }
        event.setCancelled(true);
    }

    @Deprecated
    public void message(Player player, String message, String type) {

        String localFormat = Loader.getColoredString("local-chat-format");
        String globalFormat = Loader.getColoredString("global-chat-format");
        String reallyMessage = Loader.getColoredString("player-format");
        reallyMessage = reallyMessage.replace("%player%", player.getDisplayName());
        reallyMessage = reallyMessage.replace("%message%", message);
        reallyMessage = PlaceholderAPI.setPlaceholders(player, reallyMessage);
        reallyMessage = Utils.hex(reallyMessage);

        int distance = Loader.getConfigInteger("chat-distance");

        for (Player players : Loader.getInstance().getServer().getOnlinePlayers()) {
            if (Objects.equals(type, "global")) {
                players.sendMessage(globalFormat + " " + reallyMessage.replace("!", ""));
            } else if (Objects.equals(type, "local")) {
                if (player.getLocation().distance(players.getLocation()) <= distance) {
                    players.sendMessage(localFormat + " " + reallyMessage);
                }
            }
        }
    }

}
