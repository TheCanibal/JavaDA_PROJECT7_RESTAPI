package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeTests {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void tradeTest() {
	Trade trade = new Trade("Trade Account", "Type", 10d);

	// Save
	trade = tradeService.addTrade(trade);
	assertNotNull(trade.getTradeId());
	assertTrue(trade.getAccount().equals("Trade Account"));

	// Update
	trade.setAccount("Trade Account Update");
	trade = tradeService.updateTrade(trade);
	assertTrue(trade.getAccount().equals("Trade Account Update"));

	// Find
	List<Trade> listResult = tradeService.getAllTrades();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = trade.getTradeId();
	tradeService.deleteTrade(trade);
	Optional<Trade> tradeList = tradeService.getTradeById(id);
	assertFalse(tradeList.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnListOfTradePage() throws Exception {
	// get the Trade list page
	mockMvc.perform(get("/trade/list")).andExpect(status().isOk()).andExpect(model().attributeExists("trades"))
		.andExpect(view().name("trade/list"));
    }

    @Test
    @WithMockUser
    public void shouldReturnAddTradePage() throws Exception {

	// Get the Trade Add page
	mockMvc.perform(get("/trade/add")).andExpect(status().isOk()).andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser
    public void addTradeToTradeList() throws Exception {
	// New Object
	Trade trade = new Trade("Trade Account", "Type", 10d);
	// Trade List size
	int ratingSize = tradeService.getAllTrades().size();

	// perform post to add Trade to Trade List (in DB)
	mockMvc.perform(post("/trade/validate").flashAttr("trade", trade)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/trade/list"));
	// Verify if Trade is added to DB
	assertEquals(tradeService.getAllTrades().size(), ratingSize + 1);
	// Delete Trade from DB
	Integer id = trade.getTradeId();
	// delete
	tradeService.deleteTrade(trade);
	Optional<Trade> tradeTest = tradeService.getTradeById(id);
	assertFalse(tradeTest.isPresent());
    }

    @Test
    @WithMockUser
    public void addTradeToTradeListWithEmptyFields() throws Exception {
	// perform post with empty fields and verify if there is errors
	mockMvc.perform(post("/trade/validate").param("account", "").param("type", "").param("buyQuantity", ""))
		.andExpect(status().isOk()).andExpect(view().name("trade/add"))
		.andExpect(model().attributeHasFieldErrors("trade", "account", "type", "buyQuantity"));
    }

    @Test
    @WithMockUser
    public void shouldReturnTradeUpdatePage() throws Exception {
	// New Object
	Trade trade = new Trade("Trade Account", "Type", 10d);
	// Save
	trade = tradeService.addTrade(trade);
	assertNotNull(trade.getTradeId());
	assertTrue(trade.getAccount().equals("Trade Account"));
	// get ID
	Integer id = trade.getTradeId();
	// Perform get to show update Trade page
	mockMvc.perform(get("/trade/update/{id}", id)).andExpect(status().isOk()).andExpect(view().name("trade/update"))
		.andExpect(model().attributeExists("trade"));

	// delete
	tradeService.deleteTrade(trade);
	Optional<Trade> tradeTest = tradeService.getTradeById(id);
	assertFalse(tradeTest.isPresent());

    }

    @Test
    @WithMockUser
    public void updateTradeInTradeList() throws Exception {
	// New Object
	Trade trade = new Trade("Trade Account", "Type", 10d);

	// Save
	trade = tradeService.addTrade(trade);
	assertNotNull(trade.getTradeId());
	assertTrue(trade.getAccount().equals("Trade Account"));
	// get ID
	Integer id = trade.getTradeId();

	// perform post to update Trade
	mockMvc.perform(post("/trade/update/{id}", id).flashAttr("trade", trade)
		.param("account", "Trade Account Modify").param("type", "Type Modify").param("buyQuantity", "20d"))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/trade/list"));

	// Verifying if there is modifications
	assertEquals(trade.getAccount(), "Trade Account Modify");
	assertEquals(trade.getType(), "Type Modify");
	assertEquals(trade.getBuyQuantity(), 20d);

	// delete
	tradeService.deleteTrade(trade);
	Optional<Trade> tradeTest = tradeService.getTradeById(id);
	assertFalse(tradeTest.isPresent());

    }

    @Test
    @WithMockUser
    public void updateTradeToTradeListWithEmptyFields() throws Exception {
	// New Object
	Trade trade = new Trade("Trade Account", "Type", 10d);

	// Save
	trade = tradeService.addTrade(trade);
	assertNotNull(trade.getTradeId());
	assertTrue(trade.getAccount().equals("Trade Account"));
	// get ID
	Integer id = trade.getTradeId();

	// perform post with empty fields and verify if there is errors
	mockMvc.perform(post("/trade/update/{id}", id).param("account", "").param("type", "").param("buyQuantity", ""))
		.andExpect(status().isOk()).andExpect(view().name("trade/update"))
		.andExpect(model().attributeHasFieldErrors("trade", "account", "type"));
	// Verifying if there is no modifications
	assertEquals(trade.getAccount(), "Trade Account");
	assertEquals(trade.getType(), "Type");
	assertEquals(trade.getBuyQuantity(), 10d);

	// delete
	tradeService.deleteTrade(trade);
	Optional<Trade> tradeTest = tradeService.getTradeById(id);
	assertFalse(tradeTest.isPresent());

    }

    @Test
    @WithMockUser
    public void deleteTradeInTradeList() throws Exception {
	// New Object
	Trade trade = new Trade("Trade Account", "Type", 10d);

	// Save
	trade = tradeService.addTrade(trade);
	assertNotNull(trade.getTradeId());
	assertTrue(trade.getAccount().equals("Trade Account"));
	// get ID
	Integer id = trade.getTradeId();

	// perform post to delete Trade
	mockMvc.perform(get("/trade/delete/{id}", id).flashAttr("trade", trade)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/trade/list"));

	// Verify if Trade is Deleted
	Optional<Trade> tradeTest = tradeService.getTradeById(id);
	assertFalse(tradeTest.isPresent());

    }
}
