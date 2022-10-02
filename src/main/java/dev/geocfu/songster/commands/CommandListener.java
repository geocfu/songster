package dev.geocfu.songster.commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class CommandListener extends ListenerAdapter {
  static final Logger logger = LoggerFactory.getLogger(CommandListener.class);
  private final ArrayList<SlashCommand> slashCommands = new ArrayList<>();

  public CommandListener(ArrayList<SlashCommand> slashCommands) {
    this.slashCommands.addAll(slashCommands);
  }

  /**
   * This method runs when the jda has finished initializing and is ready to accept events from a
   * guild.
   *
   * @param event ReadyEvent
   */
  @Override
  public void onReady(@Nonnull ReadyEvent event) {
    JDA jda = event.getJDA();
    registerSlashCommands(jda);
  }

  /**
   * The entrypoint when a Slash Command is being received.
   *
   * @param event SlashCommandInteractionEvent
   */
  @Override
  public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
    if (!shouldReply(event)) return;
    logger.info("Received Slash Command \"{}\"", event.getName());

    String incomingCommand = event.getName();

    for (SlashCommand slashCommand : slashCommands) {
      if (incomingCommand.equalsIgnoreCase(slashCommand.getName())) {
        slashCommand.execute(event);
      }
    }
  }

  /**
   * Determine if we should reply to the invocation of the slash command.
   *
   * @param event SlashCommandInteractionEvent
   * @return True if we should reply to the sender
   */
  private Boolean shouldReply(@Nonnull SlashCommandInteractionEvent event) {
    if (event.isFromGuild() && !event.getUser().isBot()) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  /**
   * Registers all the application's Slash Commands.
   *
   * @param jda JDA
   */
  public void registerSlashCommands(JDA jda) {
    ArrayList<SlashCommandData> commandsToRegister = new ArrayList<>();

    for (SlashCommand slashCommand : slashCommands) {
      logger.info(
          "Slash Command \"{}\" added to the queue to be registered.", slashCommand.getName());
      commandsToRegister.add(slashCommand.getRegistration());
    }

    jda.updateCommands()
        .addCommands(commandsToRegister)
        .queue(
            (response) -> logger.info("All Slash Commands have been registered successfully."),
            (error) -> logger.error("Slash Commands registration failed.", error));
  }
}
