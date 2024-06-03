package edu.leicester.co2103.part1s2.controller;

import edu.leicester.co2103.part1s2.ErrorInfo;
import edu.leicester.co2103.part1s2.domain.Author;
import edu.leicester.co2103.part1s2.domain.Book;
import edu.leicester.co2103.part1s2.domain.Order;
import edu.leicester.co2103.part1s2.repo.AuthorRepository;
import edu.leicester.co2103.part1s2.repo.BookRepository;
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
public class BookRestController {
    @Autowired
    BookRepository brepo;

    //get all books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> listAllBooks() {
        List<Book> books = brepo.findAll();
        if (books.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }
    //create a book
    @PostMapping("/books")
    public ResponseEntity<?> createBook(@RequestBody Book book, UriComponentsBuilder ucBuilder) {
        if (brepo.existsById(book.getIsbn())) {
            return new ResponseEntity(new ErrorInfo("A Book with ISBN " + book.getIsbn() + " already exists."), HttpStatus.CONFLICT);
        }
        brepo.save(book);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/books/{isbn}").buildAndExpand(book.getIsbn()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //get a book
    @GetMapping("/books/{isbn}")
    public ResponseEntity<?> getBook(@PathVariable("isbn") String isbn) {
        Book book = brepo.findById(isbn).orElse(null);
        if (book == null) {
            return new ResponseEntity(new ErrorInfo("Book with ISBN " + isbn + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    //update (put) book
    @PutMapping("/books/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable("isbn") String isbn, @RequestBody Book book) {
        Book currentBook = brepo.findById(isbn).orElse(null);
        if (currentBook == null) {
            return new ResponseEntity(new ErrorInfo("Book with ISBN " + isbn + " not found."), HttpStatus.NOT_FOUND);
        }
        currentBook.setTitle(book.getTitle());
        currentBook.setPublicationYear(book.getPublicationYear());
        currentBook.setPrice(book.getPrice());
        currentBook.getAuthors().clear();
        currentBook.getOrders().clear();
        currentBook.getOrders().addAll(book.getOrders());
        currentBook.getAuthors().addAll(book.getAuthors());
        brepo.save(currentBook);
        return new ResponseEntity<Book>(currentBook, HttpStatus.OK);
    }

    //delete a specific book
    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable("isbn") String isbn) {
        Book currentBook = brepo.findById(isbn).orElse(null);
        if (currentBook == null) {
            return new ResponseEntity(new ErrorInfo("Book with ISBN " + isbn + " not found."), HttpStatus.NOT_FOUND);
        }
        brepo.delete(currentBook);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //get all authors of a specific book
    @GetMapping("books/{isbn}/authors")
    public ResponseEntity<?> ListAuthorsOfBook(@PathVariable("isbn") String isbn) {
        Book book = brepo.findById(isbn).orElse(null);
        List<Author> authors = new ArrayList<>();
        if(book!=null)
        {
            authors = book.getAuthors();
        }
        if (authors.isEmpty() || book==null) {
            return new ResponseEntity<>(new ErrorInfo("Book with isbn " + isbn + " does not have any authors"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
    }

    //get all orders containing a specific book
    @GetMapping("books/{isbn}/orders")
    public ResponseEntity<?> ListBooksOfAuthor(@PathVariable("isbn") String isbn) {
        Book book = brepo.findById(isbn).orElse(null);
        List<Order> orders = new ArrayList<>();
        if(book != null)
        {
            orders = book.getOrders();
        }
        if (orders.isEmpty() || book==null) {
            return new ResponseEntity<>(new ErrorInfo("Book with isbn " + isbn + " does not have any orders"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }



}
