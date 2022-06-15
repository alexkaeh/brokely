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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferRepository transferRepo;
    private JdbcUserDao userDao;

    @Autowired
    public TransferController(TransferRepository transferRepo, JdbcUserDao userDao) {
        this.transferRepo = transferRepo;
        this.userDao = userDao;
    }

    @GetMapping("")
    public List<TransferDto> getAllTransfers(Principal principal) {
        List<TransferDto> outputs = new ArrayList<>();
        List<Transfer> transfers = transferRepo.findAll();
        String name = principal.getName();

        for (Transfer transfer : transfers) {
            TransferDto transferDto = new TransferDto(
                    transfer.getTransferId(),
                    transfer.getTransferType().getTransferTypeDesc(),
                    transfer.getTransferStatus().getTransferStatusDesc(),
                    transfer.getAccountFrom().getUser().getUsername(),
                    transfer.getAccountTo().getUser().getUsername(),
                    transfer.getAmount()
            );
            if (transferDto.getAccountFromName().equals(name) || transferDto.getAccountToName().equals(name)) {
                outputs.add(transferDto);
            }
        }
        return outputs;
    }

    @GetMapping("/{id}")
    public TransferDto getTransferById(Principal principal, @PathVariable int id) {
        Transfer transfer = transferRepo.findByTransferId(id);
        String name = principal.getName();

        TransferDto transferDto = new TransferDto(
                transfer.getTransferId(),
                transfer.getTransferType().getTransferTypeDesc(),
                transfer.getTransferStatus().getTransferStatusDesc(),
                transfer.getAccountFrom().getUser().getUsername(),
                transfer.getAccountTo().getUser().getUsername(),
                transfer.getAmount()
        );
        if (transferDto.getAccountFromName().equals(name) || transferDto.getAccountToName().equals(name)) {
            return transferDto;
        }
        return new TransferDto(); // User did not have permission to access
    }

//        return new TransferDto(  // A truly horrifying multi-table join, decipher at your own risk
//                transfer.getTransferId(),
//                trTypeRepo.findById(transfer.getTransferTypeId()).getTransferTypeDesc(),
//                trStatRepo.findById(transfer.getTransferStatusId()).getTransferStatusDesc(),
//                userDao.findUsernameById(accRepo.findById(transfer.getAccountFrom()).getUserId()),
//                userDao.findUsernameById(accRepo.findById(transfer.getAccountTo()).getUserId()),
//                transfer.getAmount()
//        );


}
