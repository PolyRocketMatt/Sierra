package com.github.polyrocketmatt.sierra.impl.command;

import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.github.polyrocketmatt.sierra.impl.Sierra;

import static com.github.polyrocketmatt.sierra.engine.utils.StringUtils.PAPER_PREFIX;

public class InfoCommand extends SierraCommand {

    @Override
    public void createCommand() {
        Delegate.getFactory().create("info", "Gives information about the currently installed Sierra version")
                .withConsumerAction("info", (commander, args) -> {
                    commander.sendMessage(PAPER_PREFIX + "&7Sierra Information");
                    commander.sendMessage(PAPER_PREFIX + "&7Version: &6%s".formatted(Sierra.version()));
                })
                .build();
    }

}
