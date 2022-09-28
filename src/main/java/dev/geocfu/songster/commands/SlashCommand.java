package dev.geocfu.songster.commands;

public interface SlashCommand {
    /**
     * Returns the name of the slash command.
     *
     * @return The name of the slash command
     */
    public String name();

    /**
     * Returns the description of the slash command.
     *
     * @return the description of the slash command
     */
    String description();
}
