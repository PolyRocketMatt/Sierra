package com.github.polyrocketmatt.sierra.impl;

import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.github.polyrocketmatt.sierra.impl.command.InfoCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Sierra extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Sierra");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initialiseDirectories() {
        //  Create data directory
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        //  Get directory above data directory
    }

    private void initialiseCommands() {
        Delegate.hook(this);

        new InfoCommand().createCommand();
    }

    private void installDatapacks() {

    }

    public static String version() {
        return getPlugin().getDescription().getVersion();
    }

}
