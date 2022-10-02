package dev.geocfu.songster.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import dev.geocfu.songster.musicplayer.AudioPlayerProvider;
import dev.geocfu.songster.musicplayer.GuildMusicManager;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.managers.AudioManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class PlayCommand implements SlashCommand {
  static final Logger logger = LoggerFactory.getLogger(PlayCommand.class);

  final AudioPlayerProvider audioPlayerProvider;

  public PlayCommand(AudioPlayerProvider audioPlayerProvider) {
    this.audioPlayerProvider = audioPlayerProvider;
  }

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
    final ArrayList<OptionData> options = new ArrayList<>();

    options.add(new OptionData(OptionType.STRING, "url", "The URL of the song", true));

    return options;
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    logger.info("Executing");

    if (!event.getMember().getVoiceState().inAudioChannel()) {
      event.reply("You must be inside a Voice Channel.").queue();
      return;
    }

    final AudioManager audioManager = event.getGuild().getAudioManager();
    final VoiceChannel channel = event.getMember().getVoiceState().getChannel().asVoiceChannel();
    final String url = event.getOption("url").getAsString();

    GuildMusicManager guildMusicManager = audioPlayerProvider.getGuildAudioPlayer(event.getGuild());

    audioPlayerProvider
        .getAudioPlayerManager()
        .loadItemOrdered(
            guildMusicManager,
            url,
            new AudioPlayerLoadResultHandler(event, audioManager, channel, guildMusicManager));
  }

  private class AudioPlayerLoadResultHandler implements AudioLoadResultHandler {
    private final SlashCommandInteractionEvent event;
    private final AudioManager audioManager;
    private final GuildMusicManager guildMusicManager;
    private final VoiceChannel voiceChannel;

    public AudioPlayerLoadResultHandler(
        SlashCommandInteractionEvent event,
        AudioManager audioManager,
        VoiceChannel voiceChannel,
        GuildMusicManager guildMusicManager) {
      this.event = event;
      this.audioManager = audioManager;
      this.voiceChannel = voiceChannel;
      this.guildMusicManager = guildMusicManager;
    }

    /**
     * Called when the requested item is a track and it was successfully loaded.
     *
     * @param track The loaded track
     */
    @Override
    public void trackLoaded(AudioTrack track) {
      event.reply("Adding " + track.getInfo().title + " in queue.").queue();

      audioManager.openAudioConnection(voiceChannel);
      guildMusicManager.getScheduler().queue(track);
    }

    /**
     * Called when the requested item is a playlist and it was successfully loaded.
     *
     * @param playlist The loaded playlist
     */
    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
      event.reply("Adding playlist " + playlist.getName() + " in queue.").queue();

      audioManager.openAudioConnection(voiceChannel);
      guildMusicManager.getScheduler().queuePlaylist(playlist);
    }

    /** Called when there were no items found by the specified identifier. */
    @Override
    public void noMatches() {
      event.reply("The provided \"url\" does not seem to be a valid URL.").queue();
    }

    /**
     * Called when loading an item failed with an exception.
     *
     * @param exception The exception that was thrown
     */
    @Override
    public void loadFailed(FriendlyException exception) {
      event
          .reply(
              "The provided \"url\" was not able to be added o the queue. Please, contact the Bot developer.")
          .queue();
    }
  }
}
