import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Enderman extends Mob {
    private static final double TELEPORTATION_PROBABILITY = 0.3;

    public Enderman() {
        super("enderman", List.of(Item.ENDER_PEARL));
        addAction(this::teleportAction);
    }

    /**
     * Teleport the Enderman to a random exit via the current room.
     */
    private void teleportAction() {
        Room currentRoom = getLocation();
        Set<Direction> exits = currentRoom.getExits();

        // Enderman has nowhere to go, do nothing
        if (exits.isEmpty()) return;
        
        Direction randomExit = new ArrayList<>(exits).get(RANDOM.nextInt(exits.size()));

        // Simulate autonomous mob by adding chance to teleportation
        if (RANDOM.nextDouble() <= TELEPORTATION_PROBABILITY) {
            this.move(randomExit).ifPresent(
                    newRoom -> System.out.println("* Enderman has teleported to " + newRoom.name() + " *")
            );
        }
    }
}