package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferRepository;
import com.techelevator.tenmo.model.dto.TransferDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferRepository transferRepo;
    private JdbcUserDao userDao;

    public TransferController(TransferRepository transferRepo, JdbcUserDao userDao) {
        this.transferRepo = transferRepo;
        this.userDao = userDao;
    }


    @GetMapping
    public TransferDto[] getAllTransfers(Principal principal) {
        principal.getName();
        return null;

    }

}
