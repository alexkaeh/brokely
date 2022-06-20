package com.techelevator.tenmo.logic;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public BigDecimal sendMoney (Principal principal, TransferDto newTransferDto) {
        int userFromId = userDao.findIdByUsername(principal.getName());
        int userToId = newTransferDto.getOtherUserInRequestId();
        BigDecimal amount = newTransferDto.getAmount();
        Transfer newTransfer = new Transfer();

        newTransfer.setTransferType(transferTypeRepo.findByTransferTypeDesc("Send"));
        newTransfer.setTransferStatus(transferStatusRepo.findByTransferStatusDesc("Approved"));
        newTransfer.setAccountFrom(accountRepo.findByUserId(userFromId));
        newTransfer.setAccountTo(accountRepo.findByUserId(userToId));
        newTransfer.setAmount(newTransferDto.getAmount());

        moveBucksBetweenAccounts(userToId, userFromId, amount);
        transferRepo.save(newTransfer);

        return accountRepo.findByUserId(userFromId).getBalance();
    }

    public Boolean requestMoney(Principal principal, TransferDto newTransferDto) {
        int userToId = userDao.findIdByUsername(principal.getName());
        int userFromId = newTransferDto.getOtherUserInRequestId();
        if (userFromId == userToId) {
            return false;
        }

        BigDecimal amount = newTransferDto.getAmount();
        Transfer newTransfer = new Transfer();

        newTransfer.setTransferType(transferTypeRepo.findByTransferTypeDesc("Request"));
        newTransfer.setTransferStatus(transferStatusRepo.findByTransferStatusDesc("Pending"));
        newTransfer.setAccountFrom(accountRepo.findByUserId(userFromId));
        newTransfer.setAccountTo(accountRepo.findByUserId(userToId));
        newTransfer.setAmount(newTransferDto.getAmount());

        transferRepo.save(newTransfer);
        return true;
    }

    public BigDecimal approveRequest (Principal principal, int transferId) {
        Transfer transferToUpdate = transferRepo.findByTransferId(transferId);
        int currentUserId = userDao.findIdByUsername(principal.getName());

        // Checks to see if user has permission
        if(transferToUpdate.getAccountFrom().getUser().getId() != currentUserId) {
            return null;
        }

        boolean bucksSuccessfullyMoved = moveBucksBetweenAccounts(
                transferToUpdate.getAccountTo().getUser().getId(),
                currentUserId,
                transferToUpdate.getAmount()
        );

        // If user has enough funds update status and save new transfer
        if (bucksSuccessfullyMoved) {
            transferToUpdate.setTransferStatus(transferStatusRepo.findByTransferStatusDesc("Approved"));
            transferRepo.save(transferToUpdate);
        }

        // returns current balance no matter what
        return accountRepo.findByUserId(currentUserId).getBalance();
    }

    public boolean rejectRequest (Principal principal, int transferId) {
        Transfer transferToUpdate = transferRepo.findByTransferId(transferId);
        int currentUserId = userDao.findIdByUsername(principal.getName());

        // Checks to see if user has permission
        if(transferToUpdate.getAccountFrom().getUser().getId() != currentUserId) {
            return false;
        }

        transferToUpdate.setTransferStatus(transferStatusRepo.findByTransferStatusDesc("Rejected"));
        transferRepo.save(transferToUpdate);
        return true;
    }


    private boolean moveBucksBetweenAccounts(int userToId, int userFromId, BigDecimal amount) {
        BigDecimal accountFromBalance = accountRepo.findByUserId(userFromId).getBalance();

        // Check whether user has enough money
        if(accountFromBalance.compareTo(amount) < 0) {
            return false;
        // Checks that amount is greater than zero
        } else if (amount.compareTo(BigDecimal.valueOf(0.0)) <= 0) {
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

}
