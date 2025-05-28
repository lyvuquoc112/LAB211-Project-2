package dispatcher;

import business.RoomManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== Room Management System ===");
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
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    roomManager.importRoomsFromFile();
                    break;
                case 2:
                    roomManager.displayAvailableRooms();
                    break;
                case 3:
                    System.out.println("Enter Guest Information - Function not implemented yet");
                    break;
                case 4:
                    System.out.println("Update Guest Stay Information - Function not implemented yet");
                    break;
                case 5:
                    System.out.println("Search Guest by National ID - Function not implemented yet");
                    break;
                case 6:
                    System.out.println("Delete Guest Reservation Before Arrival - Function not implemented yet");
                    break;
                case 7:
                    System.out.println("List Vacant Rooms - Function not implemented yet");
                    break;
                case 8:
                    System.out.println("Monthly Revenue Report - Function not implemented yet");
                    break;
                case 9:
                    System.out.println("Revenue Report by Room Type - Function not implemented yet");
                    break;
                case 10:
                    System.out.println("Save Guest Information - Function not implemented yet");
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
} 