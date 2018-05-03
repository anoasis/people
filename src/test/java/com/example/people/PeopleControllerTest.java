package com.example.people;

import com.example.people.controller.PeopleController;
import com.example.people.model.Person;
import com.example.people.service.PeopleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PeopleController peopleController;

    @MockBean
    private PeopleRepository peopleRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(peopleController).build();
    }

    String payload = "{\"firstName\":\"first\",\"lastName\":\"last\",\"dob\":\"1990-01-01\",\"email\":\"my@email.com\"}";
    String malformedPayload = "{firstName\"first\",\"lastName\":\"last\",\"dob\":\"1990-01-01\",\"email\":\"my@email.com\"}";

    @Test
    public void findAll() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(1990,0,0);
        Person expected = new Person(new Long(1),"first","last", c.getTime(), "my@email.com");
        List list = new ArrayList<Person>();
        list.add(expected);

        given(this.peopleRepository.findAll()).willReturn(list);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/people")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(200));

    }

    @Test
    public void create() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(1990,0,0);
        Person expected = new Person(new Long(1),"first","last", c.getTime(), "my@email.com");

        Mockito.when(peopleRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(peopleRepository.save(Mockito.any(Person.class))).thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/people")
                .accept(MediaType.APPLICATION_JSON).content(payload)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(201));
    }

    @Test
    public void duplicateEmailCheckWhileCreate() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(1990,0,0);
        Person expected = new Person(new Long(1),"first","last", c.getTime(), "my@email.com");

        Mockito.when(peopleRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(expected));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/people")
                                        .accept(MediaType.APPLICATION_JSON).content(payload)
                                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(409));
    }

    @Test
    public void get() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/people/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(200));
    }

    @Test
    public void update() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/people/1")
                .accept(MediaType.APPLICATION_JSON).content(payload)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(200));
    }

    @Test
    public void passingMalformedPayloadOnUpdate() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/people/1")
                .accept(MediaType.APPLICATION_JSON).content(malformedPayload)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(400));
    }

    @Test
    public void delete() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(1990,0,0);
        Person expected = new Person(new Long(1),"first","last", c.getTime(), "my@email.com");

        Mockito.when(peopleRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/people/1")
                .accept(MediaType.APPLICATION_JSON).content(payload)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(204));
    }

    @Test
    public void passingNotExistingIdOnDelete() throws Exception {

        Mockito.when(peopleRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/people/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().is(422));
    }

}