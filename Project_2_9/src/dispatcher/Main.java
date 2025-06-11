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
public class Main {

    private static final String GUEST_FILE = "guestInfo.data";
    private static final String ACTIVE_ROOM_LIST_FILE = "D:\\FPT\\Ky 3\\LAB211\\SE1806-LAB211-main\\Set14_SU25\\De_LAB211\\02_J1.L.P0030.RoomManagementModule_500LOC\\Active_Room_List.txt";

    public static void main(String[] args) {
        // 1. Khởi tạo các đối tượng quản lý
        RoomManager roomManager = new RoomManager(ACTIVE_ROOM_LIST_FILE);
        GuestManager guestManager = new GuestManager(GUEST_FILE);

        // 2. Khởi tạo và chạy menu
        Menu menu = new Menu(roomManager, guestManager);
        menu.run();

        // 3. Đóng các tài nguyên
        menu.close();

    }

}
