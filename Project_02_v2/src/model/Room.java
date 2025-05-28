package model;

public class Room {
    private String roomId;
    private String roomName;
    private String roomType;
    private double dailyRate;
    private int capacity;
    private String furnitureDescription;

    public Room(String roomId, String roomName, String roomType, double dailyRate, int capacity, String furnitureDescription) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.dailyRate = dailyRate;
        this.capacity = capacity;
        this.furnitureDescription = furnitureDescription;
    }

    // Getters and setters
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getFurnitureDescription() {
        return furnitureDescription;
    }

    public void setFurnitureDescription(String furnitureDescription) {
        this.furnitureDescription = furnitureDescription;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return roomId.equals(room.roomId);
    }

    @Override
    public int hashCode() {
        return roomId.hashCode();
    }
} 