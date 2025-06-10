/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    public LocalDate inputDate(String msg, String errorMsg, boolean mustBeFuture) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            String day = getString(msg);
            try {
                LocalDate date = LocalDate.parse(day, dtf);
                if (mustBeFuture && !date.isAfter(LocalDate.now())) {
                    System.out.println(errorMsg);
                    continue;
                }
                if (!mustBeFuture && date.isAfter(LocalDate.now())) {
                    System.out.println("Birthdate cannot be in the future!");
                    continue;
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println(errorMsg);
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

    public Guest inputGuest(boolean isUpdated) {
        Guest guest = new Guest();
        Scanner scanner = new Scanner(System.in);

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
        }

        // Input Full Name
        msg = "Input Full Name (2-25 characters, start with letter): ";
        errorMsg = "Name must be 2-25 characters and start with a letter!";
        regex = "^[A-Za-z][A-Za-z ]{1,24}$";
        guest.setFullName(input(msg, errorMsg, regex));

        // Input Birthdate
        msg = "Input Birthdate (yyyy-MM-dd): ";
        errorMsg = "Invalid date format! Use yyyy-MM-dd";
        LocalDate birthdate = inputDate(msg, errorMsg, false); // false = không cho phép ngày tương lai
        guest.setBirthdate(birthdate);

        // Input Gender
        msg = "Input Gender (nam/nữ): ";
        errorMsg = "Gender must be 'nam' or 'nữ'!";
        regex = "^(nam|nữ)$";
        guest.setGender(input(msg, errorMsg, regex));

        // Input Phone Number
        msg = "Input Phone Number (10 digits): ";
        errorMsg = "Invalid phone number format!";
        regex = "^(0[3|5|7|8|9])+([0-9]{8})$";
        guest.setPhoneNumber(input(msg, errorMsg, regex));

        // Input Room ID
        msg = "Input Room ID (e.g., R101): ";
        errorMsg = "Room ID must start with a letter followed by 1-4 digits!";
        regex = "^[A-Z]\\d{1,4}$";
        guest.setRoomId(input(msg, errorMsg, regex));

        // Input Rental Days
        msg = "Input Number of Rental Days: ";
        errorMsg = "Rental days must be positive!";
        int rentalDays = inputNumber(msg, errorMsg);
        guest.setRentalDays(rentalDays);

        // Input Start Date
        msg = "Input Start Date (yyyy-MM-dd): ";
        errorMsg = "Start date must be in the future!";
        LocalDate startDate = inputDate(msg, errorMsg, true); // true = chỉ cho phép ngày tương lai
        guest.setStartDate(startDate);

        // Input Co-tenant Name (optional)
        System.out.print("Input Co-tenant Name (optional, press Enter to skip): ");
        String coTenantName = scanner.nextLine().trim();
        if (!coTenantName.isEmpty()) {
            guest.setCoTenantName(coTenantName);
        }

        return guest;
    }
}
