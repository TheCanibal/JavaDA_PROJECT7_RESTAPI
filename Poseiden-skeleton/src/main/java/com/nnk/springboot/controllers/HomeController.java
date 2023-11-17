package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.repositories.DBUserRepository;
import com.nnk.springboot.services.BidListService;

@Controller
public class HomeController {

    @Autowired
    private DBUserRepository userRepository;

    @Autowired
    private BidListService bidListService;

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
	model.addAttribute("bidLists", bidListService.getAllBidLists());
	return "redirect:/bidList/list";
    }

    /**
     * display user list
     * 
     * @return user list page
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
	ModelAndView mav = new ModelAndView();
	mav.addObject("users", userRepository.findAll());
	mav.setViewName("user/list");
	return mav;
    }

    /**
     * display error 403 page
     * 
     * @return 403 error page
     */
    @GetMapping("/access-denied")
    public ModelAndView error() {
	ModelAndView mav = new ModelAndView("/403");
	String errorMessage = "You are not authorized for the requested data.";
	mav.addObject("errorMsg", errorMessage);
	return mav;
    }

}
