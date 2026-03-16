import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay Application
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates:
 * - Handling guest booking requests fairly
 * - Using a Queue to preserve arrival order
 * - Decoupling request intake from inventory allocation
 *
 * @author SnataKr
 * @version 5.0
 */

public class BookMyStayApp {

    // REPRESENTS A GUEST BOOKING REQUEST
    static class Reservation {
        String guestName;
        String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public void displayReservation() {
            System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
        }
    }

    // BOOKING REQUEST QUEUE
    static class BookingRequestQueue {
        private Queue<Reservation> queue;

        public BookingRequestQueue() {
            queue = new LinkedList<>();
        }

        // Add a new booking request
        public void addRequest(Reservation reservation) {
            queue.add(reservation);
            System.out.println("Booking request added for " + reservation.guestName);
        }

        // Display all queued requests
        public void displayRequests() {
            System.out.println("\n--- Current Booking Requests (FIFO) ---");
            for (Reservation r : queue) {
                r.displayReservation();
            }
        }

        // Get next request without removing (peek)
        public Reservation peekNextRequest() {
            return queue.peek();
        }

        // Remove and return the next request (for future allocation)
        public Reservation processNextRequest() {
            return queue.poll();
        }

        // Check if queue is empty
        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        System.out.println("===== Book My Stay App v5.0 =====");

        // Initialize booking request queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulate guests submitting booking requests
        requestQueue.addRequest(new Reservation("Alice", "Single Room"));
        requestQueue.addRequest(new Reservation("Bob", "Double Room"));
        requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
        requestQueue.addRequest(new Reservation("Diana", "Single Room"));

        // Display queued requests
        requestQueue.displayRequests();

        // Peek at the next request to be processed
        Reservation next = requestQueue.peekNextRequest();
        System.out.println("\nNext request to process:");
        if (next != null) {
            next.displayReservation();
        }

        System.out.println("\nAll booking requests are collected and ready for allocation.");
    }
}