public class Command {
    private final CommandWord commandWord;
    private final String secondWord;

    /**
     * Create a command object. First and second words must be supplied, but
     * the second may be null.
     *
     * @param commandWord The CommandWord. UNKNOWN if the command word
     *                    was not recognised.
     * @param secondWord  The second word of the command. May be null.
     */
    public Command(CommandWord commandWord, String secondWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Return the command word (the first word) of this command.
     *
     * @return The command word.
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown() {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}

