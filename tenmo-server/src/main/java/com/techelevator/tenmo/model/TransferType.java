package com.techelevator.tenmo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TransferType {

    @Id
    private int transferTypeId;
    private String transferTypeDesc;

    public TransferType(int transferTypeId, String transferTypeDesc) {
        this.transferTypeId = transferTypeId;
        this.transferTypeDesc = transferTypeDesc;
    }

    public TransferType() {
    }

    public int getTransferTypeId() {
        return this.transferTypeId;
    }

    public String getTransferTypeDesc() {
        return this.transferTypeDesc;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }
}
