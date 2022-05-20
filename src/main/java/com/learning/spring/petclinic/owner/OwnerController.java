package com.learning.spring.petclinic.owner;

import com.learning.spring.petclinic.error.ErrorHandler;
import com.learning.spring.petclinic.error.ErrorResponse;
import com.learning.spring.petclinic.error.Messages;
import com.learning.spring.petclinic.error.OwnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OwnerController {

    private final OwnerRepo ownerRepo;

    @Autowired
    public OwnerController(OwnerRepo ownerRepo) {
        this.ownerRepo = ownerRepo;
    }


    @GetMapping("/owners")
    public List<Owner> getOwners(){
        return ownerRepo.findAll();
    }

    @GetMapping("/owners/{id}")
    public Owner getOwner(@PathVariable int id){
        return ownerRepo.findById(id)
                .orElseThrow(()->new OwnerNotFoundException(Messages.NOTFOUND));
    }

    @PostMapping("/owners")
    public ResponseEntity<ErrorResponse> postOwner(@Valid @RequestBody Owner owner){
        ownerRepo.save(owner);
        return ErrorHandler.reply(Messages.SAVED, HttpStatus.OK);
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<ErrorResponse> updateOwner(@PathVariable int id,@Valid @RequestBody Owner newOwner){
        Owner owner = ownerRepo.findById(id).orElseThrow(()-> new OwnerNotFoundException(Messages.NOTFOUND));
        owner.setFirstName(newOwner.getFirstName());
        owner.setLastName(newOwner.getLastName());
        owner.setPets(newOwner.getPets());
        ownerRepo.save(owner);
        return ErrorHandler.reply(Messages.UPDATED,HttpStatus.OK);
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<ErrorResponse> deleteOwner(@PathVariable int id){
        if(!ownerRepo.existsById(id)) {
            System.out.println("Owner exception");
            throw new OwnerNotFoundException(Messages.NOTFOUND);
        }
        ownerRepo.deleteById(id);
        return ErrorHandler.reply(Messages.DELETED,HttpStatus.OK);
    }



}
