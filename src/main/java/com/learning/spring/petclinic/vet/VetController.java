package com.learning.spring.petclinic.vet;


import com.learning.spring.petclinic.error.ErrorHandler;
import com.learning.spring.petclinic.error.ErrorResponse;
import com.learning.spring.petclinic.error.ErrorMessages;
import com.learning.spring.petclinic.error.VetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Vet REST controller
 * Endpoint: /api/vets
 */
@RestController
@RequestMapping("/api")
public class VetController {
    private final VetRepo vetRepo;

    @Autowired
    public VetController(VetRepo vetRepo){
        this.vetRepo = vetRepo;
    }

    @GetMapping("/vets")
    public List<Vet> getVets(){
        return vetRepo.findAll();
    }

    @GetMapping("/vets/{id}")
    public Vet getVet(@PathVariable int id){
        return vetRepo.findById(id)
                .orElseThrow(()->new VetNotFoundException(ErrorMessages.NOTFOUND));
    }

    @PostMapping("/vets")
    public ResponseEntity<ErrorResponse> postVet(@Valid @RequestBody Vet vet){
         vetRepo.save(vet);
        return ErrorHandler.reply(ErrorMessages.SAVED,HttpStatus.OK);
    }

    @PutMapping("/vets/{id}")
    public ResponseEntity<ErrorResponse> updateVet(@PathVariable int id,@Valid @RequestBody Vet newVet){
        Vet vet = vetRepo.findById(id).orElseThrow(()-> new VetNotFoundException(ErrorMessages.NOTFOUND));
        vet.setFirstName(newVet.getFirstName());
        vet.setLastName(newVet.getLastName());
        vet.setSpecialty(newVet.getSpecialty());
        vetRepo.save(vet);
        return ErrorHandler.reply(ErrorMessages.UPDATED,HttpStatus.OK);
    }

    @DeleteMapping("/vets/{id}")
    public ResponseEntity<ErrorResponse> deleteVet(@PathVariable int id){
        if(!vetRepo.existsById(id)) throw new VetNotFoundException(ErrorMessages.NOTFOUND);
        vetRepo.deleteById(id);
        return ErrorHandler.reply(ErrorMessages.DELETED,HttpStatus.OK);
    }



}
