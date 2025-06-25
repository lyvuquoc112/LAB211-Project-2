/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;

import business.GuestManager;
import business.RoomManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import model.Guest;

/**
 *
 * @author hanly
 */
public class Inputter {
    private Scanner sc;
    
    public Inputter() {
        sc = new Scanner(System.in);
    }
    
    public String getString(String mess) {
        System.out.println(mess);
        return sc.nextLine();
    }
    
    public int getInt(String mess) {
        int result = 0;
        try {
            result = Integer.parseInt(getString(mess));
        } catch (Exception e) {
            throw new NumberFormatException();
        }
        return result;
    }
    
    public double getDouble(String mess) {
        double result = 0;
        try {
            result = Double.parseDouble(getString(mess));
        } catch (Exception e) {
            throw new NumberFormatException();
        }
        return result;
    }

    public Date inputDate(String msg, String errorMsg, boolean mustBeFuture) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        do {            
            try {
                String dateStr = getString(msg).trim();
                Date date = sdf.parse(dateStr);
                if(mustBeFuture && !date.after(new Date())){
                    System.out.println(errorMsg);
                    continue;
                }if(!mustBeFuture && date.after(new Date())){
                    System.out.println(errorMsg);
                    continue;
                }
                return date;
            } catch (Exception e) {
                System.out.println("Invalid format. Please check it");
            }
        } while (true);
    }

    public int inputNumber(String mess, String errorMess) {
        do {            
            try {
                int number = getInt(mess);
                if(number < 0){
                    System.out.println(errorMess);
                    continue;
                }
                return number;
            } catch (Exception e) {
                System.out.println("Invalid format. Please check it");
            }
        } while (true);
    }

    public String input(String mess, String errorMess, String regex) {
        String result;
        boolean loop = false;
        do {
            result = getString(mess);
            loop = Validator.isValid(result, regex);
            if (!loop) {
                System.out.println(errorMess + " Please re-enter");
            }
        } while (!loop);
        return result;
    }

    public Guest inputGuest(boolean isUpdated, Guest oldGuest, RoomManager roomManager, GuestManager guestManager) {
        Guest guest = new Guest();
        String mess;
        String errorMess;
        String regex;
        
        // RentalDay,contenant name, phone
        
        // Nhap ID
        if(!isUpdated){
            mess = "Input National ID(12 digits): ";
            errorMess = "National ID must be exactly 12 digits!";
            regex = Validator.NATIONAL_ID_PATTERN;
            guest.setNationalId(input(mess, errorMess, regex));
        }else{
            guest.setNationalId(oldGuest.getNationalId());
        }
        
        // Nhap full name
        
        if (!isUpdated) {
            mess = "Input Full Name (2-25 characters, start with letter) ";
            errorMess = "Name must be 2-25 characters and start with a letter!";
            regex = Validator.FULL_NAME_PATTERN;
            guest.setFullName(input(mess, errorMess, regex));
        } else {
            guest.setFullName(oldGuest.getFullName());
        }
        
        // Nhap dob
        if (!isUpdated) {
            mess = "Input Birthdate (yyyy-MM-dd) ";
            errorMess = "Invalid date format! Use yyyy-MM-dd"; 
            guest.setBirthdate(inputDate(mess, errorMess, false)); // false = không cho phép ngày tương lai
        } else {
            guest.setBirthdate(oldGuest.getBirthdate());
        }
        
        // Nhap gender
        if (!isUpdated) {
            mess = "Input Gender (M/F): ";
            errorMess = "Gender must be 'M' or 'F!";
            regex = Validator.GENDER_PATTERN;
            guest.setGender(input(mess, errorMess, regex));
        } else {
            guest.setGender(oldGuest.getGender());
        }
        
        // Nhap phone
        mess = "Input phone number (10 digits)";
        errorMess = "Invalid phone number format!";
        regex = Validator.PHONE_PATTERN;
        boolean more = false;
        do {
            if (isUpdated) {
                System.out.println(mess + "[" + oldGuest.getPhoneNumber() + "]: ");
                String phone = getString("").trim();
                if(phone.isEmpty()){
                    guest.setPhoneNumber(oldGuest.getPhoneNumber());
                    more = true;
                }else if(phone.matches(regex)){
                    guest.setPhoneNumber(phone);
                    more = true;
                }else{
                    System.out.println(errorMess);
                }
            } else {
                guest.setPhoneNumber(input(mess, errorMess, regex));
                more = true;
            }
        } while (!more);
        
        // Nhap Room ID
        mess = "Input room ID (e.g., R101)";
        errorMess = "Room ID must start with R and followed by 3 digits!";
        regex = Validator.ROOM_ID_PATTERN;
        more = false;
        String roomID;
        if (!isUpdated) {
            do{
                 roomID = input(mess, errorMess, regex);
                if (!roomManager.isRoomExists(roomID)) {
                    System.out.println("Room " + roomID + " does not exist");
                    continue;
                }
                if (guestManager.isRoomRented(roomID)) {
                    System.out.println("Room " + roomID + " is rented");
                    continue;
                }
                break;
            }while(true);
            guest.setRoomId(roomID);
        } else {
            guest.setRoomId(oldGuest.getRoomId());
        }
        
        // Nhap rental day
        mess = "Input number of rental days";
        errorMess = "Rental days must be positive!";
        do {
            if (isUpdated) {
                System.out.println(mess + "[" + oldGuest.getRentalDays() + "]: ");
                String number = sc.nextLine().trim();
                if (number.isEmpty()) {
                    guest.setRentalDays(oldGuest.getRentalDays());
                    break;
                } else {
                    try {
                        int rentalDay = Integer.parseInt(number);
                        if (rentalDay < 0) {
                            System.out.println(errorMess);
                        } else {
                            guest.setRentalDays(rentalDay);
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(errorMess);
                    }
                }
            } else {
                guest.setRentalDays(inputNumber(mess, errorMess));
                break;
            }
        } while (true);
        
        // Nhap input Date
        if (!isUpdated) {
            mess = "Input Start Date (yyyy-MM-dd) ";
            errorMess = "Start date must be in the future!";
            Date startDate = inputDate(mess, errorMess, true); // true = chỉ cho phép ngày tương lai
            guest.setStartDate(startDate);
        } else {
            guest.setStartDate(oldGuest.getStartDate());
        }
        
        // Nhap conName
        mess = "Input Co-tenant Name (optional)";
        String cotenantName;
        if (isUpdated) {
            System.out.println(mess+"["+oldGuest.getCoTenantName()+"]: ");
            cotenantName = getString("").trim();
            if(!cotenantName.isEmpty()){
                guest.setCoTenantName(cotenantName);
            }else{
                guest.setCoTenantName(oldGuest.getCoTenantName());
            }
        } else {
            cotenantName = getString(mess).trim();
            if(!cotenantName.isEmpty()){
                guest.setCoTenantName(cotenantName);
            }
        }
        return guest;
    }
}
