package com.github.polyrocketmatt.sierra.impl.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SierraCommander {

    private final CommandSender sender;

    public SierraCommander(CommandSender player) {
        this.sender = player;
    }

    public boolean isOperator() {
        return sender.isOp();
    }

    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission) || isOperator();
    }

    public void sendMessage(String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
