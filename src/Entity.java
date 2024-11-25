import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private final String name;
    private final List<Room> roomHistory;
    public Inventory inventory;
    private Room currentRoom;

    public Entity(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.roomHistory = new ArrayList<>();

        roomHistory.add(startingRoom);
    }

    public void initInventory(List<Item> initialItems, int maxWeight) {
        this.inventory = new Inventory(initialItems, maxWeight);
    }

    public void travel(Room destination) {
        roomHistory.add(destination);
        currentRoom = destination;
    }

    public Room goBack() {
        if (roomHistory.isEmpty()) {
            return null;
        }

        return roomHistory.removeLast();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void die() {
        // Drop all items into room in which Entity has died in
        currentRoom.inventory.addItems(inventory.getAllItems());
        inventory.clear();
    }

    public String getName() {
        return name;
    }
}
