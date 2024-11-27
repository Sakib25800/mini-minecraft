import java.util.List;

public class Enderman extends Mob {
    private static final double TELEPORTATION_PROBABILITY = 0.3;

    public Enderman() {
        super("enderman", List.of(Item.ENDER_PEARL), 0.1);
        this.addAction(this::teleportAction);
    }

    /**
     * Teleports the Enderman to a random exit in the current room.
     * This is the primary action of an Enderman in actual Minecraft.
     */
    private void teleportAction() {
        // Simulate autonomy by only sometimes teleporting
        if (RANDOM.nextDouble() < TELEPORTATION_PROBABILITY) {
            this.teleportToRandomRoom().ifPresent(
                    destination -> System.out.println("* Enderman has teleported to " + destination.name() + " *")
            );
        }
    }
}