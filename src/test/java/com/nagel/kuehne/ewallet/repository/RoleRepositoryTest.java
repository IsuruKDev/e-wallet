package com.nagel.kuehne.ewallet.repository;

import com.nagel.kuehne.ewallet.model.ERole;
import com.nagel.kuehne.ewallet.model.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    void setUp(){
        roleRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {

        roleRepository.deleteAll();
    }

    @Test
    void findByNameTest() {
        Role role = Role.builder()
                .name(ERole.ROLE_USER)
                .build();

        roleRepository.save(role);
        Role savedInstance = roleRepository.findByName(ERole.ROLE_USER).get();

        Assertions.assertThat(savedInstance.getName()).isEqualTo(ERole.ROLE_USER);

    }
}