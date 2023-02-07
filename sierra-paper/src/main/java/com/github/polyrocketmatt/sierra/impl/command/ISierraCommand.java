package com.github.polyrocketmatt.sierra.impl.command;

import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;

@FunctionalInterface
public interface ISierraCommand {

    DelegateCommandBuilder getCommandChain();

}
