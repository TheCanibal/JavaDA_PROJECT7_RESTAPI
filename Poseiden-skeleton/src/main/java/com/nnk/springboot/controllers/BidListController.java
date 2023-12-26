package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@Controller
public class BidListController {
    @Autowired
    private BidListService bidListService;

    /**
     * display bid list page
     * 
     * @param model
     * @return html page path
     */
    @GetMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.getAllBidLists());
        return "bidList/list";
    }

    /**
     * display add bid page
     * 
     * @param bid object to add
     * @return html page path
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * verify if fields are valid then create new bid to DB
     * 
     * @param bidList bid to add
     * @param result
     * @param model
     * @return add page if errors or redirect to list
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }
        bidListService.addBidList(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * display bid update page
     * 
     * @param id    bid id
     * @param model
     * @return html page path
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidListById(id).get();
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    /**
     * verify if fields are valid then update bid in DB
     * 
     * @param id      bid id
     * @param bidList bid to update
     * @param result
     * @param model
     * @return update page if errors or redirect to bid list
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        // list Bid
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidListService.updateBidList(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * delete bid from DB
     * 
     * @param id    bid id
     * @param model
     * @return redirect to bid list
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        BidList bidList = bidListService.getBidListById(id).get();
        bidListService.deleteBidList(bidList);
        return "redirect:/bidList/list";
    }
}
