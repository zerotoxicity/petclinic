package com.learning.spring.petclinic.owner;

import com.learning.spring.petclinic.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepo extends JpaRepository<Owner,Integer> {
}
