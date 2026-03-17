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

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: $" + price);
    }
}


/**
 * Concrete Room Types
 */
class SingleRoom extends Room {
    public SingleRoom() { super(1, 200, 80); }
    public String getRoomType() { return "Single Room"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super(2, 350, 120); }
    public String getRoomType() { return "Double Room"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super(3, 600, 250); }
    public String getRoomType() { return "Suite Room"; }
}


/**
 * Reservation represents a guest booking request
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested: " + roomType);
    }
}


/**
 * Centralized Inventory (Tracks availability)
 */
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean decrementAvailability(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
    }
}


/**
 * Booking Request Queue (FIFO)
 */
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() { queue = new LinkedList<>(); }

    public void addRequest(Reservation r) {
        queue.offer(r);
        System.out.println("Request added for " + r.getGuestName());
    }

    public Reservation getNextRequest() { return queue.poll(); }

    public boolean isEmpty() { return queue.isEmpty(); }
}


/**
 * Booking Service: confirms reservations safely
 */
class BookingService {

    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms; // roomType -> assigned room IDs
    private int roomIdCounter = 100; // starting room ID

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
    }

    /**
     * Process a booking request safely
     */
    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) > 0) {
            // Generate unique room ID
            String roomId = generateUniqueRoomId(roomType);

            // Decrement inventory immediately
            boolean success = inventory.decrementAvailability(roomType);

            if (success) {
                // Record allocation
                allocatedRooms.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

                // Confirm reservation
                System.out.println("Reservation Confirmed:");
                System.out.println("Guest: " + reservation.getGuestName());
                System.out.println("Room Type: " + roomType);
                System.out.println("Assigned Room ID: " + roomId);
                System.out.println("------------------------------");
            }
        } else {
            System.out.println("Sorry " + reservation.getGuestName() + ", " + roomType + " is fully booked.");
            System.out.println("------------------------------");
        }
    }

    private String generateUniqueRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + (++roomIdCounter);
    }

    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}


/**
 * Application Entry Point
 */
public class HotelBookingApplication {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay - Booking Processor\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Simulate guest requests
        requestQueue.addRequest(new Reservation("Madhav", "Single Room"));
        requestQueue.addRequest(new Reservation("Amit", "Suite Room"));
        requestQueue.addRequest(new Reservation("Neha", "Double Room"));
        requestQueue.addRequest(new Reservation("Ravi", "Suite Room"));
        requestQueue.addRequest(new Reservation("Anjali", "Suite Room")); // exceeds inventory

        // Initialize booking service
        BookingService bookingService = new BookingService(inventory);

        // Process queued requests
        while (!requestQueue.isEmpty()) {
            Reservation next = requestQueue.getNextRequest();
            bookingService.processReservation(next);
        }

        // Display final inventory and allocations
        inventory.displayInventory();
        bookingService.displayAllocatedRooms();

        System.out.println("\nAll booking requests processed safely.");
    }
}
