package com.nagel.kuehne.ewallet.repository;

import com.nagel.kuehne.ewallet.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsernameTest() {

        User testUser = new User("user1","pass1");

        userRepository.save(testUser);

        User savedInstance = userRepository.findByUsername("user1").get();

        Assertions.assertThat(savedInstance.getUsername()).isEqualTo(testUser.getUsername());


    }

    @Test
    void existsByUsernameTest() {
        User testUser = new User("user1","pass1");

        userRepository.save(testUser);

        Boolean isUserExists = userRepository.existsByUsername("user1");

        Assertions.assertThat(isUserExists).isTrue();

    }
}