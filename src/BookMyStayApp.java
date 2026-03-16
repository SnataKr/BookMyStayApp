/**
 * Book My Stay Application
 * Demonstrates room initialization using abstraction and inheritance
 *
 * @author SnataKr
 * @version 2.0
 */

public class BookMyStayApp {

    // ABSTRACT CLASS
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

    // SINGLE ROOM CLASS
    static class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 200, 100.0);
        }
    }

    // DOUBLE ROOM CLASS
    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 350, 180.0);
        }
    }

    // SUITE ROOM CLASS
    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 500, 300.0);
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v2.0 =====");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details ---");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);

        System.out.println("\nThank you for using Book My Stay!");
    }
}
