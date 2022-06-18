package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.logic.TransferLogic;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.dto.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransferLogic transferLogic;

    @Autowired
    public TransferController(TransferLogic transferLogic) {
        this.transferLogic = transferLogic;
    }

    @GetMapping("")
    public List<TransferDto> getAllTransfers(Principal principal) {
        return transferLogic.getAllTransfers(principal);
    }

    @GetMapping("/{id}")
    public TransferDto getTransferById(Principal principal, @PathVariable int id) {
        return transferLogic.getTransferById(principal, id);
    }

    @PostMapping("/send")
    public Transfer sendMoney (@Valid @RequestBody TransferDto newTransferDto, Principal principal) {
        return transferLogic.sendMoney(newTransferDto, principal);
    }

    @GetMapping("/pending")
    public List<TransferDto> getPendingTransfers(Principal principal) {
        return transferLogic.getPendingTransfers(principal);
    }

    @PutMapping("/pending/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Transfer approveRequest (Principal principal, @PathVariable int transferId) {
        return transferLogic.approveRequest(principal, transferId);
    }

}
