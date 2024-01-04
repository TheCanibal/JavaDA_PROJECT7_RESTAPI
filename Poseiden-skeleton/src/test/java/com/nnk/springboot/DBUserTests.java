package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.services.DBUserService;

@SpringBootTest
@AutoConfigureMockMvc
public class DBUserTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DBUserService dbUserService;

    private static BCryptPasswordEncoder encoder;

    private static String password;

    @BeforeAll
    public static void createEncoder() {
        encoder = new BCryptPasswordEncoder();
        password = encoder.encode("B7e5f49917!");
    }

    @Test
    public void bidListTest() {
        DBUser user = new DBUser("KevAdmin", password, "KevAdministrator", "ADMIN");

        // Save
        user = dbUserService.addUser(user);
        assertNotNull(user.getId());
        assertEquals(user.getFullname(), "KevAdministrator", "KevAdministrator");

        // Update
        user.setFullname("KevinAdministrator");
        user = dbUserService.updateUser(user);
        assertEquals(user.getFullname(), "KevinAdministrator", "KevinAdministrator");

        // Find
        List<DBUser> listResult = dbUserService.findAllUsers();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        dbUserService.deleteUser(user);
        Optional<DBUser> userToDelete = dbUserService.findUserById(id);
        assertFalse(userToDelete.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnListOfUserListPageAsAdmin() throws Exception {
        // get the user list page
        mockMvc.perform(get("/user/list")).andExpect(status().isOk()).andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnAddUserListPageAsAdmin() throws Exception {

        // Get the user Add page
        mockMvc.perform(get("/user/add")).andExpect(status().isOk()).andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addUserToUserListAsAdmin() throws Exception {
        // New Object with no encoded password because it tests the creation of the user
        DBUser user = new DBUser("KevAdmin", "B7e5f49917!", "KevAdministrator", "ADMIN");
        // user List size
        int userListSize = dbUserService.findAllUsers().size();

        // perform post to add user to user List (in DB)
        mockMvc.perform(post("/user/validate").flashAttr("DBUser", user).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
        // Verify if user is added to DB
        assertEquals(dbUserService.findAllUsers().size(), userListSize + 1);
        // verifying the password is encoded
        assertTrue(encoder.matches("B7e5f49917!", user.getPassword()));
        // Delete user from DB
        Integer id = user.getId();
        // delete
        dbUserService.deleteUser(user);
        Optional<DBUser> userToDelete = dbUserService.findUserById(id);
        assertFalse(userToDelete.isPresent());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addUserToUserListWithEmptyFieldsAsAdmin() throws Exception {
        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/user/validate").param("fullname", "").param("username", "").param("password", "")
                .param("role", "").with(csrf())).andExpect(status().isOk()).andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("DBUser", "fullname", "username", "password", "role"));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnUpdatePageAsAdmin() throws Exception {
        // New object
        DBUser user = new DBUser("KevAdmin", password, "KevAdministrator", "ADMIN");
        // Save
        user = dbUserService.addUser(user);
        assertNotNull(user.getId());
        assertEquals(user.getFullname(), "KevAdministrator", "KevAdministrator");
        // get ID
        Integer id = user.getId();
        // Perform get to show update user page
        mockMvc.perform(get("/user/update/{id}", id)).andExpect(status().isOk()).andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("DBUser"));

        // delete
        dbUserService.deleteUser(user);
        Optional<DBUser> userToDelete = dbUserService.findUserById(id);
        assertFalse(userToDelete.isPresent());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateUserInUserListAsAdmin() throws Exception {
        // New Object
        DBUser user = new DBUser("KevAdmin", password, "KevAdministrator", "ADMIN");

        // Save
        user = dbUserService.addUser(user);
        assertNotNull(user.getId());
        assertEquals(user.getFullname(), "KevAdministrator", "KevAdministrator");
        // get ID
        Integer id = user.getId();
        // perform post to update user(roles = "ADMIN")
        mockMvc.perform(
                post("/user/update/{id}", id).flashAttr("DBUser", user).param("fullname", "KevAdministratorModify")
                        .param("username", "KevAdminModify").param("password", "B7e5f49917@").param("role", "ADMIN")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/user/list"));

        // Verifying if there is modifications
        assertEquals(user.getFullname(), "KevAdministratorModify");
        assertEquals(user.getUsername(), "KevAdminModify");
        // verifying the updated password is encoded
        assertTrue(encoder.matches("B7e5f49917@", user.getPassword()));

        // delete
        dbUserService.deleteUser(user);
        Optional<DBUser> userToDelete = dbUserService.findUserById(id);
        assertFalse(userToDelete.isPresent());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateUserToUserListWithEmptyFieldsAsAdmin() throws Exception {
        // new Object
        DBUser user = new DBUser("KevAdmin", password, "KevAdministrator", "ADMIN");

        // Save
        user = dbUserService.addUser(user);
        assertNotNull(user.getId());
        assertEquals(user.getFullname(), "KevAdministrator", "KevAdministrator");
        // get ID
        Integer id = user.getId();

        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/user/update/{id}", id).param("fullname", "").param("username", "").param("password", "")
                .param("role", "").with(csrf())).andExpect(status().isOk()).andExpect(view().name("user/update"))
                .andExpect(model().attributeHasFieldErrors("DBUser", "fullname", "username", "password", "role"));
        // Verifying if there is no modifications
        assertEquals(user.getFullname(), "KevAdministrator");
        assertEquals(user.getUsername(), "KevAdmin");
        // verifying the password is always encoded
        assertTrue(encoder.matches("B7e5f49917!", user.getPassword()));

        // delete
        dbUserService.deleteUser(user);
        Optional<DBUser> userToDelete = dbUserService.findUserById(id);
        assertFalse(userToDelete.isPresent());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteUserInUserListAsAdmin() throws Exception {
        // New Object
        DBUser user = new DBUser("KevAdmin", password, "KevAdministrator", "ADMIN");

        // Save
        user = dbUserService.addUser(user);
        assertNotNull(user.getId());
        assertEquals(user.getFullname(), "KevAdministrator", "KevAdministrator");
        // get ID
        Integer id = user.getId();

        // perform post to update user
        mockMvc.perform(get("/user/delete/{id}", id).flashAttr("user", user)).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        // Verify if user is Deleted
        Optional<DBUser> userToDelete = dbUserService.findUserById(id);
        assertFalse(userToDelete.isPresent());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldReturnAccessDeniedPageAsUserInsteadOfUserList() throws Exception {
        // get the user list page
        mockMvc.perform(get("/user/list")).andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldReturnAccessDeniedPageAsUserInsteadOfUserAdd() throws Exception {

        // Get the user Add page
        mockMvc.perform(get("/user/add")).andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/access-denied"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void shouldReturnAccessDeniedPageAsUser() throws Exception {
        // New object
        DBUser user = new DBUser("KevAdmin", password, "KevAdministrator", "ADMIN");
        // Save
        user = dbUserService.addUser(user);
        assertNotNull(user.getId());
        assertEquals(user.getFullname(), "KevAdministrator", "KevAdministrator");
        // get ID
        Integer id = user.getId();
        // Perform get to show update user page
        mockMvc.perform(get("/user/update/{id}", id)).andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/access-denied"));

        // delete
        dbUserService.deleteUser(user);
        Optional<DBUser> userToDelete = dbUserService.findUserById(id);
        assertFalse(userToDelete.isPresent());

    }

}
