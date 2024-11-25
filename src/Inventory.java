import java.util.Map;

public class Inventory {
    private final Map<String, Item> items;

    public Inventory(Map<String, Item> initialItems) {
        this.items = initialItems;
    }

    public Item removeItem(String itemName) {
        return this.items.remove(itemName);
    }

    public void addItem(Item item) {
        this.items.put(item.getName(), item);
    }

    public Item getItem(String itemName) {
        return this.items.get(itemName);
    }
}
