import java.util.Scanner;

public class Parser {
    private final CommandWords commands;  // holds all valid command words
    private final Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() {
        System.out.print("> "); // Prompt

        String inputLine = reader.nextLine();

        try (Scanner tokenizer = new Scanner(inputLine)) {
            String firstWord = tokenizer.hasNext() ? tokenizer.next() : null;
            String secondWord = tokenizer.hasNext() ? tokenizer.next() : null;
            String thirdWord = tokenizer.hasNext() ? tokenizer.next() : null;

            return new Command(commands.getCommandWord(firstWord), secondWord, thirdWord);
        }
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands() {
        commands.showAll();
    }
}
