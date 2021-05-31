package com.vaidh.customer.service;

import com.vaidh.customer.dto.JwtRegisterRequest;
import com.vaidh.customer.model.customer.UserEntity;
import com.vaidh.customer.repository.UserRepository;
import com.vaidh.customer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        return new User(userEntity.get().getUsername(), userEntity.get().getPassword(), new ArrayList<>());
    }

    public Optional<UserEntity> loadUserEntityByUserName(String userName) throws UsernameNotFoundException{
        return userRepository.findByUsername(userName);
    }

    public boolean saveUser(JwtRegisterRequest request) {
        if (request != null) {
            UserEntity user = UserUtil.convertToUserEntity(request, new BCryptPasswordEncoder().encode(request.getPassword()));
            if (userRepository.save(user)!= null) {
                return true;
            }
        }
       return false;
    }
}
