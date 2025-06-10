/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import model.Room;

/**
 *
 * @author hanly
 */
public class RoomManager extends HashSet<Room> {

    private String pathFile;

    public RoomManager(String pathFile) {
        super();
        try {
            this.pathFile = pathFile;
            //this.loadRoomDataFromFile();
        } catch (Exception e) {
        }
    }

    public void loadRoomDataFromFile() {
        int successCount = 0;
        int failCount = 0;

        File f = new File(pathFile);
        if (!f.exists()) {
            System.out.println("File not found");
            return;
        }
        try ( BufferedReader br = new BufferedReader(new FileReader(f))) {
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

            // Validate daily rate and capacity
            if (dailyRate <= 0 || capacity <= 0) {
                return false;
            }

            Room room = new Room(roomId, roomName, roomType, dailyRate, capacity, furnitureDescription);

            // Sử dụng add() của HashSet
            return this.add(room);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isRoomExists(String roomId) {
        for (Room thi : this) {
            if (thi.getRoomId().equalsIgnoreCase(roomId)) {
                return true;
            }
        }
        return false;
    }

    public Room getRoomById(String roomId) {
        for (Room thi : this) {
            if (thi.getRoomId().equalsIgnoreCase(roomId)) {
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

}
