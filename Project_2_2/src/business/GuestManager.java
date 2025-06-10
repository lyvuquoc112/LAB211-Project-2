/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import model.Guest;

/**
 *
 * @author hanly
 */
public class GuestManager extends HashSet<Guest> implements Workable<Guest> {

    public boolean isDupilcated(Guest guest) {
        return this.contains(this);
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
}
