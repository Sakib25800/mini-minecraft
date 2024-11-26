public class Command {
    private final CommandWord commandWord;
    private final String secondWord;
    private final String thirdWord;

    /**
     * Create a command object. First and second words must be supplied, but
     * the second may be null.
     *
     * @param commandWord The CommandWord. UNKNOWN if the command word
     *                    was not recognised.
     * @param secondWord  The second word of the command. May be null.
     */
    public Command(CommandWord commandWord, String secondWord, String thirdWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
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
     * @return The third word of this command. Returns null if there was no
     * second word.
     */
    public String getThirdWord() {
        return thirdWord;
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

    /**
     * @return true if the command has a third word.
     */
    public boolean hasThirdWord() {
        return (secondWord != null);
    }
}

