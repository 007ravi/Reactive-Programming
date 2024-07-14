package com.example.reactive.programming.controllers;

import com.example.reactive.programming.entities.Book;
import com.example.reactive.programming.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    //create
    @PostMapping
    public Mono<Book>create(@RequestBody Book book){
        return bookService.create(book);
    }

    //get all books
    @GetMapping
    public Flux<Book>getAll(){
        return bookService.getAll();
    }

    @GetMapping("/{bId}")
    public Mono<Book>get(@PathVariable("bId") int bookId){
return bookService.get(bookId);
    }

    @PutMapping("/{bId}")
    public Mono<Book>update (@RequestBody Book book,@PathVariable int bId){
        return bookService.update(book,bId);
    }

    @DeleteMapping("/{bId}")
    public Mono delete(@PathVariable int bId){
        return bookService.delete(bId);
    }

}
