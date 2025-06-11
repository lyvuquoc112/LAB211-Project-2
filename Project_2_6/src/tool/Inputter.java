/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;

import business.GuestManager;
import business.RoomManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
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
        String input;
        try {
            result = Integer.parseInt(mess);
        } catch (Exception e) {
            throw new NumberFormatException();
        }
        return result;
    }

    public double getDouble(String mess) {
        double result = 0;
        String input;
        try {
            result = Double.parseDouble(mess);
        } catch (Exception e) {
            throw new NumberFormatException();
        }
        return result;
    }

    public Date inputDate(String msg, String errorMsg, boolean mustBeFuture) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);  // Kiểm tra ngày chặt chẽ

        while (true) {
            System.out.print(msg);
            try {
                String dateStr = sc.nextLine().trim();
                Date date = sdf.parse(dateStr);

                // Kiểm tra ngày trong tương lai
                if (mustBeFuture && !date.after(new Date())) {
                    System.out.println(errorMsg);
                    continue;
                }

                // Kiểm tra ngày sinh không được trong tương lai
                if (!mustBeFuture && date.after(new Date())) {
                    System.out.println("Birthdate cannot be in the future!");
                    continue;
                }

                return date;
            } catch (ParseException e) {
                System.out.println("Invalid date! Please check the date exists.");
            }
        }
    }

    public int inputNumber(String mess, String errorMess) {
        while (true) {
            try {
                int number = Integer.parseInt(getString(mess));
                if (number > 0) {
                    return number;
                } else {
                    System.out.println(errorMess);
                }
            } catch (Exception e) {
                System.out.println(errorMess);
            }
        }
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

    public Guest inputGuest(boolean isUpdated, Guest oldGuest) {
        Guest guest = new Guest();

        // Input National ID
        String msg;
        String errorMsg;
        String regex;
        if (!isUpdated) {
            msg = "Input National ID (12 digits): ";
            errorMsg = "National ID must be exactly 12 digits!";
            regex = Validator.NATIONAL_ID_PATTERN;
            String nationalId = input(msg, errorMsg, regex);
            // Kiểm tra trùng lặp
            guest.setNationalId(nationalId);
        } else {
            guest.setNationalId(oldGuest.getNationalId());
        }

        // Input Full Name
        if (!isUpdated) {
            msg = "Input Full Name (2-25 characters, start with letter): ";
            errorMsg = "Name must be 2-25 characters and start with a letter!";
            regex = Validator.FULL_NAME_PATTERN;
            guest.setFullName(input(msg, errorMsg, regex));

        } else {
            guest.setFullName(oldGuest.getFullName());
        }

        // Input Birthdate
        if (!isUpdated) {
            msg = "Input Birthdate (yyyy-MM-dd): ";
            errorMsg = "Invalid date format! Use yyyy-MM-dd";
            Date birthdate = inputDate(msg, errorMsg, false); // false = không cho phép ngày tương lai
            guest.setBirthdate(birthdate);
        } else {
            guest.setBirthdate(oldGuest.getBirthdate());
        }

        // Input Gender
        if (!isUpdated) {
            msg = "Input Gender (nam/nữ): ";
            errorMsg = "Gender must be 'M' or 'F!";
            regex = Validator.GENDER_PATTERN;
            guest.setGender(input(msg, errorMsg, regex));
        } else {
            guest.setGender(oldGuest.getGender());
        }

        // Input Phone Number
        msg = "Input Phone Number (10 digits): ";
        errorMsg = "Invalid phone number format!";
        regex = Validator.PHONE_PATTERN;
        if (isUpdated) {
            System.out.println(msg +" (press Enter to keep current "+oldGuest.getPhoneNumber() +"):");
            String phone = sc.nextLine().trim();
            if (!phone.isEmpty()) {
                if (phone.matches(regex)) {
                    guest.setPhoneNumber(phone);
                } else {
                    System.out.println(errorMsg);
                    return null;
                }
            } else {
                guest.setPhoneNumber(oldGuest.getPhoneNumber());
            }
        } else {
            guest.setPhoneNumber(input(msg, errorMsg, regex));
        }

        // Input Room ID
        if (!isUpdated) {
            RoomManager roomManager = new RoomManager("D:\\FPT\\Ky 3\\LAB211\\SE1806-LAB211-main\\Set14_SU25\\De_LAB211\\02_J1.L.P0030.RoomManagementModule_500LOC\\Active_Room_List.txt");
            GuestManager guestManager = new GuestManager();
            String roomId;
            do {
                roomId = input("Input Room ID (e.g., R101): ", 
                    "Room ID must start with R and followed by 3 digits!", 
                    Validator.ROOM_ID_PATTERN).trim();
                
                // Kiểm tra phòng có tồn tại không
                if (!roomManager.isRoomExists(roomId)) {
                    System.out.println("Room " + roomId + " does not exist!");
                    continue;
                }
                
                // Kiểm tra phòng đã được đặt chưa
                if (guestManager.isRoomRented(roomId)) {
                    System.out.println("Room " + roomId + " is already rented!");
                    continue;
                }
                
                break;
            } while (true);
            
            guest.setRoomId(roomId);
        } else {
            guest.setRoomId(oldGuest.getRoomId());
        }

        // Input Rental Days
        msg = "Input Number of Rental Days: ";
        errorMsg = "Rental days must be positive!";
        if (isUpdated) {
            System.out.println(msg +" (press Enter to keep current "+oldGuest.getRentalDays() +"):");
            String daysStr = sc.nextLine().trim();
            if (!daysStr.isEmpty()) {
                try {
                    int days = Integer.parseInt(daysStr);
                    if (days > 0) {
                        guest.setRentalDays(days);
                    } else {
                        System.out.println("Rental days must be positive!");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format!");
                    return null;
                }
            } else {
                guest.setRentalDays(oldGuest.getRentalDays());
            }
        } else {

            guest.setRentalDays(inputNumber(msg, errorMsg));
        }

        // Input Start Date
        if (!isUpdated) {
            msg = "Input Start Date (yyyy-MM-dd): ";
            errorMsg = "Start date must be in the future!";
            Date startDate = inputDate(msg, errorMsg, true); // true = chỉ cho phép ngày tương lai
            guest.setStartDate(startDate);
        } else {
            guest.setStartDate(oldGuest.getStartDate());
        }

        // Input Co-tenant Name (optional)
        if (isUpdated) {
            System.out.print("Input Co-tenant name (press Enter to keep" +oldGuest.getCoTenantName()+"): ");
            String coTenant = sc.nextLine().trim();
            if (!coTenant.isEmpty()) {
                guest.setCoTenantName(coTenant);
            } else {
                guest.setCoTenantName(oldGuest.getCoTenantName());
            }
        } else {
            System.out.print("Input Co-tenant Name (optional, press Enter to skip): ");
            String coTenant = sc.nextLine().trim();
            if (!coTenant.isEmpty()) {
                guest.setCoTenantName(coTenant);
            }
        }
        return guest;
    }
}
