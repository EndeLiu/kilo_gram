package com.kilo.gram.controller;

import com.kilo.gram.pojo.CustomNode;
import com.kilo.gram.pojo.Person;
import com.kilo.gram.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping("/{name}")
    public CustomNode findByName(@PathVariable("name") String name){
        //return personService.findByName(name);
        Person person = personService.findByName(name);
        return new CustomNode(person.getName(),1);
    }

    @GetMapping("/all")
    public List<CustomNode> findAll(){
        Iterable<Person> personIterable = personService.findAll();
        List<CustomNode> customNodes = new ArrayList<>();
        for(Person person : personIterable){
            customNodes.add(new CustomNode(person.getName(),1));
        }
        return customNodes;
    }

}
