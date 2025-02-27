package com.danieloliveira.restwithspringbootandjava.repositories;

import com.danieloliveira.restwithspringbootandjava.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
}