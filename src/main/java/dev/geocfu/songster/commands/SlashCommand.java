package dev.geocfu.songster.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.internal.utils.Checks;

import java.util.ArrayList;

public interface SlashCommand {
  /**
   * Returns the name of the slash command.
   *
   * @return The name of the slash command
   */
  String getName();

  /**
   * Returns the description of the slash command.
   *
   * @return the description of the slash command
   */
  String getDescription();

  /**
   * The options to add to the Command.
   *
   * @return The options
   */
  default ArrayList<OptionData> getOptions() {
    return new ArrayList<>();
  }

  /**
   * Register the slash command
   *
   * @return The SlashCommandData object to register
   */
  default SlashCommandData getRegistration() {
    String name = getName();
    String description = getDescription();
    Checks.notEmpty(name, "Command name");
    Checks.notEmpty(description, "Command description");

    SlashCommandData command = Commands.slash(getName(), getDescription());
    if (!getOptions().isEmpty()) {
      command.addOptions(getOptions());
    }
    return command;
  }

  /** The main method that is going to be executed when the command has been summoned */
  void execute(SlashCommandInteractionEvent event);
}
