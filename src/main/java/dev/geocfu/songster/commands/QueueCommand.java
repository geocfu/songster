package dev.geocfu.songster.commands;

import dev.geocfu.songster.musicplayer.AudioPlayerProvider;
import dev.geocfu.songster.musicplayer.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueCommand implements SlashCommand {

  static final Logger logger = LoggerFactory.getLogger(QueueCommand.class);

  final AudioPlayerProvider audioPlayerProvider;

  public QueueCommand(AudioPlayerProvider audioPlayerProvider) {
    this.audioPlayerProvider = audioPlayerProvider;
  }

  /**
   * Returns the name of the slash command.
   *
   * @return The name of the slash command
   */
  @Override
  public String getName() {
    return "queue";
  }

  /**
   * Returns the description of the slash command.
   *
   * @return the description of the slash command
   */
  @Override
  public String getDescription() {
    return "Displays the current queue.";
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

    final String queue = guildMusicManager.getScheduler().getQueue();

    event.reply(queue).queue();
  }
}
