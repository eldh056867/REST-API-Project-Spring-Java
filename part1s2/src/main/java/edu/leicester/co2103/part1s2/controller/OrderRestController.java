package edu.leicester.co2103.part1s2.controller;

import edu.leicester.co2103.part1s2.ErrorInfo;
import edu.leicester.co2103.part1s2.domain.Author;
import edu.leicester.co2103.part1s2.domain.Book;
import edu.leicester.co2103.part1s2.domain.Order;
import edu.leicester.co2103.part1s2.repo.BookRepository;
import edu.leicester.co2103.part1s2.repo.OrderRepository;
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
public class OrderRestController {
    @Autowired
    OrderRepository orepo;
    @Autowired
    BookRepository brepo;
    //get all orders
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> listAllOrders() {
        List<Order> orders = orepo.findAll();
        if (orders.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    //create an order
    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Order order, UriComponentsBuilder ucBuilder) {
        if (orepo.existsById(order.getId())) {
            return new ResponseEntity(new ErrorInfo("An Order with ID " + order.getId() + " already exists."), HttpStatus.CONFLICT);
        }
        orepo.save(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //get an order
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") long id) {
        Order order = orepo.findById(id).orElse(null);
        if (order == null) {
            return new ResponseEntity(new ErrorInfo("Order with ID " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    //update (put) order
    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") long id, @RequestBody Order order) {
        Order currentOrder = orepo.findById(id).orElse(null);
        if (currentOrder == null) {
            return new ResponseEntity(new ErrorInfo("Order with ID " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        currentOrder.setCustomerName(order.getCustomerName());
        currentOrder.setDateTime(order.getDateTime());
        currentOrder.getBooks().clear();
        currentOrder.getBooks().addAll(order.getBooks());
        orepo.save(currentOrder);
        return new ResponseEntity<Order>(currentOrder, HttpStatus.OK);
    }
    //get all books of a specific order
    @GetMapping("orders/{id}/books")
    public ResponseEntity<?> ListAuthorsOfBook(@PathVariable("id") long id) {
        Order order = orepo.findById(id).orElse(null);
        List<Book> books = new ArrayList<>();
        if (order!=null)
        {
            books = order.getBooks();
        }
        if (books.isEmpty() || order == null) {
            return new ResponseEntity<>(new ErrorInfo("Order with id " + id + " does not have any books"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    //add a book to existing order
    @PostMapping("/orders/{id}/books")
    public ResponseEntity<?> AddBookToExistingOrder(@PathVariable("id") long id, @RequestBody Book book, UriComponentsBuilder ucBuilder) {
        brepo.save(book);
        Order order = orepo.findById(id).orElse(null);
        List<Book> books = order.getBooks();
        if (books.contains(book)) {
            return new ResponseEntity<>(new ErrorInfo("Order with id " + id + " already contains that book"), HttpStatus.CONFLICT);
        }
        books.add(book);
        orepo.save(order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/orders/{id}/books").buildAndExpand(order.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    //delete a book from existing order
    @DeleteMapping("/orders/{id}/books/{isbn}")
    public ResponseEntity<?> deleteBookFromExistingOrder(@PathVariable("id") long id, @PathVariable("isbn") String isbn) {
        Book book = brepo.findById(isbn).orElse(null);
        Order order = orepo.findById(id).orElse(null);
        order.getBooks().remove(book);
        if (book == null) {
            return new ResponseEntity(new ErrorInfo("Book with isbn " + isbn + " not found."), HttpStatus.NOT_FOUND);
        }
        if (order == null) {
            return new ResponseEntity(new ErrorInfo("Order with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        orepo.save(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
