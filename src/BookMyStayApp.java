import java.io.*;
import java.util.*;

class Reservation implements Serializable {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory implements Serializable {

    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void printInventory() {
        System.out.println("Current Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

class BookingHistory implements Serializable {

    private List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}

class SystemState implements Serializable {

    RoomInventory inventory;
    BookingHistory history;

    public SystemState(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "booking_state.dat";

    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Failed to save system state.");
        }
    }

    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored successfully.");
            return (SystemState) in.readObject();

        } catch (Exception e) {
            System.out.println("No valid persistence data found. Starting fresh.");
            return null;
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Data Persistence & System Recovery");

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.loadState();

        RoomInventory inventory;
        BookingHistory history;

        if (state == null) {

            inventory = new RoomInventory();
            history = new BookingHistory();

            history.addReservation(new Reservation("Abhi", "Single"));
            history.addReservation(new Reservation("Subha", "Double"));
            history.addReservation(new Reservation("Vanmathi", "Suite"));

        } else {

            inventory = state.inventory;
            history = state.history;
        }

        System.out.println("\nRecovered Booking History:");
        for (Reservation r : history.getReservations()) {
            System.out.println("Guest: " + r.getGuestName() +
                    ", Room Type: " + r.getRoomType());
        }

        System.out.println();
        inventory.printInventory();

        persistence.saveState(new SystemState(inventory, history));
    }
}