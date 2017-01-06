package com.theironyard.repositories;

import com.theironyard.models.Book;
import com.theironyard.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by melmo on 12/29/16.
 */
public interface BookRepository extends JpaRepository<Book, Integer>{
    Book findByTitle(String title);

    Page<Book> findByStatus(String status, Pageable pageable);
    Page<Book> findAllByOrderByTitleAsc(Pageable pageable);
    Page<Book> findAllByOrderByAuthorAsc(Pageable pageable);
    Page<Book> findAllByOrderByYearAsc(Pageable pageable);

    Page<Book> findByUser(User user, Pageable pageable);
    Page<Book> findByUserAndStatus(User user, String status, Pageable pageable);
    Page<Book> findByUserOrderByTitleAsc(User user, Pageable pageable);
    Page<Book> findByUserOrderByAuthorAsc(User user, Pageable pageable);
    Page<Book> findByUserOrderByYearAsc(User user, Pageable pageable);
}
