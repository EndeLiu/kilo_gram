package com.kilo.gram.service;

import com.kilo.gram.dao.PersonDao;
import com.kilo.gram.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
