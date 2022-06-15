package com.techelevator.tenmo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
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
//    int transferTypeId;
//    int transferStatusId;
//    int accountFrom;
//    int accountTo;
    @Positive
    BigDecimal amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transfer_type_id")
    private TransferType transferType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transfer_status_id")
    private TransferStatus transferStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_from", referencedColumnName = "account_id")
    private Account accountFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_to", referencedColumnName = "account_id")
    private Account accountTo;

}
