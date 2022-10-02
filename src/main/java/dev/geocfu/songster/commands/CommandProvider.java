package dev.geocfu.songster.commands;

import dev.geocfu.songster.musicplayer.AudioPlayerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CommandProvider {
  private static final Logger logger = LoggerFactory.getLogger(CommandProvider.class);
  private final ArrayList<SlashCommand> slashCommands;
  private final AudioPlayerProvider audioPlayerProvider;

  public CommandProvider() {
    slashCommands = new ArrayList<>();
    audioPlayerProvider = new AudioPlayerProvider();

    slashCommands.add(new PlayCommand(audioPlayerProvider));
    slashCommands.add(new PauseCommand(audioPlayerProvider));
    slashCommands.add(new ResumeCommand(audioPlayerProvider));
    slashCommands.add(new SkipCommand(audioPlayerProvider));
    slashCommands.add(new PlayCommand(audioPlayerProvider));
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
