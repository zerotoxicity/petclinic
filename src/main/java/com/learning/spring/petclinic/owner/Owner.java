package com.learning.spring.petclinic.owner;

import com.learning.spring.petclinic.entity.Person;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pet_owner")
public class Owner extends Person {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="owner_id")
    private List<Pet> pets;

    public Owner(){}

    public Owner(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void addPets(Pet pet){
        if (pets==null) pets= new ArrayList<>();
        pets.add(pet);
    }
}
