package com.cmpt.focusdriving.models.Student;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    private String name;
    private String email;
    private String phoneNumber;
    private String licenseNum;
    private String experience;
    private String address;

    @ElementCollection
    @CollectionTable(name = "student_availability", joinColumns = @JoinColumn(name = "sid"))
    @Column(name = "availability")
    private List<String> availability;

    public Student() {
    }

    // Constructor for basic info without availability (simplified for brevity)
    public Student(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    

    public Student(String name) {
        this.name = name;
    }

    // Full constructor including availability
    public Student(String name, String email, String phoneNumber, String licenseNum, String experience, String address, List<String> availability) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.licenseNum = licenseNum;
        this.experience = experience;
        this.address = address;
        this.availability = availability;
    }

    // Getters and setters (only showing new or modified ones for brevity)

    public List<String> getAvailability() {
        return availability;
    }

    public void setAvailability(List<String> availability) {
        this.availability = availability;
    }


    public String getLicenseNum() {
        return licenseNum;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSid() {
        return sid;
    }
    public void setSid(int sid) {
        this.sid = sid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
  


    
