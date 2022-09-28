package dev.geocfu.songster.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class CommandProvider extends ListenerAdapter {
    final static Logger logger = LoggerFactory.getLogger(CommandProvider.class);

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        JDA jda = event.getJDA();
        registerSlashCommands(jda);
    }

    /**
     * The entrypoint when a slash command is being received.
     *
     * @param event
     */
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (!shouldReply(event)) return;
        logger.info("Received slash command {}", event.getName());

        String command = event.getName();

        if (command.equalsIgnoreCase("play")) {
            new PlayCommand(event).execute();
        }
    }

    /**
     * Determine if we should reply to the invocation of the slash command.
     *
     * @param event
     * @return True if we should reply to the sender
     */
    private Boolean shouldReply(@Nonnull SlashCommandInteractionEvent event) {
        if (event.isFromGuild() && !event.getUser().isBot()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Registers slash commands.
     *
     * @param jda
     */
    public void registerSlashCommands(JDA jda) {
        logger.info("Registering Slash Commands.");
        jda.updateCommands()
                .addCommands(
                        Commands.slash("play", "Plays a song from the provided URL or adds it the playlist.")
                                .addOption(OptionType.STRING, "url", "The URL of the song"),
                        Commands.slash("pause", "Pauses the current playing song."),
                        Commands.slash("skip", "Skips the current playing song and plays the next from the playlist."),
                        Commands.slash("playlist", "Displays the current running playlist.")
                ).queue();
    }

    /**
     * Removes all the registered slash commands.
     *
     * @param jda
     */
    public void unregisterSlashCommands(JDA jda) {
        logger.info("Unregistering slash commands");
        jda.updateCommands().queue();
    }
}
