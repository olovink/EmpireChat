package com.tes.empirechat.handler;

import com.tes.empirechat.Loader;
import com.tes.empirechat.utils.Utils;
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
        String reallyMessage = String.format(Utils.getFormat(), player.getDisplayName(), message);
        int distance = Loader.getColoredInteger("chat-distance");

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
