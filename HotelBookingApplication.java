import java.util.*;

/**
 * Abstract Room class
 */
abstract class Room {

    private int numberOfBeds;
    private int size;
    private double price;

    public Room(int numberOfBeds, int size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public abstract String getRoomType();
}


/**
 * Concrete Room Types
 */
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 200, 80);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 350, 120);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 600, 250);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}


/**
 * Reservation class (Booking Request)
 * Represents guest intent
 */
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}


/**
 * Booking Request Queue (FIFO)
 */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    /**
     * Add booking request (enqueue)
     */
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    /**
     * Display all queued requests
     */
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue (FIFO Order)");
        System.out.println("----------------------------------");

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }

    /**
     * Peek next request (no removal)
     */
    public Reservation peekNext() {
        return queue.peek();
    }
}


/**
 * Application Entry Point
 */
public class HotelBookingApplication {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");
        System.out.println("Booking Request Intake System\n");

        // Initialize booking queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulating guest booking requests
        Reservation r1 = new Reservation("Madhav", "Single Room");
        Reservation r2 = new Reservation("Amit", "Suite Room");
        Reservation r3 = new Reservation("Neha", "Double Room");

        // Add requests to queue (FIFO)
        requestQueue.addRequest(r1);
        requestQueue.addRequest(r2);
        requestQueue.addRequest(r3);

        // Display queue
        requestQueue.displayQueue();

        // Show next request (without removing)
        System.out.println("\nNext request to process:");
        Reservation next = requestQueue.peekNext();
        if (next != null) {
            next.displayReservation();
        }

        System.out.println("\nNo inventory updated. Requests are waiting for processing.");
    }
}