package com.github.polyrocketmatt.sierra.impl.command;

import com.github.polyrocketmatt.delegate.api.command.tree.ICommandNode;
import com.github.polyrocketmatt.delegate.api.entity.CommanderEntity;
import com.github.polyrocketmatt.delegate.core.command.DelegateCommandBuilder;
import com.github.polyrocketmatt.delegate.core.command.definition.SubcommandDefinition;
import com.github.polyrocketmatt.delegate.core.permission.PermissionTiers;
import com.github.polyrocketmatt.delegate.impl.Delegate;

import static com.github.polyrocketmatt.sierra.engine.utils.StringUtils.PAPER_PREFIX;

public class SierraCommand implements ISierraCommand {

    @Override
    public DelegateCommandBuilder getCommandChain() {
        return Delegate.getFactory().create("sierra", "General Sierra command")
                .withPermission(PermissionTiers.OPERATOR.getTier())
                .withSubcommand(new SubcommandDefinition(new InfoCommand().getCommandChain()))
                .withConsumerAction((commander, args) ->
                        Delegate.getDelegateAPI().getCommandTree().getRoots()
                                .forEach(root -> traverseNode(commander, root, ""))
                )
                .onExcept((commander, type, args) -> {
                    String command = String.join(" ", args);

                    switch (type) {
                        default -> {}
                        case UNAUTHORIZED -> commander.sendMessage("%s &cOops, you do not have permission to use this command!".formatted(PAPER_PREFIX));
                        case COMMAND_NON_EXISTENT -> commander.sendMessage("%s &cThe command \"/%s\" does not exist!".formatted(PAPER_PREFIX, command));
                    }
                })
                .withExceptionCatching();
    }

    private void traverseNode(CommanderEntity commander, ICommandNode node, String parentDefinition) {
        String nameDefinition = node.getNameDefinition().getValue();
        String descriptionDefinition = node.getDescriptionDefinition().getValue();
        String commandName = (parentDefinition.isEmpty() ? "" : parentDefinition + " ") + nameDefinition;

        commander.sendMessage("%s &6/%s: &7%s".formatted(PAPER_PREFIX, commandName, descriptionDefinition));
        node.getChildren().forEach(child -> traverseNode(commander, child, nameDefinition));
    }

}
