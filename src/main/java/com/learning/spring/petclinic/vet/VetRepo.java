package com.learning.spring.petclinic.vet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepo extends JpaRepository<Vet,Integer> {
}
