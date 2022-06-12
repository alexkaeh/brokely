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

    @ManyToOne
    int transferTypeId;

    @ManyToOne
    int transferStatusId;

    @ManyToOne
    int accountFrom;

    @ManyToOne
    int accountTo;

    BigDecimal amount;
}
