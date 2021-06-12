package com.vaidh.customer.service;

import com.vaidh.customer.config.JwtTokenUtil;
import com.vaidh.customer.dto.JwtLoginRequest;
import com.vaidh.customer.dto.JwtRegisterRequest;
import com.vaidh.customer.dto.JwtResponse;
import com.vaidh.customer.exception.AlreadyUserExistException;
import com.vaidh.customer.model.customer.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw e;
        }
    }

    @Override
    public JwtResponse createAuthenticationToken(JwtLoginRequest jwtLoginRequest) throws Exception {
        UserDetails userDetails = null;
        if (jwtLoginRequest.getUsername() != null && !jwtLoginRequest.getUsername().isEmpty()) {
            userDetails = userDetailsService
                    .loadUserByUsername(jwtLoginRequest.getUsername());
        } else if (jwtLoginRequest.getPhoneNumber() != null && jwtLoginRequest.getPhoneNumber().isEmpty()) {
            userDetails = userDetailsService.loadUserByPhoneNumber(jwtLoginRequest.getPhoneNumber());
        }
        authenticate(userDetails.getUsername(), jwtLoginRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @Override
    public JwtResponse createUser(JwtRegisterRequest authenticationRequest) throws Exception {
        if (authenticationRequest != null) {
            //check if already user exist
            Optional<UserEntity> user = userDetailsService.loadUserEntityByUserName(authenticationRequest.getUsername());
            if (!user.isPresent()) {
                userDetailsService.saveUser(authenticationRequest);
                return createAuthenticationToken(new JwtLoginRequest(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            } else {
                throw new AlreadyUserExistException("This user already existing");
            }
        }
        return null;
    }

    @Override
    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
