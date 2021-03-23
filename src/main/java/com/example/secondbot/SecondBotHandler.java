package com.example.secondbot;

import com.annimon.tgbotsmodule.BotHandler;
import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.services.YamlConfigLoaderService;
import com.example.secondbot.roles.BotCommand;
import com.example.secondbot.roles.IgnorableAuthority;
import com.example.secondbot.roles.UserRole;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SecondBotHandler extends BotHandler {

    private final SecondBotConfig botConfig;
    private final CommandRegistry<UserRole> commands;

    public SecondBotHandler() {
        final var configLoader = new YamlConfigLoaderService();
        botConfig = configLoader.loadResource("/secondbot.yaml", SecondBotConfig.class);

        final var authority = new IgnorableAuthority();
        commands = new CommandRegistry<>(this, authority);
        commands.register(new BotCommand("/start", UserRole.all(), ctx -> {
            ctx.reply("This bot is a second example for the tgbots-module library.\n" +
                      "  https://github.com/aNNiMON/tgbots-module/\n" +
                      "Source code\n" +
                      "  https://github.com/annimon-tutorials/tgbotsmodule-bot2\n\n" +
                      "ℹ️ *Available commands*:\n" +
                      " - /me — shows user's role (available for all users)\n" +
                      " - /ignoreme — set user's role to `IGNORED` (available for `REGULAR` users only)\n" +
                      " - /unignoreme — set user's role to `REGULAR` (available for `IGNORED` users only)")
                    .disableWebPagePreview()
                    .enableMarkdown()
                    .callAsync(ctx.sender);
        }));
        commands.register(new BotCommand("/me", UserRole.all(), ctx -> {
            ctx.reply("Your status is: " + authority.getRole(ctx.user()))
                    .enableMarkdown()
                    .callAsync(ctx.sender);
        }));
        commands.register(new BotCommand("/ignoreme", UserRole.REGULAR, ctx -> {
            authority.ignore(ctx.user());
            ctx.reply("You ignored now").callAsync(ctx.sender);
        }));
        commands.register(new BotCommand("/unignoreme", UserRole.IGNORED, ctx -> {
            authority.unignore(ctx.user());
            ctx.reply("You unignored now").callAsync(ctx.sender);
        }));
    }

    @Override
    protected BotApiMethod<?> onUpdate(@NotNull Update update) {
        if (commands.handleUpdate(update)) {
            return null;
        }
        return null;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
