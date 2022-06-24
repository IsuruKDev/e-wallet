package com.nagel.kuehne.ewallet.repository;

import com.nagel.kuehne.ewallet.model.ERole;
import com.nagel.kuehne.ewallet.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
