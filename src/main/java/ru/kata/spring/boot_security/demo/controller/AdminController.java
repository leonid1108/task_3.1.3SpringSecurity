package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

	private final UserService userService;

	@Autowired
	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/admin")
	public String showAllUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "show";
	}

	@GetMapping("/admin/create")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "create";
	}

	@PostMapping("/admin/create")
	public String createUser(@ModelAttribute("user") User user, @RequestParam("roles") String[] roles) {
		user.setRoles(Set.of(roles));
		userService.saveUser(user);
		return "redirect:/admin";
	}

	@GetMapping("/admin/delete")
	public String deleteUser(@RequestParam("id") Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "delete";
	}

	@PostMapping("/admin/delete")
	public String deleteUser(@RequestParam("id") Long id) {
		userService.deleteById(id);
		return "redirect:/admin";
	}

	@GetMapping("/admin/edit")
	public String editUser(@RequestParam("id") Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "edit";
	}

	@PostMapping("/admin/edit")
	public String editUser(@RequestParam("id") Long id, @ModelAttribute("user") User user, @RequestParam("roles") String[] roles) {
		Set<String> roleSet = new HashSet<>(Arrays.asList(roles)); // Ensure this is mutable
		user.setRoles(roleSet);
		userService.editUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getAge(), user.getRoles());
		return "redirect:/admin";
	}

}
