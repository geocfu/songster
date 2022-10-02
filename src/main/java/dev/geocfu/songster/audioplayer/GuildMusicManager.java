package dev.geocfu.songster.audioplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;

public class GuildMusicManager {
  private final TrackScheduler trackScheduler;
  private final DefaultAudioPlayerManager audioPlayerManager;

  public GuildMusicManager(Guild guild) {
    this.audioPlayerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
    AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

    final AudioPlayer audioPlayer = this.audioPlayerManager.createPlayer();
    final AudioManager audioManager = guild.getAudioManager();
    this.trackScheduler = new TrackScheduler(audioPlayer, audioManager);

    final AudioPlayerSendHandler audioPlayerSendHandler = new AudioPlayerSendHandler(audioPlayer);

    audioPlayer.addListener(trackScheduler);
    audioManager.setSendingHandler(audioPlayerSendHandler);
  }

  public DefaultAudioPlayerManager getAudioPlayerManager() {
    return audioPlayerManager;
  }

  public TrackScheduler getScheduler() {
    return trackScheduler;
  }
}
