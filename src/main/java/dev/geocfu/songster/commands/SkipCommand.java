package dev.geocfu.songster.commands;

import dev.geocfu.songster.audioplayer.AudioPlayerProvider;
import dev.geocfu.songster.audioplayer.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkipCommand implements SlashCommand {
  static final Logger logger = LoggerFactory.getLogger(SkipCommand.class);

  final AudioPlayerProvider audioPlayerProvider;

  public SkipCommand(AudioPlayerProvider audioPlayerProvider) {
    this.audioPlayerProvider = audioPlayerProvider;
  }

  /**
   * Returns the name of the slash command.
   *
   * @return The name of the slash command
   */
  @Override
  public String getName() {
    return "skip";
  }

  /**
   * Returns the description of the slash command.
   *
   * @return the description of the slash command
   */
  @Override
  public String getDescription() {
    return "Skips the currently playing song.";
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

    guildMusicManager.getScheduler().nextTrack();

    event.reply("Skipped currently playing song.").queue();
  }
}
