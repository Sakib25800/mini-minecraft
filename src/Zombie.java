import java.util.List;

public class Zombie extends Mob {
    public Zombie() {
        super("zombie", List.of(Item.ROTTEN_FLESH), 0.2);
        this.addAction(this::makeNoise);
    }

    /**
     * Make Zombie noises.
     */
    private void makeNoise() {
        System.out.println("Zombie: Grrr");
    }
}
