package com.example.people.service;


import com.example.people.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PeopleRepository extends JpaRepository<Person, Long> {

    List<Person> findAll();

    Optional<Person> findByEmail(String email);

    Optional<Person> findById(Long id);

    void deleteById(Long id);
}
