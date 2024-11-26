public enum Item {
    BLAZE_POWDER("blaze_powder", "A rare powder found in villages", 2.0),
    ENDER_PEARL("ender_pearl", "A pearl dropped by Endermen", 3.0),
    BLAZE_ROD("blaze_rod", "A dangerous item from the Nether", 4.0),
    IRON_SWORD("iron_sword", "A weapon found in strongholds", 5.0),
    EYE_OF_ENDER("eye_of_ender", "Crafted using Blaze Powder + Ender Pearl", 5.0);

    private final String name;
    private final String description;
    private final double weight;

    Item(String name, String description, double weight) {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }


    public double getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " (" + weight + "kg)";
    }
}