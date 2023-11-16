package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@Controller
public class TradeController {
    // TODO: Inject Trade service
    @Autowired
    private TradeService tradeService;

    /**
     * display trade list page
     * 
     * @param model
     * @return html page path
     */
    @GetMapping("/trade/list")
    public String home(Model model) {
	// TODO: find all Trade, add to model
	model.addAttribute("trades", tradeService.getAllTrades());
	return "trade/list";
    }

    /**
     * display trade add page
     * 
     * @param trade object to add
     * @return html page path
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
	return "trade/add";
    }

    /**
     * verify if fields are valide then create new trade to DB
     * 
     * @param trade  trade to add
     * @param result
     * @param model
     * @return add page if errors or redirect to trade list
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
	// TODO: check data valid and save to db, after saving return Trade list
	if (result.hasErrors()) {
	    return "trade/add";
	}
	tradeService.addTrade(trade);
	return "redirect:/trade/list";
    }

    /**
     * display trade update page
     * 
     * @param id    trade id
     * @param model
     * @return html page path
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	// TODO: get Trade by Id and to model then show to the form
	Trade trade = tradeService.getTradeById(id).get();
	model.addAttribute("trade", trade);

	return "trade/update";
    }

    /**
     * verify if fields are valid then update trade in DB
     * 
     * @param id     trade id
     * @param trade  trade to add
     * @param result
     * @param model
     * @return update page if errors or redirect to trade list
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
	// TODO: check required fields, if valid call service to update Trade and return
	// Trade list
	if (result.hasErrors()) {
	    return "trade/update";
	}
	tradeService.updateTrade(trade);
	return "redirect:/trade/list";
    }

    /**
     * delete trade in DB
     * 
     * @param id    trade id
     * @param model
     * @return redirect to trade list
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
	// TODO: Find Trade by Id and delete the Trade, return to Trade list
	Trade trade = tradeService.getTradeById(id).get();
	tradeService.deleteTrade(trade);
	return "redirect:/trade/list";
    }
}
