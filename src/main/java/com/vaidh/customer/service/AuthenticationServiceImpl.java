package com.vaidh.customer.service;

import com.vaidh.customer.config.JwtTokenUtil;
import com.vaidh.customer.constants.MailConstant;
import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.request.JwtLoginRequest;
import com.vaidh.customer.dto.request.JwtRegisterRequest;
import com.vaidh.customer.dto.JwtResponse;
import com.vaidh.customer.dto.request.ModifyPasswordRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.dto.response.ConfirmCodeResponse;
import com.vaidh.customer.exception.AlreadyUserExistException;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.customer.ForgetPassword;
import com.vaidh.customer.model.customer.UserEntity;
import com.vaidh.customer.model.enums.ForgetPasswordCodeStatus;
import com.vaidh.customer.repository.ForgetPasswordRepository;
import com.vaidh.customer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

import static com.vaidh.customer.constants.MailConstant.CHANGE_PASSWORD_SUBJ;
import static com.vaidh.customer.constants.ResponseMessage.SUCCESSFULLY_MODIFIED;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForgetPasswordRepository forgetPasswordRepository;

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

    private String generateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    @Override
    public CommonMessageResponse forgetPassword(String emailAddress) {
        final String code = generateCode();
        mailService.sendMail(MailConstant.getForgetPasswordMessage(code),
                "christkiran.15@cse.mrt.ac.lk", CHANGE_PASSWORD_SUBJ);
        forgetPasswordRepository.save(new ForgetPassword(emailAddress, code));
        return new CommonMessageResponse("Sent");
    }

    @Override
    public ConfirmCodeResponse confirmForgetPasswordCode(String emailAddress, String code) throws ModuleException {
        if (!emailAddress.isEmpty() && !code.isEmpty()) {
            Optional<ForgetPassword> forgetPasswordOpt = forgetPasswordRepository.findByEmailAddress(emailAddress);
            if (forgetPasswordOpt.isPresent()) {
                ForgetPassword forgetPassword = forgetPasswordOpt.get();
                if (code.equals(forgetPassword.getCode())) {
                    String generatedConfirmCode = generateCode();
                    forgetPassword.setStatus(ForgetPasswordCodeStatus.EXPIRED);
                    forgetPassword.setConfirmedCode(generatedConfirmCode);
                    forgetPasswordRepository.save(forgetPassword);
                    return new ConfirmCodeResponse(generatedConfirmCode);
                }
            }
        }
        throw new ModuleException("Bad Strings");
    }

    @Override
    public CommonMessageResponse modifyPassword(ModifyPasswordRequest modifyPasswordRequest) throws ModuleException {
        if (modifyPasswordRequest != null && modifyPasswordRequest.isNonEmpty()) {
           Optional<UserEntity> userOpt = userRepository.findByEmailAddress(modifyPasswordRequest.getEmailAddress());
           if (userOpt.isPresent()) {
               UserEntity user = userOpt.get();
               user.setPassword(modifyPasswordRequest.getNewPassword());

               userRepository.save(user);
               return new CommonMessageResponse(SUCCESSFULLY_MODIFIED);
           }
        }
        throw new ModuleException("Bad Strings");
    }

}
