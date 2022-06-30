package com.nagel.kuehne.ewallet.controller;

import com.nagel.kuehne.ewallet.model.User;
import com.nagel.kuehne.ewallet.model.Wallet;
import com.nagel.kuehne.ewallet.payload.*;
import com.nagel.kuehne.ewallet.repository.UserRepository;
import com.nagel.kuehne.ewallet.security.UserDetailsImpl;
import com.nagel.kuehne.ewallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/wallet")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class WalletController {

    @Autowired
    WalletService walletService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createAWallet(@Valid @RequestBody WalletRequest walletRequest){

        Wallet wallet = Wallet.builder()
                .name(walletRequest.getName())
                .balance(walletRequest.getAmount().doubleValue())
                .user(getLoggedUser())
                .build();

        walletService.saveWallet(wallet);

        return ResponseEntity.ok(new MessageResponse("Wallet has been created successfully..."));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> findAllWallets(@PathVariable("id") long userId){

        Optional<User> optionalUser = userRepository.findById(userId);
         if (optionalUser.isPresent()){
             List<Wallet> wallets = walletService.getAllWalletsByUser(optionalUser.get());
             return ResponseEntity
                     .ok(wallets);
         }else {
             return ResponseEntity
                     .badRequest()
                     .body("User not found");
         }


    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositeRequest depositeRequest){

        Wallet wallet = walletService.findWalletById(depositeRequest.getWalletId());
        wallet =  walletService.deposite(wallet,
                BigDecimal.valueOf(depositeRequest.getDepositeAmount()).setScale(2));

        WalletResponse walletResponse = new WalletResponse(wallet.getWalletId(), wallet.getBalance(), "Transaction is successful");
        return ResponseEntity.ok(walletResponse);

    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawRequest withdrawRequest){

        try {
            Wallet wallet = walletService.findWalletById(withdrawRequest.getWalletId());
            wallet =  walletService.withdraw(wallet,
                    BigDecimal.valueOf(withdrawRequest.getWithdrawAmount()).setScale(2));

            WalletResponse walletResponse = new WalletResponse(wallet.getWalletId(), wallet.getBalance(), "Transaction is successful");
            return ResponseEntity.ok(walletResponse);
        }catch (RuntimeException e){
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }



    }

    @PostMapping("/wallet-to-wallet")
    public ResponseEntity<?> walletToWalletTransaction(@RequestBody WalletToWalletRequest wtwRequest){

        Wallet debitWallet = walletService.findWalletById(wtwRequest.getDebitWalletId());
        Wallet creditWallet = walletService.findWalletById(wtwRequest.getCreditWalletId());

        try {
            if (debitWallet != null && creditWallet != null && wtwRequest.getTransactionalAmount()>0.0){

                WalletToWalletResponse wtwResponse = walletService.walletToWalletTransaction(debitWallet, creditWallet,
                        BigDecimal.valueOf(wtwRequest.getTransactionalAmount()).setScale(2));

                return ResponseEntity.ok(wtwResponse);
            }else{
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Transactional amount is not enough or please check your wallets"));
            }
        }catch (RuntimeException e){

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

    }



    private User getLoggedUser(){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getUserId()).get();
        return user;
    }
}
