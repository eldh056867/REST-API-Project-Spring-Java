package edu.leicester.co2103.part1s2.controller;

import edu.leicester.co2103.part1s2.ErrorInfo;
import edu.leicester.co2103.part1s2.domain.Author;
import edu.leicester.co2103.part1s2.domain.Book;

import edu.leicester.co2103.part1s2.exceptions.LanguageNotFoundException;
import edu.leicester.co2103.part1s2.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api")
public class AuthorRestController {
    @Autowired
    AuthorRepository arepo;

    //get all authors
    @GetMapping("/authors")
    public ResponseEntity<List<Author>> listAllAuthors() {
        List<Author> authors = arepo.findAll();
        if (authors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
    }
    //create an author
    @PostMapping("/authors")
    public ResponseEntity<?> createAuthor(@RequestBody Author author, UriComponentsBuilder ucBuilder) {
        if (arepo.existsById(author.getId())) {
            return new ResponseEntity(new ErrorInfo("An Author named " + author.getFullName() + " already exists."), HttpStatus.CONFLICT);
        }
        arepo.save(author);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/author/{id}").buildAndExpand(author.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    //get an author
    @GetMapping("/authors/{id}")
    public ResponseEntity<?> getAuthor(@PathVariable("id") long id) {
        Author author = arepo.findById(id).orElse(null);
        if (author == null) {
            return new ResponseEntity(new ErrorInfo("Author with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Author>(author, HttpStatus.OK);
    }
    //update (put) author
    @PutMapping("/authors/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) {
        Author currentAuthor = arepo.findById(id).orElse(null);
        if (currentAuthor == null) {
            return new ResponseEntity(new ErrorInfo("Author with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        currentAuthor.setFullName(author.getFullName());
        currentAuthor.setBirthYear(author.getBirthYear());
        currentAuthor.getBooks().clear();
        currentAuthor.getBooks().addAll(author.getBooks());
        arepo.save(currentAuthor);
        return new ResponseEntity<Author>(currentAuthor, HttpStatus.OK);
    }
    //delete a specific author
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") long id) {
        Author currentAuthor = arepo.findById(id).orElse(null);
        if (currentAuthor == null) {
            return new ResponseEntity(new ErrorInfo("Author with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        arepo.delete(currentAuthor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //get all books written by specific author.
    @GetMapping("authors/{id}/books")
    public ResponseEntity<?> ListBooksOfAuthor(@PathVariable("id") long id) {
        Author author = arepo.findById(id).orElse(null);
        List<Book> books = new ArrayList<>();
        if(author!=null)
        {
            books = author.getBooks();
        }
        if (books.isEmpty() || author == null) {
            return new ResponseEntity<>(new ErrorInfo("Author with id " + id + " does not have any books"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }


}
