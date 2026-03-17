/**
 * Hotel Booking Application
 * Demonstrates abstraction, inheritance, polymorphism,
 * and simple availability management without data structures.
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

    // Abstract method
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
 * Application Entry Point
 */
public class HotelBookingApplication {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");
        System.out.println("Available Room Types\n");

        // Polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Availability variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Display Single Room
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailability);
        System.out.println("----------------------------");

        // Display Double Room
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailability);
        System.out.println("----------------------------");

        // Display Suite Room
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailability);
        System.out.println("----------------------------");

        System.out.println("Application terminated.");
    }
}