package dev.geocfu.songster.commands;

import dev.geocfu.songster.musicplayer.AudioPlayerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CommandProvider {
  private static final Logger logger = LoggerFactory.getLogger(CommandProvider.class);
  private final ArrayList<SlashCommand> slashCommands;
  private final AudioPlayerProvider audioPlayerProvider;

  /** Simple no-param constructor to limit object creation. */
  private CommandProvider() {
    slashCommands = new ArrayList<>();
    audioPlayerProvider = new AudioPlayerProvider();
  }

  /**
   * Here, we declare the available Commands that the application provides.
   *
   * @return the CommandProvider instance configured
   */
  public static CommandProvider getInstance() {
    logger.info("Creating a CommandProvider instance and configuring it.");
    CommandProvider provider = new CommandProvider();
    AudioPlayerProvider audioPlayerProvider = provider.getMusicPlayerProvider();

    provider.setSlashCommand(new PlayCommand(audioPlayerProvider));

    return provider;
  }

  /** Here, we declare the available Slash Commands that the application provides */
  private void setSlashCommand(SlashCommand slashCommand) {
    slashCommands.add(slashCommand);
  }

  /**
   * Returns all the Slash Commands the application provides.
   *
   * @return all the Slash Commands that the application provides
   */
  public ArrayList<SlashCommand> getSlashCommands() {
    return slashCommands;
  }

  /**
   * Returns the Music Player Provider that the application is going to use.
   *
   * @return The AudioPlayerProvider that music commands can take advantage of.
   */
  private AudioPlayerProvider getMusicPlayerProvider() {
    return audioPlayerProvider;
  }
}
