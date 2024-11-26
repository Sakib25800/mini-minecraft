import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Entity {
    private final String name;
    public Inventory inventory;

    public Entity(String name, int maxWeight, List<Item> initialItems) {
        this.name = name;
        this.inventory = new Inventory(maxWeight, initialItems != null ? initialItems : new ArrayList<>());
    }

    public Room getLocation() {
        return LocationManager.INSTANCE.getLocation(this);
    }

    public Optional<Room> move(Direction dir) {
        return LocationManager.INSTANCE.moveEntity(this, dir);
    }

    public Optional<Room> goBack() {
        return LocationManager.INSTANCE.goBack(this);
    }

    public void pickup(String itemName) {
        getLocation().inventory.getAllItems().stream()
                .filter(i -> i.getName().equals(itemName))
                .findFirst().ifPresentOrElse(
                        item -> {
                            this.inventory.addItem(item).ifPresentOrElse(
                                    newItem -> {
                                        System.out.println(item.getName() + " picked up.");
                                        getLocation().inventory.removeItem(item);
                                    },
                                    () -> System.out.println("Inventory full.")
                            );
                        },
                        () -> System.out.println(itemName + " is not in the room.")
                );
    }

    public void drop(String itemName) {
        // Find the item in the entity's inventory
        inventory.getAllItems().stream()
                .filter(i -> i.getName().equals(itemName))
                .findFirst().ifPresentOrElse(
                        item -> {
                            // Remove the item from the entity's inventory and add it to the room's inventory
                            this.inventory.removeItem(item);
                            getLocation().inventory.addItem(item);
                            System.out.println(item.getName() + " dropped.");
                        },
                        () -> System.out.println(name + " doesn't have the item: " + itemName)
                );
    }

    public void die() {
        // Drop all items into room in which Entity has died in
        Inventory roomInventory = getLocation().inventory;
        List<Item> droppedItems = inventory.getAllItems(); // Store dropped items for logging
        roomInventory.addItems(droppedItems); // Add dropped items to the room
        inventory.clear(); // Clear the entity's inventory
        LocationManager.INSTANCE.removeEntity(this); // Remove the entity from the game

        // Print the death message and list dropped items
        System.out.println("* " + name + " has died *");
        if (droppedItems.isEmpty()) {
            System.out.println("Dropped: Nothing.");
        } else {
            String droppedList = droppedItems.stream()
                    .map(item -> item.getName() + " (" + item.getWeight() + "kg)")
                    .collect(Collectors.joining(", "));
            System.out.println("Dropped: " + droppedList);
        }
    }

    public String getName() {
        return name;
    }
}
