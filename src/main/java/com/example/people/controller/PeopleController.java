package com.example.people.controller;

import com.example.people.model.Person;
import com.example.people.service.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRepository;

    public PeopleController(PeopleRepository peopleService) {
        this.peopleRepository = peopleService;
    }

    @GetMapping(value = "")
    public List<Person> all() {
        return this.peopleRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Person> get(@PathVariable(value = "id") Long id) {
        return this.peopleRepository.findById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody Person person) {
        if(this.peopleRepository.findByEmail(person.getEmail()).isPresent()){
            return ResponseEntity.status(409).body("duplicate email exists");
        }else{
            Person created = this.peopleRepository.save(person);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(created.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@RequestBody Person person) {
        try {
            this.peopleRepository.save(person);
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        if(this.peopleRepository.findById(id).isPresent()){
            this.peopleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

}