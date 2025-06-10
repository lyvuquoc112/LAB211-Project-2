/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author hanly
 */
public class Guest {
    private String nationalId;
    private String fullName;
    private LocalDate birthdate;
    private String gender;
    private String phoneNumber;
    private String roomId;
    private int rentalDays;
    private LocalDate startDate;
    private String coTenantName;

    public Guest() {
    }

    
    
    public Guest(String nationalId, String fullName, LocalDate birthdate, String gender, 
                String phoneNumber, String roomId, int rentalDays, LocalDate startDate, String coTenantName) {
        this.nationalId = nationalId;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.roomId = roomId;
        this.rentalDays = rentalDays;
        this.startDate = startDate;
        this.coTenantName = coTenantName;
    }

    // Getters and setters
    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    
    public int getRentalDays() { return rentalDays; }
    public void setRentalDays(int rentalDays) { this.rentalDays = rentalDays; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public String getCoTenantName() { return coTenantName; }
    public void setCoTenantName(String coTenantName) { this.coTenantName = coTenantName; }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nationalId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Guest other = (Guest) obj;
        return Objects.equals(this.nationalId, other.nationalId);
    }
    
}
