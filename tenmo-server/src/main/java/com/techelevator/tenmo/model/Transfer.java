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

    @ManyToOne
    @JoinColumn(name = "transfer_type_id")
    private TransferType transferType;

    @ManyToOne
    @JoinColumn(name = "transfer_status_id")
    private TransferStatus transferStatus;

    @ManyToOne
    @JoinColumn(name = "account_from")
    private Account accountFromJpa;

    @ManyToOne
    @JoinColumn(name = "account_to")
    private Account accountToJpa;

    BigDecimal amount;
}
