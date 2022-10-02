package dev.geocfu.songster.commands;

import dev.geocfu.songster.audioplayer.AudioPlayerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CommandProvider {
  private static final Logger logger = LoggerFactory.getLogger(CommandProvider.class);
  private final ArrayList<SlashCommand> slashCommands;

  public CommandProvider() {
    logger.info("Initializing applications Slash Commands");
    slashCommands = new ArrayList<>();

    final AudioPlayerProvider audioPlayerProvider = new AudioPlayerProvider();
    slashCommands.add(new PlayCommand(audioPlayerProvider));
    slashCommands.add(new PauseCommand(audioPlayerProvider));
    slashCommands.add(new ResumeCommand(audioPlayerProvider));
    slashCommands.add(new SkipCommand(audioPlayerProvider));
    slashCommands.add(new QueueCommand(audioPlayerProvider));
  }

  /**
   * Returns all the Slash Commands the application provides.
   *
   * @return all the Slash Commands that the application provides
   */
  public ArrayList<SlashCommand> getSlashCommands() {
    return slashCommands;
  }
}
