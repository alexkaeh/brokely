package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.model.dto.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
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

    @Autowired
    public TransferController(TransferRepository transferRepo, JdbcUserDao userDao, TransferTypeRepository transferTypeRepo, TransferStatusRepository transferStatusRepo, AccountRepository accountRepo) {
        this.transferRepo = transferRepo;
        this.userDao = userDao;
        this.transferTypeRepo = transferTypeRepo;
        this.transferStatusRepo = transferStatusRepo;
        this.accountRepo = accountRepo;
    }


    @GetMapping("")
    public List<TransferDto> getAllTransfers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        Account account = accountRepo.findByUserId(userId);

        List<Transfer> transfers = transferRepo.findByAccountToOrAccountFrom(account, account);
        List<TransferDto> outputs = new ArrayList<>();

        for (Transfer transfer : transfers) {
            TransferDto transferDto = new TransferDto(
                    transfer.getTransferId(),
                    transfer.getTransferType().getTransferTypeDesc(),
                    transfer.getTransferStatus().getTransferStatusDesc(),
                    transfer.getAccountFrom().getUser().getUsername(),
                    transfer.getAccountTo().getUser().getUsername(),
                    transfer.getAmount()
            );
            outputs.add(transferDto);
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
        BigDecimal amount = newTransferDto.getAmount();
        Transfer newTransfer = new Transfer();

        newTransfer.setTransferType(transferTypeRepo.findByTransferTypeDesc("Send"));
        newTransfer.setTransferStatus(transferStatusRepo.findByTransferStatusDesc("Approved"));
        newTransfer.setAccountFrom(accountRepo.findByUserId(userFromId));
        newTransfer.setAccountTo(accountRepo.findByUserId(userToId));
        newTransfer.setAmount(newTransferDto.getAmount());

        // TODO validation goes here

        // increase recipient balance
        Account recipient = accountRepo.findByUserId(userToId);
        recipient.setBalance(recipient.getBalance().add(amount));
        accountRepo.save(recipient);

        // decrease sender balance
        Account sender = accountRepo.findByUserId(userFromId);
        sender.setBalance(sender.getBalance().subtract(amount));
        accountRepo.save(sender);

        return transferRepo.save(newTransfer);
    }

}
