package com.learning.spring.petclinic.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static com.learning.spring.petclinic.error.Messages.REGEX_ALPHANUMERIC_SPACE;

@MappedSuperclass
public class Person extends BaseEntity {

    @Column(name = "first_name")
    @Pattern(regexp=REGEX_ALPHANUMERIC_SPACE)
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp=REGEX_ALPHANUMERIC_SPACE)
    @NotEmpty
    private String lastName;

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
