package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountRepository;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountRepository accountRepo;
    private JdbcUserDao userDao;

    @Autowired
    public AccountController(AccountRepository accountRepo, JdbcUserDao userDao) {
        this.accountRepo = accountRepo;
        this.userDao = userDao;
    }

    public AccountController() {
    }


//    @GetMapping
//    public Account getBalance(Principal principal) {
//        String name = principal.getName();
//        Integer id = userDao.findIdByUsername(name);
//        Optional<Account> acc = accountRepo.findByUserId(id);
//        return acc.orElse(new Account());
//    }

    //CHANGED TO RETURN BigDecimal
    @GetMapping
    public BigDecimal getBalance(Principal principal) {
        String name = principal.getName();
        Integer id = userDao.findIdByUsername(name);
        Optional<Account> acc = accountRepo.findByUserId(id);
        Account userAccount = acc.orElse(new Account());
        return userAccount.getBalance();
    }
}

//@GetMapping
//public BigDecimal getBalance(Principal principal) {
//    int id = 1005;
//    Optional<Account> optAccount = accountRepo.findById(id);
//    return optAccount.isPresent() ? optAccount.get().getBalance() : new BigDecimal(0);
//}
//}
