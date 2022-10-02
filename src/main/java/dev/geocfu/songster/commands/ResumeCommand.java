package dev.geocfu.songster.commands;

import dev.geocfu.songster.audioplayer.AudioPlayerProvider;
import dev.geocfu.songster.audioplayer.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResumeCommand implements SlashCommand {
  static final Logger logger = LoggerFactory.getLogger(ResumeCommand.class);

  final AudioPlayerProvider audioPlayerProvider;

  public ResumeCommand(AudioPlayerProvider audioPlayerProvider) {
    this.audioPlayerProvider = audioPlayerProvider;
  }

  /**
   * Returns the name of the slash command.
   *
   * @return The name of the slash command
   */
  @Override
  public String getName() {
    return "resume";
  }

  /**
   * Returns the description of the slash command.
   *
   * @return the description of the slash command
   */
  @Override
  public String getDescription() {
    return "Resumes the player.";
  }

  /**
   * The main method that is going to be executed when the command has been summoned
   *
   * @param event SlashCommandInteractionEvent
   */
  @Override
  public void execute(SlashCommandInteractionEvent event) {
    logger.info("Executing");

    final Guild guild = event.getGuild();
    final GuildMusicManager guildMusicManager = audioPlayerProvider.getGuildAudioPlayer(guild);

    guildMusicManager.getScheduler().resume();
  }
}
