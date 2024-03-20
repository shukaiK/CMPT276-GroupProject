package com.cmpt.focusdriving.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cmpt.focusdriving.models.Student;
import com.cmpt.focusdriving.models.UserRepository;
import com.cmpt.focusdriving.models.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/all")
    public String getAllUsers(Model model) {
        System.out.println("Hello from all users");
        List<User> users = userRepository.findAll(); // db
        model.addAttribute("users", users);
        return "user/all";
    }

    @PostMapping("/user/signup")
    public String addUser(@RequestParam Map<String, String> newuser,
            HttpServletResponse response) {
        System.out.println("ADD student");
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        String newRole = newuser.get("role");
        User user = new User(newName, newPwd, newRole);
        userRepository.save(user);
        response.setStatus(201);
        return "user/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("session_user");
        if (user == null) {
            return "user/login";
        } else {
            model.addAttribute("user", user);
            if (user.getRole() == "admin") {
                return "user/ownerdashboard";
            } else {
                return "user/dashboard";

            }
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> formData, Model model,
            HttpServletRequest request, HttpSession session) {
        // processing login
        String name = formData.get("name");
        String pwd = formData.get("password");
        List<User> userlist = userRepository.findByNameAndPassword(name, pwd);
        if (userlist.isEmpty()) {
            return "user/login";
        } else {
            // success
            User user = userlist.get(0);
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            if ((user.getRole()).equals("admin")) {
                return "user/ownerdashboard";
            } else {
                return "user/dashboard";
            }
        }
    }

    @GetMapping("/user/logout")
public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false); // Get session without creating a new one
    if (session != null && session.getAttribute("session_user") != null) {
        session.removeAttribute("session_user"); // Remove only the user attribute
    }
    return "redirect:/login"; // Use redirect to avoid direct directory path
}
}
