import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Mini Minecraft.
 */
public class Game {
    private static final String PLAYER_NAME = "Steve";
    private static final int INVENTORY_CAPACITY = 5;
    private static final Room SPAWN_ROOM = Room.PLAINS;

    private final Parser parser;
    private final Player player;
    private final List<Mob> mobs;

    /**
     * Constructs a new Game instance and initializes the player and parser.
     * The player starts in the spawn room (Plains).
     */
    public Game() {
        this.parser = new Parser();
        this.player = new Player(PLAYER_NAME, INVENTORY_CAPACITY);
        this.mobs = new ArrayList<>();

        initRoomItems();
        initMobs();
    }

    /**
     * Initialises all items in the game and places them.
     */
    private void initRoomItems() {
        Room.VILLAGE.items.addItem(Item.BLAZE_POWDER);
        Room.PLAINS.items.addItem(Item.BLAZE_ROD);
        Room.STRONGHOLD.items.addItem(Item.IRON_SWORD);
    }
    
    /**
     * Initialises all mobs in the game and spawns them.
     */
    private void initMobs() {
        Mob enderman = new Mobs.Enderman();
        Mob zombie = new Mobs.Zombie();

        mobs.add(enderman);
        mobs.add(zombie);

        LocationManager.INSTANCE.spawn(enderman, Room.FOREST);
        LocationManager.INSTANCE.spawn(zombie, Room.PLAINS);
    }

    /**
     * Starts the game and enters the game loop, where the player can issue commands.
     * The loop continues until the player quits.
     */
    public void play() {
        // Spawn player, begin game
        LocationManager.INSTANCE.spawn(player, SPAWN_ROOM);

        printWelcome();

        boolean finished = false;
        // Every entered command is a Minecraft tick
        while (!finished) {
            Command command = parser.getCommand();

            this.checkWinCondition();
            // Trigger mob actions every tick to simulate autonomy
            mobs.forEach(Mob::performAction);
            mobs.forEach(Mob::autoMove);

            finished = processCommand(command);
        }

        System.out.println("Thank you for playing. Good bye.");
    }

    /**
     * Prints the initial welcome message and instructions for the game.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Mini Minecraft!");
        System.out.println("You are " + player.getName());
        System.out.println("Find items and craft an Eye of Ender to win.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(player.getLocation());
    }

    /**
     * Processes a command issued by the player and performs the appropriate action.
     *
     * @param command The command to process.
     * @return {@code true} if the player chose to quit, otherwise {@code false}.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.commandWord();

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

            case MAP:
                System.out.println(player.getLocation());
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }

        return wantToQuit;
    }

    /**
     * Displays the help message, giving the player instructions on how to play the game.
     */
    private void printHelp() {
        System.out.println("Collect Blaze Powder (Village) + Ender Pearl (Enderman) to craft Eye of Ender.");
        System.out.println("Travel between rooms to find what you need.\n");
        System.out.println(player.getLocation() + "\nYour command words are:");
        parser.showCommands();
    }

    /**
     * Moves the player to a new room.
     *
     * @param command The command containing the direction to move.
     */
    private void gotoRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.secondWord();

        // Attempt to move player in given direction
        Direction.fromString(direction)
                .flatMap(player::move)
                .ifPresentOrElse(
                        (destination) -> {
                            System.out.println(destination);

                            // Nether room is for teleporting
                            if (destination == Room.NETHER) {
                                player.teleportToRandomRoom().ifPresentOrElse(
                                        (dest) -> {
                                            System.out.println("* Teleporting to " + dest.name() + " *");
                                            System.out.println(dest);
                                        },
                                        () -> System.out.println("Looks like there's nowhere to teleport to")
                                );
                            }
                        },
                        () -> {
                            // Player is being dumb, show them the possible exits
                            System.out.println("I don't know that direction.\n" + player.getLocation().getExitString());
                        }
                );
    }

    /**
     * Moves the player back to the previous room.
     */
    private void goBack() {
        player.goBack().ifPresentOrElse(
                System.out::println,    // output destination
                () -> System.out.println("Can't go back.")
        );
    }

    /**
     * Displays the player's inventory.
     */
    private void showInventory() {
        System.out.println(player.inventory);
    }

    /**
     * Attempt to add an item to the player's inventory.
     *
     * @param command The command containing the item to pick up.
     */
    private void pickupItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Pick what?");
            return;
        }

        String itemName = command.secondWord();
        String result = player.pickup(itemName);

        System.out.println(result);
    }

    /**
     * Attempt to drop an item from the player's inventory.
     *
     * @param command The command containing the item to drop.
     */
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.secondWord();
        String result = player.drop(itemName);

        System.out.println(result);
    }

    /**
     * Attempt to craft an item from the player's inventory.
     *
     * @param command The command containing the two ingredients to craft the item.
     */
    private void craftItem(Command command) {
        if (!command.hasSecondWord() || !command.hasThirdWord()) {
            System.out.println("Craft what with what?");
            return;
        }

        String firstItem = command.secondWord();
        String secondItem = command.thirdWord();

        // Get the items from inventory
        Optional<Item> item1Opt = player.inventory.getItem(firstItem);
        Optional<Item> item2Opt = player.inventory.getItem(secondItem);

        // If either item is missing, bail
        if (item1Opt.isEmpty() || item2Opt.isEmpty()) {
            System.out.println("You don't have those items!");
            return;
        }

        // Find the corresponding crafting recipe
        Recipes.findRecipe(item1Opt.get(), item2Opt.get()).ifPresentOrElse(
                recipe -> {
                    // Remove items from player inventory
                    player.inventory.removeItem(item1Opt.get().getName());
                    player.inventory.removeItem(item2Opt.get().getName());

                    // and replace with crafted item
                    Item craftedItem = recipe.result();
                    player.inventory.addItem(craftedItem);

                    System.out.println("Crafted: " + craftedItem.getName());
                },
                () -> System.out.println("Incompatible items!")
        );
    }

    /**
     * Handles the 'quit' command, ending the game.
     *
     * @param command The quit command.
     * @return {@code true} if the player wants to quit, otherwise {@code false}.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }

        return true;
    }

    /**
     * Attempt to attack a specified {@link Mob} in the current room.
     *
     * @param command The command containing the mob name to attack.
     */
    private void attack(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Attack what?");
            return;
        }

        String mobName = command.secondWord();

        player.getLocation().getMobs().stream()     // make sure mob exists at current location
                .filter(mob -> mob.getName().equals(mobName))
                .findFirst()
                .ifPresentOrElse(
                        mob -> {
                            String result = player.kill(mob);   // mob exists in the room, kill it
                            mobs.remove(mob);   // remove mob from game
                            System.out.println(result);
                        },
                        () -> System.out.println("There is no such mob here.")
                );
    }

    /**
     * Checks if the player has crafted the Eye of Ender and whether they are in the Portal Room.
     * If both conditions are met, the player gets the W.
     */
    private void checkWinCondition() {
        Optional<Item> eyeOfEnder = player.inventory.getItem(Item.EYE_OF_ENDER.getName());

        // Not in End Portal room, bail
        if (player.getLocation() != Room.END_PORTAL_ROOM) return;

        // Check if the player has the Eye of Ender
        eyeOfEnder.ifPresent((eye) -> {
            System.out.println("You've activated the End Portal! You win!");
            System.exit(0);
        });
    }
}