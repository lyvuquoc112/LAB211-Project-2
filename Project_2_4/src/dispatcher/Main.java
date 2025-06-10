/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import business.GuestManager;
import business.RoomManager;
import java.util.Scanner;
import model.Guest;
import tool.Inputter;
import tool.Validator;

/**
 *
 * @author hanly
 */
public class Main {
    
    
    public static final int CONTINUE = 1;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RoomManager roomManager = new RoomManager("D:\\FPT\\Ky 3\\LAB211\\SE1806-LAB211-main\\Set14_SU25\\De_LAB211\\02_J1.L.P0030.RoomManagementModule_500LOC\\Active_Room_List.txt");
        GuestManager guestManager = new GuestManager();
        Inputter inputter = new Inputter();
        int option = CONTINUE;
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
                    roomManager.loadRoomDataFromFile();
                    break;
                    
                case 2:
                    roomManager.displayAvailableRoomList();
                    break;
                    
                case 3:
                    do {                        
                        guestManager.addNew(inputter.inputGuest(false,null));
                        System.out.println("1. Continue entering customer information");
                        System.out.println("2. Back to main menu");
                        option = Integer.parseInt(inputter.input("Chose your option", "Option must be 1 or 2", "^[12]$"));
                    } while (option == CONTINUE);
                    // TODO: Implement enter guest information
                   
                    break;
                    
                case 4:
                    System.out.println("\n=== Update Guest Stay Information ===");
                    String ID = inputter.input("Input National ID", "National ID must be exactly 12 digits", Validator.NATIONAL_ID_PATTERN);
                    Guest guest = guestManager.findGuestById(ID);
                    if(guest==null){
                        System.out.println("No guest found with the requested ID!");
                    }else{
                        Guest updatedGuset = inputter.inputGuest(true, guest);
                        if(updatedGuset!=null){
                            guestManager.update(updatedGuset);
                            System.out.println("Update successful");
                            guestManager.displayGuestInfo(updatedGuset);
                        }else{
                            System.out.println("Update failed");
                        }
                    }
                    break;
                    
                case 5:
                    do {                        
                        System.out.println("Enter National ID: ");
                        String nationalID = scanner.nextLine();
                        Guest g = guestManager.findGuestById(nationalID);
                        if(g != null){
                            guestManager.displayGuestAndRoomInformation(g, roomManager);
                        }else{
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println("No guest found with the requested ID '" + nationalID + "'");
                        }
                        System.out.println("1. Continue entering customer information");
                        System.out.println("2. Back to main menu");
                        option = Integer.parseInt(inputter.input("Chose your option", "Option must be 1 or 2", "^[12]$"));
                    } while (option == CONTINUE);
                    break;
                    
                case 6:
                    do {                        
                        System.out.println("Enter National ID: ");
                        String nationalID = scanner.nextLine();
                        Guest gues = guestManager.findGuestById(nationalID);
                        if(gues==null){
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println("No guest found with the requested ID '" + nationalID + "'");
                        }else if(!guestManager.canCancelReservation(gues)){
                            System.out.println("--------------------------------------------------------------------");
                            System.out.println("The room booking for this guest cannot be cancelled !");
                        }else{
                            guestManager.displayGuestAndRoomInformation(gues, roomManager);
                            System.out.println("--------------------------------------------------------------------");
                            String confirmation = inputter.input("Do you really want to cancel this guest's room booking ? [Y/N]: ", "Only Y or N", "^[YyNn]$");
                            if(confirmation.equalsIgnoreCase("Y")){
                                guestManager.deleteGuestReservation(gues);
                                System.out.println("The booking associated with ID " + nationalID + " has been successfully canceled.");
                            }else{
                                 System.out.println("Booking cancellation cancelled.");
                            }
                        }
                        System.out.println("1. Continue entering customer information");
                        System.out.println("2. Back to main menu");
                        option = Integer.parseInt(inputter.input("Chose your option", "Option must be 1 or 2", "^[12]$"));
                    } while (option == CONTINUE);
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
