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
public class Menu {

    private static final int CONTINUE = 1;
    private static final int IMPORT_ROOM_DATA_FROM_TEXT_FILE = 1;
    private static final int DISPLAY_AVAILABLE_ROOM_LIST = 2;
    private static final int ENTER_GUEST_INFORMATION = 3;
    private static final int UPDATE_GUEST_STAY_INFORMATION = 4;
    private static final int SEARCH_GUEST_BY_NATIONAL_ID = 5;
    private static final int DELETE_GUEST_RESERVATION = 6;
    private static final int LIST_VACANT_ROOMS = 7;
    private static final int MONTHLY_REVENUE_REPORT = 8;
    private static final int REVENUE_REPORT_BY_ROOM_TYPE = 9;
    private static final int SAVE_GUEST_INFORMATION = 10;
    private static final int QUIT = 0;

    private static Scanner scanner;
    private static RoomManager roomManager;
    private static GuestManager guestManager;
    private static Inputter inputter;

    public Menu(RoomManager roomManager, GuestManager guestManager) {
        this.scanner = new Scanner(System.in);
        this.roomManager = roomManager;
        this.guestManager = guestManager;
        this.inputter = new Inputter();
    }

    private static void showMenu() {
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

    private static int getMenuChoice() {
        int result = 0;
        boolean more = false;
        do {
            System.out.print("Enter Test Case No. : ");
            try {
                result = Integer.parseInt(scanner.nextLine());
                more = true;
            } catch (Exception e) {
                System.out.println("Accept only interger. Please Re-enters");

            }
        } while (!more);
        return result;
    }

    private static void handelImportRoomDataFromTextFile() {
        System.out.println("\n=== Import Room Data ===");
        roomManager.loadRoomDataFromFile();
    }

    private static void handelDisplayAvailableRoomList() {
        roomManager.displayAvailableRoomList();
    }

    private static void handelEnterGuestInformation() {
        int option;
        do {
            guestManager.addNew(inputter.inputGuest(false, null, roomManager, guestManager));
            System.out.println("1. Continue entering customer information");
            System.out.println("2. Back to main menu");
            option = Integer.parseInt(inputter.input("Chose your option", "Option must be 1 or 2", "^[12]$"));
        } while (option == CONTINUE);
    }

    private static void handelUpdateGuestStayInformation() {
        System.out.println("\n=== Update Guest Stay Information ===");
        String ID = inputter.input("Input National ID", "National ID must be exactly 12 digits", Validator.NATIONAL_ID_PATTERN);
        Guest guest = guestManager.findGuestById(ID);
        if (guest == null) {
            System.out.println("No guest found with the requested ID!");
        } else {
            Guest updatedGuset = inputter.inputGuest(true, guest, roomManager, guestManager);
            if (updatedGuset != null) {
                guestManager.update(updatedGuset);
                System.out.println("Update successful");
                guestManager.displayGuestInfo(updatedGuset);
            } else {
                System.out.println("Update failed");
            }
        }
    }

    private static void handelSearchGuestByNationalID() {
        int option;
        do {
            System.out.println("Enter National ID: ");
            String nationalID = scanner.nextLine();
            Guest g = guestManager.findGuestById(nationalID);
            if (g != null) {
                guestManager.displayGuestAndRoomInformation(g, roomManager);
            } else {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("No guest found with the requested ID '" + nationalID + "'");
                System.out.println("--------------------------------------------------------------------");

            }
            System.out.println("1. Continue entering customer information");
            System.out.println("2. Back to main menu");
            option = Integer.parseInt(inputter.input("Chose your option", "Option must be 1 or 2", "^[12]$"));
        } while (option == CONTINUE);
    }

    private static void handelDeleteGuestReservation() {
        int option;
        do {
            System.out.println("Enter National ID: ");
            String nationalID = scanner.nextLine();
            Guest gues = guestManager.findGuestById(nationalID);
            if (gues == null) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("No guest found with the requested ID '" + nationalID + "'");
            } else if (!guestManager.canCancelReservation(gues)) {
                System.out.println("--------------------------------------------------------------------");
                System.out.println("The room booking for this guest cannot be cancelled !");
            } else {
                guestManager.displayGuestAndRoomInformation(gues, roomManager);
                System.out.println("--------------------------------------------------------------------");
                String confirmation = inputter.input("Do you really want to cancel this guest's room booking ? [Y/N]: ", "Only Y or N", "^[YyNn]$");
                if (confirmation.equalsIgnoreCase("Y")) {
                    guestManager.deleteGuestReservation(gues);
                    System.out.println("The booking associated with ID " + nationalID + " has been successfully canceled.");
                } else {
                    System.out.println("Booking cancellation cancelled.");
                }
            }
            System.out.println("1. Continue entering customer information");
            System.out.println("2. Back to main menu");
            option = Integer.parseInt(inputter.input("Chose your option", "Option must be 1 or 2", "^[12]$"));
        } while (option == CONTINUE);
    }

    private static void handelListVacantRooms() {
        roomManager.displayVacantRooms(guestManager);
    }

    private static void handelMonthlyRevenueReport() {
        String targetMonth = inputter.input("Enter target month (MM/YYYY): ",
                "Invalid format. Please use MM/YYYY format.",
                "^(0[1-9]|1[0-2])/\\d{4}$");
        guestManager.displayMonthlyRevenueReport(targetMonth, roomManager);
    }

    private static void handelRevenueReportByRoomType() {
        String roomType = inputter.input("Enter room type (Deluxe/Standard/Suite/Superior): ",
                "Invalid room type! Please enter a valid room type.",
                "^(Deluxe|Standard|Suite|Superior)$");
        guestManager.displayRevenueByRoomType(roomType, roomManager);
    }

    private static void handelSaveGuestInformation() {
        guestManager.saveGuestInfo();
    }

    private static void handelQuit() {
        System.out.println("\nThank you for using ATZ Resort Management System!");
    }

    private static void processMenuChoice(int testCase) {
        switch (testCase) {
            case IMPORT_ROOM_DATA_FROM_TEXT_FILE:
                handelImportRoomDataFromTextFile();
                break;
            case DISPLAY_AVAILABLE_ROOM_LIST:
                handelDisplayAvailableRoomList();
                break;
            case ENTER_GUEST_INFORMATION:
                handelEnterGuestInformation();
                break;
            case UPDATE_GUEST_STAY_INFORMATION:
                handelUpdateGuestStayInformation();
                break;
            case SEARCH_GUEST_BY_NATIONAL_ID:
                handelSearchGuestByNationalID();
                break;
            case DELETE_GUEST_RESERVATION:
                handelDeleteGuestReservation();
                break;
            case LIST_VACANT_ROOMS:
                handelListVacantRooms();
                break;
            case MONTHLY_REVENUE_REPORT:
                handelMonthlyRevenueReport();
                break;
            case REVENUE_REPORT_BY_ROOM_TYPE:
                handelRevenueReportByRoomType();
                break;
            case SAVE_GUEST_INFORMATION:
                handelSaveGuestInformation();
                break;
            case QUIT:
                handelQuit();
                break;
            default:
                System.out.println("\nInvalid choice! Please try again.");
        }
    }

    public void run() {
        int testCase = QUIT;
        do {
            showMenu();
            testCase = getMenuChoice();
            processMenuChoice(testCase);
        } while (testCase != QUIT);
    }
    
    public void close() {
        scanner.close();
    }
}
