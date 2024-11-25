import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory {
    private final Map<String, Item> items;
    private final int maxWeight;

    public Inventory(List<Item> initialItems, int maxWeight) {
        this.maxWeight = maxWeight;
        // We implicitly allow duplicates by using a List, so to counter this
        // we check for duplicates and keep the last one
        this.items = initialItems.stream()
                .collect(Collectors.toMap(
                        Item::getName,
                        item -> item,
                        (existing, replacement) -> replacement)
                );
    }

    public Item removeItem(String itemName) {
        return items.remove(itemName);
    }

    public void addItem(Item item) {
        // Check if additional item would exceed max weight
        double currentWeight = getCurrentInventoryWeight();
        if (currentWeight + item.getWeight() > maxWeight) {
            throw new IllegalStateException("Cannot add item, it would exceed max weight capacity.");
        }

        // If not, add to inventory
        this.items.put(item.getName(), item);
    }

    public Item getItem(String itemName) {
        return items.get(itemName);
    }

    public double getCurrentInventoryWeight() {
        return items.values().stream()
                .mapToDouble(Item::getWeight)
                .sum();
    }

    @Override
    public String toString() {
        String itemsList = String.join(", ", this.items.keySet());
        return "Inventory(" + items.size() + "): " + itemsList + (itemsList.isEmpty() ? "" : ",");
    }
}
