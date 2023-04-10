package com.github.polyrocketmatt.sierra.impl.command;

import com.github.polyrocketmatt.sierra.impl.Sierra;

public class CreateWorldCommand extends SierraCommand {

    public CreateWorldCommand() {
        super(
                "create",
                new String[] { },
                "Creates a new world with the specified name",
                new String[] { "name" },
                "sierra.command.create"
        );
    }

    @Override
    public void run(SierraCommander player, String[] args) {
        String name = args[0];

        if (Sierra.getInstance().getWorldManager().worldExists(name)) {
            player.sendMessage("&cA world with the name '%s' already exists!".formatted(name));
        } else {
            int scale = Sierra.getInstance().getConfiguration().getInt("scale");

            player.sendMessage("&aCreating world '%s'...".formatted(name));
            Sierra.getInstance().getWorldManager().createWorld(name, scale);
        }


    }
}
