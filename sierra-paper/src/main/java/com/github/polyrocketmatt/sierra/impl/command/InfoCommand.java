package com.github.polyrocketmatt.sierra.impl.command;

import com.github.polyrocketmatt.delegate.api.command.feedback.FeedbackType;
import com.github.polyrocketmatt.delegate.core.permission.PermissionTiers;
import com.github.polyrocketmatt.delegate.impl.Delegate;
import com.github.polyrocketmatt.sierra.impl.Sierra;

import java.util.Objects;

import static com.github.polyrocketmatt.sierra.engine.utils.StringUtils.PAPER_PREFIX;

public class InfoCommand extends SierraCommand {

    @Override
    public void createCommand() {
        Delegate.getFactory().create("info", "Gives information about the currently installed Sierra version")
                .withConsumerAction("info", (commander, args) -> {
                    commander.sendMessage(PAPER_PREFIX + "&7Sierra Information");
                    commander.sendMessage(PAPER_PREFIX + "&7Version: &6%s".formatted(Sierra.version()));
                })
                .withPermission(PermissionTiers.OPERATOR.getTier())
                .onExcept((commander, type, args) -> {
                    if (Objects.requireNonNull(type) == FeedbackType.UNAUTHORIZED)
                        commander.sendMessage("%s &cOops, you do not have permission to use this command!".formatted(PAPER_PREFIX));
                })
                .build();
    }

}
