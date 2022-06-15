package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.dto.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferRepository trRepo;
    private JdbcUserDao userDao;
    private TransferStatusRepository trStatRepo;
    private TransferTypeRepository trTypeRepo;
    private AccountRepository accRepo;

    @Autowired
    public TransferController(TransferRepository trRepo, JdbcUserDao userDao, TransferStatusRepository trStRepo, TransferTypeRepository trTyRepo, AccountRepository accRepo) {
        this.trRepo = trRepo;
        this.userDao = userDao;
        this.trStatRepo = trStRepo;
        this.trTypeRepo = trTyRepo;
        this.accRepo = accRepo;
    }


    @GetMapping
    public List<TransferDto> getAllTransfers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return null;
    }

    @GetMapping(path = "/{id}")
    public TransferDto getTransferById(Principal principal, @PathVariable int id) {
        Transfer transfer = trRepo.findByTransferId(id);
        return new TransferDto(  // A truly horrifying multi-table join, decipher at your own risk
                transfer.getTransferId(),
                trTypeRepo.findById(transfer.getTransferTypeId()).getTransferTypeDesc(),
                trStatRepo.findById(transfer.getTransferStatusId()).getTransferStatusDesc(),
                userDao.findUsernameById(accRepo.findById(transfer.getAccountFrom()).getUserId()),
                userDao.findUsernameById(accRepo.findById(transfer.getAccountTo()).getUserId()),
                transfer.getAmount()
        );
    }

}
