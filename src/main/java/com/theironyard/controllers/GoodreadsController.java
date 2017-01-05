package com.theironyard.controllers;

import com.theironyard.models.Book;
import com.theironyard.models.User;
import com.theironyard.repositories.BookRepository;
import com.theironyard.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String home(Model model, HttpSession session, @RequestParam(defaultValue = "0") int allPage, @RequestParam(defaultValue = "0") int userPage, String filterUser, String filterBy, String sortUser, String sortBy, String filterAll, String sortAll){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);
        String returnVal;
        Page<Book> usersBooks = null;
        Page<Book> allBooks = null;

        if (currentUserId != null){
            User currentUser = userRepository.findOne(currentUserId);
            if (Boolean.valueOf(filterUser)){
                usersBooks = bookRepository.findByUserAndStatus(currentUser, filterBy, new PageRequest(userPage, 5));
            }
            else if (Boolean.valueOf(sortUser)){
                if (sortBy.equals("title")){
                    usersBooks = bookRepository.findByUserOrderByTitleAsc(currentUser, new PageRequest(userPage, 5));
                }
                else if (sortBy.equals("author")){
                    usersBooks = bookRepository.findByUserOrderByAuthorAsc(currentUser, new PageRequest(userPage, 5));
                }
                else if (sortBy.equals("year")){
                    usersBooks = bookRepository.findByUserOrderByYearAsc(currentUser, new PageRequest(userPage, 5));
                }
            }
            else {
                usersBooks = bookRepository.findByUser(currentUser, new PageRequest(userPage, 5));
            }
            if (usersBooks.hasPrevious()){
                model.addAttribute("usersPrev", true);
                model.addAttribute("usersPrevPageNum", userPage -1);
            }
            if (usersBooks.hasNext()){
                model.addAttribute("usersNext", true);
                model.addAttribute("usersNextPageNum", userPage + 1);
            }
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("usersBooks", usersBooks);
            returnVal = "/user";
        }
        else {
            returnVal = "/home";
        }

        if (Boolean.valueOf(filterAll)){
            allBooks = bookRepository.findByStatus(filterBy, new PageRequest(allPage, 5));
        }
        else if (Boolean.valueOf(sortAll)){
            if (sortBy.equals("title")){
                allBooks = bookRepository.findAllByOrderByTitleAsc(new PageRequest(allPage, 5));
            }
            else if (sortBy.equals("author")){
                allBooks = bookRepository.findAllByOrderByAuthorAsc(new PageRequest(allPage, 5));
            }
            else if (sortBy.equals("year")){
                allBooks = bookRepository.findAllByOrderByYearAsc(new PageRequest(allPage, 5));
            }
        }
        else {
            allBooks = bookRepository.findAll(new PageRequest(allPage, 5));
        }
        if (allBooks.hasPrevious()){
            model.addAttribute("allPrev", true);
            model.addAttribute("allPrevPageNum", allPage -1);
        }
        if (allBooks.hasNext()){
            model.addAttribute("allNext", true);
            model.addAttribute("allNextPageNum", allPage + 1);
        }
        model.addAttribute("allBooks", allBooks);
        return returnVal;
    }

    @RequestMapping(path = "/add-book", method = RequestMethod.GET)
    public String loadAddBook(Model model, HttpSession session, @RequestParam(defaultValue = "0") int userPage){
        Integer currentUserId = (Integer) session.getAttribute(SESSION_USER_ID);
        String returnVal;

        if (currentUserId != null){
            User currentUser = userRepository.findOne(currentUserId);
            Page<Book> usersBooks = bookRepository.findByUser(currentUser, new PageRequest(userPage, 5));

            if (usersBooks.hasPrevious()){
                model.addAttribute("usersPrev", true);
                model.addAttribute("usersPrevPageNum", userPage -1);
            }
            if (usersBooks.hasNext()){
                model.addAttribute("usersNext", true);
                model.addAttribute("usersNextPageNum", userPage + 1);
            }
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

}
