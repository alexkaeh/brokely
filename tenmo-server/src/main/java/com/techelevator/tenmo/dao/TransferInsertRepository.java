package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public class TransferInsertRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void insertWithEntityManager(Transfer transfer) {
        this.entityManager.persist(transfer);
    }

    @Transactional
    public void insertWithQuery(int accFrom, int accTo, BigDecimal amount, int trStatusId, int trTypeId) {
            entityManager.createNativeQuery("INSERT INTO transfer (account_from, account_to, amount, " +
                            "transfer_status_id, transfer_type_id) VALUES (?,?,?,?,?)")
                    .setParameter(1, accFrom)
                    .setParameter(2, accTo)
                    .setParameter(3, amount)
                    .setParameter(4, trStatusId)
                    .setParameter(5, trTypeId)
                    .executeUpdate();
    }

}
