package com.learning.spring.petclinic.vet;

import com.learning.spring.petclinic.vet.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepo extends JpaRepository<Vet,Integer> {
}
