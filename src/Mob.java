import java.util.List;

abstract public class Mob extends Entity {
    private static final int MOB_INVENTORY_CAPACITY = 50;
    public Runnable action;

    public Mob(String name, List<Item> initialItems) {
        super(name, MOB_INVENTORY_CAPACITY, initialItems);
    }

    public void performAction() {
        if (action != null) {
            action.run();
        }
    }

    public void addAction(Runnable action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}