package com.github.polyrocketmatt.sierra.impl.event;

import com.github.polyrocketmatt.delegate.api.command.data.ActionItem;
import com.github.polyrocketmatt.delegate.api.command.data.CommandCapture;
import com.github.polyrocketmatt.delegate.impl.event.DelegateCommandEvent;
import com.github.polyrocketmatt.sierra.engine.logging.SierraLogger;
import com.github.polyrocketmatt.sierra.impl.Sierra;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class CommandListener implements Listener {

    public CommandListener() {
        Sierra.getPlugin().getServer().getPluginManager().registerEvents(this, Sierra.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandEvent(DelegateCommandEvent event) {
        if (event.getPlugin() != Sierra.getPlugin()) {
            System.out.println(event.getPlugin().getName());

            return;
        }

        for (CommandCapture.Capture capture : event.getCapture()) {
            ActionItem<?> item = capture.result();
            String action = capture.action();

            //  Log errors
            if (item.getResult() == ActionItem.Result.FAILURE) {
                if (action.equalsIgnoreCase("exception"))
                    SierraLogger.error(new String[]{
                            "Command failed: " + action,
                            "Reason: " + item.getItem().toString(),
                    }, SierraLogger.LogType.PLATFORM);
                else if (action.equalsIgnoreCase("stacktrace"))
                    SierraLogger.error(new String[] {
                            "Stack trace: " + item.getItem().toString(),
                    }, SierraLogger.LogType.PLATFORM);
                else
                    SierraLogger.error(new String[] {
                            "Command failed: " + action,
                            "Reason: Illegal exception capture format"
                    }, SierraLogger.LogType.PLATFORM);
            }
        }
    }

}
