package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.services.DBUserService;

@Controller
public class DBUserController {
    @Autowired
    private DBUserService userService;

    @RequestMapping("/user/list")
    public String home(Model model) {
	model.addAttribute("users", userService.findAll());
	return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(DBUser user) {
	return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid DBUser user, BindingResult result, Model model) {
	if (!result.hasErrors()) {
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    user.setPassword(encoder.encode(user.getPassword()));
	    userService.save(user);
	    return "redirect:/user/list";
	}
	return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	DBUser user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	user.setPassword("");
	model.addAttribute("DBUser", user);
	return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid DBUser user, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "user/update";
	}

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	user.setPassword(encoder.encode(user.getPassword()));
	user.setId(id);
	userService.save(user);
	model.addAttribute("users", userService.findAll());
	return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
	DBUser user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	userService.delete(user);
	model.addAttribute("users", userService.findAll());
	return "redirect:/user/list";
    }
}
