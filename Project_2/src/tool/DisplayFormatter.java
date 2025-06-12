/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import model.Guest;
import model.Room;

/**
 *
 * @author hanly
 */
public class DisplayFormatter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    
    public static String formatDate(Date date, boolean displayFormat) {
        return displayFormat ? DISPLAY_DATE_FORMAT.format(date) : DATE_FORMAT.format(date);
    }
    
    public static String formatGender(String gender) {
        if (gender == null) return "";
        return gender.trim().equalsIgnoreCase("M") ? "MALE" : "FEMALE";
    }
    
    public static void displayGuestInfo(Guest guest, boolean detailed) {
        if (detailed) {
            System.out.println("----------------------------------------------------------------");
            System.out.println("Guest information [National ID: " + guest.getNationalId() + "]");
            System.out.println("----------------------------------------------------------------");
        } else {
            System.out.println("\nGuest Information:");
        }
        
        System.out.println("Full Name: " + guest.getFullName());
        System.out.println("Phone Number: " + guest.getPhoneNumber());
        System.out.println("Birthdate: " + formatDate(guest.getBirthdate(), detailed));
        System.out.println("Gender: " + formatGender(guest.getGender()));
        
        if (detailed) {
            System.out.println("----------------------------------------------------------------");
        }
        
        System.out.println("Room ID: " + guest.getRoomId());
        System.out.println("Rental Days: " + guest.getRentalDays());
        System.out.println("Start Date: " + formatDate(guest.getStartDate(), detailed));
        
        if (detailed) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(guest.getStartDate());
            cal.add(Calendar.DAY_OF_MONTH, guest.getRentalDays());
            System.out.println("Check out: " + formatDate(cal.getTime(), detailed));
            System.out.println("----------------------------------------------------------------");
        }
    }
    
    public static void displayRoomInfo(Room room) {
        System.out.println("Room information:");
        System.out.println(" + ID           : " + room.getRoomId());
        System.out.println(" + Room         : " + room.getRoomName());
        System.out.println(" + Type         : " + room.getRoomType());
        System.out.println(" + Daily rate   : " + room.getDailyRate() + "$");
        System.out.println(" + Capacity     : " + room.getCapacity());
        System.out.println(" + Furniture    : " + room.getFurnitureDescription());
        System.out.println("----------------------------------------------------------------");
    }
}
