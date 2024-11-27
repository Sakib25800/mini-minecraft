import java.util.List;

public class Mobs {
    public static class Enderman extends Mob {
        private static final double TELEPORTATION_PROBABILITY = 0.3;

        public Enderman() {
            super("enderman", List.of(Item.ENDER_PEARL), 0.1);
            this.addAction(this::teleportAction);
        }

        private void teleportAction() {
            if (RANDOM.nextDouble() < TELEPORTATION_PROBABILITY) {
                this.teleportToRandomRoom().ifPresent(
                        destination -> System.out.println("* Enderman has teleported to " + destination.name() + " *")
                );
            }
        }
    }

    public static class Zombie extends Mob {
        public Zombie() {
            super("zombie", List.of(Item.ROTTEN_FLESH), 0.2);
            this.addAction(this::makeNoise);
        }

        private void makeNoise() {
            System.out.println("Zombie: Grrr");
        }
    }
}