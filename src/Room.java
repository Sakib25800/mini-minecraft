import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents various rooms in Mini Minecraft with inventory items, possible exits, and entities (e.g., mobs)
 * that may be present.
 * <p>Each room is connected to other rooms through exits, and the player can
 * travel between them.</p>
 */
public enum Room {
    PLAINS("a grassy starting area connecting all major locations"),
    VILLAGE("a village where rare materials are left on the floor"),
    FOREST("a dark forest where Enderman lurk"),
    NETHER("a dangerous room made for teleporting"),
    STRONGHOLD("an ancient structure housing the End Portal"),
    END_PORTAL_ROOM("an end portal room");

    // Large number as no need for a room to have a max capacity
    private static final int MAX_ITEM_CAPACITY = Integer.MAX_VALUE;

    static {
        // Initialise exits between rooms
        Map.of(
                PLAINS, Map.of(
                        Direction.NORTH, VILLAGE,
                        Direction.EAST, FOREST,
                        Direction.SOUTH, STRONGHOLD,
                        Direction.WEST, NETHER
                ),
                STRONGHOLD, Map.of(
                        Direction.NORTH, PLAINS,
                        Direction.SOUTH, END_PORTAL_ROOM
                ),
                VILLAGE, Map.of(Direction.SOUTH, PLAINS),
                FOREST, Map.of(Direction.WEST, PLAINS),
                NETHER, Map.of(Direction.EAST, PLAINS),
                END_PORTAL_ROOM, Map.of(Direction.NORTH, STRONGHOLD)
        ).forEach((room, exits) -> exits.forEach(room::setExit));
    }

    public final Inventory items;
    final HashMap<Direction, Room> exits;
    private final String description;

    Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new Inventory(MAX_ITEM_CAPACITY, new ArrayList<>());
    }

    /**
     * Sets an exit for this room in the specified direction to a neighboring room.
     *
     * @param direction The direction of the exit.
     * @param neighbor  The neighboring room.
     */
    public void setExit(Direction direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Returns a set of all directions that lead out of this room.
     *
     * @return A set of exits (directions) from this room.
     */
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

    private String getRoomDescription() {
        return name() + " - You are in " + description;
    }

    private String getItemsDescription() {
        List<Item> itemsInRoom = items.getAllItems();
        StringBuilder sb = new StringBuilder("Items: ");
        if (itemsInRoom.isEmpty()) {
            sb.append("None");
        } else {
            itemsInRoom.forEach(item -> sb.append(item).append(" "));
        }
        return sb.toString();
    }

    private String getMobsDescription() {
        List<Mob> currentMobs = getMobs();
        StringBuilder sb = new StringBuilder("Mobs: ");
        if (currentMobs.isEmpty()) {
            sb.append("None");
        } else {
            currentMobs.forEach(mob -> sb.append(mob).append(" "));
        }
        return sb.toString();
    }

    /**
     * Returns a list of mobs present in the room.
     *
     * @return A list of mobs in the room.
     */
    public List<Mob> getMobs() {
        return LocationManager.INSTANCE.getEntitiesInRoom(this).stream()
                .filter(entity -> entity instanceof Mob)
                .map(entity -> (Mob) entity)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "\n" +
                getRoomDescription() +
                "\n" +
                getExitString() +
                "\n" +
                getItemsDescription() +
                "\n" +
                getMobsDescription() +
                "\n";
    }
}