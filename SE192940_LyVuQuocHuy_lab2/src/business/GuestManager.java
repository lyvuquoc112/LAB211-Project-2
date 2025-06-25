/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.text.ParseException;
import model.Guest;
import model.Room;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tool.DisplayFormatter;

/**
 *
 * @author hanly
 */
public class GuestManager extends HashSet<Guest> implements Workable<Guest> {
    private boolean saved;
    private String pathFile;

    public GuestManager() {
    }

    public GuestManager(String pathFile) {
        this.pathFile = pathFile;
    }
    
    
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
            saved = false;
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
        saved = false;
    }

    public void displayGuestInfo(Guest guest) {
        DisplayFormatter.displayGuestInfo(guest, false);
    }
    
    public void displayGuestAndRoomInformation(Guest guest, RoomManager roomManager) {
        DisplayFormatter.displayGuestInfo(guest, true);
        Room room = roomManager.getRoomById(guest.getRoomId());
        if (room != null) {
            DisplayFormatter.displayRoomInfo(room);
        }
    }
    
    public boolean canCancelReservation(Guest guest) {
        Date today = new Date();
        return guest.getStartDate().after(today);
    }
    
    public boolean deleteGuestReservation(Guest guest) {
        saved = false;
        return this.remove(guest);
    }

    public void displayMonthlyRevenueReport(String targetMonth, RoomManager roomManager) {
        try {
            
            //1. Chuyển đổi chuỗi tháng thành Date
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            sdf.setLenient(false);
            Date date = sdf.parse(targetMonth);
            
            // 2. Lấy tháng và năm từ Date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);  // 0-11
            int year = cal.get(Calendar.YEAR);
            
            // 3. Tạo map lưu doanh thu theo phòng
            Map<String, Double> revenueByRoom = new HashMap<>();
            
            // 4. Tính doanh thu cho từng phòng
            for (Guest thi : this) {
                Calendar guestCal = Calendar.getInstance();
                guestCal.setTime(thi.getStartDate());
                if(guestCal.get(Calendar.MONTH)==month &&
                        guestCal.get(Calendar.YEAR)== year){
                    Room room = roomManager.getRoomById(thi.getRoomId());
                    double revenue = room.getDailyRate()*thi.getRentalDays();
                    revenueByRoom.put(thi.getRoomId(), revenue);
                }
            }
            // 5. Hiển thị báo cáo
            System.out.println("\nMonthly Revenue Report - '" + targetMonth + "'");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-8s|%-20s|%-12s|%-10s|%10s\n", 
                "RoomID", "Room Name", "Room type", "DailyRate", "Amount");
            System.out.println("----------------------------------------------------------------");
            
            
            // Kiểm tra có dữ liệu không
            if (revenueByRoom.isEmpty()) {
                System.out.println("There is no data on guests who have rented rooms");
            } else {
                // In thông tin từng phòng
                List<String> sortedRevenueByRoom = new ArrayList<>(revenueByRoom.keySet());
                Collections.sort(sortedRevenueByRoom);
                for (String roomId : sortedRevenueByRoom) {
                    Room room = roomManager.getRoomById(roomId);
                    System.out.printf("%-8s|%-20s|%-12s|%-10.2f|%10.2f\n",
                        room.getRoomId(),
                        room.getRoomName(),
                        room.getRoomType(),
                        room.getDailyRate(),
                        revenueByRoom.get(roomId));
                }
            }
            System.out.println("----------------------------------------------------------------");
            
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use MM/YYYY format.");
        }
    }

    public boolean isRoomRented(String roomId) {
        for (Guest guest : this) {
            if (guest.getRoomId().equalsIgnoreCase(roomId)) {
                return true;
            }
        }
        return false;
    }

    public void displayRevenueByRoomType(String roomType, RoomManager roomManager) {

        // Kiểm tra roomType có hợp lệ không
        if (!isValidRoomType(roomType)) {
            System.out.println("Invalid room type!");
            return;
        }

        double totalRevenue = 0.0;
        for (Guest guest : this) {
            Room room = roomManager.getRoomById(guest.getRoomId());
            if (room.getRoomType().equalsIgnoreCase(roomType)) {
                totalRevenue += room.getDailyRate() * guest.getRentalDays();
            }
        }

        // Hiển thị báo cáo
        System.out.println("\nRevenue Report by Room Type");
        System.out.println("----------------------------");
        System.out.printf("%-12s|%10s\n", "Room type", "Amount");
        System.out.println("----------------------------");

        System.out.printf("%-12s|%10s\n",
            roomType,
            String.format("$%,.0f", totalRevenue));
        System.out.println("----------------------------");
    }

    // Phương thức kiểm tra loại phòng hợp lệ
    private boolean isValidRoomType(String roomType) {
        if (roomType == null || roomType.trim().isEmpty()) {
            return false;
        }
        // Danh sách các loại phòng hợp lệ
        String[] validTypes = {"Deluxe", "Standard", "Suite", "Superior"};
        for (String type : validTypes) {
            if (type.equalsIgnoreCase(roomType.trim())) {
                return true;
            }
        }
        return false;
    }

    public void saveGuestInfo() {
        
        if(this.saved){
            return;
        }
        try {
            // Tạo file để lưu dữ liệu
            File f = new File(pathFile);
            if(!f.exists()){
                f.createNewFile();
            }
            // Tạo ObjectOutputStream để ghi dữ liệu
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
                // Ghi từng guest
                for (Guest guest : this) {
                    oos.writeObject(guest);
                }
                System.out.println("Guest information has been successfully saved to "+pathFile);
                this.saved = true;
            }
        } catch (IOException e) {
            System.out.println("Error saving guest information: " + e.getMessage());
        }
    }
}
