import java.util.*;

public class LocationManager {
    public static final LocationManager INSTANCE = new LocationManager();

    private final Map<Entity, Deque<Room>> locationHistory = new HashMap<>();
    private final Set<Entity> currentlyProcessing = new HashSet<>();

    private LocationManager() {
    }

    public void spawn(Entity entity, Room startingRoom) {
        locationHistory.put(entity, new ArrayDeque<>(List.of(startingRoom)));
    }

    public Room getLocation(Entity entity) {
        return locationHistory.get(entity).getLast();
    }

    public List<Entity> getEntitiesInRoom(Room room) {
        return locationHistory.entrySet().stream()
                .filter(entry -> entry.getValue().getLast() == room)
                .map(Map.Entry::getKey)
                .toList();
    }


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
                .forEach(this::triggerActionSafely);

        // Move the entity
        locationHistory.get(entity).addLast(nextRoom);
        return Optional.of(nextRoom);
    }

    public Optional<Room> goBack(Entity entity) {
        Deque<Room> history = locationHistory.get(entity);
        if (history.size() <= 1) {
            return Optional.empty();
        }

        history.removeLast();
        return Optional.of(history.getLast());
    }

    private void triggerActionSafely(Mob mob) {
        if (currentlyProcessing.contains(mob)) {
            return; // Prevent reentrant calls
        }
        try {
            currentlyProcessing.add(mob);
            mob.performAction();
        } finally {
            currentlyProcessing.remove(mob);
        }
    }

    public void removeEntity(Entity entity) {
        locationHistory.remove(entity);
    }
}