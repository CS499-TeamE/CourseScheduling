package model;

/**
 * Room class. This class stores information about available rooms
 *
 * @author Ed Brown
 * @version 0.1
 * @since 9/27/2020
 */
public class Room {
    //variables
    private int roomNumber; //classroom number
    private String building; //building classroom is in
    private int roomCapacity; //maximum number of student capacity

    /**
     * Default Constructor
     */
    public Room(){
        this.roomNumber = -1;
        this.roomCapacity = -1;
        this.building = null;
    }

    /**
     * Overload constructor
     * @param roomNumber room number of classroom
     * @param roomCapacity maximum number of desks in room
     */
    public Room(String build, int roomNumber, int roomCapacity) {
        this.roomNumber = roomNumber;
        this.roomCapacity = roomCapacity;
        this.building = build;
    }

    /**
     * Room number getter
     * @return string of the room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Room number setter
     * @param roomNumber room number of classroom
     */
    public void setRoomNumber(int roomNumber) {
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
     * Getter for building
     * @return String representing building classroom is in
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Setter for building
     * @param building  String representing building classroom is in
     */
    public void setBuilding(String building) {
        this.building = building;
    }

    /**
     * This function prints values of this class to the console for debugging
     */
    public void printRoom() {
        System.out.println("Room '" + this.building + " " + this.roomNumber + "' has a maximum capacity of " + this.roomCapacity);
    }

    @Override
    public String toString() {
        if (this.getRoomNumber() == 0) {
            return "No Preference";
        }
        return String.valueOf(this.getRoomNumber());
    }


}