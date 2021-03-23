package com.example.secondbot;

import com.annimon.tgbotsmodule.BotHandler;
import com.annimon.tgbotsmodule.BotModule;
import com.annimon.tgbotsmodule.Runner;
import com.annimon.tgbotsmodule.beans.Config;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class SecondBot implements BotModule {

    public static void main(String[] args) {
        Runner.run(List.of(new SecondBot()));
    }

    @Override
    public @NotNull BotHandler botHandler(@NotNull Config config) {
        return new SecondBotHandler();
    }
}
