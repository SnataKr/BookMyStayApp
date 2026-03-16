import java.util.*;

class Reservation {

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

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}

class RoomInventory {

    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public int getAvailableRooms(String type) {
        return rooms.getOrDefault(type, 0);
    }

    public void decrementRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void printInventory() {
        System.out.println("\nRemaining Inventory:");
        System.out.println("Single: " + rooms.get("Single"));
        System.out.println("Double: " + rooms.get("Double"));
        System.out.println("Suite: " + rooms.get("Suite"));
    }
}

class RoomAllocationService {

    private Map<String, Integer> counters = new HashMap<>();

    public RoomAllocationService() {
        counters.put("Single", 0);
        counters.put("Double", 0);
        counters.put("Suite", 0);
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        if (inventory.getAvailableRooms(type) <= 0) {
            System.out.println("No room available for " + reservation.getGuestName());
            return;
        }

        inventory.decrementRoom(type);

        int id = counters.get(type) + 1;
        counters.put(type, id);

        String roomId = type + "-" + id;

        System.out.println("Booking confirmed for Guest: " +
                reservation.getGuestName() +
                ", Room ID: " + roomId);
    }
}

class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (!bookingQueue.hasPendingRequests())
                    break;

                reservation = bookingQueue.getNextRequest();
            }

            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue, inventory, allocationService
                )
        );

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        inventory.printInventory();
    }
}