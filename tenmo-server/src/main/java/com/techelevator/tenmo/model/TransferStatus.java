package com.techelevator.tenmo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class TransferStatus {

    @Id
    private int transferStatusId;
    private String transferStatusDesc;

}
