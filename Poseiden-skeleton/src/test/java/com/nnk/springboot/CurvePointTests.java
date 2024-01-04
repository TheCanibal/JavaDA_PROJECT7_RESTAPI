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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointTests {

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void curvePointTest() {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

        // Save
        curvePoint = curvePointService.addCurvePoint(curvePoint);
        assertNotNull(curvePoint.getId());
        assertTrue(curvePoint.getCurveId() == 10);

        // Update
        curvePoint.setCurveId(20);
        curvePoint = curvePointService.addCurvePoint(curvePoint);
        assertTrue(curvePoint.getCurveId() == 20);

        // Find
        List<CurvePoint> listResult = curvePointService.getAllCurvePoints();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = curvePoint.getId();
        curvePointService.deleteCurvePoint(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointService.getCurveById(id);
        assertFalse(curvePointList.isPresent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnListOfCurvePointListPage() throws Exception {
        // get the Curve list page
        mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePoints")).andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithMockUser
    public void shouldReturnAddCurvePointPage() throws Exception {

        // Get the Curve Add page
        mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk()).andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser
    public void addCurvePointToCurvePointList() throws Exception {
        // New Object
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        // Curve List size
        int curvePointSize = curvePointService.getAllCurvePoints().size();

        // perform post to add Curve to Curve List (in DB)
        mockMvc.perform(post("/curvePoint/validate").flashAttr("curvePoint", curvePoint).with(csrf()))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/curvePoint/list"));
        // Verify if Curve is added to DB
        assertEquals(curvePointService.getAllCurvePoints().size(), curvePointSize + 1);
        // Delete Curve from DB
        Integer id = curvePoint.getCurveId();
        curvePointService.deleteCurvePoint(curvePoint);
        Optional<CurvePoint> curve = curvePointService.getCurveById(id);
        assertFalse(curve.isPresent());

    }

    @Test
    @WithMockUser
    public void addCurvePointToCurvePointListWithEmptyFields() throws Exception {
        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/curvePoint/validate").param("term", "").param("value", "").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "term", "value"));

    }

    @Test
    @WithMockUser
    public void shouldReturnUpdatePage() throws Exception {
        // New object
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        // Save
        curvePoint = curvePointService.addCurvePoint(curvePoint);
        assertNotNull(curvePoint.getId());
        assertEquals(curvePoint.getTerm(), 10d, 10d);
        assertEquals(curvePoint.getValue(), 30d, 30d);
        // Get ID
        Integer id = curvePoint.getId();
        // Perform get to show update Curve page
        mockMvc.perform(get("/curvePoint/update/{id}", id)).andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update")).andExpect(model().attributeExists("curvePoint"));

        // Delete
        curvePointService.deleteCurvePoint(curvePoint);
        Optional<CurvePoint> curve = curvePointService.getCurveById(id);
        assertFalse(curve.isPresent());

    }

    @Test
    @WithMockUser
    public void updateCurvePointInCurvePointList() throws Exception {
        // New Object
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

        // Save
        curvePoint = curvePointService.addCurvePoint(curvePoint);
        assertNotNull(curvePoint.getId());
        assertEquals(curvePoint.getTerm(), 10d, 10d);
        assertEquals(curvePoint.getValue(), 30d, 30d);
        // get ID
        Integer id = curvePoint.getId();

        // perform post to update Curve
        mockMvc.perform(post("/curvePoint/update/{id}", id).flashAttr("curvePoint", curvePoint).param("term", "20d")
                .param("value", "20d").with(csrf())).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));

        // Verifying if there is modifications
        assertEquals(curvePoint.getTerm(), 20d);
        assertEquals(curvePoint.getValue(), 20d);

        // Delete
        curvePointService.deleteCurvePoint(curvePoint);
        Optional<CurvePoint> curve = curvePointService.getCurveById(id);
        assertFalse(curve.isPresent());

    }

    @Test
    @WithMockUser
    public void updateCurvePointToCurvePointListWithEmptyFields() throws Exception {
        // new Object
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

        // Save
        curvePoint = curvePointService.addCurvePoint(curvePoint);
        assertNotNull(curvePoint.getId());
        assertEquals(curvePoint.getTerm(), 10d, 10d);
        assertEquals(curvePoint.getValue(), 30d, 30d);
        Integer id = curvePoint.getId();

        // perform post with empty fields and verify if there is errors
        mockMvc.perform(post("/curvePoint/update/{id}", id).param("term", "").param("value", "").with(csrf()))
                .andExpect(status().isOk()).andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "term", "value"));
        // Verifying if there is no modifications
        assertEquals(curvePoint.getTerm(), 10d);
        assertEquals(curvePoint.getValue(), 30d);

        // delete
        curvePointService.deleteCurvePoint(curvePoint);
        Optional<CurvePoint> curve = curvePointService.getCurveById(id);
        assertFalse(curve.isPresent());

    }

    @Test
    @WithMockUser
    public void deleteCurvePointInCurvePointList() throws Exception {
        // New Object
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

        // Save
        curvePoint = curvePointService.addCurvePoint(curvePoint);
        assertNotNull(curvePoint.getId());
        assertEquals(curvePoint.getTerm(), 10d, 10d);
        assertEquals(curvePoint.getValue(), 30d, 30d);
        // get ID
        Integer id = curvePoint.getId();

        // perform post to update Curve
        mockMvc.perform(get("/curvePoint/delete/{id}", id).flashAttr("curvePoint", curvePoint))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/curvePoint/list"));

        // Verify if Curve is Deleted
        Optional<CurvePoint> curve = curvePointService.getCurveById(id);
        assertFalse(curve.isPresent());

    }

}
