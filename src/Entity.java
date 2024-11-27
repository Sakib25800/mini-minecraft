import java.util.*;
import java.util.stream.Collectors;

public abstract class Entity {
    protected static final Random RANDOM = new Random();

    private final String name;
    public Inventory inventory;


    /**
     * Creates a new Entity in Mini Minecraft.
     *
     * @param name         the name of the entity
     * @param maxWeight    the maximum weight the entity's inventory can hold
     * @param initialItems the initial items the entity starts with
     */
    public Entity(String name, int maxWeight, List<Item> initialItems) {
        this.name = name;
        this.inventory = new Inventory(maxWeight, initialItems != null ? initialItems : new ArrayList<>());
    }

    /**
     * Gets the current room the entity is in.
     *
     * @return the room the entity is located in
     */
    public Room getLocation() {
        return LocationManager.INSTANCE.getLocation(this);
    }

    /**
     * Sets the current room the entity is in.
     */
    public void setLocation(Room to) {
        LocationManager.INSTANCE.setLocation(this, to);
    }

    /**
     * Moves the entity in the given direction.
     *
     * @param dir the direction to move the entity
     * @return an optional room the entity moves to
     */
    public Optional<Room> move(Direction dir) {
        return LocationManager.INSTANCE.moveEntity(this, dir);
    }

    /**
     * Moves the entity back to the previous room.
     *
     * @return an optional room the entity returns to
     */
    public Optional<Room> goBack() {
        return LocationManager.INSTANCE.goBack(this);
    }

    /**
     * Picks up an item from the room's floor.
     *
     * @param itemName the name of the item to pick up
     * @return a message indicating the result of the pickup action
     */
    public String pickup(String itemName) {
        Optional<Item> item = getLocation().items.getAllItems().stream()
                .filter(i -> i.getName().equals(itemName))
                .findFirst();

        // If the item is found
        return item.map(value -> {
                    // Player cannot pick up
                    if (!value.isPickable()) {
                        return itemName + " is not pickable.";
                    }

                    // Try to add the item to the inventory
                    return this.inventory.addItem(value)
                            .map(newItem -> {
                                getLocation().items.removeItem(value);
                                return value.getName() + " picked up.";
                            })
                            .orElse("Inventory full.");
                })
                .orElseGet(() -> itemName + " is not in the room.");
    }

    /**
     * Drops an item from the entity's inventory onto the floor.
     *
     * @param itemName the name of the item to drop
     * @return a message indicating the result of the drop action
     */
    public String drop(String itemName) {
        Optional<Item> item = inventory.getAllItems().stream()
                .filter(i -> i.getName().equals(itemName))
                .findFirst();

        if (item.isEmpty()) {
            return "Item not found: " + itemName;
        }

        // Remove item from inventory
        this.inventory.removeItem(item.get());
        // and add item to current room
        getLocation().items.addItem(item.get());

        return item.get().getName() + " dropped.";
    }

    /**
     * Causes the entity to die, dropping all items in the room and removing it from the game.
     *
     * @return a message indicating the result of the death action and any dropped items
     */
    public String die() {
        Inventory roomItems = getLocation().items;
        List<Item> droppedItems = inventory.getAllItems(); // Store dropped items for result

        roomItems.addItems(droppedItems); // Add dropped items to the room
        inventory.clear(); // Clear the entity's inventory
        LocationManager.INSTANCE.removeEntity(this); // Remove the entity

        // Construct death message
        String deathMessage = "* " + name + " has died *\n";
        if (droppedItems.isEmpty()) {
            deathMessage += "Dropped: Nothing.";
        } else {
            String droppedList = droppedItems.stream()
                    .map(item -> item.getName() + " (" + item.getWeight() + "kg)")
                    .collect(Collectors.joining(", "));
            deathMessage += "Dropped: " + droppedList;
        }

        return deathMessage;
    }

    /**
     * Teleport Entity to a random room.
     */
    public Optional<Room> teleportToRandomRoom() {
        Room currentRoom = getLocation();
        Room[] availableRooms = Arrays.stream(Room.values())
                .filter(room -> !room.equals(currentRoom)) // don't teleport to the same room
                .toArray(Room[]::new);

        if (availableRooms.length == 0) {
            return Optional.empty();
        }

        // Select a random room
        Random random = new Random();
        Room randomRoom = availableRooms[random.nextInt(availableRooms.length)];

        setLocation(randomRoom);

        return Optional.of(randomRoom);
    }

    /**
     * Gets the name of the entity.
     *
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }
}