package dev.geocfu.songster.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

public class AudioPlayerProvider {
  private final HashMap<Long, GuildMusicManager> musicPlayerManagers;
  private final AudioPlayerManager audioPlayerManager;

  public AudioPlayerProvider() {
    musicPlayerManagers = new HashMap<>();
    audioPlayerManager = new DefaultAudioPlayerManager();

    AudioSourceManagers.registerRemoteSources(audioPlayerManager);
    AudioSourceManagers.registerLocalSource(audioPlayerManager);
  }

  public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
    GuildMusicManager guildMusicManager =
        musicPlayerManagers.computeIfAbsent(
            guild.getIdLong(), k -> new GuildMusicManager(audioPlayerManager));

    guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

    return guildMusicManager;
  }

  public AudioPlayerManager getAudioPlayerManager() {
    return audioPlayerManager;
  }
}
