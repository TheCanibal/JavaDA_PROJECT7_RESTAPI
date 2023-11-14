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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@SpringBootTest
@AutoConfigureMockMvc
public class BidTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListService bidListService;

    @Test
    public void bidListTest() {
	BidList bid = new BidList("Account Test", "Type Test", 10d);

	// Save
	bid = bidListService.addBidList(bid);
	assertNotNull(bid.getBidListId());
	assertEquals(bid.getBidQuantity(), 10d, 10d);

	// Update
	bid.setBidQuantity(20d);
	bid = bidListService.addBidList(bid);
	assertEquals(bid.getBidQuantity(), 20d, 20d);

	// Find
	List<BidList> listResult = bidListService.getAllBidLists();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = bid.getBidListId();
	bidListService.deleteBidList(bid);
	Optional<BidList> bidList = bidListService.getBidListById(id);
	assertFalse(bidList.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnListOfBidListPage() throws Exception {
	// get the Bid list page
	mockMvc.perform(get("/bidList/list")).andExpect(status().isOk()).andExpect(model().attributeExists("bidLists"))
		.andExpect(view().name("bidList/list"));
    }

    @Test
    @WithMockUser
    public void shouldReturnAddBidListPage() throws Exception {

	// Get the Bid Add page
	mockMvc.perform(get("/bidList/add")).andExpect(status().isOk()).andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser
    public void addBidToBidList() throws Exception {
	// New Object
	BidList bid = new BidList("Account Test", "Type Test", 10d);
	// Bid List size
	int bidListSize = bidListService.getAllBidLists().size();

	// perform post to add bid to bid List (in DB)
	mockMvc.perform(post("/bidList/validate").flashAttr("bidList", bid)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/bidList/list"));
	// Verify if bid is added to DB
	assertEquals(bidListService.getAllBidLists().size(), bidListSize + 1);
	// Delete bid from DB
	Integer id = bid.getBidListId();
	bidListService.deleteBidList(bid);
	Optional<BidList> bidList = bidListService.getBidListById(id);
	assertFalse(bidList.isPresent());

    }

    @Test
    @WithMockUser
    public void addBidToBidListWithEmptyFields() throws Exception {
	// perform post with empty fields and verify if there is errors
	mockMvc.perform(post("/bidList/validate").param("account", "").param("type", "").param("bidQuantity", ""))
		.andExpect(status().isOk()).andExpect(view().name("bidList/add"))
		.andExpect(model().attributeHasFieldErrors("bidList", "account", "type", "bidQuantity"));

    }

    @Test
    @WithMockUser
    public void shouldReturnUpdatePage() throws Exception {
	// New object
	BidList bid = new BidList("Account Test", "Type Test", 10d);
	// Save
	bid = bidListService.addBidList(bid);
	assertNotNull(bid.getBidListId());
	assertEquals(bid.getBidQuantity(), 10d, 10d);
	// Get ID
	Integer id = bid.getBidListId();
	// Perform get to show update bid page
	mockMvc.perform(get("/bidList/update/{id}", id)).andExpect(status().isOk())
		.andExpect(view().name("bidList/update")).andExpect(model().attributeExists("bidList"));

	// Delete
	bidListService.deleteBidList(bid);
	Optional<BidList> bidList = bidListService.getBidListById(id);
	assertFalse(bidList.isPresent());

    }

    @Test
    @WithMockUser
    public void updateBidInBidList() throws Exception {
	// New Object
	BidList bid = new BidList("Account Test", "Type Test", 10d);

	// Save
	bid = bidListService.addBidList(bid);
	assertNotNull(bid.getBidListId());
	assertEquals(bid.getBidQuantity(), 10d, 10d);
	// get ID
	Integer id = bid.getBidListId();

	// perform post to update bid
	mockMvc.perform(post("/bidList/update/{id}", id).flashAttr("bidList", bid).param("account", "Account Modify")
		.param("type", "Type Modify").param("bidQuantity", "20d")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/bidList/list"));

	// Verifying if there is modifications
	assertEquals(bid.getAccount(), "Account Modify");
	assertEquals(bid.getType(), "Type Modify");
	assertEquals(bid.getBidQuantity(), 20d);

	// Delete
	bidListService.deleteBidList(bid);
	Optional<BidList> bidList = bidListService.getBidListById(id);
	assertFalse(bidList.isPresent());

    }

    @Test
    @WithMockUser
    public void updateBidToBidListWithEmptyFields() throws Exception {
	// new Object
	BidList bid = new BidList("Account Test", "Type Test", 10d);

	// Save
	bid = bidListService.addBidList(bid);
	assertNotNull(bid.getBidListId());
	assertEquals(bid.getBidQuantity(), 10d, 10d);
	Integer id = bid.getBidListId();

	// perform post with empty fields and verify if there is errors
	mockMvc.perform(
		post("/bidList/update/{id}", id).param("account", "").param("type", "").param("bidQuantity", ""))
		.andExpect(status().isOk()).andExpect(view().name("bidList/update"))
		.andExpect(model().attributeHasFieldErrors("bidList", "account", "type", "bidQuantity"));
	// Verifying if there is no modifications
	assertEquals(bid.getAccount(), "Account Test");
	assertEquals(bid.getType(), "Type Test");
	assertEquals(bid.getBidQuantity(), 10d);

	// delete
	bidListService.deleteBidList(bid);
	Optional<BidList> bidList = bidListService.getBidListById(id);
	assertFalse(bidList.isPresent());

    }

    @Test
    @WithMockUser
    public void deleteBidInBidList() throws Exception {
	// New Object
	BidList bid = new BidList("Account Test", "Type Test", 10d);

	// Save
	bid = bidListService.addBidList(bid);
	assertNotNull(bid.getBidListId());
	assertEquals(bid.getBidQuantity(), 10d, 10d);
	// get ID
	Integer id = bid.getBidListId();

	// perform post to update bid
	mockMvc.perform(get("/bidList/delete/{id}", id).flashAttr("bidList", bid))
		.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/bidList/list"));

	// Verify if Bid is Deleted
	Optional<BidList> bidList = bidListService.getBidListById(id);
	assertFalse(bidList.isPresent());

    }

}
