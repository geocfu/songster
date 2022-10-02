package dev.geocfu.songster.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
  private final AudioPlayer player;
  private final BlockingQueue<AudioTrack> queue;

  public TrackScheduler(AudioPlayer player) {
    this.player = player;
    this.queue = new LinkedBlockingQueue<>();
  }

  public void queue(AudioTrack track) {
    if (!player.startTrack(track, true)) {
      queue.offer(track);
    }
  }

  public void queuePlaylist(AudioPlaylist playlist) {
    final ArrayList<AudioTrack> tracks = (ArrayList<AudioTrack>) playlist.getTracks();

    final AudioTrack firstTrack = tracks.remove(0);
    queue(firstTrack);
    queue.addAll(tracks);
  }

  public void nextTrack() {
    player.startTrack(queue.poll(), false);
  }

  @Override
  public void onPlayerPause(AudioPlayer player) {}

  @Override
  public void onPlayerResume(AudioPlayer player) {}

  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
    if (endReason.mayStartNext) {
      nextTrack();
    }
  }
}
