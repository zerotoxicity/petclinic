package com.learning.spring.petclinic.vet;


import com.learning.spring.petclinic.entity.Person;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import static com.learning.spring.petclinic.error.ErrorMessages.REGEX_ALPHANUMERIC_SPACE;

@Entity
@Table(name="vet")
public class Vet extends Person {

    @Column(name = "specialty")
    @Pattern(regexp=REGEX_ALPHANUMERIC_SPACE)
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
