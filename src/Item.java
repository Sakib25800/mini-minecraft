public enum Item {
    BLAZE_POWDER("Blaze Powder", "A rare powder found in villages", 2.0),
    ENDER_PEARL("Ender Pearl", "A pearl dropped by Endermen", 3.0),
    BLAZE_ROD("Blaze Rod", "A dangerous item from the Nether", 4.0),
    IRON_SWORD("Iron Sword", "A weapon found in strongholds", 5.0),
    EYE_OF_ENDER("Eye of Ender", "Crafted using Blaze Powder + Ender Pearl", 5.0);

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
    
    @Override
    public String toString() {
        return "Item[name=" + name + ", description=" + description + ", weight=" + weight + "]";
    }
}