package com.learning.spring.petclinic.vet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VetController {
    private final VetRepo vetRepo;

    @Autowired
    public VetController(VetRepo vetRepo){
        this.vetRepo = vetRepo;
    }
    @GetMapping("/vets")
    private List<Vet> getVets(){
        return vetRepo.findAll();
    }

    @GetMapping("/vets/{id}")
    private Vet getVet(@PathVariable int id){
        return vetRepo.findById(id)
                .orElseThrow(()->new VetNotFoundException("Vet id: "+id+" not found"));
    }



}
