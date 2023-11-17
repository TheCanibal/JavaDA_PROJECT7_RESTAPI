package com.nnk.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnHomePage() throws Exception {
	mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAdminHomePage() throws Exception {
	mockMvc.perform(get("/admin/home")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnUserListPageAsAdmin() throws Exception {
	mockMvc.perform(get("/secure/article-details")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnAccessDenidedPage() throws Exception {
	mockMvc.perform(get("/access-denied")).andExpect(status().is3xxRedirection());
    }

}
