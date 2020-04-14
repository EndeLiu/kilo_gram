package com.kilo.gram.controller;

import com.kilo.gram.pojo.CustomLink;
import com.kilo.gram.pojo.CustomNode;
import com.kilo.gram.pojo.Movie;
import com.kilo.gram.pojo.Person;
import com.kilo.gram.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping("/{name}")
    public CustomNode findByName(@PathVariable("name") String name){
        //return personService.findByName(name);
        Person person = personService.findByName(name);
        return new CustomNode(person.getName(),1, person);
    }

    @GetMapping("/query/{relationship}/{name}")
    public List<CustomNode> getActedByName(@PathVariable("relationship") String rel,@PathVariable("name") String name){
        Person person = personService.findByName(name);
        List<CustomNode> customNodes = new ArrayList<>();
        Set<Movie> movieSet = personService.getMoviesByRelationship(person,rel);
        
        for(Movie movie : movieSet){
            customNodes.add(new CustomNode(movie.getTitle(),2, movie));
            //customLinks.add(new CustomLink(person.getName(),movie.getTitle(),5));
        }
        return customNodes;
    }

    @GetMapping("/all")
    public List<CustomNode> findAll(){
        Iterable<Person> personIterable = personService.findAll();
        List<CustomNode> customNodes = new ArrayList<>();
        for(Person person : personIterable){
            customNodes.add(new CustomNode(person.getName(),1, person));
        }
        return customNodes;
    }

}
