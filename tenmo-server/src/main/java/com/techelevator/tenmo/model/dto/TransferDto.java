package com.techelevator.tenmo.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
//@Entity
//@Table(name = "transfer")
//@SecondaryTable(name = "transfer_status", pkJoinColumns = @PrimaryKeyJoinColumn(name = "transfer_status_id"))
//@SecondaryTable(name = "transfer_type", pkJoinColumns = @PrimaryKeyJoinColumn(name = "transfer_type_id"))
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

    private BigDecimal amount;

    public TransferDto(int transferId, String transferTypeDesc, String transferStatusDesc, String accountFromName, String accountToName, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeDesc = transferTypeDesc;
        this.transferStatusDesc = transferStatusDesc;
        this.accountFromName = accountFromName;
        this.accountToName = accountToName;
        this.amount = amount;
    }

    public TransferDto() {
    }
}
