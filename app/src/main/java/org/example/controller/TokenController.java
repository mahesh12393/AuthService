package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entities.RefreshToken;
import org.example.request.RefreshTokenDto;
import org.example.response.JwtResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.example.request.AuthRequestDto;
import org.example.service.JwtService;
import org.example.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto){
        System.out.println("the details of user recieved are "+ authRequestDto.getUsername() + " and passowrd is " + authRequestDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            System.out.println("is authenticated and is inside   ");
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDto.getUsername());
            return new ResponseEntity<>(JwtResponseDto.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDto.getUsername()))
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK
            );
        } else {
            return new ResponseEntity("Exception in User Service while login!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("auth/v1/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenDto refreshTokenDto){
        return refreshTokenService.findByToken(refreshTokenDto.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo) // passing functional interface
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenDto.getToken()).build();
                }).orElseThrow(()-> new RuntimeException("Refresh token does not exist in DB!"));
    }
}














