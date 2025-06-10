/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import business.GuestManager;
import business.RoomManager;
import java.util.Scanner;
import tool.Inputter;

/**
 *
 * @author hanly
 */
public class Main {
    
    
    public static final int CONTINUE = 1;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RoomManager manager = new RoomManager("D:\\FPT\\Ky 3\\LAB211\\SE1806-LAB211-main\\Set14_SU25\\De_LAB211\\02_J1.L.P0030.RoomManagementModule_500LOC\\Active_Room_List.txt");
        GuestManager guestManager = new GuestManager();
        Inputter inputter = new Inputter();
        while (true) {
            // Hiển thị menu
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
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng new line
            
            switch (choice) {
                case 1:
                    System.out.println("\n=== Import Room Data ===");
                    manager.loadRoomDataFromFile();
                    break;
                    
                case 2:
                    manager.displayAvailableRoomList();
                    break;
                    
                case 3:
                    int option;
                    do {                        
                        guestManager.addNew(inputter.inputGuest(false));
                        System.out.println("1. Continue entering customer information");
                        System.out.println("2. Back to main menu");
                        option = Integer.parseInt(inputter.input("Chose your option", "Option must be 1 or 2", "^[12]$"));
                    } while (option == CONTINUE);
                    // TODO: Implement enter guest information
                   
                    break;
                    
                case 4:
                    System.out.println("\n=== Update Guest Stay Information ===");
                    // TODO: Implement update guest information
                    System.out.println("Function not implemented yet");
                    break;
                    
                case 5:
                    System.out.println("\n=== Search Guest by National ID ===");
                    // TODO: Implement search guest
                    System.out.println("Function not implemented yet");
                    break;
                    
                case 6:
                    System.out.println("\n=== Delete Guest Reservation ===");
                    // TODO: Implement delete reservation
                    System.out.println("Function not implemented yet");
                    break;
                    
                case 7:
                    System.out.println("\n=== List Vacant Rooms ===");
                    // TODO: Implement list vacant rooms
                    System.out.println("Function not implemented yet");
                    break;
                    
                case 8:
                    System.out.println("\n=== Monthly Revenue Report ===");
                    // TODO: Implement monthly revenue report
                    System.out.println("Function not implemented yet");
                    break;
                    
                case 9:
                    System.out.println("\n=== Revenue Report by Room Type ===");
                    // TODO: Implement revenue by room type
                    System.out.println("Function not implemented yet");
                    break;
                    
                case 10:
                    System.out.println("\n=== Save Guest Information ===");
                    // TODO: Implement save guest information
                    System.out.println("Function not implemented yet");
                    break;
                    
                case 0:
                    System.out.println("\nThank you for using ATZ Resort Management System!");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("\nInvalid choice! Please try again.");
            }
        }
    }
}
