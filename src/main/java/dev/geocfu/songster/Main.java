package dev.geocfu.songster;

import dev.geocfu.songster.commands.CommandListener;
import dev.geocfu.songster.commands.CommandProvider;
import dev.geocfu.songster.commands.SlashCommand;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TOKEN");

    final CommandProvider commandProvider = new CommandProvider();
    final ArrayList<SlashCommand> slashCommands = commandProvider.getSlashCommands();
    final CommandListener commandListener = new CommandListener(slashCommands);

    JDABuilder.createDefault(token)
        .addEventListeners(commandListener)
        //        .enableCache(CacheFlag.VOICE_STATE)
        .build()
        .awaitReady();
  }
}
