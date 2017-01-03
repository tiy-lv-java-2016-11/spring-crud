package com.theironyard.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.theironyard.models.Book;
import com.theironyard.models.User;
import com.theironyard.repositories.BookRepository;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by melmo on 12/29/16.
 */
@Controller
public class GoodreadsController {
    public static final String SESSION_USER_ID = "currentUserId";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);
        String returnVal;

        if (currentUserId != null){
            User currentUser = userRepository.findOne(currentUserId);
            List<Book> usersBooks = bookRepository.findByUser(currentUser);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("usersBooks", usersBooks);
            returnVal = "/user";
        }
        else {
            returnVal = "/home";
        }
        List<Book> allBooks = bookRepository.findAll();
        model.addAttribute("allBooks", allBooks);
        return returnVal;
    }

    @RequestMapping(path = "/add-book", method = RequestMethod.GET)
    public String loadAddBook(Model model, HttpSession session){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);
        String returnVal;

        if (currentUserId != null){
            User currentUser = userRepository.findOne(currentUserId);
            List<Book> usersBooks = bookRepository.findByUser(currentUser);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("usersBooks", usersBooks);

            returnVal = "addBook";
        }
        else {
            returnVal = "redirect:/";
        }
        return returnVal;
    }

    @RequestMapping(path = "/add-book", method = RequestMethod.POST)
    public String addBook(HttpSession session, String title, String author, String year, String cover, String description, String status){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);

        if (currentUserId != null){
            User currentUser = userRepository.findOne(currentUserId);
            Book book = new Book(cover, title, author, year, description, status, currentUser);
            bookRepository.save(book);
        }
        return "redirect:/add-book";
    }

    @RequestMapping(path = "/update-book", method = RequestMethod.POST)
    public String updateBook(HttpSession session, int id, String status){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);
        Book book = bookRepository.findOne(id);

        if (currentUserId == book.getUser().getId()){
            book.setStatus(status);
            bookRepository.save(book);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/delete-book", method = RequestMethod.POST)
    public String deleteBook(HttpSession session, int id){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);
        Book book = bookRepository.findOne(id);

        if (currentUserId == book.getUser().getId()){
            bookRepository.delete(id);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/filter", method = RequestMethod.GET)
    public String filterBooks(Model model, HttpSession session, String listType, String status){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);

        if (currentUserId != null){
            User currentUser = userRepository.findOne(currentUserId);
            model.addAttribute("currentUser", currentUser);
            if (listType.equals("allBooks")){
                List<Book> allBooks = bookRepository.findByStatus(status);
                model.addAttribute("allBooks", allBooks);
            }
            else if (listType.equals("usersBooks")){
                List<Book> usersBooks = bookRepository.findByUserAndStatus(currentUser, status);
                model.addAttribute("usersBooks", usersBooks);
            }
            return "/user";
        }

        return "/";
    }

    @RequestMapping(path = "/sort", method = RequestMethod.GET)
    public String sortBooks(Model model, HttpSession session, String listType, String sortBy){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);

        if (currentUserId != null){
            User currentUser = userRepository.findOne(currentUserId);
            model.addAttribute("currentUser", currentUser);
            if (listType.equals("allBooks")){
                List<Book> allBooks = null;
                if (sortBy.equals("title")){
                    allBooks = bookRepository.findAllByOrderByTitleAsc();
                }
                else if (sortBy.equals("author")){
                    allBooks = bookRepository.findAllByOrderByAuthorAsc();
                }
                else if (sortBy.equals("year")){
                    allBooks = bookRepository.findAllByOrderByYearAsc();
                }
                model.addAttribute("allBooks", allBooks);
            }
            else if (listType.equals("usersBooks")){
                List<Book> usersBooks = null;
                if (sortBy.equals("title")){
                    usersBooks = bookRepository.findByUserOrderByTitleAsc(currentUser);
                }
                else if (sortBy.equals("author")){
                    usersBooks = bookRepository.findByUserOrderByAuthorAsc(currentUser);
                }
                else if (sortBy.equals("year")){
                    usersBooks = bookRepository.findByUserOrderByYearAsc(currentUser);
                }
                model.addAttribute("usersBooks", usersBooks);
            }
            return "/user";
        }
        return "/";
    }
}
