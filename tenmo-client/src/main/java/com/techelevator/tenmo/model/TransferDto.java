package com.techelevator.tenmo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private int transferId;
    private String transferType;
    private String transferStatus;
    private String userFrom;
    private String userTo;
    private  int userToId;
    private BigDecimal amount;

    @Override
    public String toString(){
        return toString(false);
    }
    public String toString(boolean hasDetails){
        StringBuilder sb = new StringBuilder();
        if(hasDetails){
            sb.append("Id: ").append(transferId);
            sb.append("\nFrom: ").append(userFrom);
            sb.append("\nTo: ").append(userFrom);
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
}
