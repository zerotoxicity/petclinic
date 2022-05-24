package com.learning.spring.petclinic.owner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.spring.petclinic.error.ErrorMessages;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@AutoConfigureMockMvc
public class OwnerControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper mapper;

    @MockBean
    OwnerRepo ownerRepo;

    private Owner joe(){
        Owner joe = new Owner();
        joe.setId(1);
        joe.setFirstName("Joe");
        joe.setLastName("M");
        List<Pet> petList = new ArrayList<>();
        petList.add(new Pet("Milo"));
        joe.setPets(petList);
        return joe;
    }

    private Owner kim(){
        Owner kim = new Owner();
        kim.setId(2);
        kim.setFirstName("Kim");
        kim.setLastName("S");
        List<Pet> petList = new ArrayList<>();
        petList.add(new Pet("Snow"));
        kim.setPets(petList);
        return kim;
    }

    private Owner tempOwner(){
        List<Pet> pets = new ArrayList<>();
        pets.add(new Pet("Test"));
        Owner tempOwner = new Owner(pets);
        tempOwner.setId(3);
        tempOwner.setFirstName("Kevin");
        tempOwner.setLastName("Nguyen");
        return tempOwner;
    }

    @BeforeEach
    public void setup() {
        given(this.ownerRepo.findAll()).willReturn(Lists.newArrayList(joe(), kim()));
    }


    /**
     * Desc: GET all data from /api/owners
     *
     * Expected result:
     * Status - 200
     * Retrieves 2 entries
     * @throws Exception
     */
    @Test
    public void getOwnersTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Joe")))
                .andExpect(jsonPath("$[0].pets",hasSize(1)));
    }

    /**
     * Desc: GET Kim's data from /api/owners
     *
     * Expected result:
     * Status - 200
     * Retrieves Kim's data
     * @throws Exception
     */
    @Test
    public void getVetTest_Success() throws Exception {
        Mockito.when(ownerRepo.findById(kim().getId())).thenReturn(java.util.Optional.of(kim()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/owners/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is("S")));
    }

    /**
     * Desc: GET Owner with the id 3's data from /api/owners
     *
     * Expected result:
     * Status - 404
     * Entity not found
     * @throws Exception
     */
    @Test
    public void getVetTest_NotFound() throws Exception {
        Mockito.when(ownerRepo.findById(kim().getId())).thenReturn(java.util.Optional.of(kim()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/owners/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is(ErrorMessages.NOTFOUND)));
    }

    /**
     * Desc: POST 'tempOwner' to /api/owner
     *
     * Expected result:
     * Status - 200
     * 'tempOwner' posted
     * @throws Exception
     */
    @Test
    public void postVetTest_Success() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tempOwner()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", is(ErrorMessages.SAVED)));
    }

    /**
     * Desc: POST 'tempVet' without any pets to /api/vets
     *
     * Expected result:
     * Status - 400
     * Validation fails due to no pets
     * @throws Exception
     */
    @Test
    public void postOwnerTest_NoPets() throws Exception {
        Owner temp = tempOwner();
        temp.setPets(null);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(temp));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",is(ErrorMessages.INVALID_INPUT)));
    }


    /**
     * Desc: Update "Joe" to "Sam" through "/api/vets/ + joe's id"
     *
     * Expected result:
     * Status - 200
     * Joe updated to Sam
     * @throws Exception
     */
    @Test
    public void updateOwnerTest_Success() throws Exception {
        Owner tempOwner = joe();
        tempOwner.setFirstName("Sam");

        Mockito.when(ownerRepo.findById(joe().getId())).thenReturn(java.util.Optional.of(joe()));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/owners/"+ joe().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tempOwner));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ErrorMessages.UPDATED)));
    }


    /**
     * Desc: DELETE owner with the id 3
     * Expected result:
     * Status - 404
     * Entity not found
     * @throws Exception
     */
    @Test
    public void deleteOwnerTest_NotFound() throws Exception {
        Mockito.when(ownerRepo.findById(3)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/owners/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
//