package com.cmpt.focusdriving.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cmpt.focusdriving.models.Student;
import com.cmpt.focusdriving.models.UserRepository;
import com.cmpt.focusdriving.models.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {

    @Autowired
    private UserRepository usersRepository;

    @GetMapping("/users/all")
    public String getAllUsers(Model model) {
        System.out.println("Hello from all users");
        List<Users> users = usersRepository.findAll(); // db
        model.addAttribute("users", users);
        return "users/all";
    }

    @PostMapping("/users/signup")
    public String addUser(@RequestParam Map<String, String> newuser,
            HttpServletResponse response) {
        System.out.println("ADD student");
        String newName = newuser.get("name");
        String newPwd = newuser.get("password");
        String newRole = newuser.get("role");
        UserRepository.save(new Users(newName, newPwd, newRole));
        response.setStatus(201);
        return "users/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session) {
        Users user = (Users) session.getAttribute("session_user");
        if (user == null) {
            return "users/login";
        } else {
            model.addAttribute("user", user);
            if (user.getRole() == "admin") {
                return "users/ownerdashboard";
            } else {
                return "users/dashboard";

            }
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> formData, Model model,
            HttpServletRequest request, HttpSession session) {
        // processing login
        String name = formData.get("name");
        String pwd = formData.get("password");
        List<Users> userlist = usersRepository.findByNameAndPassword(name, pwd);
        if (userlist.isEmpty()) {
            return "users/login";
        } else {
            // success
            Users user = userlist.get(0);
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            if ((user.getRole()).equals("admin")) {
                return "users/ownerdashboard";
            } else {
                return "users/dashboard";
            }
            // return "users/dashboard";
        }
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "/users/login";
    }
}