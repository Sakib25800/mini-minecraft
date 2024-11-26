import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Enderman extends Mob {

    public Enderman() {
        // Call the Mob constructor with the action as a lambda
        super("enderman", List.of(Item.ENDER_PEARL));
        addAction(this::teleport);
    }

    private void teleport() {
        Random random = new Random();
        Room currentRoom = getLocation();
        Set<Direction> exits = currentRoom.getExits();

        if (exits.isEmpty()) {
            System.out.println("* The Enderman has nowhere to go *");
            return;
        }

        Direction randomExit = new ArrayList<>(exits).get(random.nextInt(exits.size()));
        LocationManager.INSTANCE.moveEntity(this, randomExit).ifPresent(newRoom ->
                System.out.println("* The Enderman has teleported to " + newRoom.name() + " *")
        );
    }
}