package com.github.polyrocketmatt.sierra.impl;

import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;
import com.github.polyrocketmatt.sierra.engine.utils.ResourceUtils;
import com.github.polyrocketmatt.sierra.impl.manager.CommandManager;
import com.github.polyrocketmatt.sierra.impl.manager.WorldManager;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import dev.dejvokep.boostedyaml.spigot.SpigotSerializer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Sierra extends JavaPlugin {

    private static Sierra INSTANCE;

    private final File LOGGING_DIR = new File(getDataFolder(), "logging");
    private final File WORLDS_DIR = new File(getDataFolder(), "worlds");
    private final File DATA_DIR = new File(getDataFolder(), "data");

    //  Managers
    private CommandManager commandManager;
    private WorldManager worldManager;

    //  Configuration
    private YamlDocument configuration;

    public Sierra() { INSTANCE = this; }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onLoad() {
        //  If data folder doesn't exist, we need to install Sierra
        boolean install = false;
        if (!getDataFolder().exists()) {
           install = true;

           initialiseDataFolder();
           initialiseDirectories();
        }

        //  Initialises logging capabilities
        initialiseLogger();

        //  If this is the installation of Sierra, we run the installation procedures
        if (install) {
            installEngineProperties();
            installDataPacks();
        }

        //  After installation, initialise the configuration
        initialiseConfigurations();

        //  Initialise managers, events and configurations
        initialiseManagers();
        initialiseEvents();

        //  Hooking
        getCommand("sierra").setExecutor(this);
    }

    @Override
    public void onEnable() {
        initialiseManagers();
        initialiseEvents();
    }

    @Override
    public void onDisable() {
        Delegate.unhook(this);
        SierraLogger.shutdown();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void initialiseDataFolder() {
        //  Create data directory
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
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

    private void initialiseLogger() {
        String prefixFormat = "dd'_'M'_'yyyy'_'hh'_'mm'_'ss";
        SimpleDateFormat format = new SimpleDateFormat(prefixFormat);
        String date = format.format(new Date());
        File logFile = new File(LOGGING_DIR, "sierra_%s.log".formatted(date));
        SierraLogger.initialiseEngineLogger(logFile, "dd-M-yyyy hh:mm:ss");
        SierraLogger.inform("Sierra logger has been successfully initialised on thread %s"
                .formatted(SierraLogger.getThreadId()), SierraLogger.LogType.PLATFORM);
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

    //  TODO: Hook into Delegate
    private void initialiseManagers() {
        SierraLogger.inform("Initialising managers...", SierraLogger.LogType.PLATFORM);

        this.commandManager = new CommandManager();
        this.worldManager = new WorldManager(getDataFolder(), WORLDS_DIR);
    }

    private void initialiseEvents() {
        SierraLogger.inform("Initialising events...", SierraLogger.LogType.PLATFORM);
    }

    @SuppressWarnings("ConstantConditions")
    private void initialiseConfigurations() {
        SierraLogger.inform("Initialising configurations...", SierraLogger.LogType.PLATFORM);
        GeneralSettings generalSettings = GeneralSettings.builder()
                .setSerializer(SpigotSerializer.getInstance())
                .setKeyFormat(GeneralSettings.KeyFormat.OBJECT)
                .setUseDefaults(true)
                .build();
        UpdaterSettings updaterSettings = UpdaterSettings.builder()
                .setAutoSave(true)
                .setVersioning(new BasicVersioning("version"))
                .build();

        try {
            this.configuration = YamlDocument.create(
                    new File(getDataFolder(), "config.yml"),
                    getResource("config.yml"),
                    generalSettings,
                    LoaderSettings.DEFAULT,
                    DumperSettings.DEFAULT,
                    updaterSettings
            );

            //  Set defaults
            this.configuration.set("quality", "high");
            this.configuration.setComments(List.of(
                    "Quality Options: low, medium, high, ultra"
            ));
        } catch (IOException ex) {
            SierraLogger.error("Failed to initialise configuration document", SierraLogger.LogType.PLATFORM);
            SierraLogger.error("    Message: %s".formatted(ex.getMessage()), SierraLogger.LogType.PLATFORM);
            SierraLogger.error("    Stack Trace: %s".formatted(Arrays.toString(ex.getStackTrace())), SierraLogger.LogType.PLATFORM);
        }
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

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public YamlDocument getConfiguration() {
        return getConfiguration(false);
    }

    public YamlDocument getConfiguration(boolean reload) {
        if (reload) {
            try {
                configuration.update();
                configuration.reload();
            } catch (IOException ex) {
                SierraLogger.error("Failed to fetch configuration document", SierraLogger.LogType.PLATFORM);
                SierraLogger.error("    Message: %s".formatted(ex.getMessage()), SierraLogger.LogType.PLATFORM);
                SierraLogger.error("    Stack Trace: %s".formatted(Arrays.toString(ex.getStackTrace())), SierraLogger.LogType.PLATFORM);
            }
        }

        return configuration;
    }

}
