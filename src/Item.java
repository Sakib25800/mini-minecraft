/**
 * Repository of all Items in Mini Minecraft.
 */
public enum Item {
    BLAZE_POWDER("blaze_powder", 2.0),
    ENDER_PEARL("ender_pearl", 3.0),
    BLAZE_ROD("blaze_rod", 4.0),
    IRON_SWORD("iron_sword", 5.0),
    EYE_OF_ENDER("eye_of_ender", 5.0);

    private final String name;
    private final double weight;

    /**
     * Constructs an Item enum with the specified name and weight.
     *
     * @param name   the name of the item.
     * @param weight the weight of the item.
     */
    Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return name + " (" + weight + "kg)";
    }
}