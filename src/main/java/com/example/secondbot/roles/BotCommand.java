package com.example.secondbot.roles;

import com.annimon.tgbotsmodule.commands.TextCommand;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import java.util.EnumSet;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class BotCommand implements TextCommand {

    private final String command;
    private final EnumSet<UserRole> roles;
    private final Consumer<MessageContext> contextConsumer;

    public BotCommand(String command, UserRole role, Consumer<MessageContext> contextConsumer) {
        this(command, EnumSet.of(role), contextConsumer);
    }
    public BotCommand(String command, EnumSet<UserRole> roles, Consumer<MessageContext> contextConsumer) {
        this.command = command;
        this.roles = roles;
        this.contextConsumer = contextConsumer;
    }

    @Override
    public String command() {
        return command;
    }

    @SuppressWarnings("unchecked")
    @Override
    public EnumSet<UserRole> authority() {
        return roles;
    }

    @Override
    public void accept(@NotNull MessageContext context) {
        contextConsumer.accept(context);
    }
}
