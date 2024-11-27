/**
 * Repository of all Items in Mini Minecraft.
 */
public enum Item {
    ENDER_PEARL("ender_pearl", 3.0, true),
    IRON_SWORD("iron_sword", 5.0, true),
    BLAZE_POWDER("blaze_powder", 2.0, true),
    ROTTEN_FLESH("rotten_flesh", 1.0, true),
    BLAZE_ROD("blaze_rod", 4.0, false),
    EYE_OF_ENDER("eye_of_ender", 5.0, false);

    private final String name;
    private final double weight;
    private final boolean isPickable;

    /**
     * Constructs an Item enum with the specified name and weight.
     *
     * @param name   the name of the item.
     * @param weight the weight of the item.
     */
    Item(String name, double weight, boolean isPickable) {
        this.name = name;
        this.weight = weight;
        this.isPickable = isPickable;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isPickable() {
        return isPickable;
    }

    @Override
    public String toString() {
        return name + " (" + weight + "kg)";
    }
}