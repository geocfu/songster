package dev.geocfu.songster.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;

public class GuildMusicManager {
  private final TrackScheduler trackScheduler;
  private final AudioPlayerSendHandler audioPlayerSendHandler;
  private final DefaultAudioPlayerManager audioPlayerManager;

  public GuildMusicManager(Guild guild) {
    this.audioPlayerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
    AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

    final AudioPlayer audioPlayer = this.audioPlayerManager.createPlayer();
    final AudioManager audioManager = guild.getAudioManager();
    this.trackScheduler = new TrackScheduler(audioPlayer, audioManager);

    this.audioPlayerSendHandler = new AudioPlayerSendHandler(audioPlayer);

    audioPlayer.addListener(trackScheduler);
    audioManager.setSendingHandler(this.audioPlayerSendHandler);
  }

  public DefaultAudioPlayerManager getAudioPlayerManager() {
    return audioPlayerManager;
  }

  public TrackScheduler getScheduler() {
    return trackScheduler;
  }
}
