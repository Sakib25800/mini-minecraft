import java.util.List;

/**
 * Represents a Mob Entity in the game, which is a type of {@link Entity}.
 * Mobs have an inventory with a fixed capacity and an associated action that can be executed.
 */
abstract public class Mob extends Entity {
    private static final int MOB_INVENTORY_CAPACITY = 50;
    public Runnable action;

    /**
     * Constructs a new Mob with the given name and initial items.
     *
     * @param name         The name of the mob.
     * @param initialItems The list of initial items the mob starts with.
     */
    public Mob(String name, List<Item> initialItems) {
        super(name, MOB_INVENTORY_CAPACITY, initialItems);
    }

    /**
     * Executes the mob's action if it has been set.
     */
    public void performAction() {
        if (action != null) {
            action.run();
        }
    }

    /**
     * Sets the action that the mob will perform on {@link Room} change.
     *
     * @param action The action to be set.
     */
    public void addAction(Runnable action) {
        this.action = action;
    }
    
    @Override
    public String toString() {
        return this.getName();
    }
}