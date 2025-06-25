/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import model.Guest;
import model.Room;
import tool.Inputter;

/**
 *
 * @author hanly
 */
public class RoomManager extends HashSet<Room> {

    private String pathFile;
    private boolean isDataLoaded = false;
    private static RoomManager instance;

    public RoomManager(String pathFile) {
        super();
        try {
            this.pathFile = pathFile;
        } catch (Exception e) {
        }
    }

    public static RoomManager getInstance(String pathFile) {
        if (instance == null) {
            instance = new RoomManager(pathFile);
        }
        return instance;
    }

    public void loadRoomDataFromFile() {
        if (isDataLoaded) { 
            System.out.println("Room data has already been loaded.");
            return;
        }

        int successCount = 0;
        int failCount = 0;

        File f = new File(pathFile);
        if (!f.exists()) {
            System.out.println("File not found");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (validateAndAddRoom(line)) {
                    successCount++;
                } else {
                    failCount++;
                }
            }
            System.out.println(successCount + " rooms successfully loaded.");
            System.out.println(failCount + " entries failed.");
            isDataLoaded = true;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private boolean validateAndAddRoom(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length != 6) {
                return false;
            }

            String roomId = parts[0].trim();
            String roomName = parts[1].trim();
            String roomType = parts[2].trim();
            double dailyRate = Double.parseDouble(parts[3].trim());
            int capacity = Integer.parseInt(parts[4].trim());
            String furnitureDescription = parts[5].trim();

            if (dailyRate <= 0 || !parts[3].contains(".")|| capacity <= 0) {
                return false;
            }
            
            if (isRoomExists(roomId)) {
                return false;
            }
            return this.add(new Room(roomId, roomName, roomType, dailyRate, capacity, furnitureDescription));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isRoomExists(String roomId) {
        for (Room thi : this) {
            if (thi.getRoomId().equalsIgnoreCase(roomId.trim())) {
                return true;
            }
        }
        return false;
    }

    public Room getRoomById(String roomId) {
        for (Room thi : this) {
            if (thi.getRoomId().equalsIgnoreCase(roomId.trim())) {
                return thi;
            }
        }
        return null;
    }

    public void displayAvailableRoomList() {
        if (this.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet");
        } else {
            System.out.println("\nActive Room List");
            System.out.printf("%-8s|%-20s|%-18s|%-8s|%-10s|%-30s\n", "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture  ");
            System.out.println("--------+--------------------+------------------+--------+----------+-------------------------------------------------");
            for (Room thi : this) {
                System.out.printf("%-8s|%-20s|%-18s|%8.1f|%10d|%-30s\n",
                        thi.getRoomId(),
                        thi.getRoomName(),
                        thi.getRoomType(),
                        thi.getDailyRate(),
                        thi.getCapacity(),
                        thi.getFurnitureDescription());
            }
        }
    }

    public void displayVacantRooms(GuestManager guestManager) {
        if (this.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet");
            return;
        }

        // Tạo một HashSet chứa các phòng đang được thuê
        HashSet<String> rentedRoomIds = new HashSet<>();
        for (Guest guest : guestManager) {
            rentedRoomIds.add(guest.getRoomId());
        }

        // Đếm số phòng trống
        int vacantCount = 0;

        // Hiển thị tiêu đề
        System.out.println("\nAvailable Room List");
        System.out.printf("%-8s|%-20s|%-18s|%-8s|%-10s|%-30s\n",
                "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
        System.out.println("--------+--------------------+------------------+--------+----------+-------------------------------------------------");

        // Duyệt qua tất cả phòng và hiển thị những phòng chưa được thuê
        for (Room room : this) {
            // phòng nào thuê rồi thì ko in ra
            if (!rentedRoomIds.contains(room.getRoomId())) {
                System.out.printf("%-8s|%-20s|%-18s|%8.1f|%10d|%-30s\n",
                        room.getRoomId(),
                        room.getRoomName(),
                        room.getRoomType(),
                        room.getDailyRate(),
                        room.getCapacity(),
                        room.getFurnitureDescription());
                vacantCount++;
            }
        }

        // Kiểm tra nếu không có phòng trống
        if (vacantCount == 0) {
            System.out.println("\nAll rooms have currently been rented out; no rooms are available");
        }
    }
}
