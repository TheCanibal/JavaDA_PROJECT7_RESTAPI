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
    // TODO: Inject Bid service
    @Autowired
    private BidListService bidListService;

    @GetMapping("/bidList/list")
    public String home(Model model) {
	// TODO: call service find all bids to show to the view
	model.addAttribute("bidLists", bidListService.getAllBidLists());
	return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
	return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
	// TODO: check data valid and save to db, after saving return bid list
	if (result.hasErrors()) {
	    return "bidList/add";
	}
	bidListService.addBidList(bidList);
	return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	// TODO: get Bid by Id and to model then show to the form
	BidList bidList = bidListService.getBidListById(id).get();
	model.addAttribute("bidList", bidList);
	return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
	// TODO: check required fields, if valid call service to update Bid and return
	// list Bid
	if (result.hasErrors()) {
	    return "bidList/update";
	}
	bidListService.updateBidList(bidList);
	return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
	// TODO: Find Bid by Id and delete the bid, return to Bid list
	BidList bidList = bidListService.getBidListById(id).get();
	bidListService.deleteBidList(bidList);
	return "redirect:/bidList/list";
    }
}
