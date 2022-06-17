package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDto {

    private int transferId;
    private String transferType;
    private String transferStatus;
    private String accountFrom;
    private String accountTo;
    private  int userToId;

    private BigDecimal amount;

    public TransferDto(int transferId, String transferType, String transferStatus, String accountFrom, String accountTo, int accountToId, BigDecimal amount) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.userToId = accountToId;
        this.amount = amount;
    }

    public TransferDto() {
    }

    @Override
    public String toString(){
        return toString(false);
    }
    public String toString(boolean hasDetails){
        StringBuilder sb = new StringBuilder();
        if(hasDetails){
            sb.append("Id: ").append(transferId);
            sb.append("\nFrom: ").append(accountFrom);
            sb.append("\nTo: ").append(accountFrom);
            sb.append("\nType: ").append(transferType);
            sb.append("\nStatus: ").append(transferStatus);
            sb.append("\nAmount: ").append(amount);
            return sb.toString();
        }
        sb.append(transferId);
        sb.append("\t\t").append("From: ").append(userToId);
        sb.append("\t\t").append("$").append(amount);
        return sb.toString();
    }

    public int getTransferId() {
        return this.transferId;
    }

    public String getTransferType() {
        return this.transferType;
    }

    public String getTransferStatus() {
        return this.transferStatus;
    }

    public String getAccountFrom() {
        return this.accountFrom;
    }

    public String getAccountTo() {
        return this.accountTo;
    }

    public int getUserToId() {
        return this.userToId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
