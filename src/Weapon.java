public class Weapon extends Item {
    private final int damage;

    public Weapon(String name, String description, double weight, int damage) {
        super(name, description, weight);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void use() {
        System.out.println("Swinging the " + getName() + " to deal " + damage + " damage.");
    }

    @Override
    public String toString() {
        return super.toString() + ", damage=" + damage;
    }
}
