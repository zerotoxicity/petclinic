package com.learning.spring.petclinic.owner;

import com.learning.spring.petclinic.entity.BaseEntity;
import com.learning.spring.petclinic.owner.Owner;

import javax.persistence.*;

@Entity
@Table(name="pet")
public class Pet extends BaseEntity {

    @Column(name = "pet_name")
    private String name;

    public Pet(){}

    public Pet(String name, Owner owner){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
