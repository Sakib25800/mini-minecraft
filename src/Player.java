import java.util.Optional;

public class Player extends Entity {
    public Player(String name, int maxWeight) {
        // Player starts off with no inventory
        super(name, maxWeight, null);
    }

    public void kill(Mob mob) {
        // Check if the player has an Iron Sword
        Optional<Item> ironSword = this.inventory.getAllItems().stream()
                .filter(item -> item == Item.IRON_SWORD)
                .findFirst();

        // If no Iron Sword is found, fail to kill
        if (ironSword.isEmpty()) {
            System.out.println("You need an Iron Sword to kill this mob!");
            return;
        }

        // Else kill mob
        mob.die();
    }
}