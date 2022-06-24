package com.nagel.kuehne.ewallet.security;

import com.nagel.kuehne.ewallet.model.User;
import com.nagel.kuehne.ewallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User: %s, not found",username)));

        return UserDetailsImpl.build(user);
    }
}
