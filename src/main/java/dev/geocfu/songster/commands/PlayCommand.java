package dev.geocfu.songster.commands;

public class PlayCommandImpl implements SlashCommand {
    @Override
    public String name() {
        return "play";
    }

    @Override
    public String description() {
        return "Plays a song from the provided URL or adds it the playlist.";
    }

    /**
     *
     */
    @Override
    public void execute() {

    }
//
//    public OptionData[] options() {
//        return [{OptionType.STRING, "url", "The URL of the song"}];
//    }

//    @Override
//    public static SlashCommandData register() {
////        return Commands.slash(this.name(), this.description()).addOptions();
//        return Commands.slash(name(), description());
//    }
}
