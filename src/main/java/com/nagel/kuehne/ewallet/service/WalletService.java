package com.nagel.kuehne.ewallet.service;

import com.nagel.kuehne.ewallet.model.User;
import com.nagel.kuehne.ewallet.model.Wallet;
import com.nagel.kuehne.ewallet.payload.WalletToWalletResponse;
import com.nagel.kuehne.ewallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service @Transactional @AllArgsConstructor @NoArgsConstructor
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    public Wallet saveWallet(Wallet wallet){

        return walletRepository.save(wallet);
    }

    public Wallet findWalletById(long walletId){

        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(()->new RuntimeException(String.format("Wallet %d is not found")));

        return wallet;
    }

    public List<Wallet> getAllWalletsByUser(User user){
        return walletRepository.findWalletByUser(user);
    }

    public Wallet deposite(Wallet wallet, BigDecimal depositeAmount){
        BigDecimal newBalance = BigDecimal.valueOf(wallet.getBalance()).add(depositeAmount).setScale(2);
        wallet.setBalance(newBalance.doubleValue());
        return saveWallet(wallet);
    }

    public Wallet withdraw(Wallet wallet, BigDecimal withdrawAmount){
        BigDecimal newBalance = BigDecimal.valueOf(wallet.getBalance()).subtract(withdrawAmount).setScale(2);
        if (newBalance.doubleValue()>0.0){
            wallet.setBalance(newBalance.doubleValue());
            saveWallet(wallet);
        }else
            throw new RuntimeException(String.format("Wallet %d dosen't have sufficient funds",wallet.getWalletId()));

        return wallet;

    }

    public WalletToWalletResponse walletToWalletTransaction(Wallet debitWallet, Wallet creditWallet, BigDecimal transaction){

        BigDecimal debitBalance = BigDecimal.valueOf(debitWallet.getBalance());
        BigDecimal creditBalance = BigDecimal.valueOf(creditWallet.getBalance());
        WalletToWalletResponse wtwResponse = new WalletToWalletResponse();

        debitBalance = debitBalance.subtract(transaction).setScale(2);
        creditBalance = creditBalance.add(transaction).setScale(2);
        if (debitBalance.doubleValue()>0.0){
            debitWallet.setBalance(debitBalance.doubleValue());
            creditWallet.setBalance(creditBalance.doubleValue());

            Wallet creditWalletUpdated = saveWallet(creditWallet);
            Wallet debitWalletUpdated = saveWallet(debitWallet);


            wtwResponse.setDebitWallet(debitWalletUpdated);
            wtwResponse.setCreditWallet(creditWalletUpdated);
            wtwResponse.setMessage("Transaction has been completed");
        }else {
            throw new RuntimeException(String.format("Wallet %s doesn't have sufficient funds to continue " +
                    "wallet to wallet transaction ", debitWallet.getName()));
        }

        return wtwResponse;

    }
}
