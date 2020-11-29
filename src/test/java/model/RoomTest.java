package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the Room Class
 *
 * @author Ed Brown
 */
class RoomTest {
    @Test
    void testDefaultConstructor() {
        Room r = new Room();
        assertEquals(-1,r.getRoomNumber());
        assertEquals(-1,r.getRoomCapacity());
        assertEquals(null, r.getBuilding());
    }

    @Test
    void testOverloadConstructor() {
        Room r = new Room("Building",12,200);
        assertEquals(12,r.getRoomNumber());
        assertEquals(200,r.getRoomCapacity());
        assertEquals("Building", r.getBuilding());
    }

    @Test
    void testRoomNumber() {
        Room r = new Room();
        for(int i=-10; i<100; i++){
            r.setRoomNumber(i);
            assertEquals(i,r.getRoomNumber());
        }
    }

    @Test
    void testRoomCapacity() {
        Room r = new Room();
        for(int i=-10; i<100; i++){
            r.setRoomCapacity(i);
            assertEquals(i,r.getRoomCapacity());
        }
    }

    @Test
    void testBuilding() {
        Room r = new Room();
        String[] names = {"Tech Hall", "Test", "1234", "RandomPlace"};
        for(String building : names){
            r.setBuilding(building);
            assertEquals(building, r.getBuilding());
        }
    }
}