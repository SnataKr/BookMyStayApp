import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay Application
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates centralized inventory management using HashMap
 * with single source of truth and controlled updates.
 *
 * @author SnataKr
 * @version 3.1
 */

public class BookMyStayApp {

    // UC3: ROOM INVENTORY USING HASHMAP
    static class RoomInventory {

        private Map<String, Integer> inventory;

        // Constructor: initialize room availability
        public RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 2);
        }

        // Get availability for a room type
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        // Update availability for a room type
        public void updateAvailability(String roomType, int count) {
            inventory.put(roomType, count);
        }

        // Display current inventory
        public void displayInventory() {
            System.out.println("\n--- Current Room Inventory ---");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v3.1 =====");

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Example: Booking a Single Room
        System.out.println("\nBooking a Single Room...");
        int current = inventory.getAvailability("Single Room");
        inventory.updateAvailability("Single Room", current - 1);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nThank you for using Book My Stay!");
    }
}