import java.util.*;

/**
 * Abstract Room class representing common properties
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
 * Centralized Inventory (State Holder)
 */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // deliberately unavailable
        inventory.put("Suite Room", 2);
    }

    // READ-ONLY access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


/**
 * Search Service (READ-ONLY SERVICE)
 * Responsible only for retrieving and displaying available rooms
 */
class SearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("\nAvailable Rooms");
        System.out.println("--------------------------");

        for (Room room : rooms) {

            int availability = inventory.getAvailability(room.getRoomType());

            // Defensive check: show only available rooms
            if (availability > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + availability);
                System.out.println("--------------------------");
            }
        }
    }
}


/**
 * Application Entry Point
 */
public class HotelBookingApplication {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");

        // Room domain objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Inventory (state holder)
        RoomInventory inventory = new RoomInventory();

        // Search service (read-only)
        SearchService searchService = new SearchService();

        // Guest initiates search
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch completed. System state unchanged.");
    }
}