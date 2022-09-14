package com.order.management.api.yamkelavenfolo.controllers;

import com.order.management.api.yamkelavenfolo.model.authentication.AuthenticationRequest;
import com.order.management.api.yamkelavenfolo.model.authentication.AuthenticationResponse;
import com.order.management.api.yamkelavenfolo.model.errorHandling.AuthenticationErrorDetail;
import com.order.management.api.yamkelavenfolo.model.response.ResponseEntity;
import com.order.management.api.yamkelavenfolo.service.UsersDetailsService;
import com.order.management.api.yamkelavenfolo.service.security.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersDetailsService usersDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @PostMapping(value = "/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password())
            );

            final UserDetails userDetails = usersDetailsService.loadUserByUsername(authenticationRequest.username());

            final String token = tokenUtil.generateToken(userDetails);

            return new ResponseEntity(HttpStatus.OK.toString(), new AuthenticationResponse(token));

        } catch (BadCredentialsException e) {
            AuthenticationErrorDetail errorDetail = new AuthenticationErrorDetail("Incorrect username or password");
            return new ResponseEntity(HttpStatus.BAD_REQUEST.toString(), errorDetail);
        }
    }
}
