/**
 * Represents a command entered by the player in the game.
 * A command consists of a command word (e.g., "go", "pickup item1") and optional
 * second and third words.
 *
 * @param commandWord The primary command word (e.g., "go", "pickup").
 * @param secondWord  The optional second word that adds context to the command (e.g., "north", "diamond").
 * @param thirdWord   The optional third word for further context (e.g., "item2").
 */
public record Command(CommandWord commandWord, String secondWord, String thirdWord) {
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
        return (thirdWord != null);
    }
}