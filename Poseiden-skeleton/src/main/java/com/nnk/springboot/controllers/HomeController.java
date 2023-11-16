package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    /**
     * display home page
     * 
     * @param model
     * @return html page path
     */
    @GetMapping("/")
    public String home(Model model) {
	return "home";
    }

    /**
     * redirect to bid list page
     * 
     * @param model
     * @return redirection to bid list
     */
    @GetMapping("/admin/home")
    public String adminHome(Model model) {
	return "redirect:/bidList/list";
    }

}
