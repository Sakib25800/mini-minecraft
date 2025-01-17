import java.util.HashMap;
import java.util.Objects;

public class CommandWords {
    private final HashMap<String, CommandWord> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();

        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Find the {@link CommandWord} associated with a command word.
     *
     * @param commandWord The word to look up.
     * @return The CommandWord corresponding to commandWord, or UNKNOWN
     * if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);

        return Objects.requireNonNullElse(command, CommandWord.UNKNOWN);
    }

    /**
     * Print all valid commands to System.out.
     */
    public void showAll() {
        for (String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }

        System.out.println();
    }
}
