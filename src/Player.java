import java.util.Optional;

/**
 * Represents a Player entity in the game, which is a type of {@link Entity}.
 * A player has the ability to kill mobs if they have an Iron Sword in their inventory.
 */
public class Player extends Entity {

    /**
     * Constructs a new Player with the specified name and maximum weight capacity for inventory.
     * Initially, the player starts with an empty inventory.
     *
     * @param name      The name of the player.
     * @param maxWeight The maximum weight the player's inventory can hold.
     */
    public Player(String name, int maxWeight) {
        // Start with no inventory
        super(name, maxWeight, null);
    }

    /**
     * Attempts to kill the specified mob. The player can only kill the mob if they possess an Iron Sword
     * in their inventory.
     *
     * @param mob The mob to be killed.
     * @return A message indicating the result of the kill attempt:
     * - Success message if the mob is killed.
     * - Failure message if the player doesn't have an Iron Sword.
     */
    public String kill(Mob mob) {
        // Check if the player has an Iron Sword
        Optional<Item> ironSword = this.inventory.getAllItems().stream()
                .filter(item -> item == Item.IRON_SWORD)
                .findFirst();

        // If not, fail to kill
        if (ironSword.isEmpty()) {
            return "You need an Iron Sword to kill mobs!";
        }

        // Else kill mob
        return mob.die();
    }
}