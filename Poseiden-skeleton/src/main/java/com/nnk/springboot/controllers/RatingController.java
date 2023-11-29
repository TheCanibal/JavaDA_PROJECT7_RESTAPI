package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@Controller
public class RatingController {
    // TODO: Inject Rating service
    @Autowired
    private RatingService ratingService;

    /**
     * display rating list
     * 
     * @param model
     * @return html page path
     */
    @GetMapping("/rating/list")
    public String home(Model model) {
        // TODO: find all Rating, add to model
        model.addAttribute("ratings", ratingService.getAllRatings());
        return "rating/list";
    }

    /**
     * display add rating page
     * 
     * @param rating rating to add
     * @return html page path
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * verify if fields are valid then create new rating in DB
     * 
     * @param rating rating to add
     * @param result
     * @param model
     * @return add page if errors or redirect to rating list
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.addRating(rating);
        return "redirect:/rating/list";
    }

    /**
     * display update page
     * 
     * @param id    rating id
     * @param model
     * @return html page path
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        Rating rating = ratingService.getRatingById(id).get();
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * verify if fields are valid then update rating in DB
     * 
     * @param id     rating id
     * @param rating rating to update
     * @param result
     * @param model
     * @return update page if errors or redirect to rating list
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
            Model model) {
        // TODO: check required fields, if valid call service to update Rating and
        // return Rating list
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.updateRating(rating);
        return "redirect:/rating/list";
    }

    /**
     * delete rating from DB
     * 
     * @param id    rating id
     * @param model
     * @return redirect to rating list
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        Rating rating = ratingService.getRatingById(id).get();
        ratingService.deleteRating(rating);
        return "redirect:/rating/list";
    }
}
