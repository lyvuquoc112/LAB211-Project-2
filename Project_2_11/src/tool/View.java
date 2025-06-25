/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;

import model.Guest;
import model.Room;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author hanly
 */
public class View {
    public static void showMenu() {
        System.out.println("\n=== ATZ RESORT MANAGEMENT SYSTEM ===");
        System.out.println("1. Import Room Data from Text File");
        System.out.println("2. Display Available Room List");
        System.out.println("3. Enter Guest Information");
        System.out.println("4. Update Guest Stay Information");
        System.out.println("5. Search Guest by National ID");
        System.out.println("6. Delete Guest Reservation Before Arrival");
        System.out.println("7. List Vacant Rooms");
        System.out.println("8. Monthly Revenue Report");
        System.out.println("9. Revenue Report by Room Type");
        System.out.println("10. Save Guest Information");
        System.out.println("0. Quit");
    }

    public static void showMessage(String msg) {
        System.out.println(msg);
    }

    public static void displayGuestInfo(Guest guest) {
        System.out.println("Guest: " + guest.getFullName() + ", ID: " + guest.getNationalId());
        // Thêm các thông tin khác nếu cần
    }

    public static void displayRoomInfo(Room room) {
        System.out.println("Room: " + room.getRoomId() + ", Name: " + room.getRoomName() +
                ", Type: " + room.getRoomType() + ", Rate: " + room.getDailyRate() +
                ", Capacity: " + room.getCapacity() + ", Furniture: " + room.getFurnitureDescription());
    }

    public static void displayRoomList(Collection<Room> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("Room list is currently empty, not loaded yet");
        } else {
            System.out.println("\nActive Room List");
            System.out.printf("%-8s|%-20s|%-18s|%-8s|%-10s|%-30s\n", "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
            System.out.println("--------+--------------------+------------------+--------+----------+-------------------------------------------------");
            for (Room room : rooms) {
                System.out.printf("%-8s|%-20s|%-18s|%8.1f|%10d|%-30s\n",
                        room.getRoomId(),
                        room.getRoomName(),
                        room.getRoomType(),
                        room.getDailyRate(),
                        room.getCapacity(),
                        room.getFurnitureDescription());
            }
        }
    }

    public static void displayVacantRooms(Collection<Room> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No vacant rooms available.");
        } else {
            System.out.println("\nAvailable Room List");
            System.out.printf("%-8s|%-20s|%-18s|%-8s|%-10s|%-30s\n", "RoomID", "RoomName", "Type", "Rate", "Capacity", "Furniture");
            System.out.println("--------+--------------------+------------------+--------+----------+-------------------------------------------------");
            for (Room room : rooms) {
                System.out.printf("%-8s|%-20s|%-18s|%8.1f|%10d|%-30s\n",
                        room.getRoomId(),
                        room.getRoomName(),
                        room.getRoomType(),
                        room.getDailyRate(),
                        room.getCapacity(),
                        room.getFurnitureDescription());
            }
        }
    }

    public static void displayMonthlyRevenueReport(String targetMonth, Map<String, Double> revenueByRoom, Map<String, Room> roomMap) {
        System.out.println("\nMonthly Revenue Report - '" + targetMonth + "'");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-8s|%-20s|%-12s|%-10s|%10s\n",
                "RoomID", "Room Name", "Room type", "DailyRate", "Amount");
        System.out.println("----------------------------------------------------------------");
        if (revenueByRoom.isEmpty()) {
            System.out.println("There is no data on guests who have rented rooms");
        } else {
            for (String roomId : revenueByRoom.keySet()) {
                Room room = roomMap.get(roomId);
                System.out.printf("%-8s|%-20s|%-12s|%10.2f|%10.2f\n",
                        room.getRoomId(),
                        room.getRoomName(),
                        room.getRoomType(),
                        room.getDailyRate(),
                        revenueByRoom.get(roomId));
            }
        }
        System.out.println("----------------------------------------------------------------");
    }

    public static void displayRevenueByRoomType(String roomType, double totalRevenue) {
        System.out.println("\nRevenue Report by Room Type");
        System.out.println("----------------------------");
        System.out.printf("%-12s|%10s\n", "Room type", "Amount");
        System.out.println("----------------------------");
        System.out.printf("%-12s|$%10.2f\n", roomType, totalRevenue);
        System.out.println("----------------------------");
    }
}
