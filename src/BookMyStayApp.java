import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 4: Room Search (Read-only access to inventory)
 *
 * Demonstrates:
 * - Retrieving room availability without modifying inventory
 * - Displaying only available rooms
 * - Using room domain objects for details and pricing
 * - Clear separation between search logic and booking logic
 *
 * @author SnataKr
 * @version 4.0
 */

public class BookMyStayApp {

    // ABSTRACT ROOM CLASS
    static abstract class Room {
        String roomType;
        int beds;
        int size;
        double price;

        public Room(String roomType, int beds, int size, double price) {
            this.roomType = roomType;
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        public void displayRoomDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sq.ft");
            System.out.println("Price per night: $" + price);
        }
    }

    // CONCRETE ROOM TYPES
    static class SingleRoom extends Room {
        public SingleRoom() { super("Single Room", 1, 200, 100.0); }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() { super("Double Room", 2, 350, 180.0); }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() { super("Suite Room", 3, 500, 300.0); }
    }

    // CENTRALIZED INVENTORY
    static class RoomInventory {
        private Map<String, Integer> inventory;

        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);
        }

        // Read-only access for search
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        // Booking updates inventory
        public void updateAvailability(String roomType, int count) {
            inventory.put(roomType, count);
        }
    }

    // SEARCH LOGIC (read-only)
    static void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {
        System.out.println("\n--- Available Rooms ---");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.roomType);
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println();
            }
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v4.0 =====");

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search available rooms (read-only)
        searchAvailableRooms(inventory, rooms);

        // Example booking (updates inventory)
        System.out.println("Booking a Single Room...");
        int current = inventory.getAvailability("Single Room");
        inventory.updateAvailability("Single Room", current - 1);

        // Show updated availability after booking
        System.out.println("\n--- Inventory After Booking ---");
        searchAvailableRooms(inventory, rooms);

        System.out.println("\nThank you for using Book My Stay!");
    }
}