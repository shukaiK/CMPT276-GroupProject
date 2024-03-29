package com.cmpt.focusdriving.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cmpt.focusdriving.models.email;
import com.cmpt.focusdriving.models.Student.Student;
import com.cmpt.focusdriving.models.Student.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private email senderService;

    @PostMapping("/html/form")
    public String form(@RequestParam Map<String, String> user, HttpServletResponse response) {
        // Extracting basic information
        String emailString = user.get("email");
        String nameString = user.get("name");
        String phoneString = user.get("phone");
        String addressString = user.get("address");
        String licenseNum = user.get("licenseNum");
        String experienceStr = user.get("experience");

        // Building the list of availabilities
        List<String> availability = new ArrayList<>();
        String[] daysOfWeek = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        for (String day : daysOfWeek) {
            String availabilityStatus = user.getOrDefault(day, "Not Available");
            availability.add(day + ": " + availabilityStatus);
        }

        // Constructing the message for email
        StringBuilder messageConcat = new StringBuilder();
        messageConcat.append("Name: ").append(nameString)
                .append("\nEmail: ").append(emailString)
                .append("\nPhoneNumber: ").append(phoneString)
                .append("\nAddress: ").append(addressString)
                .append("\nLicense Number: ").append(licenseNum)
                .append("\nExperience: ").append(experienceStr)
                .append("\n\nAvailability:\n");

        availability.forEach(avail -> messageConcat.append(avail).append("\n"));

        // Sending emails
        senderService.sendEmail("cmpt276.groupproject@gmail.com", "New Request by " + nameString,
                messageConcat.toString());
        senderService.sendEmail(emailString, "Attention: Your request has been sent", "Dear " + nameString
                + ",\nYour request has been sent to our invoice, and we will respond back shortly.");

        // Creating and saving the Student object
        Student student = new Student(nameString, emailString, phoneString, licenseNum, experienceStr, addressString,
                availability);
        studentRepo.save(student);

        // Redirecting to the home page
        return "redirect:/html/home.html";
    }
}
