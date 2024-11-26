import java.util.Optional;

public class Game {
    private static final String PLAYER_NAME = "Steve";
    private static final int INVENTORY_CAPACITY = 5;
    private static final Room SPAWN_ROOM = Room.PLAINS;

    private final Parser parser;
    private final Player player;

    public Game() {
        parser = new Parser();
        player = new Player(PLAYER_NAME, INVENTORY_CAPACITY);

        LocationManager.INSTANCE.spawn(player, Room.PLAINS);
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Mini Minecraft!");
        System.out.println("You are " + player.getName());
        System.out.println("Find items and craft an Eye of Ender to win.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getLocation());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                gotoRoom(command);
                break;

            case BACK:
                goBack();
                break;

            case INVENTORY:
                showInventory();
                break;

            case PICKUP:
                pickupItem(command);
                break;

            case DROP:
                dropItem(command);
                break;

            case CRAFT:
                craftItem(command);
                break;

            case ATTACK:
                attack(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }

        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You need to collect Blaze Powder and craft an Eye of Ender.");
        System.out.println("Travel between rooms to find what you need.");
        System.out.println("Blaze Powder is in the Village and you must kill the Enderman for the Eye of Ender.");
        System.out.println();
        System.out.println("Current room: " + player.getLocation());
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void gotoRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Output current room
        Direction.fromString(direction)
                .flatMap(player::move)
                .ifPresentOrElse(
                        System.out::println,
                        () -> {
                            Room currentRoom = player.getLocation();
                            System.out.println("I don't know that direction.\n" + currentRoom.getExitString());
                        }
                );
    }

    private void goBack() {
        player.goBack().ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Can't go back.")
        );
    }

    private void showInventory() {
        System.out.println(player.inventory);
    }

    private void pickupItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Pick what?");
            return;
        }

        String itemName = command.getSecondWord();
        player.pickup(itemName);
    }

    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        player.drop(itemName);
    }

    private void craftItem(Command command) {
        if (!command.hasSecondWord() || !command.hasThirdWord()) {
            System.out.println("Craft what with what?");
            return;
        }

        String firstItem = command.getSecondWord();
        String secondItem = command.getThirdWord();

        // Get the items from the inventory
        Optional<Item> item1Opt = player.inventory.getItem(firstItem);
        Optional<Item> item2Opt = player.inventory.getItem(secondItem);

        // If either item is missing, print a message and return
        if (item1Opt.isEmpty() || item2Opt.isEmpty()) {
            System.out.println("You don't have those items!");
            return;
        }

        // If a recipe exists, process it, otherwise show a message
        Recipes.findRecipe(item1Opt.get(), item2Opt.get()).ifPresentOrElse(
                recipe -> {
                    player.inventory.removeItem(item1Opt.get().getName());
                    player.inventory.removeItem(item2Opt.get().getName());

                    Item craftedItem = recipe.result();
                    player.inventory.addItem(craftedItem);

                    System.out.println("Crafted: " + craftedItem.getName());
                    checkWinCondition();
                },
                () -> System.out.println("Can't craft with those items!")
        );
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }

        return true;
    }

    private void attack(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Attack what?");
            return;
        }

        String mobName = command.getSecondWord();

        player.getLocation().getMobs().stream()
                .filter(mob -> mob.getName().equals(mobName))
                .findFirst()
                .ifPresentOrElse(
                        player::kill,
                        () -> System.out.println("There is no such mob here.")
                );
    }

    private void checkWinCondition() {
        Optional<Item> eyeOfEnder = player.inventory.getItem(Item.EYE_OF_ENDER.getName());

        eyeOfEnder.ifPresentOrElse((eye) -> {
            // If the player is in the Portal Room, they win
            if (player.getLocation() == Room.PORTAL_ROOM) {
                System.out.println("You've activated the End Portal! You win!");
                System.exit(0);
            } else {
                // If the player is not in the Portal Room, they need to go there
                System.out.println("You have the Eye of Ender. Head to the Portal Room to win!");
            }
        }, () -> System.out.println("You don't have the Eye of Ender. Craft it and head to the Portal Room to win."));
    }
}