package com.example.people.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="PEOPLE")
public class Person {

    @Id
    @Column(name="id")
    @JsonProperty(value="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    @JsonProperty(value="firstName")
    private String firstName;

    @Column(name="last_name")
    @JsonProperty(value="lastName")
    private String lastName;

    @Column(name="dob")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty(value="dob")
    private Date dob;

    @Transient
    @JsonProperty(value="age")
    private int age;

    @Column(name="email")
    @JsonProperty(value="email")
    private String email;

    public Person(){}

    public Person(Long id, String firstName, String lastName, Date dob, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return firstName+" "+lastName;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        long diff = new Date().getTime() - getDob().getTime();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(diff);
        return c.get(Calendar.YEAR)-1970;
    }

    public void setAge(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
