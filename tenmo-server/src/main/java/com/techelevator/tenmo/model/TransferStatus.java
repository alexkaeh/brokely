package com.techelevator.tenmo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TransferStatus {

    @Id
    private int transferStatusId;
    private String transferStatusDesc;

    public TransferStatus(int transferStatusId, String transferStatusDesc) {
        this.transferStatusId = transferStatusId;
        this.transferStatusDesc = transferStatusDesc;
    }

    public TransferStatus() {
    }

    public int getTransferStatusId() {
        return this.transferStatusId;
    }

    public String getTransferStatusDesc() {
        return this.transferStatusDesc;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }
}
