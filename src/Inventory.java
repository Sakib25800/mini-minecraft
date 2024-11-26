import java.util.*;
import java.util.stream.Collectors;

public class Inventory {
    private final Map<String, Item> items;
    private final int capacity;

    public Inventory(int capacity, List<Item> initialItems) {
        this.capacity = capacity;
        this.items = new HashMap<>();
        this.addItems(Objects.requireNonNullElse(initialItems, List.of()));
    }

    public Inventory(int capacity) {
        this(capacity, new ArrayList<>());
    }

    public void removeItem(String itemName) {
        items.remove(itemName);
    }

    public void removeItem(Item item) {
        items.remove(item.getName());
    }

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

    public Optional<Map<String, Item>> addItems(List<Item> itemsToAdd) {
        double newTotalWeight = getCurrentInventoryWeight() +
                itemsToAdd.stream().mapToDouble(Item::getWeight).sum();

        // Check if additional weight exceeds max weight
        if (newTotalWeight > capacity) {
            return Optional.empty();
        }

        // Otherwise, add all items to inventory
        itemsToAdd.forEach(this::addItem);

        return Optional.of(items);
    }

    public Optional<Item> getItem(String itemName) {
        return Optional.ofNullable(items.get(itemName));
    }

    public List<Item> getAllItems() {
        return List.copyOf(items.values());
    }

    public double getCurrentInventoryWeight() {
        return items.values().stream()
                .mapToDouble(Item::getWeight)
                .sum();
    }

    public void clear() {
        items.clear();
    }

    @Override
    public String toString() {
        String itemsList = this.items.entrySet().stream()
                .map(entry -> entry.getKey() + " (" + entry.getValue().getWeight() + "kg)")
                .collect(Collectors.joining(", "));
        return "Inventory(" + getCurrentInventoryWeight() + "kg): " + (itemsList.isEmpty() ? "Empty" : itemsList);
    }
}
