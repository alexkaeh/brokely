package com.techelevator.tenmo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int transferId;
    int transferTypeId;
    int transferStatusId;
    int accountFrom;
    int accountTo;
    BigDecimal amount;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "transfer_type_id")
//    private TransferType transferType;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "transfer_status_id")
//    private TransferStatus transferStatus;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumns({
//            @JoinColumn(name = "account_from", referencedColumnName = "account_id"),
//            @JoinColumn(name = "account_to", referencedColumnName = "account_id")
//    })
//    private Account account;

}
