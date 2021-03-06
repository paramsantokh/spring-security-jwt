package com.example.springsecurityjwt;

import com.example.springsecurityjwt.filters.JwtRequestFilter;
import com.example.springsecurityjwt.model.AuthenticationRequest;
import com.example.springsecurityjwt.model.AuthenticationResponse;
import com.example.springsecurityjwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {
  
  @Autowired
  private AuthenticationManager authenticationManager;
  
  @Autowired
  private MyUserDetailsService userDetailsService;
  
  @Autowired
  private JwtUtil jwtUtil;
  
  @RequestMapping ("/hello")
  public String getMessage() {
    return "Hello World!!";
  }
  
  @RequestMapping (value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
    } catch (BadCredentialsException ex) {
      throw new Exception("User-name or Password incorrect");
    }
    
    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
    
    String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}
