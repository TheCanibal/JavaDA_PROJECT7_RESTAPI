package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@Controller
public class RuleNameController {
    // TODO: Inject RuleName service
    @Autowired
    private RuleNameService ruleService;

    /**
     * display rule name list
     * 
     * @param model
     * @return html page path
     */
    @GetMapping("/ruleName/list")
    public String home(Model model) {
	// TODO: find all RuleName, add to model
	model.addAttribute("ruleNames", ruleService.getAllRuleNames());
	return "ruleName/list";
    }

    /**
     * display add rule name page
     * 
     * @param ruleName object to add
     * @return html page path
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
	return "ruleName/add";
    }

    /**
     * verify if fields are valid then create new rule name in DB
     * 
     * @param ruleName ruleName to add
     * @param result
     * @param model
     * @return add page if errors or redirect to rule name list
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
	// TODO: check data valid and save to db, after saving return RuleName list
	if (result.hasErrors()) {
	    return "ruleName/add";
	}
	ruleService.addRuleName(ruleName);
	return "redirect:/ruleName/list";
    }

    /**
     * display update page
     * 
     * @param id    rule name id
     * @param model
     * @return html page path
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
	// TODO: get RuleName by Id and to model then show to the form
	RuleName ruleName = ruleService.getRuleNameById(id).get();
	model.addAttribute("ruleName", ruleName);
	return "ruleName/update";
    }

    /**
     * Verify if fields are valid then update rule name in DB
     * 
     * @param id       rule name id
     * @param ruleName rule name to update
     * @param result
     * @param model
     * @return update page if errors or redirect to the rule name list
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
	    Model model) {
	// TODO: check required fields, if valid call service to update RuleName and
	// return RuleName list
	if (result.hasErrors()) {
	    return "ruleName/update";
	}
	ruleService.updateRuleName(ruleName);
	return "redirect:/ruleName/list";
    }

    /**
     * delete rule name from db
     * 
     * @param id    rule name id
     * @param model
     * @return redirect to rule name list
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
	// TODO: Find RuleName by Id and delete the RuleName, return to Rule list
	RuleName ruleName = ruleService.getRuleNameById(id).get();
	ruleService.deleteRuleName(ruleName);
	return "redirect:/ruleName/list";
    }
}
