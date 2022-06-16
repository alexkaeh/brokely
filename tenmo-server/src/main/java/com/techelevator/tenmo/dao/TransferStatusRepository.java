package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferStatusRepository extends JpaRepository<TransferStatus, Integer> {

    TransferStatus findById(int id);

    // FIXME I have no idea if Spring can actually parse this method
    TransferStatus findByTransferStatusDesc(String transferStatusDesc);
}
