package com.bikerental.bike_rental.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    // Inject UserService

    @Autowired
    private UserService userService;


    // Show Registration Page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "user/register";     // → WEB-INF/views/user/register.jsp
    }

    // Show Login Page

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";        // → WEB-INF/views/user/login.jsp
    }

    // Show User Profile Page

    @GetMapping("/profile/{userId}")
    public String showProfilePage(@PathVariable String userId,
                                  Model model) {
        try {
            User user = userService.findUserById(userId);

            if (user == null) {
                model.addAttribute("error", "User not found.");
                return "user/error";
            }

            model.addAttribute("user", user);
            return "user/profile";  // → WEB-INF/views/user/profile.jsp

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/error";
        }
    }

    // Show User List Page

    @GetMapping("/list")
    public String showUserListPage(Model model) {
        try {
            model.addAttribute("users", userService.getAllUsers());
            return "user/userList"; // → WEB-INF/views/user/userList.jsp

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/error";
        }
    }

    // Show Edit Page

    @GetMapping("/edit/{userId}")
    public String showEditPage(@PathVariable String userId,
                               Model model) {
        try {
            User user = userService.findUserById(userId);

            if (user == null) {
                model.addAttribute("error", "User not found.");
                return "user/error";
            }

            model.addAttribute("user", user);
            return "user/edit";     // → WEB-INF/views/user/edit.jsp

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/error";
        }
    }


    //  CREATE - Register new user

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String role,
            @RequestParam(required = false) String licenseNumber,
            @RequestParam(required = false) String bankAccount,
            @RequestParam(required = false) String businessName,
            Model model) {

        try {
            // Generate unique user ID
            String userId = "U" + System.currentTimeMillis();

            User user;

            // Create correct subclass based on role - Polymorphism
            if ("RIDER".equalsIgnoreCase(role)) {
                user = new RiderUser(userId, name, email,
                        password, phone, licenseNumber);

            } else if ("OWNER".equalsIgnoreCase(role)) {
                user = new BikeOwner(userId, name, email,
                        password, phone, bankAccount, businessName);

            } else {
                user = new User(userId, name, email, password, phone);
            }

            // Save to users.txt via service
            userService.registerUser(user);

            model.addAttribute("success",
                    "Registration successful! Your User ID: " + userId);
            return "user/register";

        } catch (IllegalArgumentException e) {
            // Duplicate email
            model.addAttribute("error", e.getMessage());
            return "user/register";

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/register";
        }
    }


    //  LOGIN

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        try {
            // Polymorphism: login() checks correct subclass internally
            User user = userService.login(email, password);

            if (user == null) {
                model.addAttribute("error", "Invalid email or password.");
                return "user/login";
            }

            // Pass logged-in user to profile page
            model.addAttribute("user", user);
            model.addAttribute("success", "Welcome back, " + user.getName() + "!");

            // Redirect to profile page
            return "redirect:/users/profile/" + user.getUserId();

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/login";
        }
    }


    //  UPDATE - Update user profile

    @PostMapping("/update/{userId}")
    public String updateUser(
            @PathVariable String userId,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String password,
            Model model) {

        try {
            boolean updated = userService.updateUser(userId, phone, password);

            if (!updated) {
                model.addAttribute("error", "User not found.");
                return "user/edit";
            }

            model.addAttribute("success", "Profile updated successfully.");

            // Reload updated user into model
            User updatedUser = userService.findUserById(userId);
            model.addAttribute("user", updatedUser);

            return "user/profile";

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/edit";
        }
    }

    // UPDATE - Rider license number

    @PostMapping("/update/license/{userId}")
    public String updateLicense(
            @PathVariable String userId,
            @RequestParam String licenseNumber,
            Model model) {

        try {
            boolean updated = userService.updateRiderLicense(userId, licenseNumber);

            if (!updated) {
                model.addAttribute("error", "Rider not found.");
                return "user/edit";
            }

            model.addAttribute("success", "License updated successfully.");
            User updatedUser = userService.findUserById(userId);
            model.addAttribute("user", updatedUser);
            return "user/profile";

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/edit";
        }
    }

    // UPDATE - Owner bank account

    @PostMapping("/update/bank/{userId}")
    public String updateBankAccount(
            @PathVariable String userId,
            @RequestParam String bankAccount,
            Model model) {

        try {
            boolean updated = userService.updateOwnerBankAccount(userId, bankAccount);

            if (!updated) {
                model.addAttribute("error", "Owner not found.");
                return "user/edit";
            }

            model.addAttribute("success", "Bank account updated successfully.");
            User updatedUser = userService.findUserById(userId);
            model.addAttribute("user", updatedUser);
            return "user/profile";

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/edit";
        }
    }


    //  DELETE - Remove user account

    @PostMapping("/delete/{userId}")
    public String deleteUser(
            @PathVariable String userId,
            Model model) {

        try {
            boolean deleted = userService.deleteUser(userId);

            if (!deleted) {
                model.addAttribute("error", "User not found.");
                return "user/userList";
            }

            // Redirect to user list after successful delete
            return "redirect:/users/list";

        } catch (Exception e) {
            model.addAttribute("error", "System error: " + e.getMessage());
            return "user/userList";
        }
    }
}