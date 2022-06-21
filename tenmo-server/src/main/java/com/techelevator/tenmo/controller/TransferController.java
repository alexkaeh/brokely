/**
 * Manages API access to Transfer table. All business logic is delegated to TransferLogic.
 */
package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.logic.TransferLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/transfer")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private final TransferLogic transferLogic;

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

    @GetMapping("/pending")
    public List<TransferDto> getPendingTransfers(Principal principal) {
        return transferLogic.getPendingTransfers(principal);
    }

    @PostMapping("/send")
    public BigDecimal sendMoney(@Valid @RequestBody TransferDto newTransferDto, Principal principal) {
        return transferLogic.sendMoney(principal, newTransferDto);
    }

    @PostMapping("/request")
    public Boolean requestMoney(@Valid @RequestBody TransferDto newTransferDto, Principal principal) {
        return transferLogic.requestMoney(principal, newTransferDto);
    }

    @PutMapping("/pending/approve/{transferId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BigDecimal approveRequest(Principal principal, @PathVariable int transferId) {
        return transferLogic.approveRequest(principal, transferId);
    }

    @PutMapping("/pending/reject/{transferId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean rejectRequest(Principal principal, @PathVariable int transferId) {
        return transferLogic.rejectRequest(principal, transferId);
    }

}
