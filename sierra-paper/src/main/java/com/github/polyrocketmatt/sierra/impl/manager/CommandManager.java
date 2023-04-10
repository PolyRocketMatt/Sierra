package com.github.polyrocketmatt.sierra.impl.manager;

import com.github.polyrocketmatt.sierra.engine.utils.logger.SierraLogger;
import com.github.polyrocketmatt.sierra.impl.command.CreateWorldCommand;
import com.github.polyrocketmatt.sierra.impl.command.InfoCommand;
import com.github.polyrocketmatt.sierra.impl.command.SierraCommand;
import com.github.polyrocketmatt.sierra.impl.command.SierraCommander;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.polyrocketmatt.sierra.engine.utils.StringUtils.PAPER_PREFIX;

public class CommandManager implements CommandExecutor {

    private final List<SierraCommand> commands = new ArrayList<>();

    public CommandManager() {
        SierraLogger.inform("Initialising command manager...", SierraLogger.LogType.PLATFORM);

        this.commands.add(new CreateWorldCommand());
        this.commands.add(new InfoCommand());

        SierraLogger.inform("   -> Initialized %s commands".formatted(this.commands.size()), SierraLogger.LogType.PLATFORM);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("sierra")) {
            SierraCommander commander = new SierraCommander(sender);

            if (args.length == 0) {
                commander.sendMessage(PAPER_PREFIX + "&7Sierra Commands");

                for (SierraCommand sierraCommand : this.commands)
                    if (commander.hasPermission(sierraCommand.getPermission()))
                        commander.sendMessage(sierraCommand.getUsage());
            } else {
                String commandName = args[0];
                String[] commandArgs = new String[args.length - 1];
                System.arraycopy(args, 1, commandArgs, 0, commandArgs.length);

                for (SierraCommand sierraCommand : this.commands) {
                    if (sierraCommand.getName().equalsIgnoreCase(commandName)) {
                        if (sierraCommand.getArguments().length != commandArgs.length)
                            commander.sendMessage("&cIncorrect number of arguments. Expected %s, got %s".formatted(sierraCommand.getArguments().length, commandArgs.length));
                        else if (!commander.hasPermission(sierraCommand.getPermission()))
                            commander.sendMessage("&cYou do not have permission to run this command!");
                        else
                            try {
                                sierraCommand.run(commander, commandArgs);
                            } catch (Exception ex) {
                                String trace =  Arrays.toString(ex.getStackTrace());

                                SierraLogger.error("An error occurred whilst running command '%s'".formatted(sierraCommand.getName()), SierraLogger.LogType.PLATFORM);
                                SierraLogger.error("    Type: %s".formatted(ex.getClass().getSimpleName()), SierraLogger.LogType.PLATFORM);
                                SierraLogger.error("    Message: %s".formatted(ex.getMessage()), SierraLogger.LogType.PLATFORM);
                                SierraLogger.error("    Stack Trace: %s".formatted(trace), SierraLogger.LogType.PLATFORM);
                            }
                    }
                }
            }
        }

        return true;
    }
}
