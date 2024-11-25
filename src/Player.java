public class Player extends Entity {
    public Player(String name, Room startingRoom) {
        super(name, startingRoom);
    }

    public void kill(Mob mob) {
        Item sword = this.inventory.getItem();
        mob.die();
    }
}
