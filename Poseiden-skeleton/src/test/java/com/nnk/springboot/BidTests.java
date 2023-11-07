package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.nnk.springboot.repositories.BidListRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BidTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void bidListTest() {
	BidList bid = new BidList("Account Test", "Type Test", 10d);

	// Save
	bid = bidListRepository.save(bid);
	assertNotNull(bid.getBidListId());
	assertEquals(bid.getBidQuantity(), 10d, 10d);

	// Update
	bid.setBidQuantity(20d);
	bid = bidListRepository.save(bid);
	assertEquals(bid.getBidQuantity(), 20d, 20d);

	// Find
	List<BidList> listResult = bidListRepository.findAll();
	assertTrue(listResult.size() > 0);

	// Delete
	Integer id = bid.getBidListId();
	bidListRepository.delete(bid);
	Optional<BidList> bidList = bidListRepository.findById(id);
	assertFalse(bidList.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnHomePage() throws Exception {
	mockMvc.perform(get("/bidList/list")).andExpect(status().isOk()).andExpect(view().name("bidList/list"));
    }

}
