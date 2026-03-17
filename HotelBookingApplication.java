import java.util.HashMap;
import java.util.Map;

/**
 * Abstract representation of a Room in the hotel.
 * Defines common attributes shared by all room types.
 *
 * @author Madhav
 * @version 1.0
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

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price per night: $" + price);
    }
}


/**
 * Single Room Implementation
 */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 80);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}


/**
 * Double Room Implementation
 */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 120);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}


/**
 * Suite Room Implementation
 */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 250);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}


/**
 * Centralized Inventory Management
 * Uses HashMap to maintain availability of room types.
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    /**
     * Constructor initializes room availability
     */
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    /**
     * Get availability for a room type
     */
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    /**
     * Update availability for a room type
     */
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }

    /**
     * Display full inventory
     */
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory");
        System.out.println("--------------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " available");
        }
    }
}


/**
 * Application Entry Point
 */
public class HotelBookingApplication {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");
        System.out.println("--------------------------");

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display room details
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability(singleRoom.getRoomType()));
        System.out.println("--------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability(doubleRoom.getRoomType()));
        System.out.println("--------------------------");

        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability(suiteRoom.getRoomType()));
        System.out.println("--------------------------");

        // Display centralized inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}