package edu.leicester.co2103.part1s2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private long id;
    private String fullName;
    private int birthYear;
    private String nationality;

    @JsonIgnore
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Book> books = new ArrayList<>();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthyear) {
        this.birthYear = birthyear;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }
}