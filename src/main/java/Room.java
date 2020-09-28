package CourseScheduling.src.main.java;

/**
 * Room class. This class stores information about available rooms
 *
 * @author Ed Brown
 * @version 0.1
 * @since 9/27/2020
 */
public class Room {
    //variables
    private String roomNumber; //classroom number
    private int roomCapacity; //maximum number of student capacity

    /**
     * Default Constructor
     */
    public Room(){
        this.roomNumber = null;
        this.roomCapacity = -1;
    }

    /**
     * Overload constructor
     * @param roomNumber room number of classroom
     */
    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
        this.roomCapacity = -1;
    }

    /**
     * Overload constructor
     * @param roomNumber room number of classroom
     * @param roomCapacity maximum number of desks in room
     */
    public Room(String roomNumber, int roomCapacity) {
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
    }

    /**
     * Room number getter
     * @return string of the room number
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Room number setter
     * @param roomNumber room number of classroom
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Room Capacity getter
     * @return string of the capacity of the room
     */
    public int getRoomCapacity() {
        return roomCapacity;
    }

    /**
     * Room capacity setter
     * @param roomCapacity capacity of the room
     */
    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    /**
     * This function prints values of this class to the console
     */
    public void printRoom(){
        System.out.println("Room '" + this.roomNumber +"' has a maximum capacity of " + this.roomCapacity);
    }
}
