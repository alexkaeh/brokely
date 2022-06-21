package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    Transfer findByTransferId(int id);

    List<Transfer> findByAccountFromEqualsAndTransferStatusEquals(Account accTo, TransferStatus transferStatus);

    List<Transfer> findByAccountToAndTransferStatusIsNotOrAccountFromAndTransferStatusIsNot(Account account, TransferStatus status, Account account1, TransferStatus status1);
}
