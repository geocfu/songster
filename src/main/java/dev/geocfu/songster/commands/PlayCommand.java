package dev.geocfu.songster.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayCommand implements SlashCommand {
    final static Logger logger = LoggerFactory.getLogger(PlayCommand.class);

    SlashCommandInteractionEvent event;

    public PlayCommand(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    public String name() {
        return "play";
    }

    public String description() {
        return "Plays a song from the provided URL or adds it the playlist.";
    }

    public void execute() {
        logger.info("executing");
    }

}
