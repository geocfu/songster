package dev.geocfu.songster;

import dev.geocfu.songster.commands.CommandProvider;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Dotenv dotenv = Dotenv.load();
        CommandProvider commandProvider = new CommandProvider();

        JDA jda = JDABuilder.createDefault(dotenv.get("TOKEN"))
                .addEventListeners(commandProvider)
                .setStatus(OnlineStatus.IDLE)
                .build()
                .awaitReady();
    }
}
