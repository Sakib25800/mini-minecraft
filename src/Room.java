import java.util.*;
import java.util.stream.Collectors;

public enum Room {
    PLAINS("a grassy starting area connecting all major locations"),
    VILLAGE("a village where rare materials are left on the floor"),
    FOREST("a dark forest where Enderman lurk"),
    NETHER("a dangerous dimension with essential materials"),
    STRONGHOLD("an ancient structure housing the End Portal"),
    PORTAL_ROOM("an end portal room");

    // Weight is a large value, since rooms don't really
    // need max weight
    private static final int MAX_INVENTORY_CAPACITY = Integer.MAX_VALUE;

    static {
        // Initialise Map
        Map.of(
                PLAINS, Map.of(
                        Direction.NORTH, VILLAGE,
                        Direction.EAST, FOREST,
                        Direction.SOUTH, STRONGHOLD,
                        Direction.WEST, NETHER
                ),
                STRONGHOLD, Map.of(
                        Direction.NORTH, PLAINS,
                        Direction.SOUTH, PORTAL_ROOM
                ),
                VILLAGE, Map.of(Direction.SOUTH, PLAINS),
                FOREST, Map.of(Direction.WEST, PLAINS),
                NETHER, Map.of(Direction.EAST, PLAINS),
                PORTAL_ROOM, Map.of(Direction.NORTH, STRONGHOLD)
        ).forEach((room, exits) -> exits.forEach(room::setExit));

        // Initialise inventory
        Room.VILLAGE.inventory.addItem(Item.BLAZE_POWDER);
        Room.STRONGHOLD.inventory.addItem(Item.IRON_SWORD);

        // Initialise mobs
        LocationManager.INSTANCE.spawn(new Enderman(), Room.PLAINS);
    }

    public final Inventory inventory;
    final HashMap<Direction, Room> exits;
    private final String description;

    Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
        this.inventory = new Inventory(MAX_INVENTORY_CAPACITY, new ArrayList<>());
    }

    public List<Entity> getEntities() {
        return LocationManager.INSTANCE.getEntitiesInRoom(this);
    }

    public void setExit(Direction direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public Set<Direction> getExits() {
        return exits.keySet();
    }

    public String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");

        for (Direction exit : getExits()) {
            Room destination = exits.get(exit);
            returnString.append(" ").append(exit).append(" (").append(destination.name()).append(")");
        }

        return returnString.toString();
    }

    public Optional<Room> getExit(Direction direction) {
        return Optional.ofNullable(exits.get(direction));
    }

    public List<Mob> getMobs() {
        return LocationManager.INSTANCE.getEntitiesInRoom(this).stream()
                .filter(entity -> entity instanceof Mob)
                .map(entity -> (Mob) entity)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n");

        // Long description of the room
        sb.append("You are in ").append(description);

        sb.append("\n");

        // Output exits
        sb.append("\n").append(getExitString());

        sb.append("\n");

        // Items in the room
        List<Item> itemsInRoom = inventory.getAllItems();
        sb.append("Items: ");
        if (itemsInRoom.isEmpty()) {
            sb.append("None");
        } else {
            itemsInRoom.forEach(sb::append);
        }

        sb.append("\n");

        // Entities in the room
        sb.append("Mobs: ");
        List<Mob> currentMobs = getMobs();
        if (currentMobs.isEmpty()) {
            sb.append("None");
        } else {
            currentMobs.forEach(sb::append);
        }

        sb.append("\n");

        return sb.toString();
    }
}