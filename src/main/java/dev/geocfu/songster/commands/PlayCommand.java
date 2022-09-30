package dev.geocfu.songster.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class PlayCommand implements SlashCommand {
  static final Logger logger = LoggerFactory.getLogger(PlayCommand.class);

  @Override
  public String getName() {
    return "play";
  }

  @Override
  public String getDescription() {
    return "Plays a song from the provided URL or adds it the playlist.";
  }

  @Override
  public ArrayList<OptionData> getOptions() {
    ArrayList<OptionData> options = new ArrayList<>();

    options.add(new OptionData(OptionType.STRING, "url", "The URL of the song", true));

    return options;
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    logger.info("executing");
  }
}
