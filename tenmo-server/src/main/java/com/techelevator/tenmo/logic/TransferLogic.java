package com.techelevator.tenmo.logic;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransferLogic {

    private TransferRepository transferRepo;
    private JdbcUserDao userDao;
    private TransferTypeRepository transferTypeRepo;
    private TransferStatusRepository transferStatusRepo;
    private AccountRepository accountRepo;

    @Autowired
    public TransferLogic (TransferRepository transferRepo, JdbcUserDao userDao,
                         TransferTypeRepository transferTypeRepo, TransferStatusRepository transferStatusRepo,
                         AccountRepository accountRepo) {
        this.transferRepo = transferRepo;
        this.userDao = userDao;
        this.transferTypeRepo = transferTypeRepo;
        this.transferStatusRepo = transferStatusRepo;
        this.accountRepo = accountRepo;
    }

    public BigDecimal sendMoney (TransferDto newTransferDto, Principal principal) {
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

        moveBucksBetweenAccounts(userToId, userFromId, amount);
        transferRepo.save(newTransfer);

        return accountRepo.findByUserId(userFromId).getBalance();
    }

    private boolean moveBucksBetweenAccounts(int userToId, int userFromId, BigDecimal amount) {
        BigDecimal accountFromBalance = accountRepo.findByUserId(userFromId).getBalance();

        if(accountFromBalance.compareTo(amount) < 0) {
            return false;
        }

        // increase recipient balance
        Account recipient = accountRepo.findByUserId(userToId);
        recipient.setBalance(recipient.getBalance().add(amount));
        accountRepo.save(recipient);

        // decrease sender balance
        Account sender = accountRepo.findByUserId(userFromId);
        sender.setBalance(sender.getBalance().subtract(amount));
        accountRepo.save(sender);

        return true;
    }

    public TransferDto getTransferById(Principal principal, int id) {
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

    public List<TransferDto> getAllTransfers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        Account account = accountRepo.findByUserId(userId);

        List<Transfer> transfers = transferRepo.findByAccountToOrAccountFrom(account, account);
        List<TransferDto> transferDtos = makeTransferIntoDto(transfers);

        return transferDtos;
    }

    public List<TransferDto> getPendingTransfers(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());

        List<Transfer> transfers = transferRepo.findByAccountToEqualsAndTransferStatusEquals(
                accountRepo.findByUserId(userId),
                transferStatusRepo.findByTransferStatusDesc("Pending")
        );
        List<TransferDto> transferDtos = makeTransferIntoDto(transfers);

        return transferDtos;
    }

    private List<TransferDto> makeTransferIntoDto (List<Transfer> transfers) {
        List<TransferDto> transferDtos = new ArrayList<>();

        for (Transfer transfer : transfers) {
            TransferDto transferDto = new TransferDto(
                    transfer.getTransferId(),
                    transfer.getTransferType().getTransferTypeDesc(),
                    transfer.getTransferStatus().getTransferStatusDesc(),
                    transfer.getAccountFrom().getUser().getUsername(),
                    transfer.getAccountTo().getUser().getUsername(),
                    transfer.getAmount()
            );
            transferDtos.add(transferDto);
        }
        return transferDtos;
    }

    public Transfer approveRequest (Principal principal, int transferId) {
        Transfer transferToUpdate = transferRepo.findByTransferId(transferId);
        int currentUserId = userDao.findIdByUsername(principal.getName());

        if(transferToUpdate.getAccountFrom().getUser().getId() == currentUserId) {
            transferToUpdate.setTransferStatus(transferStatusRepo.findByTransferStatusDesc("Approved"));

            boolean bucksSuccessfullyMoved = moveBucksBetweenAccounts(
                    transferToUpdate.getAccountTo().getUser().getId(),
                    currentUserId,
                    transferToUpdate.getAmount()
            );

            if(!bucksSuccessfullyMoved) {
                // TODO error handling?
            }

            return transferRepo.save(transferToUpdate);
        }

        return null;
    }

}
