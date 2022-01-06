package com.vaidh.customer.service;

import com.vaidh.customer.config.JwtTokenUtil;
import com.vaidh.customer.constants.MailConstant;
import com.vaidh.customer.dto.request.JwtLoginRequest;
import com.vaidh.customer.dto.request.JwtRegisterRequest;
import com.vaidh.customer.dto.JwtResponse;
import com.vaidh.customer.dto.request.ModifyPasswordRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.dto.response.ConfirmCodeResponse;
import com.vaidh.customer.dto.response.UserDetailsResponse;
import com.vaidh.customer.exception.AlreadyUserExistException;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.customer.ForgetPassword;
import com.vaidh.customer.model.customer.UserEntity;
import com.vaidh.customer.model.enums.ForgetPasswordCodeStatus;
import com.vaidh.customer.repository.ForgetPasswordRepository;
import com.vaidh.customer.repository.UserRepository;
import com.vaidh.customer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        } else if (jwtLoginRequest.getPhoneNumber() != null && !jwtLoginRequest.getPhoneNumber().isEmpty()) {
            userDetails = userDetailsService.loadUserByPhoneNumber(jwtLoginRequest.getPhoneNumber());
        }
        authenticate(userDetails.getUsername(), jwtLoginRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }

    @Override
    public JwtResponse createUser(JwtRegisterRequest authenticationRequest) throws Exception {
        if (authenticationRequest != null) {
            if (!UserUtil.validatePassword(authenticationRequest.getPassword())) {
                throw new ModuleException("Password is not valid");
            }
            //check if already user exist
            Optional<UserEntity> user = userDetailsService.loadUserEntityByUserName(authenticationRequest.getUsername());
            if (!user.isPresent()) {
                userDetailsService.saveUser(authenticationRequest);
                return createAuthenticationToken(new JwtLoginRequest(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            } else {
                throw new AlreadyUserExistException("This user already existing");
            }
        } else {
            throw new ModuleException("Bad Request");
        }
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

    private String generateAlphaNumeric() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    @Override
    public CommonMessageResponse forgetPassword(String username) throws ModuleException {
        final String code = generateCode();
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Optional<ForgetPassword> forgetPasswordOpt = forgetPasswordRepository.findByUsername(username);
            if (forgetPasswordOpt.isPresent()) {
                ForgetPassword forgetPassword = forgetPasswordOpt.get();
                forgetPassword.setCode(code);
                forgetPassword.setStatus(ForgetPasswordCodeStatus.NEW);
                forgetPassword.setConfirmedCode("");
                forgetPasswordRepository.save(forgetPassword);
            } else {
                forgetPasswordRepository.save(new ForgetPassword( user.get().getUsername(), code));
            }
            mailService.sendMail(MailConstant.getForgetPasswordMessage(code),
                    user.get().getEmailAddress(), CHANGE_PASSWORD_SUBJ);
            return new CommonMessageResponse(String.format("Sent to %s*****",user.get().getEmailAddress().substring(0,4)));
        }
        throw new ModuleException("User not exist");
    }

    @Override
    public ConfirmCodeResponse confirmForgetPasswordCode(String username, String code) throws ModuleException {
        if (!username.isEmpty() && !code.isEmpty()) {
            Optional<ForgetPassword> forgetPasswordOpt = forgetPasswordRepository.findByUsername(username);
            if (forgetPasswordOpt.isPresent()) {
                ForgetPassword forgetPassword = forgetPasswordOpt.get();
                if (code.equals(forgetPassword.getCode())) {
                    String generatedConfirmCode = generateAlphaNumeric();
                    forgetPassword.setStatus(ForgetPasswordCodeStatus.EXPIRED);
                    forgetPassword.setConfirmedCode(generatedConfirmCode);
                    forgetPasswordRepository.save(forgetPassword);
                    return new ConfirmCodeResponse(String.format("auth/modify-password?confirmedCode=%s&username=%s", generatedConfirmCode, username));
                }
            }
        }
        throw new ModuleException("Bad Strings");
    }

    @Override
    public CommonMessageResponse modifyPassword(ModifyPasswordRequest modifyPasswordRequest) throws ModuleException {
        if (modifyPasswordRequest != null && modifyPasswordRequest.isNonEmpty() &&
                UserUtil.validatePassword(modifyPasswordRequest.getNewPassword())) {
            Optional<UserEntity> userOpt = Optional.empty();
            if (modifyPasswordRequest.getPhoneNumber() != null && !modifyPasswordRequest.getPhoneNumber().isEmpty()) {
                userOpt = userRepository.findByPhoneNumber(modifyPasswordRequest.getPhoneNumber());
            } else if(modifyPasswordRequest.getUsername() != null && !modifyPasswordRequest.getUsername().isEmpty()) {
                userOpt =userRepository.findByUsername(modifyPasswordRequest.getUsername());
            }
            if (modifyPasswordRequest.getCurrentPassword() != null && !modifyPasswordRequest.getCurrentPassword().isEmpty()) {
                if (userOpt.isPresent()) {
                    UserEntity user = userOpt.get();
                    try {
                        authenticate(user.getUsername(), modifyPasswordRequest.getCurrentPassword());
                    } catch (Exception e) {
                        throw new ModuleException("Wrong email or current password");
                    }
                    user.setPassword(new BCryptPasswordEncoder().encode(modifyPasswordRequest.getNewPassword()));

                    userRepository.save(user);
                    return new CommonMessageResponse(SUCCESSFULLY_MODIFIED);
                } else {
                    throw new ModuleException("No user exists");
                }
            } else if (modifyPasswordRequest.getCode() != null && !modifyPasswordRequest.getCode().isEmpty()) {
                Optional<ForgetPassword> forgetPasswordOpt = forgetPasswordRepository.findByUsername(modifyPasswordRequest.getUsername());
                if (forgetPasswordOpt.isPresent() && forgetPasswordOpt.get().getConfirmedCode() != null
                && forgetPasswordOpt.get().getConfirmedCode().equals(modifyPasswordRequest.getCode())) {
                    Optional<UserEntity> userOptCode = userRepository.findByUsername(modifyPasswordRequest.getUsername());
                    if (userOptCode.isPresent()) {
                        UserEntity userCode = userOptCode.get();
                        userCode.setPassword(new BCryptPasswordEncoder().encode(modifyPasswordRequest.getNewPassword()));

                        userRepository.save(userCode);
                        return new CommonMessageResponse(SUCCESSFULLY_MODIFIED);
                    }
                } else {
                    throw new ModuleException("Wrong confirmation code");
                }
            }
        }
        throw new ModuleException("Bad Strings");
    }

    @Override
    public String refreshToken() throws Exception {
        return jwtTokenUtil.generateToken(userDetailsService
                .loadUserByUsername(getCurrentUserName()));
    }

    @Override
    public UserDetailsResponse getUserDetails() throws Exception {
        Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(getCurrentUserName());
        if (userEntityOptional.isPresent()) {
            return UserUtil.convertToUserDetails(userEntityOptional.get());
        }
        throw new Exception("Something went wrong");
    }

    @Override
    public UserDetailsResponse getUserDetails(String username) throws Exception {
        Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(username);
        if (userEntityOptional.isPresent()) {
            return UserUtil.convertToUserDetails(userEntityOptional.get());
        }
        throw new Exception("Something went wrong");
    }


}
