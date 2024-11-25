public abstract class Item {
    private final double weight;
    private final String name;
    private final String description;

    public Item(String name, String description, double weight) {
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
        return "Item[name=" + name + ", description=" + description + ", weight=" + weight + "]";
    }
}