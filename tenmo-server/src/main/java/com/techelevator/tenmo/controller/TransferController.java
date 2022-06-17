package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.model.dto.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferRepository transferRepo;
    private JdbcUserDao userDao;
    private TransferTypeRepository transferTypeRepo;
    private TransferStatusRepository transferStatusRepo;
    private AccountRepository accountRepo;
    private TransferInsertRepository transferInsertRepo;

    @Autowired
    public TransferController(TransferRepository transferRepo, JdbcUserDao userDao, TransferTypeRepository transferTypeRepo, TransferStatusRepository transferStatusRepo, AccountRepository accountRepo, TransferInsertRepository transferInsertRepo) {
        this.transferRepo = transferRepo;
        this.userDao = userDao;
        this.transferTypeRepo = transferTypeRepo;
        this.transferStatusRepo = transferStatusRepo;
        this.accountRepo = accountRepo;
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
            // checks that from = user, or to = user, but not both
            if (transferDto.getAccountFromName().equals(name) ^ transferDto.getAccountToName().equals(name)) {
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
        // FIXME This triggers if user didn't have auth, should throw an HTTP error instead
        return new TransferDto();
    }

    @PostMapping("/send")
    public Transfer sendMoney (@Valid @RequestBody TransferDto newTransferDto, Principal principal){
        int userFromId = userDao.findIdByUsername(principal.getName());
        int userToId = newTransferDto.getUserToId();
        Transfer newTransfer = new Transfer();

        newTransfer.setTransferType(transferTypeRepo.findByTransferTypeDesc("Send"));
        newTransfer.setTransferStatus(transferStatusRepo.findByTransferStatusDesc("Approved"));
        newTransfer.setAccountFrom(accountRepo.findByUserId(userFromId));
        newTransfer.setAccountTo(accountRepo.findByUserId(userToId));
        newTransfer.setAmount(newTransferDto.getAmount());

        // TODO validation goes here

        return transferRepo.save(newTransfer);
    }

}
