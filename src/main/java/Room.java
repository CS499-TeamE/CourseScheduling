/**
 * Room class that contains room information, such as Room Number
 *
 * @author Youssef Sleiman
 */
public class Room {
    /**
     * Class Variable
     */
    private int roomNum;

    /**
     * Default constructor. Does not set room number.
     */
    public Room() {}

    /**
     * Overloaded constructor for if the room number is known
     * @param roomNum integer that represents the room number
     */
    public Room(int roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * Returns the value of the room number
     * @return an integer that represents the room number
     */
    public int getRoomNum() {
        return this.roomNum;
    }

    /**
     * Updates the value of the room number
     * @param roomNum integer that represents the room number
     */
    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }
}
