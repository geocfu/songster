package dev.geocfu.songster;

import dev.geocfu.songster.commands.CommandListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    Dotenv dotenv = Dotenv.load();
    String token = dotenv.get("TOKEN");

    JDABuilder.createDefault(token).addEventListeners(new CommandListener()).build().awaitReady();
  }
}
