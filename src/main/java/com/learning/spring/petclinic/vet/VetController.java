package com.learning.spring.petclinic.vet;


import com.learning.spring.petclinic.error.ErrorHandler;
import com.learning.spring.petclinic.error.ErrorResponse;
import com.learning.spring.petclinic.error.Messages;
import com.learning.spring.petclinic.error.VetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
                .orElseThrow(()->new VetNotFoundException(Messages.NOTFOUND));
    }

    @PostMapping("/vets")
    public ResponseEntity<ErrorResponse> postVet(@Valid @RequestBody Vet vet){
         vetRepo.save(vet);
        return ErrorHandler.reply(Messages.SAVED,HttpStatus.OK);
    }

    @PutMapping("/vets/{id}")
    public ResponseEntity<ErrorResponse> updateVet(@PathVariable int id,@Valid @RequestBody Vet newVet){
        Vet vet = vetRepo.findById(id).orElseThrow(()-> new VetNotFoundException(Messages.NOTFOUND));
        vet.setFirstName(newVet.getFirstName());
        vet.setLastName(newVet.getLastName());
        vet.setSpecialty(newVet.getSpecialty());
        vetRepo.save(vet);
        return ErrorHandler.reply(Messages.UPDATED,HttpStatus.OK);
    }

    @DeleteMapping("/vets/{id}")
    public ResponseEntity<ErrorResponse> deleteVet(@PathVariable int id){
        if(!vetRepo.existsById(id)) throw new VetNotFoundException(Messages.NOTFOUND);
        vetRepo.deleteById(id);
        return ErrorHandler.reply(Messages.DELETED,HttpStatus.OK);
    }



}
