import java.util.List;

public abstract class Entity {
    private final String name;
    public Inventory inventory;

    public Entity(String name) {
        this.name = name;
    }

    public void initInventory(List<Item> initialItems, int maxWeight) {
        this.inventory = new Inventory(initialItems, maxWeight);
    }

    public String getName() {
        return name;
    }
}
