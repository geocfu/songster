package dev.geocfu.songster.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {
  private final TrackScheduler trackScheduler;
  private final AudioPlayerSendHandler audioPlayerSendHandler;

  public GuildMusicManager(AudioPlayerManager manager) {
    AudioPlayer player = manager.createPlayer();
    trackScheduler = new TrackScheduler(player);
    player.addListener(trackScheduler);
    audioPlayerSendHandler = new AudioPlayerSendHandler(player);
  }

  public AudioPlayerSendHandler getSendHandler() {
    return audioPlayerSendHandler;
  }

  public TrackScheduler getScheduler() {
    return trackScheduler;
  }
}
