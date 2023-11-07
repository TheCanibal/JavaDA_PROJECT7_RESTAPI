package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> getAllRuleNames() {
	return ruleNameRepository.findAll();
    }

    public Optional<RuleName> getRuleNameById(Integer id) {
	return ruleNameRepository.findById(id);
    }

    public RuleName addRuleName(RuleName ruleName) {
	return ruleNameRepository.save(ruleName);
    }

    public RuleName updateRuleName(RuleName ruleName) {
	return ruleNameRepository.save(ruleName);
    }

    public void deleteRuleName(RuleName ruleName) {
	ruleNameRepository.delete(ruleName);
    }
}
