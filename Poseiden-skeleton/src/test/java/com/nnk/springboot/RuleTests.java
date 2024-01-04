package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleTests {

    @Autowired
    private RuleNameService ruleNameService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ruleTest() {
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        rule = ruleNameService.addRuleName(rule);
        assertNotNull(rule.getId());
        assertTrue(rule.getName().equals("Rule Name"));

        // Update
        rule.setName("Rule Name Update");
        rule = ruleNameService.updateRuleName(rule);
        assertTrue(rule.getName().equals("Rule Name Update"));

        // Find
        List<RuleName> listResult = ruleNameService.getAllRuleNames();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rule.getId();
        ruleNameService.deleteRuleName(rule);
        Optional<RuleName> ruleList = ruleNameService.getRuleNameById(id);
        assertFalse(ruleList.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnListOfRulePage() throws Exception {
        // get the Rule list page
        mockMvc.perform(get("/ruleName/list")).andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleNames")).andExpect(view().name("ruleName/list"));
    }

    @Test
    @WithMockUser
    public void shouldReturnAddRulePage() throws Exception {

        // Get the Rule Add page
        mockMvc.perform(get("/ruleName/add")).andExpect(status().isOk()).andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser
    public void addRuleToRuleList() throws Exception {
        // New Object
        RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Rule List size
        int ratingSize = ruleNameService.getAllRuleNames().size();

        // perform post to add Rule to Rule List (in DB)
        mockMvc.perform(post("/ruleName/validate").flashAttr("ruleName", ruleName).with(csrf()))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/ruleName/list"));
        // Verify if Rule is added to DB
        assertEquals(ruleNameService.getAllRuleNames().size(), ratingSize + 1);
        // Delete Rule from DB
        Integer id = ruleName.getId();
        // delete
        ruleNameService.deleteRuleName(ruleName);
        // Verify if Rule is Deleted
        Optional<RuleName> ruleTest = ruleNameService.getRuleNameById(id);
        assertFalse(ruleTest.isPresent());
    }

    @Test
    @WithMockUser
    public void addRatingToRatingListWithEmptyFields() throws Exception {
        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/ruleName/validate").param("name", "").param("description", "").param("json", "")
                .param("template", "").param("sqlStr", "").param("sqlPart", "").with(csrf())).andExpect(status().isOk())
                .andExpect(view().name("ruleName/add")).andExpect(model().attributeHasFieldErrors("ruleName", "name",
                        "description", "json", "template", "sqlStr", "sqlPart"));
    }

    @Test
    @WithMockUser
    public void shouldReturnRatingUpdatePage() throws Exception {
        // New object
        RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        ruleName = ruleNameService.addRuleName(ruleName);
        assertNotNull(ruleName.getId());
        assertTrue(ruleName.getName().equals("Rule Name"));
        // get ID
        Integer id = ruleName.getId();
        // Perform get to show update Rule page
        mockMvc.perform(get("/ruleName/update/{id}", id)).andExpect(status().isOk())
                .andExpect(view().name("ruleName/update")).andExpect(model().attributeExists("ruleName"));

        // delete
        ruleNameService.deleteRuleName(ruleName);
        // Verify if Rule is Deleted
        Optional<RuleName> ruleTest = ruleNameService.getRuleNameById(id);
        assertFalse(ruleTest.isPresent());

    }

    @Test
    @WithMockUser
    public void updateRatingInRatingList() throws Exception {
        // New Object
        RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        ruleName = ruleNameService.addRuleName(ruleName);
        assertNotNull(ruleName.getId());
        assertTrue(ruleName.getName().equals("Rule Name"));
        // get ID
        Integer id = ruleName.getId();

        // perform post to update Rule
        mockMvc.perform(post("/ruleName/update/{id}", id).flashAttr("ruleName", ruleName)
                .param("name", "Rule Name Modify").param("description", "Description Modify")
                .param("json", "Json Modify").param("template", "Template Modify").param("sqlStr", "SQL Modify")
                .param("sqlPart", "SQL Part Modify").with(csrf())).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));

        // Verifying if there is modifications
        assertEquals(ruleName.getName(), "Rule Name Modify");
        assertEquals(ruleName.getDescription(), "Description Modify");
        assertEquals(ruleName.getJson(), "Json Modify");
        assertEquals(ruleName.getTemplate(), "Template Modify");
        assertEquals(ruleName.getSqlStr(), "SQL Modify");
        assertEquals(ruleName.getSqlPart(), "SQL Part Modify");

        // delete
        ruleNameService.deleteRuleName(ruleName);
        // Verify if Rule is Deleted
        Optional<RuleName> ruleTest = ruleNameService.getRuleNameById(id);
        assertFalse(ruleTest.isPresent());

    }

    @Test
    @WithMockUser
    public void updateRatingToRatingListWithEmptyFields() throws Exception {
        // new Object
        RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        ruleName = ruleNameService.addRuleName(ruleName);
        assertNotNull(ruleName.getId());
        assertTrue(ruleName.getName().equals("Rule Name"));
        // get ID
        Integer id = ruleName.getId();

        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/ruleName/update/{id}", id).param("name", "").param("description", "").param("json", "")
                .param("template", "").param("sqlStr", "").param("sqlPart", "").with(csrf())).andExpect(status().isOk())
                .andExpect(view().name("ruleName/update")).andExpect(model().attributeHasFieldErrors("ruleName", "name",
                        "description", "json", "template", "sqlStr", "sqlPart"));
        // Verifying if there is no modifications
        assertEquals(ruleName.getName(), "Rule Name");
        assertEquals(ruleName.getDescription(), "Description");
        assertEquals(ruleName.getJson(), "Json");
        assertEquals(ruleName.getTemplate(), "Template");
        assertEquals(ruleName.getSqlStr(), "SQL");
        assertEquals(ruleName.getSqlPart(), "SQL Part");
        // delete
        ruleNameService.deleteRuleName(ruleName);
        // Verify if Rule is Deleted
        Optional<RuleName> ruleTest = ruleNameService.getRuleNameById(id);
        assertFalse(ruleTest.isPresent());

    }

    @Test
    @WithMockUser
    public void deleteRatingInRatingList() throws Exception {
        // New Object
        RuleName ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

        // Save
        ruleName = ruleNameService.addRuleName(ruleName);
        assertNotNull(ruleName.getId());
        assertTrue(ruleName.getName().equals("Rule Name"));
        // get ID
        Integer id = ruleName.getId();

        // perform post to delete Rule
        mockMvc.perform(get("/ruleName/delete/{id}", id).flashAttr("ruleName", ruleName))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/ruleName/list"));

        // Verify if Rule is Deleted
        Optional<RuleName> ruleTest = ruleNameService.getRuleNameById(id);
        assertFalse(ruleTest.isPresent());

    }
}
