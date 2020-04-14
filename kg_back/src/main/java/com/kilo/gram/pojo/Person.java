package com.kilo.gram.pojo;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long born;


    @Relationship(type = "ACTED_IN", direction = Relationship.OUTGOING)
    private Set<Movie> actedInMovie = new HashSet<>();

    @Relationship(type = "PRODUCED", direction = Relationship.OUTGOING)
    private Set<Movie> producedMovie = new HashSet<>();

    @Relationship(type = "DIRECTED", direction = Relationship.OUTGOING)
    private Set<Movie> directedMovie = new HashSet<>();

    @Relationship(type = "WROTE", direction = Relationship.OUTGOING)
    private Set<Movie> wroteMovie = new HashSet<>();

    @Relationship(type = "REVIEWED", direction = Relationship.OUTGOING)
    private Set<Movie> reviewedMovie = new HashSet<>();



    public Person(){

    }

    public Person(String name, Long born) {
        this.name = name;
        this.born = born;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBorn() {
        return born;
    }

    public void setBorn(Long born) {
        this.born = born;
    }

    public Set<Movie> getActedInMovie() {
        return actedInMovie;
    }

    public void setActedInMovie(Set<Movie> actedInMovie) {
        this.actedInMovie = actedInMovie;
    }

    public Set<Movie> getProducedMovie() {
        return producedMovie;
    }

    public void setProducedMovie(Set<Movie> producedMovie) {
        this.producedMovie = producedMovie;
    }

    public Set<Movie> getDirectedMovie() {
        return directedMovie;
    }

    public void setDirectedMovie(Set<Movie> directedMovie) {
        this.directedMovie = directedMovie;
    }

    public Set<Movie> getWroteMovie() {
        return wroteMovie;
    }

    public void setWroteMovie(Set<Movie> wroteMovie) {
        this.wroteMovie = wroteMovie;
    }

    public Set<Movie> getReviewedMovie() {
        return reviewedMovie;
    }

    public void setReviewedMovie(Set<Movie> reviewedMovie) {
        this.reviewedMovie = reviewedMovie;
    }
}
