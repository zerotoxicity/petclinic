package com.learning.spring.petclinic.vet;

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

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(VetController.class)
@AutoConfigureMockMvc
public class VetControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper mapper;

    @MockBean
    VetRepo vetRepo;

    private Vet sam() {
        Vet sam = new Vet("Radiology");
        sam.setFirstName("Sam");
        sam.setLastName("Smith");
        sam.setId(1);
        return sam;
    }

    private Vet john() {
        Vet john = new Vet("General medicine");
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setId(2);
        return john;
    }

    @BeforeEach
    public void setup() {
        given(this.vetRepo.findAll()).willReturn(Lists.newArrayList(sam(), john()));
    }

    /**
     * Desc: GET all data from /api/vets
     *
     * Expected result:
     * Status - 200
     * Retrieves 2 entries
     * @throws Exception
     */
    @Test
    public void getVetsTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].firstName", is("John")));
    }

    /**
     * Desc: GET John's data from /api/vets
     *
     * Expected result:
     * Status - 200
     * Retrieves John's data
     * @throws Exception
     */
    @Test
    public void getVetTest_Success() throws Exception {
        Mockito.when(vetRepo.findById(john().getId())).thenReturn(java.util.Optional.of(john()));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vets/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }
    /**
     * Desc: POST 'tempVet' to /api/vets
     *
     * Expected result:
     * Status - 200
     * 'tempVet' posted
     * @throws Exception
     */
    @Test
    public void postVetTest_Success() throws Exception {
        Vet tempVet = new Vet("Entomology");
        tempVet.setId(3);
        tempVet.setFirstName("Jane");
        tempVet.setLastName("Seo");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/vets")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tempVet));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", is(ErrorMessages.SAVED)));
    }

    /**
     * Desc: Update "John" to "Thomas" through "/api/vets/ + john's id"
     *
     * Expected result:
     * Status - 200
     * John updated to Thomas
     * @throws Exception
     */
    @Test
    public void updateVetTest_Success() throws Exception {
        Vet tempVet = john();
        tempVet.setFirstName("Thomas");

        Mockito.when(vetRepo.findById(john().getId())).thenReturn(java.util.Optional.of(john()));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/vets/"+john().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tempVet));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(ErrorMessages.UPDATED)));
    }


    /**
     * Desc: Update "John" to "Thomas+" through "/api/vets/ + john's id"
     *
     * Expected result:
     * Status - 400
     * Validation fails due to failing the regex
     * @throws Exception
     */
    @Test
    public void updateVetTest_WrongText() throws Exception {
        Vet tempVet = john();
        tempVet.setFirstName("Thomas+");

        Mockito.when(vetRepo.findById(john().getId())).thenReturn(java.util.Optional.of(john()));


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/vets/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tempVet));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(ErrorMessages.INVALID_INPUT)));
    }

    /**
     * Desc: DELETE Vet with the id 5
     *
     * Expected result:
     * Status - 404
     * Entity not found
     * @throws Exception
     */
    @Test
    public void deleteVetTest_NotFound() throws Exception {
        Mockito.when(vetRepo.findById(5)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/vets/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
