package dev.geocfu.songster.audioplayer;

import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

public class AudioPlayerProvider {
  private final HashMap<Long, GuildMusicManager> musicPlayerManagers;

  public AudioPlayerProvider() {
    musicPlayerManagers = new HashMap<>();
  }

  public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
    return musicPlayerManagers.computeIfAbsent(
        guild.getIdLong(), k -> new GuildMusicManager(guild));
  }
}
