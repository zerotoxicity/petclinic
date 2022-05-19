package com.learning.spring.petclinic.vet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.spring.petclinic.vet.Vet;
import com.learning.spring.petclinic.vet.VetController;
import com.learning.spring.petclinic.vet.VetRepo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(VetController.class)
public class VetControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    public ObjectMapper mapper;

    @MockBean
    VetRepo vetRepo;

    private Vet sam(){
        Vet sam = new Vet("Radiology");
        sam.setFirstName("Sam");
        sam.setLastName("Smith");
        sam.setId(1);
        return sam;
    }

    private Vet john(){
        Vet john = new Vet("General medicine");
        john.setFirstName("John");
        john.setLastName("Doe");
        john.setId(2);
        return john;
    }

    @BeforeEach
    public void setup(){
        given(this.vetRepo.findAll()).willReturn(Lists.newArrayList(sam(), john()));
    }

    @Test
    public void getVetsTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[1].firstName", is("John")));
    }

    @Test
    public void getVetTest() throws Exception{
        Mockito.when(vetRepo.findById(john().getId())).thenReturn(java.util.Optional.of(john()));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vets/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.lastName",is("Doe")));
    }

    @Test
    public void postVetTest() throws Exception{
        Vet tempVet = new Vet("Entomology");
        tempVet.setId(3);
        tempVet.setFirstName("Jane");
        tempVet.setLastName("Seo");

        Mockito.when(vetRepo.save(tempVet)).thenReturn(tempVet);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/vets")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(tempVet));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.message",is("Vet has been saved")));
    }

    @Test
    public void updateVetTest() throws Exception{

    }

    @Test
    public void deleteVetTest() throws Exception{

    }

}
