package com.theironyard.repositories;

import com.theironyard.models.Book;
import com.theironyard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by melmo on 12/29/16.
 */
public interface BookRepository extends JpaRepository<Book, Integer>{
    List<Book> findByUser(User user);
    List<Book> findByUserAndStatus(User user, String status);
    List<Book> findByStatus(String status);
    List<Book> findAllByOrderByTitleAsc();
    List<Book> findAllByOrderByAuthorAsc();
    List<Book> findAllByOrderByYearAsc();
    List<Book> findByUserOrderByTitleAsc(User user);
    List<Book> findByUserOrderByAuthorAsc(User user);
    List<Book> findByUserOrderByYearAsc(User user);
}
