package com.example.gestion_navette_v1.security.services;

import com.example.gestion_navette_v1.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDatailsService implements UserDetailsService {
   private final AppUserService appUserService;
   
   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     return new CustomUserDetails(appUserService.getUserByEmail(email));
   }
}