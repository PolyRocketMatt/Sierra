package com.github.polyrocketmatt.sierra.impl.command;

import com.github.polyrocketmatt.sierra.impl.Sierra;

import static com.github.polyrocketmatt.sierra.engine.utils.StringUtils.PAPER_PREFIX;

public class InfoCommand extends SierraCommand {

    public InfoCommand() {
        super("info",
                new String[] { },
                "Displays information about Sierra",
                new String[] { },
                "sierra.command.info"
        );
    }

    @Override
    public void run(SierraCommander player, String[] args) {
        player.sendMessage(PAPER_PREFIX + "&7Sierra Information");
        player.sendMessage(PAPER_PREFIX + "&7Version: &6%s".formatted(Sierra.version()));
    }

}
