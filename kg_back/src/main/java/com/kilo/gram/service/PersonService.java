package com.kilo.gram.service;

import com.kilo.gram.dao.PersonDao;
import com.kilo.gram.pojo.Movie;
import com.kilo.gram.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PersonService {
    @Autowired
    PersonDao personDao;

    public Person findByName(String name){
        return personDao.findByName(name);
    }

    public Iterable<Person> findAll(){
        return  personDao.findAll();
    }

    public Set<Movie> getMoviesByRelationship(Person person, String rel){
        if(rel.equals("actedby")){
            return person.getActedInMovie();
        }
        else if(rel.equals("directed")){
            return person.getDirectedMovie();
        }
        else if(rel.equals("wrote")){
            return person.getWroteMovie();
        }
        else if(rel.equals("reviewed")){
            return person.getReviewedMovie();
        }
        else if(rel.equals("produced")){
            return person.getProducedMovie();
        }
        else{
            return null;
        }
    }

}
