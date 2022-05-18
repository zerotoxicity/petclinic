package com.learning.spring.petclinic.vet;


import com.learning.spring.petclinic.entity.Person;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="vet")
public class Vet extends Person {
    @NotNull
    @Column(name = "specialty")
    private String specialty;

    public Vet(String specialty){
        this.specialty=specialty;
    }

    public Vet(){}

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
