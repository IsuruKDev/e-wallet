package com.nagel.kuehne.ewallet.repository;

import com.nagel.kuehne.ewallet.model.User;
import com.nagel.kuehne.ewallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findWalletByUser(User user);
    Optional<Wallet> findByWalletId(long walletId);
}
