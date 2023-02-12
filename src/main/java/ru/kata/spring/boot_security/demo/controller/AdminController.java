package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleRepository roleRepository;
    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {

        this.userService = userService;
        this.roleRepository = roleRepository;
    }
    @GetMapping("")
    public String allUsers(Model model){
        model.addAttribute("users", userService.showUsers());
        return "indexAdmin";
    }
    @GetMapping("/adduser")
    public String addUser(@ModelAttribute("user") User user, Model model){
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "adduser";
    }
    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.showUser(id));
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);
        return "edituser";
    }
    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }}
