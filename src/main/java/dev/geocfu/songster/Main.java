package dev.geocfu.songster;

import dev.geocfu.songster.commands.CommandListener;
import dev.geocfu.songster.commands.CommandProvider;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TOKEN");

    CommandProvider commandProvider = CommandProvider.getInstance();
    CommandListener commandListener = new CommandListener(commandProvider.getSlashCommands());

    JDABuilder.createDefault(token)
        .addEventListeners(commandListener)
        .enableCache(CacheFlag.VOICE_STATE)
        .build()
        .awaitReady();
  }
}
