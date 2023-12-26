package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@Controller
public class CurvePointController {
    @Autowired
    private CurvePointService curveService;

    /**
     * recover all curvePoints in database and send them to the curvePoint html page
     * to display them
     * 
     * @param model
     * @return html page path
     */
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curveService.getAllCurvePoints());
        return "curvePoint/list";
    }

    /**
     * display the add curve point page
     * 
     * @param curvePoint object to add
     * @return html page path
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Verify if fields are valid and then create a new curve point in DB
     * 
     * @param curvePoint to add to DB
     * @param result
     * @param model
     * @return add page if error or redirect to the list
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curveService.addCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * display the curve point update page
     * 
     * @param id    curve point id
     * @param model
     * @return html page path
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curveService.getCurveById(id).get();
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * Verify if fields are valid and then update curve point in DB
     * 
     * @param id         curve point id
     * @param curvePoint curve point to update in DB
     * @param result
     * @param model
     * @return update page if error or redirect to the list
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
            Model model) {

        // Curve list
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curveService.updateCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * delete curve point with his id
     * 
     * @param id    curve point id
     * @param model
     * @return redirection to the list
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curveService.getCurveById(id).get();
        curveService.deleteCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }
}
