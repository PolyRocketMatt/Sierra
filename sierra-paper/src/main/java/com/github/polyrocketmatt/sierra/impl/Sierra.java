package com.github.polyrocketmatt.sierra.impl;

import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.github.polyrocketmatt.sierra.impl.command.InfoCommand;
import com.github.polyrocketmatt.sierra.logging.SierraLogger;
import com.github.polyrocketmatt.sierra.utils.ResourceUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class Sierra extends JavaPlugin {

    private static Sierra INSTANCE;

    private final File LOGGING_DIR = new File(getDataFolder(), "logging");
    private final File WORLDS_DIR = new File(getDataFolder(), "worlds");
    private final File DATA_DIR = new File(getDataFolder(), "data");

    public Sierra() { INSTANCE = this; }

    @Override
    public void onLoad() {
        //  If data folder doesn't exist, we need to install Sierra
        boolean install = false;
        if (!getDataFolder().exists()) {
           install = true;

           initialiseDataFolder();
        }

        initialiseLogger();

        if (install) {
            initialiseDirectories();
            installEngineProperties();
            installDataPacks();
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initialiseDataFolder() {
        //  Create data directory
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
    }

    private void initialiseLogger() {
        File logFile = new File(LOGGING_DIR, "sierra.log");
        SierraLogger.initialiseEngineLogger(logFile, "dd-M-yyyy hh:mm:ss");
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initialiseDirectories() {
        //  Create utility directories
        if (!LOGGING_DIR.exists())  LOGGING_DIR.mkdir();
        if (!WORLDS_DIR.exists())   WORLDS_DIR.mkdir();
        if (!DATA_DIR.exists())     DATA_DIR.mkdir();

        //  Get directory above data directory
        //  TODO: Install data-pack
    }

    private void installEngineProperties() {
        InputStream propertyStream = ResourceUtils.getEngineResourceIS("engine.properties");
        Properties properties = new Properties();

        if (propertyStream != null) {
            try {
                properties.load(propertyStream);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void installDataPacks() {

    }

    private void initialiseCommands() {
        Delegate.hook(this);

        new InfoCommand().createCommand();
    }


    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Sierra");
    }

    public static Sierra getInstance() {
        return INSTANCE;
    }

    public static String version() {
        return getPlugin().getDescription().getVersion();
    }

}
