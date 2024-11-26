import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Enderman extends Mob {
    public Enderman() {
        super("enderman", List.of(Item.ENDER_PEARL));
        addAction(this::teleport);
    }

    /**
     * Teleport the Enderman to a random exit in the current room.
     */
    private void teleport() {
        Random random = new Random();
        Room currentRoom = getLocation();
        Set<Direction> exits = currentRoom.getExits();

        // Enderman has nowhere to go, do nothing
        if (exits.isEmpty()) {
            return;
        }

        Direction randomExit = new ArrayList<>(exits).get(random.nextInt(exits.size()));
        LocationManager.INSTANCE.moveEntity(this, randomExit).ifPresent(newRoom ->
                System.out.println("* The Enderman has teleported to " + newRoom.name() + " *")
        );
    }
}