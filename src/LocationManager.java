import java.util.*;

public class LocationManager {
    public static final LocationManager INSTANCE = new LocationManager();

    private final Map<Entity, List<Room>> locationHistory = new HashMap<>();
    private final Set<Entity> currentlyProcessing = new HashSet<>();

    private LocationManager() {
    }

    /**
     * Spawns a new entity in the starting room.
     *
     * @param entity       The entity to spawn.
     * @param startingRoom The room where the entity starts.
     */
    public void spawn(Entity entity, Room startingRoom) {
        // Initialize the location history for the entity with the starting room
        locationHistory.put(entity, new ArrayList<>(List.of(startingRoom)));
    }

    /**
     * Gets the current room of the entity.
     *
     * @param entity The entity whose location is requested.
     * @return The current room of the entity.
     */
    public Room getLocation(Entity entity) {
        List<Room> history = locationHistory.get(entity);
        return history != null && !history.isEmpty() ? history.getLast() : null;
    }

    /**
     * Gets a list of all entities in the specified room.
     *
     * @param room The room to check.
     * @return A list of entities in the room.
     */
    public List<Entity> getEntitiesInRoom(Room room) {
        return locationHistory.entrySet().stream()
                .filter(entry -> entry.getValue().getLast() == room)
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Moves the specified entity to the next room in the given direction.
     *
     * @param entity    The entity to move.
     * @param direction The direction in which to move.
     * @return The next room the entity moves to, or empty if the move is not possible.
     */
    public Optional<Room> moveEntity(Entity entity, Direction direction) {
        Room currentRoom = getLocation(entity);
        Room nextRoom = currentRoom.exits.get(direction);

        if (nextRoom == null) {
            return Optional.empty();
        }

        // Before moving, trigger actions of mobs in the current room
        getEntitiesInRoom(currentRoom).stream()
                .filter(e -> e instanceof Mob)
                .map(e -> (Mob) e)
                .forEach(this::triggerMobAction);

        // Run the room's on enter callback, if any
        Runnable onEnter = currentRoom.getOnEnterHandler();
        if (onEnter != null) {
            onEnter.run();
        }

        // Move the entity by adding the next room to the location history list
        locationHistory.get(entity).add(nextRoom);
        return Optional.of(nextRoom);
    }

    /**
     * Moves the specified entity back to the previous room, if possible.
     *
     * @param entity The entity to move back.
     * @return The previous room the entity moves to, or empty if the entity cannot go back.
     */
    public Optional<Room> goBack(Entity entity) {
        List<Room> history = locationHistory.get(entity);
        if (history == null || history.size() <= 1) {
            return Optional.empty();
        }

        // Remove the current room
        history.removeLast();
        // and return the previous one
        return Optional.of(history.getLast());
    }

    /**
     * Triggers the action of a Mob without causing recursion by preventing the mob from triggering its own action
     * while it is ongoing e.g. Enderman teleporting to other rooms.
     *
     * @param mob The mob whose action should be triggered.
     */
    private void triggerMobAction(Mob mob) {
        // Action already in process, don't run again
        if (currentlyProcessing.contains(mob)) {
            return;
        }

        try {
            currentlyProcessing.add(mob);
            mob.performAction();
        } finally {
            currentlyProcessing.remove(mob);
        }
    }

    /**
     * Removes the entity and stops tracking location.
     *
     * @param entity The entity to remove.
     */
    public void removeEntity(Entity entity) {
        locationHistory.remove(entity);
    }
}