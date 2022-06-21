/**
 * This object is represents many joins being performed by the server to retrieve data from many tables, to create a
 * human-readable version of the "transfer" table in our database.
 */
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
    /* This is a special field that is only used to send info *TO* the server, and is never received *FROM* the server.
    Using the auth token the server can always determine the current user's identity, so this is used when the user
    needs to reference *another* user, such as when the current user is requesting or sending money. */
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


    // This method is called by the ConsoleService#printTable method to create a formatted table of transfers for the user.
    @Override
    public String[] toStringArray(String currentUser) {
        String[] arr = new String[3];
        arr[0] = "" + transferId;
        arr[1] = accountFromName.equals(currentUser) ? "To: " + accountToName : "From: " + accountFromName;
        arr[2] = String.format("%" + ConsoleService.CELL_WIDTH + "s", "$" + amount);

        return arr;
    }

    // This method is used when a single transfer is being displayed, and contains more detail.
    @Override
    public String toString() {
        return "Id: " + transferId +
                "\nFrom: " + accountFromName +
                "\nTo: " + accountToName +
                "\nType: " + transferTypeDesc +
                "\nStatus: " + transferStatusDesc +
                "\nAmount: " + amount;
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
