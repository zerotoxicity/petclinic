package com.learning.spring.petclinic.entity;

import com.learning.spring.petclinic.error.Messages;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.learning.spring.petclinic.error.Messages.REGEX_ALPHANUMERIC_SPACE;

@MappedSuperclass
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @Pattern(regexp=REGEX_ALPHANUMERIC_SPACE)
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp=REGEX_ALPHANUMERIC_SPACE)
    @NotEmpty
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }
}
