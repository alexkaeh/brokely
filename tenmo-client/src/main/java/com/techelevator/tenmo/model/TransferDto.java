package com.techelevator.tenmo.model;

import com.techelevator.tenmo.services.ConsoleService;

import java.math.BigDecimal;

import static com.techelevator.tenmo.services.ConsoleService.CELL_WIDTH;

public class TransferDto implements Arrayable {

    private int transferId;
    private String transferTypeDesc;
    private String transferStatusDesc;
    private String accountFromName;
    private String accountToName;
    private int otherUserInRequestId;

    private BigDecimal amount;

    public TransferDto(int transferId, String transferType, String transferStatus, String accountFrom, String accountTo, int accountToId, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeDesc = transferType;
        this.transferStatusDesc = transferStatus;
        this.accountFromName = accountFrom;
        this.accountToName = accountTo;
        this.otherUserInRequestId = accountToId;
        this.amount = amount;
    }

    public TransferDto() {
    }

    @Override
    public String[] toStringArray() {
        return null;
    }

    @Override
    public String[] toStringArray(String currentUser) {
        String[] arr = new String[3];
        arr[0] = "" + transferId;
        arr[1] = accountFromName.equals(currentUser) ? "To: " + accountToName : "From: " + accountFromName;
        arr[2] = String.format("%" + ConsoleService.CELL_WIDTH + "s", "$" + amount);

        return arr;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Id: ").append(transferId);
        sb.append("\nFrom: ").append(accountFromName);
        sb.append("\nTo: ").append(accountToName);
        sb.append("\nType: ").append(transferTypeDesc);
        sb.append("\nStatus: ").append(transferStatusDesc);
        sb.append("\nAmount: ").append(amount);

        return sb.toString();
    }

    public int getTransferId() {
        return this.transferId;
    }

    public String getTransferTypeDesc() {
        return this.transferTypeDesc;
    }

    public String getTransferStatusDesc() {
        return this.transferStatusDesc;
    }

    public String getAccountFromName() {
        return this.accountFromName;
    }

    public String getAccountToName() {
        return this.accountToName;
    }

    public int getOtherUserInRequestId() {
        return this.otherUserInRequestId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public void setAccountFromName(String accountFromName) {
        this.accountFromName = accountFromName;
    }

    public void setAccountToName(String accountToName) {
        this.accountToName = accountToName;
    }

    public void setOtherUserInRequestId(int otherUserInRequestId) {
        this.otherUserInRequestId = otherUserInRequestId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
