/**
 * Repository of all valid command words for Mini Minecraft
 * along with a string in a particular language.
 */
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
    MAP("map"),
    UNKNOWN("?");

    private final String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }
}