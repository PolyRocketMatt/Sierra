package com.github.polyrocketmatt.sierra.impl.command;

public abstract class SierraCommand {

    protected String name;
    protected String[] aliases;
    protected String description;
    protected String[] arguments;
    protected String permission;

    public SierraCommand(String name, String[] aliases, String description, String[] arguments, String permission) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.arguments = arguments;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String[] getArguments() {
        return arguments;
    }

    public String getPermission() {
        return permission;
    }

    public abstract void run(SierraCommander player, String[] args);

}
