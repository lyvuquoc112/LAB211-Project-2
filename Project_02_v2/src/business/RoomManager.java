package business;

import model.Room;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomManager {
    private static final String FILE_PATH = "D:\\FPT\\Ky 3\\LAB211\\SE1806-LAB211-main\\Set14_SU25\\De_LAB211\\02_J1.L.P0030.RoomManagementModule_500LOC\\Active_Room_List.txt";
    private List<Room> rooms;
    private Set<String> roomIds;

    public RoomManager() {
        this.rooms = new ArrayList<>();
        this.roomIds = new HashSet<>();
    }

    public boolean importRoomsFromFile() {
        int successCount = 0;
        int failCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Room room = parseRoomLine(line);
                    if (validateRoom(room)) {
                        rooms.add(room);
                        roomIds.add(room.getRoomId());
                        successCount++;
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    failCount++;
                }
            }
            System.out.println(successCount + " rooms successfully loaded.");
            System.out.println(failCount + " entries failed.");
            return true;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    private Room parseRoomLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid line format");
        }

        String roomId = parts[0].trim();
        String roomName = parts[1].trim();
        String roomType = parts[2].trim();
        double dailyRate = Double.parseDouble(parts[3].trim());
        int capacity = Integer.parseInt(parts[4].trim());
        String furnitureDescription = parts[5].trim();

        return new Room(roomId, roomName, roomType, dailyRate, capacity, furnitureDescription);
    }

    private boolean validateRoom(Room room) {
        // Check if roomId is unique
        if (roomIds.contains(room.getRoomId())) {
            return false;
        }

        // Check if dailyRate is positive
        if (room.getDailyRate() <= 0) {
            return false;
        }

        // Check if capacity is positive
        if (room.getCapacity() <= 0) {
            return false;
        }

        return true;
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }
} 