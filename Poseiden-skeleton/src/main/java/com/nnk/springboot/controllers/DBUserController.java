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

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.services.DBUserService;

@Controller
public class DBUserController {
    @Autowired
    private DBUserService userService;

    /**
     * display user list
     * 
     * @param model
     * @return html page path
     */
    @GetMapping("/user/list")
    public String home(Model model) {
	model.addAttribute("users", userService.findAll());
	return "user/list";
    }

    /**
     * display add user page
     * 
     * @param user object to add
     * @return html page path
     */
    @GetMapping("/user/add")
    public String addUser(DBUser user) {
	return "user/add";
    }

    /**
     * verify if fields are valid then create a new user in DB
     * 
     * @param user   user to add in DB
     * @param result
     * @param model
     * @return add page if error or redirect to the list
     */
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

    /**
     * display update page
     * 
     * @param id    user id
     * @param model
     * @return html page path
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	DBUser user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	user.setPassword("");
	model.addAttribute("DBUser", user);
	return "user/update";
    }

    /**
     * verify if fields are valid then update user in DB
     * 
     * @param id     user id
     * @param user   user to add
     * @param result
     * @param model
     * @return update page if error or redireect to the list
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid DBUser user, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    return "user/update";
	}

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	user.setPassword(encoder.encode(user.getPassword()));
	user.setId(id);
	userService.save(user);
	return "redirect:/user/list";
    }

    /**
     * delete user in db
     * 
     * @param id    user id
     * @param model
     * @return redirection to the list
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
	DBUser user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	userService.delete(user);
	return "redirect:/user/list";
    }
}
