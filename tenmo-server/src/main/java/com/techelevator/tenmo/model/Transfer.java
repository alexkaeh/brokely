package com.techelevator.tenmo.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int transferId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transfer_type_id")
    private TransferType transferType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transfer_status_id")
    private TransferStatus transferStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_from", referencedColumnName = "account_id")
    private Account accountFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_to", referencedColumnName = "account_id")
    private Account accountTo;

    BigDecimal amount;


    //    int transferTypeId;
//    int transferStatusId;
//    int accountFrom;
//    int accountTo;

    public Transfer(int transferId, TransferType transferType, TransferStatus transferStatus, Account accountFrom, Account accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer(TransferType transferType, TransferStatus transferStatus, Account accountFrom, Account accountTo, BigDecimal amount) {
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfer() {
    }

    public int getTransferId() {
        return this.transferId;
    }

    public TransferType getTransferType() {
        return this.transferType;
    }

    public TransferStatus getTransferStatus() {
        return this.transferStatus;
    }

    public Account getAccountFrom() {
        return this.accountFrom;
    }

    public Account getAccountTo() {
        return this.accountTo;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
