import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Entity {
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
        Optional<Item> item = getLocation().inventory.getAllItems().stream()
                .filter(i -> i.getName().equals(itemName))
                .findFirst();

        return item.map(value -> this.inventory.addItem(value)
                .map(newItem -> {
                    getLocation().inventory.removeItem(value);
                    return value.getName() + " picked up.";
                })
                .orElse("Inventory full.")).orElseGet(() -> itemName + " is not in the room.");
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

        if (item.isPresent()) {
            this.inventory.removeItem(item.get());
            getLocation().inventory.addItem(item.get());
            return item.get().getName() + " dropped.";
        } else {
            return name + " doesn't have the item: " + itemName;
        }
    }

    /**
     * Causes the entity to die, dropping all items in the room and removing it from the game.
     *
     * @return a message indicating the result of the death action and any dropped items
     */
    public String die() {
        Inventory roomInventory = getLocation().inventory;
        List<Item> droppedItems = inventory.getAllItems(); // Store dropped items for result

        roomInventory.addItems(droppedItems); // Add dropped items to the room
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
     * Gets the name of the entity.
     *
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }
}