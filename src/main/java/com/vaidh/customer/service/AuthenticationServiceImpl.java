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
        authenticate(jwtLoginRequest.getUsername(), jwtLoginRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtLoginRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @Override
    public JwtResponse createUser(JwtRegisterRequest authenticationRequest) throws Exception {
        if (authenticationRequest != null) {
            //check if already user exist
            Optional<UserEntity> user = userDetailsService.loadUserEntityByUserName(authenticationRequest.getUsername());
            if (user.isEmpty()) {
                userDetailsService.saveUser(authenticationRequest);
                return createAuthenticationToken(new JwtLoginRequest(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            } else {
                throw new AlreadyUserExistException("This user already existing");
            }
        }
        return null;
    }
}
