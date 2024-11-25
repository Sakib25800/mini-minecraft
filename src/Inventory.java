import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private final Map<String, Item> items;
    private final int maxWeight;

    public Inventory(List<Item> initialItems, int maxWeight) {
        this.maxWeight = maxWeight;
        this.items = new HashMap<>();

        this.addItems(initialItems);
    }

    public Item removeItem(String itemName) {
        return items.remove(itemName);
    }

    public Item addItem(Item item) {
        // Check if additional item would exceed max weight
        double newWeight = getCurrentInventoryWeight() + item.getWeight();
        if (newWeight > maxWeight) {
            return null;
        }

        // If not, add to inventory
        return items.put(item.getName(), item);
    }

    public Map<String, Item> addItems(List<Item> itemsToAdd) {
        double newTotalWeight = getCurrentInventoryWeight() +
                itemsToAdd.stream().mapToDouble(Item::getWeight).sum();

        // Check if additional weight exceeds max weight
        if (newTotalWeight > maxWeight) {
            return null;
        }

        // Otherwise, add all items to inventory
        itemsToAdd.forEach(this::addItem);

        return items;
    }

    public Item getItem(String itemName) {
        return items.get(itemName);
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
        String itemsList = String.join(", ", this.items.keySet());
        return "Inventory(" + items.size() + "kg): " + itemsList + (itemsList.isEmpty() ? "" : ",");
    }
}
