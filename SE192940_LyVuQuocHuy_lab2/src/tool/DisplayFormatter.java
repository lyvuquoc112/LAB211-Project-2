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
        if (gender == null) {
            return "";
        }
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

        System.out.println("Full name: " + guest.getFullName());
        System.out.println("Phone number: " + guest.getPhoneNumber());
        System.out.println("Birth day: " + formatDate(guest.getBirthdate(), detailed));
        System.out.println("Gender: " + formatGender(guest.getGender()));

        if (detailed) {
            System.out.println("----------------------------------------------------------------");
        }

        System.out.println("Rental room: " + guest.getRoomId());
        System.out.println("Check in: "+formatDate(guest.getStartDate(), detailed));
        System.out.println("Rental Days: " + guest.getRentalDays());

        if (detailed) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(guest.getStartDate());
            cal.add(Calendar.DAY_OF_MONTH, guest.getRentalDays());
            System.out.println("Check out: " + formatDate(cal.getTime(), detailed));
            System.out.println("----------------------------------------------------------------");
        }
    }

    public static void displayRoomInfo(Room room) {
        System.out.printf(" + %-18s\n", "Room information:");
        System.out.printf(" + %-18s:%s\n", "ID ", room.getRoomId());
        System.out.printf(" + %-18s:%s\n", "Room", room.getRoomName());
        System.out.printf(" + %-18s:%s\n", "Type", room.getRoomType());
        System.out.printf(" + %-18s:%s\n", "Daily rate: ", String.format("%,.0f$", room.getDailyRate()));
        System.out.printf(" + %-18s:%d\n", "Capacity", room.getCapacity());
        System.out.printf(" + %-18s:%s\n", "Furniture", room.getFurnitureDescription());
        System.out.println("----------------------------------------------------------------");
    }
}
