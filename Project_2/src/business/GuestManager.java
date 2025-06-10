/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import model.Guest;
import model.Room;

/**
 *
 * @author hanly
 */
public class GuestManager extends HashSet<Guest> implements Workable<Guest> {

    public boolean isDupilcated(Guest guest) {
        return this.contains(guest);
    }

    @Override
    public void addNew(Guest guest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (!isDupilcated(guest)) {
            this.add(guest);
            System.out.println("\nGuest registered successfully for room " + guest.getRoomId());
            System.out.println("Rental from " + sdf.format(guest.getStartDate())
                    + " for " + guest.getRentalDays() + " days");
        } else {
            System.out.println("This national ID is already registered!");
        }
    }

    public Guest findGuestById(String id) {
        for (Guest thi : this) {
            if (thi.getNationalId().equals(id)) {
                return thi;
            }
        }
        return null;
    }

    public void update(Guest guest) {
        this.remove(guest);
        this.add(guest);
    }

    public void displayGuestInfo(Guest guest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("\nGuest Information:");
        System.out.println("National ID: " + guest.getNationalId());
        System.out.println("Full Name: " + guest.getFullName());
        System.out.println("Birthdate: " + sdf.format(guest.getBirthdate()));
        System.out.println("Gender: " + guest.getGender());
        System.out.println("Phone Number: " + guest.getPhoneNumber());
        System.out.println("Room ID: " + guest.getRoomId());
        System.out.println("Rental Days: " + guest.getRentalDays());
        System.out.println("Start Date: " + sdf.format(guest.getStartDate()));
        System.out.println("Co-tenant Name: " + guest.getCoTenantName());
    }
    
    private String formatGender(String gender) {
    if (gender == null) return "";
    return gender.trim().equalsIgnoreCase("M") ? "MALE" : "FEMALE";
}

    public void displayGuestAndRoomInformation(Guest guest, RoomManager roomManager) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Guest information [National ID: " + guest.getNationalId() + "]");
        System.out.println("----------------------------------------------------------------");
        System.out.println(" Full name          : " + guest.getFullName());
        System.out.println(" Phone number       : " + guest.getPhoneNumber());
        System.out.println(" Birth day          : " + new SimpleDateFormat("dd/MM/yyyy").format(guest.getBirthdate()));
        System.out.println(" Gender             : " + formatGender(guest.getGender()));
        System.out.println("----------------------------------------------------------------");
        System.out.println(" Rental room        : " + guest.getRoomId());
        System.out.println(" Check in           : " + new SimpleDateFormat("dd/MM/yyyy").format(guest.getStartDate()));
        System.out.println(" Rental days        : " + guest.getRentalDays());

        // Calculate check out date
        Calendar cal = Calendar.getInstance(); // Tạo một đối tượng Calendar mới
        cal.setTime(guest.getStartDate());    // Set thời gian bắt đầu là ngày check-in
        cal.add(Calendar.DAY_OF_MONTH, guest.getRentalDays()); // Cộng thêm số ngày thuê
        System.out.println(" Check out          : " + new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()));
        System.out.println("----------------------------------------------------------------");

        Room r = roomManager.getRoomById(guest.getRoomId());
        System.out.println("Room information:");
        System.out.println(" + ID           : " + r.getRoomId());
        System.out.println(" + Room         : " + r.getRoomName());
        System.out.println(" + Type         : " + r.getRoomType());
        System.out.println(" + Daily rate   : " + r.getDailyRate() + "$");
        System.out.println(" + Capacity     : " + r.getCapacity());
        System.out.println(" + Furniture    : " + r.getFurnitureDescription());
    }
}
