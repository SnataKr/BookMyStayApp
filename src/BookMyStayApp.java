import java.util.ArrayList;
import java.util.List;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting\n");

        List<Reservation> bookingHistory = new ArrayList<>();

        // Adding confirmed bookings
        bookingHistory.add(new Reservation("Abhi", "Single"));
        bookingHistory.add(new Reservation("Subha", "Double"));
        bookingHistory.add(new Reservation("Vanmathi", "Suite"));

        System.out.println("Booking History Report\n");

        for (Reservation r : bookingHistory) {
            System.out.println("Guest: " + r.guestName + ", Room Type: " + r.roomType);
        }
    }
}