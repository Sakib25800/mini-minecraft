public enum CommandWord {
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    BACK("back"),
    INVENTORY("inventory"),
    PICKUP("pickup"),
    DROP("drop"),
    CRAFT("craft"),
    ATTACK("attack"),
    UNKNOWN("?");

    private final String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }
}