package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private AccountRepository accountRepo;
    private JdbcUserDao userDao;

    @Autowired
    public UserController(AccountRepository accountRepo, JdbcUserDao userDao) {
        this.accountRepo = accountRepo;
        this.userDao = userDao;
    }

    @GetMapping
    public List<User> getUsers(){
        return userDao.findAll();
    }
}
