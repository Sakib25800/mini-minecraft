public record Command(CommandWord commandWord, String secondWord, String thirdWord) {
    /**
     * Create a command object. First and second words must be supplied, but
     * the second may be null.
     *
     * @param commandWord The CommandWord. UNKNOWN if the command word
     *                    was not recognised.
     * @param secondWord  The second word of the command. May be null.
     */
    public Command {
    }

    /**
     * Return the command word (the first word) of this command.
     *
     * @return The command word.
     */
    @Override
    public CommandWord commandWord() {
        return commandWord;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    @Override
    public String secondWord() {
        return secondWord;
    }

    /**
     * @return The third word of this command. Returns null if there was no
     * second word.
     */
    @Override
    public String thirdWord() {
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

