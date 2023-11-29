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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingTests {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ratingTest() {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Save
        rating = ratingService.addRating(rating);
        assertNotNull(rating.getId());
        assertTrue(rating.getOrderNumber() == 10);

        // Update
        rating.setOrderNumber(20);
        rating = ratingService.updateRating(rating);
        assertTrue(rating.getOrderNumber() == 20);

        // Find
        List<Rating> listResult = ratingService.getAllRatings();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingService.deleteRating(rating);
        Optional<Rating> ratingList = ratingService.getRatingById(id);
        assertFalse(ratingList.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnListOfRatingPage() throws Exception {
        // get the rating list page
        mockMvc.perform(get("/rating/list")).andExpect(status().isOk()).andExpect(model().attributeExists("ratings"))
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithMockUser
    public void shouldReturnAddRatingPage() throws Exception {

        // Get the rating Add page
        mockMvc.perform(get("/rating/add")).andExpect(status().isOk()).andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser
    public void addRatingToRatingList() throws Exception {
        // New Object
        Rating ratingTest = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        // Bid List size
        int ratingSize = ratingService.getAllRatings().size();

        // perform post to add rating to rating List (in DB)
        mockMvc.perform(post("/rating/validate").flashAttr("rating", ratingTest)).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
        // Verify if rating is added to DB
        assertEquals(ratingService.getAllRatings().size(), ratingSize + 1);
        // Delete rating from DB
        Integer id = ratingTest.getId();
        ratingService.deleteRating(ratingTest);
        Optional<Rating> rating = ratingService.getRatingById(id);
        assertFalse(rating.isPresent());
    }

    @Test
    @WithMockUser
    public void addRatingToRatingListWithEmptyFields() throws Exception {
        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/rating/validate").param("moodysRating", "").param("sandPRating", "")
                .param("fitchRating", "").param("orderNumber", "")).andExpect(status().isOk())
                .andExpect(view().name("rating/add")).andExpect(model().attributeHasFieldErrors("rating",
                        "moodysRating", "sandPRating", "fitchRating", "orderNumber"));
    }

    @Test
    @WithMockUser
    public void shouldReturnRatingUpdatePage() throws Exception {
        // New object
        Rating ratingTest = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        // Save
        ratingTest = ratingService.addRating(ratingTest);
        assertNotNull(ratingTest.getId());
        assertTrue(ratingTest.getOrderNumber() == 10);
        // get ID
        Integer id = ratingTest.getId();
        // Perform get to show update rating page
        mockMvc.perform(get("/rating/update/{id}", id)).andExpect(status().isOk())
                .andExpect(view().name("rating/update")).andExpect(model().attributeExists("rating"));

        // Delete
        ratingService.deleteRating(ratingTest);
        Optional<Rating> rating = ratingService.getRatingById(id);
        assertFalse(rating.isPresent());

    }

    @Test
    @WithMockUser
    public void updateRatingInRatingList() throws Exception {
        // New Object
        Rating ratingTest = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Save
        ratingTest = ratingService.addRating(ratingTest);
        assertNotNull(ratingTest.getId());
        assertTrue(ratingTest.getOrderNumber() == 10);
        // get ID
        Integer id = ratingTest.getId();

        // perform post to update rating
        mockMvc.perform(post("/rating/update/{id}", id).flashAttr("rating", ratingTest)
                .param("moodysRating", "Moodys Modify").param("sandPRating", "Sand PRating Modify")
                .param("fitchRating", "Fitch Rating Modify").param("orderNumber", "20"))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/rating/list"));

        // Verifying if there is modifications
        assertEquals(ratingTest.getMoodysRating(), "Moodys Modify");
        assertEquals(ratingTest.getSandPRating(), "Sand PRating Modify");
        assertEquals(ratingTest.getFitchRating(), "Fitch Rating Modify");
        assertEquals(ratingTest.getOrderNumber(), 20);

        // Delete
        ratingService.deleteRating(ratingTest);
        Optional<Rating> rating = ratingService.getRatingById(id);
        assertFalse(rating.isPresent());

    }

    @Test
    @WithMockUser
    public void updateRatingToRatingListWithEmptyFields() throws Exception {
        // new Object
        Rating ratingTest = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Save
        ratingTest = ratingService.addRating(ratingTest);
        assertNotNull(ratingTest.getId());
        assertTrue(ratingTest.getOrderNumber() == 10);
        // get ID
        Integer id = ratingTest.getId();

        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/rating/update/{id}", id).param("moodysRating", "").param("sandPRating", "")
                .param("fitchRating", "").param("orderNumber", "")).andExpect(status().isOk())
                .andExpect(view().name("rating/update")).andExpect(model().attributeHasFieldErrors("rating",
                        "moodysRating", "sandPRating", "fitchRating", "orderNumber"));
        // Verifying if there is no modifications
        assertEquals(ratingTest.getMoodysRating(), "Moodys Rating");
        assertEquals(ratingTest.getSandPRating(), "Sand PRating");
        assertEquals(ratingTest.getFitchRating(), "Fitch Rating");
        assertEquals(ratingTest.getOrderNumber(), 10);

        // delete
        ratingService.deleteRating(ratingTest);
        Optional<Rating> rating = ratingService.getRatingById(id);
        assertFalse(rating.isPresent());

    }

    @Test
    @WithMockUser
    public void deleteRatingInRatingList() throws Exception {
        // New Object
        Rating ratingTest = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

        // Save
        ratingTest = ratingService.addRating(ratingTest);
        assertNotNull(ratingTest.getId());
        assertTrue(ratingTest.getOrderNumber() == 10);
        // get ID
        Integer id = ratingTest.getId();

        // perform post to delete rating
        mockMvc.perform(get("/rating/delete/{id}", id).flashAttr("rating", ratingTest))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/rating/list"));

        // Verify if rating is Deleted
        Optional<Rating> rating = ratingService.getRatingById(id);
        assertFalse(rating.isPresent());

    }
}
