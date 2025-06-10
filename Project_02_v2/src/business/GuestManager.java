/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.util.HashSet;
import model.Guest;

/**
 *
 * @author hanly
 */
public class GuestManager extends HashSet<Guest> implements Workable<Guest> {

    public boolean isDuppilcated(Guest guest) {
        return this.contains(this);
    }

    @Override
    public void addNew(Guest t) {
        if (!isDuppilcated(t)) {
            this.add(t);
        }
    }

}
