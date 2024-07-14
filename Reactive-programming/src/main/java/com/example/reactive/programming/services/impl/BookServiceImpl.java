package com.example.reactive.programming.services.impl;

import com.example.reactive.programming.entities.Book;
import com.example.reactive.programming.repositories.BookRepository;
import com.example.reactive.programming.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public Mono<Book> create(Book book) {
        Mono<Book>createdBook=bookRepository.save(book);
        return createdBook;
    }

    @Override
    public Flux<Book> getAll() {
        return bookRepository.findAll().delayElements(Duration.ofSeconds(4)).doOnNext(data->System.out.println(data.getName()+" onnext called"));
    }

    @Override
    public Mono<Book> get(int id) {
        Mono<Book>book=bookRepository.findById(id);
        return book;
    }

    @Override
    public Mono<Book> update(Book book, int bookId) {
        Mono<Book>oldBook=bookRepository.findById(bookId);
        oldBook.flatMap(book1 -> {
            book1.setAuthor(book.getAuthor());
            book1.setName(book.getName());
            book1.setDescription(book.getDescription());
            book1.setPublisher(book.getPublisher());
            return bookRepository.save(book1);
        });
        return bookRepository.findById(bookId);
    }

    @Override
    public Mono<Void> delete(int bookId) {
      return bookRepository
              .findById(bookId)
              .flatMap(book -> bookRepository.delete(book));

    }

    @Override
    public Flux<Book> search(String query) {
        return null;
    }
}
