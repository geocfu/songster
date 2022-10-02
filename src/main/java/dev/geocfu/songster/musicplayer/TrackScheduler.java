package dev.geocfu.songster.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
  private final AudioPlayer player;
  private final AudioManager audioManager;
  private final LinkedBlockingQueue<AudioTrack> queue;

  public TrackScheduler(AudioPlayer player, AudioManager audioManager) {
    this.audioManager = audioManager;
    this.player = player;
    this.queue = new LinkedBlockingQueue<>();
  }

  public void play(AudioTrack track) {
    if (!player.startTrack(track, true)) {
      queue.offer(track);
    }
  }

  public void play() {
    player.startTrack(queue.poll(), true);
  }

  public void playPlaylist(AudioPlaylist playlist) {
    queue.addAll(playlist.getTracks());
    play();
  }

  public void nextTrack() {
    if (!player.startTrack(queue.poll(), false)) {
      audioManager.closeAudioConnection();
    }
  }

  public void pause() {
    player.setPaused(Boolean.TRUE);
  }

  public void resume() {
    player.setPaused(Boolean.FALSE);
  }

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    if (endReason.mayStartNext) {
      if (queue.isEmpty()) {
        audioManager.closeAudioConnection();
        return;
      }
      nextTrack();
    }
  }

  public String getQueue() {
    return queue.toString();
  }
}
