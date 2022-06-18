package com.techelevator.tenmo.dao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    Transfer findByTransferId(int id);

    List<Transfer> findByAccountToOrAccountFrom(Account accFrom, Account accTo);

    List<Transfer> findByAccountToEqualsAndTransferStatusIdEquals(int accountToId, int transferStatusId);

}
