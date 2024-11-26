import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents an inventory that can hold a collection of items with a weight capacity.
 */
public class Inventory {
    private final Map<String, Item> items;
    private final int capacity;

    /**
     * Constructs an Inventory with the specified capacity and initial items.
     *
     * @param capacity     the maximum weight capacity of the inventory.
     * @param initialItems the list of initial items to add to the inventory.
     */
    public Inventory(int capacity, List<Item> initialItems) {
        this.capacity = capacity;
        this.items = new HashMap<>();
        this.addItems(Objects.requireNonNullElse(initialItems, List.of()));
    }

    /**
     * Constructs an Inventory with the specified capacity and no initial items.
     *
     * @param capacity the maximum weight capacity of the inventory.
     */
    public Inventory(int capacity) {
        this(capacity, new ArrayList<>());
    }

    /**
     * Removes an item from the inventory by its name.
     *
     * @param itemName the name of the item to remove.
     */
    public void removeItem(String itemName) {
        items.remove(itemName);
    }

    /**
     * Removes the specified item from the inventory.
     *
     * @param item the item to remove.
     */
    public void removeItem(Item item) {
        items.remove(item.getName());
    }

    /**
     * Adds an item to the inventory, if it does not exceed the maximum weight.
     *
     * @param item the item to add.
     * @return an Optional containing the item if added, or empty if the weight limit is exceeded.
     */
    public Optional<Item> addItem(Item item) {
        // Check if additional item would exceed max weight
        double newWeight = getCurrentInventoryWeight() + item.getWeight();
        if (newWeight > capacity) {
            return Optional.empty();
        }

        // If not, add to inventory
        items.put(item.getName(), item);

        return Optional.of(item);
    }

    /**
     * A convenience method that adds a list of items to the inventory, if the total weight does not
     * exceed the capacity.
     *
     * @param itemsToAdd the list of items to add.
     * @throws IllegalStateException if adding the items exceeds the maximum weight capacity.
     */
    public void addItems(List<Item> itemsToAdd) {
        double newTotalWeight = getCurrentInventoryWeight() +
                itemsToAdd.stream().mapToDouble(Item::getWeight).sum();

        // Check if additional weight exceeds max weight
        if (newTotalWeight > capacity) {
            throw new IllegalStateException("Adding these items exceeds the maximum weight capacity.");
        }

        // Otherwise, add all items to inventory
        itemsToAdd.forEach(this::addItem);
    }

    /**
     * Retrieves an item from the inventory by its name.
     *
     * @param itemName the name of the item to retrieve.
     * @return an Optional containing the item if found, or empty if not found.
     */
    public Optional<Item> getItem(String itemName) {
        return Optional.ofNullable(items.get(itemName));
    }

    /**
     * Retrieves a list of all items in the inventory.
     *
     * @return a list of all items in the inventory.
     */
    public List<Item> getAllItems() {
        return List.copyOf(items.values());
    }

    /**
     * Calculates the current total weight of all items in the inventory.
     *
     * @return the total weight of the items in the inventory.
     */
    public double getCurrentInventoryWeight() {
        return items.values().stream()
                .mapToDouble(Item::getWeight)
                .sum();
    }

    /**
     * Clears all items from the inventory.
     */
    public void clear() {
        items.clear();
    }

    @Override
    public String toString() {
        // Create the string for the items in the inventory
        String itemsList = this.items.entrySet().stream()
                .map(entry -> entry.getKey() + " (" + entry.getValue().getWeight() + "kg)")
                .collect(Collectors.joining(", "));

        // Get current inventory weight and max capacity
        double currentWeight = getCurrentInventoryWeight();

        // Return the formatted string with the current weight and max weight
        return "Inventory (" + currentWeight + "/" + capacity + "kg): "
                + (itemsList.isEmpty() ? "Empty" : itemsList);
    }
}