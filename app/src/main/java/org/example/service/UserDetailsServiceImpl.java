package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.UserInfo;
import org.example.eventProducer.UserInfoProducer;
import org.example.model.UserInfoDto;
import org.example.repository.UserRepository;
import org.example.util.UserValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            UserInfo userInfo = userRepository.findByUsername(username);

            System.out.println("value of userInfo recieved is " + userInfo);

            if(userInfo == null){
                throw new UsernameNotFoundException("Could not find User. Please sign up first!");
            }
            return new CustomUserDetails(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public UserInfo checkIfUserAlreadyExist(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }


    public Boolean signupUser(UserInfoDto userInfoDto){
        if(userInfoDto.getEmail() != null && !userInfoDto.getEmail().isEmpty() && !UserValidationUtil.isValidEmail(userInfoDto)){
            throw new IllegalArgumentException("Provided email address is invalid!");
        }

        if(userInfoDto.getPhoneNumber() != null && !userInfoDto.getPhoneNumber().isEmpty() && !UserValidationUtil.isValidPhoneNumber(userInfoDto)){
            throw new IllegalArgumentException("Provided phone number is invalid!");
        }
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));

        if(Objects.nonNull(checkIfUserAlreadyExist(userInfoDto))) return Boolean.FALSE;

        String userId = UUID.randomUUID().toString();
        userRepository.save(new UserInfo(userId,userInfoDto.getUsername(),userInfoDto.getPassword(),new HashSet<>()));

//        sending event to kafka
        userInfoProducer.sendEventToKafka(userInfoDto);
        return Boolean.TRUE;
    }
}
