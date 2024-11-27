import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a Mob Entity in the game, which is a type of {@link Entity}.
 * Mobs have an inventory with a fixed capacity and an associated action that can be executed.
 */
abstract public class Mob extends Entity {
    private static final int MOB_INVENTORY_CAPACITY = 50;
    private final double movementProbability;
    private Runnable action;

    /**
     * Constructs a new Mob with the given name and initial items.
     *
     * @param name         The name of the mob.
     * @param initialItems The list of initial items the mob starts with.
     */
    public Mob(String name, List<Item> initialItems, double movementProbability) {
        super(name, MOB_INVENTORY_CAPACITY, initialItems);
        this.movementProbability = movementProbability;
    }

    /**
     * Executes the mob's action, if any.
     */
    public void performAction() {
        if (action != null) {
            action.run();
        }
    }

    /**
     * Autonomous movement using chance. Find nearest exits and take a random one.
     */
    public void autoMove() {
        // Sometimes move, sometimes don't
        if (RANDOM.nextDouble() > this.movementProbability) {
            return;
        }

        Room currentRoom = getLocation();
        Set<Direction> exits = currentRoom.getExits();

        // Nowhere to go, do nothing
        if (exits.isEmpty()) return;

        Direction randomExit = new ArrayList<>(exits).get(RANDOM.nextInt(exits.size()));
        
        // Move to random location
        this.move(randomExit).ifPresent(
                newRoom -> System.out.println("* " + getName() + " has moved to " + newRoom.name() + " *")
        );
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