/**
 * Special human read
 */
package com.techelevator.tenmo.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

public class TransferDto {

    @Id
    private int transferId;

    @Column(name = "transfer_type_desc", table = "transfer_type")
    private String transferTypeDesc;

    @Column(name = "transfer_status_desc", table = "transfer_status")
    private String transferStatusDesc;

    @Column(name="username", table = "tenmo_user")
    private String accountFromName;

    @Column(name="username", table = "tenmo_user")
    private String accountToName;

    private int otherUserInRequestId;

    private BigDecimal amount;


    public TransferDto() {
    }

    public TransferDto(int transferId, String transferTypeDesc, String transferStatusDesc, String accountFromName, String accountToName, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeDesc = transferTypeDesc;
        this.transferStatusDesc = transferStatusDesc;
        this.accountFromName = accountFromName;
        this.accountToName = accountToName;
        this.amount = amount;
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
